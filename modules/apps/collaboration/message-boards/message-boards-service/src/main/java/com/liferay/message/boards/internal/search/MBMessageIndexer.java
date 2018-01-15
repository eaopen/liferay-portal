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

package com.liferay.message.boards.internal.search;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.message.boards.kernel.model.MBCategory;
import com.liferay.message.boards.kernel.model.MBCategoryConstants;
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.service.MBCategoryLocalService;
import com.liferay.message.boards.kernel.service.MBCategoryService;
import com.liferay.message.boards.kernel.service.MBMessageLocalService;
import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.message.boards.service.MBDiscussionLocalService;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BaseRelatedEntryIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelperUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.RelatedEntryIndexer;
import com.liferay.portal.kernel.search.RelatedEntryIndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.messageboards.util.MBMessageAttachmentsUtil;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	property = {"related.entry.indexer.class.name=com.liferay.message.boards.kernel.model.MBMessage"},
	service = {Indexer.class, RelatedEntryIndexer.class}
)
public class MBMessageIndexer
	extends BaseIndexer<MBMessage> implements RelatedEntryIndexer {

	public static final String CLASS_NAME = MBMessage.class.getName();

	public MBMessageIndexer() {
		setDefaultSelectedFieldNames(
			Field.ASSET_TAG_NAMES, Field.CLASS_NAME_ID, Field.CLASS_PK,
			Field.COMPANY_ID, Field.CONTENT, Field.ENTRY_CLASS_NAME,
			Field.ENTRY_CLASS_PK, Field.GROUP_ID, Field.MODIFIED_DATE,
			Field.SCOPE_GROUP_ID, Field.TITLE, Field.UID);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public void addRelatedClassNames(
			BooleanFilter contextFilter, SearchContext searchContext)
		throws Exception {

		_relatedEntryIndexer.addRelatedClassNames(contextFilter, searchContext);
	}

	@Override
	public void addRelatedEntryFields(Document document, Object obj)
		throws Exception {

		FileEntry fileEntry = (FileEntry)obj;

		MBMessage message = MBMessageAttachmentsUtil.fetchMessage(
			fileEntry.getFileEntryId());

		if (message == null) {
			return;
		}

		document.addKeyword(Field.CATEGORY_ID, message.getCategoryId());

		document.addKeyword("discussion", false);
		document.addKeyword("threadId", message.getThreadId());
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, String entryClassName,
			long entryClassPK, String actionId)
		throws Exception {

		MBMessage message = mbMessageLocalService.getMessage(entryClassPK);

		if (message.isDiscussion()) {
			Indexer<?> indexer = IndexerRegistryUtil.getIndexer(
				message.getClassName());

			return indexer.hasPermission(
				permissionChecker, message.getClassName(), message.getClassPK(),
				ActionKeys.VIEW);
		}

		return _messageModelResourcePermission.contains(
			permissionChecker, entryClassPK, ActionKeys.VIEW);
	}

	@Override
	public boolean isVisible(long classPK, int status) throws Exception {
		MBMessage message = mbMessageLocalService.getMessage(classPK);

		return isVisible(message.getStatus(), status);
	}

	@Override
	public boolean isVisibleRelatedEntry(long classPK, int status) {
		try {
			MBMessage message = mbMessageLocalService.getMessage(classPK);

			if (message.isDiscussion()) {
				Indexer<?> indexer = IndexerRegistryUtil.getIndexer(
					message.getClassName());

				return indexer.isVisible(message.getClassPK(), status);
			}
		}
		catch (Exception e) {
			if (_log.isInfoEnabled()) {
				_log.info("Unable to get message boards message", e);
			}

			return false;
		}

		return true;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		addStatus(contextBooleanFilter, searchContext);

		boolean discussion = GetterUtil.getBoolean(
			searchContext.getAttribute("discussion"));

		contextBooleanFilter.addRequiredTerm("discussion", discussion);

		if (searchContext.isIncludeDiscussions()) {
			addRelatedClassNames(contextBooleanFilter, searchContext);
		}

		String classNameId = GetterUtil.getString(
			searchContext.getAttribute(Field.CLASS_NAME_ID));

		if (Validator.isNotNull(classNameId)) {
			contextBooleanFilter.addRequiredTerm(
				Field.CLASS_NAME_ID, classNameId);
		}

		long threadId = GetterUtil.getLong(
			(String)searchContext.getAttribute("threadId"));

		if (threadId > 0) {
			contextBooleanFilter.addRequiredTerm("threadId", threadId);
		}

		long[] categoryIds = searchContext.getCategoryIds();

		if ((categoryIds != null) && (categoryIds.length > 0) &&
			(categoryIds[0] !=
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID)) {

			TermsFilter categoriesTermsFilter = new TermsFilter(
				Field.CATEGORY_ID);

			for (long categoryId : categoryIds) {
				try {
					mbCategoryService.getCategory(categoryId);
				}
				catch (PortalException pe) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Unable to get message boards category " +
								categoryId,
							pe);
					}

					continue;
				}

				categoriesTermsFilter.addValue(String.valueOf(categoryId));
			}

			if (!categoriesTermsFilter.isEmpty()) {
				contextBooleanFilter.add(
					categoriesTermsFilter, BooleanClauseOccur.MUST);
			}
		}
	}

	@Override
	public void updateFullQuery(SearchContext searchContext) {
		if (searchContext.isIncludeDiscussions()) {
			searchContext.addFullQueryEntryClassName(MBMessage.class.getName());

			searchContext.setAttribute("discussion", Boolean.TRUE);
		}
	}

	@Override
	protected void doDelete(MBMessage mbMessage) throws Exception {
		deleteDocument(mbMessage.getCompanyId(), mbMessage.getMessageId());
	}

	@Override
	protected Document doGetDocument(MBMessage mbMessage) throws Exception {
		Document document = getBaseModelDocument(CLASS_NAME, mbMessage);

		document.addKeyword(Field.CATEGORY_ID, mbMessage.getCategoryId());
		document.addText(Field.CONTENT, processContent(mbMessage));
		document.addKeyword(
			Field.ROOT_ENTRY_CLASS_PK, mbMessage.getRootMessageId());
		document.addText(Field.TITLE, mbMessage.getSubject());

		if (mbMessage.isAnonymous()) {
			document.remove(Field.USER_NAME);
		}

		MBDiscussion discussion =
			mbDiscussionLocalService.fetchThreadDiscussion(
				mbMessage.getThreadId());

		if (discussion == null) {
			document.addKeyword("discussion", false);
		}
		else {
			document.addKeyword("discussion", true);
		}

		document.addKeyword("threadId", mbMessage.getThreadId());

		if (mbMessage.isDiscussion()) {
			List<RelatedEntryIndexer> relatedEntryIndexers =
				RelatedEntryIndexerRegistryUtil.getRelatedEntryIndexers(
					mbMessage.getClassName());

			if (relatedEntryIndexers != null) {
				for (RelatedEntryIndexer relatedEntryIndexer :
						relatedEntryIndexers) {

					Comment comment = commentManager.fetchComment(
						mbMessage.getMessageId());

					if (comment != null) {
						relatedEntryIndexer.addRelatedEntryFields(
							document, comment);

						document.addKeyword(Field.RELATED_ENTRY, true);
					}
				}
			}
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(document, Field.TITLE, Field.CONTENT);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(MBMessage mbMessage) throws Exception {
		if ((!mbMessage.isApproved() && !mbMessage.isInTrash()) ||
			(mbMessage.isDiscussion() && mbMessage.isRoot())) {

			return;
		}

		Document document = getDocument(mbMessage);

		IndexWriterHelperUtil.updateDocument(
			getSearchEngineId(), mbMessage.getCompanyId(), document,
			isCommitImmediately());

		reindexAttachments(mbMessage);
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		MBMessage message = mbMessageLocalService.getMessage(classPK);

		doReindex(message);

		if (message.isRoot()) {
			List<MBMessage> messages = mbMessageLocalService.getThreadMessages(
				message.getThreadId(), WorkflowConstants.STATUS_APPROVED);

			for (MBMessage curMessage : messages) {
				reindex(curMessage);
			}
		}
		else {
			reindex(message);
		}
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCategories(companyId);
		reindexDiscussions(companyId);
		reindexRoot(companyId);
	}

	protected String processContent(MBMessage message) {
		String content = message.getBody();

		try {
			if (message.isFormatBBCode()) {
				content = BBCodeTranslatorUtil.getHTML(content);
			}
		}
		catch (Exception e) {
			_log.error(
				StringBundler.concat(
					"Unable to parse message ",
					String.valueOf(message.getMessageId()), ": ",
					e.getMessage()),
				e);
		}

		content = HtmlUtil.extractText(content);

		return content;
	}

	protected void reindexAttachments(MBMessage mbMessage)
		throws PortalException {

		Indexer<DLFileEntry> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			DLFileEntry.class);

		for (FileEntry attachmentsFileEntry :
				mbMessage.getAttachmentsFileEntries()) {

			indexer.reindex((DLFileEntry)attachmentsFileEntry.getModel());
		}
	}

	protected void reindexCategories(final long companyId)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			mbCategoryLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<MBCategory>() {

				@Override
				public void performAction(MBCategory category)
					throws PortalException {

					reindexMessages(
						companyId, category.getGroupId(),
						category.getCategoryId());
				}

			});

		actionableDynamicQuery.performActions();
	}

	protected void reindexDiscussions(final long companyId)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			groupLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Group>() {

				@Override
				public void performAction(Group group) throws PortalException {
					reindexMessages(
						companyId, group.getGroupId(),
						MBCategoryConstants.DISCUSSION_CATEGORY_ID);
				}

			});

		actionableDynamicQuery.performActions();
	}

	protected void reindexMessages(
			long companyId, long groupId, final long categoryId)
		throws PortalException {

		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			mbMessageLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property categoryIdProperty = PropertyFactoryUtil.forName(
						"categoryId");

					dynamicQuery.add(categoryIdProperty.eq(categoryId));

					Property statusProperty = PropertyFactoryUtil.forName(
						"status");

					Integer[] statuses = {
						WorkflowConstants.STATUS_APPROVED,
						WorkflowConstants.STATUS_IN_TRASH
					};

					dynamicQuery.add(statusProperty.in(statuses));
				}

			});
		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setGroupId(groupId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<MBMessage>() {

				@Override
				public void performAction(MBMessage message) {
					if (message.isDiscussion() && message.isRoot()) {
						return;
					}

					try {
						Document document = getDocument(message);

						indexableActionableDynamicQuery.addDocuments(document);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index message boards message " +
									message.getMessageId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	protected void reindexRoot(final long companyId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			groupLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Group>() {

				@Override
				public void performAction(Group group) throws PortalException {
					reindexMessages(
						companyId, group.getGroupId(),
						MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);
				}

			});

		actionableDynamicQuery.performActions();
	}

	@Reference
	protected CommentManager commentManager;

	@Reference
	protected GroupLocalService groupLocalService;

	@Reference
	protected MBCategoryLocalService mbCategoryLocalService;

	@Reference
	protected MBCategoryService mbCategoryService;

	@Reference
	protected MBDiscussionLocalService mbDiscussionLocalService;

	@Reference
	protected MBMessageLocalService mbMessageLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		MBMessageIndexer.class);

	@Reference(
		target = "(model.class.name=com.liferay.message.boards.kernel.model.MBMessage)"
	)
	private ModelResourcePermission<MBMessage> _messageModelResourcePermission;

	private final RelatedEntryIndexer _relatedEntryIndexer =
		new BaseRelatedEntryIndexer();

}