package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WithdrawConfirmResponse implements Serializable {

	private static final long serialVersionUID = 741474295575421250L;
	@JsonProperty("mowecum_order_num") 
	private String mownecumOrderNum;
	@JsonProperty("company_order_num") 
	private String companyOrderNum;
	private String status;
	@JsonProperty("error_msg") 
	private String errorMsg;

	public String getMownecumOrderNum() {
		return mownecumOrderNum;
	}

	public void setMownecumOrderNum(String mownecumOrderNum) {
		this.mownecumOrderNum = mownecumOrderNum;
	}

	public String getCompanyOrderNum() {
		return companyOrderNum;
	}

	public void setCompanyOrderNum(String companyOrderNum) {
		this.companyOrderNum = companyOrderNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
