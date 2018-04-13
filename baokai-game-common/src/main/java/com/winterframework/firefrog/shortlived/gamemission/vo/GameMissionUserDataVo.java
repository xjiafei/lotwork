package com.winterframework.firefrog.shortlived.gamemission.vo;


public class GameMissionUserDataVo {

	private String missionCode;

	private Long itemSeq;
	
	private Integer completeTimes;

	private String targets;
	
	public String getMissionCode() {
		return missionCode;
	}

	public void setMissionCode(String missionCode) {
		this.missionCode = missionCode;
	}

	public Long getItemSeq() {
		return itemSeq;
	}

	public void setItemSeq(Long itemSeq) {
		this.itemSeq = itemSeq;
	}

	public String getTargets() {
		return targets;
	}

	public void setTargets(String targets) {
		this.targets = targets;
	}

	public Integer getCompleteTimes() {
		return completeTimes;
	}

	public void setCompleteTimes(Integer completeTimes) {
		this.completeTimes = completeTimes;
	}
}
