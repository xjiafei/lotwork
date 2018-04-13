package com.winterframework.firefrog.subsys.web.dto;

public class SubSysFundResponse {

	private Long id;
	private Long bal;
	private int status;
	private String errMsg;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getBal() {
		return bal;
	}

	public void setBal(Long bal) {
		this.bal = bal;
	}
	
	
	
}
