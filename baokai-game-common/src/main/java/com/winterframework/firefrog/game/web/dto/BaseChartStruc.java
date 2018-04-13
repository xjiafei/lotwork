/**   
* @Title: BaseChartStruc.java 
* @Package com.winterframework.firefrog.game.web.dto 
* @Description: winter-game-common.BaseChartStruc.java 
* @author Denny  
* @date 2014-4-4 下午4:14:05 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.game.entity.TrendType;

/** 
* @ClassName: BaseChartStruc 
* @Description: 走势图数据基本结构 
* @author Denny 
* @date 2014-4-4 下午4:14:05 
*  
*/
public class BaseChartStruc implements Serializable {

	private static final long serialVersionUID = 8550470133881833718L;

	private String webIssueCode;
	private String numberRecord;

	private Map<TrendType, List<String>> chartStruc;

	public String getWebIssueCode() {
		return webIssueCode;
	}

	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}

	public String getNumberRecord() {
		return numberRecord;
	}

	public void setNumberRecord(String numberRecord) {
		this.numberRecord = numberRecord;
	}

	public Map<TrendType, List<String>> getChartStruc() {
		return chartStruc;
	}

	public void setChartStruc(Map<TrendType, List<String>> chartStruc) {
		this.chartStruc = chartStruc;
	}


}
