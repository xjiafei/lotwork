package com.winterframework.firefrog.help.web.dto;

import javax.validation.constraints.NotNull;
public class CateStrucDeleteRequest {
	
	@NotNull
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
