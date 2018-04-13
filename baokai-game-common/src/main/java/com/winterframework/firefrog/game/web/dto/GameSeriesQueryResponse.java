package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

public class GameSeriesQueryResponse implements Serializable {
 

	/**
	 * 
	 */
	private static final long serialVersionUID = -9000899796730021559L;
	private List<GameSeriesListStruc> gameSerieslist;
	
	public GameSeriesQueryResponse(){
		
	}

	public List<GameSeriesListStruc> getGameSerieslist() {
		return gameSerieslist;
	}

	public void setGameSerieslist(List<GameSeriesListStruc> gameSerieslist) {
		this.gameSerieslist = gameSerieslist;
	}
}
