package com.winterframework.firefrog.global.web.dto;

import java.io.Serializable;

public class GlobalSensitiveWordNameRequest implements Serializable {

	private static final long serialVersionUID = 5690570606822004114L;

	private Long id;

	private String word;

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
