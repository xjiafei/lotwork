package com.winterframework.firefrog.phone.web.begin.vo;

import java.util.Date;

import com.winterframework.firefrog.phone.web.begin.annotation.LogContent;
import com.winterframework.firefrog.phone.web.begin.annotation.MoneyField;
import com.winterframework.orm.dal.ibatis3.BaseEntity;



public class BeginNewMission extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4206560491969068492L;

	private String createUser;

	private Date createTime;

	private String modifyUser;

	private Date modifyTime;

	@LogContent(title="绑卡奖励文案")
	private String bindCardText;

	@MoneyField
	@LogContent(title="綁卡奖励金额")
	private Long bindCardPremium;

	@LogContent(title="首充奖励文案")
	private String chargeText;

	@LogContent(title="首次提款文案")
	private String withdrawText;

	@MoneyField
	@LogContent(title="定义提款金额")
	private Long withdrawFactor;

	@MoneyField
	@LogContent(title="首提奖励金额")
	private Long withdrawPremium;

	@MoneyField
	@LogContent(title="每日投注最低金额")
	private Long dayBetFactor;

	@MoneyField
	@LogContent(title="每日答题金额随机下限")
	private Long dayAnsLow;
	
	@MoneyField
	@LogContent(title="每日答题金额随机上限")
	private Long dayAnsHigh;
	
	@LogContent(title="每日答题奖励单位")
	private Long dayAnsUnit;
	
	private Long version;
	
	@LogContent(title="人工审核")
	private String bindCardCheck;

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getBindCardText() {
		return bindCardText;
	}

	public void setBindCardText(String bindCardText) {
		this.bindCardText = bindCardText;
	}

	public Long getBindCardPremium() {
		return bindCardPremium;
	}

	public void setBindCardPremium(Long bindCardPremium) {
		this.bindCardPremium = bindCardPremium;
	}

	public String getChargeText() {
		return chargeText;
	}

	public void setChargeText(String chargeText) {
		this.chargeText = chargeText;
	}

	public String getWithdrawText() {
		return withdrawText;
	}

	public void setWithdrawText(String withdrawText) {
		this.withdrawText = withdrawText;
	}

	public Long getWithdrawFactor() {
		return withdrawFactor;
	}

	public void setWithdrawFactor(Long withdrawFactor) {
		this.withdrawFactor = withdrawFactor;
	}

	public Long getWithdrawPremium() {
		return withdrawPremium;
	}

	public void setWithdrawPremium(Long withdrawPremium) {
		this.withdrawPremium = withdrawPremium;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getBindCardCheck() {
		return bindCardCheck;
	}

	public void setBindCardCheck(String bindCardCheck) {
		this.bindCardCheck = bindCardCheck;
	}

	public Long getDayBetFactor() {
		return dayBetFactor;
	}

	public void setDayBetFactor(Long dayBetFactor) {
		this.dayBetFactor = dayBetFactor;
	}

	public Long getDayAnsLow() {
		return dayAnsLow;
	}

	public void setDayAnsLow(Long dayAnsLow) {
		this.dayAnsLow = dayAnsLow;
	}

	public Long getDayAnsHigh() {
		return dayAnsHigh;
	}

	public void setDayAnsHigh(Long dayAnsHigh) {
		this.dayAnsHigh = dayAnsHigh;
	}

	public Long getDayAnsUnit() {
		return dayAnsUnit;
	}

	public void setDayAnsUnit(Long dayAnsUnit) {
		this.dayAnsUnit = dayAnsUnit;
	}

}
