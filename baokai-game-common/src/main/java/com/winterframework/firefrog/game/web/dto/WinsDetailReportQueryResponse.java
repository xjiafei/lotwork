package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

public class WinsDetailReportQueryResponse implements Serializable {

	private static final long serialVersionUID = 9189861421780727599L;

	private List<OperationDetailReportStruc> operationDetailReportStruc;
	private Long lotteryid;
	private String lotteryName;
	private Long issueCode;
	private String webIssueCode;
	private Long reportDate;

	public List<OperationDetailReportStruc> getOperationDetailReportStruc() {
		return operationDetailReportStruc;
	}

	public void setOperationDetailReportStruc(List<OperationDetailReportStruc> operationDetailReportStruc) {
		this.operationDetailReportStruc = operationDetailReportStruc;
	}

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}

	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	public Long getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}

	public String getWebIssueCode() {
		return webIssueCode;
	}

	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}

	public Long getReportDate() {
		return reportDate;
	}

	public void setReportDate(Long reportDate) {
		this.reportDate = reportDate;
	}

}
