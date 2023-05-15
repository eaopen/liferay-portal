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

package com.liferay.portal.service.base;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutBranch;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.service.BaseServiceImpl;
import com.liferay.portal.kernel.service.LayoutBranchService;
import com.liferay.portal.kernel.service.LayoutBranchServiceUtil;
import com.liferay.portal.kernel.service.persistence.LayoutBranchPersistence;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the layout branch remote service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portal.service.impl.LayoutBranchServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.impl.LayoutBranchServiceImpl
 * @generated
 */
public abstract class LayoutBranchServiceBaseImpl
	extends BaseServiceImpl
	implements IdentifiableOSGiService, LayoutBranchService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>LayoutBranchService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>LayoutBranchServiceUtil</code>.
	 */

	/**
	 * Returns the layout branch local service.
	 *
	 * @return the layout branch local service
	 */
	public com.liferay.portal.kernel.service.LayoutBranchLocalService
		getLayoutBranchLocalService() {

		return layoutBranchLocalService;
	}

	/**
	 * Sets the layout branch local service.
	 *
	 * @param layoutBranchLocalService the layout branch local service
	 */
	public void setLayoutBranchLocalService(
		com.liferay.portal.kernel.service.LayoutBranchLocalService
			layoutBranchLocalService) {

		this.layoutBranchLocalService = layoutBranchLocalService;
	}

	/**
	 * Returns the layout branch remote service.
	 *
	 * @return the layout branch remote service
	 */
	public LayoutBranchService getLayoutBranchService() {
		return layoutBranchService;
	}

	/**
	 * Sets the layout branch remote service.
	 *
	 * @param layoutBranchService the layout branch remote service
	 */
	public void setLayoutBranchService(
		LayoutBranchService layoutBranchService) {

		this.layoutBranchService = layoutBranchService;
	}

	/**
	 * Returns the layout branch persistence.
	 *
	 * @return the layout branch persistence
	 */
	public LayoutBranchPersistence getLayoutBranchPersistence() {
		return layoutBranchPersistence;
	}

	/**
	 * Sets the layout branch persistence.
	 *
	 * @param layoutBranchPersistence the layout branch persistence
	 */
	public void setLayoutBranchPersistence(
		LayoutBranchPersistence layoutBranchPersistence) {

		this.layoutBranchPersistence = layoutBranchPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService
		getCounterLocalService() {

		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService
			counterLocalService) {

		this.counterLocalService = counterLocalService;
	}

	public void afterPropertiesSet() {
		LayoutBranchServiceUtil.setService(layoutBranchService);
	}

	public void destroy() {
		LayoutBranchServiceUtil.setService(null);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return LayoutBranchService.class.getName();
	}

	protected Class<?> getModelClass() {
		return LayoutBranch.class;
	}

	protected String getModelClassName() {
		return LayoutBranch.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = layoutBranchPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
				dataSource, sql);

			sqlUpdate.update();
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	@BeanReference(
		type = com.liferay.portal.kernel.service.LayoutBranchLocalService.class
	)
	protected com.liferay.portal.kernel.service.LayoutBranchLocalService
		layoutBranchLocalService;

	@BeanReference(type = LayoutBranchService.class)
	protected LayoutBranchService layoutBranchService;

	@BeanReference(type = LayoutBranchPersistence.class)
	protected LayoutBranchPersistence layoutBranchPersistence;

	@BeanReference(
		type = com.liferay.counter.kernel.service.CounterLocalService.class
	)
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutBranchServiceBaseImpl.class);

}