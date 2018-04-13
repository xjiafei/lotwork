package com.winterframework.firefrog.game.dao.vo;

import java.io.Serializable;
import java.util.Date;

public class GameExceptionAuditOrder implements Serializable {

	private static final long serialVersionUID = -1385002756465990008L;

	private Long lotteryId;
	private Long issueCode;
	private String webIssueCode;
	private Date saleTime;
	private Date openDrawTime;//理论开奖时间
	private Date factionDrawTime;//实际开奖时间
	private Integer status;

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
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

	public Date getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
	}

	public Date getOpenDrawTime() {
		return openDrawTime;
	}

	public void setOpenDrawTime(Date openDrawTime) {
		this.openDrawTime = openDrawTime;
	}

	public Date getFactionDrawTime() {
		return factionDrawTime;
	}

	public void setFactionDrawTime(Date factionDrawTime) {
		this.factionDrawTime = factionDrawTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
