package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

public class GameOrderQueryResponse implements Serializable {

	private static final long serialVersionUID = 109920214872655867L;

	private List<OrdersStruc> ordersStruc;

	public List<OrdersStruc> getOrdersStruc() {
		return ordersStruc;
	}

	public void setOrdersStruc(List<OrdersStruc> ordersStruc) {
		this.ordersStruc = ordersStruc;
	}
}
