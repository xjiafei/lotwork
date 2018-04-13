package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/**
 * 
* @ClassName: ZuxuanbaseChartStruc 
* @Description: 组选图表基本结构 
* @author Richard
* @date 2013-8-21 上午10:52:59 
*
 */
public class ZuxuanbaseChartStruc implements Serializable {

	private static final long serialVersionUID = 8378844964210170708L;

	private String chartContent;
	
	public ZuxuanbaseChartStruc(){
		
	}

	public String getChartContent() {
		return chartContent;
	}

	public void setChartContent(String chartContent) {
		this.chartContent = chartContent;
	}
	
}
