/**   
* @Title: AccountBalanceApprRequest.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-18 下午2:58:27 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

/** 
* @ClassName: FundAdjustApprRequest 
* @Description: 账户资金调整审批Request
* @author Alan 
* @date 2013-7-18 下午2:58:27 
*  
*/
public class FundAdjustApprRequest {

	//申请id
	private Long typeId;
	//审批结果
	private Long status;

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

}
