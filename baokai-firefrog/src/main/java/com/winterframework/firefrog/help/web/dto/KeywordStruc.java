package com.winterframework.firefrog.help.web.dto;

import java.io.Serializable;

public class KeywordStruc implements Serializable {

	private static final long serialVersionUID = 6974793845071030444L;
	
	private Long id;
	
	private String keyword;
	
	private Long no;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

}
