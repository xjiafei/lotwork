package com.winterframework.firefrog.shortlived.gamemission.enums;

public enum GameMissionCompleteStatus {
	
	ERROR(0L), SUCCESS(1L), ERROR_REQUEST_AGAIN(2L), ERROR_NOT_COMPLETE(3L);

	private Long value;

	GameMissionCompleteStatus(Long value) {
		this.value = value;
	}

	public Long value() {
		return this.value;
	}

}
