package com.winterframework.firefrog.global.web.dto;

import java.io.Serializable;

public class GlobalSensitiveWordStruc implements Serializable {

	private static final long serialVersionUID = -5961350448836429207L;

	private Long id;
	
	private Long type;
	
	private String word;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
}
