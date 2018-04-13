package com.winterframework.firefrog.fund.entity;



public interface FundStatus {
	public Long getStatis();
	public <T extends FundStatus> T creatStatus(Long value);
}
