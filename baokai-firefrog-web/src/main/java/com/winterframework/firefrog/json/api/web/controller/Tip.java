package com.winterframework.firefrog.json.api.web.controller;

public class Tip {
	public Tip(String tip) {
		this.html = tip;
	}

	private String html;

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

}
