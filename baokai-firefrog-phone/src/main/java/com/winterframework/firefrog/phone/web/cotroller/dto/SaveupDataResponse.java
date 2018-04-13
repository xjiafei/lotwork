package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class SaveupDataResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1409579192202585954L;
	private Ownfund ownfund;//	付款人资料
	private Userfund userfund;
	public Ownfund getOwnfund() {
		return ownfund;
	}
	public void setOwnfund(Ownfund ownfund) {
		this.ownfund = ownfund;
	}
	public Userfund getUserfund() {
		return userfund;
	}
	public void setUserfund(Userfund userfund) {
		this.userfund = userfund;
	} 
	
	
}
