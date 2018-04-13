package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class UserPointResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5457264521649648922L;
	private Double point;//	总反点
	private Double amount;//	昨日投注金额
	private Double win;//	昨日中奖金额
	
	public Double getPoint() {
		return point;
	}
	public void setPoint(Double point) {
		this.point = point;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getWin() {
		return win;
	}
	public void setWin(Double win) {
		this.win = win;
	}

}
