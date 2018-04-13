package com.winterframework.firefrog.phone.web.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.IPConverter;
import com.winterframework.firefrog.common.validate.business.IKeyGenerator;
import com.winterframework.firefrog.common.validate.business.IValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.common.validate.business.IValidatorFactory;
import com.winterframework.firefrog.game.entity.GameOrder;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.exception.GameBetAmountErrorException;
import com.winterframework.firefrog.game.exception.GameBetContentPatternErrorException;
import com.winterframework.firefrog.game.exception.GameTotbetsErrorException;
import com.winterframework.firefrog.game.web.dto.BetDetailStruc;
import com.winterframework.firefrog.game.web.dto.BetDetailStrucDTO;
import com.winterframework.firefrog.game.web.dto.BetLimitQueryByBetRequest;
import com.winterframework.firefrog.game.web.dto.BetLimitQueryResponse;
import com.winterframework.firefrog.game.web.dto.BetMethodMultipleStruc;
import com.winterframework.firefrog.game.web.dto.BetMethodValidListQueryRequest;
import com.winterframework.firefrog.game.web.dto.BetMethodValidListQueryResponse;
import com.winterframework.firefrog.game.web.dto.DTOConvert;
import com.winterframework.firefrog.game.web.dto.GameBetBalls;
import com.winterframework.firefrog.game.web.dto.GameBetJsonDateStruc;
import com.winterframework.firefrog.game.web.dto.GameBetJsonResultStruc;
import com.winterframework.firefrog.game.web.dto.GameBetTplData;
import com.winterframework.firefrog.game.web.dto.GameLimit;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameOrderQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameOrderRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderRequestDTO;
import com.winterframework.firefrog.game.web.dto.GameOrderResponse;
import com.winterframework.firefrog.game.web.dto.LockPointRequestDTO;
import com.winterframework.firefrog.game.web.dto.LotteryGameUserAwardGroupQueryRequest;
import com.winterframework.firefrog.game.web.dto.LotteryGameUserAwardGroupQueryResponse;
import com.winterframework.firefrog.game.web.dto.LotteryGameUserAwardListStruc;
import com.winterframework.firefrog.game.web.dto.MMCOrderDetail;
import com.winterframework.firefrog.game.web.dto.OrdersStruc;
import com.winterframework.firefrog.game.web.dto.PointsRequestDTO;
import com.winterframework.firefrog.game.web.dto.SlipsStruc;
import com.winterframework.firefrog.game.web.util.GameAwardNameUtil;
import com.winterframework.firefrog.phone.util.IPUtil;
import com.winterframework.firefrog.phone.utils.Encrypter;
import com.winterframework.firefrog.phone.web.bet.convertor.IBetNameConvertor;
import com.winterframework.firefrog.phone.web.bet.convertor.impl.BetNameConvertor;
import com.winterframework.firefrog.phone.web.bet.entity.LotteryBetMethod;
import com.winterframework.firefrog.phone.web.bet.entity.LotteryConfig;
import com.winterframework.firefrog.phone.web.bet.entity.LotteryGameGroup;
import com.winterframework.firefrog.phone.web.bet.entity.LotteryGameSet;
import com.winterframework.firefrog.phone.web.cotroller.dto.GameListDto;
import com.winterframework.firefrog.phone.web.cotroller.dto.OpenLinkDetailRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserBizSwitchRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserBizSwitchResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserToken;
import com.winterframework.firefrog.phone.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.phone.web.validate.business.LotteryPlayMethodKeyGenerator;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.RequestHeader;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResponseHeader;

@Controller("slmmc2000Controller")
@RequestMapping("/slmmc2000")
public class Slmmc2000Controller extends BaseController {

	private static final Logger log = LoggerFactory
			.getLogger(Slmmc2000Controller.class);

	@PropertyConfig(value = "url.game.buy")
	private String saveGameOrder;

	@PropertyConfig(value = "url.front.gameList")
	private String queryOrders;

	@PropertyConfig(value = "url.front.gamedetail")
	private String queryOrderDetail;

	@PropertyConfig(value = "url.front.userBal")
	private String getUserBal;

	@PropertyConfig(value = "url.query.gameValidBetMethods")
	private String gameValidBetMethods;

	@PropertyConfig(value = "url.game.queryBetLimitByBet")
	private String queryBetLimit;

	@PropertyConfig(value = "url.user.queryUserDetailInfoByAccount")
	private String queryUserByNameUrl;
	
	@PropertyConfig(value="url.game.userAwardUrl")
	private String userAwardUrl;
	
	@PropertyConfig(value="url.front.queryBizSwitch")
	private String queryBizSwitch;

	private static final Long LOTTERYID = 99113L;

	private static final String LOTTERYCODE = "slmmc2000";

	private static final String defaultMethod = "13,10,10";

	protected IBetNameConvertor bnConvertor;
	//扩展配置项，由各彩种添加
	private Map<String, String> extendedProperties;
	
	//基本配置项
	private Map<String, String> properties;
	
	private ObjectMapper jsonMapper = new ObjectMapper();

	@Resource(name = "gameBetNumberValidatorFactory")
	private IValidatorFactory<GameSlip> validatorFactory;

	@ResponseBody
	@RequestMapping("/init")
	public Response<LotteryConfig> initData(@RequestBody Request<OpenLinkDetailRequest> request) throws Exception {
		Response<LotteryConfig> response = new Response<LotteryConfig>(request);
		log.info("---------------------------slmmc2000 initData --------------------------");
		String token = request.getHead().getSessionId();
		log.info("token : " + request.getHead().getSessionId());
		log.info("activityType : " + request.getBody().getParam().getActivityType());
		
		try {
			String account = getUserNameByToken(token);
			if (null == account) {
				response.getHead().setStatus(109990L);
				return response;
			}
			UserToken ut = getUserToken(account);
			LotteryConfig result = new LotteryConfig();
			// test code
//			UserToken ut = new UserToken();
//			ut.setUserName("captainray");
//			ut.setUserId(1300271L);
			// get user balance
			if (null != ut.getUserId()) {
				Response<Long> balance = httpClient.invokeHttp(firefrogUrl
						+ getUserBal, ut.getUserId(), Long.class);
				log.info("balance : "
						+ new BigDecimal(balance.getBody().getResult()).divide(
								new BigDecimal(10000), 2, RoundingMode.HALF_UP)
								.doubleValue());
				result.setBalance(new BigDecimal(balance.getBody().getResult())
						.divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP)
						.doubleValue());
			}

			result.setGameType(LOTTERYCODE);
			result.setGameTypeCn("超級2000秒秒彩");
			result.setLotteryId(LOTTERYID);
			result.setUserId(ut.getUserId());
			result.setUserName(ut.getUserName());
			// get bet type status
			BetMethodValidListQueryRequest betMethodValidListQueryRequest = new BetMethodValidListQueryRequest();
			betMethodValidListQueryRequest.setLotteryid(LOTTERYID);
			Response<BetMethodValidListQueryResponse> betMethodValidListQueryResponse = httpClient
					.invokeHttp(gameUrl + gameValidBetMethods,
							betMethodValidListQueryRequest,
							BetMethodValidListQueryResponse.class);
			String[] gameTypeCodes = betMethodValidListQueryResponse.getBody()
					.getResult().getValidMethods();
			log.info("ary length : " + gameTypeCodes.length);

			Set<LotteryGameGroup> gameGroups = new HashSet<LotteryGameGroup>();

			Set<LotteryGameSet> gameSets = new HashSet<LotteryGameSet>();

			Set<LotteryBetMethod> betMethods = new HashSet<LotteryBetMethod>();

			for (String gameTypeCode : gameTypeCodes) {
				String[] codes = StringUtils.split(gameTypeCode, ",");
				Integer gameGroupCode = Integer.valueOf(codes[0]);
				Integer gameSetCode = Integer.valueOf(codes[1]);
				Integer betMethodCode = Integer.valueOf(codes[2]);

				LotteryGameGroup gameGroup = new LotteryGameGroup();
				gameGroup.setCode(gameGroupCode);
				gameGroup.setName(GameAwardNameUtil.getName(LOTTERYID,
						gameGroupCode, gameSetCode, betMethodCode, 0));
				gameGroup.setTitle(GameAwardNameUtil.getTitle(LOTTERYID,
						gameGroupCode, gameSetCode, betMethodCode, 0));

				LotteryGameSet gameSet = new LotteryGameSet();
				gameSet.setCode(gameSetCode);
				gameSet.setName(GameAwardNameUtil.getName(LOTTERYID,
						gameGroupCode, gameSetCode, betMethodCode, 1));
				gameSet.setTitle(GameAwardNameUtil.getTitle(LOTTERYID,
						gameGroupCode, gameSetCode, betMethodCode, 1));
				gameSet.setParent(gameGroup.getName());

				LotteryBetMethod betMethod = new LotteryBetMethod();
				betMethod.setCode(betMethodCode);
				betMethod.setName(GameAwardNameUtil.getName(LOTTERYID,
						gameGroupCode, gameSetCode, betMethodCode, 2));
				betMethod.setTitle(GameAwardNameUtil.getTitle(LOTTERYID,
						gameGroupCode, gameSetCode, betMethodCode, 2));
				betMethod.setParent(gameSet.getName());
				betMethod.setMode(gameGroup.getName());

				gameGroups.add(gameGroup);
				gameSets.add(gameSet);
				betMethods.add(betMethod);
			}

			bnConvertor = new BetNameConvertor(new ArrayList<LotteryGameGroup>(
					gameGroups), new ArrayList<LotteryGameSet>(gameSets),
					new ArrayList<LotteryBetMethod>(betMethods));
			result.setGameMethods(bnConvertor.getGameTypes(gameTypeCodes));
					
			boolean flag = false;
			for (String code : gameTypeCodes) {
				if (defaultMethod.equals(code)) {
					flag = true;
					List<LotteryGameGroup> temp = bnConvertor.getGameTypes(new String[] { code });
					result.setDefaultMethod(temp.get(0).getName() + "." + temp.get(0).getChilds().get(0).getName()
							+ "." + temp.get(0).getChilds().get(0).getChilds().get(0).getName());
				}
			}
			//当指定默认玩法被停售时默认玩法动态加载
			if (flag == false) {
				if (!result.getGameMethods().isEmpty()) {
					result.setDefaultMethod(result.getGameMethods().get(0).getName() + "."
							+ result.getGameMethods().get(0).getChilds().get(0).getName() + "."
							+ result.getGameMethods().get(0).getChilds().get(0).getChilds().get(0).getName());
				}
			}
			//查询投注限制
			BetLimitQueryByBetRequest betLimitQueryRequest = new BetLimitQueryByBetRequest();
			betLimitQueryRequest.setLotteryid(LOTTERYID);
			Response<BetLimitQueryResponse> betLimitQueryResponse = httpClient.invokeHttp( gameUrl + queryBetLimit, betLimitQueryRequest, BetLimitQueryResponse.class);
			
			List<Map<String, GameLimit>> gameLimits = new ArrayList<Map<String, GameLimit>>();
			Map<String, GameLimit> map = new HashMap<String, GameLimit>();
			
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
							if(bs.getMultiples()!=null&&bs.getMultiples().length>0){
								gl.setMaxmultiples(bs.getMultiples());
							}else{
							gl.setMaxmultiple(bs.getMultiple().longValue());
							}
						}
					} 
				} else {
					gl.setMaxmultiple(-1L);//null值为无限制
				}
				//gl.setUsergroupmoney(null);// 此数据已不在这里获取了，现在随意给个null值  此行不能注销，前台js需要
				map.put(GameAwardNameUtil.getFullName(LOTTERYID, gameGroupCode, gameSetCode, betMethodCode), gl);
			}
			gameLimits.add(map);
			result.setGameLimits(gameLimits);
			
			//获取当前用户在当前彩种的奖金组信息
			LotteryGameUserAwardGroupQueryRequest awardRequest = new LotteryGameUserAwardGroupQueryRequest();
			awardRequest.setLotteryId(LOTTERYID);
			awardRequest.setUserId(ut.getUserId());
			
			Response<LotteryGameUserAwardGroupQueryResponse> userAwardResponse = httpClient.invokeHttp(gameUrl+ userAwardUrl, awardRequest,new Pager(), ut.getUserId(),ut.getUserName(), new TypeReference<Response<LotteryGameUserAwardGroupQueryResponse>>() {
			});
			
			List<LotteryGameUserAwardListStruc> userAwardList = new ArrayList<LotteryGameUserAwardListStruc>();
			if(null != userAwardResponse.getBody().getResult()){
				userAwardList = userAwardResponse.getBody().getResult().getUserAwardListStruc();
			}
			result.setAwardGroups(userAwardList);
			for(LotteryGameUserAwardListStruc awardStruc:userAwardList){
				if(awardStruc.getBetType().intValue() == 1){
					result.setUserAwardName(awardStruc.getAwardName());
					break;
				}
			}
			
		
			// get user records
			List<GameListDto> records = getGameList(ut);
			log.info("records size : " + records.size());
			result.setRecords(records);
			//add AWARD_RET_STATUS queryBizSwitch
			UserBizSwitchRequest bizSwitchrReq = new UserBizSwitchRequest();
			bizSwitchrReq.setUserId(ut.getUserId());
			bizSwitchrReq.setType(1);//1:獎金返點 2:超级2000
			
			Response<UserBizSwitchResponse> bizSwitchRsp = httpClient.invokeHttp(firefrogUrl+ queryBizSwitch, bizSwitchrReq,new Pager(), ut.getUserId(),ut.getUserName(), new TypeReference<Response<UserBizSwitchResponse>>() {
			});
			
			result.setRetSwitch(bizSwitchRsp.getBody().getResult().getStatus());
			response.setResult(result);

		} catch (Exception e) {
			log.error("slmmc2000 initData error : ", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}

	@ResponseBody
	@RequestMapping("/bet")
	public Response<GameBetJsonResultStruc> betSubmit(
			@RequestBody Request<OpenLinkDetailRequest> request,
			HttpServletRequest request1) throws Exception {
		String token = request.getHead().getSessionId();
		log.info("----------------------mobile slmmc2000 bet start!!!");
		Response<GameBetJsonResultStruc> response = new Response<GameBetJsonResultStruc>(request);
		String data = request.getBody().getParam().getData();
		  
		log.info("data : " + data);
		UserToken ut = null;
		try {
			String account = getUserNameByToken(token);
			if (null == account) {
				response.getHead().setStatus(109990L);
				return response;
			}
			ut = getUserToken(account);
			// test code
//			 ut = new UserToken();
//			 ut.setUserName("suhern6");
//			 ut.setUserId(1338619L);//joy188:1300271/dev:1292830

			// 此处本地开发环境运行会抛出一个异常，属于正常现象，已经try catch处理，原因是我们没有登录功能，服务器上此处无bug
			long userFreeze = getUserInfos(ut.getUserName(), queryUserByNameUrl)
					.getBody().getResult().getUserStruc().getFreezeMethod();
			log.info("userFreeze : " + userFreeze);
			if (userFreeze == 2L || userFreeze == 3L || userFreeze == 1L) {
				log.info("用户账户已冻结不能投注! ");
				response.getHead().setStatus(109992L);// 109992 此功能已经冻结
				return response;
			}
		} catch (Exception e) {
			log.error("获取用户信息异常", e);
		}

		GameBetJsonResultStruc result = null;
		try {
			result = saveMMC(data, request1, ut.getUserId(), ut.getUserName());
		} catch (Exception e) {
			log.info("saveMMC exception message : " + Long.parseLong(e.getMessage()));
			response.getHead().setStatus(Long.parseLong(e.getMessage()));// 109992
			return response;
		}
		response.setResult(result);
		return response;
	}

	/**
	 * @Title: orderBet
	 * @Description:普通投注
	 * @param dto
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private Response<GameOrderResponse> orderBet(GameOrderRequestDTO dto,
			HttpServletRequest request, Long userId, String account)
			throws Exception {

		GameOrderRequest gr = new GameOrderRequest();

		log.info("--------------orderBet----------------");
		log.info("account : " + account);
		log.info("userId : " + userId);
		
		gr.setLotteryid(LOTTERYID);
		try {
			gr.setUserIp(IPConverter.ipToLong(IPUtil.getClientIpAddr(request)));
			log.info("UserIp : " + gr.getUserIp());
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("dto.getAmount() : " + dto.getAmount());
		gr.setPackageAmount(formatMultipleMoney(Double.valueOf(dto.getAmount())));

		log.info("PackageAmount : " + gr.getPackageAmount());
		gr.setSaleTime(new Date().getTime());
		try{
			gr.setServerIp(IPConverter.ipToLong(getProperty("serverIP")));
		} catch (Exception e) {
			log.info("exception : " + e);
			gr.setServerIp(2130706433L);
		}
		gr.setChannelId(transferChannelId(dto.getChannel()));
		
		log.info("channel : " + dto.getChannel());
		gr.setChannelVersion(dto.getVersion());
		//新增玩法類型
		log.info("activityType : " + dto.getActivityType());
		gr.setActivityType(dto.getActivityType());
		log.info("IsFirstSubmit : " + dto.getIsFirstSubmit());
		gr.setIsFirstSubmit(dto.getIsFirstSubmit());
		if (dto.getOrders() != null && !dto.getOrders().isEmpty()) {
			log.info("IssueCode : " + dto.getOrders().get(0).getIssueCode());
			gr.setIssueCode(dto.getOrders().get(0).getIssueCode());
		}

		List<BetDetailStruc> bdss = new ArrayList<BetDetailStruc>();
		
		Long totalDetailMoney = 0L;
		List<BetDetailStrucDTO> ary = dto.getBalls();
		log.info("ary size : " + ary.size());
		for (BetDetailStrucDTO bd : dto.getBalls()) {
			BetDetailStruc bds = new BetDetailStruc();

			bds.setBetdetail(bd.getBall());
			bds.setFileMode(isFileMode(bd.getType()));
			String[] type = StringUtils.split(bd.getType(), '.');
			bds.setGameGroupCode(GameAwardNameUtil.getCode(LOTTERYID, type[0],
					type[1], type[2], 0));
			bds.setGameSetCode(GameAwardNameUtil.getCode(LOTTERYID, type[0],
					type[1], type[2], 1));
			if (GameAwardNameUtil.getCode(LOTTERYID, type[0], type[1], type[2],
					2) != null) {
				bds.setBetMethodCode(GameAwardNameUtil.getCode(LOTTERYID,
						type[0], type[1], type[2], 2));
			}
			bds.setIssueCode(gr.getIssueCode());
			bds.setMoneyMode(bd.getMoneyunit() == 0.1 ? 2 :(bd.getMoneyunit() == 0.01?3: 1));// 页面传过来的值时0.1
																// 为角模式，对应接口数据1元2角
			log.info("MoneyMode : " + bds.getMoneyMode());
			bds.setMultiple(bd.getMultiple());
			log.info("Multiple : " + bds.getMultiple());
			bds.setItemAmount(getItemAmountValue(bd));// 元角模式单注金额
			log.info("ItemAmount : " + bds.getItemAmount());
			bds.setTotbets(getTotalBet(bd));// 注数
			log.info("TotalBet : " + bds.getTotbets());
			bds.setTotamount(getTotalAmountValue(bd));
			log.info("Totamount : " + bds.getTotamount());
			totalDetailMoney += bds.getTotamount();
			log.info("totalDetailMoney : " + totalDetailMoney);
			// 变价列表
			LockPointRequestDTO lockPointDTO = bd.getLockPoint();
			if (lockPointDTO != null) {
				List<PointsRequestDTO> points = lockPointDTO.getPointsList();
				if (points != null && !points.isEmpty()) {
					bds.setPointsList(points);
				}
			}

			bdss.add(bds);
		}
		gr.setBetDetailStruc(bdss);
		long status = 0l;
		Response<GameOrderResponse> gameOrderResponse = new Response<GameOrderResponse>();
		log.info("----------totalDetailMoney : " + totalDetailMoney.longValue());
		log.info("----------getPackageAmount : " + gr.getPackageAmount());
		if (totalDetailMoney.longValue() != gr.getPackageAmount()) {
			status = 202001l;// 投注金额错误
			RequestHeader requestH = new RequestHeader();
			ResponseHeader rh = ResponseHeader.createReponseHeader(requestH);
			rh.setStatus(status);
			gameOrderResponse.setHead(rh);
			return gameOrderResponse;
		} else {
			// 验证
			GameOrder gameOrder = DTOConvert.convertGameOrderRequest2GameOrder(
					gr, userId);
			status = validate(gameOrder);
			if (status > -1l) {
				RequestHeader requestH = new RequestHeader();
				ResponseHeader rh = ResponseHeader
						.createReponseHeader(requestH);
				rh.setStatus(status);
				gameOrderResponse.setHead(rh);
				return gameOrderResponse;
			}
		}

		log.info("before betHttpClient.saveGameOrder ~~~~~!");
		gameOrderResponse = httpClient.invokeHttp(gameUrl + saveGameOrder, gr,
				userId, account, GameOrderResponse.class);
		log.info("after betHttpClient.saveGameOrder ~~~~~!");
		return gameOrderResponse;
	}

	public GameBetJsonResultStruc saveMMC(String data,
			HttpServletRequest request, Long userId, String account)
			throws Exception {
		log.info("saveGameOrder start");
		log.info("-------------------------------------------------");
		log.info("data : " + data);

		// springmvc 支持直接解析json，但是参数或类型错误不会打印出来，直接给报一个400 bad
		// request，因此在此用代码转换json串 // 金额*10000 自解析json处理异常抛出
		GameOrderRequestDTO dto = new GameOrderRequestDTO();
		try {
			dto = convertJsonToObject(data, GameOrderRequestDTO.class);
		} catch (Exception e) {
			log.error("saveGameOrder json param paser error!", e);
			// return serverMessages.get(99999L);// 参数修改成投注格式错误
			throw new Exception("202004");
		}

		Long status = 0L;
		Response<GameOrderResponse> result = null;
		
		result = orderBet(dto, request, userId,account);
		status = result.getHead().getStatus();
		// status = 111111L;
		log.info("status : " + status);
		

		// 构建秒秒彩返回json
		if (status == 0) {
			Response<GameOrderDetailQueryResponse> response = new Response<GameOrderDetailQueryResponse>();
			GameOrderDetailQueryRequest queryRequest = new GameOrderDetailQueryRequest();
			queryRequest.setOrderId(result.getBody().getResult().getOrderId());
			try {

				response = httpClient.invokeHttp(gameUrl + queryOrderDetail,
						queryRequest, userId, account,
						GameOrderDetailQueryResponse.class);
				log.info("queryOrderDetail end");
			} catch (Exception e) {
				log.error("queryOrderDetail is error.", e);
				throw new Exception("204001");
			}

			GameBetJsonResultStruc struc = new GameBetJsonResultStruc();
			struc.setIsSuccess(1);
			struc.setType("success");
			struc.setMsg("恭喜您投注成功");

			GameBetJsonDateStruc datas = new GameBetJsonDateStruc();
			OrdersStruc oc = response.getBody().getResult().getOrdersStruc();
			List<SlipsStruc> slipStrucs = response.getBody().getResult()
					.getSlipsStruc();
			datas.setProjectId(oc.getOrderCode());
			datas.setWriteTime(DateUtils.format(
					DataConverterUtil.convertLong2Date(oc.getSaleTime()),
					DateUtils.DATETIME_FORMAT_PATTERN));
			datas.setResult(formatOrderNumber(oc.getNumberRecord()));
			datas.setTotalprice(oc.getTotamount());
			datas.setWinMoney(oc.getTotwin());
			if (oc.getStatus() == 2) {
				datas.setWinNum(1L);
			} else {
				datas.setWinNum(0L);
				datas.setWinMoney(0L);
			}

			List<MMCOrderDetail> list = new ArrayList<MMCOrderDetail>();
			for (SlipsStruc ss : slipStrucs) {
				MMCOrderDetail md = new MMCOrderDetail();
				md.setCode(ss.getBetDetail());
				if (ss.getStatus() == 2)// 中奖
				{
					md.setIsWin(1);
				} else {
					md.setIsWin(0);
				}
				md.setMethodName(GameAwardNameUtil.getTitle(LOTTERYID,
						ss.getGameGroupCode(), ss.getGameSetCode(),
						ss.getBetMethodCode(), 0)
						+ "_"
						+ GameAwardNameUtil.getTitle(LOTTERYID,
								ss.getGameGroupCode(), ss.getGameSetCode(),
								ss.getBetMethodCode(), 2));
				if (ss.getMoneyMode() == 1) { // 1元2角
					md.setModes("元");
				}else if (ss.getMoneyMode() == 3) { // 1元2角
					md.setModes("分");
				} else {
					md.setModes("角");
				}
				md.setNum(ss.getTotbets());
				md.setMultiple(ss.getMultiple() + "");
				md.setProjectid(oc.getOrderCode());
				md.setTotalPrice(ss.getTotamount() + "");
				if (ss.getStatus() == 2) {
					md.setWinNum(3L);
					md.setWinMoney(ss.getAward());
				} else {
					md.setWinNum(0L);
					md.setWinMoney(0L);
				}
				md.setWriteTime(DateUtils.format(
						DataConverterUtil.convertLong2Date(oc.getSaleTime()),
						DateUtils.DATETIME_FORMAT_PATTERN));
				list.add(md);
			}
			datas.setList(list);
			struc.setData(datas);
			Response<Long> balance = httpClient.invokeHttp(firefrogUrl
					+ getUserBal, userId, Long.class);

			struc.setBalance(new BigDecimal(balance.getBody().getResult())
					.divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP)
					.doubleValue());
			return struc;

		}

		if (status == 204005) {// 秒秒彩开奖失败
			Response<GameOrderDetailQueryResponse> response = new Response<GameOrderDetailQueryResponse>();
			GameOrderDetailQueryRequest queryRequest = new GameOrderDetailQueryRequest();
			queryRequest.setOrderId(result.getBody().getResult().getOrderId());
			log.info("--------------------------------------status == 204005");
			try {
				response = httpClient.invokeHttp(gameUrl + queryOrderDetail,
						queryRequest, userId, account,
						GameOrderDetailQueryResponse.class);
				log.info("queryOrderDetail end");
			} catch (Exception e) {
				throw new Exception("204005");
			}

			GameBetJsonResultStruc struc = new GameBetJsonResultStruc();
			struc.setIsSuccess(0);
			struc.setType("normal");
			struc.setMsg("秒秒彩开奖失败！投注订单已做撤销处理，请重新投注！");
			GameBetJsonDateStruc datas = new GameBetJsonDateStruc();
			OrdersStruc oc = response.getBody().getResult().getOrdersStruc();
			datas.setProjectId(oc.getOrderCode());
			datas.setWriteTime(DateUtils.format(
					DataConverterUtil.convertLong2Date(oc.getSaleTime()),
					DateUtils.DATETIME_FORMAT_PATTERN));
			datas.setResult(formatOrderNumber(oc.getNumberRecord()));
			datas.setTotalprice(oc.getTotamount());
			datas.setWinMoney(oc.getTotwin());
			if (oc.getStatus() == 2) {
				datas.setWinNum(1L);
			} else {
				datas.setWinNum(0L);
				datas.setWinMoney(0L);
			}
			struc.setData(datas);
			return struc;

		}

		// 保存选号球，假如存在暂停玩法，删掉暂停选号球返回
		List<GameBetBalls> balls = new ArrayList<GameBetBalls>();
		List<BetDetailStrucDTO> betBalls = dto.getBalls();
		GameBetJsonDateStruc jds = new GameBetJsonDateStruc();
		GameBetJsonResultStruc hr = new GameBetJsonResultStruc();
		GameBetTplData tplDate = new GameBetTplData();
		String strStatus = status + "";
		log.info("strStatus : " + strStatus);
		if (strStatus.length() == 12) {
			// 投注方式暂停异常处理需要截取活的投注方式名称
			Integer gameGroupCode = Integer.valueOf(strStatus.substring(0, 2));
			Integer gameBetCode = Integer.valueOf(strStatus.substring(2, 4));
			Integer gameMethodCode = Integer.valueOf(strStatus.substring(4, 6));
			String betName = GameAwardNameUtil.getTitle(LOTTERYID,
					gameGroupCode, gameBetCode, gameMethodCode, 0)
					+ GameAwardNameUtil.getTitle(LOTTERYID, gameGroupCode,
							gameBetCode, gameMethodCode, 1)
					+ GameAwardNameUtil.getTitle(LOTTERYID, gameGroupCode,
							gameBetCode, gameMethodCode, 2);
			String betEnName = GameAwardNameUtil.getFullName(LOTTERYID,
					gameGroupCode, gameBetCode, gameMethodCode);
			// tplDate.setMsg(getMessage("gameMethodPaused", new String[] {
			// betName }));
			jds.setTplData(tplDate);
			hr.setIsSuccess(0);
			hr.setType("pauseBet");
			// r.setMsg(getMessage("gameMethodPaused", new String[] { betName
			// }));
			for (BetDetailStrucDTO ball : betBalls) {
				if (ball.getType().equals(betEnName)) {
					GameBetBalls gb = new GameBetBalls();
					gb.setBall(ball.getBall());
					gb.setId(Long.valueOf(ball.getId()));
					gb.setType(ball.getType());
					balls.add(gb);
				}
			}
			tplDate.setBalls(balls);
			hr.setData(jds);
			return hr;
		} else {
			// return handingBetStatus(status);
			log.info("strStatus : " + strStatus);
			throw new Exception(strStatus);
		}
	}

	private <T> T convertJsonToObject(String json, Class<T> objClass)
			throws Exception {
		return jsonMapper.readValue(json, objClass);
	}

	private String formatOrderNumber(String numberRecord) {
		if (numberRecord != null) {
			String[] nums = numberRecord.split("");
			StringBuilder sb = new StringBuilder();
			for (int i = 1; i < nums.length; i++) {
				if (i != nums.length - 1) {
					sb.append(nums[i]).append(",");
				} else {
					sb.append(nums[i]);
				}
			}
			return sb.toString();
		}
		return null;
	}

	private List<GameListDto> getGameList(UserToken ut) throws Exception {
		GameOrderQueryRequest gameRequest = new GameOrderQueryRequest();
		gameRequest.setAccount(ut.getUserName());
		gameRequest.setLotteryId(99113L);
		gameRequest.setParentType(0);
		gameRequest.setContainSub(0);
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(java.util.Calendar.DATE, -7);

		gameRequest.setStartTime(cal.getTimeInMillis());
		gameRequest.setEndTime(new Date().getTime());
		Response<GameOrderQueryResponse> gameOrderResponse = null;
		try {
			gameOrderResponse = httpClient.invokeHttp(gameUrl + queryOrders,
					gameRequest, new Pager(1, 10000), ut.getUserId(),
					ut.getUserName(),
					new TypeReference<Response<GameOrderQueryResponse>>() {
					});
		} catch (Exception e) {
			throw new Exception("getGameList error!!");
		}
		List<OrdersStruc> orderList = gameOrderResponse.getBody().getResult()
				.getOrdersStruc();

		List<GameListDto> list = new ArrayList<GameListDto>();
		if (null != orderList && !orderList.isEmpty()) {
			for (int i=0;i < orderList.size();i++) {
				if(i >= 20){
					break;
				}
				GameListDto dto = new GameListDto();
				log.info("----------------------------");
				dto.setOrderCode(orderList.get(i).getOrderCode());
				log.info("OrderCode : " + dto.getOrderCode());
				dto.setNumberRecord(orderList.get(i).getNumberRecord());
				log.info("NumberRecord : " + dto.getNumberRecord());
				dto.setLotteryid(orderList.get(i).getLotteryid());
				log.info("Lotteryid : " + orderList.get(i).getLotteryid());
				dto.setBonus(new BigDecimal(orderList.get(i).getTotwin() == null ? 0
						: orderList.get(i).getTotwin()).divide(new BigDecimal(10000), 2,
						RoundingMode.HALF_UP).doubleValue());
				log.info("Bonus : " + dto.getBonus());
				dto.setEnid(String.valueOf(orderList.get(i).getOrderId()));
				log.info("Enid : " + dto.getEnid());
				dto.setIfwin(orderList.get(i).getStatus());
				log.info("Ifwin : " + dto.getIfwin());
				dto.setIscancel(orderList.get(i).getCancelModels());
				log.info("Iscancel : " + dto.getIscancel());
				dto.setIssue(orderList.get(i).getWebIssueCode());
				log.info("Issue : " + dto.getIssue());
				dto.setTime(DateUtils.format(
						DataConverterUtil.convertLong2Date(orderList.get(i).getSaleTime()),
						DateUtils.DATETIME_FORMAT_PATTERN));
				log.info("Time : " + dto.getTime());
				dto.setTotalprice(new BigDecimal(
						orderList.get(i).getTotamount() == null ? 0 : orderList.get(i).getTotamount())
						.divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP)
						.floatValue());
				log.info("Totalprice : " + dto.getTotalprice());

				list.add(dto);
			}
		}
		return list;
	}

	private Long getTotalAmountValue(BetDetailStrucDTO bd) {
		return ((long) ((Double) (2 * 10000 * bd.getMoneyunit())).intValue()
				* bd.getNum() * bd.getMultiple());
	}

	private long formatMultipleMoney(Double amount) {
		return ((Double) (amount * 10000)).longValue();
	}

	/**
	 * 投注格式、 注数、金额验证
	 * 
	 * @param gameOrder
	 * @return
	 * @throws Exception
	 */
	private Long validate(GameOrder gameOrder) throws Exception {

		long status = -1l;

		try {
			for (GameSlip slip : gameOrder.getSlipList()) {
				IKeyGenerator keyGen = new LotteryPlayMethodKeyGenerator(
						99113L, slip.getGameBetType().getGameGroupCode(), slip
								.getGameBetType().getGameSetCode(), slip
								.getGameBetType().getBetMethodCode(), "_$_");

				IValidateExecutorContext context = new BetValidateContext(
						gameOrder, slip);
				IValidateExecutor<GameSlip> validatorExecutor = validatorFactory
						.getValidateExecutor("slmmc2000", keyGen);
				log.info("=======================getSlipList3");
				validatorExecutor.execute(slip, context);
				log.info("=======================getSlipList4");
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
	 * 根据配置的玩法判断是否为单式文件模式
	 * 
	 * @param betMethodType
	 * @return
	 */
	private int isFileMode(String betMethodType) {
		String fileModeMethodTypes = "danshi,hunhezuxuan";
		String[] methodTypes = StringUtils.split(fileModeMethodTypes, ',');
		for (String methodType : methodTypes) {
			if (betMethodType.contains(methodType)) {
				return 1;
			}
		}
		return 0;
	}
	
	private int transferChannelId(String channel){
		int id = 1;
		if("ios".equals(channel.trim())){
			id = 2;
		} else if("android".equals(channel.trim())) {
			id = 4;
		}
		return id;
	}

	private int getItemAmountValue(BetDetailStrucDTO bd) {
		return ((Double) (2 * 10000 * bd.getMoneyunit())).intValue();
	}

	private int getTotalBet(BetDetailStrucDTO bd) {
		return bd.getNum();
	}
	
	private String getProperty(String propName) {
		if (extendedProperties != null && extendedProperties.containsKey(propName)) {
			return extendedProperties.get(propName);
		}
		return properties == null ? null : properties.get(propName);
	}
}
