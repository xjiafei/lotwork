package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameLhcOdds;

public class GameLhcZodiacResponse implements Serializable {

	private static final long serialVersionUID = -7566922148095655398L;
	private List<LhcGameZodiac> gameZodiac;
	public List<LhcGameZodiac> getGameZodiac() {
		return gameZodiac;
	}
	public void setGameZodiac(List<LhcGameZodiac> gameZodiac) {
		this.gameZodiac = gameZodiac;
	}
	
	
	
}
