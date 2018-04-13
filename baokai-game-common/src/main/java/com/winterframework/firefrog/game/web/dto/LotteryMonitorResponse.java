package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
* @ClassName: LotteryMonitorResponse 
* @Description: 5.5.33	彩种奖期监控-风险详情(OMI033) 响应
* @author Richard
* @date 2013-10-12 上午10:41:07 
*
 */
public class LotteryMonitorResponse implements Serializable {

	private static final long serialVersionUID = -9160029543571690580L;

	private List<RiskOrderDetailStruc> list;

	public List<RiskOrderDetailStruc> getList() {
		return list;
	}

	public void setList(List<RiskOrderDetailStruc> list) {
		this.list = list;
	}
	
}
