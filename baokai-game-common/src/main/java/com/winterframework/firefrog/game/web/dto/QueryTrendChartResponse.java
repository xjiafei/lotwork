package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/**
 * 
* @ClassName: Bets 
* @Description:号码走势图表查询相应
* @author david
* @date 2013-8-23 下午2:35:42 
*
 */
public class QueryTrendChartResponse implements Serializable {

	private static final long serialVersionUID = 6210895184293763906L;

	private BaseTrendChartStruc baseTrendChartStrucs;

	public BaseTrendChartStruc getBaseTrendChartStrucs() {
		return baseTrendChartStrucs;
	}

	public void setBaseTrendChartStrucs(BaseTrendChartStruc baseTrendChartStrucs) {
		this.baseTrendChartStrucs = baseTrendChartStrucs;
	}

	
}
