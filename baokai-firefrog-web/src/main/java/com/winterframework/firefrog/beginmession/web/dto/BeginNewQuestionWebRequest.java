package com.winterframework.firefrog.beginmession.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.beginmession.dao.vo.BeginNewQuestion;

/**
 * 
 * @author Ami.Tsai
 *
 */
public class BeginNewQuestionWebRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8287212556919047788L;

	private Long pageNo;
	
	private List<BeginNewQuestion> questions;
	
	private String userName;
	
	private Long deleteId;
	
	public Long getPageNo() {
		return pageNo;
	}

	public void setPageNo(Long pageNo) {
		this.pageNo = pageNo;
	}


	public void setQuestions(List<BeginNewQuestion> questions) {
		this.questions = questions;
	}

	public List<BeginNewQuestion> getQuestions() {
		return questions;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getDeleteId() {
		return deleteId;
	}

	public void setDeleteId(Long deleteId) {
		this.deleteId = deleteId;
	}

}
