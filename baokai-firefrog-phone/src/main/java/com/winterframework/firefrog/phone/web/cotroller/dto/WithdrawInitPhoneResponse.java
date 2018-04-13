package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

public class WithdrawInitPhoneResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8656811297700990430L;
//	private UserInfo userinfo;
	private Double availablebalance;//	可提现金额
	private List<Banks> banks;//	绑卡资料
//	private Integer count;//	已提现次数
	private Long times;//	可提现次数
	private Double maxWithdrawMoney;
	private Double lowLimit;
	private Double upLimit;
	public Double getLowLimit() {
		return lowLimit;
	}
	public void setLowLimit(Double lowLimit) {
		this.lowLimit = lowLimit;
	}
	public Double getUpLimit() {
		return upLimit;
	}
	public void setUpLimit(Double upLimit) {
		this.upLimit = upLimit;
	}
	public Double getMaxWithdrawMoney() {
		return maxWithdrawMoney;
	}
	public void setMaxWithdrawMoney(Double maxWithdrawMoney) {
		this.maxWithdrawMoney = maxWithdrawMoney;
	}
	/*public UserInfo getUserinfo() {
		return userinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}*/
	public Double getAvailablebalance() {
		return availablebalance;
	}
	public void setAvailablebalance(Double availablebalance) {
		this.availablebalance = availablebalance;
	}
	public List<Banks> getBanks() {
		return banks;
	}
	public void setBanks(List<Banks> banks) {
		this.banks = banks;
	}
	/*public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}*/
	public Long getTimes() {
		return times;
	}
	public void setTimes(Long times) {
		this.times = times;
	}
	
	
	
}
