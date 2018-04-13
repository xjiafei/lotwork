package com.winterframework.firefrog.help.web.dto;

import java.io.Serializable;
import java.util.List;

public class CateStruc implements Serializable {
	
	private static final long serialVersionUID = -8299466089638656997L;

	private Long id;
	
	private String name;
	
	private Long level;
	
	private Long number;
	
	private List<CateStruc> subCate;

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

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public List<CateStruc> getSubCate() {
		return subCate;
	}

	public void setSubCate(List<CateStruc> subCate) {
		this.subCate = subCate;
	}

}
