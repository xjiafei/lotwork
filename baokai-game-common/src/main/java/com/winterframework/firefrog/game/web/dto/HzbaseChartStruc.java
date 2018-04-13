package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
/**
 * 
* @ClassName: HzbaseChartStruc 
* @Description: 和值图表基本结构 
* @author Richard
* @date 2013-8-21 上午10:56:57 
*
 */
public class HzbaseChartStruc implements Serializable {

	private static final long serialVersionUID = -764409691900146090L;

	private String chartContent;
	
	public HzbaseChartStruc() {
		
	}

	public String getChartContent() {
		return chartContent;
	}

	public void setChartContent(String chartContent) {
		this.chartContent = chartContent;
	}
	
	
}
