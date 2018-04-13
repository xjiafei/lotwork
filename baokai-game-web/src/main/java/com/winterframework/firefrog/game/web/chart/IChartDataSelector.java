package com.winterframework.firefrog.game.web.chart;


/**
 * @Title 数据筛选器接口
 * 
 * @author bob
 *
 */
public interface IChartDataSelector {

	/**
	 * 筛选数据
	 * 
	 * @param data 父级对象传入数据
	 * @return 经过筛选后的数据
	 */
	public Object select(Object data);

}
