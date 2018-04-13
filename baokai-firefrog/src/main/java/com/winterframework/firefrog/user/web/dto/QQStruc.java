package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.winterframework.modules.validate.FFLength;

public class QQStruc implements Serializable {

	private static final long serialVersionUID = -7790870506669359730L;

	@Min(10000)
	private long qq;

	@NotNull
	@NotEmpty
	@FFLength(max = 16, min = 4)
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
