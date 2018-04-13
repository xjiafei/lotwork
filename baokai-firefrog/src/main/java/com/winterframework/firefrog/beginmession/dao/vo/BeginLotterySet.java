package com.winterframework.firefrog.beginmession.dao.vo;

import java.util.Date;

import com.winterframework.firefrog.beginmession.annotation.LogContent;
import com.winterframework.firefrog.beginmession.annotation.MoneyField;
import com.winterframework.orm.dal.ibatis3.BaseEntity;


public class BeginLotterySet extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6054824596185572915L;

	private String createUser;

	private Date createTime;

	private String modifyUser;

	private Date modifyTime;

	private Long lotteryType;

	@LogContent(lotteryUnit=true,title="奖励单位")
	private Long lotteryUnit;

	@MoneyField
	@LogContent(title="奖励下限")
	private Long lotteryLow;

	@MoneyField
	@LogContent(title="奖励上限")
	private Long lotteryHigh;

	private Long version;
	
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

	public Long getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Long lotteryType) {
		this.lotteryType = lotteryType;
	}

	public Long getLotteryUnit() {
		return lotteryUnit;
	}

	public void setLotteryUnit(Long lotteryUnit) {
		this.lotteryUnit = lotteryUnit;
	}

	public Long getLotteryLow() {
		return lotteryLow;
	}

	public void setLotteryLow(Long lotteryLow) {
		this.lotteryLow = lotteryLow;
	}

	public Long getLotteryHigh() {
		return lotteryHigh;
	}

	public void setLotteryHigh(Long lotteryHigh) {
		this.lotteryHigh = lotteryHigh;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}
