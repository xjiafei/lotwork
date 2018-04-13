package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

import com.winterframework.firefrog.game.web.dto.GameOrderResponseDTO;

public class BuyResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2942239093474627093L;

	private Long orderId;

	private GameOrderResponseDTO gameOrderDTO;

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
