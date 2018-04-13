package com.winterframework.firefrog.fund.web.dto;

/** 
* @ClassName: QueryFundTransferOrderDTO 
* @Description: 用户查询转账记录请求参数DTO 
* @author david
* @date 2013-7-2 上午11:56:50 
*  
*/
public class QueryFundTransferOrderDTO {
	private String sn;
	private Long fromDate;
	private Long toDate;
	private Long[] status;
	private String exactUser;
	private Long direction;

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

	public Long[] getStatus() {
		return status;
	}

	public void setStatus(Long[] status) {
		this.status = status;
	}

	public String getExactUser() {
		return exactUser;
	}

	public void setExactUser(String exactUser) {
		this.exactUser = exactUser;
	}

	public Long getDirection() {
		return direction;
	}

	public void setDirection(Long direction) {
		this.direction = direction;
	}
	
}
