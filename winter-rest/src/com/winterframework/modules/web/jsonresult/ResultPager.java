package com.winterframework.modules.web.jsonresult;

import java.util.Map;

public class ResultPager {
	private int startNo;
	private int endNo;
	private int total;
	private Map<String,Object> otherMap;

	public ResultPager() {
	};

	public ResultPager(int startNo, int endNo, int size) {
		this.startNo = startNo;
		this.endNo = endNo;
		this.total = size;
	}

	public int getStartNo() {
		return startNo;
	}

	public void setStartNo(int startNo) {
		this.startNo = startNo;
	}

	public int getEndNo() {
		return endNo;
	}

	public void setEndNo(int endNo) {
		this.endNo = endNo;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Map<String, Object> getOtherMap() {
		return otherMap;
	}

	public void setOtherMap(Map<String, Object> otherMap) {
		this.otherMap = otherMap;
	}

}
