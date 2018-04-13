package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
* @ClassName: BaseTrendChartStruc 
* @Description: 号码走势表基本结构
* @author Richard
* @date 2013-9-5 下午2:54:56 
*
 */
public class BaseTrendChartStruc implements Serializable {

	private static final long serialVersionUID = -1955973983975566906L;

	/**出现总次数*/
	private List<Integer> totalCount;
	/**平均遗漏值*/
	private List<Integer> avgOmit;
	/**最大遗漏值*/
	private List<Integer> maxOmit;
	/**最大连出值*/
	private List<Integer> maxEven;
	
	private List<BaseChartStruc> baseChartStrucs;
	
	public BaseTrendChartStruc(){
		
	}

	public List<Integer> getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(List<Integer> totalCount) {
		this.totalCount = totalCount;
	}

	public List<Integer> getAvgOmit() {
		return avgOmit;
	}

	public void setAvgOmit(List<Integer> avgOmit) {
		this.avgOmit = avgOmit;
	}

	public List<Integer> getMaxOmit() {
		return maxOmit;
	}

	public void setMaxOmit(List<Integer> maxOmit) {
		this.maxOmit = maxOmit;
	}

	public List<Integer> getMaxEven() {
		return maxEven;
	}

	public void setMaxEven(List<Integer> maxEven) {
		this.maxEven = maxEven;
	}

	public List<BaseChartStruc> getBaseChartStrucs() {
		return baseChartStrucs;
	}

	public void setBaseChartStrucs(List<BaseChartStruc> baseChartStrucs) {
		this.baseChartStrucs = baseChartStrucs;
	}

}
