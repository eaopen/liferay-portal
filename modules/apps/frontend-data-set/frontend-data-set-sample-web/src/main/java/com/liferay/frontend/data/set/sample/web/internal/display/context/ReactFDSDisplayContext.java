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

package com.liferay.frontend.data.set.sample.web.internal.display.context;

import com.liferay.frontend.data.set.sample.web.internal.constants.FDSSampleFDSNames;
import com.liferay.frontend.data.set.sample.web.internal.view.util.FDSViewSerializerUtil;
import com.liferay.frontend.data.set.view.FDSViewSerializer;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marko Cikos
 */
public class ReactFDSDisplayContext {

	public ReactFDSDisplayContext(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;
	}

	public String getAPIURL() {
		return "/o/c/fdssamples";
	}

	public Object getViews() {
		FDSViewSerializer fdsViewSerializer =
			FDSViewSerializerUtil.getFDSViewSerializer();

		return fdsViewSerializer.serialize(
			FDSSampleFDSNames.REACT, PortalUtil.getLocale(_httpServletRequest));
	}

	private final HttpServletRequest _httpServletRequest;

}