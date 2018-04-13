package com.winterframework.firefrog.game.web.chart.assembler;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.winterframework.firefrog.game.web.chart.IChartBodyRowGroupPaneDataAssembler;
import com.winterframework.firefrog.game.web.chart.data.ChartBodyRowGroupPaneData;

/**
 * @Title 号码分布数据组装
 * 
 * 号码分布
 * “号球”+“|” +“遗漏或冷热值”+“|” +“是否高亮”+“|”+“单号或重号”
 * 
 * 号球范围：0-9
 * 单重号取值:0单号、1重号、2未出号码
 * 高亮取值：0 不高亮、1 高亮
 * 
 * @author bob
 *
 */
public class HmfbPaneDataAssembler implements IChartBodyRowGroupPaneDataAssembler {

	@Override
	public void assemble(ChartBodyRowGroupPaneData bodyItemPane, String ball, Map<String, String> styles, String numberRecordFormat) {
		
		String[] ballV = StringUtils.split(ball, "|");

		if ("1".equals(ballV[2])) {
			bodyItemPane.setPane(String.valueOf(ballV[0]));
			if ("1".equals(ballV[3])) {
				bodyItemPane.setStyleClass(styles.get("purpleStyle"));
			} else {
				bodyItemPane.setStyleClass(styles.get("orangeStyle"));
			}
		} else {
			bodyItemPane.setPane(String.valueOf(ballV[1]));
			bodyItemPane.setStyleClass(styles.get("normalStyle"));
		}
	}

}
