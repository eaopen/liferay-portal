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

package com.liferay.commerce.subscription.web.internal.frontend.data.set.view.table;

import com.liferay.commerce.subscription.web.internal.constants.CommerceSubscriptionFDSNames;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.table.BaseTableFDSView;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilderFactory;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "frontend.data.set.name=" + CommerceSubscriptionFDSNames.SUBSCRIPTION_ENTRIES,
	service = FDSView.class
)
public class CommerceSubscriptionEntryTableFDSView extends BaseTableFDSView {

	@Override
	public FDSTableSchema getFDSTableSchema(Locale locale) {
		FDSTableSchemaBuilder fdsTableSchemaBuilder =
			_fdsTableSchemaBuilderFactory.create();

		return fdsTableSchemaBuilder.add(
			"subscriptionId", "id",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"actionLink")
		).add(
			"subscriptionStatus", "status",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"label")
		).add(
			"orderId", "order-id",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"link")
		).add(
			"accountEntryId", "account-id",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"link")
		).add(
			"accountEntryName", "account-name"
		).build();
	}

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

}