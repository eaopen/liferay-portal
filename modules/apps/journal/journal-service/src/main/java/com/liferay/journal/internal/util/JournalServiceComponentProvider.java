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

package com.liferay.journal.internal.util;

import com.liferay.journal.configuration.JournalGroupServiceConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Juergen Kappler
 */
@Component(
	configurationPid = "com.liferay.journal.configuration.JournalGroupServiceConfiguration",
	service = {}
)
public class JournalServiceComponentProvider {

	public static JournalServiceComponentProvider
		getJournalServiceComponentProvider() {

		return _journalServiceComponentProvider;
	}

	public JournalGroupServiceConfiguration
		getJournalGroupServiceConfiguration() {

		return _journalGroupServiceConfiguration;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_journalGroupServiceConfiguration = ConfigurableUtil.createConfigurable(
			JournalGroupServiceConfiguration.class, properties);

		_journalServiceComponentProvider = this;
	}

	@Deactivate
	protected void deactivate() {
		_journalServiceComponentProvider = null;
	}

	protected void unsetJournalGroupServiceConfiguration(
		JournalGroupServiceConfiguration journalGroupServiceConfiguration) {

		_journalGroupServiceConfiguration = null;
	}

	private static JournalServiceComponentProvider
		_journalServiceComponentProvider;

	private JournalGroupServiceConfiguration _journalGroupServiceConfiguration;

}