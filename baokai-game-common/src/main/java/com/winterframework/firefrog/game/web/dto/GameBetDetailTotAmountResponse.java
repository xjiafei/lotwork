package com.winterframework.firefrog.game.web.dto;

import java.util.List;

import com.winterframework.firefrog.game.entity.GameBetDetailTotAmountEntity;

public class GameBetDetailTotAmountResponse {

	/** 單一玩法所有投注金額*/	
	private List<GameBetDetailTotAmountEntity> gameBetDetailTotAmountStruc ;
	
	public List<GameBetDetailTotAmountEntity> getGameBetDetailTotAmountStruc() {
		return gameBetDetailTotAmountStruc;
	}
	public void setGameBetDetailTotAmountStruc(
			List<GameBetDetailTotAmountEntity> gameBetDetailTotAmountStruc) {
		this.gameBetDetailTotAmountStruc = gameBetDetailTotAmountStruc;
	}
}
