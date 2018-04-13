package com.winterframework.firefrog.game.entity;

public class SlipItemAssist extends ItemAssist {

	private Long slipId;
	private static final long serialVersionUID = 2360005377857415852L;

	public SlipItemAssist(ItemAssist itemAssist) {
		this.betTypeCode = itemAssist.getBetTypeCode();
		this.createTime = itemAssist.getCreateTime();
		this.evaluatAward = itemAssist.getEvaluatAward();
		this.evaluatAwardDown = itemAssist.getEvaluatAwardDown();
		this.id = itemAssist.getId();
		this.updateTime = itemAssist.getUpdateTime();
		this.winNumber = itemAssist.getWinNumber();
		this.lhcCode = itemAssist.getLhcCode();
	}

	public SlipItemAssist() {
	}

	public Long getSlipId() {
		return slipId;
	}

	public void setSlipId(Long slipId) {
		this.slipId = slipId;
	}
}
