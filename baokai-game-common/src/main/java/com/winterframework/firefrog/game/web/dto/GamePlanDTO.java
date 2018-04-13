package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

public class GamePlanDTO implements Serializable {

	private static final long serialVersionUID = -3996709249805557360L;
	private Long gamePlanId;
	
	public GamePlanDTO(){
		
	}

	public Long getGamePlanId() {
		return gamePlanId;
	}



	public void setGamePlanId(Long gamePlanId) {
		this.gamePlanId = gamePlanId;
	}
	
}
