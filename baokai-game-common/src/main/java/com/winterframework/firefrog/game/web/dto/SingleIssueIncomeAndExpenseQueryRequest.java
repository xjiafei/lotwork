package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: SingleIssueIncomeAndExpenseQueryRequest 
* @Description: 单期收支报表查询请求参数DTO 
* @author Denny 
* @date 2014-2-21 下午3:02:10 
*  
*/
public class SingleIssueIncomeAndExpenseQueryRequest implements Serializable {

	private static final long serialVersionUID = -1922594001953513587L;

	private Long lotteryid;
	private String webIssueCode;
	private Long selectTimeMode;
	private Long startCreateTime;
	private Long endCreateTime;

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}

	public String getWebIssueCode() {
		return webIssueCode;
	}

	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}

	public Long getSelectTimeMode() {
		return selectTimeMode;
	}

	public void setSelectTimeMode(Long selectTimeMode) {
		this.selectTimeMode = selectTimeMode;
	}

	public Long getStartCreateTime() {
		return startCreateTime;
	}

	public void setStartCreateTime(Long startCreateTime) {
		this.startCreateTime = startCreateTime;
	}

	public Long getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Long endCreateTime) {
		this.endCreateTime = endCreateTime;
	}
}
