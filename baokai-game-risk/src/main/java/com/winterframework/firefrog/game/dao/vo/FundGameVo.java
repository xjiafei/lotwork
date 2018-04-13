package com.winterframework.firefrog.game.dao.vo;

public class FundGameVo {

	private Long userId;
	private Long amount;
	
	/**
	 * 值为资金变动类型-摘要-交易码(ModelCode-SummaryCode-TradeCode)
	 * 单期投注，GM-DVCB-DVCX
	 */
	private String reason;
	private Long operator;
	private String exCode;
	private String planCode;
	private String note;
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

	public String getExCode() {
		return exCode;
	}

	public void setExCode(String exCode) {
		this.exCode = exCode;
	}

}
