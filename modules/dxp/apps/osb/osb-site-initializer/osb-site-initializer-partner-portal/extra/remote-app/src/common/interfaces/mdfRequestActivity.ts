/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import LiferayObject from './liferayObject';
import MDFRequestBudget from './mdfRequestBudget';

export default interface MDFRequestActivity extends Partial<LiferayObject> {
	budgets: MDFRequestBudget[];
	endDate: Date;
	leadFollowUpStrategies: string;
	leadGenerated: string;
	mdfRequestAmount: string;
	name: string;
	r_tacticToActivities_c_tacticId: string;
	r_typeActivityToActivities_c_typeActivityId: string;
	startDate: Date;
	targetofLeads: string;
	totalCostOfExpense: string;
}
