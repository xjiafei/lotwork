package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class CancelGameRequest  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6981703813267592855L;
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
