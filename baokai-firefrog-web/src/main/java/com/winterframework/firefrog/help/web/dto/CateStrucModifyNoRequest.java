package com.winterframework.firefrog.help.web.dto;

import javax.validation.constraints.NotNull;
public class CateStrucModifyNoRequest {
	
	@NotNull
	private Long id;
	
	@NotNull
	private Long number;
	
	@NotNull
	private Long id1;
	
	@NotNull
	private Long number1;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Long getId1() {
		return id1;
	}

	public void setId1(Long id1) {
		this.id1 = id1;
	}

	public Long getNumber1() {
		return number1;
	}

	public void setNumber1(Long number1) {
		this.number1 = number1;
	}
}
