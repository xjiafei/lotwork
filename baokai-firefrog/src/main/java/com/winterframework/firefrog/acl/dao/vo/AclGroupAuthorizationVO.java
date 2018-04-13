/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.acl.dao.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class AclGroupAuthorizationVO extends BaseEntity {
	private static final long serialVersionUID = -657764334331L;
	//alias
	public static final String TABLE_ALIAS = "AclGroupAuthorization";
	public static final String ALIAS_GID = "groupid";
	public static final String ALIAS_ACL_ID = "权限id";

	//date formats

	//columns START
	private Long gid;
	private Long aclId;

	//columns END

	public AclGroupAuthorizationVO() {
	}

	public AclGroupAuthorizationVO(Long id) {
		this.id = id;
	}

	public void setGid(Long value) {
		this.gid = value;
	}

	public Long getGid() {
		return this.gid;
	}

	public void setAclId(Long value) {
		this.aclId = value;
	}

	public Long getAclId() {
		return this.aclId;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Gid", getGid()).append("AclId", getAclId())
				.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getGid()).append(getAclId()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AclGroupAuthorizationVO == false)
			return false;
		if (this == obj)
			return true;
		AclGroupAuthorizationVO other = (AclGroupAuthorizationVO) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getGid(), other.getGid())

		.append(getAclId(), other.getAclId())

		.isEquals();
	}
}
