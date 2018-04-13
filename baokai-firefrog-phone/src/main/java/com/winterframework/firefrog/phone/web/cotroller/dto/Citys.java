package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class Citys implements Serializable{
	
	private static final long serialVersionUID = -2349618641801934352L;
	private String id;
	private String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
