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

package com.liferay.ai.creator.openai.web.internal.display.context;

import com.liferay.ai.creator.openai.configuration.manager.AICreatorOpenAIConfigurationManager;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.theme.ThemeDisplay;

/**
 * @author Lourdes Fernández Besada
 */
public class AICreatorOpenAICompanyConfigurationDisplayContext {

	public AICreatorOpenAICompanyConfigurationDisplayContext(
		AICreatorOpenAIConfigurationManager aiCreatorOpenAIConfigurationManager,
		ThemeDisplay themeDisplay) {

		_aiCreatorOpenAIConfigurationManager =
			aiCreatorOpenAIConfigurationManager;
		_themeDisplay = themeDisplay;
	}

	public String getAPIKey() throws ConfigurationException {
		return _aiCreatorOpenAIConfigurationManager.
			getAICreatorOpenAICompanyAPIKey(_themeDisplay.getCompanyId());
	}

	public boolean isEnabled() throws ConfigurationException {
		return _aiCreatorOpenAIConfigurationManager.
			isAICreatorOpenAICompanyEnabled(_themeDisplay.getCompanyId());
	}

	private final AICreatorOpenAIConfigurationManager
		_aiCreatorOpenAIConfigurationManager;
	private final ThemeDisplay _themeDisplay;

}