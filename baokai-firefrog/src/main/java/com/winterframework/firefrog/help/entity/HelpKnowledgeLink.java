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

public class HelpKnowledgeLink  {
	private Long id;
	private Long kid;
	private Long helpId;
	private String content;

	//columns END

	public HelpKnowledgeLink() {
	}

	public HelpKnowledgeLink(Long id) {
		this.id = id;
	}

	public void setKid(Long value) {
		this.kid = value;
	}

	public Long getKid() {
		return this.kid;
	}

	public void setHelpId(Long value) {
		this.helpId = value;
	}

	public Long getHelpId() {
		return this.helpId;
	}

	public void setContent(String value) {
		this.content = value;
	}

	public String getContent() {
		return this.content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
