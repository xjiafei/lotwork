package com.winterframework.firefrog.shortlived.poolking.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class PoolKingRequestVo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Long userId;

	private Date startDate;

	private Date endDate;
	
	private Integer startNo;
	
	private Integer endNo;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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
}
