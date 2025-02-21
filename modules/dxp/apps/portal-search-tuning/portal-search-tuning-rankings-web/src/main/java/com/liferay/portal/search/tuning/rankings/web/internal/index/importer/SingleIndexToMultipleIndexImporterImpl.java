/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index.importer;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexCreator;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexReader;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexNameBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wade Cao
 * @author Adam Brandizzi
 */
@Component(service = SingleIndexToMultipleIndexImporter.class)
public class SingleIndexToMultipleIndexImporterImpl
	implements SingleIndexToMultipleIndexImporter {

	@Override
	public void importRankings() {
		try {
			_createRankingIndices();

			_importDocuments();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to create result ranking indices for existing " +
						"companies",
					exception);
			}
		}
	}

	@Override
	public boolean needImport() {
		for (Company company : _companyService.getCompanies()) {
			if (!_rankingIndexReader.isExists(
					_rankingIndexNameBuilder.getRankingIndexName(
						company.getCompanyId()))) {

				return true;
			}
		}

		return _rankingIndexReader.isExists(SINGLE_INDEX_NAME);
	}

	protected static final String RANKINGS_INDEX_NAME_SUFFIX =
		"search-tuning-rankings";

	protected static final RankingIndexName SINGLE_INDEX_NAME =
		() -> "liferay-search-tuning-rankings";

	private boolean _addDocuments(String indexName, List<Document> documents) {
		boolean successed = true;

		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

		documents.forEach(
			document -> {
				IndexDocumentRequest indexDocumentRequest =
					new IndexDocumentRequest(
						_getRankingIndexName(indexName), document);

				bulkDocumentRequest.addBulkableDocumentRequest(
					indexDocumentRequest);
			});

		BulkDocumentResponse bulkDocumentResponse =
			_searchEngineAdapter.execute(bulkDocumentRequest);

		if (bulkDocumentResponse.hasErrors()) {
			successed = false;
		}

		return successed;
	}

	private void _createRankingIndices() {
		for (Company company : _companyService.getCompanies()) {
			RankingIndexName rankingIndexName =
				_rankingIndexNameBuilder.getRankingIndexName(
					company.getCompanyId());

			if (!_rankingIndexReader.isExists(rankingIndexName)) {
				_rankingIndexCreator.create(rankingIndexName);
			}
		}
	}

	private List<Document> _getDocuments(RankingIndexName singleIndexName) {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(singleIndexName.getIndexName());
		searchSearchRequest.setQuery(_queries.matchAll());
		searchSearchRequest.setFetchSource(true);

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		return TransformUtil.transform(
			searchHits.getSearchHits(), SearchHit::getDocument);
	}

	private String _getRankingIndexName(String indexName) {
		return indexName + StringPool.DASH + RANKINGS_INDEX_NAME_SUFFIX;
	}

	private Map<String, List<Document>> _groupDocumentByIndex(
		List<Document> documents) {

		Map<String, List<Document>> documentsMap = new HashMap<>();

		for (Document document : documents) {
			List<Document> curDocuments = documentsMap.computeIfAbsent(
				document.getString("index"), key -> new ArrayList<>());

			curDocuments.add(document);
		}

		return documentsMap;
	}

	private void _importDocuments() {
		if (!_rankingIndexReader.isExists(SINGLE_INDEX_NAME)) {
			return;
		}

		List<Document> documents = _getDocuments(SINGLE_INDEX_NAME);

		if (documents.isEmpty()) {
			return;
		}

		boolean result = true;

		Map<String, List<Document>> documentsMap = _groupDocumentByIndex(
			documents);

		for (Map.Entry<String, List<Document>> entry :
				documentsMap.entrySet()) {

			result = result && _addDocuments(entry.getKey(), entry.getValue());
		}

		if (result) {
			_rankingIndexCreator.delete(SINGLE_INDEX_NAME);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SingleIndexToMultipleIndexImporterImpl.class);

	@Reference
	private CompanyService _companyService;

	@Reference
	private Queries _queries;

	@Reference
	private RankingIndexCreator _rankingIndexCreator;

	@Reference
	private RankingIndexNameBuilder _rankingIndexNameBuilder;

	@Reference
	private RankingIndexReader _rankingIndexReader;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

}