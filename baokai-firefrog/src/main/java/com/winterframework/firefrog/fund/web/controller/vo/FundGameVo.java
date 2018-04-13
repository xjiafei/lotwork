package com.winterframework.firefrog.fund.web.controller.vo;

import com.winterframework.firefrog.fund.enums.EnumItem;
import com.winterframework.firefrog.fund.enums.FundModelTool;

public class FundGameVo {

	private Long userId;
	private Long amount;
	private String key;
    /**
	 * 值为资金变动类型-摘要-交易码(ModelCode-SummaryCode-StatusCode)
	 * 单期投注，GM-DVCB-1
	 */
	private String reason;
	private String exCode;
	private String planCode;
	private String note;
	private Long operator;
	private String sn;
	private Long amountBal;
	public FundGameVo(){}
	public FundGameVo(EnumItem item,Long userId,Long amount,String sn,boolean isBack){
		this(item,userId,amount,sn,isBack,null);

	}
	public FundGameVo(EnumItem item,Long userId,Long amount,String sn,boolean isBack,String note){
		this.reason=FundModelTool.createKey(item);
		this.userId=userId;
		this.amount=amount;
		//this.sn=sn;		
		this.setIsAclUser(isBack?1L:0L);
		this.note=note;

	}
	
	public String getPlanCode() {
		return planCode;
	}
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getExCode() {
		return exCode;
	}
	public void setExCode(String exCode) {
		this.exCode = exCode;
	}

	/**
	 * 代表operator是后台用户，还是玩家。1代表后台用户，0代表玩家。
	 */
	private Long isAclUser;

	public Long getIsAclUser() {
		return isAclUser;
	}

	public void setIsAclUser(Long isAclUser) {
		this.isAclUser = isAclUser;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}
	public Long getAmountBal() {
		return amountBal;
	}
	public void setAmountBal(Long amountBal) {
		this.amountBal = amountBal;
	}

		

}
