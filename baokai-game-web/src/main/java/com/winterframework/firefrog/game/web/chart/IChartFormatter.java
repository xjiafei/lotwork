package com.winterframework.firefrog.game.web.chart;


/**
 * @Title 格式化类
 * 
 * 目前系统中只有两个格式化类
 * 1. DefaultChartFormatter.java
 * 		解析模板中的关键词，如@{xxx}、@{data:xxx}和&lt;boolean:xxx&gt;&lt;/boolean&gt;，找到对应的Chart对象或已筛选数据中的对应属性，并替换模板中的文键词
 * 2. DefaultChartLoopFormatter.java
 * 		如果Chart对象自身需要循环，如走势图中的每一行数据、或每一行中的每一格，根据筛选的数据进行循环输出
 * 
 * @author bob
 *
 */
public interface IChartFormatter {

	/**
	 * 
	 * 
	 * @param chart
	 * @param template
	 * @return
	 * @throws Exception
	 */
	public String format(ChartEntitySupport chart, ChartTemplate template) throws Exception;

}
