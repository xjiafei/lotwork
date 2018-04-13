package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/**
 * 
* @ClassName: ZxChartStruc 
* @Description: 直选图表基本结构
* @author Richard
* @date 2013-8-21 上午10:49:39 
*
 */
public class ZxChartStruc implements Serializable{

	private static final long serialVersionUID = -4682598419270318506L;
	
	private String chartContent;
	
	public ZxChartStruc() {
		
	}

	public String getChartContent() {
		return chartContent;
	}

	public void setChartContent(String chartContent) {
		this.chartContent = chartContent;
	}
	
}
