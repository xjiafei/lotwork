package com.winterframework.firefrog.fund.dao.vo;

import java.util.Map;

public class ResultPager extends com.winterframework.modules.web.jsonresult.ResultPager{
	private int startNo;
	private int endNo;
	private int total;
	private Long totalinBal;
	private Long totalfrozeAmt;
	private Long totaloutBal;
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

	public Long getTotalinBal() {
		return totalinBal;
	}

	public void setTotalinBal(Long totalinBal) {
		this.totalinBal = totalinBal;
	}

	public Long getTotalfrozeAmt() {
		return totalfrozeAmt;
	}

	public void setTotalfrozeAmt(Long totalfrozeAmt) {
		this.totalfrozeAmt = totalfrozeAmt;
	}

	public Long getTotaloutBal() {
		return totaloutBal;
	}

	public void setTotaloutBal(Long totaloutBal) {
		this.totaloutBal = totaloutBal;
	}

	
}
