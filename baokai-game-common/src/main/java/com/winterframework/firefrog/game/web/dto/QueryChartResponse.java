package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

 
public class QueryChartResponse implements Serializable { 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3866143399361884626L;
	private GameTrendChartStruc trendChartStruc;
	public GameTrendChartStruc getTrendChartStruc() {
		return trendChartStruc;
	}
	public void setTrendChartStruc(GameTrendChartStruc trendChartStruc) {
		this.trendChartStruc = trendChartStruc;
	}


	
}
