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

package com.liferay.commerce.service.base;

import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.commerce.service.CPDefinitionInventoryLocalServiceUtil;
import com.liferay.commerce.service.persistence.CPDefinitionInventoryPersistence;
import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
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
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
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
 * Provides the base implementation for the cp definition inventory local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.commerce.service.impl.CPDefinitionInventoryLocalServiceImpl}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.impl.CPDefinitionInventoryLocalServiceImpl
 * @generated
 */
public abstract class CPDefinitionInventoryLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements AopService, CPDefinitionInventoryLocalService,
			   IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>CPDefinitionInventoryLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>CPDefinitionInventoryLocalServiceUtil</code>.
	 */

	/**
	 * Adds the cp definition inventory to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionInventoryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDefinitionInventory the cp definition inventory
	 * @return the cp definition inventory that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPDefinitionInventory addCPDefinitionInventory(
		CPDefinitionInventory cpDefinitionInventory) {

		cpDefinitionInventory.setNew(true);

		return cpDefinitionInventoryPersistence.update(cpDefinitionInventory);
	}

	/**
	 * Creates a new cp definition inventory with the primary key. Does not add the cp definition inventory to the database.
	 *
	 * @param CPDefinitionInventoryId the primary key for the new cp definition inventory
	 * @return the new cp definition inventory
	 */
	@Override
	@Transactional(enabled = false)
	public CPDefinitionInventory createCPDefinitionInventory(
		long CPDefinitionInventoryId) {

		return cpDefinitionInventoryPersistence.create(CPDefinitionInventoryId);
	}

	/**
	 * Deletes the cp definition inventory with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionInventoryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param CPDefinitionInventoryId the primary key of the cp definition inventory
	 * @return the cp definition inventory that was removed
	 * @throws PortalException if a cp definition inventory with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CPDefinitionInventory deleteCPDefinitionInventory(
			long CPDefinitionInventoryId)
		throws PortalException {

		return cpDefinitionInventoryPersistence.remove(CPDefinitionInventoryId);
	}

	/**
	 * Deletes the cp definition inventory from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionInventoryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDefinitionInventory the cp definition inventory
	 * @return the cp definition inventory that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CPDefinitionInventory deleteCPDefinitionInventory(
		CPDefinitionInventory cpDefinitionInventory) {

		return cpDefinitionInventoryPersistence.remove(cpDefinitionInventory);
	}

	@Override
	public <T> T dslQuery(DSLQuery dslQuery) {
		return cpDefinitionInventoryPersistence.dslQuery(dslQuery);
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
			CPDefinitionInventory.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return cpDefinitionInventoryPersistence.findWithDynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CPDefinitionInventoryModelImpl</code>.
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

		return cpDefinitionInventoryPersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CPDefinitionInventoryModelImpl</code>.
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

		return cpDefinitionInventoryPersistence.findWithDynamicQuery(
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
		return cpDefinitionInventoryPersistence.countWithDynamicQuery(
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

		return cpDefinitionInventoryPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public CPDefinitionInventory fetchCPDefinitionInventory(
		long CPDefinitionInventoryId) {

		return cpDefinitionInventoryPersistence.fetchByPrimaryKey(
			CPDefinitionInventoryId);
	}

	/**
	 * Returns the cp definition inventory matching the UUID and group.
	 *
	 * @param uuid the cp definition inventory's UUID
	 * @param groupId the primary key of the group
	 * @return the matching cp definition inventory, or <code>null</code> if a matching cp definition inventory could not be found
	 */
	@Override
	public CPDefinitionInventory fetchCPDefinitionInventoryByUuidAndGroupId(
		String uuid, long groupId) {

		return cpDefinitionInventoryPersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the cp definition inventory with the primary key.
	 *
	 * @param CPDefinitionInventoryId the primary key of the cp definition inventory
	 * @return the cp definition inventory
	 * @throws PortalException if a cp definition inventory with the primary key could not be found
	 */
	@Override
	public CPDefinitionInventory getCPDefinitionInventory(
			long CPDefinitionInventoryId)
		throws PortalException {

		return cpDefinitionInventoryPersistence.findByPrimaryKey(
			CPDefinitionInventoryId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(
			cpDefinitionInventoryLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CPDefinitionInventory.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"CPDefinitionInventoryId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			cpDefinitionInventoryLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(
			CPDefinitionInventory.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"CPDefinitionInventoryId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(
			cpDefinitionInventoryLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CPDefinitionInventory.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"CPDefinitionInventoryId");
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		final ExportActionableDynamicQuery exportActionableDynamicQuery =
			new ExportActionableDynamicQuery() {

				@Override
				public long performCount() throws PortalException {
					ManifestSummary manifestSummary =
						portletDataContext.getManifestSummary();

					StagedModelType stagedModelType = getStagedModelType();

					long modelAdditionCount = super.performCount();

					manifestSummary.addModelAdditionCount(
						stagedModelType, modelAdditionCount);

					long modelDeletionCount =
						ExportImportHelperUtil.getModelDeletionCount(
							portletDataContext, stagedModelType);

					manifestSummary.addModelDeletionCount(
						stagedModelType, modelDeletionCount);

					return modelAdditionCount;
				}

			};

		initActionableDynamicQuery(exportActionableDynamicQuery);

		exportActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					portletDataContext.addDateRangeCriteria(
						dynamicQuery, "modifiedDate");
				}

			});

		exportActionableDynamicQuery.setCompanyId(
			portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CPDefinitionInventory>() {

				@Override
				public void performAction(
						CPDefinitionInventory cpDefinitionInventory)
					throws PortalException {

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, cpDefinitionInventory);
				}

			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				PortalUtil.getClassNameId(
					CPDefinitionInventory.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return cpDefinitionInventoryPersistence.create(
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
				"Implement CPDefinitionInventoryLocalServiceImpl#deleteCPDefinitionInventory(CPDefinitionInventory) to avoid orphaned data");
		}

		return cpDefinitionInventoryLocalService.deleteCPDefinitionInventory(
			(CPDefinitionInventory)persistedModel);
	}

	@Override
	public BasePersistence<CPDefinitionInventory> getBasePersistence() {
		return cpDefinitionInventoryPersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return cpDefinitionInventoryPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns all the cp definition inventories matching the UUID and company.
	 *
	 * @param uuid the UUID of the cp definition inventories
	 * @param companyId the primary key of the company
	 * @return the matching cp definition inventories, or an empty list if no matches were found
	 */
	@Override
	public List<CPDefinitionInventory>
		getCPDefinitionInventoriesByUuidAndCompanyId(
			String uuid, long companyId) {

		return cpDefinitionInventoryPersistence.findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of cp definition inventories matching the UUID and company.
	 *
	 * @param uuid the UUID of the cp definition inventories
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of cp definition inventories
	 * @param end the upper bound of the range of cp definition inventories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching cp definition inventories, or an empty list if no matches were found
	 */
	@Override
	public List<CPDefinitionInventory>
		getCPDefinitionInventoriesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			OrderByComparator<CPDefinitionInventory> orderByComparator) {

		return cpDefinitionInventoryPersistence.findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the cp definition inventory matching the UUID and group.
	 *
	 * @param uuid the cp definition inventory's UUID
	 * @param groupId the primary key of the group
	 * @return the matching cp definition inventory
	 * @throws PortalException if a matching cp definition inventory could not be found
	 */
	@Override
	public CPDefinitionInventory getCPDefinitionInventoryByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return cpDefinitionInventoryPersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the cp definition inventories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CPDefinitionInventoryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition inventories
	 * @param end the upper bound of the range of cp definition inventories (not inclusive)
	 * @return the range of cp definition inventories
	 */
	@Override
	public List<CPDefinitionInventory> getCPDefinitionInventories(
		int start, int end) {

		return cpDefinitionInventoryPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of cp definition inventories.
	 *
	 * @return the number of cp definition inventories
	 */
	@Override
	public int getCPDefinitionInventoriesCount() {
		return cpDefinitionInventoryPersistence.countAll();
	}

	/**
	 * Updates the cp definition inventory in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionInventoryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDefinitionInventory the cp definition inventory
	 * @return the cp definition inventory that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPDefinitionInventory updateCPDefinitionInventory(
		CPDefinitionInventory cpDefinitionInventory) {

		return cpDefinitionInventoryPersistence.update(cpDefinitionInventory);
	}

	@Deactivate
	protected void deactivate() {
		CPDefinitionInventoryLocalServiceUtil.setService(null);
	}

	@Override
	public Class<?>[] getAopInterfaces() {
		return new Class<?>[] {
			CPDefinitionInventoryLocalService.class,
			IdentifiableOSGiService.class, CTService.class,
			PersistedModelLocalService.class
		};
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		cpDefinitionInventoryLocalService =
			(CPDefinitionInventoryLocalService)aopProxy;

		CPDefinitionInventoryLocalServiceUtil.setService(
			cpDefinitionInventoryLocalService);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return CPDefinitionInventoryLocalService.class.getName();
	}

	@Override
	public CTPersistence<CPDefinitionInventory> getCTPersistence() {
		return cpDefinitionInventoryPersistence;
	}

	@Override
	public Class<CPDefinitionInventory> getModelClass() {
		return CPDefinitionInventory.class;
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<CPDefinitionInventory>, R, E>
				updateUnsafeFunction)
		throws E {

		return updateUnsafeFunction.apply(cpDefinitionInventoryPersistence);
	}

	protected String getModelClassName() {
		return CPDefinitionInventory.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource =
				cpDefinitionInventoryPersistence.getDataSource();

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

	protected CPDefinitionInventoryLocalService
		cpDefinitionInventoryLocalService;

	@Reference
	protected CPDefinitionInventoryPersistence cpDefinitionInventoryPersistence;

	@Reference
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionInventoryLocalServiceBaseImpl.class);

}