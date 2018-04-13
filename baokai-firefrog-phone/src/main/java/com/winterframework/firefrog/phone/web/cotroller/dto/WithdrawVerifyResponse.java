package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

public class WithdrawVerifyResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3674655526461717159L;
	
	private Datas datas;
	private UserDto user;
	private List<Questions> questions;
	
	public Datas getDatas() {
		return datas;
	}

	public void setDatas(Datas datas) {
		this.datas = datas;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public List<Questions> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Questions> questions) {
		this.questions = questions;
	}

	

/*	public Double getAvailablebalance() {
		return availablebalance;
	}

	public void setAvailablebalance(Double availablebalance) {
		this.availablebalance = availablebalance;
	}

	private Double availablebalance;*/

}
