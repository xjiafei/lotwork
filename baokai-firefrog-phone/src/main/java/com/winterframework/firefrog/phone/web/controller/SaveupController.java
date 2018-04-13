package com.winterframework.firefrog.phone.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.phone.web.cotroller.dto.SaveupCheckRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.SaveupCheckResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.SaveupCommitRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.SaveupCommitResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.SaveupDataRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.SaveupDataResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserToken;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@RequestMapping("/user")
@Controller("saveupController")
public class SaveupController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(SaveupController.class);
	
	@ResponseBody
	@RequestMapping("/saveupData")
	public Response<SaveupDataResponse> saveupData(@RequestBody Request<SaveupDataRequest> request) throws Exception{
		
		Response<SaveupDataResponse> response = new Response<SaveupDataResponse>(request);
		
		String token = request.getHead().getSessionId();
		try {
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			
			
		} catch (Exception e) {
			log.error("saveupData error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/saveupCheck")
	public Response<SaveupCheckResponse> saveupCheck(@RequestBody Request<SaveupCheckRequest> request) throws Exception{
		Response<SaveupCheckResponse>  response = new Response<SaveupCheckResponse>(request);
		
		String token = request.getHead().getSessionId();
		try {
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
		} catch (Exception e) {
			log.error("saveupCheck error", e);
			response.getHead().setStatus(901000L);
		}
		
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/saveupCommit")
	public Response<SaveupCommitResponse> saveupCommit(@RequestBody Request<SaveupCommitRequest> request) throws Exception{
		
		Response<SaveupCommitResponse> response = new Response<SaveupCommitResponse>(request);
		String token = request.getHead().getSessionId();
		try {
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			
		} catch (Exception e) {
			log.error("saveupCommit error", e);
			response.getHead().setStatus(901000L);
		}
		
		return response;
	}

}
