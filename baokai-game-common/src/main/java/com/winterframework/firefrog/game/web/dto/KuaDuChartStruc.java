/**   
* @Title: KuaDuChartStruc.java 
* @Package com.winterframework.firefrog.game.web.dto 
* @Description: winter-game-common.KuaDuChartStruc.java 
* @author Denny  
* @date 2014-4-4 下午4:35:39 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: KuaDuChartStruc 
* @Description: 跨度遗漏数据结构
* @author Denny 
* @date 2014-4-4 下午4:35:39 
*  
*/
public class KuaDuChartStruc implements Serializable {

	private static final long serialVersionUID = 5957776976049042276L;

	/**数据格式 ：跨度值|遗漏值|是否高亮 */
	private String chartContent;

	public String getChartContent() {
		return chartContent;
	}

	public void setChartContent(String chartContent) {
		this.chartContent = chartContent;
	}
}
