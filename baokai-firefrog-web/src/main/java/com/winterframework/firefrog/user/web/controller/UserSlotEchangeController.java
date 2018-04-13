package com.winterframework.firefrog.user.web.controller;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.user.web.dto.UserSlotExchangeChangeRequest;
import com.winterframework.firefrog.user.web.dto.UserSlotExchangeChangeResponse;
import com.winterframework.firefrog.user.web.dto.UserSlotExchangeCheckRequest;
import com.winterframework.firefrog.user.web.dto.UserSlotExchangeCheckResponse;
import com.winterframework.firefrog.user.web.dto.UserSlotExchangeModifyRequest;
import com.winterframework.firefrog.user.web.dto.UserSlotExchangeModifyResponse;
import com.winterframework.firefrog.user.web.dto.UserSlotExchangeRequest;
import com.winterframework.firefrog.user.web.dto.UserSlotExchangeResponse;
import com.winterframework.firefrog.user.web.dto.UserSlotOccupancyRequest;
import com.winterframework.firefrog.user.web.dto.UserSlotOccupancyResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.RequestContext;

@Controller("userSlotEchangeController")
@RequestMapping(value = "/user/slot")
public class UserSlotEchangeController {

	private static final Logger logger = LoggerFactory
			.getLogger(UserSlotEchangeController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.user.slot.occupancy")
	private String occupancy;

	@PropertyConfig(value = "url.user.slot.occupancySave")
	private String occupancySave;
	
	@PropertyConfig(value = "url.user.slot.modifyExchangeStyle")
	private String modifyExchangeStyle;
	
	@PropertyConfig(value = "url.user.slot.exchangeCheck")
	private String exchangeCheck;
	
	@PropertyConfig(value = "url.user.slot.exchange")
	private String exchange;

	@PropertyConfig(value = "url.user.slot.queryExchange")
	private String queryExchange;

	@PropertyConfig(value = "url.user.slot.change")
	private String change;

	@PropertyConfig(value = "url.connect")
	private String urlPath;

	@RequestMapping(value = "/change")
	public @ResponseBody Response<UserSlotExchangeChangeResponse> change(@Valid UserSlotExchangeChangeRequest request) throws Exception {

		logger.info("===change Start");

		String activityType = request.getActivityType();
		
		logger.info("activityType:" + activityType);
		
		Response<UserSlotExchangeChangeResponse> response = new Response<UserSlotExchangeChangeResponse>();

		try {
			UserSlotExchangeChangeRequest req = new UserSlotExchangeChangeRequest();
			req.setActivityType(activityType);
			response = httpClient.invokeHttp(urlPath+change,req ,new TypeReference<Response<UserSlotExchangeChangeResponse>>(){});
		} catch (Exception e) {
			logger.error("change error", e);
			throw e;
		}
		return response;
	}
	
	@RequestMapping(value = "/occupancy")
	public @ResponseBody Response<UserSlotOccupancyResponse> occupancy(@Valid UserSlotOccupancyRequest request) throws Exception {

		logger.info("===occupancy Start");
		
		String cellularPhone = request.getCellularPhone();
		String exchangeNumber = request.getExchangeNumber();
		Long exchangePrize = request.getExchangePrize();
		String activityType = request.getActivityType();
		
		logger.info("=cellularPhone:" + cellularPhone);
		logger.info("=exchangeNumber:" + exchangeNumber);
		logger.info("=exchangePrize:" + exchangePrize);
		logger.info("=activityType:" + activityType);

		Response<UserSlotOccupancyResponse> response;

		try {
			UserSlotOccupancyRequest req = new UserSlotOccupancyRequest();
			req.setCellularPhone(cellularPhone);
			req.setExchangeNumber(exchangeNumber);
			req.setExchangePrize(exchangePrize);
			req.setActivityType(activityType);
			response = httpClient.invokeHttp(urlPath + occupancy,req,new TypeReference<Response<UserSlotOccupancyResponse>>(){});
		} catch (Exception e) {
			logger.error("occupancy error", e);
			throw e;
		}
		return response;
	}

	@RequestMapping(value = "/modifyExchangeStyle")
	public @ResponseBody Response<UserSlotExchangeModifyResponse> modifyExchangeStyle(
			@Valid UserSlotExchangeModifyRequest request) throws Exception {

		logger.info("===modifyExchangeStyle Start");
		Long type = request.getType();
		String exchangeNumber = request.getExchangeNumber();
		logger.info("=type:" + type);
		logger.info("=exchangeNumber:" + exchangeNumber);

		Response<UserSlotExchangeModifyResponse> response;

		try {
			UserSlotExchangeModifyRequest req = new UserSlotExchangeModifyRequest();
			req.setType(type);
			req.setExchangeNumber(exchangeNumber);
			response = httpClient.invokeHttp(urlPath + modifyExchangeStyle,req,new TypeReference<Response<UserSlotExchangeModifyResponse>>(){});
		} catch (Exception e) {
			logger.error("modifyExchangeStyle error", e);
			throw e;
		}
		return response;
	}
	
	@RequestMapping(value = "/exchangeCheck")
	public @ResponseBody Response<UserSlotExchangeCheckResponse> exchangeCheck(
			@Valid UserSlotExchangeCheckRequest request) throws Exception {

		logger.info("===exchangeCheck Start");
		Long userId = RequestContext.getCurrUser().getId();
		logger.info("=userId:" + userId);

		Response<UserSlotExchangeCheckResponse> response;

		try {
			UserSlotExchangeCheckRequest req = new UserSlotExchangeCheckRequest();
			req.setUserId(userId);
			response = httpClient.invokeHttp(urlPath + exchangeCheck,req,new TypeReference<Response<UserSlotExchangeCheckResponse>>(){});
		} catch (Exception e) {
			logger.error("exchangeCheck error", e);
			throw e;
		}
		return response;
	}
	
	@RequestMapping(value = "/exchange")
	public @ResponseBody Response<UserSlotExchangeResponse> exchange(
			@Valid UserSlotExchangeRequest request,HttpServletRequest httpRequest) throws Exception {

		logger.info("===exchange Start");
		Long userId = RequestContext.getCurrUser().getId();
		String exchangeNumber = request.getExchangeNumber();
		String clientIp = getClientIpAddr(httpRequest);
		logger.info("=userId:" + userId);
		logger.info("=exchange:" + exchangeNumber);
		logger.info("=clientIp:" + clientIp);

		Response<UserSlotExchangeResponse> response;

		try {
			UserSlotExchangeRequest req = new UserSlotExchangeRequest();
			req.setUserId(userId);
			req.setExchangeNumber(exchangeNumber);
			req.setClientIp(clientIp);
			response = httpClient.invokeHttp(urlPath + exchange,req,new TypeReference<Response<UserSlotExchangeResponse>>(){});
		} catch (Exception e) {
			logger.error("exchange error", e);
			throw e;
		}
		return response;
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
	        return tt[tt.length-1].trim(); 
	    } 
}
