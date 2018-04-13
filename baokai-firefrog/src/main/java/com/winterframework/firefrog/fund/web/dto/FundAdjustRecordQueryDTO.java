/**   
* @Title: AccountBalanceRecordQueryDTO.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-18 上午11:09:26 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;

/** 
* @ClassName: FundAdjustRecordQueryDTO 
* @Description: 资金调整记录查询DTO 
* @author Alan 
* @date 2013-7-18 上午11:09:26 
*  
*/
public class FundAdjustRecordQueryDTO implements Serializable {

	private static final long serialVersionUID = -1092251670896283225L;

	public static String SORT_COLUMNS = "APPLY_TIME";

	private Long status;

	private int startNo;
	private int endNo;
	private String sortColumns;

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
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

	public static String getSORT_COLUMNS() {
		return SORT_COLUMNS;
	}

	public static void setSORT_COLUMNS(String sORT_COLUMNS) {
		SORT_COLUMNS = sORT_COLUMNS;
	}

}
