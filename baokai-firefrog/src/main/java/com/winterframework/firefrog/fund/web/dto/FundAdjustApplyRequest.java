/**   
* @Title: AccountBalanceApplyRequest.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-18 上午11:09:26 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

/** 
* @ClassName: FundAdjustApplyRequest 
* @Description: 资金调整申请Request
* @author Alan 
* @date 2013-7-18 上午11:09:26 
*  
*/
public class FundAdjustApplyRequest {

	//类型ID
	private Long typeId;
	//被操作对象(接受者)
	private String rcvAct;
	//金额
	private Long depositAmt;
	//备注(请求备注)
	private String memo;

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getRcvAct() {
		return rcvAct;
	}

	public void setRcvAct(String rcvAct) {
		this.rcvAct = rcvAct;
	}

	public Long getDepositAmt() {
		return depositAmt;
	}

	public void setDepositAmt(Long depositAmt) {
		this.depositAmt = depositAmt;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
