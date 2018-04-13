package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

public class GameIssueQueryRequest implements Serializable {

	private static final long serialVersionUID = 1429388665080304524L;

	@NotNull
	private Long lotteryId;
	/** 状态 */
	private Long issueCode;
	
	private String webIssueCode;
	
	private Date openAwardTime;
	
	private Long id;
	
	private Long nextId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getOpenAwardTime() {
		return openAwardTime;
	}

	public void setOpenAwardTime(Date openAwardTime) {
		this.openAwardTime = openAwardTime;
	}

	public String getWebIssueCode() {
		return webIssueCode;
	}

	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}

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

	public Long getNextId() {
		return nextId;
	}

	public void setNextId(Long nextId) {
		this.nextId = nextId;
	}

}
