/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.fund.dao.vo;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class FundCharge extends BaseEntity {

	//alias
	public static final String TABLE_ALIAS = "FundCharge";
	public static final String ALIAS_USER_ID = "用户id";
	public static final String ALIAS_BANK_ID = "银行id";
	public static final String ALIAS_PRE_CHARGE_AMT = "预计充值金额";
	public static final String ALIAS_CARD_NUMBER = "卡号";
	public static final String ALIAS_RCV_CARD_NUMBER = "收款卡号";
	public static final String ALIAS_RCV_ACC_NAME = "收款人姓名";
	public static final String ALIAS_RCV_EMAIL = "收款人email";
	public static final String ALIAS_APPLY_TIME = "申请时间";
	public static final String ALIAS_REAL_CHARGE_AMT = "实际充值金额";
	public static final String ALIAS_CHARGE_TIME = "充值时间";
	public static final String ALIAS_MC_NOTICE_TIME = "mownecum通知时间";
	public static final String ALIAS_STATUS = "状态1:申请状态 2:充值成功 3：充值失败";
	public static final String ALIAS_CHARGE_MEMO = "附言";
	public static final String ALIAS_MC_FEE = "mownecum小费";
	public static final String ALIAS_SN = "流水号";
	public static final String ALIAS_MC_EXPIRE_TIME = "充值过期时间";
	public static final String ALIAS_MC_ERROR_MSG = "错误原因";
	public static final String ALIAS_MC_CHANNEL = "交易渠道";
	public static final String ALIAS_MC_AREA = "trade";
	public static final String ALIAS_MC_UUID = "uuid";
	public static final String ALIAS_MC_SN = "mc 订单号";
	public static final String ALIAS_MC_BANK_FEE = "银行手续费";
	public static final String ALIAS_USER_ACT = "用户账号";
	public static final String ALIAS_TEMP_SN = "临时订单号";

	//date formats

	//columns START
	private Long userId;
	private Long bankId;
	private Long preChargeAmt;
	private String cardNumber;
	private String rcvCardNumber;
	private String rcvAccName;
	private String rcvEmail;
	private Date applyTime;
	private Long realChargeAmt;
	private Date chargeTime;
	private Date mcNoticeTime;
	private Long status;
	private String chargeMemo;
	private Long mcFee;
	private String sn;
	private Date mcExpireTime;
	private String mcErrorMsg;
	private String mcChannel;
	private Long depositMode;
	private String breakUrl;
	private String mcArea;
	private String mcUuid;
	private String mcSn;
	private Long mcBankFee;
	private String userAct;
	private String tempSn;
	private String account;
	private Long payBankId;
	private String rcvBankName;
	private String topVip;
	private Long realBankId;
	private Long platfom;
	private String ver;
	private Date operatingTime;
	private String chargeCardNum;
	private Long chargeMode;
	//columns END

	public Long getChargeMode() {
		return chargeMode;
	}

	public void setChargeMode(Long chargeMode) {
		this.chargeMode = chargeMode;
	}

	public Long getRealBankId() {
		return realBankId;
	}

	public void setRealBankId(Long realBankId) {
		this.realBankId = realBankId;
	}

	public FundCharge() {
	}

	public String getTopVip() {
		return topVip;
	}

	public void setTopVip(String topVip) {
		this.topVip = topVip;
	}

	public Long getDepositMode() {
		return depositMode;
	}

	public void setDepositMode(Long depositMode) {
		this.depositMode = depositMode;
	}

	public String getBreakUrl() {
		return breakUrl;
	}

	public void setBreakUrl(String breakUrl) {
		this.breakUrl = breakUrl;
	}

	public String getRcvBankName() {
		return rcvBankName;
	}

	public void setRcvBankName(String rcvBankName) {
		this.rcvBankName = rcvBankName;
	}

	public FundCharge(Long id) {
		this.id = id;
	}

	public void setUserId(Long value) {
		this.userId = value;
	}

	public Long getPayBankId() {
		return payBankId;
	}

	public void setPayBankId(Long payBankId) {
		this.payBankId = payBankId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setBankId(Long value) {
		this.bankId = value;
	}

	public Long getBankId() {
		return this.bankId;
	}

	public void setPreChargeAmt(Long value) {
		this.preChargeAmt = value;
	}

	public Long getPreChargeAmt() {
		return this.preChargeAmt;
	}

	public void setCardNumber(String value) {
		this.cardNumber = value;
	}

	public String getCardNumber() {
		return this.cardNumber;
	}

	public void setRcvCardNumber(String value) {
		this.rcvCardNumber = value;
	}

	public String getRcvCardNumber() {
		return this.rcvCardNumber;
	}

	public void setRcvAccName(String value) {
		this.rcvAccName = value;
	}

	public String getRcvAccName() {
		return this.rcvAccName;
	}

	public void setRcvEmail(String value) {
		this.rcvEmail = value;
	}

	public String getRcvEmail() {
		return this.rcvEmail;
	}

	public void setApplyTime(Date value) {
		this.applyTime = value;
	}

	public Date getApplyTime() {
		return this.applyTime;
	}

	public void setRealChargeAmt(Long value) {
		this.realChargeAmt = value;
	}

	public Long getRealChargeAmt() {
		return this.realChargeAmt;
	}

	public void setChargeTime(Date value) {
		this.chargeTime = value;
	}

	public Date getChargeTime() {
		return this.chargeTime;
	}

	public void setMcNoticeTime(Date value) {
		this.mcNoticeTime = value;
	}

	public Date getMcNoticeTime() {
		return this.mcNoticeTime;
	}

	public void setStatus(Long value) {
		this.status = value;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setChargeMemo(String value) {
		this.chargeMemo = value;
	}

	public String getChargeMemo() {
		return this.chargeMemo;
	}

	public void setMcFee(Long value) {
		this.mcFee = value;
	}

	public Long getMcFee() {
		return this.mcFee;
	}

	public void setSn(String value) {
		this.sn = value;
	}

	public String getSn() {
		return this.sn;
	}

	public void setMcExpireTime(Date value) {
		this.mcExpireTime = value;
	}

	public Date getMcExpireTime() {
		return this.mcExpireTime;
	}

	public void setMcErrorMsg(String value) {
		this.mcErrorMsg = value;
	}

	public String getMcErrorMsg() {
		return this.mcErrorMsg;
	}

	public void setMcChannel(String value) {
		this.mcChannel = value;
	}

	public String getMcChannel() {
		return this.mcChannel;
	}

	public void setMcArea(String value) {
		this.mcArea = value;
	}

	public String getMcArea() {
		return this.mcArea;
	}

	public void setMcUuid(String value) {
		this.mcUuid = value;
	}

	public String getMcUuid() {
		return this.mcUuid;
	}

	public void setMcSn(String value) {
		this.mcSn = value;
	}

	public String getMcSn() {
		return this.mcSn;
	}

	public void setMcBankFee(Long value) {
		this.mcBankFee = value;
	}

	public Long getMcBankFee() {
		return this.mcBankFee;
	}

	public void setUserAct(String value) {
		this.userAct = value;
	}

	public String getUserAct() {
		return this.userAct;
	}

	public void setTempSn(String value) {
		this.tempSn = value;
	}

	public String getTempSn() {
		return this.tempSn;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("UserId", getUserId())
				.append("BankId", getBankId()).append("PreChargeAmt", getPreChargeAmt())
				.append("CardNumber", getCardNumber()).append("RcvCardNumber", getRcvCardNumber())
				.append("RcvAccName", getRcvAccName()).append("RcvEmail", getRcvEmail())
				.append("ApplyTime", getApplyTime()).append("RealChargeAmt", getRealChargeAmt())
				.append("ChargeTime", getChargeTime()).append("McNoticeTime", getMcNoticeTime())
				.append("Status", getStatus()).append("ChargeMemo", getChargeMemo()).append("McFee", getMcFee())
				.append("Sn", getSn()).append("McExpireTime", getMcExpireTime()).append("McErrorMsg", getMcErrorMsg())
				.append("McChannel", getMcChannel()).append("McArea", getMcArea()).append("McUuid", getMcUuid())
				.append("McSn", getMcSn()).append("McBankFee", getMcBankFee()).append("UserAct", getUserAct())
				.append("TempSn", getTempSn()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getUserId()).append(getBankId()).append(getPreChargeAmt())
				.append(getCardNumber()).append(getRcvCardNumber()).append(getRcvAccName()).append(getRcvEmail())
				.append(getApplyTime()).append(getRealChargeAmt()).append(getChargeTime()).append(getMcNoticeTime())
				.append(getStatus()).append(getChargeMemo()).append(getMcFee()).append(getSn())
				.append(getMcExpireTime()).append(getMcErrorMsg()).append(getMcChannel()).append(getMcArea())
				.append(getMcUuid()).append(getMcSn()).append(getMcBankFee()).append(getUserAct()).append(getTempSn())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FundCharge == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		FundCharge other = (FundCharge) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getUserId(), other.getUserId())

		.append(getBankId(), other.getBankId())

		.append(getPreChargeAmt(), other.getPreChargeAmt())

		.append(getCardNumber(), other.getCardNumber())

		.append(getRcvCardNumber(), other.getRcvCardNumber())

		.append(getRcvAccName(), other.getRcvAccName())

		.append(getRcvEmail(), other.getRcvEmail())

		.append(getApplyTime(), other.getApplyTime())

		.append(getRealChargeAmt(), other.getRealChargeAmt())

		.append(getChargeTime(), other.getChargeTime())

		.append(getMcNoticeTime(), other.getMcNoticeTime())

		.append(getStatus(), other.getStatus())

		.append(getChargeMemo(), other.getChargeMemo())

		.append(getMcFee(), other.getMcFee())

		.append(getSn(), other.getSn())

		.append(getMcExpireTime(), other.getMcExpireTime())

		.append(getMcErrorMsg(), other.getMcErrorMsg())

		.append(getMcChannel(), other.getMcChannel())

		.append(getMcArea(), other.getMcArea())

		.append(getMcUuid(), other.getMcUuid())

		.append(getMcSn(), other.getMcSn())

		.append(getMcBankFee(), other.getMcBankFee())

		.append(getUserAct(), other.getUserAct())

		.append(getTempSn(), other.getTempSn())

		.isEquals();
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getPlatfom() {
		return platfom;
	}

	public void setPlatfom(Long platfom) {
		this.platfom = platfom;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public Date getOperatingTime() {
		return operatingTime;
	}

	public void setOperatingTime(Date operatingTime) {
		this.operatingTime = operatingTime;
	}

	public String getChargeCardNum() {
		return chargeCardNum;
	}

	public void setChargeCardNum(String chargeCardNum) {
		this.chargeCardNum = chargeCardNum;
	}
	
}
