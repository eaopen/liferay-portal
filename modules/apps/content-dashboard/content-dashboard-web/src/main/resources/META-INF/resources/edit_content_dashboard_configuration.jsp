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
ContentDashboardAdminConfigurationDisplayContext contentDashboardAdminConfigurationDisplayContext = (ContentDashboardAdminConfigurationDisplayContext)request.getAttribute(ContentDashboardAdminConfigurationDisplayContext.class.getName());
%>

<liferay-frontend:edit-form
	action="<%= contentDashboardAdminConfigurationDisplayContext.getActionURL() %>"
	method="post"
	name="fm"
>
	<aui:input name="redirect" type="hidden" value="<%= contentDashboardAdminConfigurationDisplayContext.getRedirect() %>" />

	<liferay-frontend:edit-form-body>
		<c:if test='<%= GetterUtil.getBoolean(SessionMessages.get(renderRequest, "emptyAssetVocabularyIds")) %>'>
			<clay:alert
				dismissible="<%= true %>"
				displayType="warning"
				message="you-have-not-selected-any-vocabularies"
			/>
		</c:if>

		<liferay-frontend:fieldset>
			<aui:field-wrapper>
				<p class="sheet-text">
					<liferay-ui:message key="select-vocabularies-description" />
				</p>

				<div class="vocabularies-selection-wrapper">
					<span
						aria-hidden="true"
						class="loading-animation
						vocabularies-selection-loader"
					>
					</span>

					<react:component
						module="js/components/VocabulariesSelectionBox"
						props='<%=
							HashMapBuilder.<String, Object>put(
								"leftBoxName", "availableAssetVocabularyIds"
							).put(
								"leftList", contentDashboardAdminConfigurationDisplayContext.getAvailableVocabularyJSONArray()
							).put(
								"rightBoxName", "currentAssetVocabularyIds"
							).put(
								"rightList", contentDashboardAdminConfigurationDisplayContext.getCurrentVocabularyJSONArray()
							).build()
						%>'
					/>
				</div>
			</aui:field-wrapper>
		</liferay-frontend:fieldset>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>