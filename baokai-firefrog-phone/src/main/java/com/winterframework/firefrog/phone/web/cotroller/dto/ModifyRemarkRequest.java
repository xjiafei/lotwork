package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class ModifyRemarkRequest implements Serializable {

	
	private static final long serialVersionUID = 5242036821765768755L;
	
	private String id;
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
