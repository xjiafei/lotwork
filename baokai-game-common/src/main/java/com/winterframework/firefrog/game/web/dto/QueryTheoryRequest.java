package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

public class QueryTheoryRequest implements Serializable {

	private static final long serialVersionUID = 5733526334899719108L;

	private Long lotteyId;
	
	public QueryTheoryRequest(){
		
	}

	public Long getLotteyId() {
		return lotteyId;
	}

	public void setLotteyId(Long lotteyId) {
		this.lotteyId = lotteyId;
	}
	
}
