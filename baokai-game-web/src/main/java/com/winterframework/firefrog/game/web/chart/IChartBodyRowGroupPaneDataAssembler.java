package com.winterframework.firefrog.game.web.chart;

import java.util.Map;

import com.winterframework.firefrog.game.web.chart.data.ChartBodyRowGroupPaneData;

public interface IChartBodyRowGroupPaneDataAssembler {
	
	public void assemble(ChartBodyRowGroupPaneData bodyItemPane, String balls, Map<String, String> styles, String numberRecordFormat);

}
