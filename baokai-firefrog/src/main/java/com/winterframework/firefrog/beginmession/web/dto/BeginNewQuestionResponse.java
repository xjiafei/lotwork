package com.winterframework.firefrog.beginmession.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.beginmession.dao.vo.BeginNewQuestion;

public class BeginNewQuestionResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2992830159393835649L;

	private List<BeginNewQuestion> questions;

	public List<BeginNewQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<BeginNewQuestion> questions) {
		this.questions = questions;
	}
	
}
