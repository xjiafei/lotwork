package com.winterframework.firefrog.fund.web.dto;

import java.util.List;

import com.winterframework.modules.web.jsonresult.Pager;

public class FundChargeAppealResult {

	private Pager pager;

	private Integer totalCount;

	private List<FundChargeAppealResponse> responses;

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer i) {
		this.totalCount = i;
	}

	public List<FundChargeAppealResponse> getResponses() {
		return responses;
	}

	public void setResponses(List<FundChargeAppealResponse> responses) {
		this.responses = responses;
	}

}
