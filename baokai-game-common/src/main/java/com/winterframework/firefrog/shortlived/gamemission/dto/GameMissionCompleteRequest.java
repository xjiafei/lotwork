package com.winterframework.firefrog.shortlived.gamemission.dto;


public class GameMissionCompleteRequest {

	private String missionCode;

	private Long itemSeq;

	private Long userId;

	private Long missionId;

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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getMissionId() {
		return missionId;
	}

	public void setMissionId(Long missionId) {
		this.missionId = missionId;
	}
}
