package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class AddCustomizedUserRequest implements Serializable{
	
	private static final long serialVersionUID = 2438722701848004193L;
	private Integer usertype;//	用户类型
	private String username;//	用户名
	private String userpass;//	密码
	private String nickname;//昵称
	private String loginIp;//	用户IP
	public Integer getUsertype() {
		return usertype;
	}
	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserpass() {
		return userpass;
	}
	public void setUserpass(String userpass) {
		this.userpass = userpass;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	
}
