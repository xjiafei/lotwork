package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

 
/**
 * 开奖结果查询
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月15日
 */
public class GameDrawResultQueryRequest implements Serializable {
  
	private static final long serialVersionUID = -6092244748463872056L;
	/** 彩种 */
	private long lotteryId;
	/** 奖期*/
	private long issueCode;
	/** 最近几期 */
	private int num; 

	public long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public long getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(long issueCode) {
		this.issueCode = issueCode;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
 

}
