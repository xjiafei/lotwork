package com.winterframework.firefrog.json.api.web.controller;

import java.text.DateFormat;

public class Note {
	private Long endTime;
	public Note(String text, String url,Long endTime) {
		this.text = text;
		this.url = url;
		this.endTime=endTime;
	}

	public Note(String text) {
		this(text, "#",System.currentTimeMillis());
	}

	private String text;
	private String url;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	
}
