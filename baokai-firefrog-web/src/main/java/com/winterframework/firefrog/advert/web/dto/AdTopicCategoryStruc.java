package com.winterframework.firefrog.advert.web.dto;

import java.io.Serializable;

public class AdTopicCategoryStruc implements Serializable {

	private static final long serialVersionUID = -354702096336625366L;

	private Long id;

	private String name;

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

}
