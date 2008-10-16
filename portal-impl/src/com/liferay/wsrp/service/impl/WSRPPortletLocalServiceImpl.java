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
/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2008 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.wsrp.service.impl;

import com.liferay.portal.SystemException;
import com.liferay.wsrp.NoSuchPortletException;
import com.liferay.wsrp.model.WSRPPortlet;
import com.liferay.wsrp.service.base.WSRPPortletLocalServiceBaseImpl;

import java.util.List;

/**
 * <a href="WSRPPortletLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Rajesh Thiagarajan
 *
 */
public class WSRPPortletLocalServiceImpl
		extends WSRPPortletLocalServiceBaseImpl {

	public WSRPPortlet getWSRPPortlet( String portletName )
		throws SystemException, NoSuchPortletException {

		return wsrpPortletPersistence.findByPortletName(portletName);
	}

	public List<WSRPPortlet> getWSRPPortlets() throws SystemException {

		return wsrpPortletPersistence.findAll();
	}

	public List<WSRPPortlet> getWSRPPortlets( String producerEntityId )
		throws SystemException {

		return wsrpPortletPersistence.findByProducerEntityId(producerEntityId);
	}

	public List<WSRPPortlet> getWSRPPortlets(
			String producerEntityId, String portletHandle)
		throws SystemException {

		return wsrpPortletPersistence.findByP_P(
			producerEntityId, portletHandle);
	}

}