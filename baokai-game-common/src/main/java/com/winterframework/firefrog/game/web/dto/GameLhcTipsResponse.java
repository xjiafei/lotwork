package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameLhcOdds;

public class GameLhcTipsResponse implements Serializable {

	private static final long serialVersionUID = -7566922148095655398L;
	private List<LhcGameTips> gameTips;
	public List<LhcGameTips> getGameTips() {
		return gameTips;
	}
	public void setGameTips(List<LhcGameTips> gameTips) {
		this.gameTips = gameTips;
	}
	
	
	
	
}
