package com.winterframework.firefrog.game.web.chart.entity;

import java.util.Map;

import com.winterframework.firefrog.game.web.chart.ChartEntitySupport;
import com.winterframework.firefrog.game.web.chart.ChartTemplate;
import com.winterframework.firefrog.game.web.chart.data.ChartBodyRowGroupData;

/**
 * @Title 走势图数据区行内分组内格子
 * 
 * 由于要匹配不同类别的样式，因此定义了多种模板，根据传入的数据类型匹配对应的模板
 * 
 * @author bob
 *
 */
public class ChartBodyRowGroupPane extends ChartEntitySupport {
	
	// 定义的模板
	private Map<String, ChartTemplate> templates;
	
	/**
	 * 根据传入数据中的样式类别，设置不同的模板，并调用父类format方法执行格式化
	 * 
	 * @param type
	 * @return
	 */
	public String format(Object data) {
		
		ChartBodyRowGroupData bodyRowGroupData = (ChartBodyRowGroupData) data;
		
		if (bodyRowGroupData.getStyleType() != null) {
			setTemplate(getTemplate(bodyRowGroupData.getStyleType()));
		}
		
		return super.format(data);
	}

	public Map<String, ChartTemplate> getTemplates() {
		return templates;
	}

	public void setTemplates(Map<String, ChartTemplate> templates) {
		this.templates = templates;
	}
	
	public ChartTemplate getTemplate(String type) {
		return templates.get(type);
	}
	
}
