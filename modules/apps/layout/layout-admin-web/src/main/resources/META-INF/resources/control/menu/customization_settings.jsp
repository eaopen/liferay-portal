<%--
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
--%>

<%@ include file="/control/menu/init.jsp" %>

<%
String portletNamespace = PortalUtil.getPortletNamespace(LayoutAdminPortletKeys.GROUP_PAGES);
%>

<div class="active control-menu-link customization-link d-block d-md-none">
	<clay:icon
		aria-label='<%= LanguageUtil.get(request, "this-page-can-be-customized") %>'
		cssClass="lfr-portal-tooltip"
		data='<%=
			HashMapBuilder.<String, Object>put(
				"qa-id", "customizations"
			).build()
		%>'
		id='<%= portletNamespace + "customizationButton" %>'
		symbol="pencil"
		title='<%= LanguageUtil.get(request, "this-page-can-be-customized") %>'
	/>
</div>