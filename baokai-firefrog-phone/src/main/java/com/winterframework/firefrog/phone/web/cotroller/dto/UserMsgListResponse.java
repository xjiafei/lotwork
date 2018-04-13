package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

public class UserMsgListResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2938536112301171920L;
	private List<UserMsgListDto> list;

	public List<UserMsgListDto> getList() {
		return list;
	}

	public void setList(List<UserMsgListDto> list) {
		this.list = list;
	}
}
