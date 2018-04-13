package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.Date;


/**
 * 
* @ClassName: GameReportRequest 
* @Description: 游戏报表请求 
* @author Richard
* @date 2014-2-21 上午10:45:22 
*
 */
public class GameReportRequest implements Serializable {

	
	private static final long serialVersionUID = -4067171233306402831L;
	
	private Long userId;
	
	private Date startTime;
	private Date endTime;
	
	private String tid;
	private String userName;
	private String tradeDate;
	private String gameType;
	private String planCode;
	private String orderCode;
	private String reson;
	private Double amonut;
	private String lotteryName;
	private String status;
	private Integer containSub;
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(String tradeDate) {
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

	public Double getAmonut() {
		return amonut;
	}

	public void setAmonut(Double amonut) {
		this.amonut = amonut;
	}

	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getContainSub() {
		return containSub;
	}

	public void setContainSub(Integer containSub) {
		this.containSub = containSub;
	}
}
