package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class MessageMarkAndDeleteRequestDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4357941327485067746L;
	private long id;
	private int flag;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	
}
