package com.winterframework.firefrog.common.redis;

import java.io.Serializable;

public class ConfigRedisResponse implements Serializable {

	private static final long serialVersionUID = 1902056092745600096L;

	private String val;

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

}
