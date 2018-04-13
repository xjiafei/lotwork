package com.winterframework.firefrog.schedule.dto;


public class SBWinNumber {
	
	String number;
	
	long winrate;

	long threshold;
	
	
	
	/**
	 * @return the threshold
	 */
	public long getThreshold() {
		return threshold;
	}

	/**
	 * @param threshold the threshold to set
	 */
	public void setThreshold(long threshold) {
		this.threshold = threshold;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the winrate
	 */
	public long getWinrate() {
		return winrate;
	}
	
	/**
	 * @param winrate the winrate to set
	 */
	public void setWinrate(long winrate) {
		this.winrate = winrate;
	}
	
	
}
