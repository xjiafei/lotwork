package com.winterframework.firefrog.game.web.chart.assembler;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.winterframework.firefrog.game.web.chart.IChartBodyRowGroupPaneDataAssembler;
import com.winterframework.firefrog.game.web.chart.data.ChartBodyRowGroupPaneData;

/**
 * @Title 位数分布数据组装
 * 
 * 位数分布
 * “位数”+“|”+“号球”+“|”+“遗漏或冷热值”+“|”+“是否高亮”
 * 
 * 位数范围:万（5）、千（4）、百（3）、十（2）、个（1）位
 * 号球范围：0-9
 * 高亮取值：0 不高亮、1 高亮
 * 每组取值以逗号”,”分隔
 * 参数间以竖杠“|”分隔
 * 
 * @author bob
 *
 */
public class WsfbPaneDataAssembler implements IChartBodyRowGroupPaneDataAssembler {
	
	public void assemble(ChartBodyRowGroupPaneData bodyItemPane, String ball, Map<String, String> styles, String numberRecordFormat) {
		
		String[] ballV = StringUtils.split(ball, "|");
		
		if ("1".equals(ballV[3])) {
			bodyItemPane.setStyleClass(styles.get("orangeStyle"));
			bodyItemPane.setPane(String.valueOf(ballV[1]));
		} else {
			bodyItemPane.setStyleClass(styles.get("normalStyle"));
			bodyItemPane.setPane(String.valueOf(ballV[2]));
		}
	}

}
