package com.winterframework.firefrog.notice.web.dto;

import java.util.List;

public class MsgInfoResponse {
	
	private Long isSuccess;
	
	private String type;
	
	private String msg;
	
	private List<MsgInfo> data;

	public Long getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Long isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<MsgInfo> getData() {
		return data;
	}

	public void setData(List<MsgInfo> data) {
		this.data = data;
	}

}
