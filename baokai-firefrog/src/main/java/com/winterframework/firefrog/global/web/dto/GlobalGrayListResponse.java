package com.winterframework.firefrog.global.web.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;



public class GlobalGrayListResponse implements Serializable {

	private static final long serialVersionUID = -6628443060017336569L;
	
	private List<GlobalGrayListStruc> userData;

	public List<GlobalGrayListStruc> getUserData() {
		return userData;
	}

	public void setUserData(List<GlobalGrayListStruc> userData) {
		this.userData = userData;
	}
	
	
	

	
}
