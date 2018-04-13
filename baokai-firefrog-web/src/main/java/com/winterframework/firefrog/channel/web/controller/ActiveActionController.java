package com.winterframework.firefrog.channel.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.channel.web.dto.ActionRequest;
import com.winterframework.firefrog.channel.web.dto.ChannelDto;
import com.winterframework.firefrog.channel.web.dto.ChannelResponse;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("activeActionController")
@RequestMapping(value = "/active")
public class ActiveActionController {

	
	@PropertyConfig(value = "url.channel.getParam")
	private String getParam;
	
	@PropertyConfig(value = "url.channel.action")
	private String action;
	
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;
	
	@PropertyConfig(value = "url.connect")
	private String serverPath;
	
	@RequestMapping(value = "/action")
	public ModelAndView action (@RequestParam("param") String param) throws Exception{
		ModelAndView mav = new ModelAndView("/channel/redirect");
		Long id =(Long)httpClient.invokeHttp(serverPath+getParam,param,new TypeReference<Response<Long>>(){}).getBody().getResult();
		if(id != null){
			Response<ChannelResponse> res =httpClient.invokeHttp(serverPath+action,id,new TypeReference<Response<ChannelResponse>>(){});
			if(res.getBody().getResult().getStatus() == 0l){
				if(res.getBody().getResult().getUrl() != null){
					mav.setViewName("/channel/frameset");
				}
			}
			mav.addObject("channel", res.getBody().getResult());
			
		}else{
			mav.addObject("error",5l);
		}
		return mav;
	}
	
	@RequestMapping(value = "/labar")
	public ModelAndView test (@RequestParam("param") String param ,HttpServletRequest req) throws Exception{
		ModelAndView mav = new ModelAndView("/channel/redirect");
		Long id =(Long)httpClient.invokeHttp(serverPath+getParam,param,new TypeReference<Response<Long>>(){}).getBody().getResult();
		if(id != null){
			ActionRequest dto = new ActionRequest();
			dto.setId(id);
			dto.setIp(getClientIpAddr(req));
			Response<ChannelResponse> res =httpClient.invokeHttp(serverPath+action,dto,new TypeReference<Response<ChannelResponse>>(){});
			if(res.getBody().getResult().getStatus() == 0l){
				if(res.getBody().getResult().getUrl() != null){
					mav.setViewName("/channel/frameset");
					mav.addObject("url", "http://www.dev03.com/gameActive/labar");
				}
			}
			mav.addObject("channel", res.getBody().getResult());
			
		}else{
			mav.addObject("error",5l);
		}
		return mav;
	}
	
	 public static String getClientIpAddr(HttpServletRequest request) { 
	        String ip = request.getHeader("x-forwarded-for"); 
	        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getHeader("Proxy-Client-IP"); 
	        } 
	        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getHeader("WL-Proxy-Client-IP"); 
	        } 
	        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getRemoteAddr(); 
	        } 
	        if(ip!=null && ip.length()>0){
	        	
	        }else{
	        	return "";
	        }
	        String[] tt=ip.split(",");
	        return tt[0].trim(); 
	    } 
	
}
