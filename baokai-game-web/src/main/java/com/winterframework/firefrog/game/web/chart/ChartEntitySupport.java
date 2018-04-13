package com.winterframework.firefrog.game.web.chart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.game.web.chart.formatter.DefaultChartFormatter;


/**
 * @Title 所有Chart对象的支持类
 * 
 * 功能：
 * 1. 格式化当前Chart对象
 * 2. 从上往下层层传递数据
 * 
 * 整个走势图分解为以下几个对象：
 * 1. Chart 走势图
 * 2. ChartTitle 走势图标题栏
 * 3. ChartTitleText 走势图标题栏文本
 * 4. ChartTitleItem 走势图标题栏项目
 * 5. ChartTitleItemPane 走势图标题栏项目内格子
 * 6. ChartBody 走势图数据区
 * 7. ChartBodyRow 走势图数据区行
 * 8. ChartBodyRowGroup 走势图数据区行内分组
 * 9. ChartBodyRowGroupPane 走势图数据区行内分组内格子
 * 
 * @author bob
 *
 */
public abstract class ChartEntitySupport {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	// 默认格式化类
	private static IChartFormatter DEFAULT_CHART_FORMATTER = new DefaultChartFormatter();
	
	// 格式化
	private IChartFormatter formatter;
	
	// 模板
	private ChartTemplate template;
	
	// 数据选择器
	private IChartDataSelector selector;
	
	// 已选择的数据
	private Object selectedData;
	
	/**
	 * 根据数据和模板格式化为显示格式
	 * 
	 * 1. 接收传入数据<code>data</code>
	 * 	1.1 如果当前Chart对象有单独的selector，则使用selector进行数据筛选
	 * 	1.2 如果当前Chart对象没有selector，则把传入数据置为已选择的数据
	 * 2. 如果当前Chart对象有独立formatter，则使用其格式化
	 * 3. 如果当前Chart对象没有独立formatter，则使用默认formatter来格式化
	 * 
	 * @param data 从父级传入的数据
	 * @return
	 */
	public String format(Object data) {
						
		if (selector != null) {
			selectedData = selector.select(data);
		}  else {
			selectedData = data;
		}
		
		try {
			return formatter != null ? formatter.format(this, template) : DEFAULT_CHART_FORMATTER.format(this, template);
		} catch (Exception e) {
			logger.error("Format [" + this + "] with data [" + data + "] and template [" + template + "] failue:", e);
		}
		
		return "";
	}
		
	public IChartFormatter getFormatter() {
		return formatter;
	}

	public void setFormatter(IChartFormatter formatter) {
		this.formatter = formatter;
	}

	public ChartTemplate getTemplate() {
		return template;
	}

	public void setTemplate(ChartTemplate template) {
		this.template = template;
	}

	public IChartDataSelector getSelector() {
		return selector;
	}

	public void setSelector(IChartDataSelector selector) {
		this.selector = selector;
	}
	
	public Object getSelectedData() {
		return selectedData;
	}
	
	public void setSelectedData(Object selectedData) {
		this.selectedData = selectedData;
	}

}
