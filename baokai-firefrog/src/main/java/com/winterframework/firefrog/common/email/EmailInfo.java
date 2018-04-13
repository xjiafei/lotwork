package com.winterframework.firefrog.common.email;

import java.io.Serializable;

public class EmailInfo implements Serializable {

	private static final long serialVersionUID = 5968641901814291516L;

	private String address;

	private String title;

	private String content;
	
	private String fromAdress;
	
	private String fileNames;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getFromAdress() {
		return fromAdress;
	}

	public void setFromAdress(String fromAdress) {
		this.fromAdress = fromAdress;
	}

	public String getFileNames() {
		return fileNames;
	}

	public void setFileNames(String fileNames) {
		this.fileNames = fileNames;
	}
}
