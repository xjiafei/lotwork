package com.winterframework.firefrog.game.web.chart.entity;

import com.winterframework.firefrog.game.web.chart.ChartEntitySupport;


/**
 * @Title 走势标题栏项目内格子
 * 
 * @author bob
 *
 */
public class ChartTitleItemPane extends ChartEntitySupport {
	
	// 格子内显示内容
	private String pane;

	public String getPane() {
		return pane;
	}

	public void setPane(String pane) {
		this.pane = pane;
	}

}
