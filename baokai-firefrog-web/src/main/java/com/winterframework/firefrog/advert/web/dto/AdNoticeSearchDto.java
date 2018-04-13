package com.winterframework.firefrog.advert.web.dto;

import java.io.Serializable;

public class AdNoticeSearchDto implements Serializable {

	private static final long serialVersionUID = 8081003019465504339L;

	private String title;

	private Long type;

	private Long status;

	private Long periodStatus;

	private Long rcTopAgent;

	private Long rcOtAgent;

	private Long rcVip;

	private Long rcNonVip;

	private Long rcCustomer;
	
	private Long pageNo;
	
	private String orderBy;
	
	private Long isAudit;
	
	private Long rcAll;
	
	private Long frontCall;
	
	private Long userId;
	
	private Long noticeLevel;

	
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getPeriodStatus() {
		return periodStatus;
	}

	public void setPeriodStatus(Long periodStatus) {
		this.periodStatus = periodStatus;
	}

	public Long getRcTopAgent() {
		return rcTopAgent;
	}

	public void setRcTopAgent(Long rcTopAgent) {
		this.rcTopAgent = rcTopAgent;
	}

	public Long getRcOtAgent() {
		return rcOtAgent;
	}

	public void setRcOtAgent(Long rcOtAgent) {
		this.rcOtAgent = rcOtAgent;
	}

	public Long getRcVip() {
		return rcVip;
	}

	public void setRcVip(Long rcVip) {
		this.rcVip = rcVip;
	}

	public Long getRcNonVip() {
		return rcNonVip;
	}

	public void setRcNonVip(Long rcNonVip) {
		this.rcNonVip = rcNonVip;
	}

	public Long getRcCustomer() {
		return rcCustomer;
	}

	public void setRcCustomer(Long rcCustomer) {
		this.rcCustomer = rcCustomer;
	}

	public Long getPageNo() {
		return pageNo;
	}

	public void setPageNo(Long pageNo) {
		this.pageNo = pageNo;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Long getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(Long isAudit) {
		this.isAudit = isAudit;
	}

	public Long getRcAll() {
		return rcAll;
	}

	public void setRcAll(Long rcAll) {
		this.rcAll = rcAll;
	}

	public Long getFrontCall() {
		return frontCall;
	}

	public void setFrontCall(Long frontCall) {
		this.frontCall = frontCall;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
