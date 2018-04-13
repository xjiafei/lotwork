/**   
* @Title: ZuXuanChartStruc.java 
* @Package com.winterframework.firefrog.game.web.dto 
* @Description: winter-game-common.ZuXuanChartStruc.java 
* @author Denny  
* @date 2014-4-4 下午4:25:51 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: ZuXuanChartStruc 
* @Description: 组选遗漏数据结构
* @author Denny 
* @date 2014-4-4 下午4:25:51 
*  
*/
public class ZuXuanChartStruc implements Serializable {

	private static final long serialVersionUID = -4015169169191341505L;

	/** 数据格式：组选类型|遗漏值|是否高亮 */
	private String chartContent;

	public String getChartContent() {
		return chartContent;
	}

	public void setChartContent(String chartContent) {
		this.chartContent = chartContent;
	}
	
	
}
