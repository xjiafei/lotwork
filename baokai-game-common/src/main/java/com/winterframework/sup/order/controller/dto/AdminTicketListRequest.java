package com.winterframework.sup.order.controller.dto;

import com.winterframework.firefrog.common.base.BaseRequest;

public class AdminTicketListRequest extends BaseRequest{

	private static final long serialVersionUID = 1L;
	
	private String platformCode;
	
	private Long platformId;
	
	private String account;
	
	private String name;
	
	private String acl;
	
	private Long status;
	
	private Integer page = 1;
	
	private Integer pageSize = 10;

//	private AdminTicketListFilter filter;

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAcl() {
		return acl;
	}

	public void setAcl(String acl) {
		this.acl = acl;
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

	public Long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}

//	public AdminTicketListFilter getFilter() {
//		return filter;
//	}

//	public void setFilter(AdminTicketListFilter filter) {
//		this.filter = filter;
//	}
}
