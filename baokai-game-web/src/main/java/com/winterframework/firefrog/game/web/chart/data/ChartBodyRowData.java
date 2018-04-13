package com.winterframework.firefrog.game.web.chart.data;

import java.util.List;


/**
 * @Title 走势图数据区行数据
 * 
 * @author bob
 *
 */
public class ChartBodyRowData {
	
	private List<ChartBodyRowGroupData> groups;

	public List<ChartBodyRowGroupData> getGroups() {
		return groups;
	}

	public void setGroups(List<ChartBodyRowGroupData> groups) {
		this.groups = groups;
	}

}
