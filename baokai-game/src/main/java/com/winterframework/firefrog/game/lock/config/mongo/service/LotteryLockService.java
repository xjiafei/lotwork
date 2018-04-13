package com.winterframework.firefrog.game.lock.config.mongo.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.validate.business.IKeyGenerator;
import com.winterframework.firefrog.common.wincaculate.ICommonKeyFactory;
import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGamePackageDao;
import com.winterframework.firefrog.game.dao.IGamePackageItemDao;
import com.winterframework.firefrog.game.dao.IGamePlanDao;
import com.winterframework.firefrog.game.dao.IGamePlanDetailDao;
import com.winterframework.firefrog.game.dao.IGamePointDao;
import com.winterframework.firefrog.game.dao.IGameReturnPointDao;
import com.winterframework.firefrog.game.dao.IGameSlipDao;
import com.winterframework.firefrog.game.dao.vo.GameAward;
import com.winterframework.firefrog.game.dao.vo.GamePackage;
import com.winterframework.firefrog.game.dao.vo.GamePackageItem;
import com.winterframework.firefrog.game.dao.vo.GamePoint;
import com.winterframework.firefrog.game.dao.vo.GameRetPoint;
import com.winterframework.firefrog.game.entity.GameLockEntity;
import com.winterframework.firefrog.game.entity.GameOrder;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.entity.LockPoint;
import com.winterframework.firefrog.game.entity.MoneyMode;
import com.winterframework.firefrog.game.entity.Points;
import com.winterframework.firefrog.game.lock.base.Method;
import com.winterframework.firefrog.game.lock.config.mongo.service.LockIssue.PhaseStatus;
import com.winterframework.firefrog.game.lock.config.mongo.service.LockIssue.PontStatus;
import com.winterframework.firefrog.game.service.IGameAwardGroupService;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGameLockService;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.service.IGameUserAwardGroupService;
import com.winterframework.firefrog.game.service.assist.bet.LotteryPlayMethodKeyGenerator;
import com.winterframework.firefrog.game.util.LhcRedisUtil;
import com.winterframework.firefrog.game.web.dto.GameOrderResponseDTO;
import com.winterframework.firefrog.game.web.dto.GamePlanParm;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.util.JsonMapper;

/** 
 * 封锁核心处理类 
 * @author  Richard
 * @author  Henry
 * @date 2014年9月1日 下午1:50:24 
 */
public abstract class LotteryLockService {

	/**
	 * 检查当前彩种是否支持封锁变价，目前只处理99108(3d)、99109(P3P5)、99401(雙色球)、99701(六合彩)彩种
	 * @param lotteryId 彩種ID
	 * @return true:是、false:否
	 */
	public boolean checkLotteryId(Long lotteryId) {
		log.debug("检查彩种id是否支持封锁变价，lotteryId=" + lotteryId);
		for (Long _lotteryId : lotteryIds) {
			if (lotteryId.equals(_lotteryId)) {
				return true;
			}
		}
		log.debug("彩种【" + lotteryId + "】,暂不支持封锁变价。");
		return false;
	}

	/**
	 * 订单封锁、变价处理主入口
	 * @param gameOrder 訂單
	 * @param userId 用戶ID
	 * @return 返回true 需要封锁变价，如果是false 则可以进行投注。
	 * @throws Exception
	 */
	public boolean haveNoLockAndChange(GameOrder gameOrder, Long userId) throws Exception {

		Long lotteryId = gameOrder.getLottery().getLotteryId();
		Long issueCode = gameOrder.getGameIssue().getIssueCode();
		log.debug("进行封锁变价，lotteryId=" + lotteryId + ",userId=" + userId + ",issueCode=" + issueCode);

		if (!checkLotteryId(lotteryId)) {
			return false;
		}

		// 获取封锁变价配置信息
		GameLockEntity glEntity = gameLockServiceImpl.queryCurrUseGameLockEntity(lotteryId);
		if (null == glEntity) {
			log.error("获取封锁变价参数错误。");
			throw new RuntimeException("获取封锁变价参数错误。");
		}
		//获取缓存中的LockIssue信息，为空根据GameLockEntity配置信息初始化
		LockIssue lockIssueP5 = null;
		LockIssue lockIssue = null;
		if (lotteryId == 99109L) {
			//P3 lockIssue
			lockIssue = getLockIssue(glEntity, 99109L, issueCode, null);
			//P5 lockIssue
			lockIssueP5 = getLockIssue(glEntity, 99110L, issueCode, P5);
		} else {
			lockIssue = getLockIssue(glEntity, lotteryId, issueCode, null);
		}

		// 获取用户獎金組，只有前三_直選_單式(12_10_11) 玩法有 "MAX_VALUE" 的資料。
		Map<String, Long> awardMap = getUserAwardMap(lotteryId, userId);

		// 初始化 lockIssueMap  
		Map<String, Object> lockIssueMap = new HashMap<String, Object>();
		boolean needLockOrCHANGE = false;

		//检查订单的LockPoint是否为NULL，false 表示LockPoint为空，true表示不为空
		//检查订单是否需要检查封锁变价
		boolean needCheckLock = checkLockPointIsNull(gameOrder.getGameIssue().getIssueCode(), gameOrder, userId,
				lockIssueMap, lockIssue, lockIssueP5);

		//如果可以持久化
		if (needCheckLock) {
			List<LockIssue> lockIssueList = new ArrayList<LockIssue>();
			lockIssueMap.put(LOCK_ISSUE_LIST_KEY, lockIssueList);
			log.debug(awardMap.toString());
			/*
			 * 检查订单是否封锁和变价
			 * 不管什么情况，只要达到封锁状态，则不能投注
			 * 进行封锁、变价
			 */
			needLockOrCHANGE = checkGameOrderIsLock(gameOrder.getGameIssue().getIssueCode(), gameOrder, userId,
					glEntity, awardMap, lockIssueMap, lockIssue, lockIssueP5);
			log.info("needLockOrCHANGE:" + needLockOrCHANGE);

		}
		//不需要第二次check 并且可以
		if (!needLockOrCHANGE || !needCheckLock) {
			//如果不需要check，并不需要change
			saveLockIssue2Redis(lockIssueMap);
			return false;
		}

		return needLockOrCHANGE;
	}

	/**
	 * 追号封锁处理主入口
	 * @param gamePlanResponse
	 * @param userId
	 * @param gameOrder
	 * @param gameLocke
	 * @param awardMap 投注方法的實際獎金，只有前三_直選_單式(12_10_11) 玩法有 "MAX_VALUE" 的資料
	 * @param rates
	 * @return
	 * @throws Exception
	 */
	private boolean checkGamePlanIsLock(GameOrderResponseDTO gamePlanResponse, Long userId, GameOrder gameOrder,
			GameLockEntity gameLocke, Map<String, Long> awardMap, List<Rates> rates) throws Exception {

		gamePlanResponse.setGameIssueCode(gameOrder.getGameIssue().getIssueCode());
		Long lotteryId = gameOrder.getLottery().getLotteryId();
		Long issueCode = gameOrder.getGameIssue().getIssueCode();
		boolean needCheckLock = false;
		boolean needLockOrChange = false;
		boolean canSave = false;

		// 获取封锁变价配置信息
		GameLockEntity glEntity = gameLockServiceImpl.queryCurrUseGameLockEntity(lotteryId);
		if (null == glEntity) {
			log.error("获取封锁变价参数错误。");
			throw new RuntimeException("获取封锁变价参数错误。");
		}
		//获取缓存中的LockIssue信息，为空根据GameLockEntity配置信息初始化
		LockIssue lockIssueP5 = null;
		LockIssue lockIssue = null;
		if (lotteryId == 99109L) {
			//P3 lockIssue
			lockIssue = getLockIssue(glEntity, 99109L, issueCode, null);
			//P5 lockIssue
			lockIssueP5 = getLockIssue(glEntity, 99110L, issueCode, P5);
		} else {
			lockIssue = getLockIssue(glEntity, lotteryId, issueCode, null);
		}

		Map<String, Object> lockIssueMap = new HashMap<String, Object>();
		List<LockIssue> lockIssueList = new ArrayList<LockIssue>();
		lockIssueMap.put(LOCK_ISSUE_LIST_KEY, lockIssueList);
		for (GamePlanParm planParam : gamePlanResponse.getGamePlanParm()) {
			boolean thisTurn = checkLockPointIsNull(planParam.getIssueCode(), gameOrder, userId, lockIssueMap,
					lockIssue, lockIssueP5);
			if (thisTurn) {
				needCheckLock = true;
				canSave = false;
				break;
			}
		}
		if (needCheckLock) {

			for (GamePlanParm planParam : gamePlanResponse.getGamePlanParm()) {
				// 检查订单是否封锁和变价
				boolean thisTurnneedLockOrChange = checkGameOrderIsLock(planParam.getIssueCode(), gameOrder, userId,
						gameLocke, awardMap, lockIssueMap, lockIssue, lockIssueP5);
				if (thisTurnneedLockOrChange) {
					needLockOrChange = true;
				}

			}
			if (needLockOrChange) {
			} else {
				canSave = true;
			}
		}
		if (canSave) {
			saveLockIssue2Redis(lockIssueMap);
		}

		return needLockOrChange;
	}

	/**
	 * 检查订单是否需要检查封锁变价(雙色球、六合彩一律須要返回 true 重新檢查)
	 * @param issueCode 獎期號碼
	 * @param gameOrder
	 * @param userId 用戶ID
	 * @param lockIssueMap
	 * @param lockIssue 所有彩種或六合彩(特碼玩法)
	 * @param lockIssueP5 排列5
	 * @return 检查订单的LockPoint是否为NULL，false 表示LockPoint为空，true表示不为空
	 * @throws Exception
	 */
	protected boolean checkLockPointIsNull(Long issueCode, GameOrder gameOrder, Long userId,
			Map<String, Object> lockIssueMap, LockIssue lockIssue, LockIssue lockIssueP5) throws Exception {

		// 如果GameOrder的LockPoint为null,return false
		// 一个订单有多个注单，只有通过封锁变价后再从前台返回来的lockPoint必定不为NULL，
		log.debug("检查注单的LockPoint是否为NULL");
		boolean needRecheck = true;
		Long lotteryId = gameOrder.getLottery().getLotteryId();

		//如果是双色球、六合彩每次都需要重新check
		if (lotteryId == 99401 || lotteryId == 99701) {
			return true;
		}

		//检查slip是否需要再次检查封锁
		for (GameSlip slip : gameOrder.getSlipList()) {
			String betDetail = slip.getBetDetail();
			Long amt = slip.getTotalAmount();
			Long bet = slip.getTotalBet();
			if (!slip.getIssueCode().getIssueCode().equals(issueCode)) {
				continue;
			}
			LockPoint lp = slip.getLockPoints();
			//返点信息。
			BigDecimal retPont = new BigDecimal(getGameRetPoint(slip, userId));
			if (null == lp) {
				continue;
			} else {
				needRecheck = false;
			}
			if (lotteryId.longValue() == 99109) {
				if (isP5YX(slip.getGameBetType().getBetTypeCode())) {
					GameSlip p5Slip = new GameSlip();
					String[] _betDetails = slip.getBetDetail().split(",");
					p5Slip.setMultiple(slip.getMultiple());
					p5Slip.setTotalBet(Long.valueOf((_betDetails[3] + _betDetails[4]).replaceAll("-", "").length()));
					p5Slip.setGameBetType(slip.getGameBetType());
					p5Slip.setGameOrder(gameOrder);
					p5Slip.setIssueCode(slip.getIssueCode());
					p5Slip.setTotalAmount(slip.getTotalAmount() / slip.getTotalBet() * p5Slip.getTotalBet());
					slip.setTotalBet(Long.valueOf((_betDetails[0] + _betDetails[1] + _betDetails[2])
							.replaceAll("-", "").length()));

					slip.setTotalAmount(slip.getTotalAmount() - p5Slip.getTotalAmount());
					slip.setBetDetail(_betDetails[0] + "," + _betDetails[1] + "," + _betDetails[2] + ",-,-");
					p5Slip.setBetDetail("-,-,-," + _betDetails[3] + "," + _betDetails[4]);
					p5Slip.setLockPoints(slip.getLockPoints());
					p5Slip.setMoneyMode(slip.getMoneyMode());
					needRecheck = checkList(p5Slip, lockIssueP5, retPont, 2);
					if (needRecheck) {
						return needRecheck;
					}
				}
				if (isP52(slip.getGameBetType().getBetTypeCode())) {
					needRecheck = checkList(slip, lockIssueP5, retPont, 2);
				} else {
					needRecheck = checkList(slip, lockIssue, retPont, 3);
				}

			} else {
				needRecheck = checkList(slip, lockIssue, retPont, 3);
			}
			slip.setBetDetail(betDetail);
			slip.setTotalAmount(amt);
			slip.setTotalBet(bet);
			if (needRecheck) {
				return needRecheck;
			}
		}

		if (!needRecheck) {
			//如果不需要recheck的话，则需要持久化
			@SuppressWarnings("unchecked")
			List<LockIssue> list = (List<LockIssue>) lockIssueMap.get(LOCK_ISSUE_LIST_KEY);
			if (null == list) {
				list = new ArrayList<LockIssue>();
				lockIssueMap.put(LOCK_ISSUE_LIST_KEY, list);
			}
			list.add(lockIssue);
			if (lockIssueP5 != null)
				list.add(lockIssueP5);
		}

		return needRecheck;
	}

	/**
	 * 获取缓存中的LockIssue信息，为空根据GameLockEntity配置信息初始化
	 * @param gLockEntity 封锁变价实体类
	 * @param lotteryId 彩種ID
	 * @param issueCode 獎期號碼
	 * @param extt Redis key 值中間碼
	 * @return
	 */
	protected LockIssue getLockIssue(GameLockEntity gLockEntity, Long lotteryId, Long issueCode, String extt) {
		String redKey = getRedisKey(lotteryId, issueCode, extt);

		LockIssue lockIssue = getLockIssueByRedisKey(redKey);
		if (lockIssue != null) {
			if (Long.valueOf(99401).equals(lotteryId)) {
				//如果是双色球，每次都充值
				lockIssue.setId(redKey);
				lockIssue.setBlueLimmit(gLockEntity.getBlueSlipVal());
				lockIssue.setRedLimit(gLockEntity.getRedSlipVal());
			}else if (Long.valueOf(99701).equals(lotteryId)){
				//如果是六合彩，每次都需確認
				lockIssue.setId(redKey);
				lockIssue.setUpValue(gLockEntity.getUpVal()*10000);
				lockIssue.setUpValue2(gLockEntity.getUpVal2()*10000);
				lockIssue.setUpValue3(gLockEntity.getUpVal3()*10000);
			}
		} else {
			// 持久化里卖弄存在的一些东西，根据期号获取一个对象
			lockIssue = new LockIssue();
			// 0 表示当前未产生投注金额
			lockIssue.setBetTotal(0L);
			lockIssue.setId(redKey);
			lockIssue.setBlueLimmit(gLockEntity.getBlueSlipVal());
			lockIssue.setRedLimit(gLockEntity.getRedSlipVal());
			//六合彩使用
			if (Long.valueOf(99701).equals(lotteryId)){
				lockIssue.setUpValue(gLockEntity.getUpVal()*10000);
				lockIssue.setUpValue2(gLockEntity.getUpVal2()*10000);
				lockIssue.setUpValue3(gLockEntity.getUpVal3()*10000);
			}
		}
		if (gLockEntity.getChangeStruc() != null) {

			//每次都要重新获取变价结果
			if (extt != null && P5.equals(extt)) {//P5是 不同配置
				List<Rates> rates = JsonMapper.nonAlwaysMapper().fromJson(gLockEntity.getChangeStruc2(),
						JsonMapper.nonDefaultMapper().createCollectionType(LinkedList.class, Rates.class));
				lockIssue.setAllPhases(setLockIssuePhases(rates, gLockEntity.getMinVal2(), lockIssue));
				lockIssue.setUpValue((lockIssue.getAllPhases().get(Integer.valueOf(lockIssue.getAllPhases().size()))
						.getUpLimit()));
			} else {
				List<Rates> rates = JsonMapper.nonAlwaysMapper().fromJson(gLockEntity.getChangeStruc(),
						JsonMapper.nonDefaultMapper().createCollectionType(LinkedList.class, Rates.class));
				lockIssue.setAllPhases(setLockIssuePhases(rates, gLockEntity.getMinVal(), lockIssue));
				lockIssue.setUpValue((lockIssue.getAllPhases().get(Integer.valueOf(lockIssue.getAllPhases().size()))
						.getUpLimit()));
			}
		}

		return lockIssue;
	}

	/** 
	* @Title: checkList 
	* @Description: 检查投注的总金额-返点后 累计是否超过LockIssue配置参数
	* @param lp
	* @param lockIssue
	* @param retPont
	* @param length
	* @return
	*/
	private static boolean checkList(GameSlip lp, LockIssue lockIssue, BigDecimal retPont, int length) {
		List<Points> list = lp.getLockPoints().getPoints();
		lockIssue.setBetTotal(lockIssue.getBetTotal()
				+ BigDecimal.valueOf(lp.getMultiple() * lp.getTotalBet() * 20000)
						.divide(BigDecimal.valueOf((lp.getMoneyMode().equals(MoneyMode.YUAN)) ? 1 :(lp.getMoneyMode().equals(MoneyMode.FEN)?100: 10))).longValue()
				- retPont.longValue());

		for (Points pi : list) {
			if (pi.getPoint().length() != length) {
				continue;
			}
			PontStatus p = lockIssue.getPoints().get(pi.getPoint());
			if (p == null) {
				p = new PontStatus();
				lockIssue.getPoints().put(pi.getPoint(), p);
			}
			p.setTotal(p.getTotal() - pi.getMult() * pi.getRetValue());
			log.debug("第二次【" + pi.getPoint() + "】检查封锁,倍数=" + pi.getMult() + ", 奖金为=" + pi.getRetValue());
			p.setCurrPhase(pi.getCurrPhase());
			if (p.getTotal() + lockIssue.getBetTotal() < lockIssue.getUpValue()) {
				log.debug("第二次[" + pi.getPoint() + "]检查封锁，已达到封锁上线。" + p.getTotal() + "up:" + lockIssue.getUpValue());
				return true;
			}
		}
		return false;
	}

	/**
	 * 当第一次提交订单进行封锁变价檢查后，返回能否持久化
	 * @param issueCode 獎期號碼
	 * @param gameOrder 注單
	 * @param userId 用戶ID
	 * @param gameLocke 
	 * @param awardMap 投注方法的實際獎金，只有前三_直選_單式(12_10_11) 玩法有 "MAX_VALUE" 的資料
	 * @param lockIssueMap
	 * @param lockIssue 所有彩種或六合彩(特碼玩法)
	 * @param lockIssueP5 排列5
	 * @return 未封锁:false, 封锁:true
	 * @throws Exception
	 */
	private boolean checkGameOrderIsLock(Long issueCode, GameOrder gameOrder, Long userId, GameLockEntity gameLocke,
			Map<String, Long> awardMap, Map<String, Object> lockIssueMap, LockIssue lockIssue, LockIssue lockIssueP5)
			throws Exception {

		Long lotteryId = gameOrder.getLottery().getLotteryId();
		boolean isOrderLock = false;

		//根据是否需要变价，重置LockIssue 设置setCurrPhases的PhaseStatus rate=0 minLimit=0
		boolean needCheckChange = checkAppraiseTime(gameLocke.getStartTime(), gameLocke.getEndTime());
		boolean needCheckChangeP5 = checkAppraiseTime(gameLocke.getStartTime2(), gameLocke.getEndTime2());
		reset(needCheckChange, lockIssue);
		if (lockIssueP5 != null) {
			reset(needCheckChangeP5, lockIssueP5);
		}

		//计算最小投注倍数	
		List<GameSlip> slipList = gameOrder.getSlipList();
		for (GameSlip slip : slipList) {
			if (!slip.getIssueCode().getIssueCode().equals(issueCode)) {
				continue;
			}

			// 处理订单slip,进行封锁判断， 如果达到封锁值，更新LockPoint的isLocks为true。
			if (lotteryId == 99109 && isP5(slip.getGameBetType().getBetTypeCode())) {
				isOrderLock = checkGameOrderIsLockP5(slip, gameOrder, userId, gameLocke, awardMap, lockIssueMap,
						lockIssue, lockIssueP5);
			} else {
				isOrderLock = checkGameOrderIsLockOther(slip, gameOrder, userId, gameLocke, awardMap, lockIssueMap,
						lockIssue);
			}
		}

		// 未到达封锁值才保存。
		if (isOrderLock) {
			lockIssueMap.put(IS_LOCK_KEY, new Boolean(true));
		} else {
			lockIssueMap.put(IS_LOCK_KEY, new Boolean(false));
		}
		return isOrderLock;
	}

	/**
	 * 若不处于可变价时间區段，則重置LockIssue 设置setCurrPhases的PhaseStatus rate=0 minLimit=0
	 * @param needCheckChange 是否处于可变价时间區段；是:true、否：false
	 * @param lockIssue
	 */
	private void reset(boolean needCheckChange, LockIssue lockIssue) {
		Map<Integer, PhaseStatus> realAllPhase = new HashMap<Integer, PhaseStatus>();
		if (!needCheckChange) {
			Map<Integer, PhaseStatus> allPhase = lockIssue.getAllPhases();
			PhaseStatus status = allPhase.get(Integer.valueOf(allPhase.size()));
			status.setRate(0L);
			status.setMinLimit(0L);
			realAllPhase.put(Integer.valueOf(1), status);
		} else {
			Map<Integer, PhaseStatus> allPhase = lockIssue.getAllPhases();
			realAllPhase.putAll(allPhase);
		}
		lockIssue.setCurrPhases(realAllPhase);
		log.debug("realAllPhase:" + realAllPhase);
	}

	/**
	 * 处理订单slip 判断是否封锁变价 
	 * @param slip 注單明細
	 * @param gameOrder 訂單
	 * @param userId 用戶ID
	 * @param gameLocke
	 * @param awardMap 投注方法的實際獎金，只有前三_直選_單式(12_10_11) 玩法有 "MAX_VALUE" 的資料
	 * @param lockIssueMap
	 * @param lockIssue
	 * @return
	 * @throws Exception
	 */
	private boolean checkGameOrderIsLockOther(GameSlip slip, GameOrder gameOrder, Long userId,
			GameLockEntity gameLocke, Map<String, Long> awardMap, Map<String, Object> lockIssueMap, LockIssue lockIssue)
			throws Exception {
		boolean isOrderLock = false;
		Long minimalMult = slip.getMultiple().longValue();

		SlipAssist sa = new SlipAssist(slip, lockIssue);
		dealWithSlip(gameOrder, userId, gameLocke, awardMap, sa);

		if (minimalMult > sa.getRealBetMult()) {
			minimalMult = sa.getRealBetMult();
		}

		slip.getLockPoints().setRealBeishu(minimalMult);
		@SuppressWarnings("unchecked")
		List<LockIssue> list = (List<LockIssue>) lockIssueMap.get(LOCK_ISSUE_LIST_KEY);
		if (sa.getIsChange() || sa.getIsLock()) {
			//如果任何一个有封锁或者变价的话，则封锁变价为true
			isOrderLock = true;
			dealWithSlipPointToMinMulty(sa);
		}
		if (isOrderLock) {
			//如果锁了的话，需要修改倍数以及投注金额
			if (minimalMult > 0) {
				slip.setTotalAmount(slip.getTotalAmount() / slip.getMultiple() * minimalMult);
				slip.setMultiple(minimalMult.intValue());
			} else {
				slip.setMultiple(0);
				slip.setTotalAmount(0L);
			}

		} else {
			this.dealWithSlipIssue(sa, false);
			list.add(lockIssue);
			lockIssueMap.put(LOCK_ISSUE_LIST_KEY, list);
		}
		return isOrderLock;
	}

	/**
	 * 处理P5订单slip 判断是否封锁变价 
	 * @param slip
	 * @param gameOrder
	 * @param userId
	 * @param gameLocke
	 * @param awardMap 投注方法的實際獎金，只有前三_直選_單式(12_10_11) 玩法有 "MAX_VALUE" 的資料
	 * @param lockIssueMap
	 * @param lockIssue
	 * @param lockIssueP5
	 * @return
	 * @throws Exception
	 */
	private boolean checkGameOrderIsLockP5(GameSlip slip, GameOrder gameOrder, Long userId, GameLockEntity gameLocke,
			Map<String, Long> awardMap, Map<String, Object> lockIssueMap, LockIssue lockIssue, LockIssue lockIssueP5)
			throws Exception {
		boolean isOrderLock = false;
		//默认倍数
		Long minimalMult = slip.getMultiple().longValue();
		SlipAssist sa = null;
		SlipAssist sa5 = null;
		boolean hasP3 = true;
		boolean hasP5 = false;
		//如果是好P5的话，把slip拆解成两个slip分别处理。因为处理流程是有区别的
		GameSlip p5Slip = new GameSlip();
		GameSlip p3Slip = new GameSlip();
		//是否只是后二
		hasP5 = isP52(slip.getGameBetType().getBetTypeCode());
		hasP3 = false;
		if (isP5YX(slip.getGameBetType().getBetTypeCode())) {
			//如果前三位不是全控，则包含前三
			hasP3 = !slip.getBetDetail().startsWith("-,-,-,");
			//如果后两位不是全控，这包含后二
			hasP5 = !slip.getBetDetail().endsWith(",-,-");
			String[] _betDetails = slip.getBetDetail().split(",");

			if (hasP5) {
				//如果后二有数据的话						
				p5Slip.setMultiple(slip.getMultiple());
				p5Slip.setTotalBet(Long.valueOf((_betDetails[3] + _betDetails[4]).replaceAll("-", "").length()));
				p5Slip.setGameBetType(slip.getGameBetType());
				p5Slip.setGameOrder(gameOrder);
				p5Slip.setIssueCode(slip.getIssueCode());
				p5Slip.setTotalAmount(slip.getTotalAmount() / slip.getTotalBet() * p5Slip.getTotalBet());
				p5Slip.setBetDetail("-,-,-," + _betDetails[3] + "," + _betDetails[4]);
				p5Slip.setMoneyMode(slip.getMoneyMode());
				 
				//如果有P5的话
				sa5 = new SlipAssist(p5Slip, lockIssueP5);
				dealWithSlip(gameOrder, userId, gameLocke, awardMap, sa5);
				minimalMult = sa5.getRealBetMult();
				if (slip.getLockPoints() == null) {
					slip.setLockPoints(p5Slip.getLockPoints());
				} else {
					slip.getLockPoints().getLocks().putAll(p5Slip.getLockPoints().getLocks());
					slip.getLockPoints().getPoints().addAll(p5Slip.getLockPoints().getPoints());
				}
			}

			if (hasP3) {
				p3Slip.setMultiple(slip.getMultiple());
				p3Slip.setTotalBet(Long.valueOf((_betDetails[0]+_betDetails[1] + _betDetails[2]).replaceAll("-", "").length()));
				p3Slip.setGameBetType(slip.getGameBetType());
				p3Slip.setGameOrder(gameOrder);
				p3Slip.setIssueCode(slip.getIssueCode());
				p3Slip.setTotalAmount(slip.getTotalAmount() - (p5Slip.getTotalAmount()==null?0L:p5Slip.getTotalAmount()));
				p3Slip.setBetDetail(_betDetails[0] + "," + _betDetails[1] + "," + _betDetails[2] + ",-,-");
				p3Slip.setMoneyMode(slip.getMoneyMode());
				
				sa = new SlipAssist(p3Slip, lockIssue);
				dealWithSlip(gameOrder, userId, gameLocke, awardMap, sa);
				if (minimalMult > sa.getRealBetMult()) {
					minimalMult = sa.getRealBetMult();
				}
				if (slip.getLockPoints() == null) {
					slip.setLockPoints(p3Slip.getLockPoints());
				} else {
					slip.getLockPoints().getLocks().putAll(p3Slip.getLockPoints().getLocks());
					slip.getLockPoints().getPoints().addAll(p3Slip.getLockPoints().getPoints());
				}
			}
		} else {
			sa5 = new SlipAssist(slip, lockIssueP5);
			dealWithSlip(gameOrder, userId, gameLocke, awardMap, sa5);
			if (minimalMult > sa5.getRealBetMult()) {
				minimalMult = sa5.getRealBetMult();
			}
		}

		//--------------------------
		slip.getLockPoints().setRealBeishu(minimalMult);
		@SuppressWarnings("unchecked")
		List<LockIssue> list = (List<LockIssue>) lockIssueMap.get(LOCK_ISSUE_LIST_KEY);
		if (sa != null) {
			if (sa.getIsChange() || sa.getIsLock()) {
				//如果任何一个有封锁或者变价的话，则封锁变价为true
				isOrderLock = true;
				dealWithSlipPointToMinMulty(sa);
			}
		}
		if (sa5 != null) {
			if (sa5.getIsChange() || sa5.getIsLock()) {
				//如果任何一个有封锁或者变价的话，则封锁变价为true
				isOrderLock = true;
				dealWithSlipPointToMinMulty(sa5);
			}
		}
		if (isOrderLock) {
			//如果锁了的话，需要修改倍数以及投注金额

			slip.setTotalAmount(slip.getTotalAmount() / slip.getLockPoints().getBeishu() * minimalMult);
			slip.setMultiple(minimalMult.intValue());
		} else {
			if (sa != null) {
				this.dealWithSlipIssue(sa, false);
				list.add(lockIssue);
				lockIssueMap.put(LOCK_ISSUE_LIST_KEY, list);
			}
			if (sa5 != null) {
				this.dealWithSlipIssue(sa5, true);
				list.add(lockIssueP5);
				lockIssueMap.put(LOCK_ISSUE_LIST_KEY, list);
			}
		}

		return isOrderLock;
	}

	/**
	 * 注單明細輔助物件。
	 * @author Pogi.Lin
	 */
	protected static final class SlipAssist {
		private Long realBetMult;
		/**注單单倍返点*/
		private Long realRtnPoint;
		/**需要投注倍数*/
		private Long oraBetMult;
		private String betDetail;
		private Long totalAmount;
		/**是否封鎖*/
		private Boolean isLock = false;
		/**是否變價*/
		private Boolean isChange = false;
		private GameSlip gameSlip;
		/**獎期封鎖資料(來源存在 Radis，Key:"GAME_LOCK_"lotteryid_key_issueCode)*/
		private LockIssue lockIssue;
		/**此注單明細遭封鎖時的盈虧值(最大不超過封鎖值上限)*/
		private Long lockCheckValue;

		/**
		 * 取得需要投注倍数。
		 * @return
		 */
		public Long getOraBetMult() {
			return oraBetMult;
		}
		/**
		 * 設定需要投注倍数 。
		 * @param oraBetMult
		 */
		public void setOraBetMult(Long oraBetMult) {
			this.oraBetMult = oraBetMult;
		}

		public GameSlip getGameSlip() {
			return gameSlip;
		}

		public String getBetDetail() {
			return betDetail;
		}

		public void setBetDetail(String betDetail) {
			this.betDetail = betDetail;
		}

		public void setGameSlip(GameSlip gameSlip) {
			this.gameSlip = gameSlip;
		}

		public Long getTotalAmount() {
			return totalAmount;
		}

		public void setTotalAmount(Long totalAmount) {
			this.totalAmount = totalAmount;
		}
		/**
		 * 取得獎期封鎖資料(來源存在 Radis，Key:"GAME_LOCK_"lotteryid_key_issueCode)。
		 * @return
		 */
		public LockIssue getLockIssue() {
			return lockIssue;
		}
		/**
		 * 設定獎期封鎖資料(來源存在 Radis，Key:"GAME_LOCK_"lotteryid_key_issueCode)。
		 * @param lockIssue
		 */
		public void setLockIssue(LockIssue lockIssue) {
			this.lockIssue = lockIssue;
		}
		/**
		 * <pre>
		 * 初始化 For 一般彩種
		 * gameSlip = gameSlip
		 * realBetMult = gameSlip.multiple(倍數)
		 * oraBetMult = gameSlip.multiple(倍數)
		 * totalAmount = gameSlip.totalAmount(總金額)
		 * lockIssue = lockIssue
		 * </pre>
		 * @param gameSlip
		 * @param lockIssue
		 */
		public SlipAssist(GameSlip gameSlip, LockIssue lockIssue) {
			//初始化 记录订单的投注金额 投注内容以及投足倍数
			this.gameSlip = gameSlip;
			this.realBetMult = gameSlip.getMultiple().longValue();
			this.oraBetMult = gameSlip.getMultiple().longValue();
			this.totalAmount = gameSlip.getTotalAmount();
			this.lockIssue = lockIssue;
		}
		/**
		 * <pre>
		 * 初始化 For 六合彩
		 * gameSlip = gameSlip
		 * realBetMult = gameSlip.singleWin(單注獎金)
		 * oraBetMult = gameSlip.singleWin(單注獎金)
		 * totalAmount = gameSlip.totalAmount(總金額)
		 * lockIssue = lockIssue
		 * </pre>
		 * @param gameSlip
		 * @param lockIssue
		 * @param lotteryId
		 */
		public SlipAssist(GameSlip gameSlip, LockIssue lockIssue,Long lotteryId) {
			//初始化 记录订单的投注金额 投注内容以及賠率
			if(lotteryId==99701){
				this.gameSlip = gameSlip;
				//賠率
				this.realBetMult = gameSlip.getSingleWin();
				this.oraBetMult = gameSlip.getSingleWin();
				this.totalAmount = gameSlip.getTotalAmount();
				this.lockIssue = lockIssue;
			}
		}

		public Long getRealBetMult() {
			return realBetMult;
		}
		/**
		 * 取得注單单倍返点。
		 * @return
		 */
		public Long getRealRtnPoint() {
			return realRtnPoint;
		}
		/**
		 * 設定注單单倍返点。
		 * @param realRtnPoint
		 */
		public void setRealRtnPoint(Long realRtnPoint) {
			this.realRtnPoint = realRtnPoint;
		}

		public void setRealBetMult(Long realBetMult) {
			if (realBetMult == null)
				realBetMult = 0L;
			if (this.realBetMult > realBetMult)
				this.realBetMult = realBetMult;
		}
		/**
		 * 取得是否封鎖。
		 * @return
		 */
		public Boolean getIsLock() {
			return isLock;
		}
		/**
		 * 設定是否封鎖。
		 * @param isLock
		 */
		public void setIsLock(Boolean isLock) {
			this.isLock = isLock;
		}
		/**
		 * 取得是否變價。
		 * @return
		 */
		public Boolean getIsChange() {
			return isChange;
		}
		/**
		 * 設定是否變價。
		 * @param isChange
		 */
		public void setIsChange(Boolean isChange) {
			this.isChange = isChange;
		}
		/**
		 * 取得此注單明細遭封鎖時的盈虧值(最大不超過封鎖值上限)。
		 * @return
		 */
		public Long getLockCheckValue() {
			return lockCheckValue;
		}
		/**
		 * 設定此注單明細遭封鎖時的盈虧值(最大不超過封鎖值上限)。
		 * @param lockCheckValue
		 */
		public void setLockCheckValue(Long lockCheckValue) {
			this.lockCheckValue = lockCheckValue;
		}
	}
	
	/**
	 * 
	 * @param gameOrder
	 * @param userId
	 * @param gameLocke
	 * @param awardMap 投注方法的實際獎金，只有前三_直選_單式(12_10_11) 玩法有 "MAX_VALUE" 的資料
	 * @param slipAssist
	 * @throws Exception
	 */
	private void dealWithSlip(GameOrder gameOrder, Long userId, GameLockEntity gameLocke, Map<String, Long> awardMap,
			SlipAssist slipAssist) throws Exception {

		GameSlip slip = slipAssist.getGameSlip();
		slip.setLockPoints(LockPoint.getEmptyLockPoint(slip.getMultiple().longValue()));

		LockIssue lockIssue = slipAssist.getLockIssue();

		//奖金組奖金
		Long award = 0L;
		//取最大的单注投注奖金
		Long groupAward = getMaxAwardValue(awardMap);

		// slip返点数		
		BigDecimal retPont = new BigDecimal(getGameRetPoint(slip, userId));
		log.debug("返点：retPont=" + retPont);
		//设置单倍返点
		slipAssist.setRealRtnPoint(retPont.longValue() / slip.getMultiple());

		Set<String> betTypeCodes = getBetTypeCodes(slip.getGameBetType().getBetTypeCode(), slip.getBetDetail());
		for (String betTypeCode : betTypeCodes) {
			log.debug("检查是否需要封锁, slipId =" + slip.getId() + ", betTypeCode=" + betTypeCode);
			String betDetail = slip.getBetDetail();

			// 获取组三单式投注奖金
			Long awardZ3 = null;
			// 获取组六单式投注奖金
			Long awardZ6 = null;
			award = getAwardBybetTypeCode(awardMap, betTypeCode, slip.getGameBetType().getBetTypeCode());
			// 组选和值(12_11_33)，组选包胆(12_11_39)，混合组选 (12_11_37)分解为组三单式(12_11_62)，组六单式(12_11_63)
			if (betTypeCode.equals("12_11_33") || betTypeCode.equals("12_11_37") || betTypeCode.equals("12_11_39")) {// 组选和值，组选包胆，混合组选 分解为组三单式，组六单式
				awardZ3 = getAwardBybetTypeCode(awardMap, "12_11_62", slip.getGameBetType().getBetTypeCode());
				awardZ6 = getAwardBybetTypeCode(awardMap, "12_11_63", slip.getGameBetType().getBetTypeCode());
			}
			// 若為角模式投注則尚須除以10
			award=award==null?0L:(award/(slip.getMoneyMode().equals(MoneyMode.JIAO)?10:(slip.getMoneyMode().equals(MoneyMode.FEN)?100:1)));
			awardZ3=awardZ3==null?0L:(awardZ3/(slip.getMoneyMode().equals(MoneyMode.JIAO)?10:(slip.getMoneyMode().equals(MoneyMode.FEN)?100:1)));
			awardZ6=awardZ6==null?0L:(awardZ6/(slip.getMoneyMode().equals(MoneyMode.JIAO)?10:(slip.getMoneyMode().equals(MoneyMode.FEN)?100:1)));
			
			// 获取影响到的point
			Map<String, Integer> map = getInfluncePoint(betTypeCode, gameOrder.getLottery().getLotteryId(), betDetail);
			if (map != null && !map.isEmpty()) {
				Iterator<Map.Entry<String, Integer>> influencePoint = map.entrySet().iterator();
				Entry<String, Integer> entry;
				while (influencePoint.hasNext()) {
					entry = influencePoint.next();
					// 组选和值(12_11_33)，组选包胆(12_11_39)，混合组选 (12_11_37)分解为组三单式(12_11_62)，组六单式(12_11_63)
					if (betTypeCode.equals("12_11_33") || betTypeCode.equals("12_11_37")
							|| betTypeCode.equals("12_11_39")) {
						Set<Character> set = new HashSet<Character>();
						set.add(entry.getKey().charAt(0));
						set.add(entry.getKey().charAt(1));
						set.add(entry.getKey().charAt(2));
						if (set.size() == 3) {
							award = awardZ6;
						} else {
							award = awardZ3;
						}
					}

					log.debug("begin to deal with point:" + entry + " +method:" + betTypeCode);
					// 倍数
					Map<String, PontStatus> points = lockIssue.getPoints();
					// 变价信息。
					PontStatus pont = points.get(entry.getKey());
					// 取不到，标识没有该价格，
					if (null == pont) {
						pont = new PontStatus();
						// 1阶段
						pont.setCurrPhase(0);
						pont.setTotal(0L);
						points.put(entry.getKey(), pont);
					}
					// 计算单倍对betTotal影响
					BigDecimal singleTotal = BigDecimal.valueOf(slip.getTotalAmount()).subtract(retPont)
							.divide(BigDecimal.valueOf(slip.getMultiple()), 2, RoundingMode.HALF_UP);
					Map<String, Long> needBetMulty = new HashMap<String, Long>();
					//设置需要投注倍数 
					needBetMulty.put("needMulty", slipAssist.getOraBetMult());
					//设置投注总金额
					needBetMulty.put("totalBet", lockIssue.getBetTotal());
					//needBetMulty只针对当前point做处理
					//处理slip部分
					resetPhase(slipAssist.getLockIssue(), pont);
					nextLockBet(entry, pont, singleTotal, gameLocke.getMinVal(), groupAward, award,
							lockIssue.getBetTotal(), 0L, needBetMulty, slip, slipAssist,true);
				}
			}
		}
	}

	/**
	 * 倍数缩减为真正倍数
	 * @param sa
	 * @return
	 */
	private boolean dealWithSlipPointToMinMulty(SlipAssist sa) {
		LockIssue issue = sa.getLockIssue();
		GameSlip slip = sa.getGameSlip();
		LockPoint lps = slip.getLockPoints();
		long finalBetMulty = slip.getLockPoints().getRealBeishu();
		Map<String, Long> mps = new HashMap<String, Long>();
		Collections.sort(lps.getPoints(), new Comparator<Points>() {
			@Override
			public int compare(Points o1, Points o2) {
				return o1.getCurrPhase() - o2.getCurrPhase();
			}

		});
		Iterator<Points> stuIter = lps.getPoints().iterator();
		while (stuIter.hasNext()) {
			Points ps = stuIter.next();
			Long po = mps.get(ps.getPoint());
			if (po == null) {
				//如果倍数比最终磕头倍数小
				if (finalBetMulty >= ps.getMult()) {
					mps.put(ps.getPoint(), ps.getMult());
				} else {
					mps.put(ps.getPoint(), finalBetMulty);
					ps.setMult(finalBetMulty);
				}
			} else {
				//如果上一次已经大雨最大值了，后面的一律删除掉
				if (po >= finalBetMulty) {
					stuIter.remove();
				} else if ((po + ps.getMult()) > finalBetMulty) {
					ps.setMult(finalBetMulty - po);
					mps.put(ps.getPoint(), finalBetMulty);
				} else {
					mps.put(ps.getPoint(), po + ps.getMult());
				}
			}
			dealWithIssulePoint(issue, ps);
		}
		return true;
	}

	private boolean dealWithSlipIssue(SlipAssist sa, boolean ifP5) {

		LockIssue issue = sa.getLockIssue();
		GameSlip slip = sa.getGameSlip();
		if (ifP5) {
			//如果是怕咧5，才重新she
			if (sa.getRealBetMult() == 0) {
				slip.setMultiple(0);
				slip.setTotalAmount(0L);
			}
		}
		LockPoint lps = slip.getLockPoints();
		long finalBetMulty = sa.getRealBetMult();
		Map<String, Long> mps = new HashMap<String, Long>();
		Collections.sort(lps.getPoints(), new Comparator<Points>() {
			@Override
			public int compare(Points o1, Points o2) {
				return o1.getCurrPhase() - o2.getCurrPhase();
			}
		});
		Iterator<Points> stuIter = lps.getPoints().iterator();
		while (stuIter.hasNext()) {
			Points ps = stuIter.next();
			if (ifP5 && ps.getPoint().length() != 2) {
				//如果是P5,则轮训
				continue;
			}
			Long po = mps.get(ps.getPoint());
			if (po == null) {
				if (finalBetMulty == 0 || ps.getMult() == 0) {
					stuIter.remove();
					continue;
				}

				//如果倍数比最终磕头倍数小
				if (finalBetMulty >= ps.getMult()) {
					mps.put(ps.getPoint(), lps.getRealBeishu());
				} else {
					mps.put(ps.getPoint(), finalBetMulty);
					ps.setMult(finalBetMulty);
				}
			} else {
				//如果上一次已经大雨最大值了，后面的一律删除掉

				if (po >= finalBetMulty) {
					stuIter.remove();
					continue;
				} else if ((po + ps.getMult()) > finalBetMulty) {
					ps.setMult(finalBetMulty - po);
					mps.put(ps.getPoint(), finalBetMulty);
				} else {
					mps.put(ps.getPoint(), po + ps.getMult());

				}
			}
			dealWithIssulePoint(issue, ps);
		}
		issue.setBetTotal(issue.getBetTotal() + slip.getTotalAmount() - sa.getRealRtnPoint() * sa.getRealBetMult());

		return true;

	}

	private static void dealWithIssulePoint(LockIssue issue, Points ps) {
		PontStatus point = issue.getPoints().get(ps.getPoint());
		if (point == null) {
			point = new PontStatus();
			issue.getPoints().put(ps.getPoint(), point);
		}
		point.setCurrPhase(ps.getCurrPhase());
		point.setTotal(point.getTotal() - ps.getMult() * ps.getRetValue());
	}
	
	/**
	 * 
	 * @param entry
	 * @param pont
	 * @param sinlgChangeBetTotal =单注收益=单注的（投注-返点）
	 * @param minValue
	 * @param groupAward
	 * @param betAward
	 * @param lockBetTotal
	 * @param betAmount
	 * @param needMultyp key:"needMulty"(需要投注倍数)、"totalBet"(投注总金额)
	 * @param slip
	 * @param slipAssist
	 * @param isFirst
	 */
	private void nextLockBet(Entry<String, Integer> entry, PontStatus pont, BigDecimal sinlgChangeBetTotal,
			Long minValue, Long groupAward, Long betAward, Long lockBetTotal, Long betAmount,
			Map<String, Long> needMultyp, GameSlip slip, SlipAssist slipAssist,boolean isFirst) {
		//每次进来都需要重新设置一下当前phase
		Map<Integer, PhaseStatus> currPhases = slipAssist.getLockIssue().getCurrPhases();
		Long needMulty = needMultyp.get("needMulty");

		PhaseStatus status = currPhases.get(pont.getCurrPhase() + 1);
		if (status == null) {
			if(isFirst){
				status=currPhases.get(1);
			}else{
			//如果已经是最后一个区间的话
				addLock(slip, entry.getKey(), needMulty.longValue(), slipAssist);
				return;
			}
		}
		log.debug("lockBetTotal:" + lockBetTotal);
		log.debug("status:" + status);
		log.debug("betAmount:" + betAmount);
		log.debug("pont.getTotal():" + pont.getTotal());
		// 当前盈亏-变价区上限
		BigDecimal diffValue = (BigDecimal.valueOf(lockBetTotal)).add(BigDecimal.valueOf(pont.getTotal())).subtract(
				BigDecimal.valueOf(status.getUpLimit() + betAmount));
		if (diffValue.longValue() <= 0) {
			//如果当前差额已经小于0
			addLock(slip, entry.getKey(), needMulty.longValue(), slipAssist);
			//设置可投注倍数
			return;
		}
		// 当前变价趋的奖金
		BigDecimal currenAward = calculate(betAward, groupAward, minValue, BigDecimal.valueOf(status.getRate()));
		if (currenAward.intValue() < 0) {
			log.info("currentAward<0:" + currenAward.intValue());
			//如果最新的奖金是小于-的话
			addLock(slip, entry.getKey(), needMulty.longValue(), slipAssist);
			//设置可投注倍数
			return;
		}
		if (currenAward.subtract(sinlgChangeBetTotal).longValue() <= 0) {
			log.info("currenAward.subtract(sinlgChangeBetTotal).intValue()<0:" + currenAward.intValue());
			//当奖金没有投注多的时候
			addChangePrice(slip, entry, pont.getCurrPhase(), needMulty.longValue(), currenAward.longValue(),
					sinlgChangeBetTotal.longValue(), slipAssist);
			needMultyp.put("needMulty", 0L);
			needMultyp.put("totalBet", lockBetTotal);
			return;
		}

		BigDecimal realMuilt = diffValue.divide(currenAward.subtract(sinlgChangeBetTotal), 0, RoundingMode.DOWN);
		log.debug("realMuilt:diffValue" + diffValue + " currenAward:" + currenAward + " sinlgChangeBetTotal"
				+ sinlgChangeBetTotal + " realMuilt:" + realMuilt);
		if (needMulty <= realMuilt.longValue()) {
			addChangePrice(slip, entry, pont.getCurrPhase(), needMulty.longValue(), currenAward.longValue(),
					sinlgChangeBetTotal.longValue(), slipAssist);
			//如果需要投倍数比可投倍数小的话，可以全部都投进去，
			lockBetTotal += BigDecimal.valueOf(needMulty).multiply(sinlgChangeBetTotal).longValue();
			betAmount += BigDecimal.valueOf(needMulty).multiply(currenAward).longValue();
			needMultyp.put("needMulty", 0L);
			needMultyp.put("totalBet", lockBetTotal);
			return;
		} else {
			if (realMuilt.longValue() > 1) {
				log.info("realMuilt.longValue() > 1:" + realMuilt.intValue());
				addChangePrice(slip, entry, pont.getCurrPhase(), realMuilt.longValue(), currenAward.longValue(),
						sinlgChangeBetTotal.longValue(), slipAssist);
				needMultyp.put("needMulty", needMulty.longValue() - realMuilt.longValue());
				lockBetTotal += realMuilt.multiply(sinlgChangeBetTotal).longValue();
				betAmount += realMuilt.multiply(currenAward).longValue();
			}
			pont.setCurrPhase(pont.getCurrPhase() + 1);
			nextLockBet(entry, pont, sinlgChangeBetTotal, minValue, groupAward, betAward, lockBetTotal, betAmount,
					needMultyp, slip, slipAssist,false);
		}
	}

	// 设置封锁
	private static void addLock(GameSlip slip, String point, Long lockMulty, SlipAssist slipAssist) {
		slip.getLockPoints().getLocks().put(point, lockMulty);
		slipAssist.setIsLock(true);
		log.debug("lock point:" + point + " " + lockMulty + " " + slipAssist.getRealBetMult());
		slip.getLockPoints().setRealBeishu(slip.getLockPoints().getBeishu() - lockMulty);
		//如果需要投注的倍数早曾的最新的投注倍数比slip最小投注倍数还小的话，更新最小投注倍数
		if (lockMulty > 0 && slip.getMultiple() - lockMulty < slipAssist.getRealBetMult()) {
			slipAssist.setRealBetMult(slipAssist.getOraBetMult() - lockMulty);
		}
	}

	/**
	 * 设置变价
	 * @param slip
	 * @param entry
	 * @param currPhase
	 * @param multy
	 * @param currBet
	 * @param single
	 * @param slipAssist
	 */
	private static void addChangePrice(GameSlip slip, Entry<String, Integer> entry, Integer currPhase, Long multy,
			Long currBet, Long single, SlipAssist slipAssist) {
		Points ps = new Points();
		ps.setPoint(entry.getKey());
		ps.setSingle(single);
		ps.setMult(multy);
		ps.setRetValue(currBet);
		ps.setCurrPhase(currPhase);
		ps.setSingleBet(entry.getValue().longValue());

		if (slip.getLockPoints() != null)
			slip.getLockPoints().getPoints().add(ps);
		if (currPhase > 0) {
			//如果不是第一阶段					
			slipAssist.setIsChange(true);
		}
	}

	/**p5 玩法列表*/
	private static final List<String> BET_TYPE_LIST = new ArrayList<String>();
	static {
		// P5后二_直选_直选复式
		BET_TYPE_LIST.add("30_10_10");
		// P5后二_直选_直选单式
		BET_TYPE_LIST.add("30_10_11");
		// P5后二_直选_直选和值
		BET_TYPE_LIST.add("30_10_33");
		// P5后二_直选_跨度
		BET_TYPE_LIST.add("30_10_34");
		// P5后二_组选_组选复式
		BET_TYPE_LIST.add("30_11_10");
		// P5后二_组选_组选单式
		BET_TYPE_LIST.add("30_11_11");
		// P5后二_组选_组选和值
		BET_TYPE_LIST.add("30_11_33");
		// P5后二_组选_组选包胆
		BET_TYPE_LIST.add("30_11_39");
		// P5一星_定位胆_复式
		BET_TYPE_LIST.add("31_14_10");
		// P5一星_定位胆_十位
		BET_TYPE_LIST.add("31_14_60");
		// P5一星_定位胆_個位
		BET_TYPE_LIST.add("31_14_61");
	}

	// 获取p5，p3的封锁值，
	private boolean checkUpValBySlip(String betTypeCode) {
		// 一个slip的玩法是一致的; gameLocke.getUpVal2(); //p5
		for (int i = 0; i < BET_TYPE_LIST.size(); i++) {
			String str = BET_TYPE_LIST.get(i);
			if (betTypeCode.equals(str)) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * 是否為排列5一星_定位胆_複式(31_14_10)
	 * @param betType 投注方式
	 * @return
	 */
	private boolean isP5YX(String betType) {
		return betType.equals("31_14_10");
	}
	
	/**
	 * 是否為排列5後二(30)、一星(31)玩法。
	 * @param betType 投注方式
	 * @return
	 */
	private boolean isP5(String betType) {
		return betType.startsWith("31") || betType.startsWith("30");
	}
	
	/**
	 * 是否為排列5後二(30)玩法。
	 * @param betType 投注方式
	 * @return
	 */
	private boolean isP52(String betType) {
		return betType.startsWith("30");
	}

	/**
	 * 针对p5 一星投注內容进行拆分。<br>
	 * 例1：投注內容為 -,-,8,-,- 則返回 31_14_57 + 31_14_10(原投注方式)的 Set 資料。<br>
	 * 例2：投注內容為 -,-,-,0,- 則返回 31_14_60 + 31_14_10(原投注方式)的 Set 資料。<br>
	 * 若非排列5一星_定位胆_複式的投注方式，則返回原投注方式的 Set 資料。
	 * @param betType 投注方式
	 * @param betDetail 注單內容
	 * @return
	 */
	protected Set<String> getBetTypeCodes(String betType, String betDetail) {

		Set<String> betTypes = new HashSet<String>();
		// 來源：slip.getGameBetType().getBetTypeCode();
		String str = betType;
		if (isP5YX(str)) {
			String[] strs = str.split("_");
			String[] bets = betDetail.split(",");

			// 個、十、百位
			for (int i = 0; i < 3; i++) {
				if (!bets[i].equals("-")) {
					betTypes.add(strs[0] + "_" + strs[1] + "_" + 57);
				}
			}
			// 千、萬位
			for (int i = 3; i <= 4; i++) {
				if (!bets[i].equals("-")) {
					betTypes.add(strs[0] + "_" + strs[1] + "_" + 60);
				}
			}

			return betTypes;
		}

		betTypes.add(str);
		return betTypes;
	}

	/**
	 * 若 betType 為排列5一星_定位胆_複式(31_14_10)用 betType 自 awardMap 取實際獎金；<br>
	 * 其他投注方式用 betTypeCode 自 awardMap 取實際獎金。
	 * @param awardMap 投注方法的實際獎金，只有前三_直選_單式(12_10_11) 玩法有 "MAX_VALUE" 的資料
	 * @param betTypeCode 自定義投注方式
	 * @param betType 注單明細的投注方式
	 * @return
	 */
	protected Long getAwardBybetTypeCode(Map<String, Long> awardMap, String betTypeCode, String betType) {
		if (isP5YX(betType)) {
			return awardMap.get(betType);
		}
		return awardMap.get(betTypeCode);
	}

	/**
	 * 保存每个号码的Phase
	 * @param list
	 * @param minVal
	 * @param lockIssue
	 * @return
	 */
	private Map<Integer, PhaseStatus> setLockIssuePhases(List<Rates> list, Long minVal, LockIssue lockIssue) {
		Map<Integer, PhaseStatus> allPhases = new HashMap<Integer, PhaseStatus>();
		if (lockIssue.getAllPhases() != null) {
			allPhases = lockIssue.getAllPhases();
		}
		Integer index = 1;
		for (Rates rate : list) {

			PhaseStatus phaseStatus = new PhaseStatus();
			phaseStatus.setMinLimit(rate.getFrom());
			phaseStatus.setUpLimit(rate.getTo());
			phaseStatus.setMinVal(minVal);
			phaseStatus.setRate(rate.getRate().longValue());

			allPhases.put(index, phaseStatus);
			index++;
		}

		return allPhases;
	}

	/**
	 * 將 Radis 取出的資料轉換成 LockIssue 物件。
	 * @param redisKey
	 * @return
	 */
	public LockIssue getLockIssueByRedisKey(String redisKey) {
		String lockIssueJson = redisClient.get(redisKey);
		LockIssue lockIssue = JsonMapper.nonDefaultMapper().fromJson(lockIssueJson, LockIssue.class);
		return lockIssue;
	}

	/**
	 * 通过彩种和奖期 获取 Radis 內相应的 LockIssue 信息。
	 * @param lotteryId 彩種ID
	 * @param issueCode 當期獎期號碼
	 * @param key 組 redis key 用 
	 * @return
	 */
	public LockIssue getLockIssueByLotteryIdAndIssueCode(Long lotteryId, Long issueCode, String key) {
		String redisKey = getRedisKey(lotteryId, issueCode, key);
		return getLockIssueByRedisKey(redisKey);
	}

	/**
	 * 通过彩种和奖期 获取相应的LockIssue信息。
	 * @param lotteryId
	 * @param issueCode
	 * @return
	 */
	public List<LockIssue> getLockIssuesByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) {
		String redisKey = getRedisKey(lotteryId, issueCode, null);

		Set<String> set = redisClient.keys(redisKey);
		List<LockIssue> list = new ArrayList<LockIssue>();

		for (String str : set) {
			list.add(JsonMapper.nonDefaultMapper().fromJson(str, LockIssue.class));
		}

		return list;
	}

	/**
	 * 組合 redis 使用的 key。
	 * @param lotteryId 彩種ID
	 * @param issueCode 獎期號碼
	 * @param key 
	 * @return "GAME_LOCK_"lotteryid_key_issueCode
	 */
	private String getRedisKey(Long lotteryId, Long issueCode, String key) {

		log.debug("抓取封锁资料生成RedisKey lotteyId={}, issueCode={}, key={}", lotteryId, issueCode, key);
		StringBuffer sb = new StringBuffer();
		sb.append(LOCK_KEY);
		sb.append(lotteryId);
		sb.append("_");
		if (key != null) {
			sb.append(key);
			sb.append("_");
		}
		sb.append(issueCode);

		log.debug("抓取封锁资料时生成的RedisKEY =" + sb.toString());
		return sb.toString();
	}

	/**
	 * 
	 * @Title: undoGamePlanDetail
	 * @Description: 追号预约撤销。
	 * @param issueCode
	 * @param planId
	 * @throws Exception
	 */
	public void undoGamePlanDetail(Long issueCode, Long planId) throws Exception {

		log.debug("进行封锁变价预约撤销操作： issueCode=" + issueCode + ", PlanId=" + planId);

		com.winterframework.firefrog.game.dao.vo.GamePlan gamePlan = gamePlanDao.getPlanVoById(planId);

		Long lotteryId = gamePlan.getLotteryid();
		// 检查当前彩种是否支持封锁变价，目前只处理99108(3d)，99109彩种
		if (!checkLotteryId(lotteryId)) {
			log.debug("彩种【" + lotteryId + "】,暂未支持封锁变价信息，无效回滚。");
			return;
		}

		com.winterframework.firefrog.game.dao.vo.GamePlanDetail gamePlanDetail = gamePlanDetailDao
				.getGamePlanDetailByPlanIdAndIssueCode(planId, issueCode);

		GamePackage gamePackage = gamePackageDao.getById(gamePlan.getPackageId());
		Long userId = gamePackage.getUserid();

		List<GamePackageItem> itemList = gamePackageItemDao.getPackageItemListByPackageId(gamePlan.getPackageId());

		// 用户奖金组
		Map<String, Long> awardMap = getUserAwardMap(lotteryId, userId);

		// 单注最大奖金 如1700
		Long maxAward = getMaxAwardValue(awardMap);

		// 获取变价信息
		GameLockEntity glEntity = gameLockServiceImpl.queryCurrUseGameLockEntity(lotteryId);
		if (null == glEntity) {
			log.error("获取封锁变价参数错误。");
			throw new RuntimeException("获取封锁变价参数错误。");
		}
		List<Rates> rates = JsonMapper.nonAlwaysMapper().fromJson(glEntity.getChangeStruc(),
				JsonMapper.nonDefaultMapper().createCollectionType(LinkedList.class, Rates.class));

		String redisKey = getRedisKey(lotteryId, issueCode, null);
		Long upValue = 0L;
		Long award = 0L;
		Map<String, Integer> map = null;
		for (GamePackageItem item : itemList) {

			if (item.getFileMode() == 1) {
				// 文件模式将文件内容读取出来，读取方式直接读取有误问题？
				File file = new File(item.getBetDetail());
				String content = null;
				try {
					content = FileUtils.readFileToString(file);
				} catch (IOException e) {
					log.error("无法取得文件内容", e);
				}
				item.setBetDetail(content);
			} else {
				item.setBetDetail(item.getBetDetail());
			}

			Set<String> betTypeCodes = getBetTypeCodes(item.getBetTypeCode(), item.getBetDetail());

			for (String betTypeCode : betTypeCodes) {
				// 获取rediskey，
				if (lotteryId == 99109L) {
					log.debug("进行封锁逻辑处理  planId=" + planId + ",lotteryId=" + lotteryId + ", 需要重新判断upValue");

					redisKey = getRedisKey(lotteryId, issueCode, "P3");
					upValue = glEntity.getUpVal();

					if (checkUpValBySlip(betTypeCode)) {
						upValue = glEntity.getUpVal2();
						redisKey = getRedisKey(lotteryId, issueCode, "P5");
					}
					log.debug("进行封锁逻辑处理 planId=" + planId + ",lotteryId=" + lotteryId + ", 重新获取upValue=" + upValue);
				}

				// get lockissue
				LockIssue lockIssue = getLockIssueByRedisKey(redisKey);
				if (null == lockIssue) {
					log.debug("无封锁变价信息，lotteryId=" + lotteryId + ", redisKey = " + redisKey + ", issueCode = "
							+ issueCode);
					return;
				}

				Long betTotal = lockIssue.getBetTotal();
				map = getInfluncePoint(betTypeCode, lotteryId, item.getBetDetail());
				if (map == null) {
					continue;
				}
				award = getAwardBybetTypeCode(awardMap, betTypeCode, item.getBetTypeCode());

				// 减去投注金额。
				betTotal -= item.getTotamount();

				Iterator<Map.Entry<String, Integer>> iter = map.entrySet().iterator();
				Entry<String, Integer> entry;
				while (iter.hasNext()) {

					entry = iter.next();

					// 预计中奖总金额
					Long numTotalAmount = getTotalAmont(item.getTotbets(), award, (item.getMultiple() * gamePlanDetail
							.getMutiple().intValue()));

					Map<String, PontStatus> points = lockIssue.getPoints();
					// 变价信息。
					PontStatus pont = points.get(entry.getKey());

					// 取不到，标识没有该价格，//撤销时必须存在的
					if (null == pont) {
						log.debug("撤销封锁变价时，无该变价信息。key =" + entry.getKey());
						continue;
					}

					// 计算当前变价点
					lockIssue.setBetTotal(betTotal);
					BigDecimal totalBgd = new BigDecimal(pont.getTotal());
					BigDecimal numTotalBgd = new BigDecimal(numTotalAmount);
					pont.setTotal(totalBgd.subtract(numTotalBgd, MathContext.DECIMAL32).longValue());
					points.put(entry.getKey(), pont);
					lockIssue.setPoints(points);

					redisClient.set(redisKey, JsonMapper.nonDefaultMapper().toJson(lockIssue));
				}
			}
		}
	}

	/**
	 * 用户撤销或系统撤销调用。
	 * @param lotteryId
	 * @param issueCode
	 * @param userId
	 * @param orderId
	 * @throws Exception
	 */
	public void undoLock(Long lotteryId, Long issueCode, Long userId, Long orderId) throws Exception {

		log.debug("进行封锁变价的撤销操作： lotteryId=" + lotteryId + ", userId = " + userId + ", issueCode = " + issueCode);

		// 检查当前彩种是否支持封锁变价，目前只处理99108(3d)，99109彩种
		if (!checkLotteryId(lotteryId)) {
			log.debug("彩种【" + lotteryId + "】,暂未支持封锁变价信息，无效回滚。");
			return;
		}
		//GameOrder
		GameOrder gameOrder = gameOrderDao.getOrderById(orderId);

		List<GameSlip> slipList = gameOrderService.querySlipsByOrderId(orderId);

		LockIssue lockIssue = null;
		LockIssue lockIssueP5 = null;

		GameLockEntity glEntity = gameLockServiceImpl.queryCurrUseGameLockEntity(lotteryId);
		if (null == glEntity) {
			log.error("获取封锁变价参数错误。");
			throw new RuntimeException("获取封锁变价参数错误。");
		}
		Map<String, PontStatus> points5 = null;
		if (lotteryId.equals(99109L)) {
			lockIssue = getLockIssue(glEntity, 99109L, issueCode, null);
			lockIssueP5 = getLockIssue(glEntity, 99110L, issueCode, P5);
			points5 = lockIssueP5.getPoints();
		} else {
			lockIssue = getLockIssue(glEntity, lotteryId, issueCode, null);
		}

		Long minSingle = cancultureSlipBaseBet(gameOrder);
		log.debug("彩种:" + lotteryId + ":minSingle" + minSingle + "");
		for (GameSlip slip : slipList) {

			if (lotteryId == 99109) {
				if (isP5(slip.getGameBetType().getBetTypeCode())) {
					if (isP5YX(slip.getGameBetType().getBetTypeCode())) {
						String[] _betDetails = slip.getBetDetail().split(",");
						Long p5BetCount = Long.valueOf((_betDetails[3] + _betDetails[4]).replaceAll("-", "").length());
						Long p3BetCount = Long.valueOf((_betDetails[0] + _betDetails[1] + _betDetails[2]).replaceAll(
								"-", "").length());
						lockIssueP5.setBetTotal(lockIssueP5.getBetTotal() - minSingle
								* (slip.getMoneyMode().equals(MoneyMode.YUAN) ? 10 :(slip.getMoneyMode().equals(MoneyMode.FEN)?100: 1)) * slip.getMultiple()
								* p5BetCount);
						lockIssue.setBetTotal(lockIssue.getBetTotal() - minSingle
								* (slip.getMoneyMode().equals(MoneyMode.YUAN) ? 10 :(slip.getMoneyMode().equals(MoneyMode.FEN)?100: 1))  * slip.getMultiple()
								* p3BetCount);
					} else {
						lockIssueP5.setBetTotal(lockIssueP5.getBetTotal() - minSingle
								* (slip.getMoneyMode().equals(MoneyMode.YUAN) ? 10 :(slip.getMoneyMode().equals(MoneyMode.FEN)?100: 1))  * slip.getMultiple()
								* slip.getTotalBet());
					}
				}
			} else {
				log.debug("lockIssue.getBetTotal:before:" + lockIssue.getBetTotal());
				lockIssue.setBetTotal(lockIssue.getBetTotal() - minSingle
						* (slip.getMoneyMode().equals(MoneyMode.YUAN) ? 10 :(slip.getMoneyMode().equals(MoneyMode.FEN)?100: 1))  * slip.getMultiple()
						* slip.getTotalBet());
				log.debug("lockIssue.getBetTotal:end:" + lockIssue.getBetTotal());
			}
			List<GamePoint> list = gamePointDao.getGamePointsBySlipId(slip.getId());

			for (GamePoint point : list) {
				log.debug("point:" + lotteryId + ":point" + point);

				if (isP5(slip.getGameBetType().getBetTypeCode())) {
					if (isP5YX(slip.getGameBetType().getBetTypeCode())) {

						if (point.getPoint().length() == 2) {
							Map<String, PontStatus> points = lockIssueP5.getPoints();

							PontStatus pont = points.get(point.getPoint());
							BigDecimal totalBgd = new BigDecimal(pont.getTotal());
							pont = points5.get(point.getPoint());
							pont.setTotal(totalBgd.add(BigDecimal.valueOf(point.getRetValue() * point.getMult()),
									MathContext.DECIMAL32).longValue());
							resetPhase(lockIssueP5, pont);
						} else {
							Map<String, PontStatus> points = lockIssue.getPoints();

							PontStatus pont = points.get(point.getPoint());
							BigDecimal totalBgd = new BigDecimal(pont.getTotal());
							log.debug("pointTotal:before:" + pont.getTotal());
							pont.setTotal(totalBgd.add(BigDecimal.valueOf(point.getRetValue() * point.getMult()),
									MathContext.DECIMAL32).longValue());
							log.debug("pointTotal:end:" + pont.getTotal());
							resetPhase(lockIssue, pont);
						}
					} else {
						Map<String, PontStatus> points = lockIssueP5.getPoints();

						PontStatus pont = points.get(point.getPoint());
						if (pont != null && pont.getTotal() != null) {
							BigDecimal totalBgd = new BigDecimal(pont.getTotal());

							pont = points.get(point.getPoint());
							pont = points5.get(point.getPoint());
							pont.setTotal(totalBgd.add(BigDecimal.valueOf(point.getRetValue() * point.getMult()),
									MathContext.DECIMAL32).longValue());
							resetPhase(lockIssueP5, pont);
						}
					}
				} else {
					Map<String, PontStatus> points = lockIssue.getPoints();

					PontStatus pont = points.get(point.getPoint());
					BigDecimal totalBgd = new BigDecimal(pont.getTotal());
					log.debug("pointTotal:before:" + pont.getTotal());
					pont.setTotal(totalBgd.add(BigDecimal.valueOf(point.getRetValue() * point.getMult()),
							MathContext.DECIMAL32).longValue());
					log.debug("pointTotal:end:" + pont.getTotal());
					resetPhase(lockIssue, pont);
				}
			}
		}
		if (lockIssueP5 != null) {
			redisClient.set(lockIssueP5.getId(), JsonMapper.nonDefaultMapper().toJson(lockIssueP5));
		}
		log.debug("before save:" + lockIssue);
		redisClient.set(lockIssue.getId(), JsonMapper.nonDefaultMapper().toJson(lockIssue));
	}

	private static void resetPhase(LockIssue lockIssue, PontStatus status) {
		log.debug("resetPhase:" + status + " lockIssue.getBetTotal():" + lockIssue.getBetTotal());
		if (status.getTotal() + lockIssue.getBetTotal() >= 0) {
			//如果是正收入，则从0开始
			status.setCurrPhase(0);
		}
		if (status.getTotal() + lockIssue.getBetTotal() < lockIssue.getUpValue()) {
			//如果超过了封锁了，直接进入封锁
			status.setCurrPhase(lockIssue.getAllPhases().size());
		}
		for (Entry<Integer, PhaseStatus> test : lockIssue.getAllPhases().entrySet()) {
			if (test.getValue().getUpLimit() < status.getTotal() + lockIssue.getBetTotal()) {
				status.setCurrPhase(test.getKey() - 1);
				log.info("resetPhase:" + status + " test:" + test);
				return;
			}
		}
	}

	private Long cancultureSlipBaseBet(GameOrder order) {
		int allBet = 0;
		List<GameSlip> slips = gameSlipDaoImpl.getSlipsByOrderId(order.getId());
		//返点
		Long retPoint = getGameRetPoint(order.getId());
		for (GameSlip slip : slips) {
			allBet += slip.getMultiple() * slip.getTotalBet() * (slip.getMoneyMode().equals(MoneyMode.YUAN) ? 10 : (slip.getMoneyMode().equals(MoneyMode.FEN)?100:1));
		}
		return (order.getTotalAmount() - retPoint) / allBet;
	}

	/**
	 * 追号封锁变价
	 * @param gamePlanResponse
	 * @param gameOrder
	 * @return
	 * @throws Exception
	 */
	public boolean gamePlanLock(GameOrderResponseDTO gamePlanResponse, GameOrder gameOrder) throws Exception {
		Long lotteryId = gameOrder.getLottery().getLotteryId();
		// 检查当前彩种是否支持封锁变价，目前只处理99108,99109,
		if (!checkLotteryId(lotteryId)) {
			log.debug("彩种【" + lotteryId + "】,暂不支持封锁变价。");
			return false;
		}

		Long issueCode = gameOrder.getGameIssue().getIssueCode();
		Long userId = gameOrder.getGamePackage().getUser().getId();

		log.debug("进行封锁变价，lotteryId=" + lotteryId + ",userId=" + userId + ",issueCode=" + issueCode);

		// 用户奖金组
		Map<String, Long> awardMap = getUserAwardMap(lotteryId, userId);
		// 单注最大奖金 如1700
		Long maxAward = getMaxAwardValue(awardMap);

		// 获取变价信息
		GameLockEntity glEntity = gameLockServiceImpl.queryCurrUseGameLockEntity(lotteryId);
		if (null == glEntity) {
			log.error("获取封锁变价参数错误。");
			throw new RuntimeException("获取封锁变价参数错误。");
		}
		List<Rates> rates = JsonMapper.nonAlwaysMapper().fromJson(glEntity.getChangeStruc(),
				JsonMapper.nonDefaultMapper().createCollectionType(LinkedList.class, Rates.class));

		if (checkGamePlanIsLock(gamePlanResponse, userId, gameOrder, glEntity, awardMap, rates)) {

			log.debug("追号封锁变价检查，该奖期已达到封锁状态，不能进行投注。lotteryId=" + lotteryId + ",issueCode=" + issueCode + ",userId="
					+ userId);

			return true;
		}

		return false;
	}

	/**
	 * 將 lockIssueMap 內 key 值為 "lockIssueList" 的所有 LockIssue 物件轉成 json 後存入 redis 內(redis key = lockIssue.id)。
	 * @param lockIssueMap
	 */
	protected void saveLockIssue2Redis(Map<String, Object> lockIssueMap) {
		@SuppressWarnings("unchecked")
		List<LockIssue> lockList = (List<LockIssue>) lockIssueMap.get(LOCK_ISSUE_LIST_KEY);
		for (LockIssue lockIssue : lockList) {
			redisClient.set(lockIssue.getId(), JsonMapper.nonDefaultMapper().toJson(lockIssue));
		}
	}

	public void updateLockRetPoint(GameOrder gameOrder, Long retPoint) throws Exception {

		Long lotteryId = gameOrder.getLottery().getLotteryId();
		Long issueCode = gameOrder.getGameIssue().getIssueCode();

		log.debug("进行封锁变价返点处理，lotteryId=" + lotteryId + ",issueCode=" + issueCode + ",retPoint=" + retPoint);

		// 检查当前彩种是否支持封锁变价，目前只处理99108(3d)彩种
		if (!checkLotteryId(lotteryId)) {
			log.debug("彩种【" + lotteryId + "】,暂不支持封锁变价。");
			return;
		}

		String redKey = getRedisKey(lotteryId, issueCode, null);
		LockIssue lockIssue = getLockIssueByRedisKey(redKey);
		if (lockIssue == null) {
			return;
		} else {
			lockIssue.setBetTotal(lockIssue.getRetPoint() + retPoint);
			redisClient.set(redKey, JsonMapper.nonDefaultMapper().toJson(lockIssue));
		}
	}

	/**
	 * 取得注單總返點。
	 * @param list 注單明細
	 * @param userId
	 * @return 結果已除1萬
	 * @throws Exception
	 */
	public Long getGameRetPoint(GameSlip list, Long userId) throws Exception {
		Long chain = gameReturnPointDao.getRetunPoint(list, null);
		return list.getTotalAmount() * chain / 10000;
	}

	public Long getGameRetPoint(Long gameOrderId) {
		Long retPoint = 0L;
		GameRetPoint gameRetPoint = gameReturnPointDao.getRetPointByOrderIdWithOutStatus(gameOrderId);
		String chain = gameRetPoint.getRetPointChain();
		String[] chains = chain.split(",");
		for (String string : chains) {
			retPoint += Long.parseLong(string);
		}
		log.debug(" GameOrderId 的返点值为：" + retPoint);
		return retPoint;
	}

	/**
	 * 根据玩法获取对应的influncePoint 信息
	 * @param betTypeCode 投注方式
	 * @param lotteryId 彩種ID
	 * @param betDetail 注單內容
	 * @return 
	 */
	protected Map<String, Integer> getInfluncePoint(String betTypeCode, Long lotteryId, String betDetail) {
		log.debug(" getInfluncePoint  betTypeCode =" + betTypeCode + ",lotteryId = " + lotteryId + ", betDetail ="
				+ betDetail);
		// 根据玩法method获取到GameAward
		String[] betTypeCodes = betTypeCode.split("_");
		IKeyGenerator keyGenerator = new LotteryPlayMethodKeyGenerator(lotteryId, Integer.parseInt(betTypeCodes[0]),
				Integer.parseInt(betTypeCodes[1]), Integer.parseInt(betTypeCodes[2]), seperator);

		// 根据玩法获取对应的influncePoint 信息
		@SuppressWarnings("unchecked")
		List<Method> list = (List<Method>) factory.getObject(keyGenerator);

		if (null != list && !list.isEmpty()) {
			Method method = list.get(0);
			method.setBetContent(betDetail);
			method.setMethodId(betTypeCode);
			method.setLhcRedisUtil(lhcRedisUtil);

			return method.influncePoint();
		}
		return null;
	}

	/**
	 * 中奖金额
	 * @param totalBet
	 * @param award
	 * @param slipMult
	 * @return
	 */
	private Long getTotalAmont(Long totalBet, Long award, Integer slipMult) {
		log.debug("计算中奖金额：totalBet=" + totalBet + ",award=" + award + ",slipMult=" + slipMult);
		return totalBet * award * slipMult;
	}

	/**
	 * 计算变价阶段的奖金
	 * @param award
	 * @param groupAward
	 * @param minVal
	 * @param rate
	 * @return
	 */
	private static BigDecimal calculate(Long award, Long groupAward, Long minVal, BigDecimal rate) {
		log.debug("计算变价阶段的奖金，award=" + award + ",groupAward=" + groupAward + ", minVal=" + minVal + ",rate="
				+ rate.toEngineeringString());

		BigDecimal realRate = BigDecimal.valueOf(groupAward - minVal * 10000).multiply(rate)
				.divide(BigDecimal.valueOf(groupAward).multiply(BigDecimal.valueOf(100)), 4, RoundingMode.HALF_UP);
		return BigDecimal.valueOf(award).multiply(BigDecimal.valueOf(1).subtract(realRate));
	}

	/**
	 * 取最大的单注投注奖金
	 * @param awardMap 獎金組
	 * @return
	 */
	private Long getMaxAwardValue(Map<String, Long> awardMap) {
		return awardMap.get(MAX_VALUE);
	}

	/**
	 * 获取用户投注方法的實際獎金，只有前三_直選_單式(12_10_11) 玩法有 "MAX_VALUE" 的資料。
	 * @param lotteryId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	protected Map<String, Long> getUserAwardMap(Long lotteryId, Long userId) throws Exception {

		// 根据用户信息获取用户对应奖金组信息
		List<GameAward> awards = gameUserAwardGroupServiceImpl.queryLotteryGameAwardsByUser(lotteryId, userId);
		Map<String, Long> awardMap = new HashMap<String, Long>();
		for (GameAward ga : awards) {
			awardMap.put(ga.getBetTypeCode(), ga.getActualBonus());

			// 这里获取最大的单注奖金组奖金信息，比如奖金组1700，其最大的单注奖金为1700【三星直选单式】。
			// 目前因为只处理3d彩种，暂时先处理，以后加彩种，需要优化。
			if (ga.getBetTypeCode().equals("12_10_11")) {
				awardMap.put(MAX_VALUE, ga.getActualBonus());
			}
		}

		return awardMap;
	}

	/**
	 * 判断当前時間是否在可变价時間區段：startTime > sysDate > endTime
	 * @param startTime 
	 * @param endTime
	 * @return 如果不处于可变价时间區段，则返回false，否则返回true
	 * @throws Exception
	 */
	protected boolean checkAppraiseTime(Date startTime, Date endTime) throws Exception {
		if (startTime == null && endTime == null)
			return false;
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

		log.debug("判断是否处于可变价時間區段，startTime({}) ~ endTime({})", format.format(startTime), format.format(endTime));
		Date startDate = format.parse(format.format(startTime));
		Date endDate = format.parse(format.format(endTime));

		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(format.parse(format.format(new Date())));

		return calendar.compareTo(startCalendar) > 0 && calendar.compareTo(endCalendar) < 0;
	}

	private static class Rates {
		private Long from;
		private Long to;
		private BigDecimal rate;

		public Long getFrom() {
			return from;
		}

		public void setFrom(Long from) {
			this.from = from;
		}

		public Long getTo() {
			return to;
		}

		public void setTo(Long to) {
			this.to = to;
		}

		public BigDecimal getRate() {
			return rate;
		}

		public void setRate(BigDecimal rate) {
			this.rate = rate;
		}

	}

	public List<Long> getLotteryIds() {
		return lotteryIds;
	}

	public void setLotteryIds(List<Long> lotteryIds) {
		this.lotteryIds = lotteryIds;
	}

	private static final Logger log = LoggerFactory.getLogger(LockService.class);

	//用于注入封锁奖期 xml配置
	@PropertyConfig(value = "key.seperator")
	private String seperator;
	@Resource(name = "specialLotteryPlayMethoedFactory")
	private ICommonKeyFactory<Method> factory;
	/**封锁变价彩种配置(From applicationContext-resource.xml)*/
	@Resource(name = "lotteryIds")
	private List<Long> lotteryIds;

	@Resource(name = "RedisClient")
	protected RedisClient redisClient;
	/**用于设置标识的奖金组信息*/
	private static final String MAX_VALUE = "MAX_VALUE";
	/**存放在Redis中的key p3_p5*/
	protected static final String LOCK_KEY = "GAME_LOCK_";
	protected static final String LOCK_ISSUE_LIST_KEY = "lockIssueList";
	public static final String IS_LOCK_KEY = "isLock";
	public static final String P5 = "p5";

	@Autowired
	protected IGameLockService gameLockServiceImpl;
	@Autowired
	protected IGameAwardGroupService gameAwardGroupServiceImpl;
	@Autowired
	protected IGameUserAwardGroupService gameUserAwardGroupServiceImpl;
	@Resource(name = "gameReturnPointDaoImpl")
	protected IGameReturnPointDao gameReturnPointDao;
	@Resource(name = "gameOrderServiceImpl")
	protected IGameOrderService gameOrderService;
	@Resource(name = "gamePlanDaoImpl")
	protected IGamePlanDao gamePlanDao;
	@Resource(name = "gamePackageDao")
	protected IGamePackageDao gamePackageDao;
	@Resource(name = "gamePackageItemDao")
	protected IGamePackageItemDao gamePackageItemDao;
	@Resource(name = "gamePlanDetailDaoImpl")
	protected IGamePlanDetailDao gamePlanDetailDao;
	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao userCustomerDao;
	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueService;
	@Resource(name = "gamePointDaoImpl")
	private IGamePointDao gamePointDao;
	@Resource(name = "gameOrderDaoImpl")
	protected IGameOrderDao gameOrderDao;
	@Resource(name = "gameSlipDaoImpl")
	protected IGameSlipDao gameSlipDaoImpl;
	@Resource(name = "lhcRedisUtil")
	protected LhcRedisUtil lhcRedisUtil;
}
