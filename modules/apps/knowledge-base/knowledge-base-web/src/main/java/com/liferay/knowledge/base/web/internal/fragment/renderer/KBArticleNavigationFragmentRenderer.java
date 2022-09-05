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

package com.liferay.knowledge.base.web.internal.fragment.renderer;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.IOException;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = FragmentRenderer.class)
public class KBArticleNavigationFragmentRenderer implements FragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "content-display";
	}

	@Override
	public String getConfiguration(
		FragmentRendererContext fragmentRendererContext) {

		return JSONUtil.put(
			"fieldSets",
			JSONUtil.putAll(
				JSONUtil.put(
					"fields",
					JSONUtil.putAll(
						JSONUtil.put(
							"label", "item"
						).put(
							"name", "itemSelector"
						).put(
							"type", "itemSelector"
						))))
		).toString();
	}

	@Override
	public String getIcon() {
		return "page";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "knowledge-base-article-navigation");
	}

	@Override
	public void render(
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			KBArticle kbArticle = _getKBArticle(
				httpServletRequest, fragmentRendererContext);

			if (kbArticle == null) {
				return;
			}

			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher("/navigation/view.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (ServletException servletException) {
			_log.error(
				"KB article navigation fragment can not be rendered",
				servletException);
		}
	}

	private KBArticle _getKBArticle(
		HttpServletRequest httpServletRequest,
		FragmentRendererContext fragmentRendererContext) {

		try {
			FragmentEntryLink fragmentEntryLink =
				fragmentRendererContext.getFragmentEntryLink();

			JSONObject jsonObject =
				(JSONObject)_fragmentEntryConfigurationParser.getFieldValue(
					getConfiguration(fragmentRendererContext),
					fragmentEntryLink.getEditableValues(),
					fragmentRendererContext.getLocale(), "itemSelector");

			if ((jsonObject != null) && jsonObject.has("className") &&
				jsonObject.has("classPK") &&
				Objects.equals(
					jsonObject.get("className"), KBArticle.class.getName())) {

				return _kbArticleService.fetchLatestKBArticle(
					jsonObject.getLong("classPK"),
					WorkflowConstants.STATUS_ANY);
			}

			Optional<Object> displayObjectOptional =
				fragmentRendererContext.getDisplayObjectOptional();

			Object displayObject = displayObjectOptional.orElseGet(
				() -> httpServletRequest.getAttribute(
					WebKeys.LAYOUT_ASSET_ENTRY));

			if (displayObject instanceof KBArticle) {
				return (KBArticle)displayObject;
			}

			return null;
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KBArticleNavigationFragmentRenderer.class);

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private KBArticleService _kbArticleService;

	@Reference
	private Language _language;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.knowledge.base.web)"
	)
	private ServletContext _servletContext;

}