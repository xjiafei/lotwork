/**   
* @Title: HeZhiChartStruc.java 
* @Package com.winterframework.firefrog.game.web.dto 
* @Description: winter-game-common.HeZhiChartStruc.java 
* @author Denny  
* @date 2014-4-4 下午4:22:29 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: HeZhiChartStruc 
* @Description: 和值遗漏数据结构 
* @author Denny 
* @date 2014-4-4 下午4:22:29 
*  
*/
public class HeZhiChartStruc implements Serializable{

	private static final long serialVersionUID = -8900189572761076119L;
	
	/**数据格式 ：和值|遗漏值|是否高亮 */
	private String chartContent;

	public String getChartContent() {
		return chartContent;
	}

	public void setChartContent(String chartContent) {
		this.chartContent = chartContent;
	}
	
	
}
