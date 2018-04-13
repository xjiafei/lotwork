package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class ActivityRequest implements Serializable {

	private static final long serialVersionUID = 5182886917886577900L;

	private Integer system;//平台:1是ios 2是 androi
	
	private String uuid;
	
	private float prize;
	
	private String note;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getSystem() {
		return system;
	}

	public void setSystem(Integer system) {
		this.system = system;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public float getPrize() {
		return prize;
	}

	public void setPrize(float prize) {
		this.prize = prize;
	}

	
	
}
