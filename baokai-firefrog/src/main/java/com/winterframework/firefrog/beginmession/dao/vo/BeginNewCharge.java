package com.winterframework.firefrog.beginmession.dao.vo;

import java.util.Date;

import com.winterframework.firefrog.beginmession.annotation.LogContent;
import com.winterframework.firefrog.beginmession.annotation.MoneyField;
import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class BeginNewCharge extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -854077269071439739L;

	private String createUser;

	private Date createTime;

	private String modifyUser;

	private Date modifyTime;
	
	@MoneyField
	@LogContent(title="充值金额")
	private Long chargeAmt;

	@LogContent(title="流水倍数")
	private Long chargeFactor;

	@LogContent(title="返奖百分比")
	private Long chargePer;

	@MoneyField
	@LogContent(title="返奖金额")
	private Long chargePreium;

	@LogContent(title="投注期限")
	private Long bettingDate;

	@LogContent(title="并列领取")
	private String multipleGet;

	private Long version;
	
	private Long rownum;

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

	public Long getChargeAmt() {
		return chargeAmt;
	}

	public void setChargeAmt(Long chargeAmt) {
		this.chargeAmt = chargeAmt;
	}

	public Long getChargeFactor() {
		return chargeFactor;
	}

	public void setChargeFactor(Long chargeFactor) {
		this.chargeFactor = chargeFactor;
	}

	public Long getChargePer() {
		return chargePer;
	}

	public void setChargePer(Long chargePer) {
		this.chargePer = chargePer;
	}

	public Long getChargePreium() {
		return chargePreium;
	}

	public void setChargePreium(Long chargePreium) {
		this.chargePreium = chargePreium;
	}

	public Long getBettingDate() {
		return bettingDate;
	}

	public void setBettingDate(Long bettingDate) {
		this.bettingDate = bettingDate;
	}

	public String getMultipleGet() {
		return multipleGet;
	}

	public void setMultipleGet(String multipleGet) {
		this.multipleGet = multipleGet;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}


	public Long getRownum() {
		return rownum;
	}

	public void setRownum(Long rownum) {
		this.rownum = rownum;
	}


}
