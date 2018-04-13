package com.winterframework.firefrog.beginmession.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winterframework.firefrog.beginmession.web.dto.BeginNewMissionWebRequest;
import com.winterframework.firefrog.beginmession.web.dto.BeginNewMissionWebResponse;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.IUser;
import com.winterframework.modules.web.util.RequestContext;

@Controller
@RequestMapping(value ="/begin")
public class BeginNewMissionWebController {

	private static final Logger log = LoggerFactory.getLogger(BeginNewMissionWebController.class);
	
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;
	
	@PropertyConfig(value = "url.connect")
	private String urlPath;
	
	@PropertyConfig(value = "url.begin.queryMission")
	private String queryMission;
	
	@PropertyConfig(value = "url.begin.saveMission")
	private String saveMission;
	
	@RequestMapping(method=RequestMethod.POST,value="insertNewMission")
	public ModelAndView insertNewMission(@ModelAttribute BeginNewMissionWebRequest request)throws Exception{
		try {
			IUser user = RequestContext.getCurrUser();
			request.setUserName(user.getUserName());
			httpClient.invokeHttpWithoutResultType(urlPath + saveMission, request);
		} catch (Exception e) {
			log.error("insertNewMission error:", e);
			throw e;
		}
		return searchMission(request);
	}
	
	@RequestMapping(value="toNewMission")
	public ModelAndView searchMission(BeginNewMissionWebRequest request)throws Exception{
		
		ModelAndView model = new ModelAndView("advert/begin/toNewMission");
		Response<BeginNewMissionWebResponse> response = httpClient.invokeHttp(urlPath + queryMission, request, BeginNewMissionWebResponse.class);
		BeginNewMissionWebResponse res= response.getBody().getResult();
		model.addObject("mission",res.getMission());
		model.addObject("charges", res.getCharges());
		return model;
	}
	
}
