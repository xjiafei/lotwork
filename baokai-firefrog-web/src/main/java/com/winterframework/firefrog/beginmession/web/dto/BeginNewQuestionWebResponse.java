package com.winterframework.firefrog.beginmession.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.beginmession.dao.vo.BeginNewQuestion;

/**
 * 
 * @author Ami.Tsai
 *
 */
public class BeginNewQuestionWebResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7583954110718305041L;
	
	private List<BeginNewQuestion> questions;

	public List<BeginNewQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<BeginNewQuestion> questions) {
		this.questions = questions;
	}

}
