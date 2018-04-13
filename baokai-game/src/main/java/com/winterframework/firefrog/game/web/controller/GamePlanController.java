package com.winterframework.firefrog.game.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.annotation.ValidRequestHeader;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.game.dao.IGamePlanDetailDao;
import com.winterframework.firefrog.game.dao.IGameSeriesDao;
import com.winterframework.firefrog.game.dao.vo.GamePackageItem;
import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.dao.vo.GameWarnOrder;
import com.winterframework.firefrog.game.entity.BetLimit;
import com.winterframework.firefrog.game.entity.GameBetType;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameMultipleEntity;
import com.winterframework.firefrog.game.entity.GameOrder;
import com.winterframework.firefrog.game.entity.GamePlan;
import com.winterframework.firefrog.game.entity.GamePlanDetail;
import com.winterframework.firefrog.game.entity.Lottery;
import com.winterframework.firefrog.game.exception.GameBetAmountErrorException;
import com.winterframework.firefrog.game.exception.GameBetContentPatternErrorException;
import com.winterframework.firefrog.game.exception.GameBetLimitAmountException;
import com.winterframework.firefrog.game.exception.GameBetMethodStatusStopException;
import com.winterframework.firefrog.game.exception.GameBetOverMultipleLimitException;
import com.winterframework.firefrog.game.exception.GameIssueISOpenAwardException;
import com.winterframework.firefrog.game.exception.GameIssueNotExistErrorException;
import com.winterframework.firefrog.game.exception.GameIssueStatusErrorException;
import com.winterframework.firefrog.game.exception.GameIssueStatusStopSaleException;
import com.winterframework.firefrog.game.exception.GameOrderStatusErrorException;
import com.winterframework.firefrog.game.exception.GameSeriesStatusErrorException;
import com.winterframework.firefrog.game.exception.GameTotbetsErrorException;
import com.winterframework.firefrog.game.exception.LinkFundSystemErrorException;
import com.winterframework.firefrog.game.exception.NoChooseBetAwardException;
import com.winterframework.firefrog.game.exception.UserBalErrorException;
import com.winterframework.firefrog.game.exception.UserGameAwardConfigErrorException;
import com.winterframework.firefrog.game.exception.UserSubmitBusyErrorException;
import com.winterframework.firefrog.game.exception.UserTopAgentBetException;
import com.winterframework.firefrog.game.lock.config.mongo.service.LockSelector;
import com.winterframework.firefrog.game.lock.config.mongo.service.LotteryLockService;
import com.winterframework.firefrog.game.service.IBetLimitService;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGameLevelRecycleService;
import com.winterframework.firefrog.game.service.IGameMultipleService;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.service.IGamePackageItemService;
import com.winterframework.firefrog.game.service.IGamePlanService;
import com.winterframework.firefrog.game.service.IGameWarnOrderService;
import com.winterframework.firefrog.game.web.dto.BetDetailStruc;
import com.winterframework.firefrog.game.web.dto.BetMethodMultipleStruc;
import com.winterframework.firefrog.game.web.dto.DTOConvert;
import com.winterframework.firefrog.game.web.dto.GameOrderResponeOverMutipleDTO;
import com.winterframework.firefrog.game.web.dto.GameOrderResponseDTO;
import com.winterframework.firefrog.game.web.dto.GamePlanDetailQueryRequest;
import com.winterframework.firefrog.game.web.dto.GamePlanDetailQueryResponse;
import com.winterframework.firefrog.game.web.dto.GamePlanParm;
import com.winterframework.firefrog.game.web.dto.GamePlanQueryDTO;
import com.winterframework.firefrog.game.web.dto.GamePlanQueryRequest;
import com.winterframework.firefrog.game.web.dto.GamePlanQueryResponse;
import com.winterframework.firefrog.game.web.dto.GamePlanRequest;
import com.winterframework.firefrog.game.web.dto.GamePlanResponse;
import com.winterframework.firefrog.game.web.dto.GameSlipResponseDTO;
import com.winterframework.firefrog.game.web.dto.OrdersStruc;
import com.winterframework.firefrog.game.web.dto.PlansFuturesStruc;
import com.winterframework.firefrog.game.web.dto.PlansStruc;
import com.winterframework.firefrog.game.web.dto.QueryLevelRecycleHistoryResponse;
import com.winterframework.firefrog.game.web.dto.ReservationCancelledChangeStatusRequest;
import com.winterframework.firefrog.game.web.dto.ReservationCancelledRequest;
import com.winterframework.firefrog.game.web.dto.ReservationCancelledResponse;
import com.winterframework.firefrog.game.web.dto.SlipsStruc;
import com.winterframework.firefrog.game.web.dto.StopGamePlanRequest;
import com.winterframework.firefrog.game.web.dto.StopGamePlanResponse;
import com.winterframework.firefrog.game.web.dto.TaskSavePlanRequest;
import com.winterframework.firefrog.game.web.dto.TaskSavePlanResponse;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.modules.web.util.RequestContext;

/**
 * 
* @ClassName: GamePlanController 
* @Description: 追号
* @author Richard & denny
* @date 2013-7-22 上午9:29:03 
*
 */
@Controller("gamePlanController")
@RequestMapping("/game")
public class GamePlanController {

	private Logger log = LoggerFactory.getLogger(GamePlanController.class);

	@Resource(name = "gamePlanServiceImpl")
	private IGamePlanService gamePlanService;

	@Resource(name = "gameOrderServiceImpl")
	private IGameOrderService gameOrderService;

	@Resource(name = "gamePackageItemServiceImpl")
	private IGamePackageItemService gamePackageItemService;

	@Resource(name = "gameMultipleServiceImpl")
	private IGameMultipleService gameMultipleService;
	@Resource(name = "gameSeriesDaoImpl")
	private IGameSeriesDao gameSeriesDaoImpl;
	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueServiceImpl;
	
	@Resource(name="gameWarnOrderServiceImpl")
	private IGameWarnOrderService gameWarnOrderServiceImpl;
	
	@Resource(name = "gameLevelRecycleServiceImpl")
	private IGameLevelRecycleService gameLevelRecycleService;
	
	@Resource(name = "betLimitServiceImpl")
	private IBetLimitService betLimitServiceImpl;
	
	@Resource(name = "RedisClient")
	private RedisClient rc;
	@Resource(name = "gamePlanDetailDaoImpl")
	private IGamePlanDetailDao gamePlanDetailDao;
	
	@Autowired
	private LockSelector selector;
	
	/** 不限制倍数 */
	final private static long MULTIPLE_NOT_LIMIT = -1;
	
	/** 骰宝类和值限制 */
	final private static String SUM_VALUE_CODE = "34_28_71";

	/**
	 * 
	* @Title: gamePlan 
	* @Description: 4.4 追号投注
	* @return
	* @throws Exception
	 */
	@RequestMapping("/gamePlan")
	@ResponseBody
	public Object gamePlan(@RequestBody @ValidRequestHeader @ValidRequestBody Request<GamePlanRequest> request)
			throws Exception {

		Response<GamePlanResponse> response = new Response<GamePlanResponse>(request);
		String key="userBetTime_userId1"+request.getHead().getUserId();
		boolean isgameplan = true;
		try {
			long userId = request.getHead().getUserId();
			//我们服务端要判断用户在100毫秒之内只能有一个订单这个判断不是通过数据库实现，是通过redis来实现
			//修改為300秒
			Long thisTime=new Date().getTime();
			
			log.info("--------------用户提交投注：userId="+userId+"----"+thisTime);
			if(!rc.acquireLock(key, 70000) ){
				log.info("用户提交投注过于频繁：userId="+userId);
				isgameplan = false;
				throw new UserSubmitBusyErrorException();
			}else{
				if (null != request.getBody()) {
	
					GamePlanRequest gamePlan = request.getBody().getParam();
					GamePlanResponse gamePlanResponse = new GamePlanResponse();
	
					GameOrder gameOrder = DTOConvert.convertGamePlanRequest2GameOrder(gamePlan, request.getHead()
							.getUserId());
					final long lotteryId = gameOrder.getLottery().getLotteryId();
					//判读该彩种是否已经停售
					GameSeries series = gameSeriesDaoImpl.getByLotteyId(gameOrder.getLottery().getLotteryId());
					if (series != null) {
						if (series.getStatus().intValue() == 0) {
							throw new GameSeriesStatusErrorException();
						}
					}
					List<GameOrderResponeOverMutipleDTO> overMutipleDTOs = new ArrayList<GameOrderResponeOverMutipleDTO>();
					for (com.winterframework.firefrog.game.entity.GamePackageItem betDetail : gameOrder.getGamePackage()
							.getItemList()) {
	
						// 除倍数限制验证外，其他验证都移至web中做
						GameMultipleEntity entity = new GameMultipleEntity();
						GameBetType betType = betDetail.getGameBetType();
						entity.setBetMethodCode(betType.getBetMethodCode());
						entity.setGameGroupCode(betType.getGameGroupCode());
						entity.setGameSetCode(betType.getGameSetCode());
						Lottery lottery = new Lottery();
						lottery.setLotteryId(gameOrder.getLottery().getLotteryId());
						entity.setLottery(lottery);
						Integer mutiple = gameMultipleService.queryMaxMutiple(entity);
						if (null != mutiple) {
							if (mutiple != -1) {//-1 为无限制    当为角模式时 倍数限制应为元模式*10
								if (betDetail.getMoneyMode().getValue() == 1) {//元模式
									if (betDetail.getMultiple() > mutiple) {
										throw new GameBetOverMultipleLimitException();
									}
								}else if (betDetail.getMoneyMode().getValue() == 3) {//分模式
									if (betDetail.getMultiple() > mutiple * 100) {
										throw new GameBetOverMultipleLimitException();
									}
								} else {
									if (betDetail.getMultiple() > mutiple * 10) {
										throw new GameBetOverMultipleLimitException();
									}
								}
							}
	
						}
	
					}
	
					//多订单混合验证部分逻辑
					GameIssueEntity gi = gameIssueServiceImpl.queryGameIssue(gameOrder.getLottery().getLotteryId(), null);
					if (gi.getIssueCode() == gamePlan.getStartIssueCode().longValue()) {//第一期如果为当前奖期，说明投注第一期会生成订单，要对第一期进行倍数限制，目前只对第一期生成订单的数据进行限制，随需求而定
						for (BetDetailStruc betDetail : gamePlan.getBetDetailsStruc()) {
							if (betDetail.getIssueCode().longValue() == gi.getIssueCode()) {
								String betTypeCode = betDetail.getGameGroupCode() + "_" + betDetail.getGameSetCode() + "_"
										+ betDetail.getBetMethodCode();
								//检测当期是否有相同投注内容的投注，获取相应的投注倍数,为避免小数，查询结果直接为角模式结果,当fileMode为1 （文件模式）时  需要将已投注的内容转换出来才能判断
								GameMultipleEntity entity = new GameMultipleEntity();
								entity.setBetMethodCode(betDetail.getBetMethodCode());
								entity.setGameGroupCode(betDetail.getGameGroupCode());
								entity.setGameSetCode(betDetail.getGameSetCode());
								Lottery lottery = new Lottery();
								lottery.setLotteryId(gameOrder.getLottery().getLotteryId());
								entity.setLottery(lottery);
								Integer mutiple = gameMultipleService.queryMaxMutiple(entity);
								if (null != mutiple) {
									if (mutiple != MULTIPLE_NOT_LIMIT) {//-1 为无限制    当为角模式时 倍数限制应为元模式*10
										if (betDetail.getMoneyMode() == 1) {//元模式
											if (betDetail.getMultiple() > mutiple) {
												throw new GameBetOverMultipleLimitException();
											}
										}else if (betDetail.getMoneyMode() == 3) {//分模式
											if (betDetail.getMultiple() > mutiple * 100) {
												throw new GameBetOverMultipleLimitException();
											}
										} else {
											if (betDetail.getMultiple() > mutiple * 10) {
												throw new GameBetOverMultipleLimitException();
											}
										}
										Integer currentIssueMutiple = 0;
										if (betDetail.getFileMode() == 1) {
											currentIssueMutiple = gameOrderService.getCurrentIssueMutipleFile(
													betDetail.getBetdetail(), betTypeCode, gameOrder.getGamePackage()
															.getUser().getId(), gameOrder.getGamePackage().getLottery()
															.getLotteryId(), gameOrder.getGameIssue().getIssueCode());
										} else {
											currentIssueMutiple = gameOrderService.getCurrentIssueMutiple(
													betDetail.getBetdetail(), betTypeCode, gameOrder.getGamePackage()
															.getUser().getId(), gameOrder.getGamePackage().getLottery()
															.getLotteryId(), gameOrder.getGameIssue().getIssueCode());
										}
										if (currentIssueMutiple == null) {
											currentIssueMutiple = 0;
										}
										if (betDetail.getMoneyMode() == 1) {//元模式
											if (betDetail.getMultiple() * 10 > (mutiple * 10 - currentIssueMutiple)) {
												GameOrderResponeOverMutipleDTO dto = new GameOrderResponeOverMutipleDTO();
												dto.setBetDetail(betDetail.getBetdetail());
												dto.setBetTypeCode(betTypeCode);
												dto.setGameIssueCode(gameOrder.getGameIssue().getIssueCode());
												GameIssueEntity issueEntity = gameIssueServiceImpl.queryGameIssue(gameOrder
														.getLottery().getLotteryId(), gameOrder.getGameIssue()
														.getIssueCode());
												dto.setWebIssueCode(issueEntity.getWebIssueCode());
												Integer temp = mutiple * 10 - currentIssueMutiple;
												dto.setMultiple(temp < 0 ? 0 : temp);
												dto.setMoneyunit("1");
												overMutipleDTOs.add(dto);
											}
										}else if (betDetail.getMoneyMode() == 3) {//分模式
											if (betDetail.getMultiple()/10 > (mutiple * 10 - currentIssueMutiple)) {
												GameOrderResponeOverMutipleDTO dto = new GameOrderResponeOverMutipleDTO();
												dto.setBetDetail(betDetail.getBetdetail());
												dto.setBetTypeCode(betTypeCode);
												dto.setGameIssueCode(gameOrder.getGameIssue().getIssueCode());
												GameIssueEntity issueEntity = gameIssueServiceImpl.queryGameIssue(gameOrder
														.getLottery().getLotteryId(), gameOrder.getGameIssue()
														.getIssueCode());
												dto.setWebIssueCode(issueEntity.getWebIssueCode());
												Integer temp = mutiple * 10 - currentIssueMutiple;
												dto.setMultiple(temp < 0 ? 0 : temp);
												dto.setMoneyunit("0.01");
												overMutipleDTOs.add(dto);
											}
										} else {
											if (betDetail.getMultiple() > (mutiple * 10 - currentIssueMutiple)) {
												GameOrderResponeOverMutipleDTO dto = new GameOrderResponeOverMutipleDTO();
												dto.setBetDetail(betDetail.getBetdetail());
												dto.setBetTypeCode(betTypeCode);
												dto.setGameIssueCode(gameOrder.getGameIssue().getIssueCode());
												GameIssueEntity issueEntity = gameIssueServiceImpl.queryGameIssue(gameOrder
														.getLottery().getLotteryId(), gameOrder.getGameIssue()
														.getIssueCode());
												dto.setWebIssueCode(issueEntity.getWebIssueCode());
												Integer temp = mutiple * 10 - currentIssueMutiple;
												dto.setMultiple(temp < 0 ? 0 : temp);
												dto.setMoneyunit("0.1");
												overMutipleDTOs.add(dto);
											}
										}
									} else if( mutiple == MULTIPLE_NOT_LIMIT && 
											(lotteryId==99601 || lotteryId == 99602 || lotteryId == 99603) ) {
										//骰寶相關特殊投注限制
										log.debug(" betTypeCode " + betTypeCode);
										List<BetLimit> betLimitList = betLimitServiceImpl.queryBetLimitByBet(lotteryId);
										boolean isCheck = checkLimitAmountForK3dice(lotteryId, userId, gi.getIssueCode(), betTypeCode, betDetail, betLimitList);
										
										if(!isCheck){
											throw new GameBetLimitAmountException();
										}
									} else {
										// nothing
									}
								}
	
							}
	
						}
	
					}
	
					if (!overMutipleDTOs.isEmpty()) {
						response.getHead().setStatus(202005);
						gamePlanResponse.setOverMutipleDTO(overMutipleDTOs);
						response.setResult(gamePlanResponse);
						return response;
					}
	
					//				boolean isLock = lockService.lock(gameOrder, gameOrder.getGamePackage().getUser().getId());
					GameOrderResponseDTO dto = DTOConvert.convertGameOrderToPlanDTO(gameOrder);
					LotteryLockService lockService = selector.getRealService(gamePlan.getLotteryid());
					if (lockService != null) {
						boolean isLock = lockService.gamePlanLock(dto, gameOrder);
						response.setResult(gamePlanResponse);
						if (isLock) {
							log.info("需要封锁变价。。。。。。");
							response.getHead().setStatus(205000L);
							List<GamePlanParm> planParam = dto.getGamePlanParm();
							for (GamePlanParm gamePlanParm : planParam) {
								GameIssueEntity issueEntity = gameIssueServiceImpl.queryGameIssue(gameOrder.getLottery()
										.getLotteryId(), gamePlanParm.getIssueCode());
								gamePlanParm.setNumber(issueEntity.getWebIssueCode());
								if (gameOrder.getGameIssue().getIssueCode().longValue() == gamePlanParm.getIssueCode()
										.longValue()) {
									dto.setWebIssueCode(issueEntity.getWebIssueCode());
								}
							}
							Map<String, GameSlipResponseDTO> gss = new HashMap<String, GameSlipResponseDTO>();
							for (GameSlipResponseDTO gs : dto.getSlipResonseDTOList()) {
								gss.put(gs.getGameBetType().getBetTypeCode()+gs.getBetDetail()+gs.getMoneyMode().getValue(), gs);
							}
							List<GameSlipResponseDTO> resetList = new ArrayList<GameSlipResponseDTO>();
							Map<String, Object> zeroSlip = new HashMap<String, Object>();
							for (com.winterframework.firefrog.game.entity.GameSlip gameSlip : gameOrder.getSlipList()) {
								//预处理一下slip，把有投注倍数为0的slip归置一下
								if (gameSlip.getMultiple() == 0) {
									zeroSlip.put(gameSlip.getBetDetail() + gameSlip.getGameBetType().getBetTypeCode()+gameSlip.getMoneyMode().getValue(), 1);
								}
							}
							for (com.winterframework.firefrog.game.entity.GameSlip gameSlip : gameOrder.getSlipList()) {
								boolean isCurr = gameSlip.getIssueCode().getIssueCode()
										.equals(gameOrder.getGameIssue().getIssueCode());
								GameSlipResponseDTO gsTemplate = gss.get(gameSlip.getGameBetType().getBetTypeCode()+gameSlip.getBetDetail()+gameSlip.getMoneyMode().getValue());
								if (gsTemplate != null) {
									GameSlipResponseDTO gsDTO = new GameSlipResponseDTO();
									gsDTO.setBetDetail(gsTemplate.getBetDetail());
									gsDTO.setGameBetType(gsTemplate.getGameBetType());
									gsDTO.setMoneyMode(gsTemplate.getMoneyMode());
									gsDTO.setAwardMode(gsTemplate.getAwardMode());
									gsDTO.setCurr(isCurr ? 1L : 0L);
									gsDTO.setIssueCode(gameSlip.getIssueCode().getIssueCode());
									if (zeroSlip.get(gameSlip.getBetDetail() + gameSlip.getGameBetType().getBetTypeCode()+gameSlip.getMoneyMode().getValue()) != null) {
										//
										gsDTO.setMultiple(0);
										gsDTO.setTotalAmount(0l);
									} else {
										gsDTO.setMultiple(gameSlip.getMultiple());
									}
									gsDTO.setTotalBet(gsTemplate.getTotalBet());
									gsDTO.setLockPoints(gameSlip.getLockPoints());
									gsDTO.setTotalAmount(gsTemplate.getTotalAmount() / gsTemplate.getMultiple()
											* gsDTO.getMultiple());
									resetList.add(gsDTO);
								}
							}
							Comparator<? super GameSlipResponseDTO> c = new Comparator<GameSlipResponseDTO>() {
								@Override
								public int compare(GameSlipResponseDTO o1, GameSlipResponseDTO o2) {							
									return (int) (o1.getIssueCode() - o2.getIssueCode());
								}							
							};
							Collections.sort(resetList, c );
							dto.setSlipResonseDTOList(resetList);
							gamePlanResponse.setGameOrderResponseDTO(dto);
	
							return response;
						}
					}
					log.debug("不需要封锁变价处理。。。。。。");
					Long gamePlanId = gamePlanService.doBusiness(gameOrder, gamePlan.getBetOriginDataStruc(),request.getBody().getParam().getCurrentIssueCode());
	
					gamePlanResponse.setGamePlanId(gamePlanId);
					//增加追号第一期订单的返回值
					if(gameOrder.getId()!=null){
						GameOrderResponseDTO orderDto = new GameOrderResponseDTO();
						orderDto.setWebIssueCode(gameOrder.getGameIssue().getWebIssueCode());
						orderDto.setOrderCode(gameOrder.getOrderCode());
						orderDto.setSaleTime(DateUtils.format(gameOrder.getSaleTime(), DateUtils.DATETIME_FORMAT_PATTERN));
						orderDto.setTotalAmount(gameOrder.getTotalAmount());
						orderDto.setOrderId(gameOrder.getId());
						gamePlanResponse.setGameOrderResponseDTO(orderDto);
					}
					response.setResult(gamePlanResponse);
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
			log.error("用户投注奖金组数据配置错误：", e);
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
		} catch (GameBetOverMultipleLimitException e) {
			log.error("投注超出最大倍数：", e);
			response.getHead().setStatus(e.getStatus());			
		}catch (GameIssueNotExistErrorException e) {
			log.error("奖期不存在：", e);
			response.getHead().setStatus(e.getStatus());
		}catch (UserSubmitBusyErrorException e) {
			log.error("用户提交过于频繁：", e);
			response.getHead().setStatus(e.getStatus());
		} catch (GameBetLimitAmountException e) {
			log.error("投注金额超过上限：", e);
			response.getHead().setStatus(e.getStatus());
		} catch (Exception e) {
			log.error("追号操作失败：", e);
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
	 * 判斷骰種的投注金額是否超過 , TRUE : 檢核通過,  FALSE : 檢核不通過, 代表投注金額超標
	 * @param lotteryId
	 * @param userId
	 * @param issueCode 當期獎期號碼
	 * @param betTypeCode 投註方式
	 * @param betDetail
	 * @return TRUE : 檢核通過,  FALSE : 檢核不通過, 代表投注金額超標
	 * @throws Exception
	 */
	private boolean checkLimitAmountForK3dice(final long lotteryId, 
			final long userId, 
			final long issueCode, 
			final String betTypeCode,
			BetDetailStruc betDetail,
			List<BetLimit> betLimitList) throws Exception{
		
		if(CollectionUtils.isNotEmpty(betLimitList)){
			BetMethodMultipleStruc bmms = DTOConvert.betLimit2BetMethodMultipleStrucForBetPage(betLimitList.get(0));
			//取得投注限制
			Map<String, Integer> limits = bmms.getK3hezhiMultiple();
			//取得個人當期投注資料
			List<com.winterframework.firefrog.game.entity.GameSlip> gameSlips = 
					gameOrderService.querySlipsByCondition(lotteryId, userId, issueCode, betTypeCode, betDetail.getBetdetail(), 1);
			
			log.debug("DB存在的單資料 -> ");
			long dbSumAmount = 0;
			for(com.winterframework.firefrog.game.entity.GameSlip slip : gameSlips){
				log.debug("DB BetDetail -> " + slip.getBetDetail());
				log.debug("DB TotalAmount -> " + slip.getTotalAmount());
				dbSumAmount += slip.getTotalAmount();
			}
			
			log.debug("當前資料 ");
			log.debug("page BetDetail -> " + betDetail.getBetdetail());
			log.debug("page Totamount -> " + betDetail.getTotamount());
			long pageAmount = betDetail.getTotamount();
			
			//限制的金額
			long limitValue = limits.get("" + betDetail.getBetdetail()).longValue() * 2L * 10000L;
			//加總的金額
			long sumValue = dbSumAmount + pageAmount;
			
			log.debug("limitValue -> " + limitValue);
			log.debug("sumValue -> " + sumValue);
			if (limitValue < sumValue) {
				return Boolean.FALSE;
			} else {
				return Boolean.TRUE;
			}
		}
		return Boolean.TRUE;
	}
	

	/**
	 * 
	* @Title: stopPlan 
	* @Description: 4.6 终止计划
	* @return
	* @throws Exception
	 */
	@RequestMapping("/stopPlan")
	@ResponseBody
	public Object stopPlan(@RequestBody @ValidRequestHeader @ValidRequestBody Request<StopGamePlanRequest> request)
			throws Exception {

		Response<StopGamePlanResponse> response = new Response<StopGamePlanResponse>(request);
		String key = "stopplan_userId" + request.getHead().getUserId();
		boolean isstop = true;
		try {
			if (null != request.getBody()) {
				StopGamePlanRequest stopGamePlan = request.getBody().getParam();
				if (!rc.acquireLock(key, 70000)) {
					log.info("用户提交投终止计划于频繁：userid = " + request.getHead().getUserId() + "plane=" + stopGamePlan.getPlanId());
					isstop = false;
					throw new Exception();
				}else{
					gamePlanService.stopGamePlan(stopGamePlan.getPlanId(), stopGamePlan.getCancelTime(), request.getHead()
							.getUserId(), stopGamePlan.getUserType());
				}
			}

		} catch (GameOrderStatusErrorException e) {
			log.error("终止计划时计划状态错误：", e);
			response.getHead().setStatus(e.getStatus());
		} catch (LinkFundSystemErrorException e) {
			log.error("连接资金系统异常：", e);
			response.getHead().setStatus(e.getStatus());
		} catch (GameIssueISOpenAwardException e) {
			log.error("开奖中，撤销失败：", e);
			response.getHead().setStatus(e.getStatus());
		} catch (Exception e) {
			log.error("停止计划错误：", e);
			throw e;
		}finally{
			if (isstop){
				rc.releaseLock(key);
			}
		}
		return response;
	}

	/**
	 * 
	* @Title: reservationCancelledGamePlan 
	* @Description: 4.7 预约撤销
	* @return
	* @throws Exception
	 */
	@RequestMapping("/cancellReservation")
	@ResponseBody
	public Object reservationCancelledGamePlan(
			@RequestBody @ValidRequestBody Request<ReservationCancelledRequest> request) throws Exception {

		Response<ReservationCancelledResponse> response = new Response<ReservationCancelledResponse>(request);
		try {

			if (null != request.getBody()) {

				ReservationCancelledRequest cancelledRequest = request.getBody().getParam();
				GameIssueEntity gameIssue = gameIssueServiceImpl.queryGameIssue(cancelledRequest.getLotteryId(),
						cancelledRequest.getIssueCode());
				if (DateUtils.currentDate().after(gameIssue.getSaleStartTime())) {
					response.getHead().setStatus(111);
					gamePlanDetailDao.updateReservationCalledErrorStatusByPlanIdAndIssueCode(request.getBody().getParam().getPlanId(), request.getBody().getParam().getIssueCode(), new Long(request.getHead().getUserId()));
					return response;
				}

				gamePlanService.reservationCalled(cancelledRequest.getPlanId(), cancelledRequest.getIssueCode(),
						request.getHead().getUserId(), cancelledRequest.getUserType());
			}

		} catch (LinkFundSystemErrorException e) {
			log.error("链接资金错误 planid："+request.getBody().getParam().getPlanId()+",", e);
			response.getHead().setStatus(e.getStatus());
			gamePlanDetailDao.updateReservationCalledErrorStatusByPlanIdAndIssueCode(request.getBody().getParam().getPlanId(), request.getBody().getParam().getIssueCode(), new Long(request.getHead().getUserId()));
		} catch (GameIssueISOpenAwardException e) {
			log.error("开奖中，撤销失败 planid："+request.getBody().getParam().getPlanId()+",", e);
			response.getHead().setStatus(e.getStatus());
			gamePlanDetailDao.updateReservationCalledErrorStatusByPlanIdAndIssueCode(request.getBody().getParam().getPlanId(), request.getBody().getParam().getIssueCode(), new Long(request.getHead().getUserId()));
		} catch (Exception e) {

			log.error("预约撤销失败 planid："+request.getBody().getParam().getPlanId()+",", e);
			gamePlanDetailDao.updateReservationCalledErrorStatusByPlanIdAndIssueCode(request.getBody().getParam().getPlanId(), request.getBody().getParam().getIssueCode(), new Long(request.getHead().getUserId()));
			throw e;
		}
		return response;
	}
	
	/**
	 * 
	* @Title: cancellReservationChangeStatus 
	* @Description: 4.7 预约撤销 修改追號狀態
	* @return
	* @throws Exception
	 */
	@RequestMapping("/cancellReservationChangeStatus")
	@ResponseBody
	public Object reservationCancelledGamePlanChangeStatus(
			@RequestBody @ValidRequestBody Request<ReservationCancelledChangeStatusRequest> request) throws Exception {

		Response<ReservationCancelledResponse> response = new Response<ReservationCancelledResponse>(request);
		try {

			if (null != request.getBody()) {

				ReservationCancelledChangeStatusRequest cancelledRequest = request.getBody().getParam();

				gamePlanService.reservationCalledChangeStatus(cancelledRequest.getPlanId(), cancelledRequest.getIssueCode(),
						request.getHead().getUserId(), cancelledRequest.getUserType());
			}
			response.getHead().setStatus(0l);

		} catch (GameIssueISOpenAwardException e) {
			log.error("开奖中，撤销失败：", e);
			response.getHead().setStatus(e.getStatus());
		} catch (Exception e) {

			log.error("预约撤销 修改追號狀態失败：", e);
			throw e;
		}
		return response;
	}

	/** 
	* @Title: queryPlans 
	* @Description: 4.10 追号查询
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryPlans")
	@ResponseBody
	public Response<GamePlanQueryResponse> queryPlans(
			@RequestBody @ValidRequestBody Request<GamePlanQueryRequest> request) throws Exception {

		log.debug("开始查询追号列表......");
		Response<GamePlanQueryResponse> response = new Response<GamePlanQueryResponse>(request);

		long userId = request.getHead().getUserId();
		if (0L == userId) {
			userId = RequestContext.getCurrUser().getId();
		}
		int sNo = request.getBody().getPager().getStartNo();
		int eNo = request.getBody().getPager().getEndNo();
		
		//查询一代回收记录,加入搜寻条件
		QueryLevelRecycleHistoryResponse recycleHist = gameLevelRecycleService.queryRecycleLastHistory(userId);
		if(recycleHist!=null){
			request.getBody().getParam().setRecycleDate(recycleHist.getActivityDate());			
		}

		GamePlanQueryDTO queryDTO = new GamePlanQueryDTO();
		queryDTO.setUserId(userId);
		queryDTO.setQueryParam(request.getBody().getParam());

		PageRequest<GamePlanQueryDTO> pr = PageConverterUtils.getRestPageRequest(sNo, eNo);
		pr.setSearchDo(queryDTO);
		pr.setSortColumns("CREATE_TIME,id desc");
		Page<GamePlan> page = null;
		List<GamePlan> plans = null;
		List<PlansStruc> plansStrucs = new ArrayList<PlansStruc>();
		GamePlanQueryResponse result = new GamePlanQueryResponse();
		PlansStruc plansStruc = null;
		try {
			page = gamePlanService.queryPlans(pr);

			plans = page.getResult();
			if (plans != null && plans.size() > 0) {
				for (GamePlan gp : plans) {
					plansStruc = DTOConvert.gamePlan2PlansStruc(gp);
					plansStrucs.add(plansStruc);
				}
			}
			result.setPlansStruc(plansStrucs);
			response.setResult(result);
			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageFirstElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			response.setResultPage(pager);

		} catch (Exception e) {
			log.error("查询追号列表异常 ", e);
			throw e;
		}

		log.debug("查询追号列表完成。");
		return response;
	}

	/** 
	* @Title: generateGamePlan 
	* @Description: 生成追号计划 
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/generateGamePlan")
	@ResponseBody
	public Response<TaskSavePlanResponse> generateGamePlan(
			@RequestBody @ValidRequestBody Request<TaskSavePlanRequest> request) throws Exception {

		log.debug("执行正常追号计划");
		Response<TaskSavePlanResponse> response = new Response<TaskSavePlanResponse>(request);

		try {

			TaskSavePlanRequest planRequest = request.getBody().getParam();
			//			gamePlanService.generateGamePlan(planRequest.getPlanid(), planRequest.getIssueCode());
			gamePlanService.generateGamePlanByIssueCode(planRequest.getIssueCode(), planRequest.getPlanid());

		} catch (Exception e) {

			log.error("generateGamePlan error:", e);
			response.getHead().setStatus(1);
		}

		return response;
	}

	/** 
	* @Title: queryPlanDetail 
	* @Description: 4.11 追号详情查询
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryPlanDetail")
	@ResponseBody
	public Response<GamePlanDetailQueryResponse> queryPlanDetail(
			@RequestBody @ValidRequestBody Request<GamePlanDetailQueryRequest> request) throws Exception {

		log.debug("开始查询追号详情......");
		Response<GamePlanDetailQueryResponse> response = new Response<GamePlanDetailQueryResponse>(request);
		Long planId = request.getBody().getParam().getPlanid();
		String planCode = request.getBody().getParam().getPlanCode();
		if (planId == null) {
			com.winterframework.firefrog.game.dao.vo.GamePlan gamePlan = gamePlanService.queryPlanByCode(planCode);
			planId = gamePlan.getId();
		}

		GamePlanDetailQueryResponse gamePlanDetailQueryResponse = new GamePlanDetailQueryResponse();
		PlansStruc plansStruc;
		List<OrdersStruc> ordersStrucs = new ArrayList<OrdersStruc>();
		List<SlipsStruc> slipsStrucs = new ArrayList<SlipsStruc>();
		List<SlipsStruc> planSlipsStrucs = new ArrayList<SlipsStruc>();
		List<PlansFuturesStruc> plansFuturesStrucs = new ArrayList<PlansFuturesStruc>();

		GamePlan gamePlan;
		List<GameOrder> gameOrders = new ArrayList<GameOrder>();
		List<GamePlanDetail> gamePlanDetails = new ArrayList<GamePlanDetail>();
		List<GameSlip> gameSlips = new ArrayList<GameSlip>();

		try {
			gamePlan = gamePlanService.queryPlanById(planId, request.getHead().getUserId());
		
			if (gamePlan != null) {//当追号单的状态<2 并且不存在未审核的风险订单并且追号未执行最后一期的销售结束时间在当前时间之后，追号可被终止
				plansStruc = DTOConvert.gamePlan2PlansStruc(gamePlan);
				List<GameWarnOrder> wos=gameWarnOrderServiceImpl.getUndealGameWarnOrderByPlanId(planId);
				Date lastStopTime=gamePlanService.getLastPlanCanStopTime(planId);
				if(gamePlan.getStopMode().getValue()>0){//当停止方式不为不停止时，需要考虑风险订单是否已审核
					if((wos==null||wos.isEmpty())&&(gamePlan.getStatus().getValue()<2)&& (lastStopTime!=null && lastStopTime.after(new Date()))){
						plansStruc.setCanStop(true);
					}else{
						plansStruc.setCanStop(false);
					}
				}else{
					if(gamePlan.getStatus().getValue()<2&&(lastStopTime!=null && lastStopTime.after(new Date()))){
						plansStruc.setCanStop(true);
					}else{
						plansStruc.setCanStop(false);
					}
				}
				

				gameOrders = gameOrderService.queryOrdersByPlanId(planId);
				for (GameOrder gameOrder : gameOrders) {
					ordersStrucs.add(DTOConvert.gameOrder2OrdersStruc(gameOrder));
				}

				List<GamePackageItem> items = new ArrayList<GamePackageItem>();
				items = gamePackageItemService.queryPackageItemListByPackageId(gamePlanService
						.queryPackageIdByPlanId(planId));
				planSlipsStrucs.addAll(DTOConvert.pamePackageItems2SlipsStruc(items));

				gamePlanDetails = gamePlanService.queryPlanDetailsByPlanId(planId);
				if(gamePlanDetails!=null && gamePlanDetails.size()>0){
					for (GamePlanDetail gamePlanDetail : gamePlanDetails) {
						/*if (gamePlanDetail.getDetailStatus().getValue() == 0
								|| gamePlanDetail.getDetailStatus().getValue() == 2) {*/
						plansFuturesStrucs.add(DTOConvert.gamePlanDetail2PlansFuturesStruc(gamePlanDetail));
						/*}*/
					}
				}
				

				/*未使用--Gary
				gameSlips = gamePlanService.querySlipsByPlanId(planId);
				for (GameSlip gs : gameSlips) {
					slipsStrucs.add(DTOConvert.gameSlip2SlipsStruc(gs));
				}*/

				gamePlanDetailQueryResponse.setPlansStruc(plansStruc);
				gamePlanDetailQueryResponse.setOrdersStrucs(ordersStrucs);
				gamePlanDetailQueryResponse.setPlansFuturesStrucs(plansFuturesStrucs);
				gamePlanDetailQueryResponse.setSlipsStruc(slipsStrucs);
				gamePlanDetailQueryResponse.setPlanSlipsStrucs(planSlipsStrucs);

				response.setResult(gamePlanDetailQueryResponse);
			}
		} catch (Exception e) {
			log.error("查询订单详情异常 ", e);
			throw e;
		}

		log.debug("查询订单详情完成。");
		return response;
	}
	/** 
	* @Title: queryPlanDetailGetPlayName 
	* @Description: requirement #435 建议在“投注记录"、“追号记录”列表中增加一列“玩法”，不然则需点击“查看”按钮才能查询到具体玩法。
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryPlanDetailGetPlayName")
	@ResponseBody
	public Response<GamePlanDetailQueryResponse> queryPlanDetailGetPlayName(
			@RequestBody @ValidRequestBody Request<GamePlanDetailQueryRequest> request) throws Exception {
		log.debug("开始查询追号玩法......");
		Response<GamePlanDetailQueryResponse> response = new Response<GamePlanDetailQueryResponse>(request);
		Long planId = request.getBody().getParam().getPlanid();
		String planCode = request.getBody().getParam().getPlanCode();
		if (planId == null) {
			com.winterframework.firefrog.game.dao.vo.GamePlan gamePlan = gamePlanService.queryPlanByCode(planCode);
			planId = gamePlan.getId();
		}

		GamePlanDetailQueryResponse gamePlanDetailQueryResponse = new GamePlanDetailQueryResponse();
		PlansStruc plansStruc;
		List<OrdersStruc> ordersStrucs = new ArrayList<OrdersStruc>();
		List<SlipsStruc> slipsStrucs = new ArrayList<SlipsStruc>();
		List<SlipsStruc> planSlipsStrucs = new ArrayList<SlipsStruc>();
		List<PlansFuturesStruc> plansFuturesStrucs = new ArrayList<PlansFuturesStruc>();

		GamePlan gamePlan;
		List<GameOrder> gameOrders = new ArrayList<GameOrder>();
		List<GamePlanDetail> gamePlanDetails = new ArrayList<GamePlanDetail>();
		List<GameSlip> gameSlips = new ArrayList<GameSlip>();
		try {
			gamePlan = gamePlanService.queryPlanById(planId, request.getHead().getUserId());
			
			if (gamePlan != null) {//当追号单的状态<2 并且不存在未审核的风险订单并且追号未执行最后一期的销售结束时间在当前时间之后，追号可被终止
				plansStruc = DTOConvert.gamePlan2PlansStruc(gamePlan);
				List<GameWarnOrder> wos=gameWarnOrderServiceImpl.getUndealGameWarnOrderByPlanId(planId);
				Date lastStopTime=gamePlanService.getLastPlanCanStopTime(planId);
				if(gamePlan.getStopMode().getValue()>0){//当停止方式不为不停止时，需要考虑风险订单是否已审核
					if((wos==null||wos.isEmpty())&&(gamePlan.getStatus().getValue()<2)&& (lastStopTime!=null && lastStopTime.after(new Date()))){
						plansStruc.setCanStop(true);
					}else{
						plansStruc.setCanStop(false);
					}
				}else{
					if(gamePlan.getStatus().getValue()<2&&(lastStopTime!=null && lastStopTime.after(new Date()))){
						plansStruc.setCanStop(true);
					}else{
						plansStruc.setCanStop(false);
					}
				}
				List<GamePackageItem> items = new ArrayList<GamePackageItem>();
				items = gamePackageItemService.queryPackageItemListByPackageId(gamePlanService
						.queryPackageIdByPlanId(planId));
				planSlipsStrucs.addAll(DTOConvert.pamePackageItems2SlipsStruc(items));
		
				gamePlanDetailQueryResponse.setPlansStruc(plansStruc);
				gamePlanDetailQueryResponse.setOrdersStrucs(ordersStrucs);
				gamePlanDetailQueryResponse.setPlansFuturesStrucs(plansFuturesStrucs);
				gamePlanDetailQueryResponse.setSlipsStruc(slipsStrucs);
				gamePlanDetailQueryResponse.setPlanSlipsStrucs(planSlipsStrucs);
		
				response.setResult(gamePlanDetailQueryResponse);
			}
		} catch (Exception e) {
			log.error("查询订单详情异常 ", e);
			throw e;
		}
		
		log.debug("查询订单详情完成。");
		return response;
	}
	
	/** 
	* @Title: getUnDoPlansCountByUserId 
	* @Description: 查询该用户未开始与进行中的追号数量
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/getUndoPlansCountByUserId")
	@ResponseBody
	public Object getUndoPlansCountByUserId(
			@RequestBody Request<GamePlanRequest> request) throws Exception {
		log.info("开始查询未开始与进行中的追号......");
		Response<GamePlanResponse> response = new Response<GamePlanResponse>(request);
		GamePlanResponse result = new GamePlanResponse();
		GameOrderResponseDTO gameOrderResponseDTO = new GameOrderResponseDTO();
		Long userId = request.getBody().getParam().getUserId();
		try {
			Long totalAmount = gamePlanService.getUndoPlansCountByUserId(userId);
			gameOrderResponseDTO.setTotalAmount(totalAmount);
		} catch (Exception e) {
			log.error("查询未开始与进行中的追号异常 ", e);
		}
		result.setGameOrderResponseDTO(gameOrderResponseDTO);
		response.setResult(result);
		log.info("结束查询未开始与进行中的追号......");
		return response;		
	}

}
