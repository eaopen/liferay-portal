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

package com.liferay.commerce.payment.service;

import com.liferay.commerce.payment.model.CommercePaymentEntryAudit;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for CommercePaymentEntryAudit. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Luca Pellizzon
 * @see CommercePaymentEntryAuditServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CommercePaymentEntryAuditService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.commerce.payment.service.impl.CommercePaymentEntryAuditServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the commerce payment entry audit remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CommercePaymentEntryAuditServiceUtil} if injection and service tracking are not available.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePaymentEntryAudit> getCommercePaymentEntries(
			long companyId, long commercePaymentEntryId, int start, int end,
			OrderByComparator<CommercePaymentEntryAudit> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePaymentEntryAudit getCommercePaymentEntryAudit(
			long commercePaymentEntryAuditId)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePaymentEntryAudit> search(
			long companyId, long[] commercePaymentEntryIds, String[] logTypes,
			String keywords, int start, int end, String orderByField,
			boolean reverse)
		throws PortalException;

}