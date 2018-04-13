package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

public class CardListResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8179825553857165209L;
	private List<CardListDto> list;

	public List<CardListDto> getList() {
		return list;
	}

	public void setList(List<CardListDto> list) {
		this.list = list;
	}

}
