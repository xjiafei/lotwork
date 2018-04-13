package com.winterframework.firefrog.game.web.chart.entity;

import com.winterframework.firefrog.game.web.chart.ChartEntitySupport;

/**
 * @Title 走势图数据区行
 * 
 * @author bob
 *
 */
public class ChartBodyRow extends ChartEntitySupport {
	
	// 走势图数据区行内分组
	private ChartBodyRowGroup groups;

	public ChartBodyRowGroup getGroups() {
		return groups;
	}

	public void setGroups(ChartBodyRowGroup groups) {
		this.groups = groups;
	}

}
