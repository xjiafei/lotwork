package com.winterframework.firefrog.game.web.chart.entity;

import com.winterframework.firefrog.game.web.chart.ChartEntitySupport;

/**
 * @Title 走势图标题栏文本
 * 
 * @author bob
 *
 */
public class ChartTitleText extends ChartEntitySupport {
	
	// 显示文本
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
