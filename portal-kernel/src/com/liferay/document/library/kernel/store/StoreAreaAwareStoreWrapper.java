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

package com.liferay.document.library.kernel.store;

import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.function.Supplier;

/**
 * @author Adolfo Pérez
 */
public class StoreAreaAwareStoreWrapper implements Store {

	public StoreAreaAwareStoreWrapper(
		Supplier<Store> storeSupplier,
		Supplier<StoreAreaProcessor> storeAreaProcessorSupplier) {

		_storeSupplier = storeSupplier;
		_storeAreaProcessorSupplier = storeAreaProcessorSupplier;
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, InputStream inputStream)
		throws PortalException {

		Store store = _storeSupplier.get();

		store.addFile(
			companyId, repositoryId, fileName, versionLabel, inputStream);
	}

	@Override
	public void deleteDirectory(
		long companyId, long repositoryId, String dirName) {

		Store store = _storeSupplier.get();

		if (_isStoreAreaSupported()) {
			for (String fileName :
					store.getFileNames(companyId, repositoryId, dirName)) {

				for (String versionLabel :
						store.getFileVersions(
							companyId, repositoryId, fileName)) {

					_copy(
						StoreArea.DELETED, companyId, repositoryId, fileName,
						versionLabel);
				}
			}
		}

		store.deleteDirectory(companyId, repositoryId, dirName);
	}

	@Override
	public void deleteFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		Store store = _storeSupplier.get();

		if (_isStoreAreaSupported()) {
			_copy(
				StoreArea.DELETED, companyId, repositoryId, fileName,
				versionLabel);
		}

		store.deleteFile(companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		Store store = _storeSupplier.get();

		return store.getFileAsStream(
			companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public String[] getFileNames(
		long companyId, long repositoryId, String dirName) {

		Store store = _storeSupplier.get();

		return store.getFileNames(companyId, repositoryId, dirName);
	}

	@Override
	public long getFileSize(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		Store store = _storeSupplier.get();

		return store.getFileSize(
			companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public String[] getFileVersions(
		long companyId, long repositoryId, String fileName) {

		Store store = _storeSupplier.get();

		return store.getFileVersions(companyId, repositoryId, fileName);
	}

	@Override
	public boolean hasFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		Store store = _storeSupplier.get();

		return store.hasFile(companyId, repositoryId, fileName, versionLabel);
	}

	private void _copy(
		StoreArea storeArea, long companyId, long repositoryId, String fileName,
		String versionLabel) {

		Store store = _storeSupplier.get();

		try (InputStream inputStream = _getNullSafeInputStream(
				store.getFileAsStream(
					companyId, repositoryId, fileName, versionLabel))) {

			StoreArea.withStoreArea(
				storeArea,
				() -> store.addFile(
					companyId, repositoryId, fileName, versionLabel,
					inputStream));
		}
		catch (IOException ioException) {
			throw new SystemException(ioException);
		}
		catch (PortalException portalException) {
			ReflectionUtil.throwException(portalException);
		}
	}

	private InputStream _getNullSafeInputStream(InputStream inputStream) {
		if (inputStream == null) {
			return new UnsyncByteArrayInputStream(new byte[0]);
		}

		return inputStream;
	}

	private boolean _isStoreAreaSupported() {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-174816")) {
			return false;
		}

		StoreAreaProcessor storeAreaProcessor =
			_storeAreaProcessorSupplier.get();

		if (storeAreaProcessor != null) {
			return true;
		}

		return false;
	}

	private final Supplier<StoreAreaProcessor> _storeAreaProcessorSupplier;
	private final Supplier<Store> _storeSupplier;

}