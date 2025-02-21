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

package com.liferay.document.library.web.internal.upgrade.v1_0_0;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.portal.kernel.settings.SettingsLocatorHelper;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portlet.documentlibrary.constants.DLConstants;

/**
 * @author Sergio González
 */
public class UpgradePortletSettings
	extends com.liferay.portal.upgrade.v7_0_0.UpgradePortletSettings {

	public UpgradePortletSettings(SettingsLocatorHelper settingsLocatorHelper) {
		super(settingsLocatorHelper);
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgradeMainPortlet(
			DLPortletKeys.DOCUMENT_LIBRARY, DLConstants.SERVICE_NAME,
			PortletKeys.PREFS_OWNER_TYPE_GROUP, true);

		upgradeDisplayPortlet(
			DLPortletKeys.DOCUMENT_LIBRARY, DLConstants.SERVICE_NAME,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT);
		upgradeDisplayPortlet(
			DLPortletKeys.MEDIA_GALLERY_DISPLAY, DLConstants.SERVICE_NAME,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT);
	}

}