package com.winterframework.firefrog.session.interceptor.vo;

import java.io.Serializable;

public class QQStruc implements Serializable {

	private static final long serialVersionUID = -7790870506669359730L;

	private long qq;

	private String nickName;

	public long getQq() {
		return qq;
	}

	public void setQq(long qq) {
		this.qq = qq;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}
