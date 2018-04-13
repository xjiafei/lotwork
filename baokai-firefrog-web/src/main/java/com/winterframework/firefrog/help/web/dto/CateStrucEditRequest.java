package com.winterframework.firefrog.help.web.dto;

import javax.validation.constraints.NotNull;
public class CateStrucEditRequest {

	@NotNull
	private Long id;
	
	private String name;
	
	private Long number;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}
}
