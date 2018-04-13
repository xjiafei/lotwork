package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.phone.web.chart.ChartStruc;
import com.winterframework.firefrog.phone.web.chart.ChartSubStruc;

public class ChartResponse implements Serializable { 

	/**
	 * 
	 */
	private static final long serialVersionUID = 7612292776365381960L;
	 
	private List<ChartSubStruc> data;
	private List<Object> statistics;
	public List<ChartSubStruc> getData() {
		return data;
	}
	public void setData(List<ChartSubStruc> data) {
		this.data = data;
	}
	public List<Object> getStatistics() {
		return statistics;
	}
	public void setStatistics(List<Object> statistics) {
		this.statistics = statistics;
	}

	
	
	
}
