package com.winterframework.firefrog.game.web.chart.entity;

import com.winterframework.firefrog.game.web.chart.ChartEntitySupport;

/**
 * @Title 走势图数据区行内分组
 * 
 * @author bob
 *
 */
public class ChartBodyRowGroup extends ChartEntitySupport {
	
	// 走势图数据区行内分组内格子
	private ChartBodyRowGroupPane panes;

	public ChartBodyRowGroupPane getPanes() {
		return panes;
	}

	public void setPanes(ChartBodyRowGroupPane panes) {
		this.panes = panes;
	}
	
}
