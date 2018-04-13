package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;


/** 
* @ClassName: IssueStruc 
* @Description: 预览期号列表基本结构
* @author david 
* @date 2013-8-21 下午5:42:54 
*  
*/
public class PreviewIssueStruc implements Serializable {

	private static final long serialVersionUID = -8848174089315020130L;

	private Long issueCode;
	private String webIssueCode;
	private Long saleStartTime;
	private Long saleEndTime;
	private Long openAwardTime;
	private Long id;
	private Integer periodStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWebIssueCode() {
		return webIssueCode;
	}

	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}

	public Long getSaleStartTime() {
		return saleStartTime;
	}

	public void setSaleStartTime(Long saleStartTime) {
		this.saleStartTime = saleStartTime;
	}

	public Long getSaleEndTime() {
		return saleEndTime;
	}

	public void setSaleEndTime(Long saleEndTime) {
		this.saleEndTime = saleEndTime;
	}

	public Long getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}

	public Long getOpenAwardTime() {
		return openAwardTime;
	}

	public void setOpenAwardTime(Long openAwardTime) {
		this.openAwardTime = openAwardTime;
	}

	public Integer getPeriodStatus() {
		return periodStatus;
	}

	public void setPeriodStatus(Integer periodStatus) {
		this.periodStatus = periodStatus;
	}

}
