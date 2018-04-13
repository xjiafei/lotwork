package com.winterframework.firefrog.advert.entity;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.acl.entity.AclUser;

public class AdTopic {
	
	private Long id;
	
	private AdTopicCate cate;
	
	private String title;
	
	private List<String> urls;
	
	private AclUser operator;
	
	private Date gmtCreated;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AdTopicCate getCate() {
		return cate;
	}

	public void setCate(AdTopicCate cate) {
		this.cate = cate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	public AclUser getOperator() {
		return operator;
	}

	public void setOperator(AclUser operator) {
		this.operator = operator;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

}
