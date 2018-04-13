package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
* @ClassName: TrendChartStruc 
* @Description: 走势取值基本结构
* @author Richard
* @date 2013-9-5 下午2:47:25 
*
 */
public class TrendChartStruc implements Serializable {

	private static final long serialVersionUID = -6527092298981225182L;

	private List<ChartStruc> chartStrucList;
	private Long webIssueCode;
	private String numberRecord;

	public List<ChartStruc> getChartStrucList() {
		return chartStrucList;
	}

	public void setChartStrucList(List<ChartStruc> chartStrucList) {
		this.chartStrucList = chartStrucList;
	}

	public Long getWebIssueCode() {
		return webIssueCode;
	}

	public void setWebIssueCode(Long webIssueCode) {
		this.webIssueCode = webIssueCode;
	}

	public String getNumberRecord() {
		return numberRecord;
	}

	public void setNumberRecord(String numberRecord) {
		this.numberRecord = numberRecord;
	}
	
}
