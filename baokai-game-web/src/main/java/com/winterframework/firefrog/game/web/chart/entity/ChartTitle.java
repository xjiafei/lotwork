package com.winterframework.firefrog.game.web.chart.entity;

import java.util.List;

import com.winterframework.firefrog.game.web.chart.ChartEntitySupport;

/**
 * @Title 走势图标题栏
 * 
 * @author bob
 *
 */
public class ChartTitle extends ChartEntitySupport {
	
	// 走势图标题栏文本栏
	private List<ChartTitleText> texts;
	
	// 走势图标题栏项目
	private List<ChartTitleItem> items;

	public List<ChartTitleText> getTexts() {
		return texts;
	}

	public void setTexts(List<ChartTitleText> texts) {
		this.texts = texts;
	}

	public List<ChartTitleItem> getItems() {
		return items;
	}

	public void setItems(List<ChartTitleItem> items) {
		this.items = items;
	}

}
