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

package com.liferay.portal.license.deployer.internal.component.enabler;

import com.liferay.osgi.util.ComponentUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.license.deployer.internal.installer.LicenseInstaller;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Tina Tian
 */
@Component(service = {})
public class ComponentEnabler {

	@Activate
	protected void activate(ComponentContext componentContext) {
		ComponentUtil.enableComponents(
			ModuleServiceLifecycle.class,
			ModuleServiceLifecycle.LICENSE_INSTALL, componentContext,
			LicenseInstaller.class);
	}

}