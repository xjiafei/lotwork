package com.winterframework.firefrog.game.web.dto;

import com.winterframework.firefrog.game.entity.Points;

public class PointsRequestDTO {
	//影响号码
	protected String point;
	protected Long mult;
	protected Long retValue;
	protected Integer currentPhase;
	protected Long singleBet;

	public Integer getCurrentPhase() {
		return currentPhase;
	}
	public void setCurrentPhase(Integer currentPhase) {
		this.currentPhase = currentPhase;
	}
	public PointsRequestDTO() {
	}
	public PointsRequestDTO(Points tempPoints) {
		this.mult=tempPoints.getMult();
		this.point=tempPoints.getPoint();
		this.retValue=tempPoints.getRetValue();
		this.currentPhase = tempPoints.getCurrPhase();
		this.singleBet=tempPoints.getSingleBet();
	}

	public Long getSingleBet() {
		return singleBet;
	}
	public void setSingleBet(Long singleBet) {
		this.singleBet = singleBet;
	}
	public String getPoint() {
		return point;
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

	public Long getRetValue() {
		return retValue;
	}

	public void setRetValue(Long retValue) {
		this.retValue = retValue;
	}

}
