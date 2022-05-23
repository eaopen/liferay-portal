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

package com.liferay.fragment.entry.processor.freemarker;

import com.liferay.fragment.constants.FragmentConfigurationFieldDataType;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.entry.processor.freemarker.internal.configuration.FreeMarkerFragmentEntryProcessorConfiguration;
import com.liferay.fragment.entry.processor.freemarker.internal.templateparser.InputTemplateNode;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessor;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.petra.io.DummyWriter;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.DummyHttpServletResponse;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true, property = "fragment.entry.processor.priority:Integer=1",
	service = FragmentEntryProcessor.class
)
public class FreeMarkerFragmentEntryProcessor
	implements FragmentEntryProcessor {

	@Override
	public JSONObject getDefaultEditableValuesJSONObject(
		String html, String configuration) {

		return _fragmentEntryConfigurationParser.
			getConfigurationDefaultValuesJSONObject(configuration);
	}

	@Override
	public String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String html,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		if (Validator.isNull(html)) {
			return html;
		}

		FreeMarkerFragmentEntryProcessorConfiguration
			freeMarkerFragmentEntryProcessorConfiguration =
				_configurationProvider.getCompanyConfiguration(
					FreeMarkerFragmentEntryProcessorConfiguration.class,
					fragmentEntryLink.getCompanyId());

		if (!freeMarkerFragmentEntryProcessorConfiguration.enable() &&
			Validator.isNull(fragmentEntryLink.getRendererKey()) &&
			!fragmentEntryLink.isSystem()) {

			return html;
		}

		if (fragmentEntryProcessorContext.getHttpServletRequest() == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"HTTP servlet request is not set in the fragment entry " +
						"processor context");
			}

			return html;
		}

		if (fragmentEntryProcessorContext.getHttpServletResponse() == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"HTTP servlet response is not set in the fragment entry " +
						"processor context");
			}

			return html;
		}

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL,
			new StringTemplateResource("template_id", "[#ftl] " + html), true);

		template.put(TemplateConstants.WRITER, unsyncStringWriter);

		JSONObject configurationValuesJSONObject =
			_fragmentEntryConfigurationParser.getConfigurationJSONObject(
				fragmentEntryLink.getConfiguration(),
				fragmentEntryLink.getEditableValues(),
				fragmentEntryProcessorContext.getLocale());

		template.putAll(
			HashMapBuilder.<String, Object>put(
				"configuration", configurationValuesJSONObject
			).put(
				"fragmentElementId",
				fragmentEntryProcessorContext.getFragmentElementId()
			).put(
				"fragmentEntryLinkNamespace", fragmentEntryLink.getNamespace()
			).put(
				"layoutMode",
				_getLayoutMode(
					fragmentEntryProcessorContext.getHttpServletRequest())
			).putAll(
				_fragmentEntryConfigurationParser.getContextObjects(
					configurationValuesJSONObject,
					fragmentEntryLink.getConfiguration(),
					fragmentEntryProcessorContext.getSegmentsEntryIds())
			).build());

		if (_isInputFragmentEntryType(fragmentEntryLink)) {
			template.put("input", _toInputTemplateNode(fragmentEntryLink));
		}

		template.prepareTaglib(
			fragmentEntryProcessorContext.getHttpServletRequest(),
			fragmentEntryProcessorContext.getHttpServletResponse());

		template.prepare(fragmentEntryProcessorContext.getHttpServletRequest());

		try {
			template.processTemplate(unsyncStringWriter);
		}
		catch (TemplateException templateException) {
			throw new FragmentEntryContentException(
				_getMessage(templateException), templateException);
		}

		return unsyncStringWriter.toString();
	}

	@Override
	public void validateFragmentEntryHTML(String html, String configuration)
		throws PortalException {

		FreeMarkerFragmentEntryProcessorConfiguration
			freeMarkerFragmentEntryProcessorConfiguration =
				_configurationProvider.getCompanyConfiguration(
					FreeMarkerFragmentEntryProcessorConfiguration.class,
					CompanyThreadLocal.getCompanyId());

		if (!freeMarkerFragmentEntryProcessorConfiguration.enable() ||
			!_isFreemarkerTemplate(html)) {

			return;
		}

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL,
			new StringTemplateResource("template_id", "[#ftl] " + html), true);

		try {
			HttpServletRequest httpServletRequest = null;
			HttpServletResponse httpServletResponse = null;

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			if (serviceContext != null) {
				httpServletRequest = serviceContext.getRequest();
				httpServletResponse = serviceContext.getResponse();
			}

			if (httpServletResponse == null) {
				httpServletResponse = new DummyHttpServletResponse();
			}

			if ((httpServletRequest != null) &&
				(httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY) !=
					null)) {

				JSONObject configurationDefaultValuesJSONObject =
					_fragmentEntryConfigurationParser.
						getConfigurationDefaultValuesJSONObject(configuration);

				template.putAll(
					HashMapBuilder.<String, Object>put(
						"configuration", configurationDefaultValuesJSONObject
					).put(
						"fragmentElementId", StringPool.BLANK
					).put(
						"fragmentEntryLinkNamespace", StringPool.BLANK
					).put(
						"layoutMode", Constants.VIEW
					).putAll(
						_fragmentEntryConfigurationParser.getContextObjects(
							configurationDefaultValuesJSONObject, configuration,
							new long[0])
					).build());

				template.prepareTaglib(httpServletRequest, httpServletResponse);

				template.prepare(httpServletRequest);

				template.processTemplate(new DummyWriter());
			}
		}
		catch (TemplateException templateException) {
			throw new FragmentEntryContentException(
				_getMessage(templateException), templateException);
		}
	}

	private String _getLayoutMode(HttpServletRequest httpServletRequest) {
		return ParamUtil.getString(
			_portal.getOriginalServletRequest(httpServletRequest), "p_l_mode",
			Constants.VIEW);
	}

	private String _getMessage(TemplateException templateException) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getClass());

		String message = LanguageUtil.get(
			resourceBundle, "freemarker-syntax-is-invalid");

		Throwable causeThrowable = templateException.getCause();

		String causeThrowableMessage = causeThrowable.getLocalizedMessage();

		if (Validator.isNotNull(causeThrowableMessage)) {
			message = message + "\n\n" + causeThrowableMessage;
		}

		return message;
	}

	private boolean _isFreemarkerTemplate(String html) {
		if (html.contains("${") || html.contains("<#") || html.contains("<@")) {
			return true;
		}

		return false;
	}

	private boolean _isInputFragmentEntryType(
		FragmentEntryLink fragmentEntryLink) {

		FragmentEntry fragmentEntry = null;

		if (Validator.isNotNull(fragmentEntryLink.getRendererKey())) {
			fragmentEntry =
				_fragmentCollectionContributorTracker.getFragmentEntry(
					fragmentEntryLink.getRendererKey());
		}

		if (fragmentEntry == null) {
			fragmentEntry = _fragmentEntryLocalService.fetchFragmentEntry(
				fragmentEntryLink.getFragmentEntryId());
		}

		if ((fragmentEntry != null) &&
			(fragmentEntry.getType() == FragmentConstants.TYPE_INPUT)) {

			return true;
		}

		return false;
	}

	private InputTemplateNode _toInputTemplateNode(
		FragmentEntryLink fragmentEntryLink) {

		String inputHelpText =
			(String)
				_fragmentEntryConfigurationParser.getConfigurationFieldValue(
					fragmentEntryLink.getEditableValues(), "inputHelpText",
					FragmentConfigurationFieldDataType.STRING);

		String inputLabel =
			(String)
				_fragmentEntryConfigurationParser.getConfigurationFieldValue(
					fragmentEntryLink.getEditableValues(), "inputLabel",
					FragmentConfigurationFieldDataType.STRING);

		boolean inputRequired =
			(boolean)
				_fragmentEntryConfigurationParser.getConfigurationFieldValue(
					fragmentEntryLink.getEditableValues(), "inputRequired",
					FragmentConfigurationFieldDataType.BOOLEAN);

		boolean inputShowHelpText =
			(boolean)
				_fragmentEntryConfigurationParser.getConfigurationFieldValue(
					fragmentEntryLink.getEditableValues(), "inputShowHelpText",
					FragmentConfigurationFieldDataType.BOOLEAN);

		boolean inputShowLabel =
			(boolean)
				_fragmentEntryConfigurationParser.getConfigurationFieldValue(
					fragmentEntryLink.getEditableValues(), "inputShowLabel",
					FragmentConfigurationFieldDataType.BOOLEAN);

		return new InputTemplateNode(
			inputHelpText, inputLabel, "name", inputRequired, inputShowHelpText,
			inputShowLabel, "type", "value");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FreeMarkerFragmentEntryProcessor.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference
	private Portal _portal;

}