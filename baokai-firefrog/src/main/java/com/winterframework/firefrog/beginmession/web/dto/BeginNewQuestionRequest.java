package com.winterframework.firefrog.beginmession.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.beginmession.dao.vo.BeginNewQuestion;

public class BeginNewQuestionRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1046995041431519469L;
	
	private List<BeginNewQuestion> questions;

	private String userName;
	
	private Integer pageNo;
	
	private Long deleteId;
	
	public List<BeginNewQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<BeginNewQuestion> questions) {
		this.questions = questions;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Long getDeleteId() {
		return deleteId;
	}

	public void setDeleteId(Long deleteId) {
		this.deleteId = deleteId;
	}


}
