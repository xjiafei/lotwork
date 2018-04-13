package com.winterframework.firefrog.game.web.chart.entity;

import com.winterframework.firefrog.game.web.chart.ChartEntitySupport;

/**
 * @Title 走势图
 * 
 * 包括标题栏和数据区
 * 
 * @author bob
 *
 */
public class Chart extends ChartEntitySupport {

	// 走势图标题栏
	private ChartTitle title;

	// 走势图数据区
	private ChartBody body;

	public ChartTitle getTitle() {
		return title;
	}

	public void setTitle(ChartTitle title) {
		this.title = title;
	}

	public ChartBody getBody() {
		return body;
	}

	public void setBody(ChartBody body) {
		this.body = body;
	}

}
