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

package com.liferay.portlet.social.service.base;

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
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.social.kernel.model.SocialActivityAchievement;
import com.liferay.social.kernel.service.SocialActivityAchievementLocalService;
import com.liferay.social.kernel.service.SocialActivityAchievementLocalServiceUtil;
import com.liferay.social.kernel.service.persistence.SocialActivityAchievementPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the social activity achievement local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portlet.social.service.impl.SocialActivityAchievementLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.social.service.impl.SocialActivityAchievementLocalServiceImpl
 * @generated
 */
public abstract class SocialActivityAchievementLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements IdentifiableOSGiService, SocialActivityAchievementLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>SocialActivityAchievementLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>SocialActivityAchievementLocalServiceUtil</code>.
	 */

	/**
	 * Adds the social activity achievement to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SocialActivityAchievementLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param socialActivityAchievement the social activity achievement
	 * @return the social activity achievement that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SocialActivityAchievement addSocialActivityAchievement(
		SocialActivityAchievement socialActivityAchievement) {

		socialActivityAchievement.setNew(true);

		return socialActivityAchievementPersistence.update(
			socialActivityAchievement);
	}

	/**
	 * Creates a new social activity achievement with the primary key. Does not add the social activity achievement to the database.
	 *
	 * @param activityAchievementId the primary key for the new social activity achievement
	 * @return the new social activity achievement
	 */
	@Override
	@Transactional(enabled = false)
	public SocialActivityAchievement createSocialActivityAchievement(
		long activityAchievementId) {

		return socialActivityAchievementPersistence.create(
			activityAchievementId);
	}

	/**
	 * Deletes the social activity achievement with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SocialActivityAchievementLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param activityAchievementId the primary key of the social activity achievement
	 * @return the social activity achievement that was removed
	 * @throws PortalException if a social activity achievement with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public SocialActivityAchievement deleteSocialActivityAchievement(
			long activityAchievementId)
		throws PortalException {

		return socialActivityAchievementPersistence.remove(
			activityAchievementId);
	}

	/**
	 * Deletes the social activity achievement from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SocialActivityAchievementLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param socialActivityAchievement the social activity achievement
	 * @return the social activity achievement that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public SocialActivityAchievement deleteSocialActivityAchievement(
		SocialActivityAchievement socialActivityAchievement) {

		return socialActivityAchievementPersistence.remove(
			socialActivityAchievement);
	}

	@Override
	public <T> T dslQuery(DSLQuery dslQuery) {
		return socialActivityAchievementPersistence.dslQuery(dslQuery);
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
			SocialActivityAchievement.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return socialActivityAchievementPersistence.findWithDynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.social.model.impl.SocialActivityAchievementModelImpl</code>.
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

		return socialActivityAchievementPersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.social.model.impl.SocialActivityAchievementModelImpl</code>.
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

		return socialActivityAchievementPersistence.findWithDynamicQuery(
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
		return socialActivityAchievementPersistence.countWithDynamicQuery(
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

		return socialActivityAchievementPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public SocialActivityAchievement fetchSocialActivityAchievement(
		long activityAchievementId) {

		return socialActivityAchievementPersistence.fetchByPrimaryKey(
			activityAchievementId);
	}

	/**
	 * Returns the social activity achievement with the primary key.
	 *
	 * @param activityAchievementId the primary key of the social activity achievement
	 * @return the social activity achievement
	 * @throws PortalException if a social activity achievement with the primary key could not be found
	 */
	@Override
	public SocialActivityAchievement getSocialActivityAchievement(
			long activityAchievementId)
		throws PortalException {

		return socialActivityAchievementPersistence.findByPrimaryKey(
			activityAchievementId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(
			socialActivityAchievementLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(SocialActivityAchievement.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"activityAchievementId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			socialActivityAchievementLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(
			SocialActivityAchievement.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"activityAchievementId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(
			socialActivityAchievementLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(SocialActivityAchievement.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"activityAchievementId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return socialActivityAchievementPersistence.create(
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
				"Implement SocialActivityAchievementLocalServiceImpl#deleteSocialActivityAchievement(SocialActivityAchievement) to avoid orphaned data");
		}

		return socialActivityAchievementLocalService.
			deleteSocialActivityAchievement(
				(SocialActivityAchievement)persistedModel);
	}

	@Override
	public BasePersistence<SocialActivityAchievement> getBasePersistence() {
		return socialActivityAchievementPersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return socialActivityAchievementPersistence.findByPrimaryKey(
			primaryKeyObj);
	}

	/**
	 * Returns a range of all the social activity achievements.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.social.model.impl.SocialActivityAchievementModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity achievements
	 * @param end the upper bound of the range of social activity achievements (not inclusive)
	 * @return the range of social activity achievements
	 */
	@Override
	public List<SocialActivityAchievement> getSocialActivityAchievements(
		int start, int end) {

		return socialActivityAchievementPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of social activity achievements.
	 *
	 * @return the number of social activity achievements
	 */
	@Override
	public int getSocialActivityAchievementsCount() {
		return socialActivityAchievementPersistence.countAll();
	}

	/**
	 * Updates the social activity achievement in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SocialActivityAchievementLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param socialActivityAchievement the social activity achievement
	 * @return the social activity achievement that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SocialActivityAchievement updateSocialActivityAchievement(
		SocialActivityAchievement socialActivityAchievement) {

		return socialActivityAchievementPersistence.update(
			socialActivityAchievement);
	}

	/**
	 * Returns the social activity achievement local service.
	 *
	 * @return the social activity achievement local service
	 */
	public SocialActivityAchievementLocalService
		getSocialActivityAchievementLocalService() {

		return socialActivityAchievementLocalService;
	}

	/**
	 * Sets the social activity achievement local service.
	 *
	 * @param socialActivityAchievementLocalService the social activity achievement local service
	 */
	public void setSocialActivityAchievementLocalService(
		SocialActivityAchievementLocalService
			socialActivityAchievementLocalService) {

		this.socialActivityAchievementLocalService =
			socialActivityAchievementLocalService;
	}

	/**
	 * Returns the social activity achievement persistence.
	 *
	 * @return the social activity achievement persistence
	 */
	public SocialActivityAchievementPersistence
		getSocialActivityAchievementPersistence() {

		return socialActivityAchievementPersistence;
	}

	/**
	 * Sets the social activity achievement persistence.
	 *
	 * @param socialActivityAchievementPersistence the social activity achievement persistence
	 */
	public void setSocialActivityAchievementPersistence(
		SocialActivityAchievementPersistence
			socialActivityAchievementPersistence) {

		this.socialActivityAchievementPersistence =
			socialActivityAchievementPersistence;
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
			"com.liferay.social.kernel.model.SocialActivityAchievement",
			socialActivityAchievementLocalService);

		SocialActivityAchievementLocalServiceUtil.setService(
			socialActivityAchievementLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.social.kernel.model.SocialActivityAchievement");

		SocialActivityAchievementLocalServiceUtil.setService(null);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return SocialActivityAchievementLocalService.class.getName();
	}

	@Override
	public CTPersistence<SocialActivityAchievement> getCTPersistence() {
		return socialActivityAchievementPersistence;
	}

	@Override
	public Class<SocialActivityAchievement> getModelClass() {
		return SocialActivityAchievement.class;
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<SocialActivityAchievement>, R, E>
				updateUnsafeFunction)
		throws E {

		return updateUnsafeFunction.apply(socialActivityAchievementPersistence);
	}

	protected String getModelClassName() {
		return SocialActivityAchievement.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource =
				socialActivityAchievementPersistence.getDataSource();

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

	@BeanReference(type = SocialActivityAchievementLocalService.class)
	protected SocialActivityAchievementLocalService
		socialActivityAchievementLocalService;

	@BeanReference(type = SocialActivityAchievementPersistence.class)
	protected SocialActivityAchievementPersistence
		socialActivityAchievementPersistence;

	@BeanReference(
		type = com.liferay.counter.kernel.service.CounterLocalService.class
	)
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		SocialActivityAchievementLocalServiceBaseImpl.class);

	@BeanReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry
		persistedModelLocalServiceRegistry;

}