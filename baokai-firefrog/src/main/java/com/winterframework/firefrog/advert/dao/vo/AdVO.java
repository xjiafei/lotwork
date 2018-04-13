/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.advert.dao.vo;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class AdVO extends BaseEntity {
	//alias
	public static final String TABLE_ALIAS = "Ad";
	public static final String ALIAS_SPACES = "广告位";
	public static final String ALIAS_NAME = "广告名称";
	public static final String ALIAS_IMG_URL = "图片地址";
	public static final String ALIAS_GMT_EFFECT_BEGIN = "有效期开始";
	public static final String ALIAS_GMT_EFFECT_END = "有效期结束";
	public static final String ALIAS_TARGET_URL = "链接地址";
	public static final String ALIAS_STATUS = "1)已提交审核  2）审核通过 3）审核未通过 ";
	public static final String ALIAS_MEMO = "审核失败原因";
	public static final String ALIAS_APPROVER = "审批人";
	public static final String ALIAS_GMT_APPR = "审批时间";
	public static final String ALIAS_RECEIVERS = "[top_agent,other_agent,vip,non_vip,passanger]";
	public static final String ALIAS_ORDERS = "次序，用于同一广告位显示";
	public static final String ALIAS_RC_ALL = "全部站内用户";
	public static final String ALIAS_RC_GUEST = "游客";
	public static final String ALIAS_RC_TOP_AGENT = "总代";
	public static final String ALIAS_RC_OT_AGENT = "其他代理";
	public static final String ALIAS_RC_VIP = "vip";
	public static final String ALIAS_RC_NON_VIP = "非vip";
	public static final String ALIAS_RC_CUSTOMER = "普通客户";
	//date formats
	//columns START
	private Long spaces;
	private String name;
	private String imgUrl;
	private Date gmtEffectBegin;
	private Date gmtEffectEnd;
	private String targetUrl;
	private Long status;
	private String memo;
	private String approver;
	private Date gmtAppr;
	private String receivers;
	private Long orders;
	private Long rcAll;
	private Long rcGuest;
	private Long rcTopAgent;
	private Long rcOtAgent;
	private Long rcVip;
	private Long rcNonVip;
	private Long rcCustomer;
	private String operator;

	//columns END

	public AdVO() {
	}

	public AdVO(Long id) {
		this.id = id;
	}

	public void setSpaces(Long value) {
		this.spaces = value;
	}

	public Long getSpaces() {
		return this.spaces;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return this.name;
	}

	public void setImgUrl(String value) {
		this.imgUrl = value;
	}

	public String getImgUrl() {
		return this.imgUrl;
	}

	public void setGmtEffectBegin(Date value) {
		this.gmtEffectBegin = value;
	}

	public Date getGmtEffectBegin() {
		return this.gmtEffectBegin;
	}

	public void setGmtEffectEnd(Date value) {
		this.gmtEffectEnd = value;
	}

	public Date getGmtEffectEnd() {
		return this.gmtEffectEnd;
	}

	public void setTargetUrl(String value) {
		this.targetUrl = value;
	}

	public String getTargetUrl() {
		return this.targetUrl;
	}

	public void setStatus(Long value) {
		this.status = value;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setMemo(String value) {
		this.memo = value;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setApprover(String value) {
		this.approver = value;
	}

	public String getApprover() {
		return this.approver;
	}

	public void setGmtAppr(Date value) {
		this.gmtAppr = value;
	}

	public Date getGmtAppr() {
		return this.gmtAppr;
	}

	public void setReceivers(String value) {
		this.receivers = value;
	}

	public String getReceivers() {
		return this.receivers;
	}

	public void setOrders(Long value) {
		this.orders = value;
	}

	public Long getOrders() {
		return this.orders;
	}

	public void setRcAll(Long value) {
		this.rcAll = value;
	}

	public Long getRcAll() {
		return this.rcAll;
	}

	public void setRcGuest(Long value) {
		this.rcGuest = value;
	}

	public Long getRcGuest() {
		return this.rcGuest;
	}

	public void setRcTopAgent(Long value) {
		this.rcTopAgent = value;
	}

	public Long getRcTopAgent() {
		return this.rcTopAgent;
	}

	public void setRcOtAgent(Long value) {
		this.rcOtAgent = value;
	}

	public Long getRcOtAgent() {
		return this.rcOtAgent;
	}

	public void setRcVip(Long value) {
		this.rcVip = value;
	}

	public Long getRcVip() {
		return this.rcVip;
	}

	public void setRcNonVip(Long value) {
		this.rcNonVip = value;
	}

	public Long getRcNonVip() {
		return this.rcNonVip;
	}

	public void setRcCustomer(Long value) {
		this.rcCustomer = value;
	}

	public Long getRcCustomer() {
		return this.rcCustomer;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Spaces", getSpaces()).append("Name", getName())
				.append("ImgUrl", getImgUrl()).append("GmtEffectBegin", getGmtEffectBegin())
				.append("GmtEffectEnd", getGmtEffectEnd()).append("TargetUrl", getTargetUrl())
				.append("Status", getStatus()).append("Memo", getMemo()).append("Approver", getApprover())
				.append("GmtAppr", getGmtAppr()).append("Receivers", getReceivers()).append("Orders", getOrders())
				.append("RcAll", getRcAll()).append("RcGuest", getRcGuest()).append("RcTopAgent", getRcTopAgent())
				.append("RcOtAgent", getRcOtAgent()).append("RcVip", getRcVip()).append("RcNonVip", getRcNonVip())
				.append("RcCustomer", getRcCustomer()).append("operator", this.getOperator()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getSpaces()).append(getName()).append(getImgUrl())
				.append(getGmtEffectBegin()).append(getGmtEffectEnd()).append(getTargetUrl()).append(getStatus())
				.append(getMemo()).append(getApprover()).append(getGmtAppr()).append(getReceivers())
				.append(getOrders()).append(getRcAll()).append(getRcGuest()).append(getRcTopAgent())
				.append(getRcOtAgent()).append(getRcVip()).append(getRcNonVip()).append(getRcCustomer())
				.append(this.getOperator()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AdVO == false)
			return false;
		if (this == obj)
			return true;
		AdVO other = (AdVO) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getSpaces(), other.getSpaces())

		.append(getName(), other.getName())

		.append(getImgUrl(), other.getImgUrl())

		.append(getGmtEffectBegin(), other.getGmtEffectBegin())

		.append(getGmtEffectEnd(), other.getGmtEffectEnd())

		.append(getTargetUrl(), other.getTargetUrl())

		.append(getStatus(), other.getStatus())

		.append(getMemo(), other.getMemo())

		.append(getApprover(), other.getApprover())

		.append(getGmtAppr(), other.getGmtAppr())

		.append(getReceivers(), other.getReceivers())

		.append(getOrders(), other.getOrders())

		.append(getRcAll(), other.getRcAll())

		.append(getRcGuest(), other.getRcGuest())

		.append(getRcTopAgent(), other.getRcTopAgent())

		.append(getRcOtAgent(), other.getRcOtAgent())

		.append(getRcVip(), other.getRcVip())

		.append(getRcNonVip(), other.getRcNonVip())

		.append(getRcCustomer(), other.getRcCustomer()).append(this.getOperator(), other.getOperator())

		.isEquals();
	}
}
