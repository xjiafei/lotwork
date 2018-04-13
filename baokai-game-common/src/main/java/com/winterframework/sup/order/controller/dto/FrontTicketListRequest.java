package com.winterframework.sup.order.controller.dto;

import com.winterframework.firefrog.common.base.BaseRequest;

public class FrontTicketListRequest extends BaseRequest{

	private static final long serialVersionUID = 1L;
	
	private String platformCode;
	
	private String account;
	
	private String lvl;
	
	private Long platformUserId;
	
	private Integer page = 1;
	
	private Integer pageSize = 10;
	
	private String fileError;
	
	public String getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getLvl() {
		return lvl;
	}

	public void setLvl(String lvl) {
		this.lvl = lvl;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getPlatformUserId() {
		return platformUserId;
	}

	public void setPlatformUserId(Long platformUserId) {
		this.platformUserId = platformUserId;
	}

	public String getFileError() {
		return fileError;
	}

	public void setFileError(String fileError) {
		this.fileError = fileError;
	}
	
}
