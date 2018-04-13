package com.winterframework.firefrog.game.web.chart.selector;

import com.winterframework.firefrog.game.web.chart.IChartDataSelector;
import com.winterframework.firefrog.game.web.chart.data.ChartBodyRowData;

/**
 * @Title 走势图数据区行内分组数据筛选器
 * 
 * 从行数据中筛选出格子数据
 * 
 * @author bob
 *
 */
public class ChartBodyRowGroupDataSelector implements IChartDataSelector {

	@Override
	public Object select(Object data) {
		
		ChartBodyRowData bodyRowData = (ChartBodyRowData) data;
		
		return bodyRowData.getGroups();
	}

}
