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

package com.liferay.analytics.dxp.entities.exporter.internal.retriever;

import com.liferay.analytics.dxp.entities.exporter.dto.v1_0.DXPEntity;
import com.liferay.analytics.dxp.entities.exporter.retriever.DXPEntityRetriever;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.service.TeamLocalService;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(
	immediate = true,
	property = "analytics.dxp.entity.retriever.class.name=com.liferay.portal.kernel.model.Team",
	service = DXPEntityRetriever.class
)
public class TeamRetriever implements DXPEntityRetriever {

	@Override
	public Page<DXPEntity> getDXPEntitiesPage(
			long companyId, Pagination pagination,
			UnsafeFunction<BaseModel<?>, DXPEntity, Exception>
				transformUnsafeFunction)
		throws Exception {

		List<DXPEntity> dxpEntities = new ArrayList<>();

		List<Team> teams = _teamLocalService.getTeams(
			pagination.getStartPosition(), pagination.getEndPosition());

		for (Team team : teams) {
			dxpEntities.add(transformUnsafeFunction.apply(team));
		}

		return Page.of(
			dxpEntities, pagination, _teamLocalService.getTeamsCount());
	}

	@Reference
	private TeamLocalService _teamLocalService;

}