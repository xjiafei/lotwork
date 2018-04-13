package com.winterframework.firefrog.advert.web.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AdTopicStruc implements Serializable {
	private static final long serialVersionUID = 5155550440665631698L;

	private Long id;
	private Long cateId;
	private String cateName;
	private String title;
	private List<String> urls;
	private String operator;
	private Date gmtCreated;
	private String gmtCreatedStr;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCateId() {
		return cateId;
	}

	public void setCateId(Long cateId) {
		this.cateId = cateId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public String getGmtCreatedStr() {
		return gmtCreatedStr;
	}

	public void setGmtCreatedStr(String gmtCreatedStr) {
		this.gmtCreatedStr = gmtCreatedStr;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}
}
