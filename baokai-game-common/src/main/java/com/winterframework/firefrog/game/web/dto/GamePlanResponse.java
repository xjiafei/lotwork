package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

public class GamePlanResponse implements Serializable {

	private static final long serialVersionUID = -8007252447659167190L;
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
