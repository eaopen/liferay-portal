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

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayModal, {useModal} from '@clayui/modal';
import {useMutation} from 'graphql-hooks';
import React, {useContext, useRef} from 'react';

import {AppContext} from '../AppContext.es';
import {createSubTopicQuery, createTopicQuery} from '../utils/client.es';
import lang from '../utils/lang.es';
import {deleteCache} from '../utils/utils.es';

export default function NewTopicModal({
	currentSectionId,
	onClose,
	onCreateNavigateTo,
	setError,
	visible,
}) {
	const context = useContext(AppContext);
	const topicNameRef = useRef(null);
	const topicDescriptionRef = useRef(null);

	const [createNewSubTopic] = useMutation(createSubTopicQuery);

	const [createNewTopic] = useMutation(createTopicQuery);

	const isValidTopic = (topic) => {
		const invalidCharacters = /.*[-|&|'|@|\\\\|\]|}|:|,|=|>|/|<|\n|[|{|||+|#|`|?|\\"|\r|;|/|*|~|%]/g;
		if (invalidCharacters.test(topic)) {
			const error = {
				message: lang.sub(
					Liferay.Language.get(
						'the-x-cannot-contain-the-following-invalid-characters-x'
					),
					[
						Liferay.Language.get('topic-name'),
						' - & \' @ \\\\ ] } : , = > / < \\n [ {  | + # ` ? \\" \\r ; / * ~ %',
					]
				),
			};
			setError(error);

			return false;
		}

		return true;
	};

	const createTopic = () => {
		const topicName = topicNameRef.current.value.trim();
		if (isValidTopic(topicName)) {
			deleteCache();
			if (currentSectionId) {
				createNewSubTopic({
					variables: {
						description: topicDescriptionRef.current.value,
						parentMessageBoardSectionId: currentSectionId,
						title: topicName,
					},
				}).then(
					({
						data: {
							createMessageBoardSectionMessageBoardSection: section,
						},
					}) =>
						onCreateNavigateTo(
							context.useTopicNamesInURL
								? section.title
								: section.id
						)
				);
			}
			else {
				createNewTopic({
					variables: {
						description: topicDescriptionRef.current.value,
						siteKey: context.siteKey,
						title: topicName,
					},
				}).then(({data: {createSiteMessageBoardSection: section}}) =>
					onCreateNavigateTo(
						context.useTopicNamesInURL ? section.title : section.id
					)
				);
			}
		}
	};

	const {observer, onClose: close} = useModal({
		onClose,
	});

	return (
		<>
			{visible && (
				<ClayModal observer={observer} size="lg" status="info">
					<ClayModal.Header>
						{Liferay.Language.get('new-topic')}
					</ClayModal.Header>

					<ClayModal.Body>
						<ClayForm>
							<ClayForm.Group className="form-group-sm">
								<label htmlFor="basicInput">
									{Liferay.Language.get('topic-name')}
								</label>

								<ClayInput
									placeholder={Liferay.Language.get(
										'please-enter-a-valid-topic-name'
									)}
									ref={topicNameRef}
									type="text"
								/>
							</ClayForm.Group>

							<ClayForm.Group className="form-group-sm">
								<label htmlFor="basicInput">
									{Liferay.Language.get('description')}
								</label>

								<ClayInput
									className="form-control"
									component="textarea"
									placeholder={Liferay.Language.get(
										'description'
									)}
									ref={topicDescriptionRef}
								/>
							</ClayForm.Group>
						</ClayForm>
					</ClayModal.Body>

					<ClayModal.Footer
						last={
							<ClayButton.Group spaced>
								<ClayButton
									aria-label={Liferay.Language.get('cancel')}
									displayType="secondary"
									onClick={close}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>

								<ClayButton
									aria-label={Liferay.Language.get('create')}
									displayType="primary"
									onClick={() => {
										createTopic();
										close();
									}}
								>
									{Liferay.Language.get('create')}
								</ClayButton>
							</ClayButton.Group>
						}
					/>
				</ClayModal>
			)}
		</>
	);
}
