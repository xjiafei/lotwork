package com.winterframework.firefrog.game.web.dto;

/** 
* @ClassName: GameNumbers 
* @Description: 载入期号数
* @author Alan
* @date 2013-10-9 下午2:50:40 
*  
*/
public class GameNumbers {

	//web期号
	private String number;
	//期号
	private Long issueCode;
	//预计开奖时间
	private String time;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Long getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}

}
