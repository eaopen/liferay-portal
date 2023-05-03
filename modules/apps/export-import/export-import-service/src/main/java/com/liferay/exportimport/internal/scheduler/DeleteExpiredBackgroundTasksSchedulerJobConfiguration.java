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

package com.liferay.exportimport.internal.scheduler;

import com.liferay.exportimport.configuration.ExportImportServiceConfiguration;
import com.liferay.exportimport.kernel.background.task.BackgroundTaskExecutorNames;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.Time;

import java.util.Date;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author To Trinh
 * @author Ha Tang
 */
@Component(
	configurationPid = "com.liferay.exportimport.configuration.ExportImportServiceConfiguration",
	service = SchedulerJobConfiguration.class
)
public class DeleteExpiredBackgroundTasksSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		return () -> _companyLocalService.forEachCompanyId(
			companyId -> _deleteExpiredBackGroundTasks(companyId));
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			_CHECK_INTERVAL, TimeUnit.MINUTE);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_exportImportServiceConfiguration = ConfigurableUtil.createConfigurable(
			ExportImportServiceConfiguration.class, properties);
	}

	private void _deleteExpiredBackGroundTasks(long companyId)
		throws PortalException {

		ExportImportServiceConfiguration
			companyExportImportServiceConfiguration =
				_getExportImportServiceConfiguration(companyId);

		int exportImportExpirationDays =
			companyExportImportServiceConfiguration.
				exportImportExpirationDays();

		if (exportImportExpirationDays <= 0) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Export/Import cleanup job on instance " + companyId +
						" is disabled");
			}

			return;
		}

		ActionableDynamicQuery actionableDynamicQuery =
			_backgroundTaskLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				dynamicQuery.add(
					RestrictionsFactoryUtil.eq("companyId", companyId));

				Property taskExecutorClassName = PropertyFactoryUtil.forName(
					"taskExecutorClassName");

				dynamicQuery.add(
					taskExecutorClassName.in(_BACKGROUND_TASK_EXECUTOR_NAMES));

				Property status = PropertyFactoryUtil.forName("status");

				dynamicQuery.add(status.in(_STATUSES));

				long exportImportExpiryTime =
					exportImportExpirationDays * Time.DAY;

				Date expirationDate = new Date(
					System.currentTimeMillis() - exportImportExpiryTime);

				Property modifiedDate = PropertyFactoryUtil.forName(
					"modifiedDate");

				dynamicQuery.add(modifiedDate.lt(expirationDate));
			});

		actionableDynamicQuery.setPerformActionMethod(
			(BackgroundTask backgroundTask) -> {
				try {
					_backgroundTaskLocalService.deleteBackgroundTask(
						backgroundTask);
				}
				catch (PortalException portalException) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable delete backgroundTask " +
								backgroundTask.getBackgroundTaskId(),
							portalException);
					}
				}
			});

		try {
			actionableDynamicQuery.performActions();
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
	}

	private ExportImportServiceConfiguration
		_getExportImportServiceConfiguration(long companyId) {

		try {
			return _configurationProvider.getCompanyConfiguration(
				ExportImportServiceConfiguration.class, companyId);
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException);

			return _exportImportServiceConfiguration;
		}
	}

	private static final String[] _BACKGROUND_TASK_EXECUTOR_NAMES = {
		BackgroundTaskExecutorNames.LAYOUT_EXPORT_BACKGROUND_TASK_EXECUTOR,
		BackgroundTaskExecutorNames.LAYOUT_IMPORT_BACKGROUND_TASK_EXECUTOR,
		BackgroundTaskExecutorNames.PORTLET_EXPORT_BACKGROUND_TASK_EXECUTOR,
		BackgroundTaskExecutorNames.PORTLET_IMPORT_BACKGROUND_TASK_EXECUTOR
	};

	private static final int _CHECK_INTERVAL = 30;

	private static final int[] _STATUSES = {
		BackgroundTaskConstants.STATUS_CANCELLED,
		BackgroundTaskConstants.STATUS_SUCCESSFUL
	};

	private static final Log _log = LogFactoryUtil.getLog(
		DeleteExpiredBackgroundTasksSchedulerJobConfiguration.class);

	@Reference
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	private volatile ExportImportServiceConfiguration
		_exportImportServiceConfiguration;

}