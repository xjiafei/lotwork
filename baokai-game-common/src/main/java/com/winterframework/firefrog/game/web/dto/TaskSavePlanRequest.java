package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/**
 * 
* @ClassName: AddGameMoneyRequest 
* @Description: 任务新增 调用计划接口
* @author charles
* @date 2013-12-12 下午2:31:00 
*
 */
public class TaskSavePlanRequest implements Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1986214698713853156L;
	
	private Long planid;
	private Long issueCode;


	public Long getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}

	public Long getPlanid() {
		return planid;
	}

	public void setPlanid(Long planid) {
		this.planid = planid;
	}
	
}
