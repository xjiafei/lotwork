package com.winterframework.firefrog.user.entity;

import java.io.Serializable;
import java.util.Date;

public class FreezeDTO implements Serializable {

	private static final long serialVersionUID = -5737610096742680703L;

	private Long userId;
	private Long freezeId;
	private Integer freezeMethod;
	private Date freezeDate;
	private String memo;
	private String freezeAccount;
	private Long id;

	public FreezeDTO() {

	}

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

	public Integer getFreezeMethod() {
		return freezeMethod;
	}

	public void setFreezeMethod(Integer freezeMethod) {
		this.freezeMethod = freezeMethod;
	}

	public Date getFreezeDate() {
		return freezeDate;
	}

	public void setFreezeDate(Date freezeDate) {
		this.freezeDate = freezeDate;
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
