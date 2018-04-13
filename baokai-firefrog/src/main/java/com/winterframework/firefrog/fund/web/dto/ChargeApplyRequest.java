package com.winterframework.firefrog.fund.web.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.winterframework.modules.web.jsonresult.FirefrogDateDeSerializer;
import com.winterframework.modules.web.jsonresult.FirefrogDateSerializer;

public class ChargeApplyRequest {

	@NotNull
	private Long bankId;

	private Long userId;

	private Long mcBankId;

	private String userAct;
	private Long depositMode;
	private String memo;
	private Long platfom;
	private String ver;
	private String bankNumber;
	private String bankAccount;
	private String nickName;
	private String customerIp;
	/**0個人版，1企業版*/
	private Long chargeVersion;

	public Long getChargeVersion() {
		return chargeVersion;
	}

	public void setChargeVersion(Long chargeVersion) {
		this.chargeVersion = chargeVersion;
	}

	public String getCustomerIp() {
		return customerIp;
	}

	public void setCustomerIp(String customerIp) {
		this.customerIp = customerIp;
	}

	public Long getDepositMode() {
		return depositMode;
	}

	public void setDepositMode(Long depositMode) {
		this.depositMode = depositMode;
	}

	private Long preChargeAmt;
	@JsonSerialize(using = FirefrogDateSerializer.class)
	@JsonDeserialize(using = FirefrogDateDeSerializer.class)
	private Date applyTime;

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getMcBankId() {
		return mcBankId;
	}

	public void setMcBankId(Long mcBankId) {
		this.mcBankId = mcBankId;
	}

	public String getUserAct() {
		return userAct;
	}

	public void setUserAct(String userAct) {
		this.userAct = userAct;
	}

	public Long getPreChargeAmt() {
		return preChargeAmt;
	}

	public void setPreChargeAmt(Long preChargeAmt) {
		this.preChargeAmt = preChargeAmt;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
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

	public String getBankNumber() {
		return bankNumber;
	}

	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}
