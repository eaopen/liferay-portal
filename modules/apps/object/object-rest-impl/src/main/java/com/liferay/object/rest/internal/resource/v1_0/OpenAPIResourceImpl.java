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

package com.liferay.object.rest.internal.resource.v1_0;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.openapi.v1_0.ObjectEntryOpenAPIResource;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 */
@OpenAPIDefinition(info = @Info(title = "Object", version = "v1.0"))
public class OpenAPIResourceImpl {

	public OpenAPIResourceImpl(
		ObjectDefinitionLocalService objectDefinitionLocalService,
		String objectDefinitionName,
		ObjectEntryOpenAPIResource objectEntryOpenAPIResource) {

		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectDefinitionName = objectDefinitionName;
		_objectEntryOpenAPIResource = objectEntryOpenAPIResource;
	}

	@GET
	@Path("/openapi.{type:json|yaml}")
	@Produces({MediaType.APPLICATION_JSON, "application/yaml"})
	public Response getOpenAPI(@PathParam("type") String type)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				CompanyThreadLocal.getCompanyId(),
				"C_" + _objectDefinitionName);

		return _objectEntryOpenAPIResource.getOpenAPI(
			objectDefinition.getObjectDefinitionId(), type, _uriInfo);
	}

	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final String _objectDefinitionName;
	private final ObjectEntryOpenAPIResource _objectEntryOpenAPIResource;

	@Context
	private UriInfo _uriInfo;

}