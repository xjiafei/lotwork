package com.winterframework.firefrog.game.dto;

import java.util.List;

public class WanguoResponse {
	private boolean status;
	private String description;
	private String sn;
	private List<WanguoRoomDto> roomList;
	private String token;
	private String account;
	
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public List<WanguoRoomDto> getRoomList() {
		return roomList;
	}
	public void setRoomList(List<WanguoRoomDto> roomList) {
		this.roomList = roomList;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	
	
	
}
