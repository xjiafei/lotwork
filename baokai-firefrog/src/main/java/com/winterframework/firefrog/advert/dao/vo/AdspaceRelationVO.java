/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.advert.dao.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class AdspaceRelationVO extends BaseEntity {

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/
	private static final long serialVersionUID = -987867551L;
	//alias
	public static final String TABLE_ALIAS = "AdAdspaceRelation";
	public static final String ALIAS_AD_ID = "广告id";
	public static final String ALIAS_SPACE_ID = "广告位id";
	public static final String ALIAS_ORDERS = "某广告在某广告位中的次序";

	//date formats

	//columns START
	private Long adId;
	private Long spaceId;
	private Long orders;
	private Long isShown;

	//columns END

	public AdspaceRelationVO() {
	}

	public AdspaceRelationVO(Long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("AdId", getAdId())
				.append("SpaceId", getSpaceId()).append("Orders", getOrders()).append("isShown", this.getIsShown())
				.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getAdId()).append(getSpaceId()).append(getOrders())
				.append(getIsShown()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AdspaceRelationVO == false)
			return false;
		if (this == obj)
			return true;
		AdspaceRelationVO other = (AdspaceRelationVO) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getAdId(), other.getAdId())

		.append(getSpaceId(), other.getSpaceId())

		.append(getOrders(), other.getOrders()).append(getIsShown(), other.getIsShown())

		.isEquals();
	}

	public Long getAdId() {
		return adId;
	}

	public void setAdId(Long adId) {
		this.adId = adId;
	}

	public Long getSpaceId() {
		return spaceId;
	}

	public void setSpaceId(Long spaceId) {
		this.spaceId = spaceId;
	}

	public Long getOrders() {
		return orders;
	}

	public void setOrders(Long orders) {
		this.orders = orders;
	}

	public Long getIsShown() {
		return isShown;
	}

	public void setIsShown(Long isShown) {
		this.isShown = isShown;
	}
}
