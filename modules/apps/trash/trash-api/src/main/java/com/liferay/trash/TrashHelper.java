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

package com.liferay.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.trash.model.TrashEntry;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Eudaldo Alonso
 */
@ProviderType
public interface TrashHelper {

	public int getMaxAge(Group group);

	public String getNewName(
			ThemeDisplay themeDisplay, String className, long classPK,
			String oldName)
		throws PortalException;

	public String getOriginalTitle(String title);

	public String getOriginalTitle(String title, String paramName);

	public TrashEntry getTrashEntry(TrashedModel trashedModel)
		throws PortalException;

	public String getTrashTitle(long entryId);

	public PortletURL getViewContentURL(
			HttpServletRequest httpServletRequest, String className,
			long classPK)
		throws PortalException;

	public boolean isInTrashContainer(TrashedModel trashedModel);

	public boolean isInTrashExplicitly(TrashedModel trashedModel);

	public boolean isInTrashImplicitly(TrashedModel trashedModel);

	public boolean isTrashEnabled(Group group);

	public boolean isTrashEnabled(long groupId) throws PortalException;

}