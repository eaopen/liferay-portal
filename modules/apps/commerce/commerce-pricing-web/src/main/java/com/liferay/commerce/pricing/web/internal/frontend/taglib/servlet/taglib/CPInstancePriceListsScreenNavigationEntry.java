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

package com.liferay.commerce.pricing.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.price.list.portlet.action.CommercePriceListActionHelper;
import com.liferay.commerce.price.list.service.CommercePriceEntryService;
import com.liferay.commerce.pricing.web.internal.display.context.CPInstanceCommercePriceEntryDisplayContext;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.portlet.action.ActionHelper;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.servlet.ServletContext;
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
public class CPInstancePriceListsScreenNavigationEntry
	extends CPInstancePriceListsScreenNavigationCategory
	implements ScreenNavigationEntry<CPInstance> {

	@Override
	public String getEntryKey() {
		return getCategoryKey();
	}

	@Override
	public boolean isVisible(User user, CPInstance cpInstance) {
		if (cpInstance == null) {
			return false;
		}

		return true;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			CPInstanceCommercePriceEntryDisplayContext
				cpInstanceCommercePriceEntryDisplayContext =
					new CPInstanceCommercePriceEntryDisplayContext(
						_actionHelper, _commercePriceEntryService,
						_commercePriceListActionHelper, httpServletRequest,
						_itemSelector);

			httpServletRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				cpInstanceCommercePriceEntryDisplayContext);
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		_jspRenderer.renderJSP(
			_setServletContext, httpServletRequest, httpServletResponse,
			"/commerce_price_lists/cp_instance" +
				"/cp_instance_commerce_price_lists.jsp");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPInstancePriceListsScreenNavigationEntry.class);

	@Reference
	private ActionHelper _actionHelper;

	@Reference
	private CommercePriceEntryService _commercePriceEntryService;

	@Reference
	private CommercePriceListActionHelper _commercePriceListActionHelper;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.pricing.web)"
	)
	private ServletContext _setServletContext;

}