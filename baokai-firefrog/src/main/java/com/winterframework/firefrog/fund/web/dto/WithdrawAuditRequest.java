package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class WithdrawAuditRequest implements Serializable {

	private static final long serialVersionUID = -6157882120047124958L;

	@NotNull
	private Long id;
	@NotNull
	private String approveAct;
	@NotNull
	private Long status;
	private String memo;
	private String attach;
	private String isAppeal;
	private String appealTips;
	private String sn;
	

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public WithdrawAuditRequest() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApproveAct() {
		return approveAct;
	}

	public void setApproveAct(String approveAct) {
		this.approveAct = approveAct;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getIsAppeal() {
		return isAppeal;
	}

	public void setIsAppeal(String isAppeal) {
		this.isAppeal = isAppeal;
	}

	public String getAppealTips() {
		return appealTips;
	}

	public void setAppealTips(String appealTips) {
		this.appealTips = appealTips;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}
	
	
}
