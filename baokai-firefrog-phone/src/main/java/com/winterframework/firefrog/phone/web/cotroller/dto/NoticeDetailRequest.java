package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class NoticeDetailRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 304944870843644835L;
	private Long chanId;
	private Long nid;
	public Long getChanId() {
		return chanId;
	}
	public void setChanId(Long chanId) {
		this.chanId = chanId;
	}
	public Long getNid() {
		return nid;
	}
	public void setNid(Long nid) {
		this.nid = nid;
	}
	
}
