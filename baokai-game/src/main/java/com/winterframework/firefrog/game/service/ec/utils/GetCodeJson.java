package com.winterframework.firefrog.game.service.ec.utils;

import java.util.List;

public class GetCodeJson {
	private String isSuccess;
	private String message;
	private List<NumberJson> numbers;
	
	public String getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<NumberJson> getNumbers() {
		return numbers;
	}
	public void setNumbers(List<NumberJson> numbers) {
		this.numbers = numbers;
	}

}
