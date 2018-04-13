package com.winterframework.firefrog.fund.web.controller.vo;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class WithCharge extends BaseEntity{

	private Long onlineCharge;
	private Long onlineWithdraw;
	private Long chargeFee;
	private Long withdrawFee;
	private Long manualAddCoin;
	private Long manualReduceCoin;
	private Long chargeSum;
	private Long withdrawSum;
	private String account;
	private Long userVip;
	

	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getUserVip() {
		return userVip;
	}

	public void setUserVip(Long userVip) {
		this.userVip = userVip;
	}

	public Long getOnlineCharge() {
		return onlineCharge;
	}

	public void setOnlineCharge(Long onlineCharge) {
		this.onlineCharge = onlineCharge;
	}

	public Long getOnlineWithdraw() {
		return onlineWithdraw;
	}

	public void setOnlineWithdraw(Long onlineWithdraw) {
		this.onlineWithdraw = onlineWithdraw;
	}

	public Long getChargeFee() {
		return chargeFee;
	}

	public void setChargeFee(Long chargeFee) {
		this.chargeFee = chargeFee;
	}

	public Long getWithdrawFee() {
		return withdrawFee;
	}

	public void setWithdrawFee(Long withdrawFee) {
		this.withdrawFee = withdrawFee;
	}

	

	public Long getManualAddCoin() {
		return manualAddCoin;
	}

	public void setManualAddCoin(Long manualAddCoin) {
		this.manualAddCoin = manualAddCoin;
	}

	public Long getManualReduceCoin() {
		return manualReduceCoin;
	}

	public void setManualReduceCoin(Long manualReduceCoin) {
		this.manualReduceCoin = manualReduceCoin;
	}

	public Long getChargeSum() {
		return chargeSum;
	}

	public void setChargeSum(Long chargeSum) {
		this.chargeSum = chargeSum;
	}

	public Long getWithdrawSum() {
		return withdrawSum;
	}

	public void setWithdrawSum(Long withdrawSum) {
		this.withdrawSum = withdrawSum;
	}

}
