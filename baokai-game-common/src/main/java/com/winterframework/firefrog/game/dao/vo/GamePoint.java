package com.winterframework.firefrog.game.dao.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.firefrog.game.entity.Points;
import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class GamePoint extends BaseEntity {

	private static final long serialVersionUID = 8800467176327804859L;
	private Long slipId;
	/** 
	* 影响的号码
	*/
	private String point;
	/** 
	* 投注倍数
	*/
	private Long mult;
	/** 
	* 单注中奖金额
	*/
	private Long retValue;

	private Long planDetailId;
	private Long pkgDetailId;
	private Long singleBet;
	
	private int isSignRed;
	
	private String pointView;
	
	public GamePoint() {
	}

	public Long getPkgDetailId() {
		return pkgDetailId;
	}

	public void setPkgDetailId(Long pkgDetailId) {
		this.pkgDetailId = pkgDetailId;
	}

	public GamePoint(Points points, Long slipId) {
		this.slipId = slipId;
		this.point = points.getPoint();
		this.mult = points.getMult();
		this.retValue = points.getRetValue();
	}

	public GamePoint(Points points, Long slipId, Long planDetailId,Long pkgDetailId) {
		this.slipId = slipId;
		this.pkgDetailId=pkgDetailId;
		this.point = points.getPoint();
		this.mult = points.getMult();
		this.retValue = points.getRetValue();
		this.planDetailId = planDetailId;
		this.singleBet = points.getSingleBet();
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

	public Long getSlipId() {
		return slipId;
	}

	public void setSlipId(Long slipId) {
		this.slipId = slipId;
	}


	public Long getPlanDetailId() {
		return planDetailId;
	}

	public void setPlanDetailId(Long planDetailId) {
		this.planDetailId = planDetailId;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}

	public Long getSingleBet() {
		if(singleBet == null){
			singleBet = 1L;
		}
		return singleBet;
	}

	public void setSingleBet(Long singleBet) {
		this.singleBet = singleBet;
	}

	public int getIsSignRed() {
		return isSignRed;
	}

	public void setIsSignRed(int isSignRed) {
		this.isSignRed = isSignRed;
	}

	public String getPointView() {
		return pointView;
	}

	public void setPointView(String pointView) {
		this.pointView = pointView;
	}
}
