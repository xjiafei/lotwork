package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class SearchUserRequest  implements Serializable{
	
	private static final long serialVersionUID = 7425173062897563007L;
	private Integer chan_id;//	频道id
	private String username	;//用户帐号
	public Integer getChan_id() {
		return chan_id;
	}
	public void setChan_id(Integer chan_id) {
		this.chan_id = chan_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
