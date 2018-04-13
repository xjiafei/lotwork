package com.winterframework.firefrog.fund.web.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.winterframework.modules.web.jsonresult.FirefrogDateDeSerializer;

public class FundReportDeposit extends FundReportDepositRes{
	@NotNull
	private String account;
	@JsonDeserialize(using = FirefrogDateDeSerializer.class)
	@NotNull
	private Date startDate;
	@JsonDeserialize(using = FirefrogDateDeSerializer.class)
	@NotNull
	private Date endDate;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
