package com.winterframework.firefrog.game.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.SNUtil;
import com.winterframework.firefrog.game.dao.IGameControlEventDao;
import com.winterframework.firefrog.game.dao.IGameIssueDao;
import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGamePackageDao;
import com.winterframework.firefrog.game.dao.IGamePackageItemDao;
import com.winterframework.firefrog.game.dao.IGamePlanDao;
import com.winterframework.firefrog.game.dao.IGamePlanDetailDao;
import com.winterframework.firefrog.game.dao.IGameReturnPointDao;
import com.winterframework.firefrog.game.dao.IGameSlipDao;
import com.winterframework.firefrog.game.dao.IGameUserAwardGroupDao;
import com.winterframework.firefrog.game.dao.IGameWarnIssueDao;
import com.winterframework.firefrog.game.dao.vo.EnumTypeConverts;
import com.winterframework.firefrog.game.dao.vo.GameBettypeStatus;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GamePackageItem;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssue;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssueLog;
import com.winterframework.firefrog.game.dao.vo.GameWarnOrder;
import com.winterframework.firefrog.game.entity.CancelMode;
import com.winterframework.firefrog.game.entity.GameBetType;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameOrder.OrderParentType;
import com.winterframework.firefrog.game.entity.GameOrder.OrderStatus;
import com.winterframework.firefrog.game.entity.GamePackage;
import com.winterframework.firefrog.game.entity.GamePackage.GamePackageType;
import com.winterframework.firefrog.game.entity.GamePlan;
import com.winterframework.firefrog.game.entity.GamePlan.GamePlanStatus;
import com.winterframework.firefrog.game.entity.GamePlanDetail;
import com.winterframework.firefrog.game.entity.GamePlanDetail.GamePlanDetailStatus;
import com.winterframework.firefrog.game.entity.GameSeriesConfigEntity;
import com.winterframework.firefrog.game.entity.Lottery;
import com.winterframework.firefrog.game.entity.VOConvert;
import com.winterframework.firefrog.game.exception.GameIssueISOpenAwardException;
import com.winterframework.firefrog.game.enums.Separator;
import com.winterframework.firefrog.game.exception.GameIssueStatusStopSaleException;
import com.winterframework.firefrog.game.exception.GameOrderOrPlanCodeExistErrorException;
import com.winterframework.firefrog.game.exception.GameOrderStatusErrorException;
import com.winterframework.firefrog.game.exception.UserBalErrorException;
import com.winterframework.firefrog.game.exception.UserTopAgentBetException;
import com.winterframework.firefrog.game.lock.config.mongo.service.LockSelector;
import com.winterframework.firefrog.game.lock.config.mongo.service.LotteryLockService;
import com.winterframework.firefrog.game.service.IGameFundRiskService;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.service.IGamePackageService;
import com.winterframework.firefrog.game.service.IGamePlanDetailService;
import com.winterframework.firefrog.game.service.IGamePlanService;
import com.winterframework.firefrog.game.service.IGameSeriesConfigService;
import com.winterframework.firefrog.game.service.IGameWarnIssueLogService;
import com.winterframework.firefrog.game.service.IGameWarnOrderService;
import com.winterframework.firefrog.game.service.order.utlis.GameAwardModeCheck;
import com.winterframework.firefrog.game.web.dto.GamePlanBetOriginDataStruc;
import com.winterframework.firefrog.game.web.dto.GamePlanQueryDTO;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.game.web.util.GameFundTypesUtils;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/**
 * 
 * @ClassName: GamePlanServiceImpl
 * @Description:追号计划服务实现类
 * @author Richard
 * @date 2013-12-27 下午4:25:23
 * 
 */
@Service("gamePlanServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GamePlanServiceImpl implements IGamePlanService {

	private Logger log = LoggerFactory.getLogger(GamePlanServiceImpl.class);

	@Resource(name = "gamePlanDaoImpl")
	private IGamePlanDao gamePlanDao;

	@Resource(name = "gamePlanDetailDaoImpl")
	private IGamePlanDetailDao gamePlanDetailDao;
	@Resource(name = "gamePlanDetailServiceImpl")
	private IGamePlanDetailService gamePlanDetailService;
	@Resource(name = "gameOrderDaoImpl")
	private IGameOrderDao gameOrderDao;

	@Resource(name = "gamePackageServiceImpl")
	private IGamePackageService gamePackageServiceImpl;

	@Resource(name = "SNUtil")
	private SNUtil snUtil;

	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueService;

	@Resource(name = "gameSlipDaoImpl")
	private IGameSlipDao gameSlipDao;

	@Resource(name = "gameSeriesConfigServiceImpl")
	private IGameSeriesConfigService configService;

	@Resource(name = "gameIssueDaoImpl")
	private IGameIssueDao gameIssueDao;

	@Resource(name = "gameControlEventDaoImpl")
	private IGameControlEventDao gameControlEventDao;

	@Resource(name = "gameFundRiskServiceImpl")
	private IGameFundRiskService fundRiskService;

	@Resource(name = "gamePackageDao")
	private IGamePackageDao gamePackageDao;

	@Resource(name = "gamePackageItemDao")
	private IGamePackageItemDao gamePackageItemDao;

	@Resource(name = "gameOrderServiceImpl")
	private IGameOrderService gameOrderService;

	@Resource(name = "gameUserAwardGroupDaoImpl")
	private IGameUserAwardGroupDao gameUserAwardGroupDao;

	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao userCustomerDao;

	@Resource(name = "gameReturnPointDaoImpl")
	private IGameReturnPointDao gameReturnPointDao;

	@Resource(name = "gameWarnIssueDaoImpl")
	private IGameWarnIssueDao gameWarnIssueDao;

	@Resource(name = "gameWarnIssueLogServiceImpl")
	private IGameWarnIssueLogService gameWarnIssueLogServiceImpl;

	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao customerDao;

	@Resource(name = "gamePlanServiceImpl")
	private IGamePlanService gamePlanService;

	@Resource(name = "gameWarnOrderServiceImpl")
	private IGameWarnOrderService gameWarnOrderServiceImpl;

	@Resource(name = "gameAwardModeCheck") 
	private GameAwardModeCheck gameAwardMode;
	
	@Resource(name = "RedisClient")
	private RedisClient rc;
	
	@Autowired
	private LockSelector selector;

	@Override
	public Long doBusiness(
			com.winterframework.firefrog.game.entity.GameOrder gameOrder,
			List<GamePlanBetOriginDataStruc> gamePlanBetOriginDataStruc,
			Long currentIssueCode) throws Exception {
		gameOrderService.setAwardGroup(gameOrder);
		checkGameAward(gameOrder,gamePlanBetOriginDataStruc);
		
		GamePlan gamePaln = (GamePlan) gameOrder.getGamePackage();
		if (customerDao.queryUserById(
				gameOrder.getGamePackage().getUser().getId()).getUserLevel() == 0) {// 总代不能投注
			throw new UserTopAgentBetException();
		}
		if (gamePaln.getPackageAmount() > gameOrderService.getUserBal(gamePaln
				.getUser().getId())) {// 判断余额是否足够
			throw new UserBalErrorException();
		}

		// 当前奖期号
		Long currIssueCode = gameOrder.getGameIssue().getIssueCode()
				.longValue();
		GameIssueEntity currentIssueInfo = gameIssueDao.queryGameIssue(
				gameOrder.getLottery().getLotteryId(), currentIssueCode);
		// 计算返点资金链和返点用户链，用于发送给审核中心
		// 追号返点一次发送所有期数返点总数据链，单次返点链保存在追号详情gamePlanDetail中
		String userChainAndRetPointChain = getGamePlanUserRetPoint(gameOrder);

		// 将gameOrder转换为gamePlan时 由于页面传回来的数据的是将追号分成了所有的追号注单，因此这里做了合并
		GamePlan gamePlan = VOConvert.convertGameOrder2GamePlan(gameOrder);

		GameIssueEntity gameIssueEntity = gameIssueDao.queryGameIssue(gamePlan
				.getLottery().getLotteryId(), gamePlan.getStartIsuueCode());
		// 判断追号的每一期都在可销售状态并且每一期的开始时间必须在当前期之后
		for (com.winterframework.firefrog.game.entity.GameSlip gslip : gameOrder
				.getSlipList()) {
			GameIssueEntity ge = gameIssueDao.queryGameIssue(gamePlan
					.getLottery().getLotteryId(), gslip.getIssueCode()
					.getIssueCode());
			if (ge.getIssueCode().longValue() != currIssueCode) {
				if (ge.getStatus().getValue() >= GameIssueEntity.STATUS_STOP_SALE
						|| ge.getSaleStartTime().before(
								currentIssueInfo.getSaleStartTime())) {
					throw new GameIssueStatusStopSaleException();
				}
			}
		}

		// 判断奖期是否暂停销售
		if (gameIssueEntity.getStatus().getValue() >= GameIssueEntity.STATUS_STOP_SALE) {
			throw new GameIssueStatusStopSaleException();
		}

		GamePackage gamePackage = new GamePackage();
		gamePackage = gamePackageServiceImpl.saveGamePackage(gameOrder,
				gamePlanBetOriginDataStruc);

		// GameIssueEntity gi =
		// gameIssueService.queryGameIssue(gameOrder.getLottery().getLotteryId(),
		// null);

		if (currentIssueCode.longValue() == currIssueCode) {// 获取当前奖期信息
			gamePlan.setFinishIssue(1);// 生成追号计划时完成期数设置为1
		} else {
			gamePlan.setFinishIssue(0);
			gamePlan.setSoldAmount(0L);
		}

		// 20140228 add
		gamePlan.setStartWebIssue(currentIssueInfo.getWebIssueCode());
		gamePlan.setPackageId(gamePackage.getPackageId());
		com.winterframework.firefrog.game.dao.vo.GamePlan vo = gamePlanDao
				.saveGamePlan(gamePlan);
		Long gamePlanId = vo.getId();
		gamePlan.setId(gamePlanId);
		gamePlan.setPlanCode(vo.getPlanCode());
		// 生成追号明细 假如需生成当前期的订单 返回相应的plandetailid
		for (GamePlanDetail gd : gamePlan.getGamePlanDetails()) {
			Double cancelFee = gameOrderService.getCancelOrderCharge(gameOrder
					.getLottery().getLotteryId(), Double.valueOf(gd
					.getTotamount() + ""));
			gd.setCancelFee(cancelFee.longValue());
		}

		Long gamePlanDetailId = gamePlanDetailDao.saveGamePlanDetailList(
				gamePlan.getGamePlanDetails(), gamePackage, gamePlan, gameOrder
						.getGamePackage().getPackageAmount(), currentIssueCode
						.longValue());

		// 判断是否为当前期，如果不是当前期，不生成注单,生成注单时保存用户的返点信息，由于追号时的orderDetails
		// 是追号所有期的数据，只需提取当前期的数据
		if (currentIssueCode.longValue() == currIssueCode) {// 获取当前奖期信息
			
			/*//ADD BY David 16.04.26 #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 : 計算單一注單返點 (BY USER) Start 
			List<com.winterframework.firefrog.game.entity.GameSlip> ods = new ArrayList<com.winterframework.firefrog.game.entity.GameSlip>();
			for (com.winterframework.firefrog.game.entity.GameSlip gd : gameOrder
					.getSlipList()) {
				gd.setGameOrder(gameOrder);
				if (gd.getIssueCode().getIssueCode().longValue() == currentIssueInfo
						.getIssueCode()) {
					gd.setGameOrder(gameOrder);
					ods.add(gd);
				}
			}
			//ADD BY David 16.04.26 #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 : 計算單一注單返點 (BY USER) End */
			
			// 注意订单金额应该是方案金额 而不是追号总金额
			GameOrder order = createGameOrder(gameOrder, gamePlan,
					gamePlanDetailId, gamePackage.getPackageId());
			order.setEndCanCancelTime(getEndCanCancelTime(
					currentIssueInfo.getSaleEndTime(), order.getLotteryid()));
			// 生成订单
			GameSeriesConfigEntity gse = configService
					.getSeriesConfigByLotteryId(order.getLotteryid());

			order.setAdminCanCancelTime(gse.getIssuewarnBackoutTime());
			Long id = gameOrderDao.saveGameOrder(order);
			gameOrder.setSaleTime(order.getSaleTime());
			gameOrder.setId(id);
			gameOrder.setTotalAmount(order.getTotamount());
			gameOrder.getGameIssue().setWebIssueCode(currentIssueInfo.getWebIssueCode());
			gameOrder.setOrderCode(order.getOrderCode());
			
			//ADD BY David 16.04.26 #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 : 計算單一注單返點 (BY USER) Start 
			com.winterframework.firefrog.game.entity.GameOrder go = gameOrder;
			go.setGamePackage(gamePackage);
			List<com.winterframework.firefrog.game.entity.GameSlip> ods = new ArrayList<com.winterframework.firefrog.game.entity.GameSlip>();
			for (com.winterframework.firefrog.game.entity.GameSlip gd : gameOrder
					.getSlipList()) {
				if (gd.getIssueCode().getIssueCode().longValue() == currentIssueInfo.getIssueCode()) {
					gd.setGameOrder(go); //紀錄  注單返點					
					ods.add(gd);
				}
			}
			//ADD BY David 16.04.26 #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 : 計算單一注單返點 (BY USER) End
			
			gameSlipDao.saveSlipList(ods, gamePackage, id, gamePlanDetailId);
			// 保存当前期的订单返点数据到数据库
			gameReturnPointDao.saveGameOrderUserReturnPoint(ods, id);

		}
		
		// 当产生订单时候,对投注资金进行冻结,同时对返点进行冻结,发送给审核中心调用fundRiskService
		String[] temp = userChainAndRetPointChain.split("/");// 获取返点用户链 和返点金额链

		// 返点
		List<TORiskDTO> toRiskDTOList = new ArrayList<TORiskDTO>();
		// 投注DTO生成
		TORiskDTO freezerPlanDTO = new TORiskDTO();
		freezerPlanDTO.setAmount(gamePlan.getPackageAmount() + "");
		freezerPlanDTO.setIssueCode(gamePlan.getStartIsuueCode());
		freezerPlanDTO.setLotteryid(gamePlan.getLottery().getLotteryId());
		freezerPlanDTO.setPlanCodeList(gamePlan.getPlanCode());
		freezerPlanDTO
				.setType(GameFundTypesUtils.GAME_PLAN_FREEZER_DETEAIL_TYPE);
		freezerPlanDTO.setUserid(gamePlan.getUser().getId() + "");

		// 返点DTO生成
		TORiskDTO retPointDTO = new TORiskDTO();
		retPointDTO.setAmount(temp[1]);
		retPointDTO.setIssueCode(gamePlan.getStartIsuueCode());
		retPointDTO.setLotteryid(gamePlan.getLottery().getLotteryId());
		retPointDTO.setPlanCodeList(gamePlan.getPlanCode());
		retPointDTO.setType(GameFundTypesUtils.GAME_RET_FREEZER_DETEAIL_TYPE);
		retPointDTO.setUserid(temp[0]);

		toRiskDTOList.add(freezerPlanDTO);
		toRiskDTOList.add(retPointDTO);

		// 调用风控资金冻结接口
		 fundRiskService.betAmountFreezer(toRiskDTOList);
		return gamePlanId;
	}

	private Date getEndCanCancelTime(Date saleEndTime, Long lotteryId)
			throws Exception {
		GameSeriesConfigEntity gse = configService
				.getSeriesConfigByLotteryId(lotteryId);
		Long cancelTime = gse.getIssuewarnUserBackoutTime();// 单位为秒
		if (cancelTime != null) {
			return DateUtils.addSeconds(saleEndTime, -cancelTime.intValue());
		} else {
			return saleEndTime;
		}
	}

	private String getGamePlanUserRetPoint(
			com.winterframework.firefrog.game.entity.GameOrder gameOrder)
			throws Exception {
		// 按订单保存用户层级链的返点信息
		User user = userCustomerDao.queryUserById(gameOrder.getGamePackage()
				.getUser().getId());
		String userChain = user.getUserProfile().getUserChain();
		return gameReturnPointDao.getUserChainAndRetunPointChain(
				gameOrder.getSlipList(), userChain);
	}

	private GameOrder createGameOrder(
			com.winterframework.firefrog.game.entity.GameOrder order,
			GamePlan gamePlan, Long gamePlanDetailId, Long parentId)
			throws Exception {

		GameOrder gameOrder = new GameOrder();
		gameOrder.setParentid(parentId);
		gameOrder.setUserid(gamePlan.getUser().getId());
		gameOrder.setIssueCode(gamePlan.getStartIsuueCode());
		gameOrder.setLotteryid(gamePlan.getLottery().getLotteryId());
		gameOrder.setTotamount(order.getTotalAmount());
		gameOrder.setStatus(OrderStatus.WAITING.getValue());
		gameOrder.setOrderTime(new Date());
		gameOrder.setSaleTime(order.getSaleTime());
		gameOrder.setCancelModes(new Integer(CancelMode.DEFAULTS.getValue())
				.intValue());
		GameIssueEntity ge = gameIssueService.getGameIssue(gamePlan
				.getLottery().getLotteryId(), gamePlan.getStartIsuueCode());
		String orderCode = snUtil.createSN(SNUtil.TYPE_ORDER, gamePlan
				.getLottery().getLotteryId(), ge.getWebIssueCode());
		// 校验订单编号是否存在
		List<GameOrder> gameOrderList = gameOrderDao
				.getGameOrderByOrderCode(orderCode);
		if (gameOrderList != null && !gameOrderList.isEmpty()) {
			log.error("方案编号，订单编号或追号编号已经存在:" + orderCode);
			throw new GameOrderOrPlanCodeExistErrorException();
		}
		Double cancelFee = gameOrderService.getCancelOrderCharge(
				gameOrder.getLotteryid(),
				Double.valueOf(gameOrder.getTotamount() + ""));
		gameOrder.setCancelFee(cancelFee.longValue());
		gameOrder.setOrderCode(orderCode);
		gameOrder.setFileMode(order.getFileMode().getValue());
		gameOrder.setPlanId(gamePlan.getId());
		gameOrder.setPlanDetailId(gamePlanDetailId);
		gameOrder.setAwardGroupId(order.getAwardGroupId());
		return gameOrder;
	}

	@Override
	public GamePlan queryPlanDetail() throws Exception {
		return null;
	}

	/**
	 * Title: stopGamePlan Description:
	 * 
	 * @param planId
	 * @param cancelTime
	 * @param userId
	 * @param userType
	 * @throws Exception
	 * @see com.winterframework.firefrog.game.service.IGamePlanService#stopGamePlan(java.lang.Long,
	 *      java.lang.Long, java.lang.Long, java.lang.Integer)
	 */
	@Override
	public void stopGamePlan(Long planId, Long cancelTime, Long userId,
			Integer userType) throws Exception {
		boolean isFrontUser=(userType!=null && userType !=2);
		GamePlan gamePlanEn = gamePlanDao.getPlanById(planId);
		// 当前操作是系统终止追号操作，不需要做此判断
		if (userType != 2) {
			if (gamePlanEn.getUser().getId().longValue() != userId.longValue()) {// 防止非追号用户终止订单，另外因非订单用户无法查看订单详情信息，可以无法终止追号，此处判断以防万一
				log.error("追号用户UserId 不等于 操作用户UserId");
				return;
			}
		}

		if (gamePlanEn.getStatus().getValue() < 2) {// 当追号状态为已结束或者已终止状态时
			List<GameWarnOrder> wos = gameWarnOrderServiceImpl
					.getUndealGameWarnOrderByPlanId(planId);
			Date lastStopTime = gamePlanService.getLastPlanCanStopTime(planId);
			if (lastStopTime.before(new Date())) {// 当当前时间在最后一期结束销售时间之后时，不能终止追号
				throw new GameOrderStatusErrorException();
			}
			if (gamePlanEn.getStopMode().getValue() > 0) {// 当为盈利停止或者追中即停时，当有未审核的风险订单时
															// 终止追号不可用
				if (wos != null && !wos.isEmpty()) {
					throw new GameOrderStatusErrorException();
				}
			}
 
			//查看是否在開獎，開獎中撤銷訂單失敗
			boolean bo = gameIssueDao.getGameIssueIsOpenAward(gamePlanEn.getLottery().getLotteryId());
			if(bo){
				throw new GameIssueISOpenAwardException();
			}
			Date date = DateUtils.currentDate();
			// 获取未执行的追号记录列表 撤销只能撤销奖期在售或者待销售的追号单，并且奖期结束时间上不能早于当前
			List<GamePlanDetail> unExeuPlanDetails = gamePlanDetailDao
					.getUnExeuPlanDetailsByPlanIdDate(planId,date);
						// 2. 更新追号明细状态
			int count = gamePlanDetailDao.updateGamePlanDetailByPlanIdDate(planId,
					userId + "",date);
			List<GamePlanDetail> noCancelPlanDetails = gamePlanDetailDao.queryGamePlanDetailByNoExecute(planId);

			com.winterframework.firefrog.game.dao.vo.GamePlan gamePlan = gamePlanDao
					.getById(planId);
			Map<String,Long> summaryMap=gamePlanDetailService.getSummary(planId);
			if(summaryMap!=null){
				if(noCancelPlanDetails.size() <= 0){
					gamePlan.setStatus(com.winterframework.firefrog.game.dao.vo.GamePlan.Status.STOP
							.getValue()); 
				}
				gamePlan.setCancelModes(userType == null ? 0 : userType); // 撤单(1用户、2系统)
				gamePlan.setCancelTime(DataConverterUtil.convertLong2Date(cancelTime));
				Long soldAmount=summaryMap.get("soldAmount");
				Long finishedCount=summaryMap.get("finishedCount");
				Long canceledAmount=summaryMap.get("canceledAmount"); 
				Long canceledCount=summaryMap.get("canceledCount"); 
				
				gamePlan.setCanceledAmount(canceledAmount);
				gamePlan.setCancelIssue(canceledCount.intValue()); 
				gamePlan.setFinishIssue(finishedCount.intValue());
				gamePlan.setSoldAmount(soldAmount);  
				gamePlan.setUpdateTime(DateUtils.currentDate()); 
				gamePlanDao.update(gamePlan);
			}  
						
			
			// 资金发送审核
			if(count > 0){
				if (!unExeuPlanDetails.isEmpty()) {
					List<TORiskDTO> toRiskDTOList = new ArrayList<TORiskDTO>();
					Long totalCancelAmount = 0L;// 撤销的追号总金额
					String returnPointChain = "";
					Long lotteryId = gamePlan.getLotteryid();

					for (GamePlanDetail gd : unExeuPlanDetails) {

						totalCancelAmount += gd.getTotamount().longValue();

						if(isFrontUser){
							if (gd.getCancelFee() != null && gd.getCancelFee() > 0) {
								TORiskDTO cancelFeeDTO = new TORiskDTO();
								cancelFeeDTO.setAmount(gd.getCancelFee().longValue()
										+ "");
								cancelFeeDTO.setIssueCode(gamePlanEn
										.getStartIsuueCode());
								cancelFeeDTO.setLotteryid(gamePlanEn.getLottery()
										.getLotteryId());
								cancelFeeDTO.setPlanCodeList(gamePlanEn.getPlanCode());
								cancelFeeDTO
										.setType(GameFundTypesUtils.GAME_CANCEL_FEE_DETEAIL_TYPE);
								cancelFeeDTO.setUserid(gamePlanEn.getUser().getId()
										+ "");
								toRiskDTOList.add(cancelFeeDTO);
							}
						}

						// 封锁撤销。
						LotteryLockService lockService = selector
								.getRealService(lotteryId);
						if (lockService != null) {
							lockService.undoGamePlanDetail(gd.getGameIssue()
									.getIssueCode(), planId);
						}
					}
					// 投注DTO生成
					TORiskDTO unFreezerPlanDTO = new TORiskDTO();
					unFreezerPlanDTO.setAmount(totalCancelAmount + "");
					unFreezerPlanDTO.setIssueCode(gamePlanEn.getStartIsuueCode());
					unFreezerPlanDTO.setLotteryid(gamePlanEn.getLottery()
							.getLotteryId());
					unFreezerPlanDTO.setPlanCodeList(gamePlanEn.getPlanCode());
					unFreezerPlanDTO
							.setType(isFrontUser ? GameFundTypesUtils.GAME_PLAN_RESERVE_UNFREEZEN_DETEAIL_TYPE
									: GameFundTypesUtils.GAME_ADMIN_UNFREEZER_TYPE);
					unFreezerPlanDTO.setUserid(gamePlanEn.getUser().getId() + "");

					// bug1359,1428：终止追号 之前返点金额并没有发送，不用做终止追号撤销
					// 返点DTO生成
					/*
					 * TORiskDTO retPointDTO = new TORiskDTO();
					 * retPointDTO.setAmount(returnPointChain);
					 * retPointDTO.setIssueCode(gamePlanEn.getStartIsuueCode());
					 * retPointDTO
					 * .setLotteryid(gamePlanEn.getLottery().getLotteryId());
					 * retPointDTO.setPlanCodeList(gamePlanEn.getPlanCode());
					 * retPointDTO
					 * .setType(GameFundTypesUtils.GAME_CANCEL_RET_DETEAIL_TYPE); //
					 * retPointDTO
					 * .setUserid(unExeuPlanDetails.get(0).getRetUserChain());
					 * toRiskDTOList.add(retPointDTO);
					 */

					// if (userType == null || userType.intValue() == 1) {
					// Double amount = 0.00D;
					// GameSeriesConfigEntity gse =
					// configService.getSeriesConfigByLotteryId(gamePlanEn.getLottery()
					// .getLotteryId());
					// if (gse == null) {
					// amount = 0.00D;
					// } else {
					// if (totalCancelAmount.longValue() <=
					// gse.getBackoutStartFee().longValue()) {
					// amount = 0.00D;
					// } else {
					// DecimalFormat df = new DecimalFormat(".00");
					// //后台撤单时获取撤单手续费，因为金额和撤单率 都*10000 因此需要除以一个10000
					// amount = Double.valueOf(df.format(totalCancelAmount *
					// gse.getBackoutRatio() / 10000.00));
					// }
					// }
					//
					// if (amount > 0) {
					// //撤单手续费DTO生成
					// TORiskDTO cancelFeeDTO = new TORiskDTO();
					// cancelFeeDTO.setAmount(amount.longValue() + "");
					// cancelFeeDTO.setIssueCode(gamePlanEn.getStartIsuueCode());
					// cancelFeeDTO.setLotteryid(gamePlanEn.getLottery().getLotteryId());
					// cancelFeeDTO.setPlanCodeList(gamePlanEn.getPlanCode());
					// cancelFeeDTO.setType(GameFundTypesUtils.GAME_CANCEL_FEE_DETEAIL_TYPE);
					// cancelFeeDTO.setUserid(gamePlanEn.getUser().getId() + "");
					// toRiskDTOList.add(cancelFeeDTO);
					// }
					// }

					toRiskDTOList.add(unFreezerPlanDTO);

					// 调用风控资金冻结接口
					fundRiskService.cancelFee(toRiskDTOList);
				}
			}else{
				throw new GameOrderStatusErrorException();// 撤销时 如果发现订单状态不对，给用户提示
			}
			
		} else {
			throw new GameOrderStatusErrorException();// 撤销时 如果发现订单状态不对，给用户提示
		}

	}

	@Override
	public void reservationCalled(Long planId, Long issueCode, Long userId,
			Integer userType) throws Exception { 
		boolean isFrontUser=(userType!=null && userType !=2);
		com.winterframework.firefrog.game.dao.vo.GamePlanDetail gamePlanDetail = gamePlanDetailDao
				.getGamePlanDetailByPlanIdAndIssueCode(planId, issueCode);
		com.winterframework.firefrog.game.dao.vo.GamePlan gamePlan = gamePlanDao
				.getPlanVoById(planId); 
		if(gamePlanDetail.getStatus() != GamePlanDetail.GamePlanDetailStatus.WAIT_CANCEL.getValue()){
			boolean bo = gameIssueDao.getGameIssueIsOpenAward(gamePlan.getLotteryid());
			if(bo){
				throw new GameIssueISOpenAwardException();
			}
			List<String> issueList  = new ArrayList<String>();
			issueList.add(issueCode.toString());
			int count = gamePlanDetailDao.updateReservationCalledGamePlanDetailStatusByPlanIdAndIssueCode(planId, issueList, userId + "");
			log.debug("變更追號單為待撤銷狀態 planId:"+planId+",issueCode:"+issueCode);
			if(count == 0){
				log.error("撤銷失敗 planid:"+planId+",issueCode:"+issueCode);
				throw new Exception("更改狀態失敗");
			}
		}
		// 封锁撤销
		LotteryLockService lockService = selector.getRealService(gamePlan
				.getLotteryid());
		if (lockService != null) {
			lockService.undoGamePlanDetail(issueCode, planId);
		}
		
		
		int count = gamePlanDetailDao
				.updateReservationCalledGamePlanDetailByPlanIdAndIssueCode(
						planId, issueCode, userId + "");
		Map<String,Long> summaryMap=gamePlanDetailService.getSummary(planId);
		if(summaryMap!=null){
			Long soldAmount=summaryMap.get("soldAmount");
			Long finishedCount=summaryMap.get("finishedCount");
			Long canceledAmount=summaryMap.get("canceledAmount"); 
			Long canceledCount=summaryMap.get("canceledCount"); 
			
			gamePlan.setCanceledAmount(canceledAmount);
			gamePlan.setCancelIssue(canceledCount.intValue()); 
			gamePlan.setFinishIssue(finishedCount.intValue());
			gamePlan.setSoldAmount(soldAmount);
			if(canceledCount.intValue()+finishedCount.intValue()==gamePlan.getTotalIssue().intValue()){
				gamePlan.setStatus(com.winterframework.firefrog.game.dao.vo.GamePlan.Status.FINISH
						.getValue());
			}
			gamePlan.setUpdateTime(DateUtils.currentDate()); 
			gamePlanDao.update(gamePlan);
		}
		
		if (count > 0) {
			GameOrder order = gameOrderDao.getOrderByPlanIdAndIssueCode(planId,
					issueCode);
			// 2.解冻当前的金额
			List<TORiskDTO> toRiskDTOList = new ArrayList<TORiskDTO>();
			// 投注DTO生成
			TORiskDTO unFreezerPlanDTO = new TORiskDTO();
			unFreezerPlanDTO.setAmount(gamePlanDetail.getTotamount() + "");
			unFreezerPlanDTO.setIssueCode(gamePlanDetail.getIssueCode());
			unFreezerPlanDTO.setLotteryid(gamePlan.getLotteryid());
			unFreezerPlanDTO.setPlanCodeList(gamePlan.getPlanCode());
			if (order != null) {
				unFreezerPlanDTO.setOrderCodeList(order.getOrderCode());
			}
			unFreezerPlanDTO
					.setType(isFrontUser ? GameFundTypesUtils.GAME_PLAN_RESERVE_UNFREEZEN_DETEAIL_TYPE
							: GameFundTypesUtils.GAME_ADMIN_UNFREEZER_TYPE);
			unFreezerPlanDTO.setUserid(gamePlan.getUserId() + "");
			toRiskDTOList.add(unFreezerPlanDTO);

			if (isFrontUser) {
				Double amount = 0.00D;
				GameSeriesConfigEntity gse = configService
						.getSeriesConfigByLotteryId(gamePlan.getLotteryid());
				if (gse == null) {
					amount = 0.00D;
				} else {
					if (gamePlanDetail.getTotamount().longValue() <= gse
							.getBackoutStartFee().longValue()) {
						amount = 0.00D;
					} else {
						DecimalFormat df = new DecimalFormat(".00");
						// 后台撤单时获取撤单手续费，因为金额和撤单率 都*10000 因此需要除以一个10000
						amount = Double.valueOf(df.format(gamePlanDetail
								.getTotamount()
								* gse.getBackoutRatio()
								/ 10000.00));
					}
				}

				if (amount > 0) {
					// 撤单手续费DTO生成
					TORiskDTO cancelFeeDTO = new TORiskDTO();
					cancelFeeDTO.setAmount(amount.longValue() + "");
					cancelFeeDTO.setIssueCode(gamePlanDetail.getIssueCode());
					cancelFeeDTO.setLotteryid(gamePlan.getLotteryid());
					cancelFeeDTO.setPlanCodeList(gamePlan.getPlanCode());
					if (order != null) {
						cancelFeeDTO.setOrderCodeList(order.getOrderCode());
					}
					cancelFeeDTO
							.setType(GameFundTypesUtils.GAME_CANCEL_FEE_DETEAIL_TYPE);
					cancelFeeDTO.setUserid(gamePlan.getUserId() + "");
					toRiskDTOList.add(cancelFeeDTO);
				}
			}

			// 调用风控资金冻结接口
			fundRiskService.cancelFee(toRiskDTOList);
			//redis新增撤銷記錄，避免開獎task多退一筆
			rc.set("cancelPlanDetail"+gamePlanDetail.getId(), "1", 600);
		}
	}
	
	@Override
	public void reservationCalledChangeStatus(Long planId, String[] issueCode, Long userId,
			Integer userType) throws Exception {
		
		com.winterframework.firefrog.game.dao.vo.GamePlan gamePlan = gamePlanDao
				.getPlanVoById(planId); 
		boolean bo = gameIssueDao.getGameIssueIsOpenAward(gamePlan.getLotteryid());
		if(bo){
			throw new GameIssueISOpenAwardException();
		}
		List<String> issueList  = Arrays.asList(issueCode);
		int count = gamePlanDetailDao.updateReservationCalledGamePlanDetailStatusByPlanIdAndIssueCode(planId, issueList, userId + "");
		log.info("變更追號單為待撤銷狀態"+count+"筆,planId:"+planId);
	}

	@Override
	public Page<GamePlan> queryPlans(PageRequest<GamePlanQueryDTO> pr)
			throws Exception {
		Page<GamePlan> page = gamePlanDao.getPlans(pr);
		return page;
	}

	@Override
	public GamePlan queryPlanById(Long planId, Long userId) {
		GamePlan gamePlan = gamePlanDao
				.getPlanByUserIdAndPlanId(planId, userId);
		return gamePlan;
	}

	@Override
	public List<GamePlanDetail> queryPlanDetailsByPlanId(Long planId) {
		List<GamePlanDetail> gamePlanDetails = gamePlanDetailDao
				.getPlanDetailsByPlanId(planId);
		return gamePlanDetails;
	}

	@Override
	public Long queryPackageIdByPlanId(Long planId) {
		return gamePlanDao.getPackageIdByPlanId(planId);
	}

	@Override
	public void cancelFollowGamePlan(Long lotteryid, String startIssueCode,
			String endIssueCode, GameWarnIssueLog warnIssueLog)
			throws Exception {
		GameIssueEntity startIssue = gameIssueDao.queryGameIssue(lotteryid,
				startIssueCode);
		GameIssueEntity endIssue = gameIssueDao.queryGameIssue(lotteryid,
				endIssueCode);

		// 添加奖期警告表
		GameWarnIssue warn = new GameWarnIssue();
		warn.setIssueCode(startIssue.getIssueCode());
		warn.setLotteryid(lotteryid);
		warn.setWebIssueCode(startIssue.getWebIssueCode());
		warn.setIssueWarnId(warnIssueLog.getEvent());
		warn.setCreateTime(new Date());
		warn.setUpdateTime(warn.getCreateTime());
		warn.setReadFlag(0L);
		warn.setStatus(2L);
		warn.setStatusRout(warnIssueLog.getEvent());
		gameWarnIssueDao.updateIfHave(warn);
		gameWarnIssueLogServiceImpl.addWarnIssueLog(warnIssueLog);

		// 添加"撤销后续追号"待办任务
		GameControlEvent event = new GameControlEvent();
		event.setLotteryid(lotteryid);
		event.setStartIssueCode(startIssue.getIssueCode());
		event.setEndIssueCode(endIssue.getIssueCode());
		event.setSaleStartTime(startIssue.getSaleStartTime());
		event.setSaleEndTime(endIssue.getSaleEndTime());
		event.setStatus(0L);
		event.setCreateTime(new Date());
		event.setEnentType(5L);
		if (warn.getId() == null) {
			event.setWarnIssueId(-1L * warnIssueLog.getId());
		} else {
			event.setWarnIssueId(warn.getId());
		}
		event.setMessage(warnIssueLog.getDisposeInfo());
		event.setCreator(warnIssueLog.getCreator());
		gameControlEventDao.addControlEvent(event);
	}

	/**
	 * 
	 * @Title: generateGamePlanByIssueCode
	 * @Description: 根据奖期信息，执行追号计划。
	 * @param issueCode
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public void generateGamePlanByIssueCode(Long issueCode, Long lotteryId)
			throws Exception {

		log.info("开始执行追号计划，issueCode=" + issueCode + ", lotteryId ="
				+ lotteryId);
		List<com.winterframework.firefrog.game.dao.vo.GamePlanDetail> detailList = new ArrayList<com.winterframework.firefrog.game.dao.vo.GamePlanDetail>();
		detailList = gamePlanDetailDao.queryGamePlanDetailByIssueCode(
				issueCode, lotteryId);

		if (!detailList.isEmpty()) {
			for (com.winterframework.firefrog.game.dao.vo.GamePlanDetail detail : detailList) {

				log.info("获取追号计划信息，gamePlanId =" + detail.getPlanid());
				// 判断是否要执行追号，
				com.winterframework.firefrog.game.dao.vo.GamePlan plan = gamePlanDao
						.getById(detail.getPlanid());
				if (checkIsExceuteGamePlan(plan)) {
					// 执行追号计划
					generateGamePlan(detail);
				}
			}
		}
	}

	/**
	 * 
	 * @Title: checkIsExceuteGamePlan
	 * @Description: 检查并更新追号状态。
	 * @param plan
	 * @return
	 * @throws Exception
	 */
	private boolean checkIsExceuteGamePlan(
			com.winterframework.firefrog.game.dao.vo.GamePlan plan)
			throws Exception {
		log.info("更新追号计划表中奖信息。");

		boolean b = false;

		if (null == plan) {
			log.error("获取追号计划信息失败, plan 不能为null");
			return b;
		}

		// 20140212 add 追号计划为暂停状态时，不进行追号
		if (plan.getStatus().intValue() == 4) {

			log.info("该追号计划为暂停暂停，planId=" + plan.getId());
			return b;
		}

		BigDecimal totalWinAmount = plan.getWinAmount() == null ? BigDecimal.ZERO
				: new BigDecimal(plan.getWinAmount());
		BigDecimal stopAmount = plan.getStopParms() == null ? BigDecimal.ZERO
				: new BigDecimal(plan.getStopParms());
		plan.setWinAmount(totalWinAmount.longValue());
		// 停止方式 0.不停止， 1.按累计盈利停止， 2.中奖即停。
		if (totalWinAmount.longValue() > 0 && plan.getStopMode() == 2) {
			// 中奖即停
			plan.setStatus(com.winterframework.firefrog.game.dao.vo.GamePlan.Status.FINISH
					.getValue());// 已终止。
			plan.setCancelTime(new Date());
			plan.setCancelModes(2); // 系统终止。
			plan.setUpdateTime(DateUtils.currentDate());
			log.info("追号计划的停止模式为中奖即停，将追号计划停止。");
			gamePlanDao.update(plan);

			log.info("更新追号计划明细状态为已撤销状态");
			gamePlanDetailDao.updateGamePlanDetailByPlanId(plan.getId(), -1L
					+ "");

		} else if (plan.getStopMode() == 1) {
			// 按累计盈利停止
			if (totalWinAmount.compareTo(stopAmount) >= 0) {
				plan.setStatus(3);// 已终止
				plan.setCancelTime(new Date());
				plan.setCancelModes(2); // 系统终止。
				log.info("追号计划按累计盈利停止，中奖金额已超过停止参数，将追号计划停止。");
				gamePlanDao.update(plan);

				log.info("更新追号计划明细状态为已撤销状态");
				gamePlanDetailDao.updateGamePlanDetailByPlanId(plan.getId(),
						-1L + "");
			} else {
				b = true;
			}
		} else if (plan.getStopMode() == 0 || totalWinAmount.intValue() == 0) {// 20140211
																				// edit
																				// 更新中奖即停不执行追号计划问题。
			b = true;
		}

		return b;
	}

	/**
	 * 执行订单追号计划，有任务项目进行调用。 Title: generateGamePlan Description:
	 * 
	 * @param planId
	 * @param issueCode
	 * @throws Exception
	 * @see com.winterframework.firefrog.game.service.IGamePlanService#generateGamePlan(java.lang.Long,
	 *      java.lang.Long)
	 */
	private void generateGamePlan(
			com.winterframework.firefrog.game.dao.vo.GamePlanDetail detail)
			throws Exception {

		log.info("进入生成追号计划方法。game_plan_detail.id=" + detail.getId());
		// 1.获取追号信息
		com.winterframework.firefrog.game.dao.vo.GamePlan gamePlanVo = gamePlanDao
				.getById(detail.getPlanid());

		if (null == gamePlanVo) {
			log.error("无法获取gamePlan 信息， planId = " + detail.getPlanid());
			return;
		}

		/*
		 * //2。获取追号明细信息。 com.winterframework.firefrog.game.dao.vo.GamePlanDetail
		 * detail = gamePlanDetailDao.queryGamePlanDetailByPlanID(planId,
		 * issueCode);
		 * 
		 * if (null == detail) { log.info("彩种【" + gamePlanVo.getLotteryid() +
		 * "】，期号【" + issueCode + "】无明细信息！，"); //更新追号计划已完成。
		 *//**
		 * 可能出现奖期错误的问题， gamePlanVo.setStatus(2); //已结束
		 * log.info("更新追号计划为已结束状态，gamePlan.id" + planId);
		 * gamePlanVo.setCancelModes(2); //系统 gamePlanDao.update(gamePlanVo);
		 **/
		/*
		 * return; }
		 */

		log.info("开始生成订单信息。 gamePlanId =" + gamePlanVo.getId()
				+ ", gamePlanDetailId =" + detail.getId() + ", issue_code = "
				+ detail.getIssueCode());
		// 3.生成订单
		createGameOrderEntity(gamePlanVo, detail);

		log.info("开始生成追号信息，gamePlanId=" + gamePlanVo.getId()
				+ ", gamePlanDetailId =" + detail.getId());
		// 4.更新追号信息
		updateGamePlan(gamePlanVo, detail);

		log.info("开始更新追号明细信息，gamePlanDetailId= " + detail.getPlanid()
				+ ", gamePlandDetail_issueCode = " + detail.getIssueCode());
		// 5.更新追号明细信息。
		updateGamePlanDetail(detail.getPlanid(), detail.getIssueCode(), 1);

	}

	/**
	 * 
	 * @Title: updateGamePlanDetail
	 * @Description: 更新追号明细信息及追号计划状态
	 * @param result
	 * @param plan
	 */
	private void updateGamePlanDetail(Long planId, Long issueCode,
			Integer status) throws Exception {

		log.info("更新追号明细信息， gamePlan.Id = " + planId + ", issueCode = "
				+ issueCode);
		gamePlanDetailDao.updateGamePlanDetailByPlanID(planId, issueCode,
				status);
	}

	/**
	 * 
	 * @Title: updateGamePlan
	 * @Description: 更新追号计划信息。
	 * @param plan
	 * @param detail
	 */
	private void updateGamePlan(
			com.winterframework.firefrog.game.dao.vo.GamePlan plan,
			com.winterframework.firefrog.game.dao.vo.GamePlanDetail detail) {

		log.info("更新追号计划信息， planId =" + plan.getId() + ", planDetail.id="
				+ detail.getId());
		// 在这里只是进行追号，能进入这里表明其追号计划未完成，
		// 当前的已完成的奖期数。
		Integer currentIssue = (plan.getFinishIssue() == null ? 0 : plan
				.getFinishIssue()) + 1;
		plan.setFinishIssue(currentIssue);

		log.info("更新追号计划信息， planId =" + plan.getId() + ", planDetail.id="
				+ detail.getId() + ", currentIssue=" + currentIssue);
		// 取消参数
		Integer cancelIssue = plan.getCancelIssue() == null ? 0 : plan
				.getCancelIssue();
		// 总期数
		Integer totalIssue = plan.getTotalIssue();
		log.info("更新追号计划信息， planId =" + plan.getId() + ", planDetail.id="
				+ detail.getId() + ", totalIssue=" + totalIssue);
		// 销售完成金额
		BigDecimal soldAmount = new BigDecimal(plan.getSoldAmount() == null ? 0
				: plan.getSoldAmount());

		log.info("更新追号计划信息， planId =" + plan.getId() + ", planDetail.id="
				+ detail.getId() + ",soldAmount =" + soldAmount);
		BigDecimal total = new BigDecimal(detail.getTotamount())
				.add(soldAmount);
		plan.setSoldAmount(total.longValue());

		if (totalIssue - cancelIssue <= currentIssue) {
			// 更新追号计划已完成。
			plan.setStatus(com.winterframework.firefrog.game.dao.vo.GamePlan.Status.FINISH
					.getValue()); // 已结束
			log.info("更新追号计划为已结束状态，gamePlan.id" + plan.getId());
		}
		plan.setUpdateTime(DateUtils.currentDate());
		gamePlanDao.update(plan);
		log.info("更新追号计划信息， planId =" + plan.getId() + ", planDetail.id="
				+ detail.getId() + ",更新成功");
	}

	/**
	 * 
	 * @Title: createGameOrder
	 * @Description: 根据追号和明细信息生成订单信息。
	 * @param gamePlanVo
	 * @param detail
	 * @return
	 * @throws Exception
	 */
	private com.winterframework.firefrog.game.entity.GameOrder createGameOrderEntity(
			com.winterframework.firefrog.game.dao.vo.GamePlan plan,
			com.winterframework.firefrog.game.dao.vo.GamePlanDetail detail)
			throws Exception {

		com.winterframework.firefrog.game.entity.GameOrder order = new com.winterframework.firefrog.game.entity.GameOrder();
		// 生成Game_order数据
		User user = new User();

		com.winterframework.firefrog.game.dao.vo.GamePackage gamePackageVo = gamePackageDao
				.getById(plan.getPackageId());
		user.setId(gamePackageVo.getUserid());
		GamePackage packageEntity = new GamePackage();
		packageEntity.setUser(user);
		packageEntity.setFileMode(EnumTypeConverts.converFileMode(gamePackageVo
				.getFileMode()));
		order.setFileMode(EnumTypeConverts.converFileMode(gamePackageVo
				.getFileMode()));
		order.setGamePackage(packageEntity);
		order.setGameIssue(new GameIssueEntity(detail.getIssueCode()));
		order.setTotalAmount(packageEntity.getPackageAmount());
		Lottery lottery = new Lottery();
		lottery.setLotteryId(plan.getLotteryid());
		order.setLottery(lottery);

		order.setTotalAmount(detail.getTotamount());
		order.setStatus(OrderStatus.WAITING);
		order.setSaleTime(new Date());
		order.setCancelModes(CancelMode.DEFAULTS);
		order.setParentType(OrderParentType.PLAN);
		order.setParentid(gamePackageVo.getId());

		// 根据PlanId 先获取gamePackage(VO)
		// 获取第一期的方案记录信息，
		com.winterframework.firefrog.game.dao.vo.GamePackage packe = gamePackageDao
				.queryGamePackageByPlanId(plan.getId(), lottery.getLotteryId(),
						plan.getStartIsuueCode());
		order.setAwardGroupId(packe.getAwardId());
		log.info("追号计划生成订单，gamePlanId = " + plan.getId() + ", planDetailId ="
				+ detail.getId() + ",packId=" + packe.getId());

		// 原方案金额不一定合适现在的方案金额。可能存在按倍速追加
		// order.setPackageAmount(packe.getPackageAmount());

		List<com.winterframework.firefrog.game.entity.GameSlip> gameOrderDetail = new ArrayList<com.winterframework.firefrog.game.entity.GameSlip>();
		// 根据GamePackage 获取gamePackageItem 信息
		List<GamePackageItem> itemList = gamePackageItemDao
				.getPackageItemListByPackageId(packe.getId());

		// 开始生成OrderDetail 信息;
		for (GamePackageItem item : itemList) {
			log.info("追号计划生成订单，gamePlanId = " + plan.getId()
					+ ", planDetailId =" + detail.getId() + ",packItemId="
					+ item.getId());
			if (item.getFileMode() == 1) {
				// 文件模式将文件内容读取出来，读取方式直接读取有误问题？
				File file = new File(item.getBetDetail());
				String content = null;
				try {
					content = FileUtils.readFileToString(file);
				} catch (IOException e) {
					log.error("读取gamePackageItem投注内容错误，itemId=" + item.getId()
							+ e);
					throw new RuntimeException("读取gamePackageItem投注内容错误");
				}
				item.setBetDetail(content);
			}
			gameOrderDetail.add(convertGamePlanRequest2GameOrderDetail(item,
					order));
		}
		order.setSlipList(gameOrderDetail);

		Long orderId = gameOrderService.saveGameOrder(order, false,
				GamePackageType.PLAN, packe.getId());

		order.setId(orderId);
		log.info("追号计划生成订单成功，gamePlanId = " + plan.getId() + ", planDetailId ="
				+ detail.getId() + ", 生成的GameOrderId=" + orderId);
		return order;
	}

	public static com.winterframework.firefrog.game.entity.GameSlip convertGamePlanRequest2GameOrderDetail(
			GamePackageItem detailStruc,
			com.winterframework.firefrog.game.entity.GameOrder order)
			throws Exception {

		List<com.winterframework.firefrog.game.entity.GameSlip> list = new ArrayList<com.winterframework.firefrog.game.entity.GameSlip>();
		com.winterframework.firefrog.game.entity.GameSlip detail = new com.winterframework.firefrog.game.entity.GameSlip();
		// 设置奖期
		GameIssueEntity issue = new GameIssueEntity();
		issue.setIssueCode(order.getGameIssue().getIssueCode());
		detail.setIssueCode(issue);
		// 初始化圆角模式
		detail.setMoneyMode(EnumTypeConverts.convertMoneyMode(detailStruc
				.getMoneyMode()));
		detail.setBetDetail(detailStruc.getBetDetail());
		// 初始化玩法信息
		GameBetType betType = new GameBetType();
		String betTypeCode = detailStruc.getBetTypeCode();
		String[] betArr = betTypeCode.split("_");
		betType.setGameGroupCode(Integer.valueOf(betArr[0]));
		betType.setGameSetCode(Integer.valueOf(betArr[1]));
		betType.setBetMethodCode(Integer.valueOf(betArr[2]));
		detail.setGameBetType(betType);

		detail.setTotalBet(Long.valueOf(detailStruc.getTotbets()));
		detail.setTotalAmount(Long.valueOf(detailStruc.getTotamount()));
		detail.setMultiple(detailStruc.getMultiple());

		// 初始化注单状态
		detail.setGameSlipStatus(EnumTypeConverts.convertGameSlipStatus(1));
		detail.setCrateTime(new Date());
		detail.setAwardMode(detailStruc.getAwardMode());
		String[] retPoints=detailStruc.getRetPointChain().split(Separator.dot);
		detail.setRetPoint(Long.valueOf(retPoints[retPoints.length-1]));
		detail.setRetAward(detailStruc.getRetAward());
		detail.setPackageItemId(detailStruc.getId());
		detail.setGameOrder(order);
		return detail;
	}

	@Override
	public List<GameSlip> querySlipsByPlanId(long planId) throws Exception {
		return gameSlipDao.querySlipsByPlanId(planId);
	}

	@Override
	public void continueGamePlan(Long lotteryid, Long issueCode) {
		log.info("时时彩【" + lotteryid + "】追号id【" + issueCode + "】继续追号开始");

		try {
			List<Long> planidList = gamePlanDetailDao
					.getPausePlanIdByIssueCode(issueCode);

			for (Long planid : planidList) {
				com.winterframework.firefrog.game.dao.vo.GamePlan gamePlan = gamePlanDao
						.getById(planid);
				gamePlan.setStatus(com.winterframework.firefrog.game.dao.vo.GamePlan.Status.WAITING
						.getValue());
				gamePlan.setUpdateTime(DateUtils.currentDate());
				gamePlanDao.update(gamePlan);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("task continueGamePlan error:", e);
		}

		log.info("时时彩【" + lotteryid + "】追号id【" + issueCode + "】继续追号结束");
	}

	/**
	 * 
	 * Title: redo Description: 对由系统自动产生的追号信息进行重做，现在处理的只能假定下一期为派奖前。
	 * 
	 * @param lotteryId
	 * @param issueCode
	 *            当前奖期期号，重做时，根据当前奖期找到下一奖期信息并进行重做。
	 * @throws Exception
	 * @see com.winterframework.firefrog.game.service.IGamePlanService#redo(java.lang.Long,
	 *      java.lang.Long)
	 */
	@Override
	public void redo(Long lotteryId, Long issueCode) throws Exception {

		/*****************************************************
		 * 1.根据当前期获取追号计划信息。 2.通过追号计划信息及下一期奖期信息获取已自动生成的订单信息。 3.删除已生成的订单信息。
		 * 4.更新追号计划下一期为未执行。 5.更新追号计划完成期数、销售金额等信息。
		 * 
		 *****************************************************/
		log.info("重做追号信息开始，lotteryId=" + lotteryId + ",issueCode=" + issueCode);

		GameIssueEntity nextGameIssue = gameIssueService.queryNextGameIssue(
				lotteryId, issueCode);

		// 将上一期生成的追号信息进行删除【只包含系统生成的追号订单信息】。其重新生成在重做的时候进行。
		Long nextIssueCode = nextGameIssue.getIssueCode();

		log.info("重做追号信息开始，lotteryId=" + lotteryId + ",issueCode=" + issueCode
				+ ",nextIssueCode=" + nextIssueCode);
		// 1.获取追号计划信息。
		List<com.winterframework.firefrog.game.dao.vo.GamePlan> planList = gamePlanDao
				.getPlanByLotteryIdAndIssueCode(lotteryId, issueCode,
						nextIssueCode);

		for (com.winterframework.firefrog.game.dao.vo.GamePlan plan : planList) {
			GameOrder order = gameOrderDao.getOrderByPlanIdAndIssueCode(
					plan.getId(), nextIssueCode);

			if (null == order) {
				log.info("重做追号信息，lotteryId=" + lotteryId + ",issueCode="
						+ issueCode + ",nextIssueCode=" + nextIssueCode
						+ ", 无订单信息。，planId=" + plan.getId());
				continue;
			}

			// delete game_slip
			log.info("重做追号信息，lotteryId=" + lotteryId + ",issueCode="
					+ issueCode + ",nextIssueCode=" + nextIssueCode
					+ ", 开始删除注单信息，orderId=" + order.getId());
			gameSlipDao.delSlipByOrderId(order.getId(), lotteryId);

			// delete order
			log.info("重做追号信息，lotteryId=" + lotteryId + ",issueCode="
					+ issueCode + ",nextIssueCode=" + nextIssueCode
					+ ", 开始删除订单信息，orderId=" + order.getId());
			gameOrderDao.delete(order.getId());

			// update game_plan_detail
			log.info("重做追号信息，lotteryId=" + lotteryId + ",issueCode="
					+ issueCode + ",nextIssueCode=" + nextIssueCode
					+ ", 开始更新Game_plan_detail表信息，planId=" + plan.getId());
			updateGamePlanDetail(plan.getId(), nextIssueCode,
					GamePlanDetailStatus.UN_EXEC.getValue());
			updateGamePlan(plan, order);

		}

		log.info("重做追号信息结束，lotteryId=" + lotteryId + ",issueCode=" + issueCode);
	}

	private void updateGamePlan(
			com.winterframework.firefrog.game.dao.vo.GamePlan plan,
			GameOrder order) {

		log.info("更新追号计划，planId =" + plan.getId() + ", OrderId = "
				+ order.getId());
		// update game_plan
		plan.setFinishIssue(plan.getFinishIssue() - 1);
		plan.setStatus(com.winterframework.firefrog.game.dao.vo.GamePlan.Status.WAITING
				.getValue());
		plan.setSoldAmount(plan.getSoldAmount() - order.getTotamount());
		plan.setUpdateTime(DateUtils.currentDate());
		gamePlanDao.update(plan);
	}

	/**
	 * 
	 * Title: undo Description: 对由系统自动产生的追号信息进行撤销，可能存在追号信息原追中即停，撤销后，要打开追号计划。
	 * 
	 * @param lotteryId
	 * @param issueCode
	 *            当前奖期期号，撤销时，根据当前奖期找到下一奖期信息并进行撤销。
	 * @throws Exception
	 * @see com.winterframework.firefrog.game.service.IGamePlanService#undo(java.lang.Long,
	 *      java.lang.Long)
	 */
	@Override
	public void undo(Long lotteryId, Long issueCode) throws Exception {

		log.info("撤销追号信息开始，lotteryId=" + lotteryId + ",issueCode = "
				+ issueCode);

		// get game_plan
		List<com.winterframework.firefrog.game.dao.vo.GamePlan> planList = gamePlanDao
				.queryPlanByLotteryIdAndIssue(lotteryId, issueCode, null);

		undo(planList, issueCode);
		log.info("撤销追号信息结束，lotteryId=" + lotteryId + ",issueCode = "
				+ issueCode);
	}

	/**
	 * 
	 * Title: undo Description: 对由系统自动产生的追号信息进行撤销，可能存在追号信息原追中即停，撤销后，要打开追号计划。
	 * 
	 * @param lotteryId
	 * @param issueCode
	 *            当前奖期期号，撤销时，根据当前奖期找到下一奖期信息并进行撤销。
	 * @param date
	 * @throws Exception
	 * @see com.winterframework.firefrog.game.service.IGamePlanService#undo(java.lang.Long,
	 *      java.lang.Long, java.util.Date)
	 */
	@Override
	public void undo(Long lotteryId, Long issueCode, Date date)
			throws Exception {

		log.info("撤销追号信息开始，lotteryId="
				+ lotteryId
				+ ",issueCode = "
				+ issueCode
				+ ",date="
				+ DateUtils.format(date,
						DateUtils.DATETIME_JSVIEW_FORMAT_PATTERN));

		// get Game_plan
		List<com.winterframework.firefrog.game.dao.vo.GamePlan> planList = gamePlanDao
				.queryPlanByLotteryIdAndIssue(lotteryId, issueCode, date);

		undo(planList, issueCode);
		log.info("撤销追号信息结束，lotteryId="
				+ lotteryId
				+ ",issueCode = "
				+ issueCode
				+ ",date="
				+ DateUtils.format(date,
						DateUtils.DATETIME_JSVIEW_FORMAT_PATTERN));
	}

	private void undo(
			List<com.winterframework.firefrog.game.dao.vo.GamePlan> planList,
			Long issueCode) throws Exception {

		/*************************************************************
		 * 1.获取当期的追号计划信息， 2.撤销当前期已生成的下一期追号计划信息。 3.重新判断追号计划停止参数信息。 4.更新已撤销追号计划信息。
		 *************************************************************/

		for (com.winterframework.firefrog.game.dao.vo.GamePlan plan : planList) {

			if (plan.getStatus() == GamePlanStatus.STOP.getValue()) {

				reopenPlan(plan, issueCode);
			}
			cancelCurrent(plan, issueCode);
		}
	}

	private void cancelCurrent(
			com.winterframework.firefrog.game.dao.vo.GamePlan plan,
			Long issueCode) throws Exception {

		log.info("撤销追号信息开始，lotteryId=" + plan.getLotteryid() + ",issueCode = "
				+ issueCode + ",撤销本期追号信息，planId=" + plan.getId());

		/*********************************
		 * 1.通过本期信息获取订单、注单信息，并撤销； 2.撤销当前追号信息。
		 *********************************/

		// update Order
		GameOrder order = updateGameOrder(plan.getId(), issueCode);

		// udate gamePlan
		updateGamePlan(plan, issueCode, order);

		// get gamePlanDetail
		com.winterframework.firefrog.game.dao.vo.GamePlanDetail detail = gamePlanDetailDao
				.getGamePlanDetailByPlanIdAndIssueCode(plan.getId(), issueCode);
		gamePlanDetailDao.updateGamePlanDetailByPlanID(plan.getId(), issueCode,
				GamePlanDetailStatus.CANCEL.getValue());
		List<TORiskDTO> toRiskDTOList = new ArrayList<TORiskDTO>();
		Long totalCancelAmount = 0L;// 撤销的追号总金额

		// 投注DTO生成
		TORiskDTO unFreezerPlanDTO = new TORiskDTO();
		unFreezerPlanDTO.setAmount(totalCancelAmount + "");
		unFreezerPlanDTO.setIssueCode(detail.getIssueCode());
		unFreezerPlanDTO.setLotteryid(plan.getLotteryid());
		unFreezerPlanDTO.setPlanCodeList(plan.getPlanCode());
		unFreezerPlanDTO
				.setType(GameFundTypesUtils.GAME_PLAN_RESERVE_UNFREEZEN_DETEAIL_TYPE);
		unFreezerPlanDTO.setUserid(plan.getUserId() + "");

		toRiskDTOList.add(unFreezerPlanDTO);
		// 调用风控资金冻结接口
		fundRiskService.cancelFee(toRiskDTOList);
	}

	/**
	 * 
	 * @Title: updateGamePlan
	 * @Description: 更新追号信息。
	 * @param plan
	 * @param issueCode
	 * @param order
	 * @throws Exception
	 */
	private void updateGamePlan(
			com.winterframework.firefrog.game.dao.vo.GamePlan plan,
			Long issueCode, GameOrder order) throws Exception {

		updateGamePlanDetail(plan.getId(), issueCode,
				GamePlanDetailStatus.CANCEL.getValue());
		updateGamePlan(plan, order);
	}

	/**
	 * 
	 * @Title: updateGameOrder
	 * @Description: 更新订单信息。
	 * @param planid
	 * @param issueCode
	 * @return
	 */
	private GameOrder updateGameOrder(Long planid, Long issueCode) {

		log.info("撤销追号信息  ，issueCode = " + issueCode
				+ ",通过追号计划信息更新订单信息。planId=" + planid);
		GameOrder order = gameOrderDao.getOrderByPlanIdAndIssueCode(planid,
				issueCode);

		order.setCancelTime(new Date());
		order.setCancelModes(CancelMode.SYSTEM.getValue());
		order.setStatus(OrderStatus.CANCEL.getValue());

		gameSlipDao.updateGameOrderDetailCancalByOrderId(order.getId(),
				issueCode);

		gameOrderDao.update(order);

		return order;
	}

	private void reopenPlan(
			com.winterframework.firefrog.game.dao.vo.GamePlan plan,
			Long issueCode) throws Exception {

		log.info("撤销追号信息开始，lotteryId=" + plan.getLotteryid() + ",issueCode = "
				+ issueCode + ",原追号计划已停止，重新打开，planId=" + plan.getId());

		// get nextIssueCode
		GameIssueEntity nextGameIssue = gameIssueService.queryNextGameIssue(
				plan.getLotteryid(), issueCode);
		Long nextIssueCode = nextGameIssue.getIssueCode();

		// get gamePlanDetail
		com.winterframework.firefrog.game.dao.vo.GamePlanDetail detail = gamePlanDetailDao
				.getGamePlanDetailByPlanIdAndIssueCode(plan.getId(),
						nextIssueCode);
		if (null != detail) {

			generateGamePlan(detail);
		}
	}

	@Override
	public com.winterframework.firefrog.game.dao.vo.GamePlan queryPlanByCode(
			String planCode) throws Exception {
		List<com.winterframework.firefrog.game.dao.vo.GamePlan> list = gamePlanDao
				.getGamePlanByPlanCode(planCode);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Date getLastPlanCanStopTime(Long planId) throws Exception {
		return gamePlanDetailDao.getLastPlanCanStopTime(planId);
	}

	@Override
	public Long getUndoPlansCountByUserId(Long userId) throws Exception{
		return gamePlanDao.getUndoPlansCountByUserId(userId);
	}
	
	public void checkGameAward(com.winterframework.firefrog.game.entity.GameOrder gameOrder,
			List<GamePlanBetOriginDataStruc> gamePlanBetOriginDataStruc){
		List<com.winterframework.firefrog.game.entity.GamePackageItem> packageItemList=gameOrder.getGamePackage().getItemList();
		List<com.winterframework.firefrog.game.entity.GameSlip> slipList = gameOrder.getSlipList();
		for(com.winterframework.firefrog.game.entity.GamePackageItem packageItem:packageItemList){
			String betTypeCode=packageItem.getGameBetType().getBetTypeCode();
			if(!(gameAwardMode.checkAwardMode(gameOrder.getAwardGroupId().toString(),betTypeCode))){
				packageItem.setAwardMode(1);
			}
		}
		for(com.winterframework.firefrog.game.entity.GameSlip slip : slipList){
			String slipbetTypeCode=slip.getGameBetType().getBetTypeCode();
			if(!gameAwardMode.checkAwardMode(gameOrder.getAwardGroupId().toString(),slipbetTypeCode)){
				slip.setAwardMode(1);
			}
		}
		for(GamePlanBetOriginDataStruc gamePlanBet : gamePlanBetOriginDataStruc){
			StringBuilder str = new StringBuilder();
			str.append(gamePlanBet.getGameGroupCode()+"_"+gamePlanBet.getGameSetCode()+"_"+gamePlanBet.getBetMethodCode());
			if(!gameAwardMode.checkAwardMode(gameOrder.getAwardGroupId().toString(),str.toString())){
				gamePlanBet.setAwardMode(1);
			}
		}
	}
}
