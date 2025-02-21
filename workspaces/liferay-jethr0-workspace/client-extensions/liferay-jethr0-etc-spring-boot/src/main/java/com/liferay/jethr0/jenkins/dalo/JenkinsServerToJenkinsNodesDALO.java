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

package com.liferay.jethr0.jenkins.dalo;

import com.liferay.jethr0.entity.dalo.BaseEntityRelationshipDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.jenkins.node.JenkinsNode;
import com.liferay.jethr0.jenkins.node.JenkinsNodeFactory;
import com.liferay.jethr0.jenkins.server.JenkinsServer;
import com.liferay.jethr0.jenkins.server.JenkinsServerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JenkinsServerToJenkinsNodesDALO
	extends BaseEntityRelationshipDALO<JenkinsServer, JenkinsNode> {

	@Override
	public EntityFactory<JenkinsNode> getChildEntityFactory() {
		return _jenkinsNodeFactory;
	}

	@Override
	public EntityFactory<JenkinsServer> getParentEntityFactory() {
		return _jenkinsServerFactory;
	}

	@Override
	protected String getObjectRelationshipName() {
		return "jenkinsServerToJenkinsNodes";
	}

	@Autowired
	private JenkinsNodeFactory _jenkinsNodeFactory;

	@Autowired
	private JenkinsServerFactory _jenkinsServerFactory;

}