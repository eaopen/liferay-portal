/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {useId} from '@liferay/layout-content-page-editor-web';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

function _isValueValid(value) {
	const noSpacesValue = value.replace(/\s/g, '');

	return !!noSpacesValue;
}

function ValidatedInput({
	autofocus = false,
	errorMessage,
	label,
	onBlur = () => {},
	onChange = () => {},
	onFocus = () => {},
	onValidationChange = () => {},
	value = '',
}) {
	const inputId = useId();
	const [invalid, setInvalid] = useState(false);
	const nodeRef = useRef();

	useEffect(() => {
		if (nodeRef.current && autofocus) {
			nodeRef.current.focus();
		}
	}, [autofocus]);

	return (
		<ClayForm.Group className={invalid ? 'has-error' : ''}>
			{label && (
				<>
					<label htmlFor={inputId}>{label}</label>
					<ClayIcon
						className="lexicon-icon-sm ml-1 reference-mark text-warning"
						style={{verticalAlign: 'super'}}
						symbol="asterisk"
					/>
				</>
			)}

			<ClayInput
				id={inputId}
				maxLength="75"
				onBlur={_handleNameInputBlur}
				onChange={onChange}
				onFocus={_handleNameInputFocus}
				ref={nodeRef}
				type="text"
				value={value}
			/>

			{invalid && errorMessage && (
				<ClayForm.FeedbackGroup>
					<ClayForm.FeedbackItem>
						<ClayForm.FeedbackIndicator symbol="exclamation-full" />

						{errorMessage}
					</ClayForm.FeedbackItem>
				</ClayForm.FeedbackGroup>
			)}
		</ClayForm.Group>
	);

	function _handleNameInputBlur(event) {
		if (!_isValueValid(value)) {
			_setInvalid(true);
		}
		onBlur(event);
	}
	function _handleNameInputFocus(event) {
		_setInvalid(false);
		onFocus(event);
	}

	function _setInvalid(newInvalid) {
		setInvalid(newInvalid);
		if (newInvalid !== invalid) {
			onValidationChange(newInvalid);
		}
	}
}

ValidatedInput.propTypes = {
	autofocus: PropTypes.bool,
	errorMessage: PropTypes.string,
	label: PropTypes.string,
	onBlur: PropTypes.func,
	onChange: PropTypes.func,
	onFocus: PropTypes.func,
	onValidationChange: PropTypes.func,
	value: PropTypes.string,
};

export default ValidatedInput;
