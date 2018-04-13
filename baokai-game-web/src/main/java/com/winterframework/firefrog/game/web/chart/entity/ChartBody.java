package com.winterframework.firefrog.game.web.chart.entity;

import com.winterframework.firefrog.game.web.chart.ChartEntitySupport;

/**
 * @Title 走势图数据区
 * 
 * 包括多个行
 * 
 * @author bob
 *
 */
public class ChartBody extends ChartEntitySupport {
	
	// 行数据 会被formatter循环
	private ChartBodyRow rows;

	public ChartBodyRow getRows() {
		return rows;
	}

	public void setRows(ChartBodyRow rows) {
		this.rows = rows;
	}
}
