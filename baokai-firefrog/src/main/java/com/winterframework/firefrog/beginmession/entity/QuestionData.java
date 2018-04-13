package com.winterframework.firefrog.beginmession.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Ami.Tsai
 *
 */
public class QuestionData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2947392960027118398L;

	private String title;
	
	private List<String> answer;
	
	private int correct;
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCorrect() {
		return correct;
	}

	public void setCorrect(int correct) {
		this.correct = correct;
	}

	public void setAnswer(List<String> answer) {
		this.answer = answer;
	}

	public List<String> getAnswer() {
		return answer;
	}
}