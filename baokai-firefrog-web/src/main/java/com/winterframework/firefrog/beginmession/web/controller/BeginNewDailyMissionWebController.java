package com.winterframework.firefrog.beginmession.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winterframework.firefrog.beginmession.web.dto.BeginNewDailyMissionWebRequest;
import com.winterframework.firefrog.beginmession.web.dto.BeginNewDailyMissionWebResponse;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.IUser;
import com.winterframework.modules.web.util.RequestContext;

@Controller
@RequestMapping("/begin")
public class BeginNewDailyMissionWebController {

	
	
	private static final Logger log = LoggerFactory.getLogger(BeginNewDailyMissionWebController.class);
	
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;
	
	@PropertyConfig(value = "url.connect")
	private String urlPath;
	
	@PropertyConfig(value = "url.begin.queryDailyMission")
	private String queryDailyMission;
	
	@PropertyConfig(value = "url.begin.saveDailyMission")
	private String saveDailyMission;
	
	@RequestMapping(method=RequestMethod.POST,value="insertDailyMission")
	public ModelAndView insertNewMission(@ModelAttribute BeginNewDailyMissionWebRequest request)throws Exception{
		try {
			IUser user = RequestContext.getCurrUser();
			request.setUserName(user.getUserName());
			httpClient.invokeHttpWithoutResultType(urlPath + saveDailyMission, request);
		} catch (Exception e) {
			log.error("insertNewMission error:", e);
			throw e;
		}
		return searchMission(request);
	}
	
	@RequestMapping(value="toDailyMission",method=RequestMethod.GET)
	public ModelAndView searchMission(BeginNewDailyMissionWebRequest request)throws Exception{
		
		ModelAndView model = new ModelAndView("advert/begin/toDailyMission");
		Response<BeginNewDailyMissionWebResponse> response = httpClient.invokeHttp(urlPath + queryDailyMission, request, BeginNewDailyMissionWebResponse.class);
		BeginNewDailyMissionWebResponse res= response.getBody().getResult();
		model.addObject("daybets",res.getBeginNewDaybets());
		model.addObject("dayqueses",res.getBeginNewDayqueses());
		model.addObject("tolbets",res.getBeginNewTolbets());
		model.addObject("beginNewMission",res.getBeginNewMission());
		return model;
	}
	
}
