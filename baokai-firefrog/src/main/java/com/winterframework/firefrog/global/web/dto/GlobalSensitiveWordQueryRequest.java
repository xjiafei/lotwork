package com.winterframework.firefrog.global.web.dto;

import java.io.Serializable;

public class GlobalSensitiveWordQueryRequest implements Serializable {

	private static final long serialVersionUID = -246087713462832470L;

	private String word;

	private Long type;
	
	private Long pageNo;

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getPageNo() {
		return pageNo;
	}

	public void setPageNo(Long pageNo) {
		this.pageNo = pageNo;
	}

}
