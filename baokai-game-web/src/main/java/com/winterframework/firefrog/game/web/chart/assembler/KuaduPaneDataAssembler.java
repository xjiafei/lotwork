package com.winterframework.firefrog.game.web.chart.assembler;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.winterframework.firefrog.game.web.chart.IChartBodyRowGroupPaneDataAssembler;
import com.winterframework.firefrog.game.web.chart.data.ChartBodyRowGroupPaneData;

/**
 * @Title 跨度数据组装器
 * 
 * 跨度
 * “跨度值”+“|”+“遗漏或冷热值”+“|”+“是否高亮”
 * 
 * 和值范围：后三0-9，后二0-9
 * 高亮取值：0 不高亮、1 高亮
 * 每组取值以逗号”,”分隔
 * 参数间以竖杠“|”分隔
 * 
 * @author bob
 *
 */
public class KuaduPaneDataAssembler implements IChartBodyRowGroupPaneDataAssembler {

	@Override
	public void assemble(ChartBodyRowGroupPaneData bodyItemPane, String ball, Map<String, String> styles, String numberRecordFormat) {
		
		String[] ballV = StringUtils.split(ball, "|");

		if ("1".equals(ballV[2])) {
			bodyItemPane.setPane(String.valueOf(ballV[0]));
			bodyItemPane.setStyleClass(styles.get("kuaduTdStyle"));
		} else {
			bodyItemPane.setPane(String.valueOf(ballV[1]));
			bodyItemPane.setStyleClass("");
		}
	}

}
