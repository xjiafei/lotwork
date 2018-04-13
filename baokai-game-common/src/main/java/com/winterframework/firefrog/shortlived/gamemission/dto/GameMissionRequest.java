package com.winterframework.firefrog.shortlived.gamemission.dto;

public class GameMissionRequest {

	private String missionCode;
	
	private Long itemSeq;

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
	
}
