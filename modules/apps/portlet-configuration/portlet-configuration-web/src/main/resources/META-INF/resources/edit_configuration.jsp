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

<%@ include file="/init.jsp" %>

<c:if test="<%= !layout.isTypeControlPanel() && !windowState.equals(LiferayWindowState.EXCLUSIVE) %>">
	<liferay-util:include page="/tabs1.jsp" servletContext="<%= application %>">
		<liferay-util:param name="tabs1" value="setup" />
	</liferay-util:include>
</c:if>

<div class="cadmin portlet-configuration-setup">

	<%
	ConfigurationAction configurationAction = (ConfigurationAction)request.getAttribute(WebKeys.CONFIGURATION_ACTION);

	if (configurationAction != null) {
		configurationAction.include(portletConfig, request, PipingServletResponseFactory.createPipingServletResponse(pageContext));
	}
	%>

</div>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"portletId", selPortlet.getPortletName()
		).build()
	%>'
	module="js/EditConfigurationEventHandler"
/>