package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGameOrderWinDao;
import com.winterframework.firefrog.game.dao.IGamePlanDao;
import com.winterframework.firefrog.game.dao.IGameRiskConfigDao;
import com.winterframework.firefrog.game.dao.IGameSlipDao;
import com.winterframework.firefrog.game.dao.IGameWarnOrderDao;
import com.winterframework.firefrog.game.dao.IGameWarnUserDao;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameOrderWin;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.dao.vo.GameWarnOrder;
import com.winterframework.firefrog.game.dao.vo.GameWarnUser;
import com.winterframework.firefrog.game.entity.GameRiskConfig;
import com.winterframework.firefrog.game.exception.GameRiskException;
import com.winterframework.firefrog.game.service.IGameOrderWinFundService;
import com.winterframework.firefrog.game.service.IGameReturnPointFundService;
import com.winterframework.firefrog.game.service.IGameWarnService;
import com.winterframework.firefrog.game.service.bean.GameWarnServiceBean;
import com.winterframework.firefrog.game.service.bean.GameWarnServiceCacheBean;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.entity.User;

/** 
* @ClassName GameWarnServiceimpl 
* @Description 审核风控订单入库 
* @author  hugh
* @date 2014年4月11日 下午4:17:37 
*  
*/
@Service("gameWarnServiceImpl")
@Transactional
public class GameWarnServiceImpl implements IGameWarnService {

	private static final Logger log = LoggerFactory.getLogger(GameWarnServiceImpl.class);

	@Resource(name = "gameOrderWinDaoImpl")
	private IGameOrderWinDao gameOrderWinDaoImpl;

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

	/** 
	* @Title: riskFlow 
	* @Description: 风控审核 验奖过程  调用派奖、返点派奖
	* @param lotteryId
	* @param issueCode
	* @return
	*/
	@Override
	public boolean riskFlow(Long lotteryId, Long issueCode) throws GameRiskException {

		//获取用户中奖订单
		HashMap<Long, List<GameOrderWin>> users = getUserGameOrderWins(lotteryId, issueCode);
		if (users == null || users.isEmpty()) {
			return true;
		}

		//获取配置
		GameRiskConfig config = queryGameRiskConfig();

		for (Long userid : users.keySet()) {
			List<GameOrderWin> userGameOrderWins = users.get(userid);
			GameWarnServiceBean warnServiceBean = new GameWarnServiceBean(lotteryId, issueCode, userid);
			GameWarnServiceCacheBean cache = new GameWarnServiceCacheBean();

			for (GameOrderWin gameOrderWin : userGameOrderWins) {
				//处理中奖订单，并设置缓存数据
				dealOrderWin(gameOrderWin, config, cache, warnServiceBean);
			}

			//检查是否为风险用户
			if (isUserWarn(warnServiceBean.getIssueUserCountWin(), warnServiceBean.getIssueUserWinsRatio(), config)) {
				cache.setAllWarnOrdersCache();
			}

			//处理风险订单
			dealWarnOrderFund(cache, warnServiceBean);

			//处理正常订单
			dealNormalOrderFund(cache);
		}

		return false;
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
			//单注中奖金额 
			Long slipWins = gameSlip.getEvaluateWin();
			warnServiceBean.compareAndSetMaxSlipWins(slipWins);
			orderMaxSlipWins = orderMaxSlipWins > slipWins ? orderMaxSlipWins : slipWins;
			//单注中投比
			Long slipWinsRatio = slipWins / gameSlip.getTotamount();
			orderMaxSlipWinsRatio = orderMaxSlipWinsRatio > slipWinsRatio ? orderMaxSlipWinsRatio : slipWinsRatio;
		}
		GameOrder order = gameOrderDaoImpl.getById(orderId);
		//订单总共中奖金额
		Long countWin = gameOrderWin.getCountWin();
		warnServiceBean.addIssueUserCountWin(countWin);
		//订单总共购买金额
		Long totamount = order.getTotamount();
		warnServiceBean.addIssueUserTotamount(totamount);

		GameWarnOrder warnOrder = createWarnOrderWithNoChannelId(order, countWin, countWin / totamount,
				orderMaxSlipWins, orderMaxSlipWinsRatio);
		if (isOrderWarn(orderMaxSlipWins, orderMaxSlipWinsRatio, config)) {
			cache.addTrueGameWarnOrder(warnOrder);
			cache.addFreezenOrderWin(gameOrderWin);
			cache.addFreezenOrder(order);
		} else {
			cache.addMaybeGameWarnOrder(warnOrder);
			cache.addOrderWin(gameOrderWin);
			cache.addOrder(order);
		}
	}

	/** 
	* @Title: dealWarnOrderFund 
	* @Description: 处理风控订单 风险订单入库  风险用户入库 冻结派奖 冻结返点
	* @param cache
	* @param warnServiceBean
	*/
	public void dealWarnOrderFund(GameWarnServiceCacheBean cache, GameWarnServiceBean warnServiceBean) {
		//如果有风险订单
		if (cache.isHaveWarnOrder()) {
			//调用冻结OrderWin
			gameOrderWinFundServcie.orderWinFreeze(cache.getFreezenOrderWins());
			//调用冻结返点
			gameReturnPointFundServcie.returnPointFreeze(cache.getFreezenOrders());
			//插入风控订单
			insertWarnOrder(cache.getTrueGameWarnOrders());
			//插入风控用户			
			insertWarnUser(warnServiceBean);
			cache.clearWarnCache();
		}
	}

	/** 
	* @Title: dealNormalOrderFund 
	* @Description: 处理正常订单  派奖 返点派奖
	* @param cache
	*/
	public void dealNormalOrderFund(GameWarnServiceCacheBean cache) {
		//如果有正常的订单
		if (cache.isHaveNormalOrder()) {
			//调用派奖
			gameOrderWinFundServcie.orderWinFundUpdateWinsStatus(cache.getOrders(), cache.getOrderWins());
			//调用返点派奖
			gameReturnPointFundServcie.returnPointFundUpdateRetsStatus(cache.getOrders());
			cache.clearNormalCache();
		}
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
		List<GameOrderWin> gameOrderWins = getGameOrderWins(lotteryId, issueCode);

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
	* @Title: getGameOrderWins 
	* @Description: 获取这一期中奖订单
	* @param lotteryId
	* @param issueCode
	* @return
	*/
	public List<GameOrderWin> getGameOrderWins(Long lotteryId, Long issueCode) {
		List<GameOrderWin> gameOrderWins;
		try {
			gameOrderWins = gameOrderWinDaoImpl.getGameOrderWinsByLotteryIdAndIssueCode(lotteryId, issueCode);
		} catch (Exception e) {
			log.error("查询中奖订单出错");
			throw new GameRiskException("查询中奖订单出错");
		}
		return gameOrderWins;
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
	* @Title: modGameWarnUserAccount 
	* @Description: 修改用户金额
	* @param warnUser
	*/
	@Transactional(propagation = Propagation.REQUIRED)
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
	* @Title: insertWarnOrder 
	* @Description: 创建风险订单实体  不带channelId
	* @param order
	* @param countWin
	* @param winsRatio
	* @param maxSlipWins
	* @param slipWinsRatio
	 * @return 
	*/
	@Transactional(propagation = Propagation.REQUIRED)
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
		warnOrder.setParentType(new Long(order.getParentid()));
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
	@Transactional(propagation = Propagation.REQUIRED)
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
	@Transactional(propagation = Propagation.REQUIRED)
	public void insertWarnUser(GameWarnServiceBean warnServiceBean) {
		GameWarnUser warnUser = new GameWarnUser();
		warnUser.setLotteryid(warnServiceBean.getLotteryid());
		warnUser.setIssueCode(warnServiceBean.getIssueCode());
		warnUser.setUserid(warnServiceBean.getUserid());
		warnUser.setContinuousWinsIssue(warnServiceBean.getContinuousWinsIssue());
		warnUser.setContinuousWinsTimes(warnServiceBean.getContinuousWinsTimes());
		warnUser.setMaxslipWins(warnServiceBean.getMaxSlipWins());
		warnUser.setWinsRatio(warnServiceBean.getIssueUserWinsRatio());
		warnUser.setTotalWins(warnServiceBean.getIssueUserCountWin());
		modGameWarnUserAccount(warnUser);
		gameWarnUserDao.insert(warnUser);
	}

	/** 
	* @Title: insertWarnOrder
	* @Description: 插入风险订单
	* @param warnOrder
	*/
	@Transactional(propagation = Propagation.REQUIRED)
	public void insertWarnOrder(List<GameWarnOrder> warnOrders) {
		for (GameWarnOrder warnOrder : warnOrders) {
			if (warnOrder.getChannelId() == null) {
				warnOrder.setChannelId(gamePlanDao.getChannelIdByOrderId(warnOrder.getOrderId()));
			}
			warnOrderDao.insert(warnOrder);
		}
	}

	/** 
	* @Title: queryGameRiskConfig 
	* @Description: 获取审核配置
	* @return
	*/
	@Transactional(propagation = Propagation.REQUIRED)
	public GameRiskConfig queryGameRiskConfig() {
		GameRiskConfig config = null;
		try {
			config = gameRiskConfigDao.queryGameRiskConfig(99101L);
			//单注最大中奖金额。
			config.setOrderwarnMaxslipWins(config.getOrderwarnMaxslipWins() == null ? 0L : config
					.getOrderwarnMaxslipWins());
			//单注最大中投比
			config.setOrderwarnSlipWinsratio(config.getOrderwarnSlipWinsratio() == null ? 0L : config
					.getOrderwarnSlipWinsratio());
			//单人单期最大中奖金额
			config.setOrderwarnMaxwins(config.getOrderwarnMaxwins() == null ? 0L : config.getOrderwarnMaxwins());
			//单人单期最大中投比
			config.setOrderwarnWinsRatio(config.getOrderwarnWinsRatio() == null ? 0L : config.getOrderwarnWinsRatio());
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
				config.getOrderwarnSlipWinsratio(), orderMaxSlipWinsRatio)) ? true : false;

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
				config.getOrderwarnWinsRatio(), issueUserWinsRatio)) ? true : false;

	}

	/** 
	* @Title: isWarnUtil 
	* @Description: 是不是风险数据
	* @param config
	* @param order
	* @return
	*/
	public boolean isWarnUtil(Long config, Long order) {
		return (config != null && config != 0 && config < order) ? true : false;
	}

	public void setGameReturnPointFundServcie(IGameReturnPointFundService gameReturnPointFundServcie) {
		this.gameReturnPointFundServcie = gameReturnPointFundServcie;
	}

	public void setGameOrderWinFundServcie(IGameOrderWinFundService gameOrderWinFundServcie) {
		this.gameOrderWinFundServcie = gameOrderWinFundServcie;
	}
}
