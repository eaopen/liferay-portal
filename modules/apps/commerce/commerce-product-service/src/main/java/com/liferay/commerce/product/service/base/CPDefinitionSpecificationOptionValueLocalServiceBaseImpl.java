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

package com.liferay.commerce.product.service.base;

import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueLocalService;
import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueLocalServiceUtil;
import com.liferay.commerce.product.service.persistence.CPDefinitionSpecificationOptionValuePersistence;
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
 * Provides the base implementation for the cp definition specification option value local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.commerce.product.service.impl.CPDefinitionSpecificationOptionValueLocalServiceImpl}.
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.impl.CPDefinitionSpecificationOptionValueLocalServiceImpl
 * @generated
 */
public abstract class CPDefinitionSpecificationOptionValueLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements AopService, CPDefinitionSpecificationOptionValueLocalService,
			   IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>CPDefinitionSpecificationOptionValueLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>CPDefinitionSpecificationOptionValueLocalServiceUtil</code>.
	 */

	/**
	 * Adds the cp definition specification option value to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionSpecificationOptionValueLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDefinitionSpecificationOptionValue the cp definition specification option value
	 * @return the cp definition specification option value that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPDefinitionSpecificationOptionValue
		addCPDefinitionSpecificationOptionValue(
			CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue) {

		cpDefinitionSpecificationOptionValue.setNew(true);

		return cpDefinitionSpecificationOptionValuePersistence.update(
			cpDefinitionSpecificationOptionValue);
	}

	/**
	 * Creates a new cp definition specification option value with the primary key. Does not add the cp definition specification option value to the database.
	 *
	 * @param CPDefinitionSpecificationOptionValueId the primary key for the new cp definition specification option value
	 * @return the new cp definition specification option value
	 */
	@Override
	@Transactional(enabled = false)
	public CPDefinitionSpecificationOptionValue
		createCPDefinitionSpecificationOptionValue(
			long CPDefinitionSpecificationOptionValueId) {

		return cpDefinitionSpecificationOptionValuePersistence.create(
			CPDefinitionSpecificationOptionValueId);
	}

	/**
	 * Deletes the cp definition specification option value with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionSpecificationOptionValueLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param CPDefinitionSpecificationOptionValueId the primary key of the cp definition specification option value
	 * @return the cp definition specification option value that was removed
	 * @throws PortalException if a cp definition specification option value with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CPDefinitionSpecificationOptionValue
			deleteCPDefinitionSpecificationOptionValue(
				long CPDefinitionSpecificationOptionValueId)
		throws PortalException {

		return cpDefinitionSpecificationOptionValuePersistence.remove(
			CPDefinitionSpecificationOptionValueId);
	}

	/**
	 * Deletes the cp definition specification option value from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionSpecificationOptionValueLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDefinitionSpecificationOptionValue the cp definition specification option value
	 * @return the cp definition specification option value that was removed
	 * @throws PortalException
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CPDefinitionSpecificationOptionValue
			deleteCPDefinitionSpecificationOptionValue(
				CPDefinitionSpecificationOptionValue
					cpDefinitionSpecificationOptionValue)
		throws PortalException {

		return cpDefinitionSpecificationOptionValuePersistence.remove(
			cpDefinitionSpecificationOptionValue);
	}

	@Override
	public <T> T dslQuery(DSLQuery dslQuery) {
		return cpDefinitionSpecificationOptionValuePersistence.dslQuery(
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
			CPDefinitionSpecificationOptionValue.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return cpDefinitionSpecificationOptionValuePersistence.
			findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPDefinitionSpecificationOptionValueModelImpl</code>.
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

		return cpDefinitionSpecificationOptionValuePersistence.
			findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPDefinitionSpecificationOptionValueModelImpl</code>.
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

		return cpDefinitionSpecificationOptionValuePersistence.
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
		return cpDefinitionSpecificationOptionValuePersistence.
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

		return cpDefinitionSpecificationOptionValuePersistence.
			countWithDynamicQuery(dynamicQuery, projection);
	}

	@Override
	public CPDefinitionSpecificationOptionValue
		fetchCPDefinitionSpecificationOptionValue(
			long CPDefinitionSpecificationOptionValueId) {

		return cpDefinitionSpecificationOptionValuePersistence.
			fetchByPrimaryKey(CPDefinitionSpecificationOptionValueId);
	}

	/**
	 * Returns the cp definition specification option value matching the UUID and group.
	 *
	 * @param uuid the cp definition specification option value's UUID
	 * @param groupId the primary key of the group
	 * @return the matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue
		fetchCPDefinitionSpecificationOptionValueByUuidAndGroupId(
			String uuid, long groupId) {

		return cpDefinitionSpecificationOptionValuePersistence.fetchByUUID_G(
			uuid, groupId);
	}

	/**
	 * Returns the cp definition specification option value with the primary key.
	 *
	 * @param CPDefinitionSpecificationOptionValueId the primary key of the cp definition specification option value
	 * @return the cp definition specification option value
	 * @throws PortalException if a cp definition specification option value with the primary key could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue
			getCPDefinitionSpecificationOptionValue(
				long CPDefinitionSpecificationOptionValueId)
		throws PortalException {

		return cpDefinitionSpecificationOptionValuePersistence.findByPrimaryKey(
			CPDefinitionSpecificationOptionValueId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(
			cpDefinitionSpecificationOptionValueLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(
			CPDefinitionSpecificationOptionValue.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"CPDefinitionSpecificationOptionValueId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			cpDefinitionSpecificationOptionValueLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(
			CPDefinitionSpecificationOptionValue.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"CPDefinitionSpecificationOptionValueId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(
			cpDefinitionSpecificationOptionValueLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(
			CPDefinitionSpecificationOptionValue.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"CPDefinitionSpecificationOptionValueId");
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

		exportActionableDynamicQuery.setGroupId(
			portletDataContext.getScopeGroupId());

		exportActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CPDefinitionSpecificationOptionValue>() {

				@Override
				public void performAction(
						CPDefinitionSpecificationOptionValue
							cpDefinitionSpecificationOptionValue)
					throws PortalException {

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext,
						cpDefinitionSpecificationOptionValue);
				}

			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				PortalUtil.getClassNameId(
					CPDefinitionSpecificationOptionValue.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return cpDefinitionSpecificationOptionValuePersistence.create(
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
				"Implement CPDefinitionSpecificationOptionValueLocalServiceImpl#deleteCPDefinitionSpecificationOptionValue(CPDefinitionSpecificationOptionValue) to avoid orphaned data");
		}

		return cpDefinitionSpecificationOptionValueLocalService.
			deleteCPDefinitionSpecificationOptionValue(
				(CPDefinitionSpecificationOptionValue)persistedModel);
	}

	@Override
	public BasePersistence<CPDefinitionSpecificationOptionValue>
		getBasePersistence() {

		return cpDefinitionSpecificationOptionValuePersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return cpDefinitionSpecificationOptionValuePersistence.findByPrimaryKey(
			primaryKeyObj);
	}

	/**
	 * Returns all the cp definition specification option values matching the UUID and company.
	 *
	 * @param uuid the UUID of the cp definition specification option values
	 * @param companyId the primary key of the company
	 * @return the matching cp definition specification option values, or an empty list if no matches were found
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue>
		getCPDefinitionSpecificationOptionValuesByUuidAndCompanyId(
			String uuid, long companyId) {

		return cpDefinitionSpecificationOptionValuePersistence.findByUuid_C(
			uuid, companyId);
	}

	/**
	 * Returns a range of cp definition specification option values matching the UUID and company.
	 *
	 * @param uuid the UUID of the cp definition specification option values
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching cp definition specification option values, or an empty list if no matches were found
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue>
		getCPDefinitionSpecificationOptionValuesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator) {

		return cpDefinitionSpecificationOptionValuePersistence.findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the cp definition specification option value matching the UUID and group.
	 *
	 * @param uuid the cp definition specification option value's UUID
	 * @param groupId the primary key of the group
	 * @return the matching cp definition specification option value
	 * @throws PortalException if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue
			getCPDefinitionSpecificationOptionValueByUuidAndGroupId(
				String uuid, long groupId)
		throws PortalException {

		return cpDefinitionSpecificationOptionValuePersistence.findByUUID_G(
			uuid, groupId);
	}

	/**
	 * Returns a range of all the cp definition specification option values.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @return the range of cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue>
		getCPDefinitionSpecificationOptionValues(int start, int end) {

		return cpDefinitionSpecificationOptionValuePersistence.findAll(
			start, end);
	}

	/**
	 * Returns the number of cp definition specification option values.
	 *
	 * @return the number of cp definition specification option values
	 */
	@Override
	public int getCPDefinitionSpecificationOptionValuesCount() {
		return cpDefinitionSpecificationOptionValuePersistence.countAll();
	}

	/**
	 * Updates the cp definition specification option value in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionSpecificationOptionValueLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDefinitionSpecificationOptionValue the cp definition specification option value
	 * @return the cp definition specification option value that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPDefinitionSpecificationOptionValue
		updateCPDefinitionSpecificationOptionValue(
			CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue) {

		return cpDefinitionSpecificationOptionValuePersistence.update(
			cpDefinitionSpecificationOptionValue);
	}

	@Deactivate
	protected void deactivate() {
		CPDefinitionSpecificationOptionValueLocalServiceUtil.setService(null);
	}

	@Override
	public Class<?>[] getAopInterfaces() {
		return new Class<?>[] {
			CPDefinitionSpecificationOptionValueLocalService.class,
			IdentifiableOSGiService.class, CTService.class,
			PersistedModelLocalService.class
		};
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		cpDefinitionSpecificationOptionValueLocalService =
			(CPDefinitionSpecificationOptionValueLocalService)aopProxy;

		CPDefinitionSpecificationOptionValueLocalServiceUtil.setService(
			cpDefinitionSpecificationOptionValueLocalService);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return CPDefinitionSpecificationOptionValueLocalService.class.getName();
	}

	@Override
	public CTPersistence<CPDefinitionSpecificationOptionValue>
		getCTPersistence() {

		return cpDefinitionSpecificationOptionValuePersistence;
	}

	@Override
	public Class<CPDefinitionSpecificationOptionValue> getModelClass() {
		return CPDefinitionSpecificationOptionValue.class;
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction
				<CTPersistence<CPDefinitionSpecificationOptionValue>, R, E>
					updateUnsafeFunction)
		throws E {

		return updateUnsafeFunction.apply(
			cpDefinitionSpecificationOptionValuePersistence);
	}

	protected String getModelClassName() {
		return CPDefinitionSpecificationOptionValue.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource =
				cpDefinitionSpecificationOptionValuePersistence.getDataSource();

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

	protected CPDefinitionSpecificationOptionValueLocalService
		cpDefinitionSpecificationOptionValueLocalService;

	@Reference
	protected CPDefinitionSpecificationOptionValuePersistence
		cpDefinitionSpecificationOptionValuePersistence;

	@Reference
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionSpecificationOptionValueLocalServiceBaseImpl.class);

}