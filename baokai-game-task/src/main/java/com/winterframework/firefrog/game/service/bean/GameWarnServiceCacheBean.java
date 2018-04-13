package com.winterframework.firefrog.game.service.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameOrderWin;
import com.winterframework.firefrog.game.dao.vo.GameWarnOrder;


public class GameWarnServiceCacheBean {
	/**
	 * 缓存一定是风险订单的实体
	 */
	private List<GameWarnOrder> trueGameWarnOrders = new ArrayList<GameWarnOrder>();//缓存一定是风险订单的实体	
	/**
	 * 缓存有可能是风险订单的实体	
	 */
	private List<GameWarnOrder> maybeGameWarnOrders = new ArrayList<GameWarnOrder>();//缓存有可能是风险订单的实体
	/**
	 * 缓存派奖中奖订单的实体	
	 */
	private List<GameOrderWin> orderWins = new ArrayList<GameOrderWin>();//缓存派奖中奖订单的实体	
	/**
	 * 缓存冻结的中奖订单的实体	
	 */
	private List<GameOrderWin> freezenOrderWins = new ArrayList<GameOrderWin>();//缓存冻结的中奖订单的实体	
	/**
	 * 缓存派奖相关订单的实体	
	 */
	private Map<Long, GameOrder> orders = new HashMap<Long, GameOrder>();//缓存派奖相关订单的实体
	/**
	 * 缓存冻结的相关订单的实体	
	 */
	private Map<Long, GameOrder> freezenOrders = new HashMap<Long, GameOrder>();//缓存冻结的相关订单的实体	
	/**
	 * 取得缓存一定是风险订单的实体
	 */	
	public List<GameWarnOrder> getTrueGameWarnOrders() {
		return trueGameWarnOrders;
	}
	/**
	 * 设定缓存一定是风险订单的实体
	 */
	public void setTrueGameWarnOrders(List<GameWarnOrder> trueGameWarnOrders) {
		this.trueGameWarnOrders = trueGameWarnOrders;
	}
	/**
	 * 增加缓存一定是风险订单的实体
	 */
	public void addTrueGameWarnOrder(GameWarnOrder trueGameWarnOrder) {
		this.trueGameWarnOrders.add(trueGameWarnOrder);
	}
	/**
	 * 清除缓存一定是风险订单的实体
	 */
	public void clearTrueGameWarnOrders() {
		this.trueGameWarnOrders.clear();
	}
	/**
	 * 取得缓存有可能是风险订单的实体	
	 */
	public List<GameWarnOrder> getMaybeGameWarnOrders() {
		return maybeGameWarnOrders;
	}
	/**
	 * 设定缓存有可能是风险订单的实体	
	 */
	public void setMaybeGameWarnOrders(List<GameWarnOrder> maybeGameWarnOrders) {
		this.maybeGameWarnOrders = maybeGameWarnOrders;
	}
	/**
	 * 增加缓存有可能是风险订单的实体	
	 */
	public void addMaybeGameWarnOrder(GameWarnOrder maybeGameWarnOrder) {
		this.maybeGameWarnOrders.add(maybeGameWarnOrder);
	}
	/**
	 * 清除缓存有可能是风险订单的实体	
	 */
	public void clearMaybeGameWarnOrders() {
		this.maybeGameWarnOrders.clear();
	}
	/**
	 * 取得缓存派奖中奖订单的实体	
	 */
	public List<GameOrderWin> getOrderWins() {
		return orderWins;
	}
	/**
	 * 设定缓存派奖中奖订单的实体	
	 */
	public void setOrderWins(List<GameOrderWin> orderWins) {
		this.orderWins = orderWins;
	}
	/**
	 * 增加缓存派奖中奖订单的实体	
	 */
	public void addOrderWin(GameOrderWin orderWin) {
		this.orderWins.add(orderWin);
	}
	/**
	 * 清除缓存派奖中奖订单的实体	
	 */
	public void clearOrderWins() {
		this.orderWins.clear();
	}
	/**
	 * 取得缓存冻结的中奖订单的实体	
	 */
	public List<GameOrderWin> getFreezenOrderWins() {
		return freezenOrderWins;
	}
	/**
	 * 设定缓存冻结的中奖订单的实体	
	 */
	public void setFreezenOrderWins(List<GameOrderWin> freezenOrderWins) {
		this.freezenOrderWins = freezenOrderWins;
	}
	/**
	 * 增加缓存冻结的中奖订单的实体	
	 */
	public void addFreezenOrderWin(GameOrderWin freezenOrderWin) {
		this.freezenOrderWins.add(freezenOrderWin);
	}
	/**
	 * 清除缓存冻结的中奖订单的实体	
	 */
	public void clearFreezenOrderWins() {
		this.freezenOrderWins.clear();
	}
	/**
	 * 取得缓存派奖相关订单的实体	
	 */
	public Map<Long, GameOrder> getOrders() {
		return orders;
	}
	/**
	 * 设定缓存派奖相关订单的实体	
	 */
	public void setOrders(Map<Long, GameOrder> orders) {
		this.orders = orders;
	}
	/**
	 * 增加缓存派奖相关订单的实体	
	 */
	public void addOrder(GameOrder order) {
		this.orders.put(order.getId(), order);
	}
	/**
	 * 清除缓存派奖相关订单的实体	
	 */
	public void clearOrders() {
		this.orders.clear();
	}
	/**
	 * 取得缓存冻结的相关订单的实体	
	 */
	public Map<Long, GameOrder> getFreezenOrders() {
		return freezenOrders;
	}
	/**
	 * 设定缓存冻结的相关订单的实体	
	 */
	public void setFreezenOrders(Map<Long, GameOrder> freezenOrders) {
		this.freezenOrders = freezenOrders;
	}
	/**
	 * 增加缓存冻结的相关订单的实体	
	 */
	public void addFreezenOrder(GameOrder freezenOrder) {
		this.freezenOrders .put(freezenOrder.getId(), freezenOrder);
	}
	/**
	 * 设定缓存冻结的相关订单的实体	
	 */
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
