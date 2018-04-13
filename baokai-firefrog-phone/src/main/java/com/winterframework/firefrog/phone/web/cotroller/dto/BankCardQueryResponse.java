package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;


public class BankCardQueryResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6125373200024054251L;
	/** 绑卡记录 */
	private List<UserBankStruc> userBankStruc;
	/** 绑卡超时时间 */
	private long overTime;

	public List<UserBankStruc> getUserBankStruc() {
		return userBankStruc;
	}

	public void setUserBankStruc(List<UserBankStruc> userBankStruc) {
		this.userBankStruc = userBankStruc;
	}

	public long getOverTime() {
		return overTime;
	}

	public void setOverTime(long overTime) {
		this.overTime = overTime;
	}

}
