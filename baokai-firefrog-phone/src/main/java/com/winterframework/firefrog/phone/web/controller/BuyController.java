
package com.winterframework.firefrog.phone.web.controller;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.validate.business.IKeyGenerator;
import com.winterframework.firefrog.common.validate.business.IValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.common.validate.business.IValidatorFactory;
import com.winterframework.firefrog.game.dao.vo.EnumTypeConverts;
import com.winterframework.firefrog.game.dao.vo.GameAward;
import com.winterframework.firefrog.game.dao.vo.GameLhcOdds;
import com.winterframework.firefrog.game.dao.vo.GameNumberConfig;
import com.winterframework.firefrog.game.entity.CancelMode;
import com.winterframework.firefrog.game.entity.FileMode;
import com.winterframework.firefrog.game.entity.GameBetType;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameOrder;
import com.winterframework.firefrog.game.entity.GameOrder.OrderStatus;
import com.winterframework.firefrog.game.entity.GamePackage;
import com.winterframework.firefrog.game.entity.GamePackage.GamePackageType;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.entity.Lottery;
import com.winterframework.firefrog.game.entity.MoneyMode;
import com.winterframework.firefrog.game.exception.GameBetAmountErrorException;
import com.winterframework.firefrog.game.exception.GameBetContentPatternErrorException;
import com.winterframework.firefrog.game.exception.GameTotbetsErrorException;
import com.winterframework.firefrog.game.util.LhcRedisUtil;
import com.winterframework.firefrog.game.web.dto.BetDetailStruc;
import com.winterframework.firefrog.game.web.dto.BetLimitQueryByBetRequest;
import com.winterframework.firefrog.game.web.dto.BetLimitQueryResponse;
import com.winterframework.firefrog.game.web.dto.BetMethodMultipleStruc;
import com.winterframework.firefrog.game.web.dto.BetMethodValidListQueryRequest;
import com.winterframework.firefrog.game.web.dto.BetMethodValidListQueryResponse;
import com.winterframework.firefrog.game.web.dto.CancelOrderChargeRequest;
import com.winterframework.firefrog.game.web.dto.CancelOrderChargeResponse;
import com.winterframework.firefrog.game.web.dto.CancelOrderRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameLhcOddsResponse;
import com.winterframework.firefrog.game.web.dto.GameLhcZodiacResponse;
import com.winterframework.firefrog.game.web.dto.GameLimit;
import com.winterframework.firefrog.game.web.dto.GameNumbers;
import com.winterframework.firefrog.game.web.dto.GameOrderRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderResponeOverMutipleDTO;
import com.winterframework.firefrog.game.web.dto.GameOrderResponse;
import com.winterframework.firefrog.game.web.dto.GamePlanBetOriginDataStruc;
import com.winterframework.firefrog.game.web.dto.GamePlanRequest;
import com.winterframework.firefrog.game.web.dto.GamePlanResponse;
import com.winterframework.firefrog.game.web.dto.GameSlipResponseDTO;
import com.winterframework.firefrog.game.web.dto.GameUserAwardGroupQueryResponse;
import com.winterframework.firefrog.game.web.dto.LhcGameZodiac;
import com.winterframework.firefrog.game.web.dto.LotteryGameUserAwardGroupQueryRequest;
import com.winterframework.firefrog.game.web.dto.LotteryGameUserAwardGroupQueryResponse;
import com.winterframework.firefrog.game.web.dto.LotteryGameUserAwardListStruc;
import com.winterframework.firefrog.game.web.dto.PointsRequestDTO;
import com.winterframework.firefrog.game.web.dto.PreviewIssueStruc;
import com.winterframework.firefrog.game.web.dto.QuerySeriesConfigRequest;
import com.winterframework.firefrog.game.web.dto.QuerySeriesConfigResponse;
import com.winterframework.firefrog.game.web.dto.ReservationCancelledChangeStatusRequest;
import com.winterframework.firefrog.game.web.dto.ReservationCancelledRequest;
import com.winterframework.firefrog.game.web.dto.ReservationCancelledResponse;
import com.winterframework.firefrog.game.web.dto.SaveProxyBetGameAwardGroupRequest;
import com.winterframework.firefrog.game.web.dto.StopGamePlanRequest;
import com.winterframework.firefrog.game.web.dto.StopGamePlanResponse;
import com.winterframework.firefrog.game.web.dto.TraceGameIssueListQueryRequest;
import com.winterframework.firefrog.game.web.dto.TraceGameIssueListQueryResponse;
import com.winterframework.firefrog.game.web.util.GameAwardNameUtil;
import com.winterframework.firefrog.phone.web.cotroller.dto.AddBonusDataRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.AddBonusDataResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.BuyRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.CancelGameRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.CancelGameResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.CancelTaskRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.CancelTaskResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.GameOrderPhoneResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.GameOrderResponseDTO;
import com.winterframework.firefrog.phone.web.cotroller.dto.GamePlanPhoneResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.InitDataRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.InitDataResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.Issue;
import com.winterframework.firefrog.phone.web.cotroller.dto.LockPoint;
import com.winterframework.firefrog.phone.web.cotroller.dto.OrderChargeReqeust;
import com.winterframework.firefrog.phone.web.cotroller.dto.OrderChargeResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.Points;
import com.winterframework.firefrog.phone.web.cotroller.dto.Projects;
import com.winterframework.firefrog.phone.web.cotroller.dto.SimpleInitDataRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.SimpleInitDataResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserAwardStruc;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserStrucResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserToken;
import com.winterframework.firefrog.phone.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.phone.web.validate.business.LotteryPlayMethodKeyGenerator;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.RequestHeader;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResponseHeader;
import com.winterframework.modules.web.util.RequestContext;

@Controller("buyController")
@RequestMapping("/game")
public class BuyController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(BuyController.class);
	
	@PropertyConfig(value="url.game.simpleInitData")
	private String simpleInitDataUrl;
	@PropertyConfig(value = "url.game.queryBetLimitByBet")
	private String queryBetLimit;
	@PropertyConfig(value = "url.query.gameValidBetMethods")
	private String gameValidBetMethods;
	@PropertyConfig(value = "url.game.queryMaxGameIssue")
	private String queryMaxGameIssue;
	@PropertyConfig(value = "url.game.getCurrentGameIssue")
	private String currentGameIssue;
	@PropertyConfig(value = "url.game.queryTraceGameIssues")
	private String queryTraceGameIssues;
	@PropertyConfig(value="url.game.cancelGame")
	private String cancelGameUrl;
	@PropertyConfig(value="url.game.cancelTask")
	private String cancelTaskUrl;
	@PropertyConfig(value="url.game.buy")
	private String buyUrl;
	@PropertyConfig(value="url.game.gamePlan")
	private String gamePlanUrl;
	@PropertyConfig(value="url.game.cancalGameOrder")
	private String cancalGameOrderUrl;
	@PropertyConfig(value="url.game.handlingCharge")
	private String handlingCharge;
	@PropertyConfig(value="url.game.userAwardUrl")
	private String userAwardUrl;
	@PropertyConfig(value="url.front.queryUserAward")
	private String queryUserAwardUrl;
	@PropertyConfig(value="url.front.openAccount")
	private String openAccountUrl;
	@PropertyConfig(value="url.game.config")
	private String configUrl;
	@PropertyConfig(value="url.front.openLinkDetail")
	private String openLinkDetailUrl;
	@PropertyConfig(value="url.front.updateGameAwardUserGroup")
	private String updateGameAwardUserGroupUrl;
	@PropertyConfig(value="url.game.cancellReservationStatus")
	private String cancellReservationStatus;
	@PropertyConfig(value="url.game.queryGameOdds")
	private String queryGameOddsUrl;
	@PropertyConfig(value="url.game.queryGameZodiac")
	private String queryGameZodiacUrl;
	
	
	@PropertyConfig(value="url.front.teamUserBalance")
	private String teamUserBalanceUrl;
	
	@Resource(name = "gameBetNumberValidatorFactory")
	private IValidatorFactory<GameSlip> validatorFactory;
	@Resource(name="lhcRedisUtil")
	private LhcRedisUtil lhcRedisUtil;
	
	@ResponseBody
	@RequestMapping("/initData")
	public Response<InitDataResponse> initData(@RequestBody Request<InitDataRequest> request) throws Exception{
		
		Response<InitDataResponse> response = new Response<InitDataResponse>(request);
		
		String token = request.getHead().getSessionId();
		InitDataResponse result = new InitDataResponse();
		try {
			
			Long lotteryId = request.getBody().getParam().getLotteryId().longValue();
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			result.setUsername(ut.getUserName());
			
			result.setGamelimit(getGameLimits(lotteryId));//获取各玩法的倍数限制信息
			
			Response<Long> maxtime = httpClient.invokeHttp(gameUrl + queryMaxGameIssue, lotteryId, Long.class);
 			//可追号的最大期数
			result.setTracemaxtimes(maxtime.getBody().getResult().intValue());

			//获取可追号期数列表
			result.setGamenumbers(getGameNumbers(result.getTracemaxtimes(), lotteryId));

			//是否当前奖期销售暂停 0正常 1 暂停
			result.setIsstop(0);

			//获取当前奖期信息
			GameIssueQueryResponse gameIssue = getCurrentGameIssue(lotteryId);

			//将开奖号码用逗号分隔
			result.setLastballs(gameIssue.getNumberRecord());

			result.setLastnumber(gameIssue.getLastWebIssueCode());//获取上期奖期
			result.setNowstoptime(DateUtils.format(DataConverterUtil.convertLong2Date(gameIssue.getSaleEndTime()),
					DateUtils.DATETIME_JSVIEW_FORMAT_PATTERN));//当前期投注结束时间
			result.setNowtime(DateUtils.format(new Date(), DateUtils.DATETIME_JSVIEW_FORMAT_PATTERN));//當前服務器時間
			result.setNumber(gameIssue.getWebIssueCode());//当前web期号
			result.setIssueCode(gameIssue.getIssueCode());//当前期号
			result.setResulttime(DateUtils.format(DataConverterUtil.convertLong2Date(gameIssue.getSaleStartTime()),
					DateUtils.DATETIME_JSVIEW_FORMAT_PATTERN));//当前期预计开始时间
			
			response.setResult(result);
			
		} catch (Exception e) {
			log.error("initData error:", e);
			response.getHead().setStatus(901000L);
		}
		
		return response;
	}
	

	/**
	 * 投注格式、 注数、金额验证
	 * 
	 * @param gameOrder
	 * @return
	 * @throws Exception
	 */
	protected Long validate(GameOrder gameOrder,Long lotteryId) throws Exception {

		long status = -1l;

		try {
			if(lotteryId.equals(99701L)){
				//若為六合彩 去game api拿validate 需要的設定檔
				fillLhcConfigs(gameOrder);
			}
			for (GameSlip slip : gameOrder.getSlipList()) {
				IKeyGenerator keyGen = new LotteryPlayMethodKeyGenerator(lotteryId, slip.getGameBetType()
						.getGameGroupCode(), slip.getGameBetType().getGameSetCode(), slip.getGameBetType()
						.getBetMethodCode(), "_$_");
				
				IValidateExecutorContext context = new BetValidateContext(gameOrder, slip);
				IValidateExecutor<GameSlip> validatorExecutor = validatorFactory.getValidateExecutor(LOTTERY_CODE_MAP.get(lotteryId),
						keyGen);
				validatorExecutor.execute(slip, context);
			}
		} catch (GameBetContentPatternErrorException e) {
			log.error("投注格式错误：", e);
			status = e.getStatus();
		} catch (GameBetAmountErrorException e) {
			log.error("投注金额错误：", e);
			status = e.getStatus();
		} catch (GameTotbetsErrorException e) {
			log.error("投注注数错误：", e);
			status = e.getStatus();
		} catch (Exception e) {
			log.error("下注操作失败：", e);
			throw e;
		}

		return status;

	}
	/**
	 * 補上六合彩validator所需要的設定檔
	 * @param gameOrder
	 * @throws Exception
	 */
	private void fillLhcConfigs(GameOrder gameOrder) throws Exception {
		Long awardGroupId = gameOrder.getAwardGroupId();
		if(awardGroupId==null){
			LotteryGameUserAwardGroupQueryRequest request = new LotteryGameUserAwardGroupQueryRequest();

			request.setLotteryId(gameOrder.getLottery().getLotteryId());
			request.setUserId(gameOrder.getGamePackage().getUser().getId());

			List<LotteryGameUserAwardListStruc> userAwardList = new ArrayList<LotteryGameUserAwardListStruc>();
			try {
				Response<LotteryGameUserAwardGroupQueryResponse> response = httpClient.invokeHttp(gameUrl+ userAwardUrl, request,new Pager(), 0L,"", new TypeReference<Response<LotteryGameUserAwardGroupQueryResponse>>() {
				});				
				
				if (null != response.getBody().getResult()) {
					userAwardList = response.getBody().getResult().getUserAwardListStruc();
					if(userAwardList!=null){
						awardGroupId=userAwardList.get(0).getGid();
					}
				}
				log.info("getUserArwardGroupsByLotteryIdAndUserId end");
			} catch (Exception e) {
				log.error("getUserArwardGroupsByLotteryIdAndUserId is error.", e);
				throw e;
			}
		}
		List<GameAward> gameAwards = lhcRedisUtil.findGameAwardByAwardGroupId(awardGroupId);
		List<GameNumberConfig> gameNumberConfigs = lhcRedisUtil.findThisYearNumberConfig(new Date());
		for (GameSlip slip : gameOrder.getSlipList()) {
			slip.setGameAwards(gameAwards);
			slip.setGameNumberConfigs(gameNumberConfigs);
		}
	}
	
	/**
	 * 获取可追号的奖期列表
	 * 
	 * @param traceMaxTimes
	 * @return
	 * @throws Exception
	 */
	protected List<GameNumbers> getGameNumbers(int traceMaxTimes, Long lotteryId) throws Exception {

		TraceGameIssueListQueryRequest traceGameIssueListQueryRequest = new TraceGameIssueListQueryRequest();
		traceGameIssueListQueryRequest.setLotteryId(lotteryId);
		traceGameIssueListQueryRequest.setMaxCountIssue(traceMaxTimes);

		Response<TraceGameIssueListQueryResponse> traceGameIssueListQueryResponse = httpClient.invokeHttp(gameUrl + queryTraceGameIssues, traceGameIssueListQueryRequest, TraceGameIssueListQueryResponse.class);

		List<GameNumbers> gamenumbers = new ArrayList<GameNumbers>();
		for (PreviewIssueStruc is : traceGameIssueListQueryResponse.getBody().getResult().getIssueStrucs()) {
			GameNumbers gn = new GameNumbers();
			gn.setNumber(is.getWebIssueCode());
			gn.setIssueCode(is.getIssueCode());
			gn.setTime(DateUtils.format(DataConverterUtil.convertLong2Date(is.getSaleEndTime()),
					DateUtils.DATETIME_JSVIEW_FORMAT_PATTERN));
			gamenumbers.add(gn);
		}

		return gamenumbers;
	}
	
	protected GameIssueQueryResponse getCurrentGameIssue(Long lotteryId) throws Exception {

		GameIssueQueryRequest gameIssueQueryRequest = new GameIssueQueryRequest();
		gameIssueQueryRequest.setLotteryId(lotteryId);

		Response<GameIssueQueryResponse> gameIssueQueryResponse =httpClient.invokeHttp(gameUrl + currentGameIssue, gameIssueQueryRequest, GameIssueQueryResponse.class);

		return gameIssueQueryResponse.getBody().getResult();

	}
	
	
	/**
	 * 查询投注限制
	 * 
	 * @return
	 * @throws Exception
	 */
	protected List<Map<String, GameLimit>> getGameLimits(Long lotteryId) throws Exception {

		BetNameConvertor bc = new BetNameConvertor();
		BetLimitQueryByBetRequest betLimitQueryRequest = new BetLimitQueryByBetRequest();
		betLimitQueryRequest.setLotteryid(lotteryId);

		
		Response<BetLimitQueryResponse> betLimitQueryResponse = httpClient.invokeHttp(gameUrl + queryBetLimit, betLimitQueryRequest,BetLimitQueryResponse.class);

		BetMethodValidListQueryRequest betMethodValidListQueryRequest = new BetMethodValidListQueryRequest();
		betMethodValidListQueryRequest.setLotteryid(lotteryId);
		Response<BetMethodValidListQueryResponse> betMethodValidListQueryResponse = httpClient.invokeHttp(gameUrl + gameValidBetMethods, betMethodValidListQueryRequest, BetMethodValidListQueryResponse.class);

		List<Map<String, GameLimit>> gameLimits = new ArrayList<Map<String, GameLimit>>();
		Map<String, GameLimit> map = new HashMap<String, GameLimit>();
		
		//限制逻辑修改  由于前台js需要限制数据，改成以bet_type_status表为主 没有的显示为无限制

		for (String gameTypeCode : betMethodValidListQueryResponse.getBody().getResult().getValidMethods()) {

			String[] codes = StringUtils.split(gameTypeCode, ",");

			//解析得玩法群、玩法组、玩法code
			Integer gameGroupCode = Integer.valueOf(codes[0]);
			Integer gameSetCode = Integer.valueOf(codes[1]);
			Integer betMethodCode = Integer.valueOf(codes[2]);

			GameLimit gl = new GameLimit();
			if (!betLimitQueryResponse.getBody().getResult().getBetLimitList().isEmpty()) {
				gl.setMaxmultiple(-1L);
				for (BetMethodMultipleStruc bs : betLimitQueryResponse.getBody().getResult().getBetLimitList()) {
					if (gameGroupCode.intValue() == bs.getGameGroupCode()
							&& gameSetCode.intValue() == bs.getGameSetCode()
							&& betMethodCode.intValue() == bs.getBetMethodCode()) {
						gl.setMaxmultiple(bs.getMultiple().longValue());
					}
				}

			} else {
				gl.setMaxmultiple(-1L);//null值为无限制
			}
			gl.setUsergroupmoney(null);// 此数据已不在这里获取了，现在随意给个null值  此行不能注销，前台js需要
			map.put(bc.getBetMethodFullNameByValue(gameGroupCode, gameSetCode, betMethodCode), gl);

		}

		gameLimits.add(map);

		return gameLimits;
	}

	@ResponseBody
	@RequestMapping("/simpleInitData")
	public Response<SimpleInitDataResponse> simpleInitData(@RequestBody Request<SimpleInitDataRequest> request) throws Exception{
		
		Response<SimpleInitDataResponse> response = new Response<SimpleInitDataResponse>(request);
		String token = request.getHead().getSessionId();
		try {
			log.debug("---------------------simpleInitData start-------------------");
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			Long lotteryId = request.getBody().getParam().getLotteryId().longValue();
			log.debug("lotteryId : " + lotteryId);
			
			SimpleInitDataResponse result = new SimpleInitDataResponse();
			GameIssueQueryRequest gameIssueQueryRequest = new GameIssueQueryRequest();
			
			if(null == request.getBody().getParam().getLotteryId()){
				response.getHead().setStatus(201003L);//彩种不存在
				return response;
			}
			gameIssueQueryRequest.setLotteryId(lotteryId);
			
			//判断用户是否有奖金组。。如果没有预设一个。。
			LotteryGameUserAwardGroupQueryRequest userAwardRequest = new LotteryGameUserAwardGroupQueryRequest();
			userAwardRequest.setLotteryId(request.getBody().getParam().getLotteryId().longValue());
			userAwardRequest.setUserId(ut.getUserId());
			/*獲取玩家當前彩種的獎金組設定GAME_AWARD_USER_GROUP（紀錄玩家各彩種的獎金組設定）
			 *GAME_AWARD_GROUP是記錄獎金組CONFIG table
			 */
			Response<LotteryGameUserAwardGroupQueryResponse> userAwardResponse = httpClient.invokeHttp(gameUrl+ userAwardUrl, userAwardRequest,new Pager(), ut.getUserId(),ut.getUserName(), new TypeReference<Response<LotteryGameUserAwardGroupQueryResponse>>() {
			});
			
			/* 判断用户是否有奖金组 http://127.0.0.1:8084/game/queryLotteryGameUserAwardGroupByLotteryIdAndUserId */
			List<LotteryGameUserAwardListStruc> awardlist = userAwardResponse.getBody().getResult().getUserAwardListStruc();
			
			
			boolean bb = false;
			if(awardlist.isEmpty()){
				log.info("awardlist.isEmpty()");
				bb = true;
			}else{
				int count = 0;
				for(LotteryGameUserAwardListStruc struc : awardlist){
					
					if(struc.getBetType().intValue() == 0){
						log.info("struc.getBetType().intValue() count++ ");
						count++;
					}
				}
				if(count == awardlist.size()){
					log.info("count == awardlist.size() ");
					bb = true;
				}
			}
			List<UserAwardStruc> list = new ArrayList<UserAwardStruc>();
			log.info("awardlist.size() : " + awardlist.size());
			if(bb){//表示沒有設定過獎金組 須返回獎金組設定資料
				log.info("-----------------------doesn't setting bonusGroup!!----------------------");
				result.setBonusGroupStatus(0);
				
				log.info("-----------------------userAwardList size : " + awardlist.size());
				for(LotteryGameUserAwardListStruc struc:awardlist){
					UserAwardStruc data = new UserAwardStruc();
					data.setAwardGroupId(struc.getGid().toString());
					log.info("Gid : " + struc.getGid().toString());
					log.info("AwardGroupId : " + struc.getAwardGroupId().toString());
					data.setAwardName(struc.getAwardName());
					log.info("AwardName : " + struc.getAwardName());
					list.add(data);
				}
			}else{
				
				for(LotteryGameUserAwardListStruc struc:awardlist){
					UserAwardStruc data = new UserAwardStruc();
					if(struc.getBetType().intValue() == 1){
						data.setAwardGroupId(struc.getGid().toString());
						log.debug("Gid : " + struc.getGid().toString());
						log.debug("AwardGroupId : " + struc.getAwardGroupId().toString());
						data.setAwardName(struc.getAwardName());
						log.debug("AwardName : " + struc.getAwardName());
						list.add(data);
					}
				}
				//若已設定過獎金組 則不返回獎金組設定資料
				result.setBonusGroupStatus(1);
				
			}
			log.info("final list size : " + list.size());
			result.setAwardGroups(list);
			
			log.debug("bonusGroupStatus : " + result.getBonusGroupStatus());
					
			Response<GameIssueQueryResponse> gameResponse = httpClient.invokeHttp(gameUrl + simpleInitDataUrl, gameIssueQueryRequest, GameIssueQueryResponse.class);
			
			if(null != gameResponse.getBody() && null != gameResponse.getBody().getResult()){
			
				GameIssueQueryResponse gameIssue = gameResponse.getBody().getResult();
				
				Issue issue = new Issue();
				issue.setIssue(gameIssue.getWebIssueCode());
				issue.setSaleend(DateUtils.format(DataConverterUtil.convertLong2Date(gameIssue.getSaleEndTime()),
						DateUtils.DATETIME_FORMAT_PATTERN));
				issue.setSalestart(DateUtils.format(DataConverterUtil.convertLong2Date(gameIssue.getSaleStartTime()), DateUtils.DATETIME_FORMAT_PATTERN));
				issue.setIssueCode(gameIssue.getIssueCode());
				issue.setNextIssue(gameIssue.getPasuseStatus()+"");
				result.setLastWebIssueCode(gameIssue.getLastWebIssueCode());
				result.setLastOpenCode(gameIssue.getNumberRecord());
				result.setNowTime(DateUtils.format(new Date(), DateUtils.DATETIME_FORMAT_PATTERN));
				result.setIssue(issue);
			}
			//加入字段。。
			
			QuerySeriesConfigRequest configRequest = new QuerySeriesConfigRequest();
			configRequest.setLotteryid(request.getBody().getParam().getLotteryId().longValue());
			
			Response<QuerySeriesConfigResponse> configResponse = httpClient.invokeHttp(gameUrl + configUrl, configRequest, new Pager(), ut.getUserId(), ut.getUserName(), new TypeReference<Response<QuerySeriesConfigResponse>>() {
			});
						
			result.setBackOutRadio(new BigDecimal( configResponse.getBody().getResult().getBackoutRatio()).divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP).doubleValue());
			result.setBackOutStartFee(new BigDecimal(configResponse.getBody().getResult().getBackoutStartFee()).divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP).doubleValue());
			response.setResult(result);
		} catch (Exception e) {
			log.error("simpleInitData error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/addBonusData")
	public Response<AddBonusDataResponse> addBonusData(@RequestBody Request<AddBonusDataRequest> request) throws Exception{
		Response<AddBonusDataResponse> response = new Response<AddBonusDataResponse>(request);
		log.debug("-----------------addBonusData start--------------------");
		String token = request.getHead().getSessionId();
		try {
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			Long awardGroupId = request.getBody().getParam().getAwardGroupId().longValue();
			Long lotteryId = request.getBody().getParam().getLotteryId().longValue();
			log.info("awardGroupId : " + awardGroupId);
			log.info("lotteryId : " + lotteryId);
			log.info("userId : " + ut.getUserId());
			SaveProxyBetGameAwardGroupRequest retJson = new SaveProxyBetGameAwardGroupRequest();
			retJson.setAwardGroupId(awardGroupId);
			retJson.setLotteryid(lotteryId);
			retJson.setUserid(ut.getUserId());
			Response obj = httpClient.invokeHttp(gameUrl+ updateGameAwardUserGroupUrl, retJson, new TypeReference<Response<GameUserAwardGroupQueryResponse>>() {
			});
			log.debug("obj status : " + obj.getHead().getStatus());
			AddBonusDataResponse result = new AddBonusDataResponse();
			if(null != obj.getBody()){
				if (obj.getHead().getStatus() == 2) {
					log.error("aready config");
					response.getHead().setStatus(109991L);	
					return response;
				} else if (obj.getHead().getStatus() == 3) {
					log.error("modify ret error");
					response.getHead().setStatus(109991L);	
					return response;
				}				
				result.setStatus("success");
			}
							
			response.setResult(result);
		} catch( Exception e){
			log.error("addBonusData error:",e);
			response.getHead().setStatus(901000L);			
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/cancelGame") 
	public Response<CancelGameResponse> cancelGame(@RequestBody Request<CancelGameRequest> request) throws Exception{
		
		Response<CancelGameResponse> response = new Response<CancelGameResponse>(request);
		
		String token = request.getHead().getSessionId();
		try {
			
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			CancelOrderRequest requestData = new CancelOrderRequest();
			requestData.setCancelTime(new Date().getTime());
			requestData.setOrderId(Long.parseLong(request.getBody().getParam().getId()));
			requestData.setUserId(ut.getUserId());
			
			Response<StopGamePlanResponse> gameResponse = httpClient.invokeHttp(gameUrl+cancalGameOrderUrl, requestData,new Pager(), ut.getUserId(), ut.getUserName(), new TypeReference<Response<StopGamePlanResponse>>() {
			});
			
			CancelGameResponse result = new CancelGameResponse();
			if(gameResponse.getHead().getStatus()==0){
				result.setStatus("success");
			}else{
				result.setStatus(gameResponse.getHead().getStatus()+"");
			}
			
			response.setResult(result);
		} catch (Exception e) {
			log.error("cancalGame Error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/cancelTask")
	public Response<CancelTaskResponse> cancelTask(@RequestBody Request<CancelTaskRequest> request) throws Exception{
		
		Response<CancelTaskResponse> response = new Response<CancelTaskResponse>(request);
		String token = request.getHead().getSessionId();
		try {
			
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			CancelTaskRequest cancelRequest = request.getBody().getParam();
			
			Response<ReservationCancelledResponse> gameResponse = null;
			if(cancelRequest.getIssueCode() != null){
				//先將要撤銷的追號單改狀態
				ReservationCancelledChangeStatusRequest requestStatusData = new ReservationCancelledChangeStatusRequest();
				requestStatusData.setUserType(1);
				requestStatusData.setLotteryId(cancelRequest.getLotteryId());
				requestStatusData.setPlanId(Long.parseLong(cancelRequest.getPlanId()));
				requestStatusData.setIssueCode(cancelRequest.getIssueCode().split(","));
				gameResponse = httpClient.invokeHttp(gameUrl+cancellReservationStatus, requestStatusData,new Pager(), ut.getUserId(), ut.getUserName(), new TypeReference<Response<ReservationCancelledResponse>>() {
				});
				if(gameResponse.getHead().getStatus()!=0){
					log.error("更改追號狀態錯誤 planid:"+cancelRequest.getPlanId());
					response.getHead().setStatus(901000L);
					return response;
				}
				// 
				//预约撤销的时候，需要一期一期撤销
				ReservationCancelledRequest requestData = new ReservationCancelledRequest();
				requestData.setUserType(1);
				requestData.setLotteryId(cancelRequest.getLotteryId());
				requestData.setPlanId(Long.parseLong(cancelRequest.getPlanId()));
				String[] issuecodes = cancelRequest.getIssueCode().split(",");
				for(String issueCode : issuecodes){
					requestData.setIssueCode(Long.parseLong(issueCode));
					gameResponse = httpClient.invokeHttp(gameUrl+cancelTaskUrl, requestData,new Pager(), ut.getUserId(), ut.getUserName(), new TypeReference<Response<ReservationCancelledResponse>>() {
					});
				}
			
			}else{
				
				StopGamePlanRequest requestData = new StopGamePlanRequest();
				requestData.setCancelTime(new Date().getTime());
				requestData.setPlanId(Long.parseLong(request.getBody().getParam().getPlanId()));
				requestData.setUserType(2);
				gameResponse = httpClient.invokeHttp(gameUrl+cancelGameUrl, requestData, new Pager(),ut.getUserId(), ut.getUserName(), new TypeReference<Response<StopGamePlanResponse>>() {
				});
			}
			
			CancelTaskResponse result = new CancelTaskResponse();
			if(gameResponse.getHead().getStatus()==0){
				result.setStatus("success");
			}else{
				result.setStatus(gameResponse.getHead().getStatus()+"");
			}
			response.setResult(result);
		}catch(Exception e){
			log.error("cancelTask Error", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	/**
	 * 手续费计算。
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/charge")
	public Response<OrderChargeResponse> getCancelOrderCharge(@RequestBody Request<OrderChargeReqeust> request) throws Exception{
		
		Response<OrderChargeResponse> response = new Response<OrderChargeResponse>(request);
		try {
			CancelOrderChargeRequest requestData = new CancelOrderChargeRequest();
			requestData.setLotteryId(request.getBody().getParam().getLotteryId());
			requestData.setTotalBetAmount(new BigDecimal(request.getBody().getParam().getAmount()).doubleValue());

			Response<CancelOrderChargeResponse> config = httpClient.invokeHttp(
					gamePlanUrl + handlingCharge, requestData,
					CancelOrderChargeResponse.class);

			OrderChargeResponse result = new OrderChargeResponse();
			result.setAmount(new BigDecimal(config.getBody().getResult().getHandlingCharge()).divide(new BigDecimal(10000), 4, RoundingMode.HALF_UP).doubleValue());
			response.setResult(result);
		} catch (Exception e) {
			log.error("getCancelOrderCharge Error", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	/**
	 * 投注
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/buy")
	public Response<GameOrderPhoneResponse> buy(@RequestBody Request<BuyRequest> request) throws Exception{
		
		Response<GameOrderPhoneResponse> response = new Response<GameOrderPhoneResponse>(request);
		String token = request.getHead().getSessionId();
		try {
			log.debug("----------------------------------buy--------------------");
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			//add 冻结判断
			Response<UserStrucResponse> firforgResponse = httpClient.invokeHttp(firefrogUrl+teamUserBalanceUrl, null,new Pager(),ut.getUserId(), null, new TypeReference<Response<UserStrucResponse>>() {
			});
		
			UserStrucResponse user = firforgResponse.getBody().getResult();
			if(user.getFreezeMethod() !=null && user.getFreezeMethod()>0){
				
				/*
				 * 1.完全冻结，不能登陆
				 * 2.可登陆，不可投注，不可充提
				 * 3.可登陆，不可投注，可充提
				 * 4.不可转帐，不可提现 
				 */
				if(user.getFreezeMethod().intValue() < 4){
					response.getHead().setStatus(109992L);//109992 此功能已经冻结
					return response;
				}
			}
			long status = 0l;
			BuyRequest buy = request.getBody().getParam();
			if(buy.getLotteryId().longValue()==99701L){
				Response<GameLhcOddsResponse> gameOddsResponse= httpClient.invokeHttp(gameUrl + queryGameOddsUrl, "",new TypeReference<Response<GameLhcOddsResponse>>(){});
				if(gameOddsResponse==null || gameOddsResponse.getBody().getResult()==null || gameOddsResponse.getBody().getResult().getGameLhcOdds()==null){
					response.getHead().setStatus(109999L); 	//六合彩初始数据错误
					return response;
				}
				Response<GameLhcZodiacResponse> gameZodiacResponse= httpClient.invokeHttp(gameUrl + queryGameZodiacUrl, "",new TypeReference<Response<GameLhcZodiacResponse>>(){});
				if(gameZodiacResponse==null || gameZodiacResponse.getBody().getResult()==null || gameZodiacResponse.getBody().getResult().getGameZodiac()==null){
					response.getHead().setStatus(109999L); 	//六合彩初始数据错误
					return response;
				}
				List<Projects> list = buy.getList();
				for(Projects project : list){
					if(project.getMethodid().equals("54_10_81")){
						project.setOdds(40.0); //赔率40
					}else if(project.getMethodid().equals("54_18_82")){
						for(LhcGameZodiac zodiac:gameZodiacResponse.getBody().getResult().getGameZodiac()){
							if(zodiac.getZodiacNameCn().equals(project.getCodes())){
								project.setOdds(getLhcOdds(gameOddsResponse.getBody().getResult().getGameLhcOdds(),project.getMethodid(),zodiac.getSpecialFlag().equals("Y")));
							}
						}
					}else if(project.getMethodid().equals("54_37_83")){
						project.setOdds(getLhcOdds(gameOddsResponse.getBody().getResult().getGameLhcOdds(),project.getMethodid(),true));
					}else if(project.getMethodid().equals("54_19_84")){
						project.setOdds(getLhcOdds(gameOddsResponse.getBody().getResult().getGameLhcOdds(),project.getMethodid(),project.getCodes().equals("红")));
					}else if(project.getMethodid().equals("55_38_86")){
						for(LhcGameZodiac zodiac:gameZodiacResponse.getBody().getResult().getGameZodiac()){
							if(zodiac.getZodiacNameCn().equals(project.getCodes())){
								project.setOdds(getLhcOdds(gameOddsResponse.getBody().getResult().getGameLhcOdds(),project.getMethodid(),zodiac.getSpecialFlag().equals("Y")));
							}
						}
					}else if(project.getMethodid().equals("55_40_92")){
						project.setOdds(0.0);
					}else if(project.getMethodid().equals("55_40_93")){
						project.setOdds(0.0);
					}
				}
			}
			//新增檢查 擋掉雙色球角模式
			List<Projects> list = buy.getList();
			long lot_id = buy.getLotteryId();
			if(lot_id == 99401){
				for(Projects project : list){
					log.debug("-------------------------project.getMode() : " + project.getMode());
					if(project.getMode() == 2){
						log.debug("------------------------ return 109887L");
						response.getHead().setStatus(109887L); 
						return response;
					}
				}
			}
			
			if(checkRequest(request, false) && lot_id!=99601L && lot_id!=99602L && lot_id!=99701L){	//骰宝不校验
				response.getHead().setStatus(202001L); 
				return response;
			}
			
			GameOrder gameOrder = convertGameOrderRequest2GameOrder(request, RequestContext.getCurrUser().getId(), ut.getUserId());
			status = validate(gameOrder,buy.getLotteryId());
			if (status > -1l) {
				RequestHeader requestH = new RequestHeader();
				ResponseHeader rh = ResponseHeader.createReponseHeader(requestH);
				rh.setStatus(status);
				response.setHead(rh);
				return response;
			}
			
			GameOrderRequest requestData = new GameOrderRequest();
			
			requestData.setIssueCode(buy.getIssue());
			requestData.setIsFirstSubmit(buy.getIsFirstSubmit());
			requestData.setLotteryid(buy.getLotteryId());
			//2015.12.17 modify by Ami
			//修正 new BigDecimal()裡面之Float需轉成字串,否則計算會出現誤差
			requestData.setPackageAmount(new BigDecimal(buy.getMoney().toString()).multiply(new BigDecimal(10000,MathContext.DECIMAL32)).setScale(2, RoundingMode.HALF_UP).longValue());
			requestData.setSaleTime(buy.getSaleTime());
			requestData.setChannelId(buy.getChannelId());
			requestData.setChannelVersion(buy.getChannelVersion());
			requestData.setUserIp(buy.getUserIp());
			
			List<BetDetailStruc> betList = new ArrayList<BetDetailStruc>();
			for(Projects project : list){
				
				BetDetailStruc struc = new BetDetailStruc();
				struc.setBetdetail(project.getCodes());
				String[] codes = project.getMethodid().split("_"); 
				
				struc.setGameGroupCode(Integer.parseInt(codes[0]));
				struc.setGameSetCode(Integer.parseInt(codes[1]));
				struc.setBetMethodCode(Integer.parseInt(codes[2]));
				if(null != project.getFileMode()){
					struc.setFileMode(project.getFileMode());
				}
				struc.setFileMode(getFileMode(project.getMethodid()).getValue());
				struc.setIssueCode(buy.getIssue());
				if (project.getMode() == 2) {
					struc.setItemAmount(2000); // 0.2 元 x 10000
				}else if (project.getMode() == 3) {
					struc.setItemAmount(200); // 0.02 元 x 10000
				} else {
					struc.setItemAmount(20000); // 2 元
				}
				if(requestData.getLotteryid().longValue()==99601L||requestData.getLotteryid().longValue()==99602L){
					struc.setMoneyMode(MoneyMode.JIAO.getValue());
				}else{
					struc.setMoneyMode(project.getMode());
				}
				struc.setAwardMode(project.getAwardMode());
				struc.setMultiple(project.getTimes());
				struc.setTotbets(project.getNums());
				//修正 new BigDecimal()裡面之Float需轉成字串,否則計算會出現誤差
				struc.setTotamount(new BigDecimal(project.getMoney().toString()).multiply(new BigDecimal(10000), MathContext.DECIMAL32).setScale(2, RoundingMode.HALF_UP).longValue());
				struc.setOdds(project.getOdds());
				//表示第二次封锁变价提交的信息。重新set回封锁变价信息。
				if(buy.getIsFirstSubmit() != null && buy.getIsFirstSubmit()==1){
					
					List<com.winterframework.firefrog.phone.web.cotroller.dto.GameSlipResponseDTO> slipResonseDTOList = buy.getSlipResonseDTOList();
					if(null != slipResonseDTOList && slipResonseDTOList.size()>0){
						for(com.winterframework.firefrog.phone.web.cotroller.dto.GameSlipResponseDTO dto : slipResonseDTOList){
							
							List<PointsRequestDTO> pointsList = new ArrayList<PointsRequestDTO>();
							if(dto.getGameBetType().getBetTypeCode().equals(project.getMethodid())){
								
								LockPoint point = dto.getLockPoints();
								for(Points ps : point.getPoints()){
									PointsRequestDTO pdt = new PointsRequestDTO();
									pdt.setCurrentPhase(ps.getCurrPhase());
									pdt.setMult(ps.getMult());
									pdt.setPoint(ps.getPoint());
									pdt.setRetValue(new BigDecimal(ps.getRetValue()).multiply(new BigDecimal(10000), MathContext.DECIMAL32).setScale(2, RoundingMode.HALF_UP).longValue());
									pdt.setSingleBet(ps.getSingleBet());
									pointsList.add(pdt);
								}
							}
							struc.setPointsList(pointsList);
						}
					}
				}
				betList.add(struc);
			}
					
			requestData.setBetDetailStruc(betList);
			
			Response<GameOrderResponse> gameResponse= httpClient.invokeHttp(gameUrl + buyUrl, requestData, new Pager(0,10000), ut.getUserId(), ut.getUserName(),new TypeReference<Response<GameOrderResponse>>() {
			});
		
			GameOrderPhoneResponse result = new GameOrderPhoneResponse();
			if(null != gameResponse.getBody() && null != gameResponse.getBody().getResult()){
			
				GameOrderResponse gameOrderResponse = gameResponse.getBody().getResult();
				
				if(null != gameOrderResponse.getOverMutipleDTO()){
					
					List<GameOrderResponeOverMutipleDTO> overMutipleDTO = new ArrayList<GameOrderResponeOverMutipleDTO>();
					for(GameOrderResponeOverMutipleDTO overMutipledto : gameOrderResponse.getOverMutipleDTO()){
						
						String[] betCodes = overMutipledto.getBetTypeCode().split("_");
						String name ="";
						for(int i=0; i<betCodes.length ;i++){
							name += GameAwardNameUtil.getTitle(buy.getLotteryId(), Integer.parseInt(betCodes[0]), Integer.parseInt(betCodes[2]), Integer.parseInt(betCodes[2]), i) +"_"; 
						}
						overMutipledto.setBetMethod(name.substring(0, name.length()-1));
						overMutipleDTO.add(overMutipledto);
					}
					result.setOverMutipleDTO(overMutipleDTO);
				}
				result.setOrderId(gameOrderResponse.getOrderId());
				
				if(null != gameOrderResponse.getGameOrderDTO()){
					GameOrderResponseDTO gameOrderDTO = new GameOrderResponseDTO();
					
					gameOrderDTO.setGameIssueCode(gameOrderResponse.getGameOrderDTO().getGameIssueCode());
					gameOrderDTO.setGamePlanParm(gameOrderResponse.getGameOrderDTO().getGamePlanParm());
					gameOrderDTO.setIsTrace(gameOrderResponse.getGameOrderDTO().getIsTrace());
					gameOrderDTO.setLotteryName(gameOrderResponse.getGameOrderDTO().getLotteryName());
					gameOrderDTO.setStopMode(gameOrderResponse.getGameOrderDTO().getStopMode());
					gameOrderDTO.setStopParms(gameOrderResponse.getGameOrderDTO().getStopParms());
					gameOrderDTO.setTotalAmount(new BigDecimal(gameOrderResponse.getGameOrderDTO().getTotalAmount()==null ? 0 : gameOrderResponse.getGameOrderDTO().getTotalAmount()).divide(new BigDecimal(10000),2,RoundingMode.HALF_UP).doubleValue());
					gameOrderDTO.setWebIssueCode(gameOrderResponse.getGameOrderDTO().getWebIssueCode());
					List<com.winterframework.firefrog.phone.web.cotroller.dto.GameSlipResponseDTO> pdlist = new ArrayList<com.winterframework.firefrog.phone.web.cotroller.dto.GameSlipResponseDTO>();
					if (null != gameOrderResponse.getGameOrderDTO().getSlipResonseDTOList()) {
						
						for (GameSlipResponseDTO dto : gameOrderResponse
								.getGameOrderDTO().getSlipResonseDTOList()) {
							com.winterframework.firefrog.phone.web.cotroller.dto.GameSlipResponseDTO pd = new com.winterframework.firefrog.phone.web.cotroller.dto.GameSlipResponseDTO();

							pd.setBetDetail(dto.getBetDetail());

							String[] betCodes = dto.getGameBetType()
									.getBetTypeCode().split("_");
							String name = "";
							for (int i = 0; i < betCodes.length; i++) {
								name += GameAwardNameUtil.getTitle(
										buy.getLotteryId(),
										Integer.parseInt(betCodes[0]),
										Integer.parseInt(betCodes[1]),
										Integer.parseInt(betCodes[2]), i)
										+ "_";
							}
							pd.setBetMethod(name.substring(0, name.length() - 1));
							pd.setCurr(dto.getCurr());
							pd.setGameBetType(dto.getGameBetType());
							pd.setIssueCode(dto.getIssueCode());
							boolean isLock = false;
							if (null != dto.getLockPoints()) {
								
								LockPoint lockPoints = new LockPoint();
								com.winterframework.firefrog.game.entity.LockPoint point = dto
										.getLockPoints();

								isLock = point.getIsLocks();
								lockPoints.setBeishu(point.getBeishu());
								lockPoints.setBetDetail(point.getBetDetail());
								lockPoints.setBetTotal(point.getBetTotal());
								lockPoints.setLocks(point.getLocks());
								lockPoints
										.setRealBeishu(point.getIsLocks() == true ? 0L
												: point.getRealBeishu());
								List<Points> points = new ArrayList<Points>();
								for (com.winterframework.firefrog.game.entity.Points p : point
										.getPoints()) {
									Points ps = new Points();
									ps.setCurrPhase(p.getCurrPhase());
									ps.setMult(p.getMult());
									ps.setPoint(p.getPoint());
									ps.setRetValue(new BigDecimal(p
											.getRetValue() == null ? 0 : p
											.getRetValue()).divide(
											new BigDecimal(10000), 2,
											RoundingMode.HALF_UP).doubleValue());
									ps.setSingle(p.getSingle());
									ps.setSingleBet(p.getSingleBet());
									points.add(ps);
								}
								lockPoints.setPoints(points);
								pd.setLockPoints(lockPoints);
							}
							pd.setMoneyMode(dto.getMoneyMode());
							pd.setAwardMode(dto.getAwardMode());
							pd.setMultiple(isLock == true ? 0 : dto
									.getMultiple());
							pd.setTotalAmount(isLock == true ? 0d
									: new BigDecimal(
											dto.getTotalAmount() == null ? 0
													: dto.getTotalAmount())
											.divide(new BigDecimal(10000), 2,
													RoundingMode.HALF_UP)
											.doubleValue());
							pd.setTotalBet(dto.getTotalBet());
							
							pd.setFileMode(dto.getFileMode());
							pdlist.add(pd);
						}
						gameOrderDTO.setSlipResonseDTOList(pdlist);
						result.setGameOrderDTO(gameOrderDTO);
					}
				}
				
				response.setResult(result);
			}else{
				response.setResult(null);
			}
			
			response.getHead().setStatus(gameResponse.getHead().getStatus());
		} catch (Exception e) {
			log.error("buy error", e);
			response.getHead().setStatus(901000L);
		}
		
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/plan")
	public Response<GamePlanPhoneResponse> gamePlan(@RequestBody Request<BuyRequest> request) throws Exception{
		log.info("----------------------------------plan--------------------");
		Response<GamePlanPhoneResponse> response = new Response<GamePlanPhoneResponse>(request);
		
		String token = request.getHead().getSessionId();
		try {
			
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			//add 冻结判断 
			Response<UserStrucResponse> firforgResponse = httpClient.invokeHttp(firefrogUrl+teamUserBalanceUrl, null,new Pager(),ut.getUserId(), null, new TypeReference<Response<UserStrucResponse>>() {
			});
		
			UserStrucResponse user = firforgResponse.getBody().getResult();
			if(user.getFreezeMethod() !=null && user.getFreezeMethod()>0){
				
				/*
				 * 1.完全冻结，不能登陆
				 * 2.可登陆，不可投注，不可充提
				 * 3.可登陆，不可投注，可充提
				 * 4.不可转帐，不可提现
				 */
				if(user.getFreezeMethod().intValue() < 4){
					response.getHead().setStatus(109992L);//109992 此功能已经冻结
					return response;
				}
			}
			
			long status = 0l;
			BuyRequest buy = request.getBody().getParam();
			
			if(checkRequest(request,true) && buy.getLotteryId()!=99601L && buy.getLotteryId()!=99602L && buy.getLotteryId()!=99701L){
				response.getHead().setStatus(202001L); 
				return response;
			}
			
			if(checkPlanRequest(request) && buy.getLotteryId()!=99601L && buy.getLotteryId()!=99602L){
				response.getHead().setStatus(202004L); 
				return response;
			}
			
			GameOrder gameOrder = convertGameOrderRequest2GameOrder(request, RequestContext.getCurrUser().getId(),ut.getUserId());
			status = validate(gameOrder,buy.getLotteryId());
			if (status > -1l) {
				RequestHeader requestH = new RequestHeader();
				ResponseHeader rh = ResponseHeader.createReponseHeader(requestH);
				rh.setStatus(status);
				response.setHead(rh);
				return response;
			}
			
			GamePlanRequest requestData = new GamePlanRequest();
			requestData.setLotteryid(buy.getLotteryId());
			requestData.setPlanAmount(new BigDecimal(buy.getMoney()).multiply(new BigDecimal(10000), MathContext.DECIMAL32).setScale(2, RoundingMode.HALF_UP).longValue());
			requestData.setIsFirstSubmit(buy.getIsFirstSubmit());
			
			String[] traceIssues = buy.getTraceIssues().split(",");
			String[] traceTimes = buy.getTraceTimes().split(",");
			
			requestData.setSaleTime(buy.getSaleTime());
			requestData.setStartIssueCode(buy.getIssue());
			requestData.setStopMode(buy.getTraceStop());
			requestData.setStopParms(buy.getTraceIstrace().longValue());
			requestData.setTotalIssue(new Long(traceIssues.length));
			requestData.setUserip(buy.getUserIp());
			requestData.setChannelId(buy.getChannelId());
			requestData.setChannelVersion(buy.getChannelVersion());
			requestData.setCurrentIssueCode(buy.getIssue());
			
			List<BetDetailStruc> betDetailsStruc = new ArrayList<BetDetailStruc>();
			List<Projects> list = buy.getList();
			for(Projects project : list){
				
				for(int i=0;i<traceIssues.length; i++){
					BetDetailStruc struc = new BetDetailStruc();
					
					struc.setBetdetail(project.getCodes());
					String[] codes = project.getMethodid().split("_"); 
					
					struc.setGameGroupCode(Integer.parseInt(codes[0]));
					struc.setGameSetCode(Integer.parseInt(codes[1]));
					struc.setBetMethodCode(Integer.parseInt(codes[2]));
					
					if(null != project.getFileMode()){
						struc.setFileMode(project.getFileMode());
					}
					struc.setFileMode(getFileMode(project.getMethodid()).getValue());
					
					struc.setIssueCode(Long.parseLong(traceIssues[i]));
					if (project.getMode() == 2) {
						struc.setItemAmount(2000); // 0.2 元 x 10000
					}else if (project.getMode() == 3) {
						struc.setItemAmount(200); // 0.02 元 x 10000
					} else {
						struc.setItemAmount(20000); // 2 元
					}
					if(requestData.getLotteryid().longValue()==99601L ||requestData.getLotteryid().longValue()==99602L){
						struc.setMoneyMode(MoneyMode.JIAO.getValue());
					}else{
						struc.setMoneyMode(project.getMode());
					}
					struc.setAwardMode(project.getAwardMode());
					struc.setMultiple(Integer.parseInt(traceTimes[i]));
					struc.setTotbets(project.getNums());
					struc.setTotamount(new BigDecimal(project.getMoney().toString()).setScale(4,RoundingMode.DOWN).multiply(new BigDecimal(Integer.parseInt(traceTimes[i]))).multiply(new BigDecimal(10000), MathContext.DECIMAL32).setScale(2, RoundingMode.HALF_UP).longValue());
					
					//表示第二次封锁变价提交的信息。重新set回封锁变价信息。
					if(buy.getIsFirstSubmit() != null && buy.getIsFirstSubmit()==1){
						
						List<com.winterframework.firefrog.phone.web.cotroller.dto.GameSlipResponseDTO> slipResonseDTOList = buy.getSlipResonseDTOList();
						if(null != slipResonseDTOList && slipResonseDTOList.size()>0){
							for(com.winterframework.firefrog.phone.web.cotroller.dto.GameSlipResponseDTO dto : slipResonseDTOList){
								
								List<PointsRequestDTO> pointsList = new ArrayList<PointsRequestDTO>();
								if(dto.getGameBetType().getBetTypeCode().equals(project.getMethodid())){
									
									LockPoint point = dto.getLockPoints();
									for(Points ps : point.getPoints()){
										PointsRequestDTO pdt = new PointsRequestDTO();
										pdt.setCurrentPhase(ps.getCurrPhase());
										pdt.setMult(ps.getMult());
										pdt.setPoint(ps.getPoint());
										pdt.setRetValue(new BigDecimal(ps.getRetValue()).multiply(new BigDecimal(10000), MathContext.DECIMAL32).setScale(2, RoundingMode.HALF_UP).longValue());
										pdt.setSingleBet(ps.getSingleBet());
										pointsList.add(pdt);
									}
								}
								struc.setPointsList(pointsList);
							}
						}
					}
					
					betDetailsStruc.add(struc);
				}
			}
			
			List<GamePlanBetOriginDataStruc>  betOriginDataStruc = new ArrayList<GamePlanBetOriginDataStruc>();
			
			for(Projects project :list){
				
				GamePlanBetOriginDataStruc struc = new GamePlanBetOriginDataStruc();
				String[] codes = project.getMethodid().split("_"); 
				
				struc.setBetdetail(project.getCodes());
				struc.setGameGroupCode(Integer.parseInt(codes[0]));
				struc.setGameSetCode(Integer.parseInt(codes[1]));
				struc.setBetMethodCode(Integer.parseInt(codes[2]));
				
				if(null != project.getFileMode()){
					struc.setFileMode(project.getFileMode());
				}
				struc.setFileMode(getFileMode(project.getMethodid()).getValue());
				struc.setIssueCode(buy.getIssue());
				if (project.getMode() == 2) {
					struc.setItemAmount(2000); // 0.2 元 x 10000
				}else if (project.getMode() == 3) {
					struc.setItemAmount(200); // 0.02 元 x 10000
				} else {
					struc.setItemAmount(20000); // 2 元
				}
				if(requestData.getLotteryid().longValue()==99601L || requestData.getLotteryid().longValue()==99602L){
					struc.setMoneyMode(MoneyMode.JIAO.getValue());
				}else{
					struc.setMoneyMode(project.getMode());
				}
				struc.setAwardMode(project.getAwardMode());
				struc.setMultiple(project.getTimes());
				struc.setTotbets(project.getNums());
				struc.setTotamount(new BigDecimal(project.getMoney()).multiply(new BigDecimal(10000), MathContext.DECIMAL32).setScale(2, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP).longValue());;
				betOriginDataStruc.add(struc);
			}
			
			requestData.setBetOriginDataStruc(betOriginDataStruc);
			requestData.setBetDetailsStruc(betDetailsStruc);
			
			GamePlanPhoneResponse result = new GamePlanPhoneResponse();
			Response<GamePlanResponse> gameResponse = httpClient.invokeHttp(gameUrl + gamePlanUrl, requestData, new Pager(0,10000), ut.getUserId(), ut.getUserName(),new TypeReference<Response<GamePlanResponse>>() {
			});
			
			if(null != gameResponse.getBody() && null != gameResponse.getBody().getResult()){
				
				GamePlanResponse gamePlanResponse = gameResponse.getBody().getResult();
				
				if(null != gamePlanResponse.getOverMutipleDTO()){
					List<GameOrderResponeOverMutipleDTO> overMutipleDTO = new ArrayList<GameOrderResponeOverMutipleDTO>();
					for(GameOrderResponeOverMutipleDTO overMutipledto : gamePlanResponse.getOverMutipleDTO()){
						
						String[] betCodes = overMutipledto.getBetTypeCode().split("_");
						String name ="";
						for(int i=0; i<betCodes.length ;i++){
							name += GameAwardNameUtil.getTitle(buy.getLotteryId(), Integer.parseInt(betCodes[0]), Integer.parseInt(betCodes[1]), Integer.parseInt(betCodes[2]), i) +"_"; 
						}
						overMutipledto.setBetMethod(name.substring(0, name.length()-1));
						overMutipleDTO.add(overMutipledto);
					}
					result.setOverMutipleDTO(overMutipleDTO);
				}
				
				result.setGamePlanId(gamePlanResponse.getGamePlanId());
				
				if(null != gamePlanResponse.getGameOrderResponseDTO()){
					GameOrderResponseDTO gameOrderDTO = new GameOrderResponseDTO();
					
					gameOrderDTO.setGameIssueCode(gamePlanResponse.getGameOrderResponseDTO().getGameIssueCode());
					gameOrderDTO.setGamePlanParm(gamePlanResponse.getGameOrderResponseDTO().getGamePlanParm());
					gameOrderDTO.setIsTrace(gamePlanResponse.getGameOrderResponseDTO().getIsTrace());
					gameOrderDTO.setLotteryName(gamePlanResponse.getGameOrderResponseDTO().getLotteryName());
					gameOrderDTO.setStopMode(gamePlanResponse.getGameOrderResponseDTO().getStopMode());
					gameOrderDTO.setStopParms(gamePlanResponse.getGameOrderResponseDTO().getStopParms());
					gameOrderDTO.setTotalAmount(new BigDecimal(gamePlanResponse.getGameOrderResponseDTO().getTotalAmount()==null ? 0 : gamePlanResponse.getGameOrderResponseDTO().getTotalAmount()).divide(new BigDecimal(10000),2,RoundingMode.HALF_UP).doubleValue());
					gameOrderDTO.setWebIssueCode(gamePlanResponse.getGameOrderResponseDTO().getWebIssueCode());
					List<com.winterframework.firefrog.phone.web.cotroller.dto.GameSlipResponseDTO> pdlist = new ArrayList<com.winterframework.firefrog.phone.web.cotroller.dto.GameSlipResponseDTO>();
					if (null != gamePlanResponse.getGameOrderResponseDTO().getSlipResonseDTOList()) {
						for (GameSlipResponseDTO dto : gamePlanResponse
								.getGameOrderResponseDTO()
								.getSlipResonseDTOList()) {
							com.winterframework.firefrog.phone.web.cotroller.dto.GameSlipResponseDTO pd = new com.winterframework.firefrog.phone.web.cotroller.dto.GameSlipResponseDTO();

							pd.setBetDetail(dto.getBetDetail());

							String[] betCodes = dto.getGameBetType()
									.getBetTypeCode().split("_");
							String name = "";
							for (int i = 0; i < betCodes.length; i++) {
								name += GameAwardNameUtil.getTitle(
										buy.getLotteryId(),
										Integer.parseInt(betCodes[0]),
										Integer.parseInt(betCodes[1]),
										Integer.parseInt(betCodes[2]), i)
										+ "_";
							}
							pd.setBetMethod(name.substring(0, name.length() - 1));
							pd.setCurr(dto.getCurr());
							pd.setGameBetType(dto.getGameBetType());
							pd.setIssueCode(dto.getIssueCode());
							boolean isLock = false;
							if (null != dto.getLockPoints()) {
								LockPoint lockPoints = new LockPoint();
								com.winterframework.firefrog.game.entity.LockPoint point = dto.getLockPoints();
								lockPoints.setBeishu(point.getBeishu());
								lockPoints.setBetDetail(point.getBetDetail());
								lockPoints.setBetTotal(point.getBetTotal());
								lockPoints.setLocks(point.getLocks());
								isLock = point.getIsLocks();
								lockPoints
										.setRealBeishu(point.getIsLocks() == true ? 0L
												: point.getRealBeishu()); // 有封锁就直接不成功
								List<Points> points = new ArrayList<Points>();
								for (com.winterframework.firefrog.game.entity.Points p : point
										.getPoints()) {
									Points ps = new Points();
									ps.setCurrPhase(p.getCurrPhase());
									ps.setMult(p.getMult());
									ps.setPoint(p.getPoint());
									ps.setRetValue(new BigDecimal(p
											.getRetValue() == null ? 0 : p
											.getRetValue()).divide(
											new BigDecimal(10000), 2,
											RoundingMode.HALF_UP).doubleValue());
									ps.setSingle(p.getSingle());
									ps.setSingleBet(p.getSingleBet());
									points.add(ps);
								}
								lockPoints.setPoints(points);
								pd.setLockPoints(lockPoints);
							}
							pd.setMoneyMode(dto.getMoneyMode());
							pd.setAwardMode(dto.getAwardMode());
							pd.setMultiple(isLock == true ? 0 : dto
									.getMultiple());
							pd.setTotalAmount(isLock == true ? 0d
									: new BigDecimal(
											dto.getTotalAmount() == null ? 0
													: dto.getTotalAmount()).divide(new BigDecimal(10000), 2,RoundingMode.HALF_UP).doubleValue());
							pd.setTotalBet(dto.getTotalBet());
							pdlist.add(pd);
						}
						gameOrderDTO.setSlipResonseDTOList(pdlist);
						result.setGameOrderResponseDTO(gameOrderDTO);
					}

				}

				response.setResult(result);
			}else{
				response.setResult(null);
			}
			response.getHead().setStatus(gameResponse.getHead().getStatus());
		}catch(Exception e){
			log.error("gamePlan error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	private boolean checkPlanRequest(Request<BuyRequest> request) {
		BuyRequest buy = request.getBody().getParam();
		
		Float packageAmount = buy.getMoney();
		log.debug("packageAmount : " + packageAmount);
		Float amount = 0f;
		Float tempAmount = 0f;
		for(Projects project : buy.getList()){
			amount+= project.getMoney();
			if(project.getMoney()<=0){
				return true;
			}
			if(project.getNums()<=0){
				return true;
			}
			if(project.getMode()==2){
				tempAmount += 0.2f*project.getTimes()*project.getNums();
			}else if(project.getMode()==3){
				tempAmount += 0.02f*project.getTimes()*project.getNums();
			}else{
				tempAmount += 2f*project.getTimes()*project.getNums();
			}
		}
		
		if(new BigDecimal(amount.floatValue()).setScale(2, RoundingMode.HALF_UP).compareTo(new BigDecimal( tempAmount.floatValue()).setScale(2,RoundingMode.HALF_UP)) != 0){
			return true;
		}
				
		String[] issues = buy.getTraceIssues().split(",");
		String[] times = buy.getTraceTimes().split(",");
		if(issues.length != times.length){
			log.error("traceIssues length : " + issues.length 
					+",content : " + buy.getTraceIssues());
			log.error("traceTimes length : " + times.length
					+",content : " + buy.getTraceTimes());
			return true;
		}
		log.debug("---------------------------isPlan = true");
		Float temp = amount;
		log.debug("temp : " + temp);
		amount = 0f;
		log.debug("times size : " + times.length);
		for (String s : times) {
			Float temp2 = new BigDecimal(temp.toString()).multiply(new BigDecimal(Integer.parseInt(s))).setScale(2, RoundingMode.HALF_UP).floatValue();
			if(temp2 <= 0F){
				log.error("bet multiple or totamount equals <= zero~~!");
				return true;
			}
			log.debug("--amount : " + amount);
			log.debug("--temp2 : " + temp2);
			amount += temp2;
		}
		
		log.debug("packageAmount ="+ packageAmount + ", amount="+ amount);
		if(packageAmount<=0f || amount<=0f){
			return true;
		}
		
		BigDecimal bg = new BigDecimal(amount);
		bg = bg.setScale(2, RoundingMode.HALF_UP);
		log.debug("bg : " + bg);
		amount = bg.floatValue();
		log.debug("packageAmount.compareTo(amount) : " + packageAmount.compareTo(amount));
		if(packageAmount.compareTo(amount) ==0){
			log.debug("----------------------------------------------");
			return false;
		}
		return true;
	}
	
	private boolean checkRequest(Request<BuyRequest> request, boolean isPlan) {
		
		BuyRequest buy = request.getBody().getParam();
		
		Float packageAmount = buy.getMoney();
		
		Float amount = 0f;
		Float tempAmount = 0f;
		for(Projects project : buy.getList()){
			amount+= project.getMoney();
			if(project.getMoney()<=0){
				return true;
			}
			if(project.getNums()<=0){
				return true;
			}
			if(project.getMode()==2){
				tempAmount += 0.2f*project.getTimes()*project.getNums();
			}else if(project.getMode()==3){
				tempAmount += 0.02f*project.getTimes()*project.getNums();
			}else{
				tempAmount += 2f*project.getTimes()*project.getNums();
			}
		}
		
		if(new BigDecimal(amount.floatValue()).setScale(2, RoundingMode.HALF_UP).compareTo(new BigDecimal( tempAmount.floatValue()).setScale(2,RoundingMode.HALF_UP)) != 0){
			return true;
		}
		
		if(isPlan){
			Float temp = amount;
			amount = 0f;
			String[] times = buy.getTraceTimes().split(",");
			for (String s : times) {
				Float temp2 = new BigDecimal(temp).multiply(new BigDecimal(Integer.parseInt(s))).setScale(2, RoundingMode.HALF_UP).floatValue();
				amount += temp2;
			}
		}
		log.debug("packageAmount ="+ packageAmount + ", amount="+ amount);
		if(packageAmount<=0f || amount<=0f){
			return true;
		}
		
		BigDecimal bg = new BigDecimal(amount);
		bg = bg.setScale(2, RoundingMode.HALF_UP);
		
		amount = bg.floatValue();
		log.debug("amount="+ amount);
		if(packageAmount.compareTo(amount) ==0){
			return false;
		}
		return true;
	}
	
	private GameOrder convertGameOrderRequest2GameOrder(
			Request<BuyRequest> request, Long lotteryId, Long userId) {
		GameOrder order = new GameOrder();
		GamePackage gamePackage = new GamePackage();
		order.setGamePackage(gamePackage);

		User user = new User();
		user.setId(userId);
		gamePackage.setUser(user);

		Lottery lottery = new Lottery();
		lottery.setLotteryId(request.getBody().getParam().getLotteryId());
		order.setLottery(lottery);
		gamePackage.setLottery(lottery);

		GameIssueEntity gameIssueEntity = new GameIssueEntity();
		gameIssueEntity.setIssueCode(request.getBody().getParam().getIssue());
		order.setGameIssue(gameIssueEntity);
		gamePackage.setGameIssue(gameIssueEntity);

		gamePackage.setUserip(request.getBody().getParam().getUserIp());
		gamePackage.setPackageAmount(new BigDecimal(request.getBody().getParam().getMoney()).multiply(new BigDecimal(10000),MathContext.DECIMAL32).setScale(2, RoundingMode.HALF_UP).longValue());
		gamePackage.setSaleTime(DataConverterUtil.convertLong2Date(request.getBody().getParam().getSaleTime()));
		gamePackage.setType(GamePackageType.PACKAGES);

		gamePackage.setItemList(convertBetDetailsStruc(request.getBody().getParam().getList(), gamePackage));
		gamePackage.setFileMode(gamePackage.getItemList().get(0).getFileMode());

		order.setTotalAmount(new BigDecimal(request.getBody().getParam().getMoney()).multiply(new BigDecimal(10000),MathContext.DECIMAL32).setScale(2, RoundingMode.HALF_UP).longValue());
		order.setSaleTime(DataConverterUtil.convertLong2Date(request.getBody().getParam().getSaleTime()));
		order.setStatus(OrderStatus.WAITING);
		order.setCancelModes(CancelMode.DEFAULTS);
		order.setFileMode(gamePackage.getFileMode());

		order.setSlipList(convertGameOrderRequest2GameOrderDetail(order, request, userId));


		return order;
	}
	protected Double getLhcOdds(List<GameLhcOdds> lhcOddsList,String betTypeCode,boolean isOnYear){
		for(GameLhcOdds odds:lhcOddsList){
			if(betTypeCode.equals("54_18_82")){
				if(isOnYear && odds.getLhcCode().equals("ONYEAR")){
					return odds.getActualBonus()*1.0/10000;
				}else if(!isOnYear && odds.getLhcCode().equals("UNYEAR")){
					return odds.getActualBonus()*1.0/10000;
				}
			}else if(betTypeCode.equals("54_37_83")){
				if(odds.getLhcCode().equals("TWOFACE")){
					return odds.getActualBonus()*1.0/10000;
				}
			}else if(betTypeCode.equals("54_19_84")){
				if(isOnYear && odds.getLhcCode().equals("RED")){
					return odds.getActualBonus()*1.0/10000;
				}else if(!isOnYear && (odds.getLhcCode().equals("GREEN") || odds.getLhcCode().equals("BLUE"))){
					return odds.getActualBonus()*1.0/10000;
				}
			}else if(betTypeCode.equals("55_38_86")){
				if(isOnYear && odds.getLhcCode().equals("ONONEYEAR")){
					return odds.getActualBonus()*1.0/10000;
				}else if(!isOnYear && odds.getLhcCode().equals("UNONEYEAR")){
					return odds.getActualBonus()*1.0/10000;
				}
			}else{
				return 0.0;
			}
		}
		return 0.0;
	}
	public static List<com.winterframework.firefrog.game.entity.GameSlip> convertGameOrderRequest2GameOrderDetail(
			GameOrder order, Request<BuyRequest> request, Long userId) {
		List<com.winterframework.firefrog.game.entity.GameSlip> list = new ArrayList<com.winterframework.firefrog.game.entity.GameSlip>();
		
		BuyRequest buy = request.getBody().getParam();
		for (Projects detailStruc : buy.getList()) {
			com.winterframework.firefrog.game.entity.GameSlip detail = new com.winterframework.firefrog.game.entity.GameSlip();

			GameIssueEntity issue = new GameIssueEntity(buy.getIssue());
			detail.setIssueCode(issue);

			if(order.getLottery().getLotteryId().longValue()==99601L||order.getLottery().getLotteryId().longValue()==99602L){
				detail.setMoneyMode(MoneyMode.JIAO);
			}else{
				detail.setMoneyMode(EnumTypeConverts.convertMoneyMode(detailStruc.getMode()));
			}
			//六合彩賠率
			if(order.getLottery().getLotteryId().longValue()== 99701){
				detail.setSingleWin( new Double(detailStruc.getOdds() * 10000).longValue() );
			}
			detail.setBetDetail(detailStruc.getCodes());
			
			String[] betTypes = detailStruc.getMethodid().split("_");
			
			GameBetType betType = new GameBetType(Integer.parseInt(betTypes[0]), Integer.parseInt(betTypes[1]),
					Integer.parseInt(betTypes[2]));
			detail.setGameBetType(betType);

			detail.setTotalBet(Long.valueOf(detailStruc.getNums()));
			detail.setTotalAmount(new BigDecimal(detailStruc.getMoney()).multiply(new BigDecimal(10000),MathContext.DECIMAL32).setScale(2, RoundingMode.HALF_UP).longValue());
			detail.setMultiple(detailStruc.getTimes());

			detail.setGameSlipStatus(EnumTypeConverts.convertGameSlipStatus(1));
			detail.setCrateTime(new Date());

			detail.setGameOrder(order);

			list.add(detail);
		}

		return list;
	}
	
	public static  List<com.winterframework.firefrog.game.entity.GamePackageItem> convertBetDetailsStruc(
			List<Projects> betDetailStrucs, GamePackage gamePackage){

		List<com.winterframework.firefrog.game.entity.GamePackageItem> betDetails = new ArrayList<com.winterframework.firefrog.game.entity.GamePackageItem>();

		for (Projects detailStruc : betDetailStrucs) {
			com.winterframework.firefrog.game.entity.GamePackageItem item = new com.winterframework.firefrog.game.entity.GamePackageItem();
			item.setBetDetail(detailStruc.getCodes());
			if (detailStruc.getMode() == 2) {
				item.setItemAmount(2000L);
			}else if (detailStruc.getMode() == 3) {
				item.setItemAmount(200L);
			} else {
				item.setItemAmount(20000L); // 2 yuan
			}
			if(gamePackage.getLottery().getLotteryId().longValue()==99601L||gamePackage.getLottery().getLotteryId().longValue()==99602L){
				item.setMoneyMode(MoneyMode.JIAO);
			}else{
				item.setMoneyMode(EnumTypeConverts.convertMoneyMode(detailStruc.getMode()));
			}
			item.setMultiple(detailStruc.getTimes());
			item.setTotamount(new BigDecimal(detailStruc.getMoney()).multiply(new BigDecimal(10000),MathContext.DECIMAL32).setScale(2, RoundingMode.HALF_UP).longValue());
			item.setTotbets(Long.valueOf(detailStruc.getNums()));
			item.setFileMode(getFileMode(detailStruc.getMethodid()));
			String[] betTypes = detailStruc.getMethodid().split("_");
			item.setGameBetType(new GameBetType(Integer.parseInt(betTypes[0]), Integer.parseInt(betTypes[1]),
					Integer.parseInt(betTypes[2])));
			item.setCreateTime(new Date());

			item.setGamePackage(gamePackage);
			item.setAwardMode(detailStruc.getAwardMode());
			betDetails.add(item);
		}
		return betDetails;
	}
	
	private static FileMode getFileMode(String betTypeCode){
		Set<String> fileSet=new HashSet<String>();
		fileSet.add("10_10_11");
		fileSet.add("11_10_11");
		fileSet.add("12_10_11");
		fileSet.add("12_11_37");
		fileSet.add("12_11_62");
		fileSet.add("12_11_63");
		fileSet.add("13_10_11");
		fileSet.add("13_11_37");
		fileSet.add("13_11_62");
		fileSet.add("13_11_63");
		fileSet.add("14_10_11");
		fileSet.add("14_11_11");
		fileSet.add("15_10_11");
		fileSet.add("15_11_11");
		fileSet.add("22_20_11");
		fileSet.add("23_10_11");
		fileSet.add("23_11_11");
		fileSet.add("23_21_11");
		fileSet.add("24_10_11");
		fileSet.add("24_11_11");
		fileSet.add("24_22_11");
		fileSet.add("25_23_11");
		fileSet.add("26_24_11");
		fileSet.add("27_25_11");
		fileSet.add("28_26_11");
		fileSet.add("29_27_11");
		fileSet.add("30_10_11");
		fileSet.add("30_11_11");
		fileSet.add("32_71_68");
		fileSet.add("33_10_11");
		fileSet.add("33_11_37");
		fileSet.add("33_11_62");
		fileSet.add("33_11_63");
		fileSet.add("47_10_11");
		fileSet.add("47_11_37");
		fileSet.add("47_11_62");
		fileSet.add("47_11_63");
		fileSet.add("48_10_11");
		fileSet.add("48_11_11");
		if(fileSet.contains(betTypeCode)){
			return FileMode.FILE;
		}else{
			return FileMode.NUFILE;
		}

	}
}