package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;


public class WithdrawInitRequest implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3244045253104050010L;
	private Long userId;
	private Long vipLvl;
	private Long bindcardType;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getVipLvl() {
		return vipLvl;
	}
	public void setVipLvl(Long vipLvl) {
		this.vipLvl = vipLvl;
	}
	public Long getBindcardType() {
		return bindcardType;
	}
	public void setBindcardType(Long bindcardType) {
		this.bindcardType = bindcardType;
	}

}
