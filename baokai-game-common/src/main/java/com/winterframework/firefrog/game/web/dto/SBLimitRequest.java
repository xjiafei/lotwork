package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;


public class SBLimitRequest implements Serializable {
	
	String  Threshold;
	Long    slipCount;
	
	
	/**
	 * @return the slipCount
	 */
	public Long getSlipCount() {
		return slipCount;
	}

	/**
	 * @param slipCount the slipCount to set
	 */
	public void setSlipCount(Long slipCount) {
		this.slipCount = slipCount;
	}

	/**
	 * @return the threshold
	 */
	public String getThreshold() {
		return Threshold;
	}

	/**
	 * @param threshold the threshold to set
	 */
	public void setThreshold(String threshold) {
		Threshold = threshold;
	}
	

}
