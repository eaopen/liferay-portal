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

import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalServiceUtil;
import com.liferay.commerce.product.service.persistence.CPAttachmentFileEntryFinder;
import com.liferay.commerce.product.service.persistence.CPAttachmentFileEntryPersistence;
import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
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
import com.liferay.portal.kernel.dao.orm.Conjunction;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
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
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the base implementation for the cp attachment file entry local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.commerce.product.service.impl.CPAttachmentFileEntryLocalServiceImpl}.
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.impl.CPAttachmentFileEntryLocalServiceImpl
 * @generated
 */
public abstract class CPAttachmentFileEntryLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements AopService, CPAttachmentFileEntryLocalService,
			   IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>CPAttachmentFileEntryLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>CPAttachmentFileEntryLocalServiceUtil</code>.
	 */

	/**
	 * Adds the cp attachment file entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPAttachmentFileEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpAttachmentFileEntry the cp attachment file entry
	 * @return the cp attachment file entry that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPAttachmentFileEntry addCPAttachmentFileEntry(
		CPAttachmentFileEntry cpAttachmentFileEntry) {

		cpAttachmentFileEntry.setNew(true);

		return cpAttachmentFileEntryPersistence.update(cpAttachmentFileEntry);
	}

	/**
	 * Creates a new cp attachment file entry with the primary key. Does not add the cp attachment file entry to the database.
	 *
	 * @param CPAttachmentFileEntryId the primary key for the new cp attachment file entry
	 * @return the new cp attachment file entry
	 */
	@Override
	@Transactional(enabled = false)
	public CPAttachmentFileEntry createCPAttachmentFileEntry(
		long CPAttachmentFileEntryId) {

		return cpAttachmentFileEntryPersistence.create(CPAttachmentFileEntryId);
	}

	/**
	 * Deletes the cp attachment file entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPAttachmentFileEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param CPAttachmentFileEntryId the primary key of the cp attachment file entry
	 * @return the cp attachment file entry that was removed
	 * @throws PortalException if a cp attachment file entry with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CPAttachmentFileEntry deleteCPAttachmentFileEntry(
			long CPAttachmentFileEntryId)
		throws PortalException {

		return cpAttachmentFileEntryPersistence.remove(CPAttachmentFileEntryId);
	}

	/**
	 * Deletes the cp attachment file entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPAttachmentFileEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpAttachmentFileEntry the cp attachment file entry
	 * @return the cp attachment file entry that was removed
	 * @throws PortalException
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CPAttachmentFileEntry deleteCPAttachmentFileEntry(
			CPAttachmentFileEntry cpAttachmentFileEntry)
		throws PortalException {

		return cpAttachmentFileEntryPersistence.remove(cpAttachmentFileEntry);
	}

	@Override
	public <T> T dslQuery(DSLQuery dslQuery) {
		return cpAttachmentFileEntryPersistence.dslQuery(dslQuery);
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
			CPAttachmentFileEntry.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return cpAttachmentFileEntryPersistence.findWithDynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPAttachmentFileEntryModelImpl</code>.
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

		return cpAttachmentFileEntryPersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPAttachmentFileEntryModelImpl</code>.
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

		return cpAttachmentFileEntryPersistence.findWithDynamicQuery(
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
		return cpAttachmentFileEntryPersistence.countWithDynamicQuery(
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

		return cpAttachmentFileEntryPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public CPAttachmentFileEntry fetchCPAttachmentFileEntry(
		long CPAttachmentFileEntryId) {

		return cpAttachmentFileEntryPersistence.fetchByPrimaryKey(
			CPAttachmentFileEntryId);
	}

	/**
	 * Returns the cp attachment file entry matching the UUID and group.
	 *
	 * @param uuid the cp attachment file entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchCPAttachmentFileEntryByUuidAndGroupId(
		String uuid, long groupId) {

		return cpAttachmentFileEntryPersistence.fetchByUUID_G(uuid, groupId);
	}

	@Override
	public CPAttachmentFileEntry
		fetchCPAttachmentFileEntryByExternalReferenceCode(
			String externalReferenceCode, long companyId) {

		return cpAttachmentFileEntryPersistence.fetchByERC_C(
			externalReferenceCode, companyId);
	}

	@Override
	public CPAttachmentFileEntry
			getCPAttachmentFileEntryByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws PortalException {

		return cpAttachmentFileEntryPersistence.findByERC_C(
			externalReferenceCode, companyId);
	}

	/**
	 * Returns the cp attachment file entry with the primary key.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the cp attachment file entry
	 * @return the cp attachment file entry
	 * @throws PortalException if a cp attachment file entry with the primary key could not be found
	 */
	@Override
	public CPAttachmentFileEntry getCPAttachmentFileEntry(
			long CPAttachmentFileEntryId)
		throws PortalException {

		return cpAttachmentFileEntryPersistence.findByPrimaryKey(
			CPAttachmentFileEntryId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(
			cpAttachmentFileEntryLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CPAttachmentFileEntry.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"CPAttachmentFileEntryId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			cpAttachmentFileEntryLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(
			CPAttachmentFileEntry.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"CPAttachmentFileEntryId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(
			cpAttachmentFileEntryLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CPAttachmentFileEntry.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"CPAttachmentFileEntryId");
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
					Criterion modifiedDateCriterion =
						portletDataContext.getDateRangeCriteria("modifiedDate");

					if (modifiedDateCriterion != null) {
						Conjunction conjunction =
							RestrictionsFactoryUtil.conjunction();

						conjunction.add(modifiedDateCriterion);

						Disjunction disjunction =
							RestrictionsFactoryUtil.disjunction();

						disjunction.add(
							RestrictionsFactoryUtil.gtProperty(
								"modifiedDate", "lastPublishDate"));

						Property lastPublishDateProperty =
							PropertyFactoryUtil.forName("lastPublishDate");

						disjunction.add(lastPublishDateProperty.isNull());

						conjunction.add(disjunction);

						modifiedDateCriterion = conjunction;
					}

					Criterion statusDateCriterion =
						portletDataContext.getDateRangeCriteria("statusDate");

					if ((modifiedDateCriterion != null) &&
						(statusDateCriterion != null)) {

						Disjunction disjunction =
							RestrictionsFactoryUtil.disjunction();

						disjunction.add(modifiedDateCriterion);
						disjunction.add(statusDateCriterion);

						dynamicQuery.add(disjunction);
					}

					StagedModelType stagedModelType =
						exportActionableDynamicQuery.getStagedModelType();

					long referrerClassNameId =
						stagedModelType.getReferrerClassNameId();

					Property classNameIdProperty = PropertyFactoryUtil.forName(
						"classNameId");

					if ((referrerClassNameId !=
							StagedModelType.REFERRER_CLASS_NAME_ID_ALL) &&
						(referrerClassNameId !=
							StagedModelType.REFERRER_CLASS_NAME_ID_ANY)) {

						dynamicQuery.add(
							classNameIdProperty.eq(
								stagedModelType.getReferrerClassNameId()));
					}
					else if (referrerClassNameId ==
								StagedModelType.REFERRER_CLASS_NAME_ID_ANY) {

						dynamicQuery.add(classNameIdProperty.isNotNull());
					}

					Property workflowStatusProperty =
						PropertyFactoryUtil.forName("status");

					if (portletDataContext.isInitialPublication()) {
						dynamicQuery.add(
							workflowStatusProperty.ne(
								WorkflowConstants.STATUS_IN_TRASH));
					}
					else {
						StagedModelDataHandler<?> stagedModelDataHandler =
							StagedModelDataHandlerRegistryUtil.
								getStagedModelDataHandler(
									CPAttachmentFileEntry.class.getName());

						dynamicQuery.add(
							workflowStatusProperty.in(
								stagedModelDataHandler.
									getExportableStatuses()));
					}
				}

			});

		exportActionableDynamicQuery.setCompanyId(
			portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setGroupId(
			portletDataContext.getScopeGroupId());

		exportActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CPAttachmentFileEntry>() {

				@Override
				public void performAction(
						CPAttachmentFileEntry cpAttachmentFileEntry)
					throws PortalException {

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, cpAttachmentFileEntry);
				}

			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				PortalUtil.getClassNameId(
					CPAttachmentFileEntry.class.getName()),
				StagedModelType.REFERRER_CLASS_NAME_ID_ALL));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return cpAttachmentFileEntryPersistence.create(
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
				"Implement CPAttachmentFileEntryLocalServiceImpl#deleteCPAttachmentFileEntry(CPAttachmentFileEntry) to avoid orphaned data");
		}

		return cpAttachmentFileEntryLocalService.deleteCPAttachmentFileEntry(
			(CPAttachmentFileEntry)persistedModel);
	}

	@Override
	public BasePersistence<CPAttachmentFileEntry> getBasePersistence() {
		return cpAttachmentFileEntryPersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return cpAttachmentFileEntryPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns all the cp attachment file entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the cp attachment file entries
	 * @param companyId the primary key of the company
	 * @return the matching cp attachment file entries, or an empty list if no matches were found
	 */
	@Override
	public List<CPAttachmentFileEntry>
		getCPAttachmentFileEntriesByUuidAndCompanyId(
			String uuid, long companyId) {

		return cpAttachmentFileEntryPersistence.findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of cp attachment file entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the cp attachment file entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching cp attachment file entries, or an empty list if no matches were found
	 */
	@Override
	public List<CPAttachmentFileEntry>
		getCPAttachmentFileEntriesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return cpAttachmentFileEntryPersistence.findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the cp attachment file entry matching the UUID and group.
	 *
	 * @param uuid the cp attachment file entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching cp attachment file entry
	 * @throws PortalException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry getCPAttachmentFileEntryByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return cpAttachmentFileEntryPersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the cp attachment file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> getCPAttachmentFileEntries(
		int start, int end) {

		return cpAttachmentFileEntryPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of cp attachment file entries.
	 *
	 * @return the number of cp attachment file entries
	 */
	@Override
	public int getCPAttachmentFileEntriesCount() {
		return cpAttachmentFileEntryPersistence.countAll();
	}

	/**
	 * Updates the cp attachment file entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPAttachmentFileEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpAttachmentFileEntry the cp attachment file entry
	 * @return the cp attachment file entry that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPAttachmentFileEntry updateCPAttachmentFileEntry(
		CPAttachmentFileEntry cpAttachmentFileEntry) {

		return cpAttachmentFileEntryPersistence.update(cpAttachmentFileEntry);
	}

	@Deactivate
	protected void deactivate() {
		CPAttachmentFileEntryLocalServiceUtil.setService(null);
	}

	@Override
	public Class<?>[] getAopInterfaces() {
		return new Class<?>[] {
			CPAttachmentFileEntryLocalService.class,
			IdentifiableOSGiService.class, CTService.class,
			PersistedModelLocalService.class
		};
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		cpAttachmentFileEntryLocalService =
			(CPAttachmentFileEntryLocalService)aopProxy;

		CPAttachmentFileEntryLocalServiceUtil.setService(
			cpAttachmentFileEntryLocalService);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return CPAttachmentFileEntryLocalService.class.getName();
	}

	@Override
	public CTPersistence<CPAttachmentFileEntry> getCTPersistence() {
		return cpAttachmentFileEntryPersistence;
	}

	@Override
	public Class<CPAttachmentFileEntry> getModelClass() {
		return CPAttachmentFileEntry.class;
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<CPAttachmentFileEntry>, R, E>
				updateUnsafeFunction)
		throws E {

		return updateUnsafeFunction.apply(cpAttachmentFileEntryPersistence);
	}

	protected String getModelClassName() {
		return CPAttachmentFileEntry.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource =
				cpAttachmentFileEntryPersistence.getDataSource();

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

	protected CPAttachmentFileEntryLocalService
		cpAttachmentFileEntryLocalService;

	@Reference
	protected CPAttachmentFileEntryPersistence cpAttachmentFileEntryPersistence;

	@Reference
	protected CPAttachmentFileEntryFinder cpAttachmentFileEntryFinder;

	@Reference
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		CPAttachmentFileEntryLocalServiceBaseImpl.class);

}