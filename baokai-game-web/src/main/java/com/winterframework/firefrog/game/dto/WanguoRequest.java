package com.winterframework.firefrog.game.dto;

public class WanguoRequest {
	private String account;
	private String ip;
	private Long clientType;
	private Long gameType;
	private Long amount;
	private Long isFree;
	private String channel;
	private String sn;
	private String token;
	private Long userId;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Long getGameType() {
		return gameType;
	}
	public void setGameType(Long gameType) {
		this.gameType = gameType;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
	public Long getClientType() {
		return clientType;
	}
	public void setClientType(Long clientType) {
		this.clientType = clientType;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public Long getIsFree() {
		return isFree;
	}
	public void setIsFree(Long isFree) {
		this.isFree = isFree;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	
}
