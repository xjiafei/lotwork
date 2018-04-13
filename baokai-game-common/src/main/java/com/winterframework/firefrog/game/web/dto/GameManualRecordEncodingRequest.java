package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.Date;


/** 
* @ClassName GameManualRecordRequest 
* @Description 手工录号 
* @author  hugh
* @date 2014年9月15日 下午12:00:06 
*  
*/
public class GameManualRecordEncodingRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long lotteryId;
	private Long issueCode;
	private Long userId;
	private String result;
	private Date encodingTime;
	
	public Long getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Date getEncodingTime() {
		return encodingTime;
	}

	public void setEncodingTime(Date encodingTime) {
		this.encodingTime = encodingTime;
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}


}
