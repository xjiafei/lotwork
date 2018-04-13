package com.winterframework.firefrog.active.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.winterframework.firefrog.active.web.dto.ActivitySignUpRequest;

public interface IActivityService {

	/** 
	* @Title: saveSignUp 
	* @Description: 11月紅包報名 
	* @param appeal
	* @throws JsonProcessingException
	* @throws Exception
	*/
	public Integer saveSignUp(ActivitySignUpRequest appeal) throws Exception;
	
	
	public Long querySignUp(ActivitySignUpRequest appeal) throws Exception;


}
