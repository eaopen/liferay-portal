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
@XmlRootElement(name = "Columns")
public class Columns {

	public Long getId() {
		return _id;
	}

	public String getLabel() {
		return _label;
	}

	public String getValue() {
		return _value;
	}

	public void setId(Long id) {
		_id = id;
	}

	public void setId(Supplier<Long> idSupplier) {
		_id = idSupplier.get();
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setLabel(Supplier<String> labelSupplier) {
		_label = labelSupplier.get();
	}

	public void setValue(String value) {
		_value = value;
	}

	public void setValue(Supplier<String> valueSupplier) {
		_value = valueSupplier.get();
	}

	private Long _id;
	private String _label;
	private String _value;

}