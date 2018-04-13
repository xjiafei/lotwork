package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;
import java.util.Date;

public class FundAppealStatusRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long userId;

	private Date startDate;

	private Date endDate;

	private Integer status;

	private String appealSn;

	private String fundSn;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAppealSn() {
		return appealSn;
	}

	public void setAppealSn(String appealSn) {
		this.appealSn = appealSn;
	}

	public String getFundSn() {
		return fundSn;
	}

	public void setFundSn(String fundSn) {
		this.fundSn = fundSn;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
