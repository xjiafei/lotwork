package com.winterframework.firefrog.game.web.chart.assembler;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.winterframework.firefrog.game.web.chart.IChartBodyRowGroupPaneDataAssembler;
import com.winterframework.firefrog.game.web.chart.data.ChartBodyRowGroupPaneData;

/**
 * @Title 组选数据组装器
 * 
 * 组选
 * “组选类型”+“|”+“组选遗漏值”+“|”+“是否高亮”
 * 
 * 号球范围：0-9
 * 单重号取值:0单号、1重号、2未出号码
 * 高亮取值：0 不高亮、1 高亮
 * 
 * @author bob
 *
 */
public class ZuxuanPaneDataAssembler implements IChartBodyRowGroupPaneDataAssembler {

	@Override
	public void assemble(ChartBodyRowGroupPaneData bodyItemPane, String ball, Map<String, String> styles, String numberRecordFormat) {
		
		String[] ballV = StringUtils.split(ball, "|");

		if ("1".equals(ballV[2])) {
			bodyItemPane.setPane(styles.get("zuxuanHighLightPane"));
		} else {
			bodyItemPane.setPane(String.valueOf(ballV[1]));
		}
	}

}
