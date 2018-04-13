package com.winterframework.firefrog.fund.entity;

import java.io.Serializable;
import java.util.Date;

public class FundAppealStatus implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long userId;

	private Date startDate;

	private Date endDate;

	private Integer startNo;

	private Integer endNo;

	private Integer status;

	private String appealSn;

	private String fundSn;

	public enum Status {
		UNDO(0), DOING(1), PASS(2), REJECT(3);
		private int value;

		Status(int value) {
			this.value = value;
		}

		public int value() {
			return value;
		}
	}

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

	public Integer getStartNo() {
		return startNo;
	}

	public void setStartNo(Integer startNo) {
		this.startNo = startNo;
	}

	public Integer getEndNo() {
		return endNo;
	}

	public void setEndNo(Integer endNo) {
		this.endNo = endNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setStatus(Status status) {
		this.status = status.value();
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

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
