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

import {delegate, getOpener, sub} from 'frontend-js-web';

export default function ({
	layoutItemSelectorUrl,
	layoutPageTemplateEntryItemSelectorUrl,
	portletNamespace,
	productItemSelectorUrl,
	removeIcon,
	searchContainerId,
}) {
	const openerWindow = getOpener();

	const initProductSelection = (searchContainer) => {
		const selectProductButton = document.getElementById(
			`${portletNamespace}selectProduct`
		);

		if (!selectProductButton) {
			return;
		}

		selectProductButton.addEventListener('click', (event) => {
			event.preventDefault();

			openerWindow.Liferay.Util.openSelectionModal({
				onSelect: (selectedItem) => {
					if (!selectedItem) {
						return;
					}

					const searchContainerData = searchContainer.getData();

					const link = document.querySelector(
						`a[data-rowid="${searchContainerData}"]`
					);

					if (link) {
						const row = link.closest('tr');

						if (row) {
							searchContainer.deleteRow(row, link.dataset.rowid);
						}
					}

					const rowColumns = [];

					rowColumns.push(selectedItem.name);
					rowColumns.push(
						`<a class="float-right modify-link" data-rowId="${selectedItem.id}" href="javascript:void(0);">${removeIcon}</a>`
					);

					const classPKInput = document.getElementById(
						`${portletNamespace}classPK`
					);

					if (classPKInput) {
						classPKInput.value = selectedItem.id;
					}

					searchContainer.addRow(rowColumns, selectedItem.id);

					searchContainer.updateDataStore();
				},
				selectEventName: 'productDefinitionsSelectItem',
				title: sub(
					Liferay.Language.get('select-x'),
					Liferay.Language.get('product')
				),
				url: productItemSelectorUrl,
			});
		});

		const searchContainerContentBox = searchContainer.get('contentBox');

		delegate(
			searchContainerContentBox.getDOMNode(),
			'click',
			'.modify-link',
			(event) => {
				const link = event.delegateTarget;

				const row = link.closest('tr');

				searchContainer.deleteRow(row, link.dataset.rowid);

				const siteGroupInput = document.getElementById(
					`${portletNamespace}classPK`
				);

				if (siteGroupInput) {
					siteGroupInput.value = 0;
				}
			}
		);
	};

	Liferay.componentReady(`${portletNamespace}${searchContainerId}`).then(
		(searchContainer) => {
			initProductSelection(searchContainer);
		}
	);

	const initDisplayPageSelection = () => {
		const chooseLayoutButton = document.getElementById(
			`${portletNamespace}chooseLayout`
		);
		const chooseLayoutPageTemplateEntryButton = document.getElementById(
			`${portletNamespace}chooseLayoutPageTemplateEntry`
		);
		const displayPageItemRemoveIcon = document.getElementById(
			`${portletNamespace}displayPageItemRemove`
		);
		const pagesContainerInput = document.getElementById(
			`${portletNamespace}pagesContainerInput`
		);
		const displayPageNameInput = document.getElementById(
			`${portletNamespace}displayPageNameInput`
		);

		if (
			(!chooseLayoutButton && !chooseLayoutPageTemplateEntryButton) ||
			!displayPageItemRemoveIcon ||
			!pagesContainerInput ||
			!displayPageNameInput
		) {
			return;
		}

		if (chooseLayoutButton) {
			chooseLayoutButton.addEventListener('click', () => {
				openerWindow.Liferay.Util.openSelectionModal({
					buttonAddLabel: Liferay.Language.get('done'),
					multiple: true,
					onSelect: (selectedItem) => {
						if (!selectedItem) {
							return;
						}

						pagesContainerInput.value = selectedItem.id;

						displayPageNameInput.innerHTML = selectedItem.name;

						displayPageItemRemoveIcon.classList.remove('hide');
					},
					selectEventName: 'selectLayout',
					title: Liferay.Language.get('select-product-display-page'),
					url: layoutItemSelectorUrl,
				});
			});
		}

		if (chooseLayoutPageTemplateEntryButton) {
			chooseLayoutPageTemplateEntryButton.addEventListener(
				'click',
				() => {
					openerWindow.Liferay.Util.openSelectionModal({
						buttonAddLabel: Liferay.Language.get('done'),
						onSelect: (selectedItem) => {
							if (!selectedItem) {
								return;
							}

							pagesContainerInput.value = selectedItem.id;

							displayPageNameInput.innerHTML = selectedItem.name;

							displayPageItemRemoveIcon.classList.remove('hide');
						},
						selectEventName: 'selectLayoutPageTemplateEntry',
						title: Liferay.Language.get(
							'select-product-display-page'
						),
						url: layoutPageTemplateEntryItemSelectorUrl,
					});
				}
			);
		}

		displayPageItemRemoveIcon.addEventListener('click', () => {
			displayPageNameInput.innerHTML = Liferay.Language.get('none');

			pagesContainerInput.value = '';

			displayPageItemRemoveIcon.classList.add('hide');
		});
	};

	initDisplayPageSelection();
}
