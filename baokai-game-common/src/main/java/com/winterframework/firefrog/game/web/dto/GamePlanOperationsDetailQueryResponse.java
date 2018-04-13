package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/** 
* @ClassName: GamePlanOperationsDetailQueryResponse 
* @Description: 追号后台运营详细记录Response
* @author Alan
* @date 2013-10-14 下午4:09:17 
*  
*/
public class GamePlanOperationsDetailQueryResponse implements Serializable{

	private static final long serialVersionUID = 6603237962115575847L;

	/**追号基本结构*/
	private PlansStruc plansStruc;
	/**注单基本结构*/
	private List<SlipsStruc> slipsStrucs;
	/**订单基本结构*/
	private List<OrdersStruc> ordersStrucs;
	/**预约基本结构*/
	private List<PlansFuturesStruc> plansFuturesStrucs;
	/**资金交易明细*/
	private List<FundTransactionStruc> fundTransactionStrucs;
	
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
	public List<SlipsStruc> getSlipsStrucs() {
		return slipsStrucs;
	}
	public void setSlipsStrucs(List<SlipsStruc> slipsStrucs) {
		this.slipsStrucs = slipsStrucs;
	}
	public List<PlansFuturesStruc> getPlansFuturesStrucs() {
		return plansFuturesStrucs;
	}
	public void setPlansFuturesStrucs(List<PlansFuturesStruc> plansFuturesStrucs) {
		this.plansFuturesStrucs = plansFuturesStrucs;
	}
	public List<FundTransactionStruc> getFundTransactionStrucs() {
		return fundTransactionStrucs;
	}
	public void setFundTransactionStrucs(List<FundTransactionStruc> fundTransactionStrucs) {
		this.fundTransactionStrucs = fundTransactionStrucs;
	}
	
}
