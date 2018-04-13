package com.winterframework.firefrog.game.dao.vo;

import java.io.Serializable;

/** 
* @ClassName: LotteryIdAndIssueCode 
* @Description: 彩种ID和期号bean
* @author Denny 
* @date 2013-10-25 下午4:18:52 
*  
*/
public class LotteryIdAndIssueCode implements Serializable {

	private static final long serialVersionUID = 7009041011315478638L;

	private Long lotteryid;
	private Long issueCode;

	
	
	
	public LotteryIdAndIssueCode(Long lotteryid, Long issueCode) {
		super();
		this.lotteryid = lotteryid;
		this.issueCode = issueCode;
	}

	public LotteryIdAndIssueCode() {
		super();
		// TODO Auto-generated constructor stub
	}

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

}
