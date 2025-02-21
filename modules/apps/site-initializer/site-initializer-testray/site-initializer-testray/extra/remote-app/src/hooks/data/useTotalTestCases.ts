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

import {Statuses, chartColors} from '../../util/constants';
import {getRandomMaximumValue} from '../../util/mock';

const useTotalTestCases = () => {
	const donutColumns = [
		[Statuses.PASSED, 30529],
		[Statuses.FAILED, 5374],
		[Statuses.BLOCKED, 0],
		[Statuses.TEST_FIX, 0],
		[Statuses.INCOMPLETE, 21],
	];

	return {
		barChart: {
			columns: [
				[Statuses.PASSED, ...getRandomMaximumValue(20, 1000)],
				[Statuses.FAILED, ...getRandomMaximumValue(20, 500)],
				[Statuses.BLOCKED, ...getRandomMaximumValue(20, 100)],
				[Statuses.TEST_FIX, ...getRandomMaximumValue(20, 100)],
				[Statuses.INCOMPLETE, ...getRandomMaximumValue(20, 100)],
			],
		},
		colors: chartColors,
		donut: {
			columns: donutColumns,
			total: donutColumns
				.map(([, totalCase]) => totalCase)
				.reduce(
					(prevValue, currentValue) =>
						Number(prevValue) + Number(currentValue)
				),
		},
		statuses: Object.values(Statuses),
	};
};

export default useTotalTestCases;
