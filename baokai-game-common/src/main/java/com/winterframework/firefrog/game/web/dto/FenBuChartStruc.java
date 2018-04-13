/**   
* @Title: FenBuChartStruc.java 
* @Package com.winterframework.firefrog.game.web.dto 
* @Description: winter-game-common.FenBuChartStruc.java 
* @author Denny  
* @date 2014-4-4 下午4:33:54 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: FenBuChartStruc 
* @Description: 号码分布遗漏数据结构 
* @author Denny 
* @date 2014-4-4 下午4:33:54 
*  
*/
public class FenBuChartStruc implements Serializable {

	private static final long serialVersionUID = 7723172329030000597L;

	/** 数据格式：号球|遗漏值|是否高亮|单号或重号 */
	private String chartContent;

	public String getChartContent() {
		return chartContent;
	}

	public void setChartContent(String chartContent) {
		this.chartContent = chartContent;
	}
}
