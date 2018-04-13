package com.winterframework.firefrog.gameActive.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.game.web.controller.BetMethodDescriptionWebController;
import com.winterframework.firefrog.game.web.util.IPUtil;
import com.winterframework.firefrog.gameActive.dto.ActionRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("gameActiveController")
@RequestMapping(value = "/gameActive")
public class GameActiveController {
	
	private Logger logger = LoggerFactory.getLogger(BetMethodDescriptionWebController.class);
	
	@PropertyConfig(value = "url.channel.action")
	private String action;
	
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;
	
	@PropertyConfig(value = "url.connect.api")
	private String serverPath;
	
	@RequestMapping(value = "/labar")
	public ModelAndView labarIndex (@RequestParam("param") String param ,HttpServletRequest req) throws Exception{
		ModelAndView mav = new ModelAndView("/active/labar/index");
			ActionRequest dto = new ActionRequest();
			dto.setParam(param);
			dto.setIp(IPUtil.getClientIpAddr(req));
			Response<Long> res =httpClient.invokeHttp(serverPath+action,dto,new TypeReference<Response<Long>>(){});
			if(res.getBody().getResult() == 0l){
				mav.addObject("activityType","PT_NEW_ONE");
			}else{
				mav.setViewName("/active/labar/redirect");
				mav.addObject("status",res.getBody().getResult());
			}
		return mav;
	}

	@RequestMapping(value = "/labar/sub")
	public ModelAndView labarSub() throws Exception {
		ModelAndView mav = new ModelAndView("/active/labar/sub");
		return mav;
	}
	
	@RequestMapping(value = "/mmc/diamondHelp")
	public ModelAndView diamondHelp() throws Exception {
		ModelAndView mav = new ModelAndView("/active/mmcHelp/explaintion");
		return mav;
	}
	
	@RequestMapping(value = "/mmc/diamondHelpguilder")
	public ModelAndView diamondHelpguilder() throws Exception {
		ModelAndView mav = new ModelAndView("/active/mmcHelp/explaintionguider");
		return mav;
	}

}
