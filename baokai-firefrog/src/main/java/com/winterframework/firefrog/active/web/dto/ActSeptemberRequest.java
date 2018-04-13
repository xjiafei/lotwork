package com.winterframework.firefrog.active.web.dto;

import java.io.Serializable;

/**
 * 
 * @author Ami.Tsai
 *
 */
public class ActSeptemberRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 971096321107590141L;

	private String token;
	private Long userId;
	
	private Integer drawLv;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getDrawLv() {
		return drawLv;
	}

	public void setDrawLv(Integer drawLv) {
		this.drawLv = drawLv;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
