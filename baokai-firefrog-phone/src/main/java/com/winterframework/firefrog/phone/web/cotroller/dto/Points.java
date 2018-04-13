package com.winterframework.firefrog.phone.web.cotroller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Points {

	private String point;
	private Long mult;	
	private Long singleBet;
	@JsonIgnore
	private Long single;
	private Double retValue;
	
	private Integer currPhase;

	public Long getSingle() {
		return single;
	}

	public void setSingle(Long single) {
		this.single = single;
	}

	public Integer getCurrPhase() {
		return currPhase;
	}

	public void setCurrPhase(Integer currPhase) {
		this.currPhase = currPhase;
	}

	public String getPoint() {
		return point;
	}

	public Double getRetValue() {
		return retValue;
	}

	public void setRetValue(Double retValue) {
		this.retValue = retValue;
	}

	public Long getSingleBet() {
		return singleBet;
	}

	public void setSingleBet(Long singleBet) {
		this.singleBet = singleBet;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public Long getMult() {
		return mult;
	}

	public void setMult(Long mult) {
		this.mult = mult;
	}
}
