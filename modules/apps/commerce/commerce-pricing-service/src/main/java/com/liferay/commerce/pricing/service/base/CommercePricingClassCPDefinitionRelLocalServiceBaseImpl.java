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

package com.liferay.commerce.pricing.service.base;

import com.liferay.commerce.pricing.model.CommercePricingClassCPDefinitionRel;
import com.liferay.commerce.pricing.service.CommercePricingClassCPDefinitionRelLocalService;
import com.liferay.commerce.pricing.service.CommercePricingClassCPDefinitionRelLocalServiceUtil;
import com.liferay.commerce.pricing.service.persistence.CommercePricingClassCPDefinitionRelFinder;
import com.liferay.commerce.pricing.service.persistence.CommercePricingClassCPDefinitionRelPersistence;
import com.liferay.petra.function.UnsafeFunction;
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
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the base implementation for the commerce pricing class cp definition rel local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.commerce.pricing.service.impl.CommercePricingClassCPDefinitionRelLocalServiceImpl}.
 * </p>
 *
 * @author Riccardo Alberti
 * @see com.liferay.commerce.pricing.service.impl.CommercePricingClassCPDefinitionRelLocalServiceImpl
 * @generated
 */
public abstract class CommercePricingClassCPDefinitionRelLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements AopService, CommercePricingClassCPDefinitionRelLocalService,
			   IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>CommercePricingClassCPDefinitionRelLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>CommercePricingClassCPDefinitionRelLocalServiceUtil</code>.
	 */

	/**
	 * Adds the commerce pricing class cp definition rel to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommercePricingClassCPDefinitionRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commercePricingClassCPDefinitionRel the commerce pricing class cp definition rel
	 * @return the commerce pricing class cp definition rel that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommercePricingClassCPDefinitionRel
		addCommercePricingClassCPDefinitionRel(
			CommercePricingClassCPDefinitionRel
				commercePricingClassCPDefinitionRel) {

		commercePricingClassCPDefinitionRel.setNew(true);

		return commercePricingClassCPDefinitionRelPersistence.update(
			commercePricingClassCPDefinitionRel);
	}

	/**
	 * Creates a new commerce pricing class cp definition rel with the primary key. Does not add the commerce pricing class cp definition rel to the database.
	 *
	 * @param CommercePricingClassCPDefinitionRelId the primary key for the new commerce pricing class cp definition rel
	 * @return the new commerce pricing class cp definition rel
	 */
	@Override
	@Transactional(enabled = false)
	public CommercePricingClassCPDefinitionRel
		createCommercePricingClassCPDefinitionRel(
			long CommercePricingClassCPDefinitionRelId) {

		return commercePricingClassCPDefinitionRelPersistence.create(
			CommercePricingClassCPDefinitionRelId);
	}

	/**
	 * Deletes the commerce pricing class cp definition rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommercePricingClassCPDefinitionRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param CommercePricingClassCPDefinitionRelId the primary key of the commerce pricing class cp definition rel
	 * @return the commerce pricing class cp definition rel that was removed
	 * @throws PortalException if a commerce pricing class cp definition rel with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CommercePricingClassCPDefinitionRel
			deleteCommercePricingClassCPDefinitionRel(
				long CommercePricingClassCPDefinitionRelId)
		throws PortalException {

		return commercePricingClassCPDefinitionRelPersistence.remove(
			CommercePricingClassCPDefinitionRelId);
	}

	/**
	 * Deletes the commerce pricing class cp definition rel from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommercePricingClassCPDefinitionRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commercePricingClassCPDefinitionRel the commerce pricing class cp definition rel
	 * @return the commerce pricing class cp definition rel that was removed
	 * @throws PortalException
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CommercePricingClassCPDefinitionRel
			deleteCommercePricingClassCPDefinitionRel(
				CommercePricingClassCPDefinitionRel
					commercePricingClassCPDefinitionRel)
		throws PortalException {

		return commercePricingClassCPDefinitionRelPersistence.remove(
			commercePricingClassCPDefinitionRel);
	}

	@Override
	public <T> T dslQuery(DSLQuery dslQuery) {
		return commercePricingClassCPDefinitionRelPersistence.dslQuery(
			dslQuery);
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
			CommercePricingClassCPDefinitionRel.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return commercePricingClassCPDefinitionRelPersistence.
			findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.pricing.model.impl.CommercePricingClassCPDefinitionRelModelImpl</code>.
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

		return commercePricingClassCPDefinitionRelPersistence.
			findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.pricing.model.impl.CommercePricingClassCPDefinitionRelModelImpl</code>.
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

		return commercePricingClassCPDefinitionRelPersistence.
			findWithDynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return commercePricingClassCPDefinitionRelPersistence.
			countWithDynamicQuery(dynamicQuery);
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

		return commercePricingClassCPDefinitionRelPersistence.
			countWithDynamicQuery(dynamicQuery, projection);
	}

	@Override
	public CommercePricingClassCPDefinitionRel
		fetchCommercePricingClassCPDefinitionRel(
			long CommercePricingClassCPDefinitionRelId) {

		return commercePricingClassCPDefinitionRelPersistence.fetchByPrimaryKey(
			CommercePricingClassCPDefinitionRelId);
	}

	/**
	 * Returns the commerce pricing class cp definition rel with the primary key.
	 *
	 * @param CommercePricingClassCPDefinitionRelId the primary key of the commerce pricing class cp definition rel
	 * @return the commerce pricing class cp definition rel
	 * @throws PortalException if a commerce pricing class cp definition rel with the primary key could not be found
	 */
	@Override
	public CommercePricingClassCPDefinitionRel
			getCommercePricingClassCPDefinitionRel(
				long CommercePricingClassCPDefinitionRelId)
		throws PortalException {

		return commercePricingClassCPDefinitionRelPersistence.findByPrimaryKey(
			CommercePricingClassCPDefinitionRelId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(
			commercePricingClassCPDefinitionRelLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(
			CommercePricingClassCPDefinitionRel.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"CommercePricingClassCPDefinitionRelId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			commercePricingClassCPDefinitionRelLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(
			CommercePricingClassCPDefinitionRel.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"CommercePricingClassCPDefinitionRelId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(
			commercePricingClassCPDefinitionRelLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(
			CommercePricingClassCPDefinitionRel.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"CommercePricingClassCPDefinitionRelId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return commercePricingClassCPDefinitionRelPersistence.create(
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
				"Implement CommercePricingClassCPDefinitionRelLocalServiceImpl#deleteCommercePricingClassCPDefinitionRel(CommercePricingClassCPDefinitionRel) to avoid orphaned data");
		}

		return commercePricingClassCPDefinitionRelLocalService.
			deleteCommercePricingClassCPDefinitionRel(
				(CommercePricingClassCPDefinitionRel)persistedModel);
	}

	@Override
	public BasePersistence<CommercePricingClassCPDefinitionRel>
		getBasePersistence() {

		return commercePricingClassCPDefinitionRelPersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return commercePricingClassCPDefinitionRelPersistence.findByPrimaryKey(
			primaryKeyObj);
	}

	/**
	 * Returns a range of all the commerce pricing class cp definition rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.pricing.model.impl.CommercePricingClassCPDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce pricing class cp definition rels
	 * @param end the upper bound of the range of commerce pricing class cp definition rels (not inclusive)
	 * @return the range of commerce pricing class cp definition rels
	 */
	@Override
	public List<CommercePricingClassCPDefinitionRel>
		getCommercePricingClassCPDefinitionRels(int start, int end) {

		return commercePricingClassCPDefinitionRelPersistence.findAll(
			start, end);
	}

	/**
	 * Returns the number of commerce pricing class cp definition rels.
	 *
	 * @return the number of commerce pricing class cp definition rels
	 */
	@Override
	public int getCommercePricingClassCPDefinitionRelsCount() {
		return commercePricingClassCPDefinitionRelPersistence.countAll();
	}

	/**
	 * Updates the commerce pricing class cp definition rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommercePricingClassCPDefinitionRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commercePricingClassCPDefinitionRel the commerce pricing class cp definition rel
	 * @return the commerce pricing class cp definition rel that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommercePricingClassCPDefinitionRel
		updateCommercePricingClassCPDefinitionRel(
			CommercePricingClassCPDefinitionRel
				commercePricingClassCPDefinitionRel) {

		return commercePricingClassCPDefinitionRelPersistence.update(
			commercePricingClassCPDefinitionRel);
	}

	@Deactivate
	protected void deactivate() {
		CommercePricingClassCPDefinitionRelLocalServiceUtil.setService(null);
	}

	@Override
	public Class<?>[] getAopInterfaces() {
		return new Class<?>[] {
			CommercePricingClassCPDefinitionRelLocalService.class,
			IdentifiableOSGiService.class, CTService.class,
			PersistedModelLocalService.class
		};
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		commercePricingClassCPDefinitionRelLocalService =
			(CommercePricingClassCPDefinitionRelLocalService)aopProxy;

		CommercePricingClassCPDefinitionRelLocalServiceUtil.setService(
			commercePricingClassCPDefinitionRelLocalService);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return CommercePricingClassCPDefinitionRelLocalService.class.getName();
	}

	@Override
	public CTPersistence<CommercePricingClassCPDefinitionRel>
		getCTPersistence() {

		return commercePricingClassCPDefinitionRelPersistence;
	}

	@Override
	public Class<CommercePricingClassCPDefinitionRel> getModelClass() {
		return CommercePricingClassCPDefinitionRel.class;
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction
				<CTPersistence<CommercePricingClassCPDefinitionRel>, R, E>
					updateUnsafeFunction)
		throws E {

		return updateUnsafeFunction.apply(
			commercePricingClassCPDefinitionRelPersistence);
	}

	protected String getModelClassName() {
		return CommercePricingClassCPDefinitionRel.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource =
				commercePricingClassCPDefinitionRelPersistence.getDataSource();

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

	protected CommercePricingClassCPDefinitionRelLocalService
		commercePricingClassCPDefinitionRelLocalService;

	@Reference
	protected CommercePricingClassCPDefinitionRelPersistence
		commercePricingClassCPDefinitionRelPersistence;

	@Reference
	protected CommercePricingClassCPDefinitionRelFinder
		commercePricingClassCPDefinitionRelFinder;

	@Reference
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePricingClassCPDefinitionRelLocalServiceBaseImpl.class);

}