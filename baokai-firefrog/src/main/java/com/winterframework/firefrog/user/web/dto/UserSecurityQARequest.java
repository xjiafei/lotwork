package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

public class UserSecurityQARequest implements Serializable {

	private static final long serialVersionUID = -601836166159035521L;

	private QuStrucResponse quStruc[];

	public QuStrucResponse[] getQuStruc() {
		return quStruc;
	}

	public void setQuStruc(QuStrucResponse[] quStruc) {
		this.quStruc = quStruc;
	}

}
