package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;


/** 
* @ClassName: ECIssueCheckRequest 
* @Description: 开奖中心奖期校验请求 
* @author david 
* @date 2014-7-7 下午3:05:08 
*  
*/
public class ECIssueCheckRequest implements Serializable {

	private static final long serialVersionUID = -6573584275609290812L;

	private String customer;
	
	private String lottery;
	
    private String issueStartTime;
    
    private String issueEndTime;
    
	private String safestr;

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getLottery() {
		return lottery;
	}

	public void setLottery(String lottery) {
		this.lottery = lottery;
	}

	public String getIssueStartTime() {
		return issueStartTime;
	}

	public void setIssueStartTime(String issueStartTime) {
		this.issueStartTime = issueStartTime;
	}

	public String getIssueEndTime() {
		return issueEndTime;
	}

	public void setIssueEndTime(String issueEndTime) {
		this.issueEndTime = issueEndTime;
	}

	public String getSafestr() {
		return safestr;
	}

	public void setSafestr(String safestr) {
		this.safestr = safestr;
	}

}
