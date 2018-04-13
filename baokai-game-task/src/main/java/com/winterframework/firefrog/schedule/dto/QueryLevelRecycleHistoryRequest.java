package com.winterframework.firefrog.schedule.dto;

import java.io.Serializable;

/** 
* @ClassName: QueryLevelRecycleHistoryRequest 
* @Description: 查询一代回收纪录
* @author Andy 
* @date 2015-10-12 下午03:10:00 
*  
*/
public class QueryLevelRecycleHistoryRequest implements Serializable {

	private static final long serialVersionUID = -1905020680742501824L;

	private String account;
	
	private Integer startNo;
	
	private Integer endNo; 

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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
