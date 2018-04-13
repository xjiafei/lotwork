package com.winterframework.modules.web.jsonresult;

public class Pager {
	private int startNo;
	private int endNo;

	public Pager() {
	}

	public Pager(int startNo, int endNo) {
		this.startNo = startNo;
		this.endNo = endNo;
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

}
