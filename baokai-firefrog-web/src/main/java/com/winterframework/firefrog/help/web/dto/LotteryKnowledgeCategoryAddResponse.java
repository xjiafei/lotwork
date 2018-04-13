package com.winterframework.firefrog.help.web.dto;

import java.io.Serializable;

public class LotteryKnowledgeCategoryAddResponse implements Serializable{

	private static final long serialVersionUID = -37894562461L;

	private String name;
	
	private Long no;
	
	private Long id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
