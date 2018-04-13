/**   
* @Title: WeiShuChartStruc.java 
* @Package com.winterframework.firefrog.game.web.dto 
* @Description: winter-game-common.WeiShuChartStruc.java 
* @author Denny  
* @date 2014-4-4 下午4:30:20 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: WeiShuChartStruc 
* @Description: 位数遗漏数据结构
* @author Denny 
* @date 2014-4-4 下午4:30:20 
*  
*/
public class WeiShuChartStruc implements Serializable {

	private static final long serialVersionUID = 5213680338803518292L;

	/** 数据格式：位数|号球|遗漏值|是否高亮 */
	private String chartContent;

	public String getChartContent() {
		return chartContent;
	}

	public void setChartContent(String chartContent) {
		this.chartContent = chartContent;
	}
	
	
}
