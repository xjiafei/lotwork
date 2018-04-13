package com.winterframework.firefrog.active.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.active.service.IActivityVipService;
import com.winterframework.firefrog.active.web.dto.ActivityVipRequest;
import com.winterframework.firefrog.user.dao.vo.VipActivityVo;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("activeVipController")
@RequestMapping(value = "/activity")
public class ActivityVipController {

	@Resource(name = "activityVipImpl")
	private IActivityVipService activityServiceImpl;

	private static final Logger log = LoggerFactory.getLogger(ActivityVipController.class);	

	/**
	* @Title: sendEmail 
	* @Description: 发邮件
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/vip")
	public @ResponseBody
	Response<Object> sendEmail(@RequestBody Request<ActivityVipRequest> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		ActivityVipRequest struc = request.getBody().getParam();
		log.debug(request.getBody().getParam().getAccount());
		log.debug(request.getBody().getParam().getMonth().toString());
		log.debug(request.getBody().getParam().getToken());
		log.debug(request.getBody().getParam().getSource());
		log.debug(request.getBody().getParam().getStartTime());
		log.debug(request.getBody().getParam().getEndTime());		
		Integer result = activityServiceImpl.saveApplicaiton(struc);
		response.setResult(result);
		return response;
	}

	@RequestMapping(value = "/activityIndex")
	public @ResponseBody
	Response<VipActivityVo> activityIndex(@RequestBody Request<ActivityVipRequest> request) throws Exception {
		Response<VipActivityVo> response = new Response<VipActivityVo>(request);
		ActivityVipRequest struc = request.getBody().getParam();
		log.debug(request.getBody().getParam().getAccount());
		log.debug(request.getBody().getParam().getMonth().toString());
		log.debug(request.getBody().getParam().getToken());
		log.debug(request.getBody().getParam().getSource());
		log.debug(request.getBody().getParam().getStartTime());
		log.debug(request.getBody().getParam().getEndTime());		
		VipActivityVo result = activityServiceImpl.getActivityInfo(struc);
		response.setResult(result);
		return response;
	}
}
