package com.winterframework.firefrog.user.entity;

import java.io.Serializable;

/**
 * 
 * 
 * 类功能说明:客户申诉查询条件DTO
 * 
 * <p>
 * Copyright: Copyright(c) 2013
 * </p>
 * 
 * @Version 1.0
 * 
 * 
 */
public class UserAppealQueryDTO implements Serializable {
	public static String SORT_COLUMNS = "CREATE_DATE";

	private static final long serialVersionUID = 1897802600736491213L;
	private String account;
	private String operater;
	private Long operaterId;
	private Integer appealType;
	private Integer passed;
	private int startNo;
	private int endNo;
	private String sortColumns;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getOperater() {
		return operater;
	}

	public void setOperater(String operater) {
		this.operater = operater;
	}

	public Integer getAppealType() {
		return appealType;
	}

	public void setAppealType(Integer appealType) {
		this.appealType = appealType;
	}

	public Integer getPassed() {
		return passed;
	}

	public void setPassed(Integer passed) {
		this.passed = passed;
	}

	public int getStartNo() {
		return startNo;
	}

	public void setStartNo(int startNo) {
		this.startNo = startNo;
	}

	public int getEndNo() {
		return endNo;
	}

	public void setEndNo(int endNo) {
		this.endNo = endNo;
	}

	public String getSortColumns() {
		return sortColumns;
	}

	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}

	public Long getOperaterId() {
		return operaterId;
	}

	public void setOperaterId(Long operaterId) {
		this.operaterId = operaterId;
	}

}
