package com.winterframework.firefrog.beginmession.web.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * @author Ami.Tsai
 *
 */
public class BeginUpdateStatusRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 716310099951965218L;
	private Map<Long,String> data;
	private String curUser;
	
	public void setData(Map<Long, String> data) {
		this.data = data;
	}
	
	public Object getData() {
		return data;
	}

	public String getCurUser() {
		return curUser;
	}

	public void setCurUser(String curUser) {
		this.curUser = curUser;
	}
	
	
}
