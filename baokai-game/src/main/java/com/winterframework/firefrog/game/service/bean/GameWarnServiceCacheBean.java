package com.winterframework.firefrog.game.service.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameOrderWin;
import com.winterframework.firefrog.game.dao.vo.GameWarnOrder;

public class GameWarnServiceCacheBean {
	
	private List<GameWarnOrder> trueGameWarnOrders = new ArrayList<GameWarnOrder>();//缓存一定是风险订单的实体			
	private List<GameWarnOrder> maybeGameWarnOrders = new ArrayList<GameWarnOrder>();//缓存有可能是风险订单的实体
	private List<GameOrderWin> orderWins = new ArrayList<GameOrderWin>();//缓存派奖中奖订单的实体	
	private List<GameOrderWin> freezenOrderWins = new ArrayList<GameOrderWin>();//缓存冻结的中奖订单的实体	
	private Map<Long, GameOrder> orders = new HashMap<Long, GameOrder>();//缓存派奖相关订单的实体	
	private Map<Long, GameOrder> freezenOrders = new HashMap<Long, GameOrder>();//缓存冻结的相关订单的实体	
		
	public List<GameWarnOrder> getTrueGameWarnOrders() {
		return trueGameWarnOrders;
	}
	public void setTrueGameWarnOrders(List<GameWarnOrder> trueGameWarnOrders) {
		this.trueGameWarnOrders = trueGameWarnOrders;
	}
	public void addTrueGameWarnOrder(GameWarnOrder trueGameWarnOrder) {
		this.trueGameWarnOrders.add(trueGameWarnOrder);
	}
	public void clearTrueGameWarnOrders() {
		this.trueGameWarnOrders.clear();
	}
	public List<GameWarnOrder> getMaybeGameWarnOrders() {
		return maybeGameWarnOrders;
	}
	public void setMaybeGameWarnOrders(List<GameWarnOrder> maybeGameWarnOrders) {
		this.maybeGameWarnOrders = maybeGameWarnOrders;
	}
	public void addMaybeGameWarnOrder(GameWarnOrder maybeGameWarnOrder) {
		this.maybeGameWarnOrders.add(maybeGameWarnOrder);
	}
	public void clearMaybeGameWarnOrders() {
		this.maybeGameWarnOrders.clear();
	}
	public List<GameOrderWin> getOrderWins() {
		return orderWins;
	}
	public void setOrderWins(List<GameOrderWin> orderWins) {
		this.orderWins = orderWins;
	}
	public void addOrderWin(GameOrderWin orderWin) {
		this.orderWins.add(orderWin);
	}
	public void clearOrderWins() {
		this.orderWins.clear();
	}
	public List<GameOrderWin> getFreezenOrderWins() {
		return freezenOrderWins;
	}
	public void setFreezenOrderWins(List<GameOrderWin> freezenOrderWins) {
		this.freezenOrderWins = freezenOrderWins;
	}
	public void addFreezenOrderWin(GameOrderWin freezenOrderWin) {
		this.freezenOrderWins.add(freezenOrderWin);
	}
	public void clearFreezenOrderWins() {
		this.freezenOrderWins.clear();
	}
	public Map<Long, GameOrder> getOrders() {
		return orders;
	}
	public void setOrders(Map<Long, GameOrder> orders) {
		this.orders = orders;
	}
	public void addOrder(GameOrder order) {
		this.orders.put(order.getId(), order);
	}
	public void clearOrders() {
		this.orders.clear();
	}
	public Map<Long, GameOrder> getFreezenOrders() {
		return freezenOrders;
	}
	public void setFreezenOrders(Map<Long, GameOrder> freezenOrders) {
		this.freezenOrders = freezenOrders;
	}
	public void addFreezenOrder(GameOrder freezenOrder) {
		this.freezenOrders .put(freezenOrder.getId(), freezenOrder);
	}
	public void clearFreezenOrders() {
		this.freezenOrders.clear();
	}
	public void clear(){
		this.trueGameWarnOrders.clear();		
		this.maybeGameWarnOrders.clear();
		this.orderWins.clear();
		this.freezenOrderWins.clear();
		this.orders.clear();
		this.freezenOrders.clear();
	}
	
	public void setAllWarnOrdersCache(){
		//设置风险订单
		trueGameWarnOrders.addAll(maybeGameWarnOrders);
		maybeGameWarnOrders.clear();
		//设置冻结中奖订单
		freezenOrderWins.addAll(orderWins);
		orderWins.clear();
		//设置冻结订单
		freezenOrders.putAll(orders);
		orders.clear();
	}
	
	public void clearWarnCache(){
		this.trueGameWarnOrders.clear();		
		this.maybeGameWarnOrders.clear();
		this.freezenOrderWins.clear();
		this.freezenOrders.clear();
	}
	
	public void clearNormalCache(){
		this.orderWins.clear();
		this.orders.clear();
	}
	
	public boolean isHaveWarnOrder(){
		return trueGameWarnOrders.size() > 0;
	}
	
	public boolean isHaveNormalOrder(){
		return orders.size() > 0;
	}
}
