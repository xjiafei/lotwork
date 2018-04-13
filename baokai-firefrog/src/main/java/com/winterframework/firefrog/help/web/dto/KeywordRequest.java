package com.winterframework.firefrog.help.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
public class KeywordRequest {

	@Size(min=1,max=4,message="关键词应为{min}-{max}个字符")
	private String keyword;
	
	@NotNull
	private Long no;

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
