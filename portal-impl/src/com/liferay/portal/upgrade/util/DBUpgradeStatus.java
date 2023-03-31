/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.upgrade.util;

import com.liferay.petra.function.UnsafeBiConsumer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.util.DBUpgradeChecker;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.apache.logging.log4j.ThreadContext;

/**
 * @author Luis Ortiz
 */
public class DBUpgradeStatus {

	public static void addErrorMessage(String loggerName, String message) {
		Map<String, Integer> messages = _errorMessages.computeIfAbsent(
			loggerName, key -> new ConcurrentHashMap<>());

		int occurrences = messages.computeIfAbsent(message, key -> 0);

		occurrences++;

		messages.put(message, occurrences);
	}

	public static void addUpgradeProcessMessage(
		String loggerName, String message) {

		List<String> messages = _upgradeProcessMessages.computeIfAbsent(
			loggerName, key -> new ArrayList<>());

		messages.add(message);
	}

	public static void addWarningMessage(String loggerName, String message) {
		Map<String, Integer> messages = _warningMessages.computeIfAbsent(
			loggerName, key -> new ConcurrentHashMap<>());

		int occurrences = messages.computeIfAbsent(message, key -> 0);

		occurrences++;

		messages.put(message, occurrences);
	}

	public static void finish(DBUpgradeChecker dbUpgradeChecker) {
		_setFinalSchemaVersion();
		_setFinalStatus(dbUpgradeChecker);
		_setType();

		if (PropsValues.UPGRADE_LOG_CONTEXT_ENABLED) {
			ThreadContext.put("upgrade.type", _type);
			ThreadContext.put("upgrade.result", _status);
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Upgrade of type ", _type, " finished with ", _status,
					" result."));
		}

		if (PropsValues.UPGRADE_LOG_CONTEXT_ENABLED) {
			ThreadContext.clearMap();
		}
	}

	public static Map<String, Map<String, Integer>> getErrorMessages() {
		_filterMessages();

		return _errorMessages;
	}

	public static String getFinalSchemaVersion(String servletContextName) {
		ServletSchemaVersions servletSchemaVersions =
			_servletSchemaVersionsMap.get(servletContextName);

		if (servletSchemaVersions == null) {
			return null;
		}

		return servletSchemaVersions.getFinal();
	}

	public static String getInitialSchemaVersion(String servletContextName) {
		ServletSchemaVersions servletSchemaVersions =
			_servletSchemaVersionsMap.get(servletContextName);

		if (servletSchemaVersions == null) {
			return null;
		}

		return servletSchemaVersions.getInitial();
	}

	public static String getStatus() {
		return _status;
	}

	public static String getType() {
		return _type;
	}

	public static Map<String, ArrayList<String>> getUpgradeProcessMessages() {
		return _upgradeProcessMessages;
	}

	public static Map<String, Map<String, Integer>> getWarningMessages() {
		_filterMessages();

		return _warningMessages;
	}

	public static void setNoUpgradesEnabled() {
		_status = "Not enabled";
		_type = "Not enabled";
	}

	public static void start() {
		_status = "Running";

		_browseReleaseTable(
			(moduleSchemaVersions, schemaVersion) ->
				moduleSchemaVersions.setInitial(schemaVersion));
	}

	private static void _browseReleaseTable(
		UnsafeBiConsumer<ServletSchemaVersions, String, Exception>
			unsafeBiConsumer) {

		DataSource dataSource = InfrastructureUtil.getDataSource();

		if (dataSource == null) {
			return;
		}

		try (Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select servletContextName, schemaVersion from Release_")) {

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String servletContextName = resultSet.getString(
					"servletContextName");
				String schemaVersion = resultSet.getString("schemaVersion");

				ServletSchemaVersions moduleSchemaVersions =
					_servletSchemaVersionsMap.get(servletContextName);

				if (moduleSchemaVersions == null) {
					moduleSchemaVersions = new ServletSchemaVersions(null);

					_servletSchemaVersionsMap.put(
						servletContextName, moduleSchemaVersions);
				}

				unsafeBiConsumer.accept(moduleSchemaVersions, schemaVersion);
			}
		}
		catch (SQLException sqlException) {
			_log.error("Unable to get schema version", sqlException);
		}
		catch (Exception exception) {
			_log.error("Unable to process Release_ table", exception);
		}
	}

	private static void _filterMessages() {
		if (!_filtered) {
			for (String filteredClassName : _FILTERED_CLASS_NAMES) {
				_errorMessages.remove(filteredClassName);
				_warningMessages.remove(filteredClassName);
			}

			_filtered = true;
		}
	}

	private static void _setFinalSchemaVersion() {
		_browseReleaseTable(
			(moduleSchemaVersions, schemaVersion) ->
				moduleSchemaVersions.setFinal(schemaVersion));
	}

	private static void _setFinalStatus(DBUpgradeChecker dbUpgradeChecker) {
		if (dbUpgradeChecker == null) {
			_log.error(
				"Not possible to check upgrade successful completion. Please " +
					"check manually.");

			_status = "Failure";

			return;
		}

		if (!_errorMessages.isEmpty() || !dbUpgradeChecker.check()) {
			_status = "Failure";

			return;
		}

		if (!_warningMessages.isEmpty()) {
			_status = "Warning";

			return;
		}

		_status = "Success";
	}

	private static void _setType() {
		String upgradeType = "No upgrade";

		for (Map.Entry<String, ServletSchemaVersions> servlet :
				_servletSchemaVersionsMap.entrySet()) {

			ServletSchemaVersions schemaVersions = servlet.getValue();

			if (schemaVersions.getInitial() == null) {
				continue;
			}

			Version initialVersion = Version.parseVersion(
				schemaVersions.getInitial());
			Version finalVersion = Version.parseVersion(
				schemaVersions.getFinal());

			if (initialVersion.getMajor() < finalVersion.getMajor()) {
				upgradeType = "Major";

				break;
			}

			if (initialVersion.getMinor() < finalVersion.getMinor()) {
				upgradeType = "Minor";

				continue;
			}

			if (initialVersion.getMicro() < finalVersion.getMicro()) {
				if (upgradeType.compareTo("Minor") != 0) {
					upgradeType = "Micro";
				}

				continue;
			}

			String initialQualifier = initialVersion.getQualifier();
			String finalQualifier = finalVersion.getQualifier();

			if (!initialQualifier.isEmpty() && finalQualifier.isEmpty() &&
				(upgradeType.compareTo("Minor") != 0)) {

				upgradeType = "Micro";
			}
		}

		_type = upgradeType;
	}

	private static final String[] _FILTERED_CLASS_NAMES = {
		"com.liferay.portal.search.elasticsearch7.internal.sidecar." +
			"SidecarManager"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		DBUpgradeStatus.class);

	private static final Map<String, Map<String, Integer>> _errorMessages =
		new ConcurrentHashMap<>();
	private static boolean _filtered;
	private static final Map<String, ServletSchemaVersions>
		_servletSchemaVersionsMap = new ConcurrentHashMap<>();
	private static String _status = "Pending";
	private static String _type = "Not calculated";
	private static final Map<String, ArrayList<String>>
		_upgradeProcessMessages = new ConcurrentHashMap<>();
	private static final Map<String, Map<String, Integer>> _warningMessages =
		new ConcurrentHashMap<>();

	private static class ServletSchemaVersions {

		public ServletSchemaVersions(String initialSchemaVersion) {
			_initial = initialSchemaVersion;
		}

		public String getFinal() {
			return _final;
		}

		public String getInitial() {
			return _initial;
		}

		public void setFinal(String aFinal) {
			_final = aFinal;
		}

		public void setInitial(String initial) {
			if (initial == null) {
				_initial = "0.0.0";

				return;
			}

			_initial = initial;
		}

		private String _final;
		private String _initial;

	}

}