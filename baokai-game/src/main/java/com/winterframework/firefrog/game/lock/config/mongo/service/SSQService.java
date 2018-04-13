package com.winterframework.firefrog.game.lock.config.mongo.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.combinatorics.Factory;
import com.winterframework.combinatorics.Generator;
import com.winterframework.combinatorics.ICombinatoricsVector;
import com.winterframework.firefrog.game.dao.vo.GameAward;
import com.winterframework.firefrog.game.dao.vo.GamePackage;
import com.winterframework.firefrog.game.dao.vo.GamePackageItem;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;
import com.winterframework.firefrog.game.entity.GameLockEntity;
import com.winterframework.firefrog.game.entity.GameOrder;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.entity.LockPoint;
import com.winterframework.firefrog.game.entity.MoneyMode;
import com.winterframework.firefrog.game.entity.Points;
import com.winterframework.firefrog.game.lock.config.mongo.service.LockIssue.PhaseStatus;
import com.winterframework.firefrog.game.lock.config.mongo.service.LockIssue.PontStatus;
import com.winterframework.firefrog.game.service.order.utlis.GameSlipUtilsEnum;
import com.winterframework.firefrog.game.web.dto.GameOrderResponseDTO;
import com.winterframework.firefrog.game.web.dto.GamePlanParm;
import com.winterframework.modules.web.util.JsonMapper;

@SuppressWarnings("unused")
@Service("ssqLockService")
public class SSQService extends LotteryLockService {
	private static final Logger log = LoggerFactory.getLogger(SSQService.class);

	public boolean haveNoLockAndChange(GameOrder gameOrder, Long userId) throws Exception {
		Long lotteryId = gameOrder.getLottery().getLotteryId();
		Long issueCode = gameOrder.getGameIssue().getIssueCode();

		log.debug("进行封锁变价，lotteryId=" + lotteryId + ",userId=" + userId + ",issueCode=" + issueCode);
		// 获取变价信息
		GameLockEntity glEntity = gameLockServiceImpl.queryCurrUseGameLockEntity(lotteryId);
		if (null == glEntity) {
			log.error("获取封锁变价参数错误。");
			throw new RuntimeException("获取封锁变价参数错误。");
		}
		// 用户奖金组
		Map<String, Long> awardMap = getUserAwardMap(lotteryId, userId);
		List<GameAward> awards = gameUserAwardGroupServiceImpl.queryLotteryGameAwardsByUser(lotteryId, userId);

		//判断是否为第一次提交。
		Map<String, Object> lockIssueMap = new HashMap<String, Object>();
		boolean needLockOrCHANGE = false;
		//如果可以持久化
		List<LockIssue> lockIssueList = new ArrayList<LockIssue>();
		lockIssueMap.put(LOCK_ISSUE_LIST_KEY, lockIssueList);
		log.debug(awardMap.toString());
		// 检查订单是否封锁和变价
		// 不管什么情况，只有达到封锁状态，则不能投注
		needLockOrCHANGE = checkGameOrderIsLock2(gameOrder.getGameIssue().getIssueCode(), gameOrder, userId, glEntity,
				awardMap, false, lockIssueMap);
		log.info("needLockOrCHANGE:" + needLockOrCHANGE);

		//不需要第二次check 并且可以
		if (!needLockOrCHANGE) {
			//log.error(lockIssueMap.toString());
			saveLockIssue2Redis(lockIssueMap);
			return false;
		}

		return needLockOrCHANGE;

	}
	public void undoGamePlanDetail(Long issueCode, Long planId) throws Exception {
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
		GamePlanDetail planDetail=gamePlanDetailDao.queryGamePlanDetailByPlanIDForUndo(planId, issueCode);

		GameLockEntity glEntity = gameLockServiceImpl.queryCurrUseGameLockEntity(lotteryId);

		if (null == glEntity) {
			log.error("获取封锁变价参数错误。");
			throw new RuntimeException("获取封锁变价参数错误。");
		}
		LockIssue lockIssue = getLockIssue(glEntity, lotteryId, issueCode, null);

		for (GamePackageItem item : itemList) {
			if (item.getFileMode() == 1) {
				// 文件模式将文件内容读取出来，读取方式直接读取有误问题？
				File file = new File(item.getBetDetail());
				String content = null;
				try {
					content = FileUtils.readFileToString(file);
				} catch (IOException e) {
					log.error("读取gamePackageItem投注内容错误，itemId="+item.getId()+e);
					throw new RuntimeException("读取gamePackageItem投注内容错误");
				}
				item.setBetDetail(content);
			}
			Map<String, Integer> map = this.getInfluncePoint(item.getBetTypeCode(),
					item.getBetDetail());
			log.debug("map:"+map.size());
			for (Entry<String, Integer> en : map.entrySet()) {
				PontStatus ps = lockIssue.getPoints().get(en.getKey());
				log.debug("ps:"+ps.getBaishu());
				if (ps != null){
					ps.setBaishu(ps.getBaishu() - en.getValue()*planDetail.getMutiple().intValue());
					log.debug("planDetailMutiple:"+planDetail.getMutiple().intValue());
					if(ps.getBaishu().equals(Integer.valueOf(0))){
						lockIssue.getPoints().remove(en.getKey());
					}
				}
			}
		}
		redisClient.set(lockIssue.getId(), JsonMapper.nonDefaultMapper().toJson(lockIssue));

	}

	public void undoLock(Long lotteryId, Long issueCode, Long userId, Long orderId) throws Exception {

		GameLockEntity glEntity = gameLockServiceImpl.queryCurrUseGameLockEntity(lotteryId);

		if (null == glEntity) {
			log.error("获取封锁变价参数错误。");
			throw new RuntimeException("获取封锁变价参数错误。");
		}
		LockIssue lockIssue = getLockIssue(glEntity, lotteryId, issueCode, null);
		List<GameSlip> slipList = gameOrderService.querySlipsByOrderId(orderId);
		for (GameSlip slip : slipList) {
			Map<String, Integer> map = this.getInfluncePoint(slip.getGameBetType().getBetTypeCode(),
					slip.getBetDetail());
			for (Entry<String, Integer> en : map.entrySet()) {
				PontStatus ps = lockIssue.getPoints().get(en.getKey());
				if (ps != null){
					ps.setBaishu(ps.getBaishu() - en.getValue()*slip.getMultiple());
					if(ps.getBaishu().equals(Integer.valueOf(0))){
						lockIssue.getPoints().remove(en.getKey());
					}
				}
			}
		}
		redisClient.set(lockIssue.getId(), JsonMapper.nonDefaultMapper().toJson(lockIssue));

	}

	@SuppressWarnings("unchecked")
	private boolean checkGameOrderIsLock2(Long issueCode, GameOrder gameOrder, Long userId, GameLockEntity gameLocke,
			Map<String, Long> awardMap, boolean needCheckChange, Map<String, Object> lockIssueMap) throws Exception {
		// 进行封锁判断，如果达到封锁值，更新LockPoint的isLocks为true。

		//Long groupAward = getMaxAwardValue(awardMap);
		Long lotteryId = gameOrder.getLottery().getLotteryId();
		boolean isOrderLock = false;
		// 设置 LockIssue的初始值。
		LockIssue blueLock = getLockIssue(gameLocke, lotteryId, issueCode, null);
		Map<Integer, PhaseStatus> realAllPhase = new HashMap<Integer, PhaseStatus>();
		List<GameSlip> slipList = gameOrder.getSlipList();
		//计算最小投注倍数

		for (GameSlip slip : slipList) {
			if (!slip.getIssueCode().getIssueCode().equals(issueCode)) {
				continue;
			}
			int minimalMult = slip.getMultiple();
			slip.setLockPoints(LockPoint.getEmptyLockPoint(slip.getMultiple().longValue()));
			SlipAssist sa = new SlipAssist(slip, blueLock);
			dealWithSlip(blueLock, gameOrder, slip, userId, gameLocke, awardMap, sa);
			if (minimalMult > sa.getRealBetMult().intValue()) {
				minimalMult = sa.getRealBetMult().intValue();
			}
			slip.getLockPoints().setRealBeishu(sa.getRealBetMult());

			List<LockIssue> list = (List<LockIssue>) lockIssueMap.get(LOCK_ISSUE_LIST_KEY);
			if (sa.getIsLock()) {
				//如果任何一个有封锁或者变价的话，则封锁变价为true
				isOrderLock = true;
				//					List<LockIssue> list = (List<LockIssue>) lockIssueMap.get(LOCK_ISSUE_LIST_KEY);
				dealWithSlipPointToMinMulty(blueLock, slip, minimalMult);
				slip.setMultiple(minimalMult);
				slip.setTotalAmount(slip.getTotalBet() * minimalMult
						* (slip.getMoneyMode().equals(MoneyMode.YUAN) ? 20000L : (slip.getMoneyMode().equals(MoneyMode.YUAN)?200L:2000L)));
			} else {
				//					List<LockIssue> list = (List<LockIssue>) lockIssueMap.get(LOCK_ISSUE_LIST_KEY);
				this.dealWithSlipIssue(blueLock, slip, minimalMult);
				list.add(blueLock);
				lockIssueMap.put(LOCK_ISSUE_LIST_KEY, list);
				lockIssueMap.put(IS_LOCK_KEY, new Boolean(true));
				//slip.setLockPoints(null);
				list.add(blueLock);
				lockIssueMap.put(LOCK_ISSUE_LIST_KEY, list);
			}
		}

		// 未到达封锁值才保存。
		if (!isOrderLock) {
			lockIssueMap.put(IS_LOCK_KEY, new Boolean(false));
		}

		return isOrderLock;
	}

	private boolean dealWithSlipIssue(LockIssue issue, GameSlip slip, int ms) {
		LockPoint lps = slip.getLockPoints();
		long finalBetMulty = slip.getLockPoints().getRealBeishu();
		Map<String, Long> mps = new HashMap<String, Long>();
		Collections.sort(lps.getPoints(), new Comparator<Points>() {
			@Override
			public int compare(Points o1, Points o2) {
				if (o1.getCurrPhase() == null)
					return 0;
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
					continue;
				} else if ((po + ps.getMult()) > finalBetMulty) {
					ps.setMult(finalBetMulty - po);
					mps.put(ps.getPoint(), finalBetMulty);
				} else {
					mps.put(ps.getPoint(), po + ps.getMult());

				}
			}
			dealWithIssulePoint(issue, ps, ms);

			stuIter.remove();
		}
		return true;

	}

	private boolean dealWithSlipPointToMinMulty(LockIssue issue, GameSlip slip, Integer min) {
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
			//dealWithIssulePoint(issue, ps, min);
			stuIter.remove();
		}
		return true;

	}

	private static void dealWithIssulePoint(LockIssue issue, Points ps, int min) {
		PontStatus point = issue.getPoints().get(ps.getPoint());
		if (point == null) {
			point = new PontStatus();
			point.setBaishu(0);
			issue.getPoints().put(ps.getPoint(), point);
		}
		point.setBaishu(point.getBaishu() + min);
		issue.setBetTotal(issue.getBetTotal() + ps.getMult() * ps.getSingle());
	}

	protected void dealWithSlip(LockIssue lockIssue, GameOrder gameOrder, GameSlip slip, Long userId,
			GameLockEntity gameLocke, Map<String, Long> awardMap, SlipAssist slipAssist) throws Exception {
		// 可能是文件上传，存在多注
//		String[] betDetails = GameSlipUtilsEnum.INSTANSE.getBetDetai(gameOrder.getFileMode().getValue(),
//				slip.getBetDetail());
//		String[] betDetails = GameSlipUtilsEnum.INSTANSE.getBetDetai(slip);
		String betDetail = slip.getBetDetail();
//		if (gameOrder.getFileMode().getValue() == 1){
//			for(int i=0;i<betDetails.length;i++){
//				if(i!=betDetails.length-1){
//					betDetail+=betDetails[i]+" ";
//				}else{
//					betDetail+=betDetails[i];
//				}	
//			}
//		}else{
//			betDetail=slip.getBetDetail();
//		}
		// 获取投注奖金
		Long blueLimit = lockIssue.getBlueLimmit();
		Long redLimit = lockIssue.getRedLimit();
		int realMulty = slip.getMultiple();
		// 获取影响到的point
		Map<String, Integer> map = getInfluncePoint(slip.getGameBetType().getBetTypeCode(), betDetail);

		Iterator<Map.Entry<String, Integer>> influencePoint = map.entrySet().iterator();
		Entry<String, Integer> entry;
		while (influencePoint.hasNext()) {

			entry = influencePoint.next();
			int multy = entry.getValue();
			Points ponits = new Points();
			ponits.setMult(slip.getMultiple().longValue());
			ponits.setSingle(0L);
			ponits.setCurrPhase(0);
			ponits.setSingleBet(entry.getValue().longValue());
			ponits.setPoint(entry.getKey());
			slip.getLockPoints().getPoints().add(ponits);
			// 倍数
			Map<String, PontStatus> points = lockIssue.getPoints();
			// 变价信息。
			PontStatus pont = points.get(entry.getKey());
			// 取不到，标识没有该价格，
			if (null == pont) {
				pont = new PontStatus();
				// 1阶段
				pont.setTotal(0L);
				pont.setBaishu(0);
				slipAssist.setRealBetMult(slip.getMultiple().longValue());

			}
			// 计算单倍对betTotal影响	
			boolean isRed = entry.getKey().length() > 5;
			if (isRed) {
				Long leftLimit = (redLimit - pont.getBaishu()) / entry.getValue();
				if (leftLimit < 0) {
					slipAssist.setIsLock(true);
					slipAssist.setRealBetMult(0L);
					slip.getLockPoints().getLocks().put(entry.getKey(), slip.getMultiple().longValue());
				} else if (slip.getMultiple() > leftLimit) {
					slipAssist.setIsLock(true);
					slipAssist.setRealBetMult(leftLimit);
					slip.getLockPoints().getLocks().put(entry.getKey(), slip.getMultiple() - leftLimit);
				} else {
					//
				}
			} else {
				Long leftLimit = (blueLimit - pont.getBaishu()) / entry.getValue();
				if (leftLimit < 0) {
					slipAssist.setIsLock(true);
					slipAssist.setRealBetMult(0L);
					slip.getLockPoints().getLocks().put(entry.getKey(), leftLimit);
				} else if (slip.getMultiple() > leftLimit) {
					slipAssist.setIsLock(true);
					slipAssist.setRealBetMult(leftLimit);
					slip.getLockPoints().getLocks().put(entry.getKey(), slip.getMultiple() - leftLimit);
				} else {
					//continue;
				}
			}

			realMulty = slipAssist.getRealBetMult().intValue() > realMulty ? realMulty : slipAssist.getRealBetMult()
					.intValue();
		}
		slip.setMultiple(realMulty);

	}

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

	

		// 获取变价信息
		GameLockEntity glEntity = gameLockServiceImpl.queryCurrUseGameLockEntity(lotteryId);
		if (null == glEntity) {
			log.error("获取封锁变价参数错误。");
			throw new RuntimeException("获取封锁变价参数错误。");
		}
		if (checkGamePlanIsLock(gamePlanResponse, userId, gameOrder, glEntity)) {

			log.debug("追号封锁变价检查，该奖期已达到封锁状态，不能进行投注。lotteryId=" + lotteryId + ",issueCode=" + issueCode + ",userId="
					+ userId);

			return true;
		}

		return false;
	
	}
	private boolean checkGamePlanIsLock(GameOrderResponseDTO gamePlanResponse, Long userId, GameOrder gameOrder,
			GameLockEntity gameLocke) throws Exception {
		Map<Long, GamePlanParm> map = new HashMap<Long, GamePlanParm>();
		gamePlanResponse.setGameIssueCode(gameOrder.getGameIssue().getIssueCode());
		Long lotteryId = gameOrder.getLottery().getLotteryId();
		Long issueCode = gameOrder.getGameIssue().getIssueCode();
		boolean isAppraiseTime =false;
		boolean needCheckLock = false;
		boolean needLockOrChange = false;
		boolean isChange = false;
		boolean canSave = false;

		Map<String, Object> lockIssueMap = new HashMap<String, Object>();
		List<LockIssue> lockIssueList = new ArrayList<LockIssue>();
		lockIssueMap.put(LOCK_ISSUE_LIST_KEY, lockIssueList);
				
		// 获取封锁变价配置信息
		GameLockEntity glEntity = gameLockServiceImpl.queryCurrUseGameLockEntity(gameOrder.getLottery().getLotteryId());
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
		
		for (GamePlanParm planParam : gamePlanResponse.getGamePlanParm()) {
			boolean thisTurn = checkLockPointIsNull(planParam.getIssueCode(), gameOrder, userId, lockIssueMap, lockIssue , lockIssueP5);
			if (thisTurn) {
				needCheckLock = true;
				canSave = false;
				break;
			}
		}
		if (needCheckLock) {
			for (GamePlanParm planParam : gamePlanResponse.getGamePlanParm()) {
				// 检查订单是否封锁和变价
				boolean thisTurnneedLockOrChange = checkGameOrderIsLock2(planParam.getIssueCode(), gameOrder, userId,
						gameLocke, null, isChange, lockIssueMap);
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

	public Map<String, Integer> getInfluncePoint(String betTypeCode, String betDetail) {
		Map<String, Integer> points = new HashMap<String, Integer>();
		SingleSSQ ssq = getSinglePoint(betTypeCode, betDetail);
		List<String> reds = ssq.getRed();

		for (String red : reds) {
			ICombinatoricsVector<String> initialVector = Factory.createVector(red.split(","));
			Generator<String> gen = Factory.createSimpleCombinationGenerator(initialVector, 5);

			String strList = new String();
			for (ICombinatoricsVector<String> combination : gen) {
				List<String> list = new ArrayList(Arrays.asList(allNumber));
				list.removeAll(combination.getVector());
				ICombinatoricsVector<String> initialVector2 = Factory.createVector(list);
				Generator<String> gen2 = Factory.createSimpleCombinationGenerator(initialVector2, 1);
				//for (String l : list) {
				String key = combination.stringVal(",");//+ "," + l;
				//key = sortNumber(key);
				Integer it = points.get(key);
				if (points.get(key) == null) {
					points.put(key, Integer.valueOf(1));
				} else {
					points.put(key, it + Integer.valueOf(1));
				}

				//}
			}
		}
		for (String str : ssq.getBlue()) {
			points.put(str, 1);
		}
		return points;
	}

	private String sortNumber(String aa) {
		String[] a = aa.split(",");
		List<Long> list = new ArrayList<Long>();
		for (String t : a) {
			list.add(Long.valueOf(t));
		}
		Collections.sort(list);
		String ttt = "";
		for (Long t : list) {
			ttt += ("," + (t.longValue() < 10 ? ("0" + t.longValue()) : t.longValue()));

		}
		return ttt.substring(1);
	}

	public static final String[] allNumber = "01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33"
			.split(",");

	public static SingleSSQ getSinglePoint(String betTypeCode, String betDetail) {
		SingleSSQ ssq = new SingleSSQ();
		if ("32_71_67".equals(betTypeCode)) {
			String[] betContent = betDetail.split("\\+");
			//复式
			ICombinatoricsVector<String> initialVector = Factory.createVector(betContent[0].split(","));
			Generator<String> gen = Factory.createSimpleCombinationGenerator(initialVector, 6);
			for (ICombinatoricsVector<String> combination : gen) {
				ssq.getRed().add(combination.stringVal(","));
			}
			initialVector = Factory.createVector(betContent[0].split(","));
			ssq.getBlue().addAll(Arrays.asList(betContent[1].split(",")));

		} else if ("32_71_68".equals(betTypeCode)) {
			//单式
			String[] strs = betDetail.split(";");
			for (String str : strs) {
				String[] betContent = str.split("\\+");
				ssq.getRed().addAll(Arrays.asList(betContent[0]));
				ssq.getBlue().addAll(Arrays.asList(betContent[1]));
			}

		} else {
			//胆拖	
			String[] strs = betDetail.split("\\+");
			ssq.getBlue().addAll(Arrays.asList(strs[1]));
			String[] dts = strs[0].replace("D:", "").split("_T:");
			ICombinatoricsVector<String> initialVector = Factory.createVector(dts[1].split(","));
			Generator<String> gen = Factory.createSimpleCombinationGenerator(initialVector,
					6 - dts[0].split(",").length);
			for (ICombinatoricsVector<String> combination : gen) {
				ssq.getRed().add(dts[0] + "," + combination.stringVal(","));
			}
		}
		return ssq;
	}

	private static final class SingleSSQ {
		private List<String> red = new ArrayList<String>();
		private List<String> blue = new ArrayList<String>();

		public List<String> getRed() {
			return red;
		}

		public void setRed(List<String> red) {
			this.red = red;
		}

		public List<String> getBlue() {
			return blue;
		}

		public void setBlue(List<String> blue) {
			this.blue = blue;
		}

	}

	public static void main(String[] str) {

		Map sc = new SSQService().getInfluncePoint("32_71_69", "D:7,24,25,26_T:1,19,20,32+11");
		System.out.println(sc);
	}
}
