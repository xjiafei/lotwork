package com.winterframework.firefrog.game.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.vo.ActivityAwardConfig;
import com.winterframework.firefrog.game.dao.vo.ActivityConfig;
import com.winterframework.firefrog.game.dao.vo.DailyActivityVo;
import com.winterframework.firefrog.game.dao.vo.MMCRanking;
import com.winterframework.firefrog.game.dao.vo.UserSystemUpdateLog;
import com.winterframework.firefrog.game.service.IActivityUserChargeService;
import com.winterframework.firefrog.game.web.dto.ActCfgResponse;
import com.winterframework.firefrog.game.web.dto.ActivityConfigRequest;
import com.winterframework.firefrog.game.web.dto.ActivityConfigResponse;
import com.winterframework.firefrog.game.web.dto.ActivityQueryUserUpdateResponse;
import com.winterframework.firefrog.game.web.dto.ActivityUserBetOrChargeAmountStruc;
import com.winterframework.firefrog.game.web.dto.ActivityUserUpdateActionResponse;
import com.winterframework.firefrog.game.web.dto.ActivityUserUpdateRequest;
import com.winterframework.firefrog.game.web.dto.DailyActivityRequest;
import com.winterframework.firefrog.game.web.dto.DailyActivityResponse;
import com.winterframework.firefrog.game.web.dto.DailyActivityRewardStruc;
import com.winterframework.firefrog.game.web.dto.GetLuckyNumberRequest;
import com.winterframework.firefrog.game.web.dto.GetLuckyNumberResponse;
import com.winterframework.firefrog.game.web.dto.GetLuckyRequest;
import com.winterframework.firefrog.game.web.dto.GetLuckyResponse;
import com.winterframework.firefrog.game.web.dto.MmcRankingHistoryResponse;
import com.winterframework.firefrog.game.web.dto.MmcRankingRequest;
import com.winterframework.firefrog.game.web.dto.MmcRankingResponse;
import com.winterframework.firefrog.game.web.dto.UserHbApplyTimeRequest;
import com.winterframework.firefrog.game.web.dto.UserInfoRequest;
import com.winterframework.firefrog.game.web.dto.UserInfoResponse;
import com.winterframework.firefrog.shortlived.mmcRanking.service.IMMCRankingService;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepBigLittle;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepDetail;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepHongBao;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepWheelSurf;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserApplyHongBaoRequest;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserApplyHongBaoResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserDiceAwardRequest;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserDiceAwardResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserDiceRequest;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserDiceResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserHongBaoAbortRequest;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserHongBaoAbortResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserHongBaoDrawRequest;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserHongBaoDrawResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserHongBaoRequest;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserHongBaoResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserRotaryRequest;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserRotaryResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserRotaryRewardRequest;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserRotaryRewardResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IActivityAwardConfigService;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IActivityConfigService;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IActivityService;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IActivitySheepBigLittleService;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IActivitySheepDetailService;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IActivitySheepHongBaoService;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IActivitySheepWheelSurfService;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IUserSystemUpdateLogService;
import com.winterframework.firefrog.shortlived.sheepactivity.service.util.Award;
import com.winterframework.firefrog.shortlived.sheepactivity.service.util.BigLittleAward;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("activityController")
@RequestMapping("/game")
public class ActivityController {

	private Logger log = LoggerFactory.getLogger(ActivityController.class);

	@Resource(name = "activityAwardConfigServiceImpl")
	private IActivityAwardConfigService activityAwardConfigServiceImpl;

	@Resource(name = "activityConfigServiceImpl")
	private IActivityConfigService activityConfigServiceImpl;
	
	@Resource(name = "activityServiceImpl")
	private IActivityService activityServiceImpl;

	@Resource(name = "activityUserChargeServiceImpl")
	private IActivityUserChargeService activityUserChargeServiceImpl;

	@Resource(name = "userSystemUpdateLogServiceImpl")
	private IUserSystemUpdateLogService userSystemUpdateLogServiceImpl;

	@Resource(name = "activitySheepHongBaoServiceImpl")
	private IActivitySheepHongBaoService activitySheepHongBaoServiceImpl;

	@Resource(name = "activitySheepBigLittleServiceImpl")
	private IActivitySheepBigLittleService activitySheepBigLittleServiceImpl;

	@Resource(name = "activitySheepDetailServiceImpl")
	private IActivitySheepDetailService activitySheepDetailServiceImpl;
	
	@Resource(name="activitySheepWheelSurfServiceImpl")
	private IActivitySheepWheelSurfService activitySheepWheelSurfServiceImpl;
	
	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao userCustomerDao;
	
	@Resource(name="mmcRankingService")
	private IMMCRankingService mmcRankingServiceImpl;
	
	

	// GET http://localhost:8090/game/queryUserUpdateLog HTTP/1.1
	// Content-Type: application/firefrog
	// Host: localhost:8090
	// Content-Length: 157
	//
	// {"head":
	// {"sowner":1,"rowner":1,"msn":10000,"msnsn":0,"sendtime":1406519742051,"userId":794,"userAccount":"/admin/henry13/"},
	// "body":{"param":{"userId":1}}}

	// {
	// "head" : {
	// "sowner" : 1,
	// "rowner" : 1,
	// "msn" : 10000,
	// "userId" : 794,
	// "msnsn" : 0,
	// "sendtime" : 1417489888713,
	// "status" : 0
	// },
	// "body" : {
	// "result" : {
	// "isUpdate" : 0,
	// "isRecharge" : 0,
	// "isBet" : 0
	// },
	// "pager" : {
	// "startNo" : 0,
	// "endNo" : 0,
	// "total" : 0
	// }
	// }
	// }

	/**
	 * @Title: queryUserUpdateLog
	 * @Description: 查询用户升级相关情况
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryUserUpdateLog")
	@ResponseBody
	public Response<ActivityQueryUserUpdateResponse> queryUserUpdateLog(
			@RequestBody @ValidRequestBody Request<ActivityUserUpdateRequest> request)
			throws Exception {

		log.info("开始查询用户升级相关情况......");

		Response<ActivityQueryUserUpdateResponse> response = new Response<ActivityQueryUserUpdateResponse>(
				request);
		long userId = request.getBody().getParam().getUserId();
		UserSystemUpdateLog result = null;

		try {
			result = userSystemUpdateLogServiceImpl
					.getUserUpdateSystemByUserId(userId);

		} catch (Exception e) {
			log.error("查询查询用户升级相关情况异常 ", e);
			throw e;
		}

		if (result == null) {
			result = new UserSystemUpdateLog();
		}

		ActivityQueryUserUpdateResponse result1 = new ActivityQueryUserUpdateResponse();
		result1.setIsBet(result.getIsBet());
		result1.setIsRecharge(result.getIsRecharge());
		result1.setIsUpdate(result.getIsUpdate());
		response.setResult(result1);
		log.info("查询查询用户升级相关情况完成。");
		return response;
	}

	// GET http://localhost:8090/game/userUpdate HTTP/1.1
	// Content-Type: application/firefrog
	// Host: localhost:8090
	// Content-Length: 166
	//
	// {"head":
	// {"sowner":1,"rowner":1,"msn":10000,"msnsn":0,"sendtime":1406519742051,"userId":794,"userAccount":"/admin/henry13/"},
	// "body":{"param":{"userId":1,"type":2}}}

	// {
	// "head" : {
	// "sowner" : 1,
	// "rowner" : 1,
	// "msn" : 10000,
	// "userId" : 794,
	// "msnsn" : 0,
	// "sendtime" : 1417507120420,
	// "status" : 0
	// },
	// "body" : {
	// "result" : {
	// "status" : 0,
	// "message" : "success"
	// },
	// "pager" : {
	// "startNo" : 0,
	// "endNo" : 0,
	// "total" : 0
	// }
	// }
	// }

	/**
	 * @Title: queryBetLimit
	 * @Description: 投注首页查询投注限制
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userUpdate")
	@ResponseBody
	public Response<ActivityUserUpdateActionResponse> userUpdate(
			@RequestBody @ValidRequestBody Request<ActivityUserUpdateRequest> request)
			throws Exception {

		log.info("设置用户升级相关情况......");
		Response<ActivityUserUpdateActionResponse> response = new Response<ActivityUserUpdateActionResponse>(
				request);
		long userId = request.getBody().getParam().getUserId();
		int type = request.getBody().getParam().getType().intValue();

		ActivityUserUpdateActionResponse result = new ActivityUserUpdateActionResponse();

		try {
			String resultString = userSystemUpdateLogServiceImpl
					.userUpdateSystem(userId, type);
			result.setStatus(0L);
			result.setMessage(resultString);

		} catch (Exception e) {
			result.setStatus(1L);
			result.setMessage("fail：" + e.getMessage());
			log.error("设置用户升级相关情况异常 ", e);
		}

		response.setResult(result);
		log.info("设置用户升级相关情况完成。");
		return response;
	}
	
	/**
	 * @Title: getActivityConfig
	 * @Description: 查询活动配置
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getActivityConfig")
	@ResponseBody
	public Response<Object> getActivityConfig(@RequestBody Request<ActivityConfigRequest> request)
			throws Exception {
		log.info("activity id : " + request.getBody().getParam().getId());
		log.info("开始 查询活动配置......");
		Response<Object> response = new Response<Object>(request);
		log.info("-------------------------------------------");

		try {
			ActivityConfig config = activityConfigServiceImpl.getById(request.getBody().getParam().getId());
			response.setResult(config);
		}catch(Exception e){
			log.error(" 查询活动配置异常 ", e);
			throw e;
		}
		return response;
	}
	
	/**
	 * @Title: queryActivityConfig
	 * @Description: 查询活动配置
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryActivityConfig")
	@ResponseBody
	public Response<ActivityConfigResponse> queryActivityConfig(
			@RequestBody Request<ActivityConfigRequest> request)
			throws Exception {

		log.info("开始 查询活动配置......");
		Response<ActivityConfigResponse> response = new Response<ActivityConfigResponse>(
				request);

		try {
			List<ActivityAwardConfig> configs = activityAwardConfigServiceImpl.getActivityAwardConfigByActivityId(request.getBody()
							.getParam().getId());
			
			response.setResult(new ActivityConfigResponse(configs));
		} catch (Exception e) {
			log.error(" 查询活动配置异常 ", e);
			throw e;
		}

		log.info(" 查询活动配置完成。");
		return response;
	}

	/**
	 * @Title: checkBetLimit
	 * @Description: 4.32 审核投注限制
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateActivityConfig")
	@ResponseBody
	public Response<Object> checkBetLimit(
			@RequestBody @ValidRequestBody Request<ActivityAwardConfig> request)
			throws Exception {

		log.info("开始审核投注限制......");
		Response<Object> response = new Response<Object>(request);
		Long id = request.getBody().getParam().getId();
		Long lastNumber = request.getBody().getParam().getLastNumber();
		Long multiple = request.getBody().getParam().getMultiple();
		Long ratio = request.getBody().getParam().getRatio();
		try {
			ActivityAwardConfig config = activityAwardConfigServiceImpl
					.getById(id);
			config.setId(id);
			config.setLastNumber(lastNumber);
			config.setMultiple(multiple);
			config.setRatio(ratio);
			if (config.getWinNumber() != null) {
				config.setAllNumber(config.getLastNumber()
						+ config.getWinNumber());
			} else {
				config.setAllNumber(config.getLastNumber());
			}
			activityAwardConfigServiceImpl.update(config);
		} catch (Exception e) {
			log.error("审核投注限制异常 ", e);
			throw e;
		}

		log.info("审核投注限制完成。");
		return response;
	}

	/**
	 * @Title: getUserDailyActivityStruc
	 * @Description: 获取用户日常活动相关信息结构
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUserDailyActivityStruc")
	@ResponseBody
	public Response<DailyActivityResponse> getUserDailyActivityStruc(
			@RequestBody @ValidRequestBody Request<DailyActivityRequest> request)
			throws Exception {
		Response<DailyActivityResponse> response = new Response<DailyActivityResponse>(
				request);
		try {
			List<DailyActivityRewardStruc> strucs = new ArrayList<DailyActivityRewardStruc>();
			DailyActivityResponse result = new DailyActivityResponse();
			List<DailyActivityVo> dailyVos = activityServiceImpl
					.getDailyActivityStrucs(request.getBody().getParam()
							.getStartTime(), request.getBody().getParam()
							.getEndTime(), request.getBody().getParam()
							.getUserId());
			if (dailyVos != null && !dailyVos.isEmpty()) {
				for (DailyActivityVo dvo : dailyVos) {
					DailyActivityRewardStruc ds = new DailyActivityRewardStruc();
					ds.setBetCount(dvo.getBetCount());
					ds.setDate(dvo.getBetDate());
					ds.setHadGet(dvo.getRewardLogid() == null ? false : true);
					strucs.add(ds);
				}
			}

			result.setStrucs(strucs);
			response.setResult(result);
		} catch (Exception e) {
			log.error("获取用户日常活动相关信息结构异常 ", e);
			throw e;
		}

		return response;
	}

	/**
	 * @Title: getLuckyId
	 * @Description: 抽奖
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getLucky")
	@ResponseBody
	public Response<GetLuckyResponse> getLucky(
			@RequestBody @ValidRequestBody Request<GetLuckyRequest> request)
			throws Exception {
		Response<GetLuckyResponse> response = new Response<GetLuckyResponse>(
				request);
		GetLuckyResponse glr = new GetLuckyResponse();
		try {

			Long number = activityServiceImpl.getUserLuckyTime(request
					.getBody().getParam().getUserId(), request.getBody()
					.getParam().getActivityStartTime(), request.getBody()
					.getParam().getActivityEndTime());
			if (number <= 0) {
				log.error("已无可抽奖次数");
				response.getHead().setStatus(500);
				return response;
			}
			Award award = activityServiceImpl.getBetAward(request.getBody()
					.getParam());
			glr.setLuckyId(award.getId());
			glr.setDate(award.getDate());
			glr.setDesc(award.getDesc());
			response.setResult(glr);

		} catch (Exception e) {
			log.error("获取抽奖异常 ", e);
			throw e;
		}
		return response;
	}

	/**
	 * @Title: getLuckyId
	 * @Description: 获取用户的抽奖次数
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getLuckyNumber")
	@ResponseBody
	public Response<GetLuckyNumberResponse> getLuckyNumber(
			@RequestBody @ValidRequestBody Request<GetLuckyNumberRequest> request)
			throws Exception {
		Response<GetLuckyNumberResponse> response = new Response<GetLuckyNumberResponse>(
				request);
		GetLuckyNumberResponse glr = new GetLuckyNumberResponse();
		try {
			Long number = activityServiceImpl.getUserLuckyTime(request
					.getBody().getParam().getUserId(), request.getBody()
					.getParam().getActivityStartTime(), request.getBody()
					.getParam().getActivityEndTime());
			glr.setNumber(number);
			response.setResult(glr);
		} catch (Exception e) {
			log.error("获取抽奖次数异常 ", e);
			throw e;
		}
		return response;
	}

	/**
	 * @Title: getLuckyId
	 * @Description: 获取用户的抽奖次数
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userBetOrChargeAmount")
	@ResponseBody
	public Object betOrChargeAmount(@RequestBody Request<ActivityUserBetOrChargeAmountStruc[]> request) throws Exception {
		 log.info("用户充值或者消费金额插入数据开始");
//		 json =
//		 "[{\"userId\":312,\"type\":1,\"source\":\"3.0\",\"amount\":100000000,\"date\":\"2015-01-11\",\"demo\":\"abcd...\"},{\"userId\":312,\"type\":1,\"source\":\"3.0\",\"amount\":2000000000,\"date\":\"2015-01-12\",\"demo\":\"abcd...\"},{\"userId\":312,\"type\":1,\"source\":\"3.0\",\"amount\":40000000,\"date\":\"2015-01-13\",\"demo\":\"abcd...\"}]";
		try {
			ActivityUserBetOrChargeAmountStruc[] arr = request.getBody().getParam();
			activityUserChargeServiceImpl.saveUserCharge(arr);
		} catch (Exception e) {
			log.error("用户充值或者消费金额插入数据错误", e);
			return "fail";
		}
		return "ok";
	}
	
	@RequestMapping(value = "/getUserHbApplyTime")
	@ResponseBody
	public Object getUserHbApplyTime(@RequestBody Request<UserHbApplyTimeRequest> request) throws Exception {
		 log.info("用户充值或者消费金额插入数据开始");
//		 json =
//		 "[{\"userId\":312,\"type\":1,\"source\":\"3.0\",\"amount\":100000000,\"date\":\"2015-01-11\",\"demo\":\"abcd...\"},{\"userId\":312,\"type\":1,\"source\":\"3.0\",\"amount\":2000000000,\"date\":\"2015-01-12\",\"demo\":\"abcd...\"},{\"userId\":312,\"type\":1,\"source\":\"3.0\",\"amount\":40000000,\"date\":\"2015-01-13\",\"demo\":\"abcd...\"}]";
		 String str="";
		 try {
			ActivitySheepHongBao info = activitySheepHongBaoServiceImpl
					.getUserValidHongBaoInfo(request.getBody().getParam().getUserId());
			if(info!=null){
				str=DateUtils.format(info.getSignTime(),DateUtils.DATETIME_FORMAT_PATTERN);
				
			}
		} catch (Exception e) {
			log.error("获取用户任务进行中的红包申请时间", e);
			return str;
		}
		return str;
	}

	// 获取羊年红包信息
	@RequestMapping(value = "/getUserHongBaoList")
	@ResponseBody
	public Response<ActivitySheepUserHongBaoResponse> getUserHongBaoList(
			@RequestBody @ValidRequestBody Request<ActivitySheepUserHongBaoRequest> request)
			throws Exception {
		Response<ActivitySheepUserHongBaoResponse> response = new Response<ActivitySheepUserHongBaoResponse>(
				request);
		ActivitySheepUserHongBaoResponse result = new ActivitySheepUserHongBaoResponse();
		try {
			List<ActivitySheepHongBao> list = activitySheepHongBaoServiceImpl
					.getUserHongBaoList(request.getBody().getParam()
							.getUserId());
			User user=userCustomerDao.queryUserById(request.getBody().getParam().getUserId());
			String userName=user.getUserProfile().getAccount();
			if (list == null || list.size() == 0) {// 当红包不存在的时候初始化三个红包
				ActivitySheepHongBao bao1 = new ActivitySheepHongBao();
				bao1.setStatus(2L);// 红包1 默认可申领
				bao1.setUserId(request.getBody().getParam().getUserId());
				bao1.setUserName(userName);
				bao1.setIndexHb(1L);
				bao1.setVerifyStatus(0L);
				bao1.setUpdateStatus(0l);
				bao1.setAwardType(1L);
				bao1.setAllAward(0L);
				bao1.setChannel(request.getBody().getParam().getChannel());
				ActivitySheepHongBao bao2 = new ActivitySheepHongBao();
				bao2.setStatus(1L);// 红包2 默认不可申领
				bao2.setUserId(request.getBody().getParam().getUserId());
				bao2.setUserName(userName);
				bao2.setIndexHb(2L);
				bao2.setVerifyStatus(0L);
				bao2.setUpdateStatus(0l);
				bao2.setAwardType(2L);
				bao2.setAllAward(0l);
				bao2.setChannel(request.getBody().getParam().getChannel());
				ActivitySheepHongBao bao3 = new ActivitySheepHongBao();
				bao3.setStatus(1L);// 红包3 默认不可申领
				bao3.setUserId(request.getBody().getParam().getUserId());
				bao3.setUserName(userName);
				bao3.setIndexHb(3L);
				bao3.setVerifyStatus(0L);
				bao3.setUpdateStatus(0l);
				bao3.setAwardType(3L);
				bao3.setAllAward(0l);
				bao3.setChannel(request.getBody().getParam().getChannel());
				list.add(bao1);
				list.add(bao2);
				list.add(bao3);
				activitySheepHongBaoServiceImpl.initUserHongbao(list);
			}
			result.setList(list);
			response.setResult(result);
		} catch (Exception e) {
			log.error("获取用户红包列表异常 ", e);
			throw e;
		}
		return response;
	}

	// 申领红包
	@RequestMapping(value = "/applyUserHongBao")
	@ResponseBody
	public Response<ActivitySheepUserApplyHongBaoResponse> applyUserHongBao(
			@RequestBody @ValidRequestBody Request<ActivitySheepUserApplyHongBaoRequest> request)
			throws Exception {
		Response<ActivitySheepUserApplyHongBaoResponse> response = new Response<ActivitySheepUserApplyHongBaoResponse>(
				request);
		ActivitySheepUserApplyHongBaoResponse result = new ActivitySheepUserApplyHongBaoResponse();
		try {
			activitySheepHongBaoServiceImpl.applyUserHongBao(request.getBody()
					.getParam());
			Long betAmount = activitySheepHongBaoServiceImpl
					.getUserValideBetAmount(request.getBody().getParam()
							.getApplyDate(), request.getBody().getParam()
							.getUserId());
			result.setValidBetAmount(betAmount);
			response.setResult(result);
		} catch (Exception e) {
			log.error("用户申请红包异常 ", e);
			throw e;
		}
		return response;
	}

	// 放弃红包
	@RequestMapping(value = "/abortUserHongBao")
	@ResponseBody
	public Response<ActivitySheepUserHongBaoAbortResponse> abortUserHongBao(
			@RequestBody @ValidRequestBody Request<ActivitySheepUserHongBaoAbortRequest> request)
			throws Exception {
		Response<ActivitySheepUserHongBaoAbortResponse> response = new Response<ActivitySheepUserHongBaoAbortResponse>(
				request);
		try {
			activitySheepHongBaoServiceImpl.abortUserHongBao(request.getBody()
					.getParam().getUserId(), request.getBody().getParam()
					.getIndex());
		} catch (Exception e) {
			log.error("用户放弃红包异常 ", e);
			throw e;
		}
		return response;
	}

	// 领取红包
	@RequestMapping(value = "/drawUserHongBao")
	@ResponseBody
	public Response<ActivitySheepUserHongBaoDrawResponse> drawUserHongBao(
			@RequestBody @ValidRequestBody Request<ActivitySheepUserHongBaoDrawRequest> request)
			throws Exception {
		Response<ActivitySheepUserHongBaoDrawResponse> response = new Response<ActivitySheepUserHongBaoDrawResponse>(
				request);
		try {
			activitySheepHongBaoServiceImpl.drawUserHongBao(request.getBody()
					.getParam().getUserId(), request.getBody().getParam()
					.getIndex());
			ActivitySheepHongBao info = activitySheepHongBaoServiceImpl
					.getUserHongBaoInfo(request.getBody().getParam()
							.getUserId(), request.getBody().getParam()
							.getIndex());
			if(info.getAllAward().longValue()<info.getTargetAward().longValue()){
				log.error("用户领取红包异常 投注未达标");
				throw new Exception();
			}
			ActivitySheepUserHongBaoDrawResponse result = new ActivitySheepUserHongBaoDrawResponse();
			result.setAmount(info.getAward());
			response.setResult(result);
		} catch (Exception e) {
			log.error("用户领取红包异常 ", e);
			throw e;
		}
		return response;
	}

	// 获取用户羊年大小通吃相关信息
	@RequestMapping(value = "/getUserDice")
	@ResponseBody
	public Response<ActivitySheepUserDiceResponse> getUserDice(
			@RequestBody @ValidRequestBody Request<ActivitySheepUserDiceRequest> request)
			throws Exception {
		Response<ActivitySheepUserDiceResponse> response = new Response<ActivitySheepUserDiceResponse>(
				request);
		ActivitySheepUserDiceResponse result = new ActivitySheepUserDiceResponse();
		try {
			ActivitySheepBigLittle bl = activitySheepBigLittleServiceImpl
					.getUserDice(request.getBody().getParam().getUserId());
			
			User user=userCustomerDao.queryUserById(request.getBody().getParam().getUserId());
			if (bl == null) {//当用户在押大小表中没有记录的时候初始化押大小记录
				activitySheepBigLittleServiceImpl.initUserDice(request
						.getBody().getParam().getUserId(),user.getUserProfile().getAccount(),request
						.getBody().getParam().getChannel());
				result.setLastDiceTime(0L);
				result.setDiceContinus(0L);
				result.setList(null);
				response.setResult(result);
				return response;
			}
			List<ActivitySheepDetail> list = activitySheepDetailServiceImpl
					.getUserDiceDetailList(request.getBody().getParam()
							.getUserId());
			result.setLastDiceTime(bl.getLastNum());
			result.setDiceContinus(bl.getNextWinNumNow());
			result.setList(list);
			response.setResult(result);
		} catch (Exception e) {
			log.error("获取用户红包列表异常 ", e);
			throw e;
		}
		return response;
	}
	
		// 押大小结果
		@RequestMapping(value = "/getUserDiceAward")
		@ResponseBody
		public Response<ActivitySheepUserDiceAwardResponse> getUserDiceAward(
				@RequestBody @ValidRequestBody Request<ActivitySheepUserDiceAwardRequest> request)
				throws Exception {
			Response<ActivitySheepUserDiceAwardResponse> response = new Response<ActivitySheepUserDiceAwardResponse>(
					request);
			ActivitySheepUserDiceAwardRequest re=request.getBody().getParam();
			ActivitySheepUserDiceAwardResponse result = new ActivitySheepUserDiceAwardResponse();
			try {
				log.error("dice2.1"+new Date().getTime());
				BigLittleAward award=activitySheepBigLittleServiceImpl.getAward(re.getUserId(), re.getIsGuessLittle(), re.getChannel());
				log.error("dice2.2"+new Date().getTime());
				result.setAward(award.getAward());
				result.setContinuousWinNum(award.getContinuousWinNum());
				result.setLastGuessNum(award.getLastGuessNum());
				result.setResultNum(award.getResultNum());
				result.setHaveAward(award.isHaveAward());
				result.setWin(award.isWin());
				response.setResult(result);
			} catch (Exception e) {
				log.error("获取用户押大小结果异常 ", e);
				throw e;
			}
			return response;
		}
		
		// 转盘结果
		@RequestMapping(value = "/getUserRotaryAward")
		@ResponseBody
		public Response<ActivitySheepUserRotaryRewardResponse> getUserRotaryAward(
				@RequestBody @ValidRequestBody Request<ActivitySheepUserRotaryRewardRequest> request)
				throws Exception {
			Response<ActivitySheepUserRotaryRewardResponse> response = new Response<ActivitySheepUserRotaryRewardResponse>(
					request);
			ActivitySheepUserRotaryRewardRequest re=request.getBody().getParam();
			ActivitySheepUserRotaryRewardResponse result = new ActivitySheepUserRotaryRewardResponse();
			try {
				Award award=activitySheepWheelSurfServiceImpl.getAward(re.getUserId(), 5L, re.getChannel());
				result.setId(award.getId());
				result.setHaveAward(award.isHaveAward());
				result.setAward(award.getAward());
				result.setDate(award.getDate());
				result.setDesc(award.getDesc());
				result.setLastTime(award.getLastGuessNum());
				response.setResult(result);
			} catch (Exception e) {
				log.error("获取用户转盘结果异常 ", e);
				throw e;
			}
			return response;
		}
		
		// 获取用户转盘相关信息
		@RequestMapping(value = "/getUserRotary")
		@ResponseBody
		public Response<ActivitySheepUserRotaryResponse> getUserRotary(
				@RequestBody @ValidRequestBody Request<ActivitySheepUserRotaryRequest> request)
				throws Exception {
			Response<ActivitySheepUserRotaryResponse> response = new Response<ActivitySheepUserRotaryResponse>(
					request);
			ActivitySheepUserRotaryResponse result = new ActivitySheepUserRotaryResponse();
			try {
				ActivitySheepWheelSurf bl = activitySheepWheelSurfServiceImpl
						.getUserRotary(request.getBody().getParam().getUserId());
				User user =userCustomerDao.queryUserById(request.getBody().getParam().getUserId());
				List<ActivitySheepDetail> allList = activitySheepDetailServiceImpl
						.getAllUserRotaryDetailList();
				if (bl == null&&user.getVipLvl()==1) {//用户为vip 当用户在押大小表中没有记录的时候初始化押大小记录
					activitySheepWheelSurfServiceImpl.initUserRotary(request
							.getBody().getParam().getUserId(), user.getUserProfile().getAccount(),request
							.getBody().getParam().getChannel());
					result.setLastDiceTime(0L);
					result.setMyList(null);
					result.setAllList(allList);
					response.setResult(result);
					return response;
				}else if(user.getVipLvl()==0){
					result.setLastDiceTime(0L);
					result.setMyList(null);
					result.setAllList(allList);
					response.setResult(result);
					return response;
				}
				List<ActivitySheepDetail> list = activitySheepDetailServiceImpl
						.getUserRotaryDetailList(request.getBody().getParam()
								.getUserId());
				result.setLastDiceTime(bl.getLastNum());
				result.setMyList(list);
				result.setAllList(allList);
				response.setResult(result);
			} catch (Exception e) {
				log.error("获取用户红包列表异常 ", e);
				throw e;
			}
			return response;
		}
		
		

		 
		/**
		 * 获取用户信息
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/getUserInfo")
		@ResponseBody
		public Response<UserInfoResponse> getUserInfo(
				@RequestBody @ValidRequestBody Request<UserInfoRequest> request)
				throws Exception {
			Response<UserInfoResponse> response = new Response<UserInfoResponse>(
					request);
			UserInfoResponse ui = new UserInfoResponse();
			try {

				User user =userCustomerDao.queryUserById(request.getBody().getParam().getUserId());
				if(null!=user){ 
					ui.setUserId(user.getId());
					ui.setUserName(user.getUserProfile().getAccount());
					ui.setVipLvl(user.getVipLvl());
					ui.setAwardRetStatus(user.getAwardRetStatus());
					ui.setSuperPairStatus(user.getSuperPairStatus());
					ui.setLhcStatus(user.getLhcStatus());
				}
				response.setResult(ui);
			} catch (Exception e) {
				log.error("获取用户信息异常 ", e);
				throw e;
			}
			return response;
		}

		
		// 获取秒秒彩風雲榜排名
		@RequestMapping(value = "/getMMCRanking")
		@ResponseBody
		public Response<MmcRankingResponse> getMMCRanking(
				@RequestBody @ValidRequestBody Request<MmcRankingRequest> request)
				throws Exception {
			Response<MmcRankingResponse> response = new Response<MmcRankingResponse>(
					request);
			
			try {
				MmcRankingResponse result =  mmcRankingServiceImpl.queryTop(request.getBody().getParam().getAccount());
				response.setResult(result);
			} catch (Exception e) {
				log.error("秒秒風雲榜查詢排名異常 ", e);
				throw e;
			}
			return response;
		}
		
		// 获取秒秒彩風雲榜歷史冠軍
		@RequestMapping(value = "/getMMCRankingHistory")
		@ResponseBody
		public Response<MmcRankingHistoryResponse> getMMCRankingHistory(
				@RequestBody @ValidRequestBody Request<MmcRankingRequest> request)
				throws Exception {
			Response<MmcRankingHistoryResponse> response = new Response<MmcRankingHistoryResponse>(
					request);
			try {
				MmcRankingHistoryResponse result =  mmcRankingServiceImpl.queryHistory();
				response.setResult(result);
				return response;
			} catch (Exception e) {
				log.error("秒秒風雲榜歷史冠軍異常 ", e);
				throw e;
			}
		}

}
