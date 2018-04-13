package com.winterframework.firefrog.fund.web.controller.vo;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.winterframework.firefrog.fund.entity.FundUserRemark;
import com.winterframework.modules.web.jsonresult.FirefrogDateDeSerializer;
import com.winterframework.modules.web.jsonresult.FirefrogDateSerializer;

public class FundUserRemarkQuery  extends FundUserRemark{
	@JsonSerialize(using = FirefrogDateSerializer.class)  
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date startBindDate;
	@JsonSerialize(using = FirefrogDateSerializer.class)  
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date endBindDate;
	private String topAccount;
	private String account;
	
	public Date getStartBindDate() {
		return startBindDate;
	}
	public void setStartBindDate(Date startBindDate) {
		this.startBindDate = startBindDate;
	}
	public Date getEndBindDate() {
		return endBindDate;
	}
	public void setEndBindDate(Date endBindDate) {
		this.endBindDate = endBindDate;
	}
	public String getTopAccount() {
		return topAccount;
	}
	public void setTopAccount(String topAccount) {
		this.topAccount = topAccount;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}

}
