package com.winterframework.firefrog.advert.web.dto;

import java.io.Serializable;
import java.util.List;

public class AdNoticeAuditRequest implements Serializable {

	private static final long serialVersionUID = -4210556812663531774L;

	private List<Long> ids;

	private Long status;

	private String memo;
	
	private String approver;

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

}
