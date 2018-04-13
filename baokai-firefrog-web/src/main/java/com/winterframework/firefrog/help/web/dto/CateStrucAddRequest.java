package com.winterframework.firefrog.help.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
public class CateStrucAddRequest {
	
	@Size(min=1,max=30,message="名称长度应为{min}-{max}个字符")
	private String name;
	
	@NotNull
	private Long level;
	
	private Long no;
	
	private Long parentId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
}
