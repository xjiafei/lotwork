package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/** 
* @ClassName: BetChartStrucForBetUI 
* @Description: 走势图结构体 
* @author Denny 
* @date 2013-9-29 下午2:23:09 
*  
*/
public class BetChartStrucForBetUI implements Serializable {

	private static final long serialVersionUID = -8073926360021292175L;

	private String webIssueCode;
	private String numberRecord;
	
	private List<List<List<Integer>>> hzbaseChartStrucList;//和值图表基本结构
	private List<List<List<Integer>>> zuxuanbaseChartStrucList; //组选图表基本结构
	private List<List<List<Integer>>> zxChartStrucList; //直选图表基本结构

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

	public List<List<List<Integer>>> getHzbaseChartStrucList() {
		return hzbaseChartStrucList;
	}

	public void setHzbaseChartStrucList(List<List<List<Integer>>> hzbaseChartStrucList) {
		this.hzbaseChartStrucList = hzbaseChartStrucList;
	}

	public List<List<List<Integer>>> getZuxuanbaseChartStrucList() {
		return zuxuanbaseChartStrucList;
	}

	public void setZuxuanbaseChartStrucList(List<List<List<Integer>>> zuxuanbaseChartStrucList) {
		this.zuxuanbaseChartStrucList = zuxuanbaseChartStrucList;
	}

	public List<List<List<Integer>>> getZxChartStrucList() {
		return zxChartStrucList;
	}

	public void setZxChartStrucList(List<List<List<Integer>>> zxChartStrucList) {
		this.zxChartStrucList = zxChartStrucList;
	}

}
