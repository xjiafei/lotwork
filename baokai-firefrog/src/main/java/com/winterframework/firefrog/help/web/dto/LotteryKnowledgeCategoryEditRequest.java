package com.winterframework.firefrog.help.web.dto;

import javax.validation.constraints.NotNull;
public class LotteryKnowledgeCategoryEditRequest {

	private String name;
	
	private Long no;
	
	@NotNull
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
