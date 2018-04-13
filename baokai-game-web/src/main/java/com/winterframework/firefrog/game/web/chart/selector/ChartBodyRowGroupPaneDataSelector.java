package com.winterframework.firefrog.game.web.chart.selector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.winterframework.firefrog.game.web.chart.IChartBodyRowGroupPaneDataAssembler;
import com.winterframework.firefrog.game.web.chart.IChartDataSelector;
import com.winterframework.firefrog.game.web.chart.data.ChartBodyRowGroupData;
import com.winterframework.firefrog.game.web.chart.data.ChartBodyRowGroupPaneData;

/**
 * @Title 走势图数据区行内分组内格子数据筛选器
 * 
 * 处理来自分组数据，并拆分为每格数据；根据分组数据类型，使用不同的算法格式化格子数据，并设置不同的显示样式
 * 
 * @author bob
 *
 */
public class ChartBodyRowGroupPaneDataSelector implements IChartDataSelector {
	
	private Map<String, String> styles;
	
	private Map<String, IChartBodyRowGroupPaneDataAssembler> assemblers;

	@Override
	public Object select(Object data) {
		
		ChartBodyRowGroupData bodyRowGroupData = (ChartBodyRowGroupData) data;
		
		List<ChartBodyRowGroupPaneData> panes = new ArrayList<ChartBodyRowGroupPaneData>();
		
		List<String> ballList = bodyRowGroupData.getBalls();
		if (ballList != null && ballList.size() > 0) {
			for (int i = 0; i < ballList.size(); i++) {
				String ballStr = ballList.get(i);
				String[] balls = StringUtils.split(ballStr, ",");
				for (String ball : balls) {
					
					ChartBodyRowGroupPaneData bodyItemPane = new ChartBodyRowGroupPaneData();
					// 第一个，或者最后一个
					bodyItemPane.setFirst(i == 0);
					bodyItemPane.setLast(i == ballList.size() - 1);
					
					assemblers.get(bodyRowGroupData.getStyleType()).assemble(bodyItemPane, ball, styles, bodyRowGroupData.getNumberRecordFormat());
										
					panes.add(bodyItemPane);
				}
			}
		}
		
		return panes;
		
	}

	public Map<String, String> getStyles() {
		return styles;
	}

	public void setStyles(Map<String, String> styles) {
		this.styles = styles;
	}
	
	public String getStyle(String styleName) {
		return styles.get(styleName);
	}

	public Map<String, IChartBodyRowGroupPaneDataAssembler> getAssemblers() {
		return assemblers;
	}

	public void setAssemblers(Map<String, IChartBodyRowGroupPaneDataAssembler> assemblers) {
		this.assemblers = assemblers;
	}

}
