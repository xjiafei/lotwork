/**   
* @Title: AccountBalanceQueryResponse.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-18 上午11:09:26 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

/** 
* @ClassName: FundAdjustResponse 
* @Description: 资金调整response 
* @author Alan 
* @date 2013-7-18 上午11:09:26 
*  
*/
public class FundAdjustResponse {

	//流水号
	private String sn;
	//被打款用户名
	private String userAccount;
	//类型
	private Long typeId;
	//申请时间
	private Long applyTime;
	//金额
	private Long withdrawAmt;
	//备注
	private String memo;
	//申请人
	private String applyAccount;
	//审核人
	private String apprAccount;
	//审核时间
	private Long apprTime;
	//状态
	private Long status;

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Long getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Long applyTime) {
		this.applyTime = applyTime;
	}

	public Long getWithdrawAmt() {
		return withdrawAmt;
	}

	public void setWithdrawAmt(Long withdrawAmt) {
		this.withdrawAmt = withdrawAmt;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getApplyAccount() {
		return applyAccount;
	}

	public void setApplyAccount(String applyAccount) {
		this.applyAccount = applyAccount;
	}

	public String getApprAccount() {
		return apprAccount;
	}

	public void setApprAccount(String apprAccount) {
		this.apprAccount = apprAccount;
	}

	public Long getApprTime() {
		return apprTime;
	}

	public void setApprTime(Long apprTime) {
		this.apprTime = apprTime;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

}
