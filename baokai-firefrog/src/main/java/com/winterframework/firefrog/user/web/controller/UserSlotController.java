package com.winterframework.firefrog.user.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.user.service.IUserSlotExhangeService;
import com.winterframework.firefrog.user.web.dto.UserSlotExchangeByUserRequest;
import com.winterframework.firefrog.user.web.dto.UserSlotExchangeByUserResponse;
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
import com.winterframework.firefrog.user.web.dto.UserSlotOccupancySaveRequest;
import com.winterframework.firefrog.user.web.dto.UserSlotOccupancySaveResponse;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("userSlotController")
@RequestMapping(value = "/user/slot/")
public class UserSlotController {
	private static final Logger logger = LoggerFactory
			.getLogger(UserSlotController.class);

	@Resource(name = "RedisClient")
	private RedisClient redis;

	@Resource(name = "userSlotExhangeServiceImpl")
	private IUserSlotExhangeService userSlotExhangeService;

	/**
	 * 產生拉霸獎金
	 */
	@RequestMapping(value = "change")
	public @ResponseBody Response<UserSlotExchangeChangeResponse> change(
			@RequestBody Request<UserSlotExchangeChangeRequest> request)
			throws Exception {

		logger.info("===change Start");

		String activityType = request.getBody().getParam().getActivityType();
		
		logger.info("=activityType:"+activityType);
		
		Response<UserSlotExchangeChangeResponse> response = new Response<UserSlotExchangeChangeResponse>();

		UserSlotExchangeChangeResponse result;
		try {
			result = userSlotExhangeService.change(activityType);
			response.setResult(result);
			logger.info("exchangeNumber:" + result.getExchangeNumber());
			logger.info("exchangePrize:" + result.getExchangePrize());
		} catch (Exception e) {
			logger.error("change error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 領取獎項
	 */
	@RequestMapping(value = "occupancy")
	public @ResponseBody Response<UserSlotOccupancyResponse> occupancy(
			@RequestBody Request<UserSlotOccupancyRequest> request)
			throws Exception {

		logger.info("===occupancy Start");
		
		String cellularPhone = request.getBody().getParam().getCellularPhone();
		String exchangeNumber = request.getBody().getParam().getExchangeNumber();
		Long exchangePrize = request.getBody().getParam().getExchangePrize();
		String activityType = request.getBody().getParam().getActivityType();
		
		logger.info("=cellularPhone:" + cellularPhone);
		logger.info("=exchangeNumber:" + exchangeNumber);
		logger.info("=exchangePrize:" + exchangePrize);
		logger.info("=activityType:" + activityType);

		Response<UserSlotOccupancyResponse> response = new Response<UserSlotOccupancyResponse>();

		UserSlotOccupancyResponse result;
		try {
			result = userSlotExhangeService.occupancy(activityType, cellularPhone, exchangeNumber, exchangePrize);
			response.setResult(result);
		} catch (Exception e) {
			logger.error("occupancy error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 領取獎項存檔
	 */
	@RequestMapping(value = "occupancySave")
	public @ResponseBody Response<UserSlotOccupancySaveResponse> occupancySave(
			@RequestBody Request<UserSlotOccupancySaveRequest> request)
			throws Exception {

		logger.info("===occupancySave Start");
		Long userId = request.getBody().getParam().getUserId();
		String cellularPhone = request.getBody().getParam().getCellularPhone();
		String activityType = request.getBody().getParam().getActivityType();
		logger.info("=userId:" + userId);
		logger.info("=cellularPhone:" + cellularPhone);
		logger.info("=activityType:" + activityType);

		Response<UserSlotOccupancySaveResponse> response = new Response<UserSlotOccupancySaveResponse>();

		UserSlotOccupancySaveResponse result;
		try {
			result = userSlotExhangeService.occupancySave(userId, cellularPhone, activityType);
			response.setResult(result);
		} catch (Exception e) {
			logger.error("occupancySave error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 兌換驗證
	 */
	@RequestMapping(value = "modifyExchangeStyle")
	public @ResponseBody Response<UserSlotExchangeModifyResponse> modifyExchangeStyle(
			@RequestBody Request<UserSlotExchangeModifyRequest> request)
			throws Exception {

		logger.info("===modifyExchangeStyle Start");
		Long type = request.getBody().getParam().getType();
		String exchangeNumber = request.getBody().getParam().getExchangeNumber();
		logger.info("=type:"+type);
		logger.info("=exchangeNumber:"+exchangeNumber);

		Response<UserSlotExchangeModifyResponse> response = new Response<UserSlotExchangeModifyResponse>();

		UserSlotExchangeModifyResponse result;
		result = userSlotExhangeService.modifyExchange(type, exchangeNumber);
		response.setResult(result);
		return response;
	}
	
	/**
	 * 兌換驗證
	 */
	@RequestMapping(value = "exchangeCheck")
	public @ResponseBody Response<UserSlotExchangeCheckResponse> exchangeCheck(
			@RequestBody Request<UserSlotExchangeCheckRequest> request)
			throws Exception {

		logger.info("===exchangeCheck Start");
		Long userId = request.getBody().getParam().getUserId();
		logger.info("=userId:"+userId);

		Response<UserSlotExchangeCheckResponse> response = new Response<UserSlotExchangeCheckResponse>();

		UserSlotExchangeCheckResponse result;
		result = userSlotExhangeService.exchangeCheck(userId);
		response.setResult(result);
		return response;
	}
	
	/**
	 * 兌換獎金
	 */
	@RequestMapping(value = "exchange")
	public @ResponseBody Response<UserSlotExchangeResponse> exchange(
			@RequestBody Request<UserSlotExchangeRequest> request)
			throws Exception {

		logger.info("===exchange Start");
		Long userId = request.getBody().getParam().getUserId();
		String exchangeNumber = request.getBody().getParam().getExchangeNumber();
		String clientIp = request.getBody().getParam().getClientIp();
		logger.info("=userId:"+userId);
		logger.info("=exchangeNumber:"+exchangeNumber);
		logger.info("=clientIp:"+clientIp);

		Response<UserSlotExchangeResponse> response = new Response<UserSlotExchangeResponse>();
		
		UserSlotExchangeResponse result;
		result = userSlotExhangeService.exchange(userId, exchangeNumber, clientIp);
		response.setResult(result);
		return response;
	}

	/**
	 * 針對FF USER_ID 查詢拉霸用戶
	 */
	@RequestMapping(value = "queryExchange")
	public @ResponseBody Response<UserSlotExchangeByUserResponse> queryExchange(
			@RequestBody Request<UserSlotExchangeByUserRequest> request)
			throws Exception {

		logger.info("===queryExchange Start");
		Long userId = request.getBody().getParam().getUserId();
		logger.info("=userId:"+userId);
		
		Response<UserSlotExchangeByUserResponse> response = new Response<UserSlotExchangeByUserResponse>();

		UserSlotExchangeByUserResponse result;
		try {
			result = userSlotExhangeService.getSlotExchangeByUser(userId);
			response.setResult(result);
		} catch (Exception e) {
			logger.error("queryExchange error.", e);
			throw e;
		}
		return response;
	}
}
