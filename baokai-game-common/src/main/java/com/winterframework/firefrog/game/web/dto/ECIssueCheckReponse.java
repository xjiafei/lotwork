package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/** 
* @ClassName: ECIssueCheckReponse 
* @Description: 开奖中心奖期校验返回 
* @author david 
* @date 2014-7-7 下午3:05:52 
*  
*/
public class ECIssueCheckReponse implements Serializable {


	private static final long serialVersionUID = -6573584275609290812L;
	
	private Boolean isSuccess;

	private String message;
	
	private List<ECIssueCheckReponseIssueStruc> issues;

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<ECIssueCheckReponseIssueStruc> getIssues() {
		return issues;
	}

	public void setIssues(List<ECIssueCheckReponseIssueStruc> issues) {
		this.issues = issues;
	}
	

}
