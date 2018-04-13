package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.winterframework.modules.validate.FFLength;

public class UserSecurityCipherRequest implements Serializable {

	private static final long serialVersionUID = -7018035999635767655L;

	@NotNull
	@NotEmpty
	@FFLength(max = 16, min = 4)
	private String cipher;

	public String getCipher() {
		return cipher;
	}

	public void setCipher(String cipher) {
		this.cipher = cipher;
	}

}
