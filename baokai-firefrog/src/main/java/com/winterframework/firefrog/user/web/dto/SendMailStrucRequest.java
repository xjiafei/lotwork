package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

public class SendMailStrucRequest implements Serializable {

	private static final long serialVersionUID = 532091041981282390L;

	private String email;
	private String title;
	private String content;
	private Long id;

	public SendMailStrucRequest() {

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
