package com.winterframework.firefrog.fund.entity;

/**
 * 
* @ClassName: FundChangeLog 
* @Description: 资金变动实体类 
* @author Richard
* @date 2013-6-28 下午5:30:00 
*
 */
public class UserFundChangeLog {

	private Long beforBal;
	private Long beforeDamt;
	private Long ctBal;
	private Long ctDamt;
	private String reason;
	private Long operator;
	private String exCode;
	private Long ctAvailBal;
	private Long beforeAvailBal;

	private UserFund fund;
	private FundOrder fundOrder;

	private Long isVisiblebyFrontUser;

	private Long isAclUser;

	private Long userId;

	private String sn;

	private String fundSn;
	private String planCode;
	private String note;

	public UserFundChangeLog() {

	}

	public Long getCtAvailBal() {
		return ctAvailBal;
	}

	public void setCtAvailBal(Long ctAvailBal) {
		this.ctAvailBal = ctAvailBal;
	}

	public Long getBeforeAvailBal() {
		return beforeAvailBal;
	}

	public void setBeforeAvailBal(Long beforeAvailBal) {
		this.beforeAvailBal = beforeAvailBal;
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

	public Long getBeforBal() {
		return beforBal;
	}

	public void setBeforBal(Long beforBal) {
		this.beforBal = beforBal;
	}

	public Long getBeforeDamt() {
		return beforeDamt;
	}

	public void setBeforeDamt(Long beforeDamt) {
		this.beforeDamt = beforeDamt;
	}

	public Long getCtBal() {
		return ctBal;
	}

	public void setCtBal(Long ctBal) {
		this.ctBal = ctBal;
	}

	public Long getCtDamt() {
		return ctDamt;
	}

	public void setCtDamt(Long ctDamt) {
		this.ctDamt = ctDamt;
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

	public UserFund getFund() {
		return fund;
	}

	public void setFund(UserFund fund) {
		this.fund = fund;
	}

	public FundOrder getFundOrder() {
		return fundOrder;
	}

	public void setFundOrder(FundOrder fundOrder) {
		this.fundOrder = fundOrder;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getFundSn() {
		return fundSn;
	}

	public void setFundSn(String fundSn) {
		this.fundSn = fundSn;
	}

	public Long getIsVisiblebyFrontUser() {
		return isVisiblebyFrontUser;
	}

	public void setIsVisiblebyFrontUser(Long isVisiblebyFrontUser) {
		this.isVisiblebyFrontUser = isVisiblebyFrontUser;
	}

	public Long getIsAclUser() {
		return isAclUser;
	}

	public void setIsAclUser(Long isAclUser) {
		this.isAclUser = isAclUser;
	}
}
