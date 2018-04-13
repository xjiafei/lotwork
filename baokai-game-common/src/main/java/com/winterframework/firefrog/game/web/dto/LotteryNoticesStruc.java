package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
/**
 * 彩种奖期通知基本结构
* @ClassName: LotteryNoticesStruc 
* @Description: 彩种奖期通知基本结构
* @author Richard
* @date 2013-10-12 上午9:28:43 
*
 */
public class LotteryNoticesStruc implements Serializable {

	private static final long serialVersionUID = 960839520323647685L;

	private Integer	lotteryid;
	private String	lotteryName;
	private Long	issueCode;
	private String	webIssueCode;
	private Integer	status;
	
	public LotteryNoticesStruc(){
		
	}

	public Integer getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Integer lotteryid) {
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
