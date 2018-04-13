package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/** 
* @ClassName: GameRetPoint 
* @Description: 返点记录表 
* @author david 
* @date 2013-11-11 下午5:42:01 
*  
*/
public class GameRetPoint extends BaseEntity {

	private static final long serialVersionUID = 628653886678731866L;
	//alias
	public static final String TABLE_ALIAS = "游戏返点信息表";
	public static final String ALIAS_GAME_SLIP_ID = "注单信息";
	public static final String ALIAS_CREATE_TIME = "记录时间";
	public static final String ALIAS_STATUS = "状态：1  待处理  2  已派发 3 已冻结  4 已撤销  5审核中";
	public static final Integer STATUS_FOR_DEAL = 1;
	public static final Integer STATUS_SENDED = 2;
	public static final Integer STATUS_FREEZE = 3;
	public static final Integer STATUS_UNDO = 4;
	public static final Long STATUS_WARN = 5L;
	//date formats

	//columns START
	private Long gameOrderId;
	private Date createTime;
	private String retPointChain;
	private Integer status;
	private Long issueCode;
	private String retUserChain;

	//columns END

	//扩展属性，不在数据库中存在，在查询时使用
	private Long lotteryId;
	private String orderCode; 
	
	public enum Status { 
		//状态： 1 已冻结 2 已派发 3 已撤销
		FREEZE(1), DISTRIBUTE(2), CANCEL(3);
		private int _value = 1; 
		Status(int value) {
			this._value = value;
		} 
		public int getValue() {
			return _value;
		}
	}
	public GameRetPoint() {
	}

	public GameRetPoint(Long id) {
		this.id = id;
	}

	public Long getGameOrderId() {
		return gameOrderId;
	}

	public void setGameOrderId(Long gameOrderId) {
		this.gameOrderId = gameOrderId;
	}

	public Long getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRetPointChain() {
		return retPointChain;
	}

	public void setRetPointChain(String retPointChain) {
		this.retPointChain = retPointChain;
	}

	public String getRetUserChain() {
		return retUserChain;
	}

	public void setRetUserChain(String retUserChain) {
		this.retUserChain = retUserChain;
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
}
