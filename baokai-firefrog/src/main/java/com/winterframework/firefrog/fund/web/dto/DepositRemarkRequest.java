package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;

public class DepositRemarkRequest implements Serializable {

	private static final long serialVersionUID = 3872628215895612548L;

	private Long typeId;

	private String memo;

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
