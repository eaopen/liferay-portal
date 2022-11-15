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

<%@ include file="/entries/init.jsp" %>

<%
String label = LanguageUtil.get(resourceBundle, "new-collection-page-item");

List<AssetPublisherAddItemHolder> assetPublisherAddItemHolders = (List<AssetPublisherAddItemHolder>)request.getAttribute(CollectionPageLayoutTypeControllerWebKeys.ASSET_PUBLISHER_ADD_ITEM_HOLDERS);

if (assetPublisherAddItemHolders.size() == 1) {
	AssetPublisherAddItemHolder assetPublisherAddItemHolder = assetPublisherAddItemHolders.get(0);

	label = LanguageUtil.format(request, "new-x", new Object[] {assetPublisherAddItemHolder.getModelResource()});
}
%>

<liferay-ui:success key="collectionItemAdded" message="your-request-completed-successfully" />

<li class="control-menu-nav-item control-menu-nav-item-content">
	<c:choose>
		<c:when test="<%= assetPublisherAddItemHolders.size() == 1 %>">

			<%
			AssetPublisherAddItemHolder assetPublisherAddItemHolder = assetPublisherAddItemHolders.get(0);
			%>

			<liferay-ui:icon
				data="<%= data %>"
				icon="plus"
				linkCssClass="icon-monospaced lfr-portal-tooltip"
				markupView="lexicon"
				message="<%= label %>"
				url="<%= String.valueOf(assetPublisherAddItemHolder.getPortletURL()) %>"
			/>
		</c:when>
		<c:otherwise>

			<%
			CollectionItemsDetailDisplayContext collectionItemsDetailDisplayContext = (CollectionItemsDetailDisplayContext)request.getAttribute(CollectionPageLayoutTypeControllerWebKeys.COLLECTION_ITEMS_DETAIL_DISPLAY_CONTEXT);
			%>

			<clay:dropdown-menu
				aria-label="<%= label %>"
				borderless="<%= true %>"
				displayType="unstyled"
				dropdownItems="<%= collectionItemsDetailDisplayContext.getDropdownItems(assetPublisherAddItemHolders) %>"
				icon="plus"
				monospaced="<%= true %>"
				small="<%= true %>"
				title="<%= label %>"
			/>
		</c:otherwise>
	</c:choose>
</li>