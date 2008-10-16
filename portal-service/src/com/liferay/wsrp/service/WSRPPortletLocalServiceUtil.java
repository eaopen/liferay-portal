/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.wsrp.service;


/**
 * <a href="WSRPPortletLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.wsrp.service.WSRPPortletLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.wsrp.service.WSRPPortletLocalService
 *
 */
public class WSRPPortletLocalServiceUtil {
	public static com.liferay.wsrp.model.WSRPPortlet addWSRPPortlet(
		com.liferay.wsrp.model.WSRPPortlet wsrpPortlet)
		throws com.liferay.portal.SystemException {
		return getService().addWSRPPortlet(wsrpPortlet);
	}

	public static com.liferay.wsrp.model.WSRPPortlet createWSRPPortlet(
		long wsrpPortletId) {
		return getService().createWSRPPortlet(wsrpPortletId);
	}

	public static void deleteWSRPPortlet(long wsrpPortletId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteWSRPPortlet(wsrpPortletId);
	}

	public static void deleteWSRPPortlet(
		com.liferay.wsrp.model.WSRPPortlet wsrpPortlet)
		throws com.liferay.portal.SystemException {
		getService().deleteWSRPPortlet(wsrpPortlet);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.wsrp.model.WSRPPortlet getWSRPPortlet(
		long wsrpPortletId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getWSRPPortlet(wsrpPortletId);
	}

	public static java.util.List<com.liferay.wsrp.model.WSRPPortlet> getWSRPPortlets(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getWSRPPortlets(start, end);
	}

	public static int getWSRPPortletsCount()
		throws com.liferay.portal.SystemException {
		return getService().getWSRPPortletsCount();
	}

	public static com.liferay.wsrp.model.WSRPPortlet updateWSRPPortlet(
		com.liferay.wsrp.model.WSRPPortlet wsrpPortlet)
		throws com.liferay.portal.SystemException {
		return getService().updateWSRPPortlet(wsrpPortlet);
	}

	public static com.liferay.wsrp.model.WSRPPortlet getWSRPPortlet(
		java.lang.String portletName)
		throws com.liferay.portal.SystemException,
			com.liferay.wsrp.NoSuchPortletException {
		return getService().getWSRPPortlet(portletName);
	}

	public static java.util.List<com.liferay.wsrp.model.WSRPPortlet> getWSRPPortlets()
		throws com.liferay.portal.SystemException {
		return getService().getWSRPPortlets();
	}

	public static java.util.List<com.liferay.wsrp.model.WSRPPortlet> getWSRPPortlets(
		java.lang.String producerEntityId)
		throws com.liferay.portal.SystemException {
		return getService().getWSRPPortlets(producerEntityId);
	}

	public static java.util.List<com.liferay.wsrp.model.WSRPPortlet> getWSRPPortlets(
		java.lang.String producerEntityId, java.lang.String portletHandle)
		throws com.liferay.portal.SystemException {
		return getService().getWSRPPortlets(producerEntityId, portletHandle);
	}

	public static WSRPPortletLocalService getService() {
		if (_service == null) {
			throw new RuntimeException("WSRPPortletLocalService is not set");
		}

		return _service;
	}

	public void setService(WSRPPortletLocalService service) {
		_service = service;
	}

	private static WSRPPortletLocalService _service;
}