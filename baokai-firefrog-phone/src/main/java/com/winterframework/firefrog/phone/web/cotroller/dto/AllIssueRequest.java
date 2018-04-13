package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class AllIssueRequest implements Serializable {
	
	private static final long serialVersionUID = -7460068507204303357L;
	private Long chanId;
	private Long lotteryId;
	private Integer num;
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Long getChanId() {
		return chanId;
	}
	public void setChanId(Long chanId) {
		this.chanId = chanId;
	}
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	
	

}
