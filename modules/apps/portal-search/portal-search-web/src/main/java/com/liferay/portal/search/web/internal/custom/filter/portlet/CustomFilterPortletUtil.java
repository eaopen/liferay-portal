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

package com.liferay.portal.search.web.internal.custom.filter.portlet;

import com.liferay.portal.kernel.util.Validator;

/**
 * @author André de Oliveira
 */
public class CustomFilterPortletUtil {

	public static String getParameterName(
		CustomFilterPortletPreferences customFilterPortletPreferences) {

		String parameterName =
			customFilterPortletPreferences.getParameterName();

		if (Validator.isNotNull(parameterName)) {
			return parameterName;
		}

		String filterField = customFilterPortletPreferences.getFilterField();

		if (Validator.isNotNull(filterField)) {
			return filterField;
		}

		return "customfilter";
	}

}