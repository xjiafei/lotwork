/**   
* @Title: ChargeConfirmResponse.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-2 下午3:18:14 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;

/** 
* @ClassName: ChargeConfirmResponse 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-2 下午3:18:14 
*  
*/
public class ChargeConfirmResponse implements Serializable {

	private static final long serialVersionUID = 1977177907879851391L;

	private String companyOrderNum;

	private String mownecumOrderNum;

	private Long tempOrderNumber;

	private Long status;

	private String errorMsg;

	public String getCompanyOrderNum() {
		return companyOrderNum;
	}

	public void setCompanyOrderNum(String companyOrderNum) {
		this.companyOrderNum = companyOrderNum;
	}

	public String getMownecumOrderNum() {
		return mownecumOrderNum;
	}

	public void setMownecumOrderNum(String mownecumOrderNum) {
		this.mownecumOrderNum = mownecumOrderNum;
	}

	public Long getTempOrderNumber() {
		return tempOrderNumber;
	}

	public void setTempOrderNumber(Long tempOrderNumber) {
		this.tempOrderNumber = tempOrderNumber;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
