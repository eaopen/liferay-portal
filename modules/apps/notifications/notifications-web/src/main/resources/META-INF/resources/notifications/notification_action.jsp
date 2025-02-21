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

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

UserNotificationEvent userNotificationEvent = (UserNotificationEvent)row.getObject();

JSONObject jsonObject = JSONFactoryUtil.createJSONObject(userNotificationEvent.getPayload());

long subscriptionId = jsonObject.getLong("subscriptionId");

if (subscriptionId > 0) {
	Subscription subscription = SubscriptionLocalServiceUtil.fetchSubscription(subscriptionId);

	if (subscription == null) {
		subscriptionId = 0;
	}
}
%>

<c:choose>
	<c:when test="<%= notificationActionIconList %>">
		<liferay-ui:icon-list>
			<%@ include file="/notifications/notification_action_icon_body.jspf" %>
		</liferay-ui:icon-list>
	</c:when>
	<c:otherwise>
		<liferay-ui:icon-menu
			direction="left-side"
			icon="<%= StringPool.BLANK %>"
			markupView="lexicon"
			message="actions"
			showWhenSingleIcon="<%= true %>"
		>
			<%@ include file="/notifications/notification_action_icon_body.jspf" %>
		</liferay-ui:icon-menu>
	</c:otherwise>
</c:choose>