package com.winterframework.firefrog.shortlived.gamemission.param;

public class DiamondCollectTargetParam extends GameMissionParam {

	private static final long serialVersionUID = 1L;

	private Integer diamond;

	private Integer needTime;

	private Double needPay;
	
	private Long collectTimes;

	public Integer getDiamond() {
		return diamond;
	}

	public void setDiamond(Integer diamond) {
		this.diamond = diamond;
	}

	public Integer getNeedTime() {
		return needTime;
	}

	public void setNeedTime(Integer needTime) {
		this.needTime = needTime;
	}

	public Double getNeedPay() {
		return needPay;
	}

	public void setNeedPay(Double needPay) {
		this.needPay = needPay;
	}

	public Long getCollectTimes() {
		return collectTimes;
	}

	public void setCollectTimes(Long collectTimes) {
		this.collectTimes = collectTimes;
	}

}
