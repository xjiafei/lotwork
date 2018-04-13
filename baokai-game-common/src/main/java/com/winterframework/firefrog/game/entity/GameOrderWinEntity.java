package com.winterframework.firefrog.game.entity;

import java.util.Date;
/**
 * 
* @ClassName: GameOrderWinEntity 
* @Description:中奖订单实体。
* @author Richard
* @date 2013-11-20 下午6:59:01 
*
 */
public class GameOrderWinEntity {

	//columns START
	private Long orderid;
	private Long userid;
	private Long issueCode;
	private Integer lotteryid;
	private Long countWin;
	private Date orderTime;
	private Date calculateWinTime;
	private Date saleTime;
	private Long status;
	private Long winsRatio;
	private Long maxslipWins;
	private Long slipWinsratio;
	private Long orderWarnType; //1.风险订单，
	
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Long getIssueCode() {
		return issueCode;
	}
	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}
	public Integer getLotteryid() {
		return lotteryid;
	}
	public void setLotteryid(Integer lotteryid) {
		this.lotteryid = lotteryid;
	}
	public Long getCountWin() {
		return countWin;
	}
	public void setCountWin(Long countWin) {
		this.countWin = countWin;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public Date getCalculateWinTime() {
		return calculateWinTime;
	}
	public void setCalculateWinTime(Date calculateWinTime) {
		this.calculateWinTime = calculateWinTime;
	}
	public Date getSaleTime() {
		return saleTime;
	}
	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getWinsRatio() {
		return winsRatio;
	}
	public void setWinsRatio(Long winsRatio) {
		this.winsRatio = winsRatio;
	}
	public Long getMaxslipWins() {
		return maxslipWins;
	}
	public void setMaxslipWins(Long maxslipWins) {
		this.maxslipWins = maxslipWins;
	}
	public Long getSlipWinsratio() {
		return slipWinsratio;
	}
	public void setSlipWinsratio(Long slipWinsratio) {
		this.slipWinsratio = slipWinsratio;
	}
	public Long getOrderWarnType() {
		return orderWarnType;
	}
	public void setOrderWarnType(Long orderWarnType) {
		this.orderWarnType = orderWarnType;
	}
}
