package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

public class GameOrderDetailQueryResponseDTO implements Serializable {

	private static final long serialVersionUID = -840207392213883508L;

	/** 订单 */
	private OrdersStrucDTO ordersStruc;
	/** 注单列表 */
	private List<SlipsStrucDTO> slipsStruc;
	
	private Long planId;
	
	public OrdersStrucDTO getOrdersStruc() {
		return ordersStruc;
	}
	public void setOrdersStruc(OrdersStrucDTO ordersStruc) {
		this.ordersStruc = ordersStruc;
	}
	public List<SlipsStrucDTO> getSlipsStruc() {
		return slipsStruc;
	}
	public void setSlipsStruc(List<SlipsStrucDTO> slipsStruc) {
		this.slipsStruc = slipsStruc;
	}
	public Long getPlanId() {
		return planId;
	}
	public void setPlanId(Long planId) {
		this.planId = planId;
	}
}
