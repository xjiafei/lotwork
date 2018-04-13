package com.winterframework.firefrog.fund.service.impl.mow;

public abstract class MowReq {
	protected Long company_id;
	protected String key;
	protected String company_order_num;
	protected String company_user;
	public Long getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Long company_id) {
		this.company_id = company_id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getCompany_order_num() {
		return company_order_num;
	}
	public void setCompany_order_num(String company_order_num) {
		this.company_order_num = company_order_num;
	}
	public String getCompany_user() {
		return company_user;
	}
	public void setCompany_user(String company_user) {
		this.company_user = company_user;
	}
	public abstract String createParam();
	
}
