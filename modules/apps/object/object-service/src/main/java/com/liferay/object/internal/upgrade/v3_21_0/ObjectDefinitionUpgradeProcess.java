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

package com.liferay.object.internal.upgrade.v3_21_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Selton Guedes
 */
public class ObjectDefinitionUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alterTableAddColumn(
			"ObjectDefinition", "enableCategorization", "BOOLEAN");
		alterTableAddColumn("ObjectDefinition", "enableComments", "BOOLEAN");

		runSQL(
			StringBundler.concat(
				"update ObjectDefinition set enableCategorization = [$TRUE$], ",
				"enableComments = [$FALSE$] where system_ = [$FALSE$] and ",
				"storageType = 'default'"));

		runSQL(
			StringBundler.concat(
				"update ObjectDefinition set enableCategorization = ",
				"[$FALSE$], enableComments = [$FALSE$] where storageType <> ",
				"'default' or system_ = [$TRUE$]"));
	}

}