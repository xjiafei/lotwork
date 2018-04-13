package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

public class GamePlanDetailQueryResponse implements Serializable {

	private static final long serialVersionUID = -1208636374009961531L;

	private PlansStruc plansStruc;
	private List<OrdersStruc> ordersStrucs;
	private List<SlipsStruc> slipsStruc;
	private List<PlansFuturesStruc> plansFuturesStrucs;
	private List<SlipsStruc> planSlipsStrucs;

	public PlansStruc getPlansStruc() {
		return plansStruc;
	}

	public void setPlansStruc(PlansStruc plansStruc) {
		this.plansStruc = plansStruc;
	}

	public List<OrdersStruc> getOrdersStrucs() {
		return ordersStrucs;
	}

	public void setOrdersStrucs(List<OrdersStruc> ordersStrucs) {
		this.ordersStrucs = ordersStrucs;
	}

	public List<SlipsStruc> getSlipsStruc() {
		return slipsStruc;
	}

	public void setSlipsStruc(List<SlipsStruc> slipsStruc) {
		this.slipsStruc = slipsStruc;
	}

	public List<PlansFuturesStruc> getPlansFuturesStrucs() {
		return plansFuturesStrucs;
	}

	public void setPlansFuturesStrucs(List<PlansFuturesStruc> plansFuturesStrucs) {
		this.plansFuturesStrucs = plansFuturesStrucs;
	}
	
	public List<SlipsStruc> getPlanSlipsStrucs() {
		return planSlipsStrucs;
	}
	public void setPlanSlipsStrucs(List<SlipsStruc> planSlipsStrucs) {
		this.planSlipsStrucs = planSlipsStrucs;
	}
}
