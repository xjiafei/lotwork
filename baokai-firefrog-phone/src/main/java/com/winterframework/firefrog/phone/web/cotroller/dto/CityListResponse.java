package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class CityListResponse implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1717434080270482053L;
	private Integer id;//	城市编号
	private String name	;//城市名称
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
