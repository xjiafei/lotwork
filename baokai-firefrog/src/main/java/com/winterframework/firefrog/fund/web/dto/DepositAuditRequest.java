package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class DepositAuditRequest implements Serializable {

	private static final long serialVersionUID = 7055073859751864165L;
	@NotNull
	private Long id;
	private String memo;

	private Long status;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
