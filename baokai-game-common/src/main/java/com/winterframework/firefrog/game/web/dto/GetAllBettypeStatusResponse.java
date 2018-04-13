package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameBettypeStatus;

public class GetAllBettypeStatusResponse implements Serializable {

	private static final long serialVersionUID = 1843950605614103353L;
	private List<GameBettypeStatus> gameBettypeStatuss;

	public List<GameBettypeStatus> getGameBettypeStatuss() {
		return gameBettypeStatuss;
	}

	public void setGameBettypeStatuss(List<GameBettypeStatus> gameBettypeStatuss) {
		this.gameBettypeStatuss = gameBettypeStatuss;
	}
}
