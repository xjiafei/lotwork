package com.winterframework.firefrog.active.web.dto;

public class Act20160903Response {

	private boolean join;
	
	private boolean finished;
	
	private long prize;
	
	private String level;
	
	private int day;
	
	private boolean betToday;
	
	private int viplvl;

	public boolean isJoin() {
		return join;
	}

	public void setJoin(boolean join) {
		this.join = join;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public long getPrize() {
		return prize;
	}

	public void setPrize(long prize) {
		this.prize = prize;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public boolean isBetToday() {
		return betToday;
	}

	public void setBetToday(boolean betToday) {
		this.betToday = betToday;
	}

	public int getViplvl() {
		return viplvl;
	}

	public void setViplvl(int viplvl) {
		this.viplvl = viplvl;
	}
	
	
}
