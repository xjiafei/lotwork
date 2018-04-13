package com.winterframework.firefrog.game.web.dto;

import java.util.List;

/** 
* @ClassName: GameOrderOperationsQueryResponse 
* @Description: 订单记录运营查询response
* @author Alan
* @date 2013-10-14 下午5:31:36 
*  
*/
public class GameOrderOperationsQueryResponse {

	private List<OrdersStruc> ordersStrucs;

	public List<OrdersStruc> getOrdersStrucs() {
		return ordersStrucs;
	}

	public void setOrdersStrucs(List<OrdersStruc> ordersStrucs) {
		this.ordersStrucs = ordersStrucs;
	}

}
