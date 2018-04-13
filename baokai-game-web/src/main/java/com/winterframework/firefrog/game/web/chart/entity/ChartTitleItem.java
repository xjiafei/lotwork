package com.winterframework.firefrog.game.web.chart.entity;

import java.util.List;

import com.winterframework.firefrog.game.web.chart.ChartEntitySupport;

/**
 * @Title 走势标题栏项目
 * 
 * @author bob
 *
 */
public class ChartTitleItem extends ChartEntitySupport {
	
	// 走势标题栏项目内格子
	private List<ChartTitleItemPane> panes;

	public List<ChartTitleItemPane> getPanes() {
		return panes;
	}

	public void setPanes(List<ChartTitleItemPane> panes) {
		this.panes = panes;
	}

}
