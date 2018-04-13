package com.winterframework.firefrog.game.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.map.LRUMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.winterframework.firefrog.common.redis.RedisClient2;
import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGameOrderLogDao;
import com.winterframework.firefrog.game.dao.IGamePlanDao;
import com.winterframework.firefrog.game.dao.IGameRiskConfigDao;
import com.winterframework.firefrog.game.dao.IGameSlipDao;
import com.winterframework.firefrog.game.dao.IGameWarnOrderDao;
import com.winterframework.firefrog.game.dao.IGameWarnUserDao;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameOrderWin;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.dao.vo.GameWarnOrder;
import com.winterframework.firefrog.game.dao.vo.GameWarnUser;
import com.winterframework.firefrog.game.entity.GameRiskConfig;
import com.winterframework.firefrog.game.exception.GameRiskException;
import com.winterframework.firefrog.game.service.ICommonGamePlanService;
import com.winterframework.firefrog.game.service.IGameFundRiskService;
import com.winterframework.firefrog.game.service.IGameFundService;
import com.winterframework.firefrog.game.service.IGameOrderFundService;
import com.winterframework.firefrog.game.service.IGameOrderLogService;
import com.winterframework.firefrog.game.service.IGameOrderWinFundService;
import com.winterframework.firefrog.game.service.IGamePlanService;
import com.winterframework.firefrog.game.service.IGameReturnPointFundService;
import com.winterframework.firefrog.game.service.IGameSlipService;
import com.winterframework.firefrog.game.service.IGameWarnService;
import com.winterframework.firefrog.game.service.IGameWarnUserService;
import com.winterframework.firefrog.game.service.IUserSystemUpdateLogService;
import com.winterframework.firefrog.game.service.bean.GameWarnServiceBean;
import com.winterframework.firefrog.game.service.bean.GameWarnServiceCacheBean;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.entity.IndexLottery;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.util.JsonMapper;

/** 
* @ClassName GameWarnServiceimpl 
* @Description 审核风控订单入库 
* @author  hugh
* @date 2014年4月11日 下午4:17:37 
*  
*/
@Service("gameWarnServiceImpl")

public class GameWarnServiceImpl implements IGameWarnService {

	private static final Logger log = LoggerFactory.getLogger(GameWarnServiceImpl.class);

	@Resource(name = "gameOrderLogDaoImpl")
	private IGameOrderLogDao gameOrderLogDao;
	@Resource(name = "gameOrderLogServiceImpl")
	private IGameOrderLogService gameOrderLogService;
	@Resource(name = "gameOrderDaoImpl")
	private IGameOrderDao gameOrderDaoImpl;
	@Resource(name = "gameSlipDaoImpl")
	private IGameSlipDao gameSlipDaoImpl;
	@Resource(name = "gameWarnOrderDaoImpl")
	private IGameWarnOrderDao warnOrderDao;
	@Resource(name = "gamePlanDaoImpl")
	private IGamePlanDao gamePlanDao;
	@Resource(name = "gameWarnUserDaoImpl")
	private IGameWarnUserDao gameWarnUserDao;
	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao customerDao;
	@Resource(name = "gameRiskConfigDaoImpl")
	private IGameRiskConfigDao gameRiskConfigDao;
	@Resource(name = "gameReturnPointFundServcieImpl")
	private IGameReturnPointFundService gameReturnPointFundServcie;
	@Resource(name = "gameOrderWinFundServcieImpl")
	private IGameOrderWinFundService gameOrderWinFundServcie;
	@Resource(name = "gameOrderFundServcieImpl")
	private IGameOrderFundService gameOrderFundServcie;
	@Resource(name = "gameOrderFundServcieImpl")
	private IGameFundService gameFundServcie; 
	@Resource(name = "generateGamePlanServiceImpl")
	private IGamePlanService generateGamePlanServiceImpl;
	@Resource(name = "gamePlanService")
	protected ICommonGamePlanService gamePlanService; 
	@Resource(name = "gameWarnUserServiceImpl")
	protected IGameWarnUserService gameWarnUserService;
	
	@Resource(name = "gameFundRiskServiceImpl")
	protected IGameFundRiskService gameFundRiskService;
	
	
	//@Resource(name = "RedisClient")
//	private RedisClient redisClient;
	
	@Resource(name = "RedisClient2")
	private RedisClient2 redisClient2;
	
	@Resource(name = "RedisService")
	private RedisService redisService;
	
	@Resource(name = "userSystemUpdateLogServiceImpl")
	private IUserSystemUpdateLogService userSystemUpdateLogServiceImpl;
	@Resource(name = "gameSlipServiceImpl")
	private IGameSlipService gameSlipService;
	
	private final String WARN_SERVICE_RPRE = "WARN_SERVICE_";
	private final String WARN_SEPARATE = "_";
	private static JsonMapper jmapper = JsonMapper.nonEmptyMapper();

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void doBusiness(GameContext ctx,GameOrder order,boolean isRedraw) throws GameRiskException {
		log.debug("风控模块：进入");
		GameWarnServiceBean warnServiceBean = getCacheWarnServiceBean(order.getLotteryid().longValue(),
				order.getIssueCode(), order.getUserid());
		log.debug(order.getId()+"检查0 是否为风险用户  warnServiceBean" + jmapper.toJson(warnServiceBean) );
		GameOrderWin win = gameOrderWinFundServcie.getGameOrderWinByOrderId(order.getId());

		//如果中奖订单不为空，即该单中奖
		if (win != null) {
			log.debug("风控模块：进行中奖订单处理");
			//获取配置
			GameRiskConfig config = queryGameRiskConfig(order.getLotteryid()); 
			GameWarnServiceCacheBean cache = new GameWarnServiceCacheBean(); 
			//处理中奖订单，并设置缓存数据
			dealOrderWin(win, config, cache, warnServiceBean);
			log.debug(order.getId()+"检查是否为风险用户  warnServiceBean" + jmapper.toJson(warnServiceBean));
			log.debug(order.getId()+"检查是否为风险用户  config" + jmapper.toJson(config) 
					+ "getIssueUserCountWin" +warnServiceBean.getIssueUserCountWin() 
					+ "getIssueUserWinsRatio" + warnServiceBean.getIssueUserWinsRatio());
			//检查是否为风险用户
			if (isUserWarn(warnServiceBean.getIssueUserCountWin(), warnServiceBean.getIssueUserWinsRatio(), config)) {
				cache.setAllWarnOrdersCache();
				warnServiceBean.setWarn();
				log.debug(order.getId()+"检查2 是否为风险用户  config" + jmapper.toJson(config) 
						+ "getIssueUserCountWin" +warnServiceBean.getIssueUserCountWin() 
						+ "getIssueUserWinsRatio" + warnServiceBean.getIssueUserWinsRatio());
			}
			List<GameWarnOrder> warnOrderList=cache.getTrueGameWarnOrders();
			if(warnOrderList!=null && warnOrderList.size()>0){ 
				Set<Long> warnUserIdSet =(Set<Long>)ctx.get("WARN_USER_ID_SET");
				if(warnUserIdSet==null){
					warnUserIdSet=new HashSet<Long>();
				}
				for(GameWarnOrder warnOrder:warnOrderList){ 
					warnUserIdSet.add(warnOrder.getUserid());
				}
				ctx.set("WARN_USER_ID_SET", warnUserIdSet);
			}
			//处理风险订单
			dealWarnOrderFund(ctx,cache, warnServiceBean, order,isRedraw);
			 
			//处理正常订单
			dealNormalOrderFund(ctx,cache, warnServiceBean, order,isRedraw);   
		}else{   
			//未中奖
			log.debug("风控模块：进行未中奖订单处理");
			dealNotWinOrder(ctx,warnServiceBean, order,isRedraw);
			warnServiceBean.addIssueUserTotamount(order.getTotamount());
		}  
		//更新缓存
		putCacheWarnServiceBean(order.getLotteryid().longValue(), order.getIssueCode(), order.getUserid(),warnServiceBean);

	} 
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void doBusiness(GameOrder order,boolean isRedraw) throws GameRiskException {
		log.info("风控模块：进入");
		GameWarnServiceBean warnServiceBean = getCacheWarnServiceBean(order.getLotteryid().longValue(),
				order.getIssueCode(), order.getUserid());
		log.info(order.getId()+"检查0 是否为风险用户  warnServiceBean" + jmapper.toJson(warnServiceBean) );
		GameOrderWin win = gameOrderWinFundServcie.getGameOrderWinByOrderId(order.getId());

		//如果中奖订单不为空，即该单中奖
		if (win != null) {
			log.debug("风控模块：进行中奖订单处理");
			//获取配置
			GameRiskConfig config = queryGameRiskConfig(order.getLotteryid()); 
			GameWarnServiceCacheBean cache = new GameWarnServiceCacheBean(); 
			//处理中奖订单，并设置缓存数据
			dealOrderWin(win, config, cache, warnServiceBean);
			log.info(order.getId()+"检查是否为风险用户  warnServiceBean" + jmapper.toJson(warnServiceBean));
			log.info(order.getId()+"检查是否为风险用户  config" + jmapper.toJson(config) 
					+ "getIssueUserCountWin" +warnServiceBean.getIssueUserCountWin() 
					+ "getIssueUserWinsRatio" + warnServiceBean.getIssueUserWinsRatio());
			//检查是否为风险用户
			if (isUserWarn(warnServiceBean.getIssueUserCountWin(), warnServiceBean.getIssueUserWinsRatio(), config)) {
				cache.setAllWarnOrdersCache();
				warnServiceBean.setWarn();
				log.info(order.getId()+"检查2 是否为风险用户  config" + jmapper.toJson(config) 
						+ "getIssueUserCountWin" +warnServiceBean.getIssueUserCountWin() 
						+ "getIssueUserWinsRatio" + warnServiceBean.getIssueUserWinsRatio());
			}
			//处理风险订单
			dealWarnOrderFund(cache, warnServiceBean, order,isRedraw);
			 
			//处理正常订单
			dealNormalOrderFund(cache, warnServiceBean, order,isRedraw);  
		}else{   
			//未中奖
			log.debug("风控模块：进行未中奖订单处理");
			dealNotWinOrder(warnServiceBean, order,isRedraw);
			warnServiceBean.addIssueUserTotamount(order.getTotamount());
		}  
		//更新缓存
		putCacheWarnServiceBean(order.getLotteryid().longValue(), order.getIssueCode(), order.getUserid(),warnServiceBean);

	} 
	/** 
	* @Title: getCacheWarnServiceBean 
	* @Description: 缓存审核相关信息
	* @param lotteryId
	* @param issueCode
	* @param userId
	* @return
	*/
	public GameWarnServiceBean getCacheWarnServiceBean(Long lotteryId, Long issueCode, Long userId) {
	
		String string = redisClient2.get(WARN_SERVICE_RPRE + lotteryId + WARN_SEPARATE + issueCode + WARN_SEPARATE
				+ userId);
		if (string != null) {
			return jmapper.fromJson(string, GameWarnServiceBean.class);
		} else {
			return new GameWarnServiceBean(lotteryId, issueCode, userId);
		}
	}

	/** 
	* @Title: putCacheGameWarnUser 
	* @Description:获取审核相关信息
	* @param lotteryId
	* @param issueCode
	* @param userId
	* @param number
	*/
	public void putCacheWarnServiceBean(Long lotteryId, Long issueCode, Long userId, GameWarnServiceBean warn) {
	
		redisClient2.set(WARN_SERVICE_RPRE + lotteryId + WARN_SEPARATE + issueCode + WARN_SEPARATE + userId,
				jmapper.toJson(warn), 1209600);
		
	}

	/** 
	* @Title: clearCacheWarnUsersByLotteryAndIssue 
	* @Description:清空一期审核相关信息
	* @param lotteryId
	* @param issueCode
	*/
	public void clearCacheWarnServiceBeansByLotteryAndIssue(Long lotteryId, Long issueCode) {
		// 這邊這樣寫會影響效能  先移到其他台redis .4 上
	
		Set<String> keys = redisClient2.keys(WARN_SERVICE_RPRE + lotteryId + WARN_SEPARATE + issueCode + WARN_SEPARATE
				+ "*");
		if (keys != null) {
			for (String key : keys) {
			
				redisClient2.del(key);
			}
		}
	}

	/**
	* @Title: clearWarn
	* @Description:清空一期所有数据 含缓存 （日志不清）
	* @param lotteryId
	* @param issueCode 
	* @see com.winterframework.firefrog.game.service.IGameWarnService#clearWarn(java.lang.Long, java.lang.Long) 
	*/
	public void clearWarn(Long lotteryId, Long issueCode) {
		/**
		 * 1.更新后置奖期的连续中奖情况（判断条件：更新连续值>当前值）--暂时不做处理，等待提BUG
		 * 2.删除警告信息
		 * 3.删除Redis
		 */ 
		gameWarnUserDao.deleteByLotteryIssue(lotteryId, issueCode);
		warnOrderDao.deleteByLotteryIssue(lotteryId, issueCode);
		clearCacheWarnServiceBeansByLotteryAndIssue(lotteryId, issueCode);
		
	}

	/** 
	* @Title: dealOrderWin 
	* @Description: 处理中奖订单 并保存缓存
	* @param gameOrderWin
	* @param config
	* @param cache
	* @param warnServiceBean
	*/
	public void dealOrderWin(GameOrderWin gameOrderWin, GameRiskConfig config, GameWarnServiceCacheBean cache,
			GameWarnServiceBean warnServiceBean) {
		//订单中单注最大中奖金额 
		Long orderMaxSlipWins = 0L;
		//订单中单注最大中投比
		Long orderMaxSlipWinsRatio = 0L;

		Long orderId = gameOrderWin.getOrderid();
		List<GameSlip> slips = getGameSlips(orderId);

		for (GameSlip gameSlip : slips) {
			if (gameSlip.getStatus().intValue() == 2) {
				//单注中奖金额 
				Long slipWins = gameSlip.getEvaluateWin();
				warnServiceBean.compareAndSetMaxSlipWins(slipWins);
				orderMaxSlipWins = orderMaxSlipWins > slipWins ? orderMaxSlipWins : slipWins;
				//单注中投比
				//0005186: 【中三】188环境/乐利时时彩 -- 追号单中奖方案卡在”处理中“状态
				//BigDecimal a = new BigDecimal(slipWins * 10000); //修改bug 0005186注释
				//中奖金额已经是10000倍，不应该再乘以10000的
				BigDecimal a = new BigDecimal(slipWins);

				BigDecimal b = new BigDecimal(gameSlip.getTotamount());
				BigDecimal c = a.divide(b, 2, BigDecimal.ROUND_HALF_EVEN);
				Long slipWinsRatio = c.multiply(new BigDecimal(10000)).longValue();
				orderMaxSlipWinsRatio = orderMaxSlipWinsRatio > slipWinsRatio ? orderMaxSlipWinsRatio : slipWinsRatio;
				warnServiceBean.setContinuousWinsTimes(warnServiceBean.getContinuousWinsTimes().longValue() + 1L);
			}
		}
		GameOrder order = gameOrderDaoImpl.getById(orderId);
		gameOrderWin.setOrderCode(order.getOrderCode());
		//订单总共中奖金额
		Long countWin = gameOrderWin.getCountWin();
		order.setTotalWin(countWin);
		//订单总共购买金额
		Long totamount = order.getTotamount();

		//设置缓存 上下文 warnServiceBean
		if (warnServiceBean.getMaxSlipWins().longValue() < orderMaxSlipWins) {
			warnServiceBean.setMaxSlipWins(orderMaxSlipWins);
		}
		warnServiceBean.addIssueUserTotamount(totamount); //重复计算了
		warnServiceBean.addIssueUserCountWin(countWin);
		warnServiceBean.setContinuousWinsIssue(warnServiceBean.getContinuousWinsIssue().longValue() + 1L);

		////0005186: 【中三】188环境/乐利时时彩 -- 追号单中奖方案卡在”处理中“状态
		//BigDecimal a = new BigDecimal(countWin * 10000); //修改bug 0005186注释
		BigDecimal a = new BigDecimal(countWin);
		BigDecimal b = new BigDecimal(totamount);
		BigDecimal c = a.divide(b, 2, BigDecimal.ROUND_HALF_EVEN);
		Long winsRatio = c.multiply(new BigDecimal(10000)).longValue();
		GameWarnOrder warnOrder = createWarnOrderWithNoChannelId(order, countWin, winsRatio, orderMaxSlipWins,
				orderMaxSlipWinsRatio);
		log.info(order.getId() + "检查   warnServiceBean" + jmapper.toJson(warnServiceBean));
		log.info(order.getId() + "检查   config" + jmapper.toJson(config) + " =orderMaxSlipWins=="+ orderMaxSlipWins +"orderMaxSlipWinsRatio = " +orderMaxSlipWinsRatio);
		if (isOrderWarn(orderMaxSlipWins, orderMaxSlipWinsRatio, config)) {
			cache.addTrueGameWarnOrder(warnOrder);
			cache.addFreezenOrderWin(gameOrderWin);
			cache.addFreezenOrder(order);
			log.info(order.getId() + "检查2   config" + jmapper.toJson(config) + " =orderMaxSlipWins=="+ orderMaxSlipWins +"orderMaxSlipWinsRatio = " +orderMaxSlipWinsRatio);
			warnServiceBean.setWarn();
		} else {
			cache.addMaybeGameWarnOrder(warnOrder);
			cache.addOrderWin(gameOrderWin);
			cache.addOrder(order);
		}
	}

	/** 
	* @Title: dealNotWinOrder 
	* @Description: 处理不中奖订单
	* @param cache
	* @param warnServiceBean
	*/
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void dealNotWinOrder(GameWarnServiceBean warnServiceBean, GameOrder order,boolean isRedraw) {
		/*Long s=this.gameOrderLogDao.getTest(66L);
		System.out.println(s);*/ 
		if(!isRedraw){
			//调用 投注解冻至投注扣款
			gameOrderFundServcie.orderFund(order);
			//调用返点
			gameReturnPointFundServcie.returnPointFundUpdateRetsStatus(order.getId());
		} 		
		//改变订单状态
		order.setStatus(3);
		gameOrderDaoImpl.update(order); 
		
		try {
			userSystemUpdateLogServiceImpl.addUserBet(order.getUserid());
		} catch (Exception e) {
			log.error("activity error");
		}
	}  
	public void dealNotWinOrder(GameContext ctx,GameWarnServiceBean warnServiceBean, GameOrder order,boolean isRedraw) {
		if(!isRedraw){
			//调用 投注解冻至投注扣款
			gameOrderFundServcie.orderFund(ctx,order);
			//调用返点
			gameReturnPointFundServcie.returnPointFundUpdateRetsStatus(ctx,order.getId());
		} 		
		//改变订单状态
		order.setStatus(3);
		gameOrderDaoImpl.update(order); 
		
		try {
			userSystemUpdateLogServiceImpl.addUserBet(order.getUserid());
		} catch (Exception e) {
			log.error("activity error");
		}
	}  
	
	protected void addRiskDto(GameContext ctx, List<TORiskDTO> riskDtoList) {
		if(riskDtoList==null || riskDtoList.size()==0) return;
		List<TORiskDTO> dtoList=(List<TORiskDTO>)ctx.get("RISKDTOLIST");
		if(dtoList==null){
			dtoList=new ArrayList<TORiskDTO>(); 
		}
		dtoList.addAll(riskDtoList);
		ctx.set("RISKDTOLIST", dtoList);
	}
	public void dealWarnOrderFund(GameContext ctx,GameWarnServiceCacheBean cache, GameWarnServiceBean warnServiceBean, GameOrder order,boolean isRedraw) {
		//如果有风险订单
		if (cache.isHaveWarnOrder()) {
			log.info(order.getId()+"检查2 是否为风险用户  warnServiceBean" + jmapper.toJson(warnServiceBean) 
					+ "getIssueUserCountWin" +warnServiceBean.getIssueUserCountWin() 
					+ "getIssueUserWinsRatio" + warnServiceBean.getIssueUserWinsRatio());
			log.info("风控模块：进行风险订单处理");
			/**
			 * 中-异常
			 * 不中-异常
			 * 
			 */
			//调用冻结OrderWin
			gameOrderWinFundServcie.orderWinFreeze(cache.getFreezenOrderWins());
			//调用冻结返点
			gameReturnPointFundServcie.returnPointFreeze(cache.getFreezenOrders());
			
			if(isRedraw){ //重开奖： 不存在未开奖的订单
				/**处理：撤销扣款，撤销返点
				 * 异常-异常 	不处理	（根据XX判断）
				 * 中-异常	处理
				 * 不中-异常	处理
				 */
				boolean isWarnPre=order.getStatus().intValue()==GameOrder.Status.EXCEP.getValue();
				if(!isWarnPre){	//重开前状态：非异常 则需要处理
					try{
						List<TORiskDTO> dtos = new ArrayList<TORiskDTO>();
						dtos.add(gameOrderFundServcie.packageOrderCancelTORiskDTO(order));
						dtos.add(gameReturnPointFundServcie.packageCancelTORiskDTO(order));  
						//冻结扣款
						dtos.add(gameOrderFundServcie.packageOrderFreezeTORiskDTO(order)); 
						this.addRiskDto(ctx, dtos);
					}catch(Exception e){
						log.error("重开奖审核调用资金失败",e); 
					}
				}				 
			}
			/*//进入风险审核不进行投注扣减和返点派送
			//调用资金：投注金额扣减
			gameOrderFundServcie.orderFund(order);
			//调用资金：投注返点
			Map<Long, GameOrder> orderMap =new HashMap<Long,GameOrder>();
			orderMap.put(order.getId(), order);
			gameReturnPointFundServcie.returnPointFundUpdateRetsStatus(orderMap); */
			
			//插入风控订单
			insertWarnOrder(cache.getTrueGameWarnOrders());

			cache.clearWarnCache();
			//改变订单状态
			order.setStatus(5);
			gameOrderDaoImpl.update(order);
			//
			try{
				gameSlipService.changeStatus(order.getId(), GameSlip.Status.WIN.getValue(), GameSlip.Status.EXCEP.getValue());
			}catch(Exception e){
				log.error("订单进入风控，Slip状态更新失败",e);
			}
			/*订单进入审核  改为 不暂停追号  
			 * GamePlan plan = gamePlanDao.getGamePlanByOrderId(order.getId());
			if (plan != null) {
				try {
					gamePlanService.pauseGamePlan(plan.getId()); 
				} catch (Exception e) {
					log.error("计划暂停出错", e);
					throw new GameRiskException("计划暂停出错");
				}
			}*/
		}
	}
	/** 
	* @Title: dealWarnOrderFund 
	* @Description: 处理风控订单 风险订单入库  风险用户入库 冻结派奖 冻结返点
	* @param cache
	* @param warnServiceBean
	*/
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void dealWarnOrderFund(GameWarnServiceCacheBean cache, GameWarnServiceBean warnServiceBean, GameOrder order,boolean isRedraw) {
		//如果有风险订单
		if (cache.isHaveWarnOrder()) {
			log.info(order.getId()+"检查2 是否为风险用户  warnServiceBean" + jmapper.toJson(warnServiceBean) 
					+ "getIssueUserCountWin" +warnServiceBean.getIssueUserCountWin() 
					+ "getIssueUserWinsRatio" + warnServiceBean.getIssueUserWinsRatio());
			log.info("风控模块：进行风险订单处理");
			/**
			 * 中-异常
			 * 不中-异常
			 * 
			 */
			//调用冻结OrderWin
			gameOrderWinFundServcie.orderWinFreeze(cache.getFreezenOrderWins());
			//调用冻结返点
			gameReturnPointFundServcie.returnPointFreeze(cache.getFreezenOrders());
			
			if(isRedraw){ //重开奖： 不存在未开奖的订单
				/**处理：撤销扣款，撤销返点
				 * 异常-异常 	不处理	（根据XX判断）
				 * 中-异常	处理
				 * 不中-异常	处理
				 */
				boolean isWarnPre=order.getStatus().intValue()==GameOrder.Status.EXCEP.getValue();
				if(!isWarnPre){	//重开前状态：非异常 则需要处理
					try{
						List<TORiskDTO> dtos = new ArrayList<TORiskDTO>();
						dtos.add(gameOrderFundServcie.packageOrderCancelTORiskDTO(order));
						dtos.add(gameReturnPointFundServcie.packageCancelTORiskDTO(order)); 
						gameFundRiskService.cancelFee(dtos);
						dtos.clear();
						//冻结扣款
						dtos.add(gameOrderFundServcie.packageOrderFreezeTORiskDTO(order)); 
						gameFundRiskService.betAmountFreezer(dtos);
					}catch(Exception e){
						log.error("重开奖审核调用资金失败",e); 
					}
				}				 
			}
			/*//进入风险审核不进行投注扣减和返点派送
			//调用资金：投注金额扣减
			gameOrderFundServcie.orderFund(order);
			//调用资金：投注返点
			Map<Long, GameOrder> orderMap =new HashMap<Long,GameOrder>();
			orderMap.put(order.getId(), order);
			gameReturnPointFundServcie.returnPointFundUpdateRetsStatus(orderMap); */
			
			//插入风控订单
			insertWarnOrder(cache.getTrueGameWarnOrders());

			cache.clearWarnCache();
			//改变订单状态
			order.setStatus(5);
			gameOrderDaoImpl.update(order);
			/*订单进入审核  改为 不暂停追号  
			 * GamePlan plan = gamePlanDao.getGamePlanByOrderId(order.getId());
			if (plan != null) {
				try {
					gamePlanService.pauseGamePlan(plan.getId()); 
				} catch (Exception e) {
					log.error("计划暂停出错", e);
					throw new GameRiskException("计划暂停出错");
				}
			}*/
		}
	}
	/** 
	* @Title: dealNormalOrderFund 
	* @Description: 处理正常订单  派奖 返点派奖
	* @param cache
	*/ 
	public void dealNormalOrderFund(GameContext ctx,GameWarnServiceCacheBean cache, GameWarnServiceBean warnServiceBean, GameOrder order,boolean isRedraw) {
		/*Long s=this.gameOrderLogDao.getTest(88L);
		System.out.println(s);
		this.gameOrderLogDao.insertTest(66L);
		if(1==1){
			throw new Exception("");
		}*/
		//如果有正常的订单
		if (cache.isHaveNormalOrder()) {
			log.debug("风控模块：进行普通中奖订单处理");
			Map<Long, GameOrder> orders = cache.getOrders();
			if(!isRedraw){	//非重开（(重开只做重开的事，不兼职做补开的 事，所以此次事件不用考虑撤销和等待开奖的订单（即只考虑中奖和未中奖及异常的订单））
				//调用投注金额扣减
				gameOrderFundServcie.orderFund(ctx,orders.values());
				//调用投注返点
				gameReturnPointFundServcie.returnPointFundUpdateRetsStatus(ctx,orders); 
			} 
			//调用派奖
			gameOrderWinFundServcie.orderWinFundUpdateWinsStatus(ctx,orders, cache.getOrderWins());
			//改变订单状态		
			order.setStatus(2);
			gameOrderDaoImpl.update(order);
			//改变追号单中奖金额
			for (GameOrderWin win : cache.getOrderWins()) {
				GamePlan plan = gamePlanDao.getGamePlanByOrderId(win.getOrderid());
				if (plan != null) {
					Long winAmount = plan.getWinAmount() == null ? 0L : plan.getWinAmount();
					log.debug("planId="+plan.getId() + plan.getWinAmount());
					plan.setWinAmount(winAmount + win.getCountWin()); 
					log.debug("planId="+plan.getId() + plan.getWinAmount());
					gamePlanDao.update(plan);
				}
			}

			updateIssuseRedisCache(order);
			cache.clearNormalCache();
			
			try {
				userSystemUpdateLogServiceImpl.addUserBet(order.getUserid());
			} catch (Exception e) {
				log.error("activity error");
			}
		}
	}
	/** 
	* @Title: dealNormalOrderFund 
	* @Description: 处理正常订单  派奖 返点派奖
	* @param cache
	*/
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void dealNormalOrderFund(GameWarnServiceCacheBean cache, GameWarnServiceBean warnServiceBean, GameOrder order,boolean isRedraw) {
		/*Long s=this.gameOrderLogDao.getTest(88L);
		System.out.println(s);
		this.gameOrderLogDao.insertTest(66L);
		if(1==1){
			throw new Exception("");
		}*/
		//如果有正常的订单
		if (cache.isHaveNormalOrder()) {
			log.info("风控模块：进行普通中奖订单处理");
			Map<Long, GameOrder> orders = cache.getOrders();
			if(!isRedraw){	//非重开（(重开只做重开的事，不兼职做补开的 事，所以此次事件不用考虑撤销和等待开奖的订单（即只考虑中奖和未中奖及异常的订单））
				//调用投注金额扣减
				gameOrderFundServcie.orderFund(orders.values());
				//调用投注返点
				gameReturnPointFundServcie.returnPointFundUpdateRetsStatus(orders); 
			} 
			//调用派奖
			gameOrderWinFundServcie.orderWinFundUpdateWinsStatus(orders, cache.getOrderWins());
			//改变订单状态		
			order.setStatus(2);
			gameOrderDaoImpl.update(order);
			//改变追号单中奖金额
			for (GameOrderWin win : cache.getOrderWins()) {
				GamePlan plan = gamePlanDao.getGamePlanByOrderId(win.getOrderid());
				if (plan != null) {
					Long winAmount = plan.getWinAmount() == null ? 0L : plan.getWinAmount();
					log.info("planId="+plan.getId() + plan.getWinAmount());
					plan.setWinAmount(winAmount + win.getCountWin()); 
					log.info("planId="+plan.getId() + plan.getWinAmount());
					gamePlanDao.update(plan);
				}
			}

			updateIssuseRedisCache(order);
			cache.clearNormalCache();
			
			try {
				userSystemUpdateLogServiceImpl.addUserBet(order.getUserid());
			} catch (Exception e) {
				log.error("activity error");
			}
		}
	}
	/** 
	* @Title: dealNormalOrderFund 
	* @Description: 处理正常订单  派奖 返点派奖
	* @param cache
	*/
	public void dealNormalOrderFund_bk(GameWarnServiceCacheBean cache, GameWarnServiceBean warnServiceBean, GameOrder order) {
		//如果有正常的订单
		if (cache.isHaveNormalOrder()) {
			log.info("风控模块：进行普通中奖订单处理");
			Map<Long, GameOrder> orders = cache.getOrders();
			//调用投注金额扣减
			gameOrderFundServcie.orderFund(orders.values());
			//调用投注返点
			gameReturnPointFundServcie.returnPointFundUpdateRetsStatus(orders);
			//调用派奖
			gameOrderWinFundServcie.orderWinFundUpdateWinsStatus(orders, cache.getOrderWins());
			//改变订单状态		
			order.setStatus(2);
			gameOrderDaoImpl.update(order);
			//改变追号单中奖金额
			for (GameOrderWin win : cache.getOrderWins()) {
				GamePlan plan = gamePlanDao.getGamePlanByOrderId(win.getOrderid());
				if (plan != null) {
					Long winAmount = plan.getWinAmount() == null ? 0L : plan.getWinAmount();
					log.info("planId="+plan.getId() + plan.getWinAmount());
					plan.setWinAmount(winAmount + win.getCountWin());
					log.info("planId="+plan.getId() + plan.getWinAmount());
					gamePlanDao.update(plan);
				}
			}

			updateIssuseRedisCache(order);
			cache.clearNormalCache();
			
			try {
				userSystemUpdateLogServiceImpl.addUserBet(order.getUserid());
			} catch (Exception e) {
				log.error("activity error");
			}
		}
	}

	/** 
	* @Title: countAndClear 
	* @Description: 统计并清空缓存
	* @param lotteryId
	* @param issueCode
	* @param userIds
	*/
	public void countAndClear(Long lotteryId, Long issueCode, Set<Long> userIds) {
		if(userIds==null) return;
		for (Long userId : userIds) {
			GameWarnServiceBean warnServiceBean = getCacheWarnServiceBean(lotteryId, issueCode, userId);
			/*连续中奖期数和连续中奖次数   注释掉--Gary
			 * //加上前一期数据  连续中奖期数
			if (warnServiceBean.getContinuousWinsIssue() != null && warnServiceBean.getContinuousWinsIssue() != 0L) {
				GameWarnUser warnUser =null;
				try{
					warnUser=gameWarnUserService.getLastWarnUser(new GameContext(),userId, lotteryId, issueCode);
				}catch(Exception e){
					log.error("获取风险用户信息失败", e); 
					//暂作不抛出异常处理
				}
				if (warnUser == null) {
					warnUser = new GameWarnUser();
				}
				warnServiceBean.setContinuousWinsIssue(warnUser.getContinuousWinsIssue() + 1L); 
				warnServiceBean.setContinuousWinsTimes(warnUser.getContinuousWinsTimes()+ warnServiceBean.getContinuousWinsTimes());
			}*/
			//插入风控用户
			saveOrUpdateWarnUser(warnServiceBean); 
		}

		//清理TODO 暂时不在这写  视后面情况
		//clearCacheWarnServiceBeansByLotteryAndIssue(lotteryId, issueCode);
	}

	/** 
	* @Title: getUserGameOrderWins 
	* @Description: 获取用户中奖订单
	* @param lotteryId
	* @param issueCode
	* @return
	*/
	public HashMap<Long, List<GameOrderWin>> getUserGameOrderWins(Long lotteryId, Long issueCode) {

		HashMap<Long, List<GameOrderWin>> users = new HashMap<Long, List<GameOrderWin>>();
		List<GameOrderWin> gameOrderWins = gameOrderWinFundServcie.getGameOrderWins(lotteryId, issueCode);

		if (gameOrderWins == null || gameOrderWins.isEmpty()) {
			return null;
		}

		for (GameOrderWin gameOrderWin : gameOrderWins) {
			Long userid = gameOrderWin.getUserid();
			List<GameOrderWin> userGameOrderWins = users.get(userid);
			if (userGameOrderWins == null) {
				userGameOrderWins = new ArrayList<GameOrderWin>();
				userGameOrderWins.add(gameOrderWin);
			} else {
				userGameOrderWins.add(gameOrderWin);
			}
			users.put(userid, userGameOrderWins);
		}
		return users;
	}

	/** 
	* @Title: getGameSlips 
	* @Description: 查询订单注单
	* @param orderId
	* @return
	*/
	public List<GameSlip> getGameSlips(Long orderId) {
		List<GameSlip> gameSlips;
		try {
			gameSlips = gameSlipDaoImpl.querySlipByOrder(orderId);
		} catch (Exception e) {
			log.error("查询注单出错");
			throw new GameRiskException("查询注单出错");
		}
		return gameSlips;
	}

	/** 
	* @Title: createWarnOrderWithNoChannelId 
	* @Description: 创建风险订单实体  不带channelId
	* @param order
	* @param countWin
	* @param winsRatio
	* @param maxSlipWins
	* @param slipWinsRatio
	 * @return 
	*/

	public GameWarnOrder createWarnOrderWithNoChannelId(GameOrder order, Long countWin, Long winsRatio,
			Long maxSlipWins, Long slipWinsRatio) {
		GameWarnOrder warnOrder = new GameWarnOrder();
		warnOrder.setCountWin(countWin);
		warnOrder.setCreateTime(new Date());
		warnOrder.setIssueCode(order.getIssueCode());
		warnOrder.setLotteryid(order.getLotteryid());
		warnOrder.setMaxslipWins(maxSlipWins);
		warnOrder.setOrderCode(order.getOrderCode());
		warnOrder.setOrderId(order.getId());
		GamePlan plan = gamePlanDao.getGamePlanByOrderId(order.getId());
		if (plan != null) {
			warnOrder.setParentType(2L);
		} else {
			warnOrder.setParentType(1L);
		}
		warnOrder.setSaleTime(order.getSaleTime());
		warnOrder.setSlipWinsratio(slipWinsRatio);
		warnOrder.setStatus(0L);//待审核
		warnOrder.setType(1L); //风险订单
		warnOrder.setUpdateTime(new Date());
		warnOrder.setUserid(order.getUserid());
		warnOrder.setWebIssueCode(order.getWebIssueCode());
		warnOrder.setWinsRatio(winsRatio);
		warnOrder.setTotamount(order.getTotamount());
		return warnOrder;
	}

	/** 
	* @Title: insertWarnOrder
	* @Description: 插入风险订单
	* @param warnOrder
	*/

	public void insertWarnOrder(GameWarnOrder warnOrder) {
		if (warnOrder.getChannelId() == null) {
			warnOrder.setChannelId(gamePlanDao.getChannelIdByOrderId(warnOrder.getOrderId()));
		}
		warnOrderDao.insert(warnOrder);
	}

	/** 
	* @Title: insertWarnUser
	* @Description: 插入风险用户
	* @param warnServiceBean
	*/

	public void insertWarnUser(GameWarnServiceBean warnServiceBean) {
		GameWarnUser warnUser = packageGameWarnUser(warnServiceBean);
		modGameWarnUserAccount(warnUser);
		gameWarnUserDao.insert(warnUser);
	}

	public void saveOrUpdateWarnUser(GameWarnServiceBean warnServiceBean) {
		GameWarnUser warnUserDB = gameWarnUserDao.queryWarnUserByUserIdAndLotteryIdIssueCode(
				warnServiceBean.getUserid(), warnServiceBean.getLotteryid(), warnServiceBean.getIssueCode());

		if (null != warnUserDB) {
			GameWarnUser warnUser = packageGameWarnUser(warnServiceBean);
			warnUser.setId(warnUserDB.getId());
			warnUser.setUserAccount(warnUserDB.getUserAccount());
			if (warnServiceBean.getIsWarn() == 1L || warnUserDB.getType() == 1L) {
				warnUser.setType(1L);
			} else {
				warnUser.setType(0L);
			}
			gameWarnUserDao.update(warnUser);
		} else {
			insertWarnUser(warnServiceBean);
		}
	}

	/** 
	* @Title: modGameWarnUserAccount 
	* @Description: 修改用户金额
	* @param warnUser
	*/

	public void modGameWarnUserAccount(GameWarnUser warnUser) {
		User user = new User();
		try {
			user = customerDao.queryUserById(warnUser.getUserid());
			warnUser.setUserAccount(user.getUserProfile().getAccount());
		} catch (Exception e) {
			log.error("SSCGameDrawService queryUserById error: userId=" + warnUser.getUserid(), e);
		}
	}

	/** 
	* @Title: packageGameWarnUser 
	* @Description: 封装风险用户
	* @param warnUser
	*/
	public GameWarnUser packageGameWarnUser(GameWarnServiceBean warnServiceBean) {
		GameWarnUser warnUser = new GameWarnUser();
		warnUser.setType(warnServiceBean.getIsWarn());
		warnUser.setLotteryid(warnServiceBean.getLotteryid());
		warnUser.setIssueCode(warnServiceBean.getIssueCode());
		warnUser.setUserid(warnServiceBean.getUserid());
		warnUser.setContinuousWinsIssue(warnServiceBean.getContinuousWinsIssue());
		warnUser.setContinuousWinsTimes(warnServiceBean.getContinuousWinsTimes());
		warnUser.setMaxslipWins(warnServiceBean.getMaxSlipWins());
		warnUser.setWinsRatio(warnServiceBean.getIssueUserWinsRatio());
		warnUser.setTotalWins(warnServiceBean.getIssueUserCountWin());
		warnUser.setBetTotamount(warnServiceBean.getIssueUserTotamount());
		return warnUser;
	}

	/** 
	* @Title: insertWarnOrder
	* @Description: 插入风险订单
	* @param warnOrder
	*/

	public void insertWarnOrder(List<GameWarnOrder> warnOrders) {
		for (GameWarnOrder warnOrder : warnOrders) {
			if (warnOrder.getChannelId() == null) {
				warnOrder.setChannelId(gamePlanDao.getChannelIdByOrderId(warnOrder.getOrderId()));
			}
			log.debug(warnOrder.getOrderId()+"检查3 是否为风险用户  warnOrders" + jmapper.toJson(warnOrder) );
		}
	
		warnOrderDao.insert(warnOrders);
	}

	/** 
	* @Title: updateIssuseRedisCache 
	* @Description: 更新奖期相关  首页的缓存
	* @param order
	*/
	@SuppressWarnings("unchecked")
	public void updateIssuseRedisCache(GameOrder order) {
		log.debug("风控模块：update firefrog_index_lastdata_");
		try {
			if (order.getStatus().intValue() == 5 || order.getTotalWin() == null || order.getTotalWin() < 10000000L) {
				return;
			}

			Long lotteryId=order.getLotteryid();
			IndexLottery indexLottery = redisService.getHome(lotteryId);
			 
			Map<String, String> wins = indexLottery.getWins();
			LRUMap winsLRUMap = new LRUMap(120);
			if (wins != null) {
				winsLRUMap.putAll(wins);
			}

			log.debug("风控模块： new User()");
			User user = new User();
			try {
				user = customerDao.queryUserById(order.getUserid());
			} catch (Exception e) {
				log.error(" queryUserById error: userId=" + order.getUserid(), e);
			}
			if (user != null && user.getUserProfile() != null && user.getUserProfile().getAccount() != null
					&& !user.getUserProfile().getAccount().equals("")) {
				log.debug("风控模块：wins.put");
				String userName=StringUtils.isNotEmpty(user.getNickName())?("$"+user.getNickName()):user.getUserProfile().getAccount();
				winsLRUMap.put(userName, order.getTotalWin() + "");
				if(lotteryId==99701){
					String wbeIssueCode= order.getWebIssueCode();
					if(StringUtils.isNotEmpty(wbeIssueCode)){
						wbeIssueCode = wbeIssueCode.substring(0,2)+"/"+wbeIssueCode.substring(2);
						winsLRUMap.put(user.getUserProfile().getAccount()+"-"+wbeIssueCode, order.getTotalWin() + "");						
					}else{
						winsLRUMap.put(user.getUserProfile().getAccount(), order.getTotalWin() + "");					
					}
				}else{
					winsLRUMap.put(user.getUserProfile().getAccount(), order.getTotalWin() + "");					
				}
			}

			indexLottery.setWins(winsLRUMap);
			redisService.setHome(lotteryId,indexLottery); 
		} catch (Exception e) {
			log.error("updateIssuseRedisCache fail", e);
		}
	}

	/** 
	* @Title: queryGameRiskConfig 
	* @Description: 获取审核配置
	* @return
	*/

	public GameRiskConfig queryGameRiskConfig(Long lotteryId) {
		GameRiskConfig config = null;
		try {
			config = gameRiskConfigDao.queryGameRiskConfig(lotteryId);
		} catch (Exception e) {
			log.warn("审核配置为空");
			throw new GameRiskException("审核配置为空");
		}
		return config;
	}

	/** 
	* @Title: isOrderWarn 
	* @Description: 是不是风险订单
	* @param orderMaxSlipWins
	* @param orderMaxSlipWinsRatio
	* @param config
	* @return
	*/
	public boolean isOrderWarn(Long orderMaxSlipWins, Long orderMaxSlipWinsRatio, GameRiskConfig config) {

		return (isWarnUtil(config.getOrderwarnMaxslipWins(), orderMaxSlipWins) || isWarnUtil(
				config.getOrderwarnSlipWinsratio(), orderMaxSlipWinsRatio));

	}

	/** 
	* @Title: isUserWarn 
	* @Description: 是不是风险用户
	* @param orderMaxSlipWins
	* @param orderMaxSlipWinsRatio
	* @param config
	* @return
	*/
	public boolean isUserWarn(Long issueUserCountWin, Long issueUserWinsRatio, GameRiskConfig config) {

		return (isWarnUtil(config.getOrderwarnMaxwins(), issueUserCountWin) || isWarnUtil(
				config.getOrderwarnWinsRatio(), issueUserWinsRatio));

	}

	/** 
	* @Title: isWarnUtil 
	* @Description: 是不是风险数据
	* @param config
	* @param order
	* @return
	*/
	public boolean isWarnUtil(Long config, Long order) {
		return (config != null && config != 0 && config < order);
	}

	public void cancelWarnOrder(Long orderId) {
		warnOrderDao.updateStatus(Arrays.asList(orderId), 3L);
	}

	public void setGameReturnPointFundServcie(IGameReturnPointFundService gameReturnPointFundServcie) {
		this.gameReturnPointFundServcie = gameReturnPointFundServcie;
	}

	public void setGameOrderWinFundServcie(IGameOrderWinFundService gameOrderWinFundServcie) {
		this.gameOrderWinFundServcie = gameOrderWinFundServcie;
	}
 
}
