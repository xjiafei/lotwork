package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

public class ActivityDoubleboxRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6215006112016441667L;
	private Long userId;
	private List<String> betTypeCodes;
	
	public List<String> getBetTypeCodes() {
		return betTypeCodes;
	}

	public void setBetTypeCodes(List<String> betTypeCodes) {
		this.betTypeCodes = betTypeCodes;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
}
