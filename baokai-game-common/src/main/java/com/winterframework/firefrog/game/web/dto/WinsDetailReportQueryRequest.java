package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

public class WinsDetailReportQueryRequest implements Serializable {

	private static final long serialVersionUID = 2617602792463064006L;

	private Long lotteryid;
	private Long issueCode;
	private Integer sortType;

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}

	public Long getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}

	public Integer getSortType() {
		return sortType;
	}

	public void setSortType(Integer sortType) {
		this.sortType = sortType;
	}

}
