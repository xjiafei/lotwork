package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

public class GameOrderDetailQueryResponse implements Serializable {

	private static final long serialVersionUID = -840207392213883508L;

	/** 订单 */
	private OrdersStruc ordersStruc;
	/** 注单列表 */
	private List<SlipsStruc> slipsStruc;
	
	private Long planId;

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

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}
}
