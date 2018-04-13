package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

public class GameOrderResponse implements Serializable {

	private static final long serialVersionUID = -8007252447659167190L;
	private Long orderId;

	private GameOrderResponseDTO gameOrderDTO;
	
	private List<GameOrderResponeOverMutipleDTO> overMutipleDTO;

	public List<GameOrderResponeOverMutipleDTO> getOverMutipleDTO() {
		return overMutipleDTO;
	}

	public void setOverMutipleDTO(List<GameOrderResponeOverMutipleDTO> overMutipleDTO) {
		this.overMutipleDTO = overMutipleDTO;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public GameOrderResponseDTO getGameOrderDTO() {
		return gameOrderDTO;
	}

	public void setGameOrderDTO(GameOrderResponseDTO gameOrderDTO) {
		this.gameOrderDTO = gameOrderDTO;
	}
}
