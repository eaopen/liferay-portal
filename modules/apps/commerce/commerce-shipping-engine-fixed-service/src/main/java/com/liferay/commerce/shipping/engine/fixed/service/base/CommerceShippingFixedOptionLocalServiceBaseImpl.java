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

package com.liferay.commerce.shipping.engine.fixed.service.base;

import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionLocalService;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionLocalServiceUtil;
import com.liferay.commerce.shipping.engine.fixed.service.persistence.CommerceShippingFixedOptionPersistence;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the base implementation for the commerce shipping fixed option local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.commerce.shipping.engine.fixed.service.impl.CommerceShippingFixedOptionLocalServiceImpl}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.shipping.engine.fixed.service.impl.CommerceShippingFixedOptionLocalServiceImpl
 * @generated
 */
public abstract class CommerceShippingFixedOptionLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements AopService, CommerceShippingFixedOptionLocalService,
			   IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>CommerceShippingFixedOptionLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>CommerceShippingFixedOptionLocalServiceUtil</code>.
	 */

	/**
	 * Adds the commerce shipping fixed option to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceShippingFixedOptionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceShippingFixedOption the commerce shipping fixed option
	 * @return the commerce shipping fixed option that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceShippingFixedOption addCommerceShippingFixedOption(
		CommerceShippingFixedOption commerceShippingFixedOption) {

		commerceShippingFixedOption.setNew(true);

		return commerceShippingFixedOptionPersistence.update(
			commerceShippingFixedOption);
	}

	/**
	 * Creates a new commerce shipping fixed option with the primary key. Does not add the commerce shipping fixed option to the database.
	 *
	 * @param commerceShippingFixedOptionId the primary key for the new commerce shipping fixed option
	 * @return the new commerce shipping fixed option
	 */
	@Override
	@Transactional(enabled = false)
	public CommerceShippingFixedOption createCommerceShippingFixedOption(
		long commerceShippingFixedOptionId) {

		return commerceShippingFixedOptionPersistence.create(
			commerceShippingFixedOptionId);
	}

	/**
	 * Deletes the commerce shipping fixed option with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceShippingFixedOptionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceShippingFixedOptionId the primary key of the commerce shipping fixed option
	 * @return the commerce shipping fixed option that was removed
	 * @throws PortalException if a commerce shipping fixed option with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CommerceShippingFixedOption deleteCommerceShippingFixedOption(
			long commerceShippingFixedOptionId)
		throws PortalException {

		return commerceShippingFixedOptionPersistence.remove(
			commerceShippingFixedOptionId);
	}

	/**
	 * Deletes the commerce shipping fixed option from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceShippingFixedOptionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceShippingFixedOption the commerce shipping fixed option
	 * @return the commerce shipping fixed option that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CommerceShippingFixedOption deleteCommerceShippingFixedOption(
		CommerceShippingFixedOption commerceShippingFixedOption) {

		return commerceShippingFixedOptionPersistence.remove(
			commerceShippingFixedOption);
	}

	@Override
	public <T> T dslQuery(DSLQuery dslQuery) {
		return commerceShippingFixedOptionPersistence.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(DSLQuery dslQuery) {
		Long count = dslQuery(dslQuery);

		return count.intValue();
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(
			CommerceShippingFixedOption.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return commerceShippingFixedOptionPersistence.findWithDynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return commerceShippingFixedOptionPersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return commerceShippingFixedOptionPersistence.findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return commerceShippingFixedOptionPersistence.countWithDynamicQuery(
			dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection) {

		return commerceShippingFixedOptionPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public CommerceShippingFixedOption fetchCommerceShippingFixedOption(
		long commerceShippingFixedOptionId) {

		return commerceShippingFixedOptionPersistence.fetchByPrimaryKey(
			commerceShippingFixedOptionId);
	}

	/**
	 * Returns the commerce shipping fixed option with the primary key.
	 *
	 * @param commerceShippingFixedOptionId the primary key of the commerce shipping fixed option
	 * @return the commerce shipping fixed option
	 * @throws PortalException if a commerce shipping fixed option with the primary key could not be found
	 */
	@Override
	public CommerceShippingFixedOption getCommerceShippingFixedOption(
			long commerceShippingFixedOptionId)
		throws PortalException {

		return commerceShippingFixedOptionPersistence.findByPrimaryKey(
			commerceShippingFixedOptionId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(
			commerceShippingFixedOptionLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CommerceShippingFixedOption.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"commerceShippingFixedOptionId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			commerceShippingFixedOptionLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(
			CommerceShippingFixedOption.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"commerceShippingFixedOptionId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(
			commerceShippingFixedOptionLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CommerceShippingFixedOption.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"commerceShippingFixedOptionId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return commerceShippingFixedOptionPersistence.create(
			((Long)primaryKeyObj).longValue());
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		if (_log.isWarnEnabled()) {
			_log.warn(
				"Implement CommerceShippingFixedOptionLocalServiceImpl#deleteCommerceShippingFixedOption(CommerceShippingFixedOption) to avoid orphaned data");
		}

		return commerceShippingFixedOptionLocalService.
			deleteCommerceShippingFixedOption(
				(CommerceShippingFixedOption)persistedModel);
	}

	@Override
	public BasePersistence<CommerceShippingFixedOption> getBasePersistence() {
		return commerceShippingFixedOptionPersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return commerceShippingFixedOptionPersistence.findByPrimaryKey(
			primaryKeyObj);
	}

	/**
	 * Returns a range of all the commerce shipping fixed options.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping fixed options
	 * @param end the upper bound of the range of commerce shipping fixed options (not inclusive)
	 * @return the range of commerce shipping fixed options
	 */
	@Override
	public List<CommerceShippingFixedOption> getCommerceShippingFixedOptions(
		int start, int end) {

		return commerceShippingFixedOptionPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of commerce shipping fixed options.
	 *
	 * @return the number of commerce shipping fixed options
	 */
	@Override
	public int getCommerceShippingFixedOptionsCount() {
		return commerceShippingFixedOptionPersistence.countAll();
	}

	/**
	 * Updates the commerce shipping fixed option in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceShippingFixedOptionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceShippingFixedOption the commerce shipping fixed option
	 * @return the commerce shipping fixed option that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceShippingFixedOption updateCommerceShippingFixedOption(
		CommerceShippingFixedOption commerceShippingFixedOption) {

		return commerceShippingFixedOptionPersistence.update(
			commerceShippingFixedOption);
	}

	@Deactivate
	protected void deactivate() {
		CommerceShippingFixedOptionLocalServiceUtil.setService(null);
	}

	@Override
	public Class<?>[] getAopInterfaces() {
		return new Class<?>[] {
			CommerceShippingFixedOptionLocalService.class,
			IdentifiableOSGiService.class, PersistedModelLocalService.class
		};
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		commerceShippingFixedOptionLocalService =
			(CommerceShippingFixedOptionLocalService)aopProxy;

		CommerceShippingFixedOptionLocalServiceUtil.setService(
			commerceShippingFixedOptionLocalService);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return CommerceShippingFixedOptionLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return CommerceShippingFixedOption.class;
	}

	protected String getModelClassName() {
		return CommerceShippingFixedOption.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource =
				commerceShippingFixedOptionPersistence.getDataSource();

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

	protected CommerceShippingFixedOptionLocalService
		commerceShippingFixedOptionLocalService;

	@Reference
	protected CommerceShippingFixedOptionPersistence
		commerceShippingFixedOptionPersistence;

	@Reference
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceShippingFixedOptionLocalServiceBaseImpl.class);

}