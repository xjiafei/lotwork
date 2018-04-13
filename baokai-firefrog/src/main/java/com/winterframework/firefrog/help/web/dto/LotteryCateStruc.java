package com.winterframework.firefrog.help.web.dto;

import java.io.Serializable;

public class LotteryCateStruc implements Serializable{

	private static final long serialVersionUID = -1232144567L;

	private Long id;
	
	private Long no;
	
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
