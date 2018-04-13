package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameLhcOdds;

public class GameLhcOddsResponse implements Serializable {

	private static final long serialVersionUID = -7566922148095655398L;
	private List<GameLhcOdds> gameLhcOdds;
	public List<GameLhcOdds> getGameLhcOdds() {
		return gameLhcOdds;
	}
	public void setGameLhcOdds(List<GameLhcOdds> gameLhcOdds) {
		this.gameLhcOdds = gameLhcOdds;
	}
	
	
}
