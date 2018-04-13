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

public class AdNoticeVO extends BaseEntity {

	private static final long serialVersionUID = 2773740363067668836L;
	//alias
	public static final String TABLE_ALIAS = "AdNotice";
	public static final String ALIAS_TYPE = "1)紧急 2）普通";
	public static final String ALIAS_TITLE = "标题";
	public static final String ALIAS_CONTENT = "内容";
	public static final String ALIAS_GMT_EFFECT_BEGIN = "生效开始时间";
	public static final String ALIAS_GMT_EFFECT_END = "生效结束时间";
	public static final String ALIAS_OPERATOR = "创建人";
	public static final String ALIAS_SHOW_PAGES = "show 页面";
	public static final String ALIAS_STATUS = "1）草稿（待审核）2）审核中 3）已通过4）已拒绝  5)已删除";
	public static final String ALIAS_RC_ALL = "rcAll";
	public static final String ALIAS_RC_TOP_AGENT = "rcTopAgent";
	public static final String ALIAS_RC_OT_AGENT = "rcOtAgent";
	public static final String ALIAS_RC_VIP = "rcVip";
	public static final String ALIAS_RC_NON_VIP = "rcNonVip";
	public static final String ALIAS_RC_CUSTOMER = "rcCustomer";

	//date formats

	//columns START
	private Long type;
	private String title;
	private String content;
	private Date gmtEffectBegin;
	private Date gmtEffectEnd;
	private String operator;
	private String showPages;
	private Long status;
	private Long rcAll;
	private Long rcTopAgent;
	private Long rcOtAgent;
	private Long rcVip;
	private Long rcNonVip;
	private Long rcCustomer;
	private Long periodStatus;
	private String memo;
	private String approver;
	private Long isAudit;
	private Long frontCall;
	private Long noticeLevel;
	//columns END

	public AdNoticeVO() {
	}

	public Long getFrontCall() {
		return frontCall;
	}

	public void setFrontCall(Long frontCall) {
		this.frontCall = frontCall;
	}

	
	

	
	/**
	 * @return the noticeLevel
	 */
	public Long getNoticeLevel() {
		return noticeLevel;
	}

	/**
	 * @param noticeLevel the noticeLevel to set
	 */
	public void setNoticeLevel(Long noticeLevel) {
		this.noticeLevel = noticeLevel;
	}

	public AdNoticeVO(Long id) {
		this.id = id;
	}

	public void setType(Long value) {
		this.type = value;
	}

	public Long getType() {
		return this.type;
	}

	public void setTitle(String value) {
		this.title = value;
	}

	public String getTitle() {
		return this.title;
	}

	public void setContent(String value) {
		this.content = value;
	}

	public String getContent() {
		return this.content;
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

	public void setOperator(String value) {
		this.operator = value;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setShowPages(String value) {
		this.showPages = value;
	}

	public String getShowPages() {
		return this.showPages;
	}

	public void setStatus(Long value) {
		this.status = value;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setRcAll(Long value) {
		this.rcAll = value;
	}

	public Long getRcAll() {
		return this.rcAll;
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

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Type", getType()).append("Title", getTitle())
				.append("Content", getContent()).append("GmtEffectBegin", getGmtEffectBegin())
				.append("GmtEffectEnd", getGmtEffectEnd()).append("Operator", getOperator())
				.append("GmtCreated", getGmtCreated()).append("ShowPages", getShowPages())
				.append("Status", getStatus()).append("RcAll", getRcAll())
				.append("RcTopAgent", getRcTopAgent()).append("RcOtAgent", getRcOtAgent()).append("RcVip", getRcVip())
				.append("RcNonVip", getRcNonVip()).append("RcCustomer", getRcCustomer()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getType()).append(getTitle()).append(getContent())
				.append(getGmtEffectBegin()).append(getGmtEffectEnd()).append(getOperator()).append(getGmtCreated())
				.append(getShowPages()).append(getStatus()).append(getRcAll())
				.append(getRcTopAgent()).append(getRcOtAgent()).append(getRcVip()).append(getRcNonVip())
				.append(getRcCustomer()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AdNoticeVO == false)
			return false;
		if (this == obj)
			return true;
		AdNoticeVO other = (AdNoticeVO) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getType(), other.getType())

		.append(getTitle(), other.getTitle())

		.append(getContent(), other.getContent())

		.append(getGmtEffectBegin(), other.getGmtEffectBegin())

		.append(getGmtEffectEnd(), other.getGmtEffectEnd())

		.append(getOperator(), other.getOperator())

		.append(getGmtCreated(), other.getGmtCreated())

		.append(getShowPages(), other.getShowPages())

		.append(getStatus(), other.getStatus())

		.append(getRcAll(), other.getRcAll())

		.append(getRcTopAgent(), other.getRcTopAgent())

		.append(getRcOtAgent(), other.getRcOtAgent())

		.append(getRcVip(), other.getRcVip())

		.append(getRcNonVip(), other.getRcNonVip())

		.append(getRcCustomer(), other.getRcCustomer())

		.isEquals();
	}

	public Long getPeriodStatus() {
		return periodStatus;
	}

	public void setPeriodStatus(Long periodStatus) {
		this.periodStatus = periodStatus;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public Long getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(Long isAudit) {
		this.isAudit = isAudit;
	}
}
