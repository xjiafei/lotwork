package com.winterframework.firefrog.user.entity;

import java.io.Serializable;

public class UnFreezeDTO implements Serializable {

	private static final long serialVersionUID = 868820862698011900L;

	private Long userId;
	private Long freezeId;
	private String memo;
	private String freezeAccount;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getFreezeId() {
		return freezeId;
	}

	public void setFreezeId(Long freezeId) {
		this.freezeId = freezeId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getFreezeAccount() {
		return freezeAccount;
	}

	public void setFreezeAccount(String freezeAccount) {
		this.freezeAccount = freezeAccount;
	}

}
