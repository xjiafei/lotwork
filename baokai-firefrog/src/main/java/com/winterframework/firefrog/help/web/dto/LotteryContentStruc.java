package com.winterframework.firefrog.help.web.dto;

import java.io.Serializable;

public class LotteryContentStruc implements Serializable{

	private static final long serialVersionUID = -23232387756563L;

	private String name;

	private String content;

	private Long id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
