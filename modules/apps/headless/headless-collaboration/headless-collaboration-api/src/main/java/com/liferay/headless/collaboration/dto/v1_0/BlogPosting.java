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

package com.liferay.headless.collaboration.dto.v1_0;

import java.util.Date;
import java.util.function.Supplier;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@XmlRootElement(name = "BlogPosting")
public class BlogPosting {

	public AggregateRating getAggregateRating() {
		return _aggregateRating;
	}

	public String getAlternativeHeadline() {
		return _alternativeHeadline;
	}

	public String getArticleBody() {
		return _articleBody;
	}

	public String getCaption() {
		return _caption;
	}

	public Long[] getCategory() {
		return _category;
	}

	public Comment[] getComment() {
		return _comment;
	}

	public Long getContentSpace() {
		return _contentSpace;
	}

	public Creator getCreator() {
		return _creator;
	}

	public Date getDateCreated() {
		return _dateCreated;
	}

	public Date getDateModified() {
		return _dateModified;
	}

	public Date getDatePublished() {
		return _datePublished;
	}

	public String getDescription() {
		return _description;
	}

	public String getEncodingFormat() {
		return _encodingFormat;
	}

	public String getFriendlyUrlPath() {
		return _friendlyUrlPath;
	}

	public String getHeadline() {
		return _headline;
	}

	public Long getId() {
		return _id;
	}

	public ImageObject getImage() {
		return _image;
	}

	public Long getImageId() {
		return _imageId;
	}

	public String[] getKeywords() {
		return _keywords;
	}

	public ImageObjectRepository getRepository() {
		return _repository;
	}

	public Long getRepositoryId() {
		return _repositoryId;
	}

	public void setAggregateRating(AggregateRating aggregateRating) {
		_aggregateRating = aggregateRating;
	}

	public void setAggregateRating(
		Supplier<AggregateRating> aggregateRatingSupplier) {

		_aggregateRating = aggregateRatingSupplier.get();
	}

	public void setAlternativeHeadline(String alternativeHeadline) {
		_alternativeHeadline = alternativeHeadline;
	}

	public void setAlternativeHeadline(
		Supplier<String> alternativeHeadlineSupplier) {

		_alternativeHeadline = alternativeHeadlineSupplier.get();
	}

	public void setArticleBody(String articleBody) {
		_articleBody = articleBody;
	}

	public void setArticleBody(Supplier<String> articleBodySupplier) {
		_articleBody = articleBodySupplier.get();
	}

	public void setCaption(String caption) {
		_caption = caption;
	}

	public void setCaption(Supplier<String> captionSupplier) {
		_caption = captionSupplier.get();
	}

	public void setCategory(Long[] category) {
		_category = category;
	}

	public void setCategory(Supplier<Long[]> categorySupplier) {
		_category = categorySupplier.get();
	}

	public void setComment(Comment[] comment) {
		_comment = comment;
	}

	public void setComment(Supplier<Comment[]> commentSupplier) {
		_comment = commentSupplier.get();
	}

	public void setContentSpace(Long contentSpace) {
		_contentSpace = contentSpace;
	}

	public void setContentSpace(Supplier<Long> contentSpaceSupplier) {
		_contentSpace = contentSpaceSupplier.get();
	}

	public void setCreator(Creator creator) {
		_creator = creator;
	}

	public void setCreator(Supplier<Creator> creatorSupplier) {
		_creator = creatorSupplier.get();
	}

	public void setDateCreated(Date dateCreated) {
		_dateCreated = dateCreated;
	}

	public void setDateCreated(Supplier<Date> dateCreatedSupplier) {
		_dateCreated = dateCreatedSupplier.get();
	}

	public void setDateModified(Date dateModified) {
		_dateModified = dateModified;
	}

	public void setDateModified(Supplier<Date> dateModifiedSupplier) {
		_dateModified = dateModifiedSupplier.get();
	}

	public void setDatePublished(Date datePublished) {
		_datePublished = datePublished;
	}

	public void setDatePublished(Supplier<Date> datePublishedSupplier) {
		_datePublished = datePublishedSupplier.get();
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setDescription(Supplier<String> descriptionSupplier) {
		_description = descriptionSupplier.get();
	}

	public void setEncodingFormat(String encodingFormat) {
		_encodingFormat = encodingFormat;
	}

	public void setEncodingFormat(Supplier<String> encodingFormatSupplier) {
		_encodingFormat = encodingFormatSupplier.get();
	}

	public void setFriendlyUrlPath(String friendlyUrlPath) {
		_friendlyUrlPath = friendlyUrlPath;
	}

	public void setFriendlyUrlPath(Supplier<String> friendlyUrlPathSupplier) {
		_friendlyUrlPath = friendlyUrlPathSupplier.get();
	}

	public void setHeadline(String headline) {
		_headline = headline;
	}

	public void setHeadline(Supplier<String> headlineSupplier) {
		_headline = headlineSupplier.get();
	}

	public void setId(Long id) {
		_id = id;
	}

	public void setId(Supplier<Long> idSupplier) {
		_id = idSupplier.get();
	}

	public void setImage(ImageObject image) {
		_image = image;
	}

	public void setImage(Supplier<ImageObject> imageSupplier) {
		_image = imageSupplier.get();
	}

	public void setImageId(Long imageId) {
		_imageId = imageId;
	}

	public void setImageId(Supplier<Long> imageIdSupplier) {
		_imageId = imageIdSupplier.get();
	}

	public void setKeywords(String[] keywords) {
		_keywords = keywords;
	}

	public void setKeywords(Supplier<String[]> keywordsSupplier) {
		_keywords = keywordsSupplier.get();
	}

	public void setRepository(ImageObjectRepository repository) {
		_repository = repository;
	}

	public void setRepository(
		Supplier<ImageObjectRepository> repositorySupplier) {

		_repository = repositorySupplier.get();
	}

	public void setRepositoryId(Long repositoryId) {
		_repositoryId = repositoryId;
	}

	public void setRepositoryId(Supplier<Long> repositoryIdSupplier) {
		_repositoryId = repositoryIdSupplier.get();
	}

	private AggregateRating _aggregateRating;
	private String _alternativeHeadline;
	private String _articleBody;
	private String _caption;
	private Long[] _category;
	private Comment[] _comment;
	private Long _contentSpace;
	private Creator _creator;
	private Date _dateCreated;
	private Date _dateModified;
	private Date _datePublished;
	private String _description;
	private String _encodingFormat;
	private String _friendlyUrlPath;
	private String _headline;
	private Long _id;
	private ImageObject _image;
	private Long _imageId;
	private String[] _keywords;
	private ImageObjectRepository _repository;
	private Long _repositoryId;

}