package com.winterframework.firefrog.game.web.dto;

import java.util.List;

public class MmcRankingResponse {
	private Boolean start;
	private MmcRankingDataDto data;
	public Boolean getStart() {
		return start;
	}
	public void setStart(Boolean start) {
		this.start = start;
	}
	public MmcRankingDataDto getData() {
		return data;
	}
	public void setData(MmcRankingDataDto data) {
		this.data = data;
	}
	
	
}
