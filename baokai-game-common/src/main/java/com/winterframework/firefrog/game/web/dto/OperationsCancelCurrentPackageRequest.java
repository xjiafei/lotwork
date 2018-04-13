package com.winterframework.firefrog.game.web.dto;

/** 
* @ClassName: OperationsCancelCurrentPackageRequest 
* @Description: 异常操作项-撤销本期方案request
* @author Alan
* @date 2013-11-18 上午10:36:58 
*  
*/
public class OperationsCancelCurrentPackageRequest {

	//彩种ID
	private Long lotteryid;
	//奖期期号
	private Long issueCode;
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
	public String getDisposeMemo() {
		return disposeMemo;
	}
	public void setDisposeMemo(String disposeMemo) {
		this.disposeMemo = disposeMemo;
	}

}
