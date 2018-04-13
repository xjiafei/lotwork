package com.winterframework.firefrog.active.web.dto;

import java.io.Serializable;

public class Act20160903Request  implements Serializable{
	
	private static final long serialVersionUID = -3605523728033733258L;

	private Long userId;
	
	private Integer vipLvl;
	
	private String token; 

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getVipLvl() {
		return vipLvl;
	}

	public void setVipLvl(Integer vipLvl) {
		this.vipLvl = vipLvl;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
