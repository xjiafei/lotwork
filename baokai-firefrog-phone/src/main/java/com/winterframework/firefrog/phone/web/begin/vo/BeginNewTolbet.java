package com.winterframework.firefrog.phone.web.begin.vo;

import java.util.Date;

import com.winterframework.firefrog.phone.web.begin.annotation.LogContent;
import com.winterframework.firefrog.phone.web.begin.annotation.MoneyField;
import com.winterframework.orm.dal.ibatis3.BaseEntity;


public class BeginNewTolbet extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3495145789855639906L;

	private String createUser;

	private Date createTime;

	private String modifyUser;

	private Date modifyTime;

	@LogContent(title="累计投注-累计投注天数")
	private Long tolbetDate;
	
	@LogContent(title="累计投注-是否發放奖励金额")
	private String isAmount;

	@MoneyField
	@LogContent(title="累计投注-奖励金额")
	private Long amount;

	@LogContent(title="累计投注-是否抽奖机会")
	private String isLottery;

	@LogContent(title="累计投注-抽奖机会")
	private Long lottery;

	@LogContent(title="累计投注-抽獎類型",lotteryType=true)
	private Long lotteryType;

	private Long version;
	
	private Long rownum;
	
	private Boolean isSuccess;

	private Integer successStatus;
	
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

	public Long getTolbetDate() {
		return tolbetDate;
	}

	public void setTolbetDate(Long tolbetDate) {
		this.tolbetDate = tolbetDate;
	}

	public String getIsAmount() {
		return isAmount;
	}

	public void setIsAmount(String isAmount) {
		this.isAmount = isAmount;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getIsLottery() {
		return isLottery;
	}

	public void setIsLottery(String isLottery) {
		this.isLottery = isLottery;
	}

	public Long getLottery() {
		return lottery;
	}

	public void setLottery(Long lottery) {
		this.lottery = lottery;
	}

	public Long getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Long lotteryType) {
		this.lotteryType = lotteryType;
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

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Integer getSuccessStatus() {
		return successStatus;
	}

	public void setSuccessStatus(Integer successStatus) {
		this.successStatus = successStatus;
	}

}
