package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
* @ClassName: BetChartStruc 
* @Description: 直选辅助图表基本结构 
* @author Richard
* @date 2013-8-21 上午10:59:53 
*
 */
public class BetChartStruc implements Serializable {

	private static final long serialVersionUID = -7002554785626417799L;

	private String webIssueCode;
	private String numberRecord;
	private List<HzbaseChartStruc> hzbaseChartStrucList;//和值图表基本结构
	private List<ZuxuanbaseChartStruc> zuxuanbaseChartStrucList; //组选图表基本结构
	private List<ZxChartStruc> zxChartStrucList; //直选图表基本结构
	
	public BetChartStruc() {
		
	}

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

	/**
	 * 
	* @Title: getHzbaseChartStrucList 
	* @Description: 和值图表 
	* @return
	 */
	public List<HzbaseChartStruc> getHzbaseChartStrucList() {
		return hzbaseChartStrucList;
	}

	public void setHzbaseChartStrucList(List<HzbaseChartStruc> hzbaseChartStrucList) {
		this.hzbaseChartStrucList = hzbaseChartStrucList;
	}

	/**
	 * 
	* @Title: getZuxuanbaseChartStrucList 
	* @Description:组选图表
	* @return
	 */
	public List<ZuxuanbaseChartStruc> getZuxuanbaseChartStrucList() {
		return zuxuanbaseChartStrucList;
	}

	public void setZuxuanbaseChartStrucList(List<ZuxuanbaseChartStruc> zuxuanbaseChartStrucList) {
		this.zuxuanbaseChartStrucList = zuxuanbaseChartStrucList;
	}

	/**
	 * 
	* @Title: getZxChartStrucList 
	* @Description: 直选图表 
	* @return
	 */
	public List<ZxChartStruc> getZxChartStrucList() {
		return zxChartStrucList;
	}

	public void setZxChartStrucList(List<ZxChartStruc> zxChartStrucList) {
		this.zxChartStrucList = zxChartStrucList;
	}
	
	

}
