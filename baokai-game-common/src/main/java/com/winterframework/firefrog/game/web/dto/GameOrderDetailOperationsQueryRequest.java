package com.winterframework.firefrog.game.web.dto;

/** 
* @ClassName: GameOrderDetailOperationsQueryRequest 
* @Description: 订单记录运营详细request 
* @author Alan
* @date 2013-10-15 上午11:34:21 
*  
*/
public class GameOrderDetailOperationsQueryRequest {

	/**订单ID*/
	private Long orderid;
	/**订单编号*/
	private String orderCode;

	public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

}
