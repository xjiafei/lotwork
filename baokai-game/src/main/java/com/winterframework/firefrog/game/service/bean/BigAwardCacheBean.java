package com.winterframework.firefrog.game.service.bean;

import java.util.ArrayList;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameOrderWin;
import com.winterframework.firefrog.game.dao.vo.GameSlip;

/** 
* @ClassName BigAwardCacheBean 
* @Description 开大奖中间类 
* @author  hugh
* @date 2014年7月31日 下午3:10:01 
*  
*/
public class BigAwardCacheBean {
	private List<GameOrder> orders = new ArrayList<GameOrder>();
	private List<GameSlip> slips = new ArrayList<GameSlip>();
	private List<GameOrderWin> orderWins = new ArrayList<GameOrderWin>();

	private int bigOne = 0;
	private int bigTwo = 0;

	public List<GameOrder> getOrders() {
		return orders;
	}

	public List<GameSlip> getSlips() {
		return slips;
	}

	public List<GameOrderWin> getOrderWins() {
		return orderWins;
	}

	public void addOrder(GameOrder order) {
		bigOne += order.getAwardOne();
		bigTwo += order.getAwardTwo();
		this.orders.add(order);
	}

	public void addSlip(GameSlip slip) {
		this.slips.add(slip);
	}

	public void addOrderWin(GameOrderWin orderWin) {
		this.orderWins.add(orderWin);
	}

	public int getBigOne() {
		return bigOne;
	}

	public int getBigTwo() {
		return bigTwo;
	}

}
