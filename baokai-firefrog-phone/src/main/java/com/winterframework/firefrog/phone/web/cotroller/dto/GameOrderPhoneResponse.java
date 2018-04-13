package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.game.web.dto.GameOrderResponeOverMutipleDTO;

public class GameOrderPhoneResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8835792366947982715L;
	
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
