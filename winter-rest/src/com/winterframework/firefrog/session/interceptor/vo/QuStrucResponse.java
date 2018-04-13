package com.winterframework.firefrog.session.interceptor.vo;

import java.io.Serializable;

public class QuStrucResponse implements Serializable {

	private static final long serialVersionUID = -6394618524536538989L;

	private int qu;

	
	private String ans;

	public QuStrucResponse() {

	}

	public int getQu() {
		return qu;
	}

	public void setQu(int qu) {
		this.qu = qu;
	}

	public String getAns() {
		return ans;
	}

	public void setAns(String ans) {
		this.ans = ans;
	}

}
