package com.winterframework.firefrog.shortlived.gamemission.param;

import java.util.List;

public class DiamondCollectParam extends GameMissionParam {
	
	private static final long serialVersionUID = 1L;

	private Integer missionTime;

	private Long bonus;

	private List<DiamondCollectTargetParam> diamondTargets;

	public Integer getMissionTime() {
		return missionTime;
	}

	public void setMissionTime(Integer missionTime) {
		this.missionTime = missionTime;
	}

	public Long getBonus() {
		return bonus;
	}

	public void setBonus(Long bonus) {
		this.bonus = bonus;
	}

	public List<DiamondCollectTargetParam> getDiamondTargets() {
		return diamondTargets;
	}

	public void setDiamondTargets(List<DiamondCollectTargetParam> diamondTargets) {
		this.diamondTargets = diamondTargets;
	}

}
