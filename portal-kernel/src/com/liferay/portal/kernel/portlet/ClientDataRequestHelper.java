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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.model.Portlet;

import java.io.IOException;

import java.util.Collection;

import javax.portlet.PortletException;

import javax.servlet.http.Part;

/**
 * @author Jiefeng Wu
 */
public interface ClientDataRequestHelper {

	public Part getPart(String name, Object request, Portlet portlet)
		throws IOException, PortletException;

	public Collection<Part> getParts(Object request, Portlet portlet)
		throws IOException, PortletException;

}