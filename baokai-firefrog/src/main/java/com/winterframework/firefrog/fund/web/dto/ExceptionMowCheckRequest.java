package com.winterframework.firefrog.fund.web.dto;

public class ExceptionMowCheckRequest {

	private Long exceptionId;

	private String mownecumOrderNum;

	private String companyOrderNum;

	public Long getExceptionId() {
		return exceptionId;
	}

	public void setExceptionId(Long exceptionId) {
		this.exceptionId = exceptionId;
	}

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

}
