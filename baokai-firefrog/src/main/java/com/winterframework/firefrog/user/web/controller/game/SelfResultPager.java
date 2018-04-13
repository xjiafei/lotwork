package com.winterframework.firefrog.user.web.controller.game;

import java.util.HashMap;
import java.util.Map;

import com.winterframework.modules.web.jsonresult.ResultPager;

public class SelfResultPager extends ResultPager {
	public SelfResultPager(int startNo, int endNo, int size) {
		super(startNo, endNo, size);
	}

	private Map<String, Object> otherParam = new HashMap<String, Object>();

	
	public Map<String, Object> getOtherParam() {
		return otherParam;
	}

	public void setOtherParam(Map<String, Object> otherParam) {
		this.otherParam = otherParam;
	}

}
