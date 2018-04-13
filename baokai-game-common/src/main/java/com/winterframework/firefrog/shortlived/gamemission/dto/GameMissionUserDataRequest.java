package com.winterframework.firefrog.shortlived.gamemission.dto;

public class GameMissionUserDataRequest {

	private Long userId;

	private String missionCode;

	private Long itemSeq;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

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
