package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

public class GameListResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8979854000363298048L;
	private List<GameListDto> list;

	public List<GameListDto> getList() {
		return list;
	}

	public void setList(List<GameListDto> list) {
		this.list = list;
	}
	
}
