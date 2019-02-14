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

package com.liferay.headless.form.dto.v1_0;

import java.util.function.Supplier;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@XmlRootElement(name = "Creator")
public class Creator {

	public String getAdditionalName() {
		return _additionalName;
	}

	public String getAlternateName() {
		return _alternateName;
	}

	public String getEmail() {
		return _email;
	}

	public String getFamilyName() {
		return _familyName;
	}

	public String getGivenName() {
		return _givenName;
	}

	public Long getId() {
		return _id;
	}

	public String getImage() {
		return _image;
	}

	public String getJobTitle() {
		return _jobTitle;
	}

	public String getName() {
		return _name;
	}

	public String getProfileURL() {
		return _profileURL;
	}

	public void setAdditionalName(String additionalName) {
		_additionalName = additionalName;
	}

	public void setAdditionalName(Supplier<String> additionalNameSupplier) {
		_additionalName = additionalNameSupplier.get();
	}

	public void setAlternateName(String alternateName) {
		_alternateName = alternateName;
	}

	public void setAlternateName(Supplier<String> alternateNameSupplier) {
		_alternateName = alternateNameSupplier.get();
	}

	public void setEmail(String email) {
		_email = email;
	}

	public void setEmail(Supplier<String> emailSupplier) {
		_email = emailSupplier.get();
	}

	public void setFamilyName(String familyName) {
		_familyName = familyName;
	}

	public void setFamilyName(Supplier<String> familyNameSupplier) {
		_familyName = familyNameSupplier.get();
	}

	public void setGivenName(String givenName) {
		_givenName = givenName;
	}

	public void setGivenName(Supplier<String> givenNameSupplier) {
		_givenName = givenNameSupplier.get();
	}

	public void setId(Long id) {
		_id = id;
	}

	public void setId(Supplier<Long> idSupplier) {
		_id = idSupplier.get();
	}

	public void setImage(String image) {
		_image = image;
	}

	public void setImage(Supplier<String> imageSupplier) {
		_image = imageSupplier.get();
	}

	public void setJobTitle(String jobTitle) {
		_jobTitle = jobTitle;
	}

	public void setJobTitle(Supplier<String> jobTitleSupplier) {
		_jobTitle = jobTitleSupplier.get();
	}

	public void setName(String name) {
		_name = name;
	}

	public void setName(Supplier<String> nameSupplier) {
		_name = nameSupplier.get();
	}

	public void setProfileURL(String profileURL) {
		_profileURL = profileURL;
	}

	public void setProfileURL(Supplier<String> profileURLSupplier) {
		_profileURL = profileURLSupplier.get();
	}

	private String _additionalName;
	private String _alternateName;
	private String _email;
	private String _familyName;
	private String _givenName;
	private Long _id;
	private String _image;
	private String _jobTitle;
	private String _name;
	private String _profileURL;

}