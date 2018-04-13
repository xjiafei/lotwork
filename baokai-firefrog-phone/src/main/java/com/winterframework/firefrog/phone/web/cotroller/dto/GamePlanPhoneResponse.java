package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.util.List;

import com.winterframework.firefrog.game.web.dto.GameOrderResponeOverMutipleDTO;

public class GamePlanPhoneResponse {

	private Long gamePlanId;
	private GameOrderResponseDTO gameOrderResponseDTO;
	private List<GameOrderResponeOverMutipleDTO> overMutipleDTO;
	public Long getGamePlanId() {
		return gamePlanId;
	}

	public void setGamePlanId(Long gamePlanId) {
		this.gamePlanId = gamePlanId;
	}

	public GameOrderResponseDTO getGameOrderResponseDTO() {
		return gameOrderResponseDTO;
	}

	public void setGameOrderResponseDTO(GameOrderResponseDTO gameOrderResponseDTO) {
		this.gameOrderResponseDTO = gameOrderResponseDTO;
	}

	public List<GameOrderResponeOverMutipleDTO> getOverMutipleDTO() {
		return overMutipleDTO;
	}

	public void setOverMutipleDTO(List<GameOrderResponeOverMutipleDTO> overMutipleDTO) {
		this.overMutipleDTO = overMutipleDTO;
	}
}
