package com.winterframework.firefrog.user.entity;

public class CreditCardInfo {

	private String name;

	private String number;

	private String copyFrontPath;

	private String copyObliquePath;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCopyFrontPath() {
		return copyFrontPath;
	}

	public void setCopyFrontPath(String copyFrontPath) {
		this.copyFrontPath = copyFrontPath;
	}

	public String getCopyObliquePath() {
		return copyObliquePath;
	}

	public void setCopyObliquePath(String copyObliquePath) {
		this.copyObliquePath = copyObliquePath;
	}

}
