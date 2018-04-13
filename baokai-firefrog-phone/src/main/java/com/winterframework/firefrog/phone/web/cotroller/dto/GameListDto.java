package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class GameListDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4795904134725168305L;
	private Long lotteryid;//	彩种id
	private Double bonus;	//中奖金额
	private String enid;//投注单id
	private Integer ifwin;//	是否中奖
	private Integer iscancel;//	撤单状态
	private String issue;//	奖期
	private String time;//	投注时间
	private String orderCode;//订单编号
	private String numberRecord;//开奖号码
	private Float totalprice;//	投注总金额
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getNumberRecord() {
		return numberRecord;
	}
	public void setNumberRecord(String numberRecord) {
		this.numberRecord = numberRecord;
	}
	public Long getLotteryid() {
		return lotteryid;
	}
	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}
	public Double getBonus() {
		return bonus;
	}
	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}
	public String getEnid() {
		return enid;
	}
	public void setEnid(String enid) {
		this.enid = enid;
	}
	public Integer getIfwin() {
		return ifwin;
	}
	public void setIfwin(Integer ifwin) {
		this.ifwin = ifwin;
	}
	public Integer getIscancel() {
		return iscancel;
	}
	public void setIscancel(Integer iscancel) {
		this.iscancel = iscancel;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Float getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(Float totalprice) {
		this.totalprice = totalprice;
	}
	
}
