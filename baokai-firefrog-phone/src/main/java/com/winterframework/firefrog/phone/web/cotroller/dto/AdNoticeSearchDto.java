package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class AdNoticeSearchDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3283830475045491905L;

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


}
