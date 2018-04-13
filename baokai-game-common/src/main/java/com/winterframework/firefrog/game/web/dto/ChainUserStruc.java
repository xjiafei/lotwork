package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/**
 * 存储userchain结构
 * 
 * @author bob
 */
public class ChainUserStruc implements Serializable {

	private static final long serialVersionUID = 3942279875308789066L;

	/* 用户名 */
	private String account;
	/* 用户id */
	private Long userId;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
