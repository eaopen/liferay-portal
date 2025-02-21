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

package com.liferay.osb.faro.contacts.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.ShardedModel;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the ContactsCardTemplate service. Represents a row in the &quot;OSBFaro_ContactsCardTemplate&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.osb.faro.contacts.model.impl.ContactsCardTemplateModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.osb.faro.contacts.model.impl.ContactsCardTemplateImpl</code>.
 * </p>
 *
 * @author Shinn Lok
 * @see ContactsCardTemplate
 * @generated
 */
@ProviderType
public interface ContactsCardTemplateModel
	extends BaseModel<ContactsCardTemplate>, MVCCModel, ShardedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a contacts card template model instance should use the {@link ContactsCardTemplate} interface instead.
	 */

	/**
	 * Returns the primary key of this contacts card template.
	 *
	 * @return the primary key of this contacts card template
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this contacts card template.
	 *
	 * @param primaryKey the primary key of this contacts card template
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the mvcc version of this contacts card template.
	 *
	 * @return the mvcc version of this contacts card template
	 */
	@Override
	public long getMvccVersion();

	/**
	 * Sets the mvcc version of this contacts card template.
	 *
	 * @param mvccVersion the mvcc version of this contacts card template
	 */
	@Override
	public void setMvccVersion(long mvccVersion);

	/**
	 * Returns the contacts card template ID of this contacts card template.
	 *
	 * @return the contacts card template ID of this contacts card template
	 */
	public long getContactsCardTemplateId();

	/**
	 * Sets the contacts card template ID of this contacts card template.
	 *
	 * @param contactsCardTemplateId the contacts card template ID of this contacts card template
	 */
	public void setContactsCardTemplateId(long contactsCardTemplateId);

	/**
	 * Returns the group ID of this contacts card template.
	 *
	 * @return the group ID of this contacts card template
	 */
	public long getGroupId();

	/**
	 * Sets the group ID of this contacts card template.
	 *
	 * @param groupId the group ID of this contacts card template
	 */
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this contacts card template.
	 *
	 * @return the company ID of this contacts card template
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this contacts card template.
	 *
	 * @param companyId the company ID of this contacts card template
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the create time of this contacts card template.
	 *
	 * @return the create time of this contacts card template
	 */
	public long getCreateTime();

	/**
	 * Sets the create time of this contacts card template.
	 *
	 * @param createTime the create time of this contacts card template
	 */
	public void setCreateTime(long createTime);

	/**
	 * Returns the user ID of this contacts card template.
	 *
	 * @return the user ID of this contacts card template
	 */
	public long getUserId();

	/**
	 * Sets the user ID of this contacts card template.
	 *
	 * @param userId the user ID of this contacts card template
	 */
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this contacts card template.
	 *
	 * @return the user uuid of this contacts card template
	 */
	public String getUserUuid();

	/**
	 * Sets the user uuid of this contacts card template.
	 *
	 * @param userUuid the user uuid of this contacts card template
	 */
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this contacts card template.
	 *
	 * @return the user name of this contacts card template
	 */
	@AutoEscape
	public String getUserName();

	/**
	 * Sets the user name of this contacts card template.
	 *
	 * @param userName the user name of this contacts card template
	 */
	public void setUserName(String userName);

	/**
	 * Returns the modified time of this contacts card template.
	 *
	 * @return the modified time of this contacts card template
	 */
	public long getModifiedTime();

	/**
	 * Sets the modified time of this contacts card template.
	 *
	 * @param modifiedTime the modified time of this contacts card template
	 */
	public void setModifiedTime(long modifiedTime);

	/**
	 * Returns the name of this contacts card template.
	 *
	 * @return the name of this contacts card template
	 */
	@AutoEscape
	public String getName();

	/**
	 * Sets the name of this contacts card template.
	 *
	 * @param name the name of this contacts card template
	 */
	public void setName(String name);

	/**
	 * Returns the settings of this contacts card template.
	 *
	 * @return the settings of this contacts card template
	 */
	@AutoEscape
	public String getSettings();

	/**
	 * Sets the settings of this contacts card template.
	 *
	 * @param settings the settings of this contacts card template
	 */
	public void setSettings(String settings);

	/**
	 * Returns the type of this contacts card template.
	 *
	 * @return the type of this contacts card template
	 */
	public int getType();

	/**
	 * Sets the type of this contacts card template.
	 *
	 * @param type the type of this contacts card template
	 */
	public void setType(int type);

	@Override
	public ContactsCardTemplate cloneWithOriginalValues();

	public default String toXmlString() {
		return null;
	}

}