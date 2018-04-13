package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

public class GameWinListResponse implements Serializable{

	 
	/**
	 * 
	 */
	private static final long serialVersionUID = -5631264701813239902L;
	private List<GameWinDto> list;

	public List<GameWinDto> getList() {
		return list;
	}

	public void setList(List<GameWinDto> list) {
		this.list = list;
	}
	
}
