package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class OpenLinkDetailRequest implements Serializable {

	
	private static final long serialVersionUID = 5278036821765768755L;
	
	private String id;
	private String data;
	private Long activityType;

	public Long getActivityType() {
		return activityType;
	}

	public void setActivityType(Long activityType) {
		this.activityType = activityType;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
