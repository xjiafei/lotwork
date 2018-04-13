/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.help.entity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class Help {

	//columns START
	private Long id;
	private String title;
	private Boolean isRecommend;
	private String preface;
	private Long no;
	private Long solvednum;
	private Long unsolvednum;
	private Long browsenum;
	private Long cateId;
	private Long cateId2;
	private Long recNo;

	//columns END
	public Help() {
	}

	public Help(Long id) {
		this.id = id;
	}

	public void setTitle(String value) {
		this.title = value;
	}

	public String getTitle() {
		return this.title;
	}

	public void setPreface(String value) {
		this.preface = value;
	}

	public String getPreface() {
		return this.preface;
	}

	public void setNo(Long value) {
		this.no = value;
	}

	public Long getNo() {
		return this.no;
	}

	public void setSolvednum(Long value) {
		this.solvednum = value;
	}

	public Long getSolvednum() {
		return this.solvednum;
	}

	public void setUnsolvednum(Long value) {
		this.unsolvednum = value;
	}

	public Long getUnsolvednum() {
		return this.unsolvednum;
	}

	public void setBrowsenum(Long value) {
		this.browsenum = value;
	}

	public Long getBrowsenum() {
		return this.browsenum;
	}

	public void setCateId(Long value) {
		this.cateId = value;
	}

	public Long getCateId() {
		return this.cateId;
	}

	public void setCateId2(Long value) {
		this.cateId2 = value;
	}

	public Long getCateId2() {
		return this.cateId2;
	}

	public void setRecNo(Long value) {
		this.recNo = value;
	}

	public Long getRecNo() {
		return this.recNo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Boolean isRecommend) {
		this.isRecommend = isRecommend;
	}
}
