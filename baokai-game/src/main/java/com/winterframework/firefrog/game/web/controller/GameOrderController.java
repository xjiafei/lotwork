package com.winterframework.firefrog.game.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.annotation.ValidRequestHeader;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.GameBizLockService;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.game.dao.IGamePackageDao;
import com.winterframework.firefrog.game.dao.IGamePointDao;
import com.winterframework.firefrog.game.dao.IGameSeriesDao;
import com.winterframework.firefrog.game.dao.IGameWarnOrderDao;
import com.winterframework.firefrog.game.dao.vo.ActivityConfig;
import com.winterframework.firefrog.game.dao.vo.GameAward;
import com.winterframework.firefrog.game.dao.vo.GamePackage;
import com.winterframework.firefrog.game.dao.vo.GamePoint;
import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.game.dao.vo.GameWarnOrder;
import com.winterframework.firefrog.game.entity.BetLimitAssist;
import com.winterframework.firefrog.game.entity.GameBetDetailTotAmountEntity;
import com.winterframework.firefrog.game.entity.GameBetType;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameMultipleEntity;
import com.winterframework.firefrog.game.entity.GameOrder;
import com.winterframework.firefrog.game.entity.GameOrderOperationsEntity;
import com.winterframework.firefrog.game.entity.GamePackageItem;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.entity.GameSlipOperationsEntity;
import com.winterframework.firefrog.game.entity.Lottery;
import com.winterframework.firefrog.game.exception.GameBetAmountErrorException;
import com.winterframework.firefrog.game.exception.GameBetContentPatternErrorException;
import com.winterframework.firefrog.game.exception.GameBetMethodStatusStopException;
import com.winterframework.firefrog.game.exception.GameBetOverMultipleLimitException;
import com.winterframework.firefrog.game.exception.GameCancalOutTimeErrorException;
import com.winterframework.firefrog.game.exception.GameCancelOrderPermitErrorException;
import com.winterframework.firefrog.game.exception.GameIssueNotExistErrorException;
import com.winterframework.firefrog.game.exception.GameIssueStatusErrorException;
import com.winterframework.firefrog.game.exception.GameIssueStatusStopSaleException;
import com.winterframework.firefrog.game.exception.GameOrderStatusErrorException;
import com.winterframework.firefrog.game.exception.GameSeriesStatusErrorException;
import com.winterframework.firefrog.game.exception.GameTotbetsErrorException;
import com.winterframework.firefrog.game.exception.LinkFundSystemErrorException;
import com.winterframework.firefrog.game.exception.MMCOpenAwardFailedException;
import com.winterframework.firefrog.game.exception.NoChooseBetAwardException;
import com.winterframework.firefrog.game.exception.UserBalErrorException;
import com.winterframework.firefrog.game.exception.UserGameAwardConfigErrorException;
import com.winterframework.firefrog.game.exception.UserSubmitBusyErrorException;
import com.winterframework.firefrog.game.exception.UserTopAgentBetException;
import com.winterframework.firefrog.game.lock.config.mongo.service.LockSelector;
import com.winterframework.firefrog.game.lock.config.mongo.service.LotteryLockService;
import com.winterframework.firefrog.game.service.IGameAwardService;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGameLevelRecycleService;
import com.winterframework.firefrog.game.service.IGameMultipleService;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.service.ITransactionRecordService;
import com.winterframework.firefrog.game.service.order.utlis.LhcCheckOrderUtils;
import com.winterframework.firefrog.game.util.JlDiceSlipViewMap;
import com.winterframework.firefrog.game.util.LHCUtil.BetTypeCodeMapping;
import com.winterframework.firefrog.game.web.dto.ActiveUserMigrate;
import com.winterframework.firefrog.game.web.dto.CancelOrderChargeRequest;
import com.winterframework.firefrog.game.web.dto.CancelOrderChargeResponse;
import com.winterframework.firefrog.game.web.dto.CancelOrderRequest;
import com.winterframework.firefrog.game.web.dto.DTOConvert;
import com.winterframework.firefrog.game.web.dto.DTOConvertor4Web;
import com.winterframework.firefrog.game.web.dto.FundTransactionStruc;
import com.winterframework.firefrog.game.web.dto.GameBetDetailTotAmountResponse;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailOperationsQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailOperationsQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameOrderOperationsQueryDTO;
import com.winterframework.firefrog.game.web.dto.GameOrderOperationsQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderOperationsQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameOrderQueryDTO;
import com.winterframework.firefrog.game.web.dto.GameOrderQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameOrderRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderResponeOverMutipleDTO;
import com.winterframework.firefrog.game.web.dto.GameOrderResponse;
import com.winterframework.firefrog.game.web.dto.GameOrderResponseDTO;
import com.winterframework.firefrog.game.web.dto.GamePlanParm;
import com.winterframework.firefrog.game.web.dto.OrdersStruc;
import com.winterframework.firefrog.game.web.dto.QueryLevelRecycleHistoryResponse;
import com.winterframework.firefrog.game.web.dto.SlipsStruc;
import com.winterframework.firefrog.game.web.dto.UserBetInfoQueryRequest;
import com.winterframework.firefrog.game.web.dto.UserBetInfoSumStruc;
import com.winterframework.firefrog.shortlived.mmcRanking.service.IMMCRankingService;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IActivityConfigService;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.modules.web.util.RequestContext;

/**
 * 游戏订单Controller
 * @author Denny
 * @date 2013-7-22 上午9:37:52
 */
@Controller("gameOrderController")
@RequestMapping("/game")
public class GameOrderController {

	private Logger log = LoggerFactory.getLogger(GameOrderController.class);
	
	private static Long lhc = 99701L;

	@Resource(name = "gameOrderServiceImpl")
	private IGameOrderService gameOrderService;

	@Resource(name = "gameMultipleServiceImpl")
	private IGameMultipleService gameMultipleService;

	@Resource(name = "RedisClient")
	private RedisClient rc;

	@Resource(name = "gameSeriesDaoImpl")
	private IGameSeriesDao gameSeriesDaoImpl;
	private String lastUserPlayGamePR = "lastUserPlayGamePR____";

	@Resource(name = "gameTransactionRecordServiceImpl")
	private ITransactionRecordService gameTransactionRecordService;

	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueServiceImpl;

	@Resource(name = "gamePointDaoImpl")
	private IGamePointDao gamePointDaoImpl;

	@Resource(name = "gamePackageDao")
	private IGamePackageDao gamePackageDaoImpl;
	
	@Autowired
	private LockSelector selector;

	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao userCustomerDao;

	@Resource(name = "gameWarnOrderDaoImpl")
	private IGameWarnOrderDao gameWarnOrderDao;

	/**投注到活动前缀*/
	@PropertyConfig(value = "url.baseFundUrl")
	private String activityUrl;

	/**投注到活动接口 URL*/
	@PropertyConfig(value = "url.activity.migrate")
	private String migrateUrl;

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;
	@Resource(name = "gameBizLockService")
	private GameBizLockService gameBizLockService;
	
	@Resource(name = "gameLevelRecycleServiceImpl")
	private IGameLevelRecycleService gameLevelRecycleService;
	@Resource(name = "gameAwardServiceImpl")
	private IGameAwardService gameAwardService;
	
	@Resource(name="LhcCheckOrderUtils")
	private LhcCheckOrderUtils lhcCheckOrderUtils;
	
	@Resource(name = "activityConfigServiceImpl")
	private IActivityConfigService activityConfigServiceImpl;
	
	@Resource(name="mmcRankingService")
	private IMMCRankingService mmcRankingServiceImpl;

	/**
	 * 判断当前用户是否登录用户下级
	 * @param queryUser
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	private Boolean isCanSelectOrder(Long queryUser, Long loginUser) throws Exception {
		User user = userCustomerDao.queryUserById(queryUser);
		User loUser = userCustomerDao.queryUserById(loginUser);
		String userChain = user.getUserProfile().getUserChain();
		List<String> accounts = Arrays.asList(userChain.replaceFirst("/", "").split("/"));
		if (accounts.contains(loUser.getUserProfile().getAccount())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 查询订单列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryGameOrdersWithSlip")
	@ResponseBody
	public Response<List<GameOrderDetailQueryResponse>> queryGameOrdersWithSlip(
			@RequestBody Request<GameOrderQueryRequest> request) throws Exception {
		log.debug("开始查询订单列表......");

		Response<List<GameOrderDetailQueryResponse>> response = new Response<List<GameOrderDetailQueryResponse>>(
				request);
		try {
			GameOrderQueryDTO queryDTO = new GameOrderQueryDTO();
			queryDTO.setQueryParam(request.getBody().getParam());

			PageRequest<GameOrderQueryDTO> pr = PageConverterUtils.getRestPageRequest(1, Integer.MAX_VALUE);
			pr.setSearchDo(queryDTO);
			pr.setSortColumns("SALE_TIME desc");
			Page<GameOrder> page = gameOrderService.queryOrders(pr);
			List<GameOrder> orders = page.getResult();
			if (orders != null && orders.size() > 0) {
				List<GameOrderDetailQueryResponse> resultList = new ArrayList<GameOrderDetailQueryResponse>();
				for (GameOrder go : orders) {
					OrdersStruc orderStruc = DTOConvert.gameOrder2OrdersStruc(go);
					GameOrderDetailQueryResponse queryResult = new GameOrderDetailQueryResponse();
					queryResult.setOrdersStruc(orderStruc);
					List<GameSlip> gods = gameOrderService.querySlipsByOrderId(orderStruc.getOrderId());
					List<SlipsStruc> sss = new ArrayList<SlipsStruc>();
					for (GameSlip god : gods) {
						sss.add(DTOConvert.gameOrderDetail2SlipStruc(god));
					}
					queryResult.setSlipsStruc(sss);
					resultList.add(queryResult);
				}
				response.setResult(resultList);
			}
		} catch (Exception e) {
			log.error("查询订单列表异常 ", e);
			throw e;
		}
		return response;
	}

	/**
	 * 4.8 订单查询
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryOrders")
	@ResponseBody
	public Response<GameOrderQueryResponse> queryOrders(
			@RequestBody @ValidRequestHeader @ValidRequestBody Request<GameOrderQueryRequest> request) throws Exception {

		log.debug("开始查询订单列表......");
		Response<GameOrderQueryResponse> response = new Response<GameOrderQueryResponse>(request);
		List<OrdersStruc> ordersStruc = new ArrayList<OrdersStruc>();
		GameOrderQueryResponse result = new GameOrderQueryResponse();
		long userId = request.getHead().getUserId();
		if (0L == userId) {
			userId = RequestContext.getCurrUser().getId();
		} else {
			// 判断查看的用户是否为登录用户的下级
			Long loginUser = request.getBody().getParam().getSysUserId();
			if (loginUser != null) {
				if (!this.isCanSelectOrder(userId, loginUser)) {
					result.setOrdersStruc(ordersStruc);
					response.setResult(result);
					return response;
				}
			}
		}
		int sNo = request.getBody().getPager().getStartNo();
		int eNo = request.getBody().getPager().getEndNo();

		//查询一代回收记录,加入搜寻条件
		QueryLevelRecycleHistoryResponse recycleHist = gameLevelRecycleService.queryRecycleLastHistory(userId);
		if(recycleHist!=null){
			request.getBody().getParam().setRecycleDate(recycleHist.getActivityDate());			
		}
		
		GameOrderQueryDTO queryDTO = new GameOrderQueryDTO();
		queryDTO.setUserId(userId);
		queryDTO.setQueryParam(request.getBody().getParam());		

		PageRequest<GameOrderQueryDTO> pr = PageConverterUtils.getRestPageRequest(sNo, eNo);
		pr.setSearchDo(queryDTO);
		pr.setSortColumns("SALE_TIME desc");
		Page<GameOrder> page = null;
		List<GameOrder> orders = null;

		OrdersStruc orderStruc = null;
		try {
			page = gameOrderService.queryOrders(pr);
			orders = page.getResult();
			if (orders != null && orders.size() > 0) {
				for (GameOrder go : orders) {
					orderStruc = DTOConvert.gameOrder2OrdersStruc(go);
					ordersStruc.add(orderStruc);
				}
			}
			result.setOrdersStruc(ordersStruc);
			response.setResult(result);
			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageFirstElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			response.setResultPage(pager);

		} catch (Exception e) {
			log.error("查询订单列表异常 ", e);
			throw e;
		}

		log.debug("查询订单列表完成。");
		return response;
	}

	/**
	 * 4.9 订单详情查询
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryOrderDetail")
	@ResponseBody
	public Response<GameOrderDetailQueryResponse> queryOrderDetail(
			@RequestBody @ValidRequestBody Request<GameOrderDetailQueryRequest> request) throws Exception {

		log.debug("开始查询订单详情......");
		Response<GameOrderDetailQueryResponse> response = new Response<GameOrderDetailQueryResponse>(request);
		Long orderId = request.getBody().getParam().getOrderId();
		String orderCode = request.getBody().getParam().getOrderCode();
		if (orderId == null) {
			com.winterframework.firefrog.game.dao.vo.GameOrder gameOrder = gameOrderService
					.getGameOrderByOrderCode(orderCode);
			orderId = gameOrder.getId();
		}
		GameOrderDetailQueryResponse godqr = new GameOrderDetailQueryResponse();
		OrdersStruc os = new OrdersStruc();
		SlipsStruc ss = new SlipsStruc();
		GameOrder goe;
		List<GameSlip> gods;
		Long userId = request.getHead().getUserId();
		if (0L == userId) {
			userId = null;
		} else {
			// 判断查看的用户是否为登录用户的下级
			Long loginUser = request.getBody().getParam().getSysUserId();
			if (loginUser != null) {
				if (!this.isCanSelectOrder(userId, loginUser)) {
					return response;
				}
			}

		}
		List<SlipsStruc> sss = new ArrayList<SlipsStruc>();
		try {
			goe = gameOrderService.queryOrder(orderId, userId);

			if (goe.getOrderCode() != null) {
				GameWarnOrder warnOrder = gameWarnOrderDao.getGameWarnOrderByOrderCode(goe.getOrderCode());
				os = DTOConvert.gameOrder2OrdersStruc(goe);
				//利用game_order的parentId查詢gamePakage 以利區分12生肖
				GamePackage gamePackage=gamePackageDaoImpl.queryGamePackageById(os.getParentid());
				if(os.getLotteryid()==99112l && gamePackage.getActivityType()==1l){
					os.setActivityType(1l);
				}
				if (warnOrder != null && warnOrder.getStatus() == 2l) {
					os.setStatus(7);
				}
				Map<String ,Object> map = new HashMap<String ,Object>();
				map.put("orderId",orderId);
				if(goe.getLottery().getLotteryId() == 99701){
					map.put("orderBy", "BET_TYPE_CODE");
				}
				gods = gameOrderService.querySlipsByMap(map);

				for (GameSlip god : gods) { 
					ss = DTOConvert.gameOrderDetail2SlipStruc(god);
					Long awardGroupId=null==goe.getAwardGroupId()?0L:goe.getAwardGroupId();
					
					GameAward award=gameAwardService.getValidByLotteryIdAndGroupIdAndBetTypeCode(goe.getLottery().getLotteryId(), awardGroupId, god.getGameBetType().getBetTypeCode());
					ss.setGroupAward(null==award?0L:award.getActualBonus());
					ss.setGroupAwardDown(null==award?0L:award.getActualBonusDown());
					if(0L==ss.getGroupAward()){						
						GameAward maxAward=gameAwardService.getMaxBonusAwardByLotteryIdAndGroupIdAndBetTypeCodeParent(goe.getLottery().getLotteryId(), awardGroupId, god.getGameBetType().getBetTypeCode());
						ss.setGroupAward(null==maxAward?0L:maxAward.getActualBonus());
						ss.setGroupAwardDown(null==maxAward?0L:maxAward.getActualBonusDown());
						//設定六合彩多組獎金模式
						if(maxAward.getLhcMultBonus()!=null && maxAward.getLhcMultBonus().size()>0){
							ss.setLhcMultBonus(maxAward.getLhcMultBonus());
						}
					}
					List<GamePoint> gamePoints = gamePointDaoImpl.getGamePointsBySlipId(ss.getSlipid());
					if (gamePoints != null && !gamePoints.isEmpty()) {
						List<GamePoint> resultGamePoints = new ArrayList<GamePoint>();
						Long lotteryId = goe.getLottery().getLotteryId();
						for (GamePoint gamePoint : gamePoints) {
							//擋3D,P3/P5組選和值 豹子號
							if(lotteryId.intValue() == 99109 || lotteryId.intValue() == 99108){
								if(ss.getGameSetCode().intValue() == 11 && ss.getBetMethodCode().intValue() == 33){
									getResultPointsByLF(gamePoint, resultGamePoints, ss.getGameGroupCode(),ss.getGameSetCode(),ss.getBetMethodCode());
								}else{
							getResultPoints(gamePoint, resultGamePoints);
						}
							} else {
								getResultPoints(gamePoint, resultGamePoints);
							}
								
						ss.setGamePoints(resultGamePoints);
					}
					}
					sss.add(ss);
				}
				godqr.setOrdersStruc(os);
				godqr.setSlipsStruc(sss);
				godqr.setPlanId(gameOrderService.getPlanIdByOrder(os.getOrderId()));
				response.setResult(godqr);
			}
		} catch (Exception e) {
			log.error("查询订单详情异常 ", e);
			throw e;
		}

		log.debug("查询订单详情完成。");
		return response;
	}
	
	private void getResultPoints(GamePoint point, List<GamePoint> resultGamePoints) {
		if (resultGamePoints.isEmpty()) {
			resultGamePoints.add(point);
			return;
		}
		boolean isContain = false;
		for (GamePoint resultGamePoint : resultGamePoints) {
			if (point.getRetValue().equals(resultGamePoint.getRetValue())
					&& point.getMult() == resultGamePoint.getMult().longValue()) {
				resultGamePoint.setPoint(resultGamePoint.getPoint() + "," + point.getPoint());
				isContain = true;
				break;
			}
		}
		if (!isContain) {
			resultGamePoints.add(point);
		}

	}
	private boolean checkIsLeopard(String point){
		if(point.length() == 3){
			if(point.charAt(0) == point.charAt(1) && point.charAt(0) == point.charAt(2)){
				return true;
			}
		}
		if(point.length() == 2){
			if(point.charAt(0) == point.charAt(1)){
				return true;
			}
		}
		return false;
	}

	private void getResultPointsByLF(GamePoint point, List<GamePoint> resultGamePoints,Integer gameGroupCode,Integer gameSetCode,Integer betMethodCode) {
		if (resultGamePoints.isEmpty()) {
			resultGamePoints.add(point);
			return;
		}
		boolean isContain = false;
		for (GamePoint resultGamePoint : resultGamePoints) {
			if (point.getRetValue().equals(resultGamePoint.getRetValue())
					&& point.getMult() == resultGamePoint.getMult().longValue()) {
				if(!checkIsLeopard(point.getPoint())){
					resultGamePoint.setPoint(resultGamePoint.getPoint() + "," + point.getPoint());
				}
				isContain = true;
				break;
			}
		}
		if (!isContain) {
			resultGamePoints.add(point);
		}

	}

	private Integer getMultipleAssisCode(GamePackageItem betDetail, List<BetLimitAssist> list, Integer mutiple) {
		Integer minMultiplte = mutiple;
		if (betDetail.getGameBetType().getBetTypeCode().equals("34_28_71")) {
			String betDetialCode = betDetail.getBetDetail();
			List<Integer> codes = new ArrayList<Integer>();
			String[] betDetialCodes = betDetialCode.split(",");
			for (int i = 0; i < betDetialCodes.length; i++) {
				if (Integer.valueOf(betDetialCodes[i]) <= 10) {
					codes.add(Integer.valueOf(betDetialCodes[i]) + 48);
				} else {
					codes.add(21 - Integer.valueOf(betDetialCodes[i]) + 48);
				}
			}
			for (Integer code : codes) {
				for (int j = 0; j < list.size(); j++) {
					BetLimitAssist betLimitAssist = list.get(j);
					if (betLimitAssist.getAssitCode().intValue() == code) {
						minMultiplte = betLimitAssist.getMaxMultiple().intValue();
					}
				}
			}
		}
		return minMultiplte;
	}

	/**
	 * 4.3 方案投注
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveGameOrder")
	@ResponseBody
	public Response<GameOrderResponse> saveGameOrder(
			@RequestBody @ValidRequestHeader @ValidRequestBody Request<GameOrderRequest> request) throws Exception {

		Response<GameOrderResponse> response = new Response<GameOrderResponse>(request);

		long userId = request.getHead().getUserId();
		String key = "userBetTime_userId1" + userId;
		long lotteryId = 0;
		boolean isgameplan = true;	
		try {

			// 我们服务端要判断用户在100毫秒之内只能有一个订单这个判断不是通过数据库实现，是通过redis来实现
			Long thisTime = new Date().getTime();
			
			log.info("--------------用户提交投注：userId=" + userId + "----" + thisTime);
			if (!rc.acquireLock(key, 70000)) {
				log.info("用户提交投注过于频繁：userId=" + userId);
				isgameplan = false;
				throw new UserSubmitBusyErrorException();
			} else {
				
				if (null != request.getBody()) {
	
					GameOrderRequest gameOrderRequest = request.getBody().getParam();
					GameOrderResponse gameOrderResponse = new GameOrderResponse();
					GameOrder gameOrder = DTOConvert.convertGameOrderRequest2GameOrder(gameOrderRequest, userId);
					lotteryId = gameOrder.getLottery().getLotteryId();
	
					// 判读该彩种是否已经停售
					GameSeries series = gameSeriesDaoImpl.getByLotteyId(lotteryId);
					if (series != null) {
						if (series.getStatus().intValue() == 0) {
							throw new GameSeriesStatusErrorException();
						}
					}
					// 验证
					List<GameOrderResponeOverMutipleDTO> overMutipleDTOs = new ArrayList<GameOrderResponeOverMutipleDTO>();
					Map<String,GamePackageItem> betTypeMap = new HashMap<String,GamePackageItem>();
					for(GamePackageItem betDetail : gameOrder.getGamePackage().getItemList()){
						String betCodeKey = betDetail.getGameBetType().getBetTypeCode()+"_"+betDetail.getBetDetail();
						Double betDetailMutiple = (lotteryId == 99601||lotteryId ==99602||lotteryId ==99603) ? betDetail.getTotamount() / 2000 : Double
								.valueOf(betDetail.getMultiple().toString());
						Integer currentIssueMutiple = 0;
						if (betDetail.getFileMode().getValue() == 1) {
							currentIssueMutiple = gameOrderService.getCurrentIssueMutipleFile(
									betDetail.getBetDetail(), betDetail.getGameBetType().getBetTypeCode(), userId,
									lotteryId, gameOrderRequest.getIssueCode());
						} else {
							currentIssueMutiple = gameOrderService.getCurrentIssueMutiple(betDetail.getBetDetail(),
									betDetail.getGameBetType().getBetTypeCode(), userId, lotteryId,
									gameOrderRequest.getIssueCode());
						}
						if (currentIssueMutiple == null) {
							currentIssueMutiple = 0;
						}
						if(betTypeMap.get(betCodeKey) == null){
							
							GamePackageItem value = new GamePackageItem();
							value.setBetDetail(betDetail.getBetDetail());
							value.setGameBetType(betDetail.getGameBetType());
							value.setMoneyMode(betDetail.getMoneyMode());
							if(betDetail.getMoneyMode().getValue() == 1){
								value.setMultiple(Double.valueOf(betDetailMutiple*10+"").intValue());
							}else if(betDetail.getMoneyMode().getValue() == 3){
								value.setMultiple(Double.valueOf(betDetailMutiple/10+"").intValue());
							}else{
								value.setMultiple(Double.valueOf(betDetailMutiple+"").intValue());
							}
							value.setMutliple(currentIssueMutiple);
							value.setFileMode(betDetail.getFileMode());
							betTypeMap.put(betCodeKey, value);
							
						}else{
							GamePackageItem value = betTypeMap.get(betCodeKey);
							if(betDetail.getMoneyMode().getValue() == 1){
								value.setMultiple(value.getMultiple().intValue()+Double.valueOf(betDetailMutiple*10+"").intValue());
							}else if(betDetail.getMoneyMode().getValue() == 3){
								value.setMultiple(value.getMultiple().intValue()+Double.valueOf(betDetailMutiple/10+"").intValue());
							}else{
								value.setMultiple(value.getMultiple().intValue()+Double.valueOf(betDetailMutiple+"").intValue());
							}
							value.setMutliple(currentIssueMutiple);
						}
					}
					
	
					for(Entry<String,GamePackageItem> entry:betTypeMap.entrySet()){
						GamePackageItem betDetail = entry.getValue();
						
						
						// 除倍数限制验证外，其他验证都移至web中做
						GameMultipleEntity entity = new GameMultipleEntity();
						GameBetType betType = betDetail.getGameBetType();
						entity.setBetMethodCode(betType.getBetMethodCode());
						entity.setGameGroupCode(betType.getGameGroupCode());
						entity.setGameSetCode(betType.getGameSetCode());
						Lottery lottery = new Lottery();
						lottery.setLotteryId(lotteryId);
						entity.setLottery(lottery);
						GameMultipleEntity queryGameMultiple = gameMultipleService.queryGameMultiple(entity);
						Integer mutiple = queryGameMultiple.getMaxMultiple();
						String specialMultiple = queryGameMultiple.getSpecialMultiple();
						boolean isNotSpecial = true;
						if(null != specialMultiple){
							mutiple = 0;
							isNotSpecial = false;
							String[] specialMultipleDetail = specialMultiple.split(",");
							String[] betDetails = betDetail.getBetDetail().split("\\|");
							Map<String, Integer> specialMultipleMap = new HashMap<String, Integer>();
							specialMultipleMap.put("5单0双", Integer.valueOf(specialMultipleDetail[0]));
							specialMultipleMap.put("4单1双", Integer.valueOf(specialMultipleDetail[1]));
							specialMultipleMap.put("3单2双", Integer.valueOf(specialMultipleDetail[2]));
							specialMultipleMap.put("2单3双", Integer.valueOf(specialMultipleDetail[3]));
							specialMultipleMap.put("1单4双", Integer.valueOf(specialMultipleDetail[4]));
							specialMultipleMap.put("0单5双", Integer.valueOf(specialMultipleDetail[5]));
							for(int i=0;i<betDetails.length;i++){
								Integer _multipleTmp = specialMultipleMap.get(betDetails[i]);
								if(mutiple >= _multipleTmp || mutiple == 0){
									mutiple = _multipleTmp;
								}else{
									continue;
								}
							}
							List<BetLimitAssist> list = gameMultipleService.getGameMultipleAssistParentId(queryGameMultiple
									.getId());
							if (list != null && !list.isEmpty()) {
								mutiple = this.getMultipleAssisCode(betDetail, list, mutiple);
							}
		
							if (mutiple != -1) {// -1 为无限制 当为角模式时 倍数限制应为元模式*10
								Integer betDetailMutiple = betDetail.getMultiple();
								Integer currentIssueMutiple = 0;
									if (betDetailMutiple > mutiple * 10) {
										GameOrderResponeOverMutipleDTO dto = new GameOrderResponeOverMutipleDTO();
										dto.setBetDetail(betDetail.getBetDetail());
										dto.setBetTypeCode(betDetail.getGameBetType().getBetTypeCode());
										dto.setGameIssueCode(gameOrderRequest.getIssueCode());
										GameIssueEntity issueEntity = gameIssueServiceImpl.queryGameIssue(gameOrder
												.getLottery().getLotteryId(), gameOrderRequest.getIssueCode());
										dto.setWebIssueCode(issueEntity.getWebIssueCode());
										Integer temp = mutiple * 10 - currentIssueMutiple;
										dto.setMultiple(temp < 0 ? 0 : temp);
										dto.setMoneyunit((lotteryId == 99601 || lotteryId==99602|| lotteryId==99603)? "1" : "0.1");
										
										overMutipleDTOs.add(dto);
									}
								if (!overMutipleDTOs.isEmpty()) {
									response.getHead().setStatus(202005);
									gameOrderResponse.setOverMutipleDTO(overMutipleDTOs);
									response.setResult(gameOrderResponse);
									return response;
								}
								currentIssueMutiple = betDetail.getMutliple();
								if (betDetailMutiple > (mutiple * 10 - currentIssueMutiple)) {
									GameOrderResponeOverMutipleDTO dto = new GameOrderResponeOverMutipleDTO();
									dto.setBetDetail(betDetail.getBetDetail());
									dto.setBetTypeCode(betDetail.getGameBetType().getBetTypeCode());
									dto.setGameIssueCode(gameOrderRequest.getIssueCode());
									GameIssueEntity issueEntity = gameIssueServiceImpl.queryGameIssue(gameOrder
											.getLottery().getLotteryId(), gameOrderRequest.getIssueCode());
									dto.setWebIssueCode(issueEntity.getWebIssueCode());
									Integer temp = mutiple * 10 - currentIssueMutiple;
									dto.setMultiple(temp < 0 ? 0 : temp);
									dto.setMoneyunit("0.1");
									
									overMutipleDTOs.add(dto);
								}
							}
						}else if (null != mutiple && isNotSpecial) {
							List<BetLimitAssist> list = gameMultipleService.getGameMultipleAssistParentId(queryGameMultiple
									.getId());
							if (list != null && !list.isEmpty()) {
								mutiple = this.getMultipleAssisCode(betDetail, list, mutiple);
							}
		
							if (mutiple != -1) {// -1 为无限制 当为角模式时 倍数限制应为元模式*10
								Integer betDetailMutiple = betDetail.getMultiple();
								Integer currentIssueMutiple = 0;
									if (betDetailMutiple > mutiple * 10) {
										GameOrderResponeOverMutipleDTO dto = new GameOrderResponeOverMutipleDTO();
										dto.setBetDetail(betDetail.getBetDetail());
										dto.setBetTypeCode(betDetail.getGameBetType().getBetTypeCode());
										dto.setGameIssueCode(gameOrderRequest.getIssueCode());
										GameIssueEntity issueEntity = gameIssueServiceImpl.queryGameIssue(gameOrder
												.getLottery().getLotteryId(), gameOrderRequest.getIssueCode());
										dto.setWebIssueCode(issueEntity.getWebIssueCode());
										Integer temp = mutiple * 10 - currentIssueMutiple;
										dto.setMultiple(temp < 0 ? 0 : temp);
										dto.setMoneyunit((lotteryId == 99601 || lotteryId==99602|| lotteryId==99603)? "1" : "0.1");
										
										overMutipleDTOs.add(dto);
									}
								if (!overMutipleDTOs.isEmpty()) {
									response.getHead().setStatus(202005);
									gameOrderResponse.setOverMutipleDTO(overMutipleDTOs);
									response.setResult(gameOrderResponse);
									return response;
								}
								currentIssueMutiple = betDetail.getMutliple();
								if (betDetailMutiple > (mutiple * 10 - currentIssueMutiple)) {
									GameOrderResponeOverMutipleDTO dto = new GameOrderResponeOverMutipleDTO();
									dto.setBetDetail(betDetail.getBetDetail());
									dto.setBetTypeCode(betDetail.getGameBetType().getBetTypeCode());
									dto.setGameIssueCode(gameOrderRequest.getIssueCode());
									GameIssueEntity issueEntity = gameIssueServiceImpl.queryGameIssue(gameOrder
											.getLottery().getLotteryId(), gameOrderRequest.getIssueCode());
									dto.setWebIssueCode(issueEntity.getWebIssueCode());
									Integer temp = mutiple * 10 - currentIssueMutiple;
									dto.setMultiple(temp < 0 ? 0 : temp);
									dto.setMoneyunit("0.1");
									
									overMutipleDTOs.add(dto);
								}
							}
						}
					}
					if (!overMutipleDTOs.isEmpty()) {
						response.getHead().setStatus(202005);
						gameOrderResponse.setOverMutipleDTO(overMutipleDTOs);
						response.setResult(gameOrderResponse);
						return response;
					}
					
					//LHCTODO 六合彩進行單一賠率驗證待移除
					if(lotteryId == 99701){
						Boolean lhcCheck = true;
						List<GameSlip> slips= gameOrder.getSlipList();
						for(GameSlip slip:slips){
							String _betTypeCode = slip.getGameBetType().getBetTypeCode();
							if(BetTypeCodeMapping.lhc_54_19_84.name().contains(_betTypeCode)||
							   BetTypeCodeMapping.lhc_54_19_85.name().contains(_betTypeCode)||
							   BetTypeCodeMapping.lhc_54_37_83.name().contains(_betTypeCode)||
							   BetTypeCodeMapping.lhc_54_18_82.name().contains(_betTypeCode)) {
								lhcCheck = lhcCheckOrderUtils.checkFunBetTypeSingleWin(slip);
								break;
							}
						}
						//賠率異常
						if(!lhcCheck){
							throw new GameBetContentPatternErrorException();
						}
					}
					
					LotteryLockService lockService = selector.getRealService(lotteryId);
					if (lockService != null) {
						boolean isLock = lockService.haveNoLockAndChange(gameOrder, userId);
						response.setResult(gameOrderResponse);
						if (isLock) {
							log.debug("需要封锁变价。。。。。。");
							response.getHead().setStatus(205000L);
							GameOrderResponseDTO dto = DTOConvert.convertGameOrderToDTO(gameOrder);
							List<GamePlanParm> planParam = dto.getGamePlanParm();
							for (GamePlanParm gamePlanParm : planParam) {
								GameIssueEntity issueEntity = gameIssueServiceImpl.queryGameIssue(gameOrder.getLottery()
										.getLotteryId(), gamePlanParm.getIssueCode());
								gamePlanParm.setNumber(issueEntity.getWebIssueCode());
								dto.setWebIssueCode(issueEntity.getWebIssueCode());
							}
							gameOrderResponse.setGameOrderDTO(dto);
							return response;
						}
					} else {
						log.debug("不需要封锁变价处理。。。。。。");
					}
					Long gameOrderId = gameOrderService.saveGameOrder(gameOrder);
					if (lotteryId == 99112||lotteryId==99306||lotteryId == 99113) {
	
						try {
							boolean res = gameOrderService.mmcOpenAward(gameOrderId);
							if (!res) {// 假如失败，将订单状态设置为已撤销，显示给用户开奖失败
								boolean res1 = gameOrderService.mmcOpenAward(gameOrderId);
								if (!res1) {
									log.error("秒秒彩开奖错误,重试开奖，链接资金中心异常重试1：orderId=" + gameOrderId);
									
									log.error("秒秒彩重试开奖错误，资金链接异常撤销订单1：orderId=" + gameOrderId);
									gameOrderService.cancelOrderMMC(gameOrderId, new Date().getTime(), userId, false);
									log.error("秒秒彩重试开奖错误，刪除S1：orderId=" + gameOrderId);
									gameOrderService.deleteCancelOrderMMC(gameOrderId, new Date().getTime(), userId, false);
									log.error("秒秒彩重试开奖错误，刪除E1：orderId=" + gameOrderId);
									gameOrderResponse.setOrderId(gameOrderId);
									response.setResult(gameOrderResponse);
									return response;
								}
							}
							try {
								ActivityConfig config = activityConfigServiceImpl.getById(152l);
								Date nowDate = new Date();
								if (config!=null && !nowDate.before(config.getBeginTime())&& !nowDate.after(config.getEndTime())) {
									log.info("開始更新秒秒風雲榜");
									mmcRankingServiceImpl.isKingDay(gameOrderId,gameOrder.getOrderCode());
								}
	
							} catch (Exception e) {
								log.error("秒秒風雲榜異常:"+e);
								throw e;
							}
						} catch (Exception e) {
							log.error("秒秒彩开奖错误,重试开奖，链接资金中心异常重试2：orderId=" + gameOrderId);
							
							log.error("秒秒彩重试开奖错误，资金链接异常撤销订单2：orderId=" + gameOrderId);
							gameOrderService.cancelOrderMMC(gameOrderId, new Date().getTime(), userId, false);
							
							log.error("秒秒彩重试开奖错误，刪除S2：orderId=" + gameOrderId);
							gameOrderService.deleteCancelOrderMMC(gameOrderId, new Date().getTime(), userId, false);
							log.error("秒秒彩重试开奖错误，刪除E2：orderId=" + gameOrderId);
							
							gameOrderResponse.setOrderId(gameOrderId);
							response.setResult(gameOrderResponse);
							return response;
						}
	
						try {
							log.info("---活动订单导出开始----");
							ActiveUserMigrate aum = new ActiveUserMigrate();
							aum.setId(userId);
							aum.setBetTime(gameOrder.getSaleTime());
							httpClient.invokeHttp(activityUrl + migrateUrl, aum, ActiveUserMigrate.class);
							log.info("---活动订单导出结束----");
						} catch (Exception e) {
							log.error("---活动订单导出异常----", e);
						}
					}
					gameOrderResponse.setOrderId(gameOrderId);
					//增加order的返回
					GameOrderResponseDTO dto = new GameOrderResponseDTO();
					dto.setWebIssueCode(gameOrder.getGameIssue().getWebIssueCode());
					dto.setOrderCode(gameOrder.getOrderCode());
					dto.setSaleTime(DateUtils.format(gameOrder.getSaleTime(), DateUtils.DATETIME_FORMAT_PATTERN));
					dto.setTotalAmount(gameOrder.getTotalAmount());
					dto.setOrderId(gameOrderId);
					gameOrderResponse.setGameOrderDTO(dto);
					response.setResult(gameOrderResponse);
	
					// 保存最后一次游戏类型
					rc.set(lastUserPlayGamePR + String.valueOf(userId), String.valueOf(lotteryId));
				}
			}
		} catch (GameBetContentPatternErrorException e) {
			log.error("投注格式错误：", e);
			response.getHead().setStatus(e.getStatus());
		} catch (GameBetAmountErrorException e) {
			log.error("投注金额错误：", e);
			response.getHead().setStatus(e.getStatus());
		} catch (GameTotbetsErrorException e) {
			log.error("投注注数错误：", e);
			response.getHead().setStatus(e.getStatus());
		} catch (GameBetOverMultipleLimitException e) {
			log.error("投注倍数超出限制错误：", e);
			response.getHead().setStatus(e.getStatus());
		} catch (GameIssueStatusErrorException e) {
			log.error("当前奖期已停止销售：", e);
			response.getHead().setStatus(e.getStatus());
		} catch (GameBetMethodStatusStopException e) {
			log.error("当前投注方式已停止销售：", e);
			response.getHead().setStatus(e.getStatus());
		} catch (UserBalErrorException e) {
			log.error("余额不足：", e);
			response.getHead().setStatus(e.getStatus());
		} catch (UserTopAgentBetException e) {
			log.error("总代不能投注：", e);
			response.getHead().setStatus(e.getStatus());
		} catch (UserGameAwardConfigErrorException e) {
			log.error("用户奖金组数据配置错误：", e);
			response.getHead().setStatus(e.getStatus());
		} catch (LinkFundSystemErrorException e) {
			log.error("连接资金系统错误：", e);
			response.getHead().setStatus(e.getStatus());
		} catch (GameSeriesStatusErrorException e) {
			log.error("当前彩种已停售：", e);
			response.getHead().setStatus(e.getStatus());
		} catch (GameIssueStatusStopSaleException e) {
			log.error("当前奖期已停止销售：", e);
			response.getHead().setStatus(e.getStatus());
		} catch (NoChooseBetAwardException e) {
			log.error("未选择投注奖金组：", e);
			response.getHead().setStatus(e.getStatus());
		} catch (MMCOpenAwardFailedException e) {
			log.error("秒秒彩开奖异常：", e);
			response.getHead().setStatus(e.getStatus());
		} catch (GameIssueNotExistErrorException e) {
			log.error("奖期不存在：", e);
			response.getHead().setStatus(e.getStatus());
		} catch (UserSubmitBusyErrorException e) {
			log.error("用户提交过于频繁：", e);
			response.getHead().setStatus(e.getStatus());
		} catch (Exception e) {
			log.error("下注操作失败：", e);
			response.getHead().setStatus(99998L);
			throw e;
		} finally{
			//完成後解除鎖定
			if (isgameplan){
				rc.releaseLock(key);
			}
		}

		return response;
	}

	/**
	 * 4.5 订单撤销
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cancelOrder")
	@ResponseBody
	public Object cancelOrder(@RequestBody @ValidRequestHeader @ValidRequestBody Request<CancelOrderRequest> request)
			throws Exception {
		@SuppressWarnings("rawtypes")
		Response response = new Response(request);
		String key = "stoporder_userId" + request.getHead().getUserId();
		boolean iscancelorder = true;
		try {
			boolean isFrontUser = true;
			if (request.getBody().getParam().getUserId() == null) {
				isFrontUser = false;
			}
			if (!rc.acquireLock(key, 70000)) {
				log.info("用户提交订单撤销过于频繁：userid = " + request.getHead().getUserId() + "orderid =" + request.getBody().getParam().getOrderId());
				iscancelorder = false;
				throw new Exception();
			}else{
				gameOrderService.cancelOrder(request.getBody().getParam().getOrderId(), request.getBody().getParam()
						.getCancelTime(), request.getHead().getUserId(), isFrontUser);
			}
			
		} catch (GameCancalOutTimeErrorException e) {
			log.warn("超过订单允许撤销时间：", e);
			response.getHead().setStatus(e.getStatus());
		} catch (GameIssueStatusErrorException e) {
			log.error("奖期状态错误：", e);
			response.getHead().setStatus(e.getStatus());
		} catch (GameOrderStatusErrorException e) {
			log.error("订单撤销时订单状态错误：", e);
			response.getHead().setStatus(e.getStatus());
		} catch (LinkFundSystemErrorException e) {
			log.error("链接资金错误：", e);
			response.getHead().setStatus(e.getStatus());
		} catch (GameCancelOrderPermitErrorException e) {
			log.error("用户撤销权限错误：", e);
			response.getHead().setStatus(e.getStatus());
		} catch (Exception e) {
			log.error("撤销投注操作失败：", e);
			throw e;
		} finally{
			if (iscancelorder){
				rc.releaseLock(key);
			}
		}
		return response;
	}

	/**
	 * 获取投注金额的撤单金额 数据已处理 直接显示即可
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/handlingCharge")
	@ResponseBody
	public Object getCancelOrderCharge(@RequestBody @ValidRequestBody Request<CancelOrderChargeRequest> request)
			throws Exception {
		Response<CancelOrderChargeResponse> response = new Response<CancelOrderChargeResponse>();
		CancelOrderChargeResponse cancelOrderChargeResponse = new CancelOrderChargeResponse();
		try {
			Double amount = gameOrderService.getWebCancelOrderCharge(request.getBody().getParam().getLotteryId(),
					request.getBody().getParam().getTotalBetAmount());
			cancelOrderChargeResponse.setHandlingCharge(amount);
			response.setResult(cancelOrderChargeResponse);
		} catch (Exception e) {
			log.error("获取撤单金额失败：", e);
			throw e;
		}
		return response;
	}

	/**
	 * 订单记录运营查询
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryOrderOperations")
	@ResponseBody
	public Response<GameOrderOperationsQueryResponse> queryOrderOperations(
			@RequestBody @ValidRequestBody Request<GameOrderOperationsQueryRequest> request) throws Exception {

		log.debug("query order operations start...");
		Response<GameOrderOperationsQueryResponse> response = new Response<GameOrderOperationsQueryResponse>(request);
		long userId = request.getHead().getUserId();
		int sNo = request.getBody().getPager().getStartNo();
		int eNo = request.getBody().getPager().getEndNo();

		GameOrderOperationsQueryDTO queryDTO = new GameOrderOperationsQueryDTO();
		queryDTO.setUserid(userId);
		queryDTO.setQueryRequest(request.getBody().getParam());

		PageRequest<GameOrderOperationsQueryDTO> pr = PageConverterUtils.getRestPageRequest(sNo, eNo);
		pr.setSearchDo(queryDTO);
		pr.setSortColumns(DTOConvert.convert2OrderSortType(queryDTO.getQueryRequest().getSortType()));

		Page<GameOrderOperationsEntity> page = null;
		List<GameOrderOperationsEntity> orders = null;
		List<OrdersStruc> ordersStruc = new ArrayList<OrdersStruc>();
		GameOrderOperationsQueryResponse result = new GameOrderOperationsQueryResponse();
		OrdersStruc orderStruc = null;
		try {
			page = gameOrderService.queryOrderOperations(pr);
			orders = page.getResult();
			if (orders != null && orders.size() > 0) {
				for (GameOrderOperationsEntity go : orders) {
					orderStruc = DTOConvert.gameOrderOperationsEntity2OrdersStruc(go);
					ordersStruc.add(orderStruc);
				}
			}
			result.setOrdersStrucs(ordersStruc);
			response.setResult(result);
			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageFirstElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			response.setResultPage(pager);

		} catch (Exception e) {
			log.error("query order operations error", e);
			throw e;
		}

		log.info("query order operations end...");
		return response;
	}

	/**
	 * 查询出订单记录列表(用于导出excel)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryOrderOperationsListForExport")
	@ResponseBody
	public Response<GameOrderOperationsQueryResponse> queryOrderOperationsListForExport(
			@RequestBody @ValidRequestHeader @ValidRequestBody Request<GameOrderOperationsQueryRequest> request)
			throws Exception {

		log.debug("query order operations for export start...");
		Response<GameOrderOperationsQueryResponse> response = new Response<GameOrderOperationsQueryResponse>(request);
		long userId = request.getHead().getUserId();

		GameOrderOperationsQueryDTO queryDTO = new GameOrderOperationsQueryDTO();
		queryDTO.setUserid(userId);
		queryDTO.setQueryRequest(request.getBody().getParam());

		List<GameOrderOperationsEntity> orders = null;
		List<OrdersStruc> ordersStruc = new ArrayList<OrdersStruc>();
		GameOrderOperationsQueryResponse result = new GameOrderOperationsQueryResponse();
		OrdersStruc orderStruc = null;
		try {
			orders = gameOrderService.queryOrderOperationsList(queryDTO);
			if (orders != null && orders.size() > 0) {
				for (GameOrderOperationsEntity go : orders) {
					orderStruc = DTOConvert.gameOrderOperationsEntity2OrdersStruc(go);
					ordersStruc.add(orderStruc);
				}
			}
			result.setOrdersStrucs(ordersStruc);

			response.setResult(result);
		} catch (Exception e) {
			log.error("query order operations for export error", e);
			throw e;
		}

		log.info("query order operations for export end...");
		return response;
	}

	/**
	 * 查询订单记录详情
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryOrderDetailOperations")
	@ResponseBody
	public Response<GameOrderDetailOperationsQueryResponse> queryOrderDetailOperations(
			@RequestBody @ValidRequestBody Request<GameOrderDetailOperationsQueryRequest> request) throws Exception {
		log.debug("query order detail operations start");

		Response<GameOrderDetailOperationsQueryResponse> response = new Response<GameOrderDetailOperationsQueryResponse>();
		GameOrderDetailOperationsQueryResponse result = new GameOrderDetailOperationsQueryResponse();
		// query condition
		GameOrderDetailOperationsQueryRequest requestParam = request.getBody().getParam();
		Long orderid = requestParam.getOrderid();
		com.winterframework.firefrog.game.dao.vo.GameOrder gameOrder = gameOrderService
				.getGameOrderByOrderCode(requestParam.getOrderCode());
		orderid = gameOrder.getId();
		if (gameOrder != null) {
			List<FundTransactionStruc> fundTransactionStrucs = gameTransactionRecordService.queryTransactionRecord(
					requestParam.getOrderCode(), null);
			result.setFundTransactionStrucs(fundTransactionStrucs);
		}

		try {

			OrdersStruc ordersStruc = DTOConvert.gameOrderOperationsEntity2OrdersStruc(gameOrderService
					.queryOrderOperationsDetail(orderid));
			GameWarnOrder warnOrder = gameWarnOrderDao.getGameWarnOrderByOrderCode(ordersStruc.getOrderCode());
			//利用game_order的parentId查詢gamePakage 以利區分12生肖
			GamePackage gamePackage=gamePackageDaoImpl.queryGamePackageById(ordersStruc.getParentid());
			if(ordersStruc.getLotteryid()==99112l && gamePackage.getActivityType()==1l){
				ordersStruc.setLotteryName(ordersStruc.getLotteryName()+DTOConvertor4Web.shierShengXiao);
			}
			if (warnOrder != null && warnOrder.getStatus() == 2l) {
				ordersStruc.setStatus(7);
			}
			if(ordersStruc.getLotteryid() == lhc){//六合彩
				ordersStruc.setWebIssueCode(ordersStruc.getWebIssueCode().substring(0, 2)+"/"+
			ordersStruc.getWebIssueCode().substring(2, 5));
			}
			
			List<SlipsStruc> slipsStrucs = new ArrayList<SlipsStruc>();
			List<GameSlipOperationsEntity> slipEntityList = gameOrderService.querySlipOperationsListByOrderID(orderid);
			for (GameSlipOperationsEntity slipEntity : slipEntityList) {
				SlipsStruc slipsStruc = DTOConvert.gameSlipOperationsEntity2SlipStruc(slipEntity);
				Long awardGroupId=null==gameOrder.getAwardGroupId()?0L:gameOrder.getAwardGroupId();
				GameAward award=gameAwardService.getValidByLotteryIdAndGroupIdAndBetTypeCode(gameOrder.getLotteryid(), awardGroupId,slipEntity.getBetTypeCode());
				slipsStruc.setGroupAward(null==award?0L:award.getActualBonus());
				slipsStruc.setGroupAwardDown(null==award?0L:award.getActualBonusDown());
				if(0L==slipsStruc.getGroupAward()){						
					GameAward maxAward=gameAwardService.getMaxBonusAwardByLotteryIdAndGroupIdAndBetTypeCodeParent(gameOrder.getLotteryid(), awardGroupId, slipEntity.getBetTypeCode());
					slipsStruc.setGroupAward(null==maxAward?0L:maxAward.getActualBonus());
					slipsStruc.setGroupAwardDown(null==maxAward?0L:maxAward.getActualBonusDown());
					//設定六合彩多組獎金模式
					if(maxAward.getLhcMultBonus()!=null && maxAward.getLhcMultBonus().size()>0){
						slipsStruc.setLhcMultBonus(maxAward.getLhcMultBonus());
					}
					
				}
				slipsStrucs.add(slipsStruc);
				
			}

			// 调用资金查询接口
			// 调用资金接口>查询资金交易记录

			result.setOrdersStruc(ordersStruc);
			result.setSlipsStruc(slipsStrucs);
			response.setResult(result);
		} catch (Exception e) {
			log.error("query order detail operations error", e);
			throw e;
		}
		log.info("query order detail operations end");

		return response;
	}
	
	/**
	 * 用户余额查询
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryUserBal")
	@ResponseBody
	public Response<Long> queryUserBal(@RequestBody @ValidRequestBody Request<Long> req) throws Exception {

		log.debug("开始用户余额查询......");
		Response<Long> response = new Response<Long>();

		Long userId = req.getBody().getParam();

		if (userId == 0L) {
			userId = RequestContext.getCurrUser().getId();
		}
		Long userBal = 0L;

		try {
			userBal = gameOrderService.getUserBal(userId);
			response.setResult(userBal);

		} catch (Exception e) {
			log.error("查询用户余额查询异常 ", e);
			throw e;
		}

		log.debug("查询用户余额查询完成。");
		return response;
	}

	/**
	 * 首页获取用户最新的五条投注记录
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getOrdersByUserId")
	@ResponseBody
	public Response<GameOrderQueryResponse> getOrdersByUserId(@RequestParam("userId") Long userId) throws Exception {

		log.debug("首页开始查询订单列表......");

		long lotteryId = 99101;

		String lid = rc.get(lastUserPlayGamePR + userId);

		if (null != lid) {
			lotteryId = new Long(lid);
		}

		GameOrderQueryRequest gor = new GameOrderQueryRequest();
		gor.setLotteryId(lotteryId);

		Response<GameOrderQueryResponse> response = new Response<GameOrderQueryResponse>();
		int sNo = 0;
		int eNo = 5;
		GameOrderQueryDTO queryDTO = new GameOrderQueryDTO();
		queryDTO.setUserId(userId);
		queryDTO.setQueryParam(gor);

		PageRequest<GameOrderQueryDTO> pr = PageConverterUtils.getRestPageRequest(sNo, eNo);
		pr.setSearchDo(queryDTO);
		pr.setSortColumns("SALE_TIME desc");
		Page<GameOrder> page = null;
		List<GameOrder> orders = null;
		List<OrdersStruc> ordersStruc = new ArrayList<OrdersStruc>();
		GameOrderQueryResponse result = new GameOrderQueryResponse();
		OrdersStruc orderStruc = null;
		try {
			page = gameOrderService.queryOrders(pr);
			orders = page.getResult();
			if (orders != null && orders.size() > 0) {
				for (GameOrder go : orders) {
					orderStruc = DTOConvert.gameOrder2OrdersStruc(go);
					ordersStruc.add(orderStruc);
				}
			}
			result.setOrdersStruc(ordersStruc);
			response.setResult(result);
			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageFirstElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			response.setResultPage(pager);

		} catch (Exception e) {
			log.error("首页查询订单列表异常 ", e);
			throw e;
		}

		log.debug("首页查询订单列表完成。");
		return response;
	}

	@RequestMapping(value = "/queryUserBetInfoByDate")
	@ResponseBody
	public Response<UserBetInfoSumStruc> queryUserBetInfoByDate(
			@RequestBody @ValidRequestBody Request<UserBetInfoQueryRequest> request) throws Exception {
		Response<UserBetInfoSumStruc> response = new Response<UserBetInfoSumStruc>(request);
		try {
			UserBetInfoSumStruc struc = gameOrderService.queryUserBetInfoByDate(request.getBody().getParam()
					.getUserId(), DataConverterUtil.convertLong2Date(request.getBody().getParam().getStartTime()));
			response.setResult(struc);
		} catch (Exception e) {
			log.error("queryUserBetInfoByDate error", e);
			throw e;
		}
		return response;
	}
	
	
	@RequestMapping(value = "/queryBeginMissionOrder" ,method={RequestMethod.GET})
	@ResponseBody
	public List<String> queryBeginMissionOrder(@RequestParam("userId") Long userId , @RequestParam("betFactor") Long betFactor) throws Exception {
		List<String> data = new ArrayList<String>();
		try {
			Map<String ,Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("betFactor", betFactor);
			data= gameOrderService.queryBeginMissionOrder(map);
			
			log.info(" data+="+data.size());
			
		} catch (Exception e) {
			log.error("queryBeginMissionOrder error", e);
			throw e;
		}
		return data;
	}
	
	@RequestMapping(value = "/queryUserDailyBetsForAct")
	@ResponseBody
	public Long queryUserDailyBets(
			@RequestParam("userId") Long userId) throws Exception {
		Long betTotal = gameOrderService.queryUserDailyBets(userId); 
		return betTotal;
	} 
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getWinningList")
	@ResponseBody
	public Response<LinkedHashMap> getWinningList(
			@RequestBody @ValidRequestBody Request<Map<String,String>> request) throws Exception {
		Response<LinkedHashMap> response = new Response<LinkedHashMap>(request);
		LinkedHashMap linkedMap = new LinkedHashMap();
		Long userId = request.getHead().getUserId(); 
		Map<String,String> map = request.getBody().getParam();
		
		try {
			List<com.winterframework.firefrog.game.dao.vo.GameOrder> list = gameOrderService.getWinningList(map.get("orderIds"),userId,Long.valueOf(map.get("lotteryId")), Long.valueOf(map.get("issueCode")));
			for(com.winterframework.firefrog.game.dao.vo.GameOrder gameOrder :list){
				if(gameOrder.getUserid().longValue()==userId.longValue()){
					linkedMap.put("$self", gameOrder.getTotalWin()==null?0:gameOrder.getTotalWin()+"");
				}else{
					User user = userCustomerDao.queryUserById(gameOrder.getUserid());
					linkedMap.put(user.getNickName(), gameOrder.getTotalWin()==null?0:gameOrder.getTotalWin()+"");
				}
			}
			response.setResult(linkedMap);
		} catch (Exception e) {
			log.error("queryUserBetInfoByDate error", e);
			throw e;
		}
		return response;
	}
	/**
	 * 单用户单期总中奖金额
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getWinAmt")
	@ResponseBody
	public Response<Double> getWinAmt(
			@RequestBody @ValidRequestBody Request<Map<String,Long>> request) throws Exception {
		Response<Double> response = new Response<Double>(request);
		Map<String,Long> map = request.getBody().getParam();
		
		try {
			response.setResult(gameOrderService.getWinAmt(map.get("lotteryId"),map.get("issueCode"),map.get("userId")));
		} catch (Exception e) {
			log.error("getWinAmt error", e);
			throw e;
		}
		return response;
	}
	
	@RequestMapping(value = "/getPrizeBetList")
	@ResponseBody
	public Response<List<Integer>> getPrizeBetList(
			@RequestBody @ValidRequestBody Request<Map<String,Long>> request) throws Exception {
		Response<List<Integer>> response = new Response<List<Integer>>(request);
		Long userId = request.getHead().getUserId();
		Map<String,Long> map = request.getBody().getParam();
		List<Integer> resultList = new ArrayList<Integer>();
		try{
			List<GameSlip> list = gameOrderService.querySlips(map.get("lotteryId"), userId, map.get("issueCode"), 2);
			for(GameSlip gameSlip : list) {
				String key = gameSlip.getGameBetType().getBetTypeCode()+"-"+gameSlip.getBetDetail();
				Integer viewId = JlDiceSlipViewMap.getSlipViewId(key);
				if(!resultList.contains(viewId)){
					resultList.add(viewId);
				}
			}
		}catch(Exception e){
			log.error("queryUserBetInfoByDate error", e);
			throw e;
		}
		response.setResult(resultList);
		return response;
	}
	
	
	@RequestMapping(value = "/getPlayerBetList")
	@ResponseBody
	public Response<GameOrderQueryResponse> getPlayerBetList(
			@RequestBody @ValidRequestBody Request<Map<String,Long>> request) throws Exception {
		Response<GameOrderQueryResponse> response = new Response<GameOrderQueryResponse>(request);
		Long userId = request.getHead().getUserId();
		Map<String,Long> map = request.getBody().getParam();
		GameOrderQueryResponse resp = new GameOrderQueryResponse();
		List<OrdersStruc> resultList = new ArrayList<OrdersStruc>();
		resp.setOrdersStruc(resultList);
		response.setResult(resp);
		try {
			List<GameOrder> list = gameOrderService.queryPlayerBet(map.get("lotteryId"), userId, map.get("issueCode"));
			for(GameOrder go:list){
				OrdersStruc os = DTOConvert.gameOrder2OrdersStruc(go);
				os.setUserNickName(go.getNickName());
				os.setHeadImg(go.getHeadImg());
				GameWarnOrder warnOrder = gameWarnOrderDao.getGameWarnOrderByOrderCode(go.getOrderCode());
				if (warnOrder != null && warnOrder.getStatus() == 2l) {
					os.setStatus(7);
				}
				List<GameSlip> gods = gameOrderService.querySlipsByOrderId(go.getId());
				List<SlipsStruc> sss = new ArrayList<SlipsStruc>();
				for (GameSlip god : gods) { 
					SlipsStruc ss = DTOConvert.gameOrderDetail2SlipStruc(god);
					String key = ss.getGameGroupCode().intValue()+"_"+ss.getGameSetCode().intValue()+"_"+ss.getBetMethodCode().intValue()+"-"+ss.getBetDetail();
					ss.setSlipViewId(JlDiceSlipViewMap.getSlipViewId(key));
					Long awardGroupId=null==go.getAwardGroupId()?0L:go.getAwardGroupId();
					
					GameAward award=gameAwardService.getValidByLotteryIdAndGroupIdAndBetTypeCode(go.getLottery().getLotteryId(), awardGroupId, god.getGameBetType().getBetTypeCode());
					ss.setGroupAward(null==award?0L:award.getActualBonus());
					ss.setGroupAwardDown(null==award?0L:award.getActualBonusDown());
					if(0L==ss.getGroupAward()){						
						GameAward maxAward=gameAwardService.getMaxBonusAwardByLotteryIdAndGroupIdAndBetTypeCodeParent(go.getLottery().getLotteryId(), awardGroupId, god.getGameBetType().getBetTypeCode());
						ss.setGroupAward(null==maxAward?0L:maxAward.getActualBonus());
						ss.setGroupAwardDown(null==maxAward?0L:maxAward.getActualBonusDown());
					}
					
					sss.add(ss);
				}
				os.setSlipsStruc(sss);
				resultList.add(os);
			}
			
		} catch (Exception e) {
			log.error("queryUserBetInfoByDate error", e);
			throw e;
		}
		return response;
	}
	
	@RequestMapping(value = "/queryUserPeriodBets")
	@ResponseBody
	public Long queryUserDailyBets(@RequestParam("userId") Long userId,
			@RequestParam("startTime") Long startTime,
			@RequestParam("endTime") Long endTime) throws Exception {
		Long betTotal = gameOrderService.queryUserDailyPeriodBets(userId,new Date(startTime),new Date(endTime)); 
		return betTotal;
	} 
	
	
	/**
	 * 查询本期方案細單紀錄
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/querySlipListByIssueCode")
	@ResponseBody
	public Response<GameOrderDetailOperationsQueryResponse> queryOrderSlipByIssuCodeUrl(
			@RequestBody @ValidRequestBody Request<GameOrderOperationsQueryRequest> request) throws Exception {
		log.debug("querySlipListByIssueCode start");

		Response<GameOrderDetailOperationsQueryResponse> response = new Response<GameOrderDetailOperationsQueryResponse>();
		GameOrderDetailOperationsQueryResponse result = new GameOrderDetailOperationsQueryResponse();
		// query condition
		GameOrderOperationsQueryRequest requestParam = request.getBody().getParam();
		List<SlipsStruc> sss = new ArrayList<SlipsStruc>();
		SlipsStruc ss = new SlipsStruc();
		List<GameSlip> gods=null;
		try {
			if(requestParam.getOrderIdList()!=null && requestParam.getOrderIdList().size()>0){
				gods=gameOrderService.querySlipsByOrderIdList(requestParam.getOrderIdList());
			}else if (requestParam.getIssueCode()!=null){
				gods = gameOrderService.querySlipsByIssueCode(requestParam.getIssueCode());
			}
			if(gods!=null && gods.size()>0){
				for (GameSlip slipEntity : gods) {
					ss = DTOConvert.gameOrderDetail2SlipStruc(slipEntity);
			
					sss.add(ss);
				}
			}			
			result.setSlipsStruc(sss);
			response.setResult(result);
		} catch (Exception e) {
			log.error("querySlipListByIssueCode error", e);
			throw e;
		}
		log.info("querySlipListByIssueCode end");

		return response;
	}
	
	/**
	 * 查询本期方案細單紀錄
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryBetDetaiByIssudeCode")
	@ResponseBody
	public Response<GameBetDetailTotAmountResponse> queryBetDetaiByIssudeCode(
			@RequestBody @ValidRequestBody Request<GameOrderOperationsQueryRequest> request) throws Exception {
		log.debug("queryBetDetaiByIssudeCode start");

		Response<GameBetDetailTotAmountResponse> response = new Response<GameBetDetailTotAmountResponse>();
		GameBetDetailTotAmountResponse result = new GameBetDetailTotAmountResponse();
		GameOrderOperationsQueryRequest requestParam = request.getBody().getParam();
		List<GameBetDetailTotAmountEntity> gods=null;
		try {
			gods = gameOrderService.queryBetDetaiByIssudeCode(requestParam.getIssueCode(),requestParam.getLotteryid());	
			result.setGameBetDetailTotAmountStruc(gods);
			response.setResult(result);
		} catch (Exception e) {
			log.error("queryBetDetaiByIssudeCode error", e);
			throw e;
		}
		log.info("queryBetDetaiByIssudeCode end");

		return response;
	}
}