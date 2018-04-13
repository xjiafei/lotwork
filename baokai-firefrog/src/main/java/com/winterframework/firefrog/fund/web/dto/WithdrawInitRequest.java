package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class WithdrawInitRequest implements Serializable {

	private static final long serialVersionUID = -4133962219769262589L;
	@NotNull
	private Long userId;
	private Long vipLvl;
	private Long bindcardType;

	public WithdrawInitRequest() {

	}

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
