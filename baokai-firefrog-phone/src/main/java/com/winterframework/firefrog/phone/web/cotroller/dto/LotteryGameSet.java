package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"code"})
public class LotteryGameSet {
	
	private Integer code;
	
	private String title;
	
	private String name;
	
	private String parent;
	
	private List<LotteryBetMethod> childs;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public List<LotteryBetMethod> getChilds() {
		return childs;
	}

	public void setChilds(List<LotteryBetMethod> childs) {
		this.childs = childs;
	}

}
