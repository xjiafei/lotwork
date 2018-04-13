package com.winterframework.firefrog.channel.web.dto;

import java.io.Serializable;

public class ChannelSearch implements Serializable{

	private Integer searchType;
	private String 	searchValue;
	
	
	public Integer getSearchType() {
		return searchType;
	}
	public void setSearchType(Integer searchType) {
		this.searchType = searchType;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}	
}
