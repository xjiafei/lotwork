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

public class HelpFeedback {

	//columns START
	private Long id;
	private Long uid;
	private Long helpId;
	private Reason reasonId;
	private String reasonContent;
	private Boolean isSolved;

	public enum Reason {
		few, many, complex, other
	}

	//columns END

	public HelpFeedback() {
	}

	public HelpFeedback(Long id) {
		this.id = id;
	}

	public void setUid(Long value) {
		this.uid = value;
	}

	public Long getUid() {
		return this.uid;
	}

	public void setHelpId(Long value) {
		this.helpId = value;
	}

	public Long getHelpId() {
		return this.helpId;
	}

	public void setReasonId(Reason value) {
		this.reasonId = value;
	}

	public Reason getReasonId() {
		return this.reasonId;
	}

	public void setReasonContent(String value) {
		this.reasonContent = value;
	}

	public String getReasonContent() {
		return this.reasonContent;
	}

	public void setIsSolved(Boolean value) {
		this.isSolved = value;
	}

	public Boolean getIsSolved() {
		return this.isSolved;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
