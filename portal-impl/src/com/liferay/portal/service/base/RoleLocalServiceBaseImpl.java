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

import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.bean.BeanReference;
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
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.GroupPersistence;
import com.liferay.portal.kernel.service.persistence.RoleFinder;
import com.liferay.portal.kernel.service.persistence.RolePersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the role local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portal.service.impl.RoleLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.impl.RoleLocalServiceImpl
 * @generated
 */
public abstract class RoleLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements IdentifiableOSGiService, RoleLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>RoleLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>RoleLocalServiceUtil</code>.
	 */

	/**
	 * Adds the role to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RoleLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param role the role
	 * @return the role that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Role addRole(Role role) {
		role.setNew(true);

		return rolePersistence.update(role);
	}

	/**
	 * Creates a new role with the primary key. Does not add the role to the database.
	 *
	 * @param roleId the primary key for the new role
	 * @return the new role
	 */
	@Override
	@Transactional(enabled = false)
	public Role createRole(long roleId) {
		return rolePersistence.create(roleId);
	}

	/**
	 * Deletes the role with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RoleLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param roleId the primary key of the role
	 * @return the role that was removed
	 * @throws PortalException if a role with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public Role deleteRole(long roleId) throws PortalException {
		return rolePersistence.remove(roleId);
	}

	/**
	 * Deletes the role from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RoleLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param role the role
	 * @return the role that was removed
	 * @throws PortalException
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public Role deleteRole(Role role) throws PortalException {
		return rolePersistence.remove(role);
	}

	@Override
	public <T> T dslQuery(DSLQuery dslQuery) {
		return rolePersistence.dslQuery(dslQuery);
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
			Role.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return rolePersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.RoleModelImpl</code>.
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

		return rolePersistence.findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.RoleModelImpl</code>.
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

		return rolePersistence.findWithDynamicQuery(
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
		return rolePersistence.countWithDynamicQuery(dynamicQuery);
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

		return rolePersistence.countWithDynamicQuery(dynamicQuery, projection);
	}

	@Override
	public Role fetchRole(long roleId) {
		return rolePersistence.fetchByPrimaryKey(roleId);
	}

	/**
	 * Returns the role with the matching UUID and company.
	 *
	 * @param uuid the role's UUID
	 * @param companyId the primary key of the company
	 * @return the matching role, or <code>null</code> if a matching role could not be found
	 */
	@Override
	public Role fetchRoleByUuidAndCompanyId(String uuid, long companyId) {
		return rolePersistence.fetchByUuid_C_First(uuid, companyId, null);
	}

	/**
	 * Returns the role with the primary key.
	 *
	 * @param roleId the primary key of the role
	 * @return the role
	 * @throws PortalException if a role with the primary key could not be found
	 */
	@Override
	public Role getRole(long roleId) throws PortalException {
		return rolePersistence.findByPrimaryKey(roleId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(roleLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(Role.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("roleId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(roleLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(Role.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName("roleId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(roleLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(Role.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("roleId");
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
				}

			});

		exportActionableDynamicQuery.setCompanyId(
			portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Role>() {

				@Override
				public void performAction(Role role) throws PortalException {
					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, role);
				}

			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				PortalUtil.getClassNameId(Role.class.getName()),
				StagedModelType.REFERRER_CLASS_NAME_ID_ALL));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return rolePersistence.create(((Long)primaryKeyObj).longValue());
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		if (_log.isWarnEnabled()) {
			_log.warn(
				"Implement RoleLocalServiceImpl#deleteRole(Role) to avoid orphaned data");
		}

		return roleLocalService.deleteRole((Role)persistedModel);
	}

	@Override
	public BasePersistence<Role> getBasePersistence() {
		return rolePersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return rolePersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns the role with the matching UUID and company.
	 *
	 * @param uuid the role's UUID
	 * @param companyId the primary key of the company
	 * @return the matching role
	 * @throws PortalException if a matching role could not be found
	 */
	@Override
	public Role getRoleByUuidAndCompanyId(String uuid, long companyId)
		throws PortalException {

		return rolePersistence.findByUuid_C_First(uuid, companyId, null);
	}

	/**
	 * Returns a range of all the roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.RoleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of roles
	 * @param end the upper bound of the range of roles (not inclusive)
	 * @return the range of roles
	 */
	@Override
	public List<Role> getRoles(int start, int end) {
		return rolePersistence.findAll(start, end);
	}

	/**
	 * Returns the number of roles.
	 *
	 * @return the number of roles
	 */
	@Override
	public int getRolesCount() {
		return rolePersistence.countAll();
	}

	/**
	 * Updates the role in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RoleLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param role the role
	 * @return the role that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Role updateRole(Role role) {
		return rolePersistence.update(role);
	}

	/**
	 */
	@Override
	public void addGroupRole(long groupId, long roleId) {
		groupPersistence.addRole(groupId, roleId);
	}

	/**
	 */
	@Override
	public void addGroupRole(long groupId, Role role) {
		groupPersistence.addRole(groupId, role);
	}

	/**
	 */
	@Override
	public void addGroupRoles(long groupId, long[] roleIds) {
		groupPersistence.addRoles(groupId, roleIds);
	}

	/**
	 */
	@Override
	public void addGroupRoles(long groupId, List<Role> roles) {
		groupPersistence.addRoles(groupId, roles);
	}

	/**
	 */
	@Override
	public void clearGroupRoles(long groupId) {
		groupPersistence.clearRoles(groupId);
	}

	/**
	 */
	@Override
	public void deleteGroupRole(long groupId, long roleId) {
		groupPersistence.removeRole(groupId, roleId);
	}

	/**
	 */
	@Override
	public void deleteGroupRole(long groupId, Role role) {
		groupPersistence.removeRole(groupId, role);
	}

	/**
	 */
	@Override
	public void deleteGroupRoles(long groupId, long[] roleIds) {
		groupPersistence.removeRoles(groupId, roleIds);
	}

	/**
	 */
	@Override
	public void deleteGroupRoles(long groupId, List<Role> roles) {
		groupPersistence.removeRoles(groupId, roles);
	}

	/**
	 * Returns the groupIds of the groups associated with the role.
	 *
	 * @param roleId the roleId of the role
	 * @return long[] the groupIds of groups associated with the role
	 */
	@Override
	public long[] getGroupPrimaryKeys(long roleId) {
		return rolePersistence.getGroupPrimaryKeys(roleId);
	}

	/**
	 */
	@Override
	public List<Role> getGroupRoles(long groupId) {
		return groupPersistence.getRoles(groupId);
	}

	/**
	 */
	@Override
	public List<Role> getGroupRoles(long groupId, int start, int end) {
		return groupPersistence.getRoles(groupId, start, end);
	}

	/**
	 */
	@Override
	public List<Role> getGroupRoles(
		long groupId, int start, int end,
		OrderByComparator<Role> orderByComparator) {

		return groupPersistence.getRoles(
			groupId, start, end, orderByComparator);
	}

	/**
	 */
	@Override
	public int getGroupRolesCount(long groupId) {
		return groupPersistence.getRolesSize(groupId);
	}

	/**
	 */
	@Override
	public boolean hasGroupRole(long groupId, long roleId) {
		return groupPersistence.containsRole(groupId, roleId);
	}

	/**
	 */
	@Override
	public boolean hasGroupRoles(long groupId) {
		return groupPersistence.containsRoles(groupId);
	}

	/**
	 */
	@Override
	public void setGroupRoles(long groupId, long[] roleIds) {
		groupPersistence.setRoles(groupId, roleIds);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public void addUserRole(long userId, long roleId) throws PortalException {
		userPersistence.addRole(userId, roleId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public void addUserRole(long userId, Role role) throws PortalException {
		userPersistence.addRole(userId, role);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public void addUserRoles(long userId, long[] roleIds)
		throws PortalException {

		userPersistence.addRoles(userId, roleIds);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public void addUserRoles(long userId, List<Role> roles)
		throws PortalException {

		userPersistence.addRoles(userId, roles);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public void clearUserRoles(long userId) throws PortalException {
		userPersistence.clearRoles(userId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public void deleteUserRole(long userId, long roleId)
		throws PortalException {

		userPersistence.removeRole(userId, roleId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public void deleteUserRole(long userId, Role role) throws PortalException {
		userPersistence.removeRole(userId, role);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public void deleteUserRoles(long userId, long[] roleIds)
		throws PortalException {

		userPersistence.removeRoles(userId, roleIds);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public void deleteUserRoles(long userId, List<Role> roles)
		throws PortalException {

		userPersistence.removeRoles(userId, roles);
	}

	/**
	 * Returns the userIds of the users associated with the role.
	 *
	 * @param roleId the roleId of the role
	 * @return long[] the userIds of users associated with the role
	 */
	@Override
	public long[] getUserPrimaryKeys(long roleId) {
		return rolePersistence.getUserPrimaryKeys(roleId);
	}

	/**
	 */
	@Override
	public List<Role> getUserRoles(long userId) {
		return userPersistence.getRoles(userId);
	}

	/**
	 */
	@Override
	public List<Role> getUserRoles(long userId, int start, int end) {
		return userPersistence.getRoles(userId, start, end);
	}

	/**
	 */
	@Override
	public List<Role> getUserRoles(
		long userId, int start, int end,
		OrderByComparator<Role> orderByComparator) {

		return userPersistence.getRoles(userId, start, end, orderByComparator);
	}

	/**
	 */
	@Override
	public int getUserRolesCount(long userId) {
		return userPersistence.getRolesSize(userId);
	}

	/**
	 */
	@Override
	public boolean hasUserRole(long userId, long roleId) {
		return userPersistence.containsRole(userId, roleId);
	}

	/**
	 */
	@Override
	public boolean hasUserRoles(long userId) {
		return userPersistence.containsRoles(userId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public void setUserRoles(long userId, long[] roleIds)
		throws PortalException {

		userPersistence.setRoles(userId, roleIds);
	}

	/**
	 * Returns the role local service.
	 *
	 * @return the role local service
	 */
	public RoleLocalService getRoleLocalService() {
		return roleLocalService;
	}

	/**
	 * Sets the role local service.
	 *
	 * @param roleLocalService the role local service
	 */
	public void setRoleLocalService(RoleLocalService roleLocalService) {
		this.roleLocalService = roleLocalService;
	}

	/**
	 * Returns the role persistence.
	 *
	 * @return the role persistence
	 */
	public RolePersistence getRolePersistence() {
		return rolePersistence;
	}

	/**
	 * Sets the role persistence.
	 *
	 * @param rolePersistence the role persistence
	 */
	public void setRolePersistence(RolePersistence rolePersistence) {
		this.rolePersistence = rolePersistence;
	}

	/**
	 * Returns the role finder.
	 *
	 * @return the role finder
	 */
	public RoleFinder getRoleFinder() {
		return roleFinder;
	}

	/**
	 * Sets the role finder.
	 *
	 * @param roleFinder the role finder
	 */
	public void setRoleFinder(RoleFinder roleFinder) {
		this.roleFinder = roleFinder;
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
		persistedModelLocalServiceRegistry.register(
			"com.liferay.portal.kernel.model.Role", roleLocalService);

		RoleLocalServiceUtil.setService(roleLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.portal.kernel.model.Role");

		RoleLocalServiceUtil.setService(null);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return RoleLocalService.class.getName();
	}

	@Override
	public CTPersistence<Role> getCTPersistence() {
		return rolePersistence;
	}

	@Override
	public Class<Role> getModelClass() {
		return Role.class;
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<Role>, R, E> updateUnsafeFunction)
		throws E {

		return updateUnsafeFunction.apply(rolePersistence);
	}

	protected String getModelClassName() {
		return Role.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = rolePersistence.getDataSource();

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

	@BeanReference(type = RoleLocalService.class)
	protected RoleLocalService roleLocalService;

	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;

	@BeanReference(type = RoleFinder.class)
	protected RoleFinder roleFinder;

	@BeanReference(
		type = com.liferay.counter.kernel.service.CounterLocalService.class
	)
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;

	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;

	private static final Log _log = LogFactoryUtil.getLog(
		RoleLocalServiceBaseImpl.class);

	@BeanReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry
		persistedModelLocalServiceRegistry;

}