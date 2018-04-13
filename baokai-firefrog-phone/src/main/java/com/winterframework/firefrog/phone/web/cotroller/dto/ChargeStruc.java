package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.winterframework.modules.web.jsonresult.FirefrogDateDeSerializer;
import com.winterframework.modules.web.jsonresult.FirefrogDateSerializer;

public class ChargeStruc implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1440965027715732356L;
	private String sn;
	private Long applyTime;

	@JsonSerialize(using = FirefrogDateSerializer.class) 
	@JsonDeserialize(using = FirefrogDateDeSerializer.class)
	private Date chargeTime;

	private Long applyAmt;

	private Long chargeAmt;

	private Long bankId;

	private Long status;

	private Long userId;
	private String memo;
	private String realMemo;
	//用户名
	private String account;
	//所属总代
	private String topVip;
	//收款卡
	private String rcvAcct;
	private String rcvBankNumber;
	private String bankName;
	//收款银行Id
	private Long rcvBankId;
	//mow返回时间 
	private Date mowDate;
	private Long preChargeAmt;
	//金额
	private Long realChargeAmount;
	@JsonSerialize(using = FirefrogDateSerializer.class) 
	@JsonDeserialize(using = FirefrogDateDeSerializer.class)
	private Date  addCoinTime;
	private String mcSn;
	//付款渠道
	private String mcChannel;
	private Long depositMode;
	private Long shouxufei;
	private String cardNumber;
	private String cardAccount;
	private String rcvEmail;
	private Long payBankId;
	private String userAct;
	private Long realBankId;
	private String rcvBankName;
	private String platVersion;
	private Long waitTime;

	public String getPlatVersion() {
		return platVersion;
	}

	public void setPlatVersion(String platVersion) {
		this.platVersion = platVersion;
	}

	public String getUserAct() {
		return userAct;
	}

	public Long getRealBankId() {
		return realBankId;
	}

	public String getRcvBankName() {
		return rcvBankName;
	}

	public void setRcvBankName(String rcvBankName) {
		this.rcvBankName = rcvBankName;
	}

	public void setRealBankId(Long realBankId) {
		this.realBankId = realBankId;
	}

	public void setUserAct(String userAct) {
		this.userAct = userAct;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getRcvBankNumber() {
		return rcvBankNumber;
	}

	public void setRcvBankNumber(String rcvBankNumber) {
		this.rcvBankNumber = rcvBankNumber;
	}

	public String getRcvEmail() {
		return rcvEmail;
	}

	public void setRcvEmail(String rcvEmail) {
		this.rcvEmail = rcvEmail;
	}

	

	public Long getPayBankId() {
		return payBankId;
	}

	public void setPayBankId(Long payBankId) {
		this.payBankId = payBankId;
	}

	public String getMcSn() {
		return mcSn;
	}

	public void setMcSn(String mcSn) {
		this.mcSn = mcSn;
	}

	public Long getShouxufei() {
		return shouxufei;
	}

	public void setShouxufei(Long shouxufei) {
		this.shouxufei = shouxufei;
	}

	public String getRealMemo() {
		return realMemo;
	}

	public void setRealMemo(String realMemo) {
		this.realMemo = realMemo;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardAccount() {
		return cardAccount;
	}

	public void setCardAccount(String cardAccount) {
		this.cardAccount = cardAccount;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getPreChargeAmt() {
		return preChargeAmt;
	}

	public void setPreChargeAmt(Long preChargeAmt) {
		this.preChargeAmt = preChargeAmt;
	}

	public Long getDepositMode() {
		return depositMode;
	}

	public void setDepositMode(Long depositeMode) {
		this.depositMode = depositeMode;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getTopVip() {
		return topVip;
	}

	public void setTopVip(String topVip) {
		this.topVip = topVip;
	}

	public String getRcvAcct() {
		return rcvAcct;
	}

	public void setRcvAcct(String rcvAcct) {
		this.rcvAcct = rcvAcct;
	}

	public Long getRcvBankId() {
		return rcvBankId;
	}

	public void setRcvBankId(Long rcvBankId) {
		this.rcvBankId = rcvBankId;
	}

	public Date getMowDate() {
		return mowDate;
	}

	public void setMowDate(Date mowDate) {
		this.mowDate = mowDate;
	}

	public Long getRealChargeAmount() {
		return realChargeAmount;
	}

	public void setRealChargeAmount(Long realChargeAmount) {
		this.realChargeAmount = realChargeAmount;
	}

	public String getMcChannel() {
		return mcChannel;
	}

	public void setMcChannel(String mcChannel) {
		this.mcChannel = mcChannel;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Long getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Long applyTime) {
		this.applyTime = applyTime;
	}

	public Date getChargeTime() {
		return chargeTime;
	}

	public void setChargeTime(Date chargeTime) {
		this.chargeTime = chargeTime;
	}

	public Long getApplyAmt() {
		return applyAmt;
	}

	public void setApplyAmt(Long applyAmt) {
		this.applyAmt = applyAmt;
	}

	public Long getChargeAmt() {
		return chargeAmt;
	}

	public void setChargeAmt(Long chargeAmt) {
		this.chargeAmt = chargeAmt;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Date getAddCoinTime() {
		return addCoinTime;
	}

	public void setAddCoinTime(Date addCoinTime) {
		this.addCoinTime = addCoinTime;
	}

	public Long getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(Long waitTime) {
		this.waitTime = waitTime;
	}

}
