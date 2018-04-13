package com.winterframework.firefrog.game.web.controller;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.winterframework.firefrog.game.service.IActivityService;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("ActivityController")
@RequestMapping("/activity")
public class ActivityController {
	
	private static final Logger log = LoggerFactory.getLogger(ActivityController.class);
	
	@Resource(name = "ativityServiceImpl")
	private IActivityService ativityServiceImpl;

	@RequestMapping(value = "/excute")
	@ResponseBody
	public Response<Object> excute() throws Exception {

		Response<Object> response = new Response<Object>();

		try {
			
			ativityServiceImpl.excute();

		} catch (Exception e) {

			log.error("generateGamePlan error:", e);
		
		}

		return response;
	}
	

	@RequestMapping(value = "/excuteConfig")
	@ResponseBody
	public Response<Object> excuteConfig() throws Exception {

		Response<Object> response = new Response<Object>();

		try {
			
			ativityServiceImpl.excuteConfig();

		} catch (Exception e) {

			log.error("generateGamePlan error:", e);
		
		}

		return response;
	}
	
}
