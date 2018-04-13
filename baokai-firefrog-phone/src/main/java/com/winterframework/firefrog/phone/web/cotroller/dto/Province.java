package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class Province implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4189459188017878371L;
	
	private Integer id;//	省份编号
	private String name	;//省份名
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
