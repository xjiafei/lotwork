package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
* @ClassName: GameReportStruc 
* @Description:游戏报表明细 
* @author Richard
* @date 2014-2-21 上午10:46:10 
*
 */
public class GameReportStruc implements Serializable{

	private static final long serialVersionUID = -9157959990253906492L;
	private Long tid;
	private String userName;
	private Long userId;
	private Date tradeDate;
	private String gameType;
	private String planCode;
	private String orderCode;
	private String reson;
	private Long amonut;
	private String lotteryName;
	private String lotteryId;
	private Integer status;
	
	public GameReportStruc(){
		
	}

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getReson() {
		return reson;
	}

	public void setReson(String reson) {
		this.reson = reson;
	}

	public Long getAmonut() {
		return amonut;
	}

	public void setAmonut(Long amonut) {
		this.amonut = amonut;
	}

	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
