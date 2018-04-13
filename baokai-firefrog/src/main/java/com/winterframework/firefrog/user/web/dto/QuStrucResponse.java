package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.winterframework.modules.validate.FFLength;

public class QuStrucResponse implements Serializable {

	private static final long serialVersionUID = -6394618524536538989L;

	@Min(1)
	private int qu;

	@NotNull
	@NotEmpty
	@FFLength(max = 32, min = 32)
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
