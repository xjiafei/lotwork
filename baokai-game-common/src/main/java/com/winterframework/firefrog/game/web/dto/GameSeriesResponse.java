package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

public class GameSeriesResponse implements Serializable {

	private static final long serialVersionUID = -2327498530803402128L;

	private List<LotteryListStruc> list;
	
	public GameSeriesResponse(){
		
	}

	public List<LotteryListStruc> getList() {
		return list;
	}

	public void setList(List<LotteryListStruc> list) {
		this.list = list;
	}
}
