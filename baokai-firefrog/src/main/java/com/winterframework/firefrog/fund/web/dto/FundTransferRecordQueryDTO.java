package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;
import java.util.Date;

/** 
* @ClassName: FundTransferRecordQueryDTO 
* @Description: 用户转账记录查询DTO 
* @author david
* @date 2013-7-2 上午11:55:38 
*  
*/
public class FundTransferRecordQueryDTO implements Serializable {
	public static String SORT_COLUMNS = "GMT_CREATED";

	private static final long serialVersionUID = 1897802600736491213L;
	private String sn;
	private Long fromDate;
	private Long toDate;
	private int startNo;
	private int endNo;
	private Long[] status;
	private String sortColumns;
	private String exactUser;
	private Long direction;
	private Date recycleDate;

	public String getExactUser() {
		return exactUser;
	}

	public void setExactUser(String exactUser) {
		this.exactUser = exactUser;
	}

	public static String getSORT_COLUMNS() {
		return SORT_COLUMNS;
	}

	public static void setSORT_COLUMNS(String sORT_COLUMNS) {
		SORT_COLUMNS = sORT_COLUMNS;
	}



	public Long[] getStatus() {
		return status;
	}

	public void setStatus(Long[] status) {
		this.status = status;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Long getFromDate() {
		return fromDate;
	}

	public void setFromDate(Long fromDate) {
		this.fromDate = fromDate;
	}

	public Long getToDate() {
		return toDate;
	}

	public void setToDate(Long toDate) {
		this.toDate = toDate;
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

	public Long getDirection() {
		return direction;
	}

	public void setDirection(Long direction) {
		this.direction = direction;
	}

	public Date getRecycleDate() {
		return recycleDate;
	}

	public void setRecycleDate(Date recycleDate) {
		this.recycleDate = recycleDate;
	}

}
