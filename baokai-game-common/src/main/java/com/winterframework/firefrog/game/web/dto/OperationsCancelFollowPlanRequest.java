package com.winterframework.firefrog.game.web.dto;

/** 
* @ClassName: OperationsCancelFollowPlanRequest 
* @Description: 异常操作项-撤销后续追号request
* @author Alan
* @date 2013-11-18 上午10:36:58 
*  
*/
public class OperationsCancelFollowPlanRequest {

	//彩种ID
	private Long lotteryid;
	//奖期期号
	private Long issueCode;
	//起始期号
	private String startIssueCode;
	//结束期号
	private String endIssueCode;
	//备注
	private String disposeMemo;
	
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
	public String getStartIssueCode() {
		return startIssueCode;
	}
	public void setStartIssueCode(String startIssueCode) {
		this.startIssueCode = startIssueCode;
	}
	public String getEndIssueCode() {
		return endIssueCode;
	}
	public void setEndIssueCode(String endIssueCode) {
		this.endIssueCode = endIssueCode;
	}
	public String getDisposeMemo() {
		return disposeMemo;
	}
	public void setDisposeMemo(String disposeMemo) {
		this.disposeMemo = disposeMemo;
	}

}
