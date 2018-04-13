package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;
import java.util.Date;

public class QueryFundWithdrawOrderDTO implements Serializable {

	private static final long serialVersionUID = 5325642070933039348L;
	private Long userId;
	private String tCode;
	private Date fromDate;
	private Date toDate;
	private Long[] status;

	public QueryFundWithdrawOrderDTO() {

	}

	public String gettCode() {
		return tCode;
	}

	public void settCode(String tCode) {
		this.tCode = tCode;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Long[] getStatus() {
		return status;
	}

	public void setStatus(Long[] status) {
		this.status = status;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
