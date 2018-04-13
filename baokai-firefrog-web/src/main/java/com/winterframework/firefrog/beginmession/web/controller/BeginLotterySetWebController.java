package com.winterframework.firefrog.beginmession.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winterframework.firefrog.beginmession.web.dto.BeginLotterySetWebRequest;
import com.winterframework.firefrog.beginmession.web.dto.BeginLotterySetWebResponse;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.IUser;
import com.winterframework.modules.web.util.RequestContext;

@Controller
@RequestMapping("/begin")
public class BeginLotterySetWebController {

	private static final Logger log = LoggerFactory.getLogger(BeginLotterySetWebController.class);
	
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;
	
	@PropertyConfig(value = "url.connect")
	private String urlPath;
	
	@PropertyConfig(value = "url.begin.queryLotterySet")
	private String queryLotterySet;
	
	@PropertyConfig(value = "url.begin.saveLotterySet")
	private String saveLotterySet;
	
	@RequestMapping(method=RequestMethod.POST,value="insertLotterySet")
	public ModelAndView insertLotterySet(@ModelAttribute BeginLotterySetWebRequest request)throws Exception{
		try {
			IUser user = RequestContext.getCurrUser();
			request.setUserName(user.getUserName());
			httpClient.invokeHttpWithoutResultType(urlPath + saveLotterySet, request);
		} catch (Exception e) {
			log.error("insertNewMission error:", e);
			throw e;
		}
		return searchLotterySet(request);
	}
	
	@RequestMapping(value="toLotterySet",method=RequestMethod.GET)
	public ModelAndView searchLotterySet(BeginLotterySetWebRequest request)throws Exception{
		
		ModelAndView model = new ModelAndView("advert/begin/toLotterySet");
		Response<BeginLotterySetWebResponse> response = httpClient.invokeHttp(urlPath + queryLotterySet, request, BeginLotterySetWebResponse.class);
		BeginLotterySetWebResponse res= response.getBody().getResult();
		model.addObject("lotterySets",res.getBeginLotterySets());
		return model;
	}
	
}
