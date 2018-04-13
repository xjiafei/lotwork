package com.winterframework.firefrog.beginmession.dao.vo;

import java.io.Serializable;
import java.util.Date;


public class BeginLotterySet implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -5663403648388611957L;

	private String createUser;

	private Date createTime;

	private String modifyUser;

	private Date modifyTime;

	private Long lotteryType;

	private Long lotteryUnit;

	private Long lotteryLow;

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
