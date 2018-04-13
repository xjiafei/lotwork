package com.winterframework.firefrog.active.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.active.web.dto.UserActivityRegisterRequest;
import com.winterframework.firefrog.active.web.dto.UserActivityRegisterResponse;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("activeController")
@RequestMapping(value = "/active")
public class ActiveController {

	
	@PropertyConfig(value = "url.channel.getParam")
	private String getParam;
	
	@PropertyConfig(value = "url.channel.action")
	private String action;
	
//	@PropertyConfig(value = "url.user.getPtUser")
//	private String getPtUser;
	
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;
	
	@PropertyConfig(value = "url.connect")
	private String serverPath;
	
	private static final Logger logger = LoggerFactory.getLogger(ActiveController.class);
	
	@RequestMapping(value = "/pt")
	public ModelAndView action (@RequestParam("param") String param,@RequestParam(value="id",required=false) String id,@RequestParam(value="exp",required=false) String exp
			,@RequestParam(value="token",required=false) String token,@RequestParam(value="pid",required=false) String pid) throws Exception{
		
		ModelAndView mav = null;
		if(param.equals("a")){
			mav = new ModelAndView("/active/pt/index-pt-register");
		}else if(param.equals("b")){
			mav = new ModelAndView("/active/pt/index-pt-register-frame");
		}else if(param.equals("c")){
			mav = new ModelAndView("/active/pt/game-trial");
		}else if(param.equals("d")){
			mav = new ModelAndView("/active/pt/index-pt-login-frame");
		}
		return mav;
	}
	
//	@RequestMapping(value = "/ptRegister")
//	public @ResponseBody Response<UserActivityRegisterResponse> ptRegister(@RequestBody Request<UserActivityRegisterRequest> request) throws Exception {
//
//		logger.error("===ptRegister Start");
//		Long userId = request.getBody().getParam().getUserId();
//		Long type = request.getBody().getParam().getType();
//		logger.error("=userId:" + userId);
//		logger.error("=type:" + type);
//
//		Response<UserActivityRegisterResponse> response;
//
//		try {
//			UserActivityRegisterRequest req = new UserActivityRegisterRequest();
//			req.setUserId(userId);
//			req.setType(type);
//			response = httpClient.invokeHttp(serverPath+"/user/profile/getPtUser",req,new TypeReference<Response<UserActivityRegisterResponse>>(){});
//		} catch (Exception e) {
//			logger.error("ptRegister error", e);
//			throw e;
//		}
//		return response;
//	}
	
}
	