package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;


public class UserSecurityQARequest implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5751736191188873149L;
	private QuStrucResponse quStruc[];

	public QuStrucResponse[] getQuStruc() {
		return quStruc;
	}

	public void setQuStruc(QuStrucResponse[] quStruc) {
		this.quStruc = quStruc;
	}
}
