package com.winterframework.firefrog.sample.web;

import com.winterframework.modules.web.jsonresult.Pager;

public class User2 {
	private long lotteryID;
	private long timePeriod;
	private String drawNo;

	private long endTime;
	private String queryBPID;
	private Pager pager;

	public long getLotteryID() {
		return lotteryID;
	}

	public void setLotteryID(long lotteryID) {
		this.lotteryID = lotteryID;
	}

	public long getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(long timePeriod) {
		this.timePeriod = timePeriod;
	}

	public String getDrawNo() {
		return drawNo;
	}

	public void setDrawNo(String drawNo) {
		this.drawNo = drawNo;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getQueryBPID() {
		return queryBPID;
	}

	public void setQueryBPID(String queryBPID) {
		this.queryBPID = queryBPID;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

}
