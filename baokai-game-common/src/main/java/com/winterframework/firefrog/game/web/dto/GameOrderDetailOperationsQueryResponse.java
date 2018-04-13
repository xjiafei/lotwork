package com.winterframework.firefrog.game.web.dto;

import java.util.List;

/** 
* @ClassName: GameOrderDetailOperationsQueryResponse 
* @Description: 订单记录运营详细response
* @author Alan
* @date 2013-10-15 上午11:36:06 
*  
*/
public class GameOrderDetailOperationsQueryResponse {

	/** 订单 */
	private OrdersStruc ordersStruc;
	/** 注单列表 */
	private List<SlipsStruc> slipsStruc;
	/** 资金交易明细 */
	private List<FundTransactionStruc> fundTransactionStrucs;

	public OrdersStruc getOrdersStruc() {
		return ordersStruc;
	}

	public void setOrdersStruc(OrdersStruc ordersStruc) {
		this.ordersStruc = ordersStruc;
	}

	public List<SlipsStruc> getSlipsStruc() {
		return slipsStruc;
	}

	public void setSlipsStruc(List<SlipsStruc> slipsStruc) {
		this.slipsStruc = slipsStruc;
	}

	public List<FundTransactionStruc> getFundTransactionStrucs() {
		return fundTransactionStrucs;
	}

	public void setFundTransactionStrucs(List<FundTransactionStruc> fundTransactionStrucs) {
		this.fundTransactionStrucs = fundTransactionStrucs;
	}

}
