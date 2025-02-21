/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.security.audit.web.internal.portlet;

import com.liferay.portal.kernel.portlet.AdministratorControlPanelEntry;
import com.liferay.portal.kernel.portlet.ControlPanelEntry;
import com.liferay.portal.security.audit.web.internal.constants.AuditPortletKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Stian Sigvartsen
 */
@Component(
	property = "javax.portlet.name=" + AuditPortletKeys.AUDIT,
	service = ControlPanelEntry.class
)
public class AuditControlPanelEntry extends AdministratorControlPanelEntry {
}