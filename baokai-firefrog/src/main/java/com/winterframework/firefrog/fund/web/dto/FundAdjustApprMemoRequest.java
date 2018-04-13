/**   
* @Title: AccountBalanceApprMemoRequest.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-18 下午2:50:15 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

/** 
* @ClassName: AccountBalanceApprMemoRequest 
* @Description: 资金调整审批备注request
* @author Alan
* @date 2013-7-18 下午2:50:15 
*  
*/
public class FundAdjustApprMemoRequest {

	//申请id
	private Long typeId;
	//审批备注
	private String memo;

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
