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

package com.liferay.object.internal.field.business.type;

import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.business.type.ObjectFieldBusinessTypeRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Marcela Cunha
 */
@Component(service = ObjectFieldBusinessTypeRegistry.class)
public class ObjectFieldBusinessTypeRegistryImpl
	implements ObjectFieldBusinessTypeRegistry {

	@Override
	public ObjectFieldBusinessType getObjectFieldBusinessType(String key) {
		ServiceTrackerMap<String, ObjectFieldBusinessType> serviceTrackerMap =
			_getServiceTrackerMap();

		return serviceTrackerMap.getService(key);
	}

	@Override
	public List<ObjectFieldBusinessType> getObjectFieldBusinessTypes() {
		ServiceTrackerMap<String, ObjectFieldBusinessType> serviceTrackerMap =
			_getServiceTrackerMap();

		return new ArrayList(serviceTrackerMap.values());
	}

	@Override
	public Set<String> getObjectFieldDBTypes() {
		Set<String> objectFieldDBTypes = new HashSet<>();

		ServiceTrackerMap<String, ObjectFieldBusinessType> serviceTrackerMap =
			_getServiceTrackerMap();

		for (ObjectFieldBusinessType objectFieldBusinessType :
				serviceTrackerMap.values()) {

			objectFieldDBTypes.add(objectFieldBusinessType.getDBType());
		}

		return objectFieldDBTypes;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceTrackerMap != null) {
			_serviceTrackerMap.close();
		}
	}

	private ServiceTrackerMap<String, ObjectFieldBusinessType>
		_getServiceTrackerMap() {

		if (_serviceTrackerMap != null) {
			return _serviceTrackerMap;
		}

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			_bundleContext, ObjectFieldBusinessType.class,
			"object.field.business.type.key");

		return _serviceTrackerMap;
	}

	private BundleContext _bundleContext;
	private ServiceTrackerMap<String, ObjectFieldBusinessType>
		_serviceTrackerMap;

}