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

package com.liferay.commerce.order.rule.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.order.rule.entry.type.COREntryTypeJSPContributorRegistry;
import com.liferay.commerce.order.rule.entry.type.COREntryTypeRegistry;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.service.COREntryService;
import com.liferay.commerce.order.rule.web.internal.display.context.COREntryDisplayContext;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Victor Silvestre
 */
@Component(
	property = "screen.navigation.entry.order:Integer=10",
	service = ScreenNavigationEntry.class
)
public class COREntryDetailsScreenNavigationEntry
	extends COREntryDetailsScreenNavigationCategory
	implements ScreenNavigationEntry<COREntry> {

	@Override
	public String getEntryKey() {
		return getCategoryKey();
	}

	@Override
	public boolean isVisible(User user, COREntry corEntry) {
		if (corEntry == null) {
			return false;
		}

		boolean hasPermission = false;

		try {
			hasPermission = _corEntryModelResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				corEntry.getCOREntryId(), ActionKeys.UPDATE);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return hasPermission;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		COREntryDisplayContext corEntryDisplayContext =
			new COREntryDisplayContext(
				_commerceCurrencyService, _corEntryModelResourcePermission,
				_corEntryService, _corEntryTypeJSPContributorRegistry,
				_corEntryTypeRegistry, httpServletRequest, _portal);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, corEntryDisplayContext);

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse, "/cor_entry/details.jsp");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		COREntryDetailsScreenNavigationEntry.class);

	@Reference
	private CommerceCurrencyService _commerceCurrencyService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.order.rule.model.COREntry)"
	)
	private ModelResourcePermission<COREntry> _corEntryModelResourcePermission;

	@Reference
	private COREntryService _corEntryService;

	@Reference
	private COREntryTypeJSPContributorRegistry
		_corEntryTypeJSPContributorRegistry;

	@Reference
	private COREntryTypeRegistry _corEntryTypeRegistry;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

}