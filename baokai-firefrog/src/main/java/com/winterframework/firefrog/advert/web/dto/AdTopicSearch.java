package com.winterframework.firefrog.advert.web.dto;

import java.io.Serializable;

public class AdTopicSearch implements Serializable {

	private static final long serialVersionUID = -3686642194829984596L;

	private String title;

	private Long cateId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getCateId() {
		return cateId;
	}

	public void setCateId(Long cateId) {
		this.cateId = cateId;
	}

}
