package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class GameDetailRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 789499383653635826L;
	private Long chanId;
	private String id;
	public Long getChanId() {
		return chanId;
	}
	public void setChanId(Long chanId) {
		this.chanId = chanId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
