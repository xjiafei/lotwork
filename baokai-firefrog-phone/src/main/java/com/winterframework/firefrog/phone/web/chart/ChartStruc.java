package com.winterframework.firefrog.phone.web.chart;

import java.io.Serializable;
import java.util.List;

 
/**
 *ChartStruc
 * @ClassName
 * @Description
 * @author ibm
 * 2017年10月24日
 */
public class ChartStruc implements Serializable {
 

	/**
	 * 
	 */
	private static final long serialVersionUID = -7207600857387565622L;
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
