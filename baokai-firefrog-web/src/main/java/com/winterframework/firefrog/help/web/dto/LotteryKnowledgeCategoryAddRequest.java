package com.winterframework.firefrog.help.web.dto;

import javax.validation.constraints.Size;

public class LotteryKnowledgeCategoryAddRequest {

	@Size(min=1,max=6,message="名称长度应为{min}-{max}个字符")
	private String name;
	
	private Long no;

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
	
}
