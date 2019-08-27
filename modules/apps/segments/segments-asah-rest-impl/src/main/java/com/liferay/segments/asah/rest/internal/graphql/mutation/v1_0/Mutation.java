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

package com.liferay.segments.asah.rest.internal.graphql.mutation.v1_0;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.segments.asah.rest.dto.v1_0.Experiment;
import com.liferay.segments.asah.rest.dto.v1_0.Status;
import com.liferay.segments.asah.rest.resource.v1_0.ExperimentResource;
import com.liferay.segments.asah.rest.resource.v1_0.StatusResource;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setExperimentResourceComponentServiceObjects(
		ComponentServiceObjects<ExperimentResource>
			experimentResourceComponentServiceObjects) {

		_experimentResourceComponentServiceObjects =
			experimentResourceComponentServiceObjects;
	}

	public static void setStatusResourceComponentServiceObjects(
		ComponentServiceObjects<StatusResource>
			statusResourceComponentServiceObjects) {

		_statusResourceComponentServiceObjects =
			statusResourceComponentServiceObjects;
	}

	@GraphQLField
	public boolean deleteExperiment(
			@GraphQLName("experimentId") String experimentId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_experimentResourceComponentServiceObjects,
			this::_populateResourceContext,
			experimentResource -> experimentResource.deleteExperiment(
				experimentId));

		return true;
	}

	@GraphQLField
	public Experiment createExperimentStatus(
			@GraphQLName("experimentId") Long experimentId,
			@GraphQLName("status") Status status)
		throws Exception {

		return _applyComponentServiceObjects(
			_statusResourceComponentServiceObjects,
			this::_populateResourceContext,
			statusResource -> statusResource.postExperimentStatus(
				experimentId, status));
	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private <T, E1 extends Throwable, E2 extends Throwable> void
			_applyVoidComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeConsumer<T, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			unsafeFunction.accept(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(ExperimentResource experimentResource)
		throws Exception {

		experimentResource.setContextAcceptLanguage(_acceptLanguage);
		experimentResource.setContextCompany(_company);
		experimentResource.setContextHttpServletRequest(_httpServletRequest);
		experimentResource.setContextHttpServletResponse(_httpServletResponse);
		experimentResource.setContextUriInfo(_uriInfo);
		experimentResource.setContextUser(_user);
	}

	private void _populateResourceContext(StatusResource statusResource)
		throws Exception {

		statusResource.setContextAcceptLanguage(_acceptLanguage);
		statusResource.setContextCompany(_company);
		statusResource.setContextHttpServletRequest(_httpServletRequest);
		statusResource.setContextHttpServletResponse(_httpServletResponse);
		statusResource.setContextUriInfo(_uriInfo);
		statusResource.setContextUser(_user);
	}

	private static ComponentServiceObjects<ExperimentResource>
		_experimentResourceComponentServiceObjects;
	private static ComponentServiceObjects<StatusResource>
		_statusResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private Company _company;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private UriInfo _uriInfo;
	private User _user;

}