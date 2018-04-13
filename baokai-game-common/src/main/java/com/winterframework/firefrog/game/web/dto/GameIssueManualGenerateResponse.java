/**   
* @Title: GameIssueManualGenerateResponse.java 
* @Package com.winterframework.firefrog.game.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-8-25 下午3:11:38 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: GameIssueManualGenerateResponse 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-8-25 下午3:11:38 
*  
*/
public class GameIssueManualGenerateResponse implements Serializable {
	private static final long serialVersionUID = 437894321L;
	private String startDate;
	private String endDate;
	private int generateDays;
	private Long generateIssues;
	private String message;
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getGenerateDays() {
		return generateDays;
	}

	public void setGenerateDays(int generateDays) {
		this.generateDays = generateDays;
	}

	public Long getGenerateIssues() {
		return generateIssues;
	}

	public void setGenerateIssues(Long generateIssues) {
		this.generateIssues = generateIssues;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
