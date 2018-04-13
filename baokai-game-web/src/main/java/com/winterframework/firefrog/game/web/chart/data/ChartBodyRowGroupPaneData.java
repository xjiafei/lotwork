package com.winterframework.firefrog.game.web.chart.data;

/**
 * @Title 走势图数据区行内分组内格子数据
 * 
 * @author bob
 *
 */
public class ChartBodyRowGroupPaneData {
	
	// 显示内容
	private String pane;
	
	// 样式
	private String styleClass;
	
	// 是否第一个格子 
	private boolean first = false;
	
	// 是否最后一个格子
	private boolean last = false;

	public String getPane() {
		return pane;
	}

	public void setPane(String pane) {
		this.pane = pane;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

}
