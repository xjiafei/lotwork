package com.winterframework.firefrog.user.entity;

import java.io.Serializable;

/**
 * 
 * 
 * 类功能说明:客户冻结解冻信息查询DTO
 * 
 * <p>
 * Copyright: Copyright(c) 2013
 * </p>
 * 
 * @Version 1.0
 * 
 * 
 */
public class QueryUnFreezeUserLogDTO implements Serializable {
	//public static String SORT_COLUMNS = "CREATE_DATE";

	private static final long serialVersionUID = 1897802600736491213L;
	private String account;
	private int startNo;
	private int endNo;
	private String sortColumns;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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

}
