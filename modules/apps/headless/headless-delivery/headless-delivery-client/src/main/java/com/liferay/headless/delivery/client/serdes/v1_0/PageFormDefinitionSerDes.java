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

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.CustomCSSViewport;
import com.liferay.headless.delivery.client.dto.v1_0.FragmentViewport;
import com.liferay.headless.delivery.client.dto.v1_0.PageFormDefinition;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class PageFormDefinitionSerDes {

	public static PageFormDefinition toDTO(String json) {
		PageFormDefinitionJSONParser pageFormDefinitionJSONParser =
			new PageFormDefinitionJSONParser();

		return pageFormDefinitionJSONParser.parseToDTO(json);
	}

	public static PageFormDefinition[] toDTOs(String json) {
		PageFormDefinitionJSONParser pageFormDefinitionJSONParser =
			new PageFormDefinitionJSONParser();

		return pageFormDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(PageFormDefinition pageFormDefinition) {
		if (pageFormDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (pageFormDefinition.getCssClasses() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"cssClasses\": ");

			sb.append("[");

			for (int i = 0; i < pageFormDefinition.getCssClasses().length;
				 i++) {

				sb.append("\"");

				sb.append(_escape(pageFormDefinition.getCssClasses()[i]));

				sb.append("\"");

				if ((i + 1) < pageFormDefinition.getCssClasses().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (pageFormDefinition.getCustomCSS() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customCSS\": ");

			sb.append("\"");

			sb.append(_escape(pageFormDefinition.getCustomCSS()));

			sb.append("\"");
		}

		if (pageFormDefinition.getCustomCSSViewports() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customCSSViewports\": ");

			sb.append("[");

			for (int i = 0;
				 i < pageFormDefinition.getCustomCSSViewports().length; i++) {

				sb.append(
					String.valueOf(
						pageFormDefinition.getCustomCSSViewports()[i]));

				if ((i + 1) <
						pageFormDefinition.getCustomCSSViewports().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (pageFormDefinition.getFormConfig() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"formConfig\": ");

			sb.append(String.valueOf(pageFormDefinition.getFormConfig()));
		}

		if (pageFormDefinition.getFragmentStyle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragmentStyle\": ");

			sb.append(String.valueOf(pageFormDefinition.getFragmentStyle()));
		}

		if (pageFormDefinition.getFragmentViewports() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragmentViewports\": ");

			sb.append("[");

			for (int i = 0;
				 i < pageFormDefinition.getFragmentViewports().length; i++) {

				sb.append(
					String.valueOf(
						pageFormDefinition.getFragmentViewports()[i]));

				if ((i + 1) <
						pageFormDefinition.getFragmentViewports().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (pageFormDefinition.getIndexed() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"indexed\": ");

			sb.append(pageFormDefinition.getIndexed());
		}

		if (pageFormDefinition.getLayout() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"layout\": ");

			sb.append(String.valueOf(pageFormDefinition.getLayout()));
		}

		if (pageFormDefinition.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(pageFormDefinition.getName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PageFormDefinitionJSONParser pageFormDefinitionJSONParser =
			new PageFormDefinitionJSONParser();

		return pageFormDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		PageFormDefinition pageFormDefinition) {

		if (pageFormDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (pageFormDefinition.getCssClasses() == null) {
			map.put("cssClasses", null);
		}
		else {
			map.put(
				"cssClasses",
				String.valueOf(pageFormDefinition.getCssClasses()));
		}

		if (pageFormDefinition.getCustomCSS() == null) {
			map.put("customCSS", null);
		}
		else {
			map.put(
				"customCSS", String.valueOf(pageFormDefinition.getCustomCSS()));
		}

		if (pageFormDefinition.getCustomCSSViewports() == null) {
			map.put("customCSSViewports", null);
		}
		else {
			map.put(
				"customCSSViewports",
				String.valueOf(pageFormDefinition.getCustomCSSViewports()));
		}

		if (pageFormDefinition.getFormConfig() == null) {
			map.put("formConfig", null);
		}
		else {
			map.put(
				"formConfig",
				String.valueOf(pageFormDefinition.getFormConfig()));
		}

		if (pageFormDefinition.getFragmentStyle() == null) {
			map.put("fragmentStyle", null);
		}
		else {
			map.put(
				"fragmentStyle",
				String.valueOf(pageFormDefinition.getFragmentStyle()));
		}

		if (pageFormDefinition.getFragmentViewports() == null) {
			map.put("fragmentViewports", null);
		}
		else {
			map.put(
				"fragmentViewports",
				String.valueOf(pageFormDefinition.getFragmentViewports()));
		}

		if (pageFormDefinition.getIndexed() == null) {
			map.put("indexed", null);
		}
		else {
			map.put("indexed", String.valueOf(pageFormDefinition.getIndexed()));
		}

		if (pageFormDefinition.getLayout() == null) {
			map.put("layout", null);
		}
		else {
			map.put("layout", String.valueOf(pageFormDefinition.getLayout()));
		}

		if (pageFormDefinition.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(pageFormDefinition.getName()));
		}

		return map;
	}

	public static class PageFormDefinitionJSONParser
		extends BaseJSONParser<PageFormDefinition> {

		@Override
		protected PageFormDefinition createDTO() {
			return new PageFormDefinition();
		}

		@Override
		protected PageFormDefinition[] createDTOArray(int size) {
			return new PageFormDefinition[size];
		}

		@Override
		protected void setField(
			PageFormDefinition pageFormDefinition, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "cssClasses")) {
				if (jsonParserFieldValue != null) {
					pageFormDefinition.setCssClasses(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "customCSS")) {
				if (jsonParserFieldValue != null) {
					pageFormDefinition.setCustomCSS(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "customCSSViewports")) {

				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					CustomCSSViewport[] customCSSViewportsArray =
						new CustomCSSViewport[jsonParserFieldValues.length];

					for (int i = 0; i < customCSSViewportsArray.length; i++) {
						customCSSViewportsArray[i] =
							CustomCSSViewportSerDes.toDTO(
								(String)jsonParserFieldValues[i]);
					}

					pageFormDefinition.setCustomCSSViewports(
						customCSSViewportsArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "formConfig")) {
				if (jsonParserFieldValue != null) {
					pageFormDefinition.setFormConfig(
						FormConfigSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fragmentStyle")) {
				if (jsonParserFieldValue != null) {
					pageFormDefinition.setFragmentStyle(
						FragmentStyleSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fragmentViewports")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					FragmentViewport[] fragmentViewportsArray =
						new FragmentViewport[jsonParserFieldValues.length];

					for (int i = 0; i < fragmentViewportsArray.length; i++) {
						fragmentViewportsArray[i] =
							FragmentViewportSerDes.toDTO(
								(String)jsonParserFieldValues[i]);
					}

					pageFormDefinition.setFragmentViewports(
						fragmentViewportsArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "indexed")) {
				if (jsonParserFieldValue != null) {
					pageFormDefinition.setIndexed(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "layout")) {
				if (jsonParserFieldValue != null) {
					pageFormDefinition.setLayout(
						LayoutSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					pageFormDefinition.setName((String)jsonParserFieldValue);
				}
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\": ");

			Object value = entry.getValue();

			Class<?> valueClass = value.getClass();

			if (value instanceof Map) {
				sb.append(_toJSON((Map)value));
			}
			else if (valueClass.isArray()) {
				Object[] values = (Object[])value;

				sb.append("[");

				for (int i = 0; i < values.length; i++) {
					sb.append("\"");
					sb.append(_escape(values[i]));
					sb.append("\"");

					if ((i + 1) < values.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}