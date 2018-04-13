package com.winterframework.firefrog.game.web.chart.assembler;

import java.util.Map;

import com.winterframework.firefrog.game.web.chart.IChartBodyRowGroupPaneDataAssembler;
import com.winterframework.firefrog.game.web.chart.data.ChartBodyRowGroupPaneData;

/**
 * @Title 显示奖期号
 * 
 * @author bob
 *
 */
public class WebIssueCodePaneDataAssembler implements IChartBodyRowGroupPaneDataAssembler {

	@Override
	public void assemble(ChartBodyRowGroupPaneData bodyItemPane, String ball, Map<String, String> styles, String numberRecordFormat) {
		bodyItemPane.setPane(String.valueOf(ball));
		bodyItemPane.setStyleClass("");
	}

}
