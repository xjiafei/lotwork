package com.winterframework.firefrog.game.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.game.dao.IGameDrawResultDao;
import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameLock;
import com.winterframework.firefrog.game.enums.GameLockPlayType;
import com.winterframework.firefrog.game.lock.config.mongo.service.LockIssue;
import com.winterframework.firefrog.game.lock.config.mongo.service.LockService;
import com.winterframework.firefrog.game.lock.config.mongo.service.LockIssue.PontStatus;
import com.winterframework.firefrog.game.service.IGameLockService;
import com.winterframework.firefrog.game.web.dto.GameLockDataQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameLockDataStruc;
import com.winterframework.firefrog.game.web.dto.GameLockRequest;
import com.winterframework.firefrog.game.web.dto.GameLockStruc;
import com.winterframework.firefrog.game.web.dto.GameNumberShares;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/**
 * 封锁Controller 
 * @author floy
 */
@Controller("gameLockController")
@RequestMapping("/game")
public class GameLockController {

	private Logger log = LoggerFactory.getLogger(GameLockController.class);

	@Resource(name = "gameLockServiceImpl")
	private IGameLockService gameLockService;

	@Resource(name = "lockService")
	private LockService lockService;

	@Resource(name = "gameDrawResultDaoImpl")
	private IGameDrawResultDao drawResultDao;

	/** 
	* @Title: queryGameConfig 
	* @Description: 查询封锁配置
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryGameConfig")
	@ResponseBody
	public Response<GameLockStruc> queryGameConfig(@RequestBody Request<GameLockRequest> request) throws Exception {
		log.info("queryGameConfig start");
		Response<GameLockStruc> response = new Response<GameLockStruc>(request);
		try {
			GameLock gameLock = gameLockService.queryGameLock(request.getBody().getParam().getGameId());
			GameLockStruc gameLockStruc = new GameLockStruc();
			gameLockStruc.setGameId(gameLock.getGameId());
			gameLockStruc.setId(gameLock.getId());
			gameLockStruc.setStatus(gameLock.getStatus());
			gameLockStruc.setUpVal(gameLock.getUpVal());
			gameLockStruc.setUpValProcess(gameLock.getUpValProcess());
			gameLockStruc.setUpVal2(gameLock.getUpVal2());
			gameLockStruc.setUpValProcess2(gameLock.getUpValProcess2());
			gameLockStruc.setBlueSlipVal(gameLock.getBlueSlipVal());
			gameLockStruc.setBlueSlipValProcess(gameLock.getBlueSlipValProcess());
			gameLockStruc.setRedSlipVal(gameLock.getRedSlipVal());
			gameLockStruc.setRedSlipValProcess(gameLock.getRedSlipValProcess());
			gameLockStruc.setUpVal3(gameLock.getUpVal3());
			gameLockStruc.setUpValProcess3(gameLock.getUpValProcess3());
			response.setResult(gameLockStruc);
		} catch (Exception e) {
			log.error("queryGameConfig error ", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	 * @param list
	 * @param resultNumber 當期开奖号码
	 * @return
	 * @throws Exception
	 */
	private Double getTheoryProfitValue(List<GameNumberShares> list, String resultNumber) throws Exception {
		Double value = 0d;
		for (GameNumberShares gameNumberShares : list) {
			if (gameNumberShares.getNumber().equals(resultNumber)) {
				value = gameNumberShares.getProfitLoss();
				break;
			}
		}
		return value;
	}

	/**
	 * 取得封鎖數據。
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getGameLockData")
	@ResponseBody
	public Response<GameLockDataStruc> getGameLockData(@RequestBody Request<GameLockDataQueryRequest> request)
			throws Exception {
		log.info("query gameLock data start");
		Response<GameLockDataStruc> response = new Response<GameLockDataStruc>(request);
		try {
			GameLockDataQueryRequest req = request.getBody().getParam();
			List<GameNumberShares> lockSharesList = new ArrayList<GameNumberShares>();
			GameLockDataStruc struc = new GameLockDataStruc();
			GameDrawResult gameDrawResult = drawResultDao.getDrawResuleByLotteryIdAndIssueCode(req.getLotteryId(),
					req.getIssueCode());
			// 排列5
			if (req.getLotteryId() == 99109) {
				Long betTotal = 0L;
				LockIssue lockIssue = this.getLockDate(99109L, Long.valueOf(req.getIssueCode()), null,
						lockSharesList);
				if (lockIssue != null) {
					betTotal = lockIssue.getBetTotal();
				}
				List<GameNumberShares> result = gameLockService.getGameNumberShares3(betTotal, lockSharesList);
				if (gameDrawResult != null) {
					struc.setCurrentGameResult(gameDrawResult.getNumberRecord().substring(0, 3));
					struc.setCurrentGameResult2(gameDrawResult.getNumberRecord().substring(3, 5));
				}
				struc.setTheoryProfitValue(getTheoryProfitValue(result, struc.getCurrentGameResult()));
				struc.setGameSharesStruc(sortGameNumberSharesList(req.getSortType(), result));
				struc.setTotalSaleValue(Double.valueOf(betTotal)/ 10000);
				lockSharesList = new ArrayList<GameNumberShares>();
				lockIssue = this
						.getLockDate(99110L, Long.valueOf(req.getIssueCode()), "p5", lockSharesList);
				if (lockIssue != null) {
					if(lockIssue.getBetTotal() != null){
					betTotal = lockIssue.getBetTotal();
					}
				} else {
					betTotal = 0L;
				}
				result = gameLockService.getGameNumberShares2(betTotal, lockSharesList);
				struc.setTheoryProfitValue2(getTheoryProfitValue(result, struc.getCurrentGameResult2()));
				struc.setGameSharesStruc2(sortGameNumberSharesList(req.getSortType(), result));

				struc.setTotalSaleValue2(Double.valueOf(betTotal) / 10000);

				response.setResult(struc);
			} else if (req.getLotteryId() == 99401) {	// 雙色球
				this.getLockDate(req.getLotteryId(), Long.valueOf(req.getIssueCode()), null, lockSharesList);
				List<List<GameNumberShares>> pList = new ArrayList<List<GameNumberShares>>();
				
				if (!lockSharesList.isEmpty()) {
					List<GameNumberShares> subList = new ArrayList<GameNumberShares>();
					
					List<GameNumberShares> subList2 = new ArrayList<GameNumberShares>();
					for (int i = 0; i < lockSharesList.size(); i++) {
						GameNumberShares shares = lockSharesList.get(i);
						if (shares.getNumber().length() > 10) {
							subList.add(shares);
						} else {
							//subList.add(j,shares);
							subList2.add(shares);
						}
					}
					
					
					for (int i = 0; i < subList.size(); i++) {
						List<GameNumberShares> ssubList = new ArrayList<GameNumberShares>();
						GameNumberShares shares = subList.get(i);
						ssubList.add(shares);
						if (subList2.size() > i) {
							ssubList.add(subList2.get(i));
						} 
						pList.add(ssubList);

					}
					struc.setGameSharesStruc(pList);
					response.setResult(struc);
				}
			}else if (req.getLotteryId() == 99701) {	// 六合彩
				// 当前的投注金额
				Long _betTotal = 0L;
				// 當前玩法總返點金額
				Long _retValue = 0L;
				LockIssue _lockIssue = this.getLockDate(req.getLotteryId(), Long.valueOf(req.getIssueCode()), GameLockPlayType.getName(req.getPlayType().intValue()), lockSharesList);
				boolean _isZodiac = false;
				if (_lockIssue != null) {
					_betTotal = _lockIssue.getBetTotal();
					_retValue = _lockIssue.getRetPoint();
				}
				if (gameDrawResult != null) {
					struc.setCurrentGameResult(gameDrawResult.getNumberRecord());
				}
				if(req.getPlayType() == 1) {
					_isZodiac = true;
				}
				List<GameNumberShares> result = gameLockService.getGameNumberShareslLHC(_betTotal - _retValue, lockSharesList, _isZodiac);
				// 六合彩開獎號碼為多碼組合，故無法比對出 theoryProfitValue
				struc.setTheoryProfitValue(getTheoryProfitValue(result, struc.getCurrentGameResult()));
				struc.setGameSharesStruc(sortGameNumberSharesList(req.getSortType(), result));
				struc.setTotalSaleValue(Double.valueOf(_betTotal)/ 10000);
				struc.setTotalSaleValue2(0d);
				response.setResult(struc);
			} else {
				Long betTotal = 0L;
				LockIssue lockIssue = this.getLockDate(req.getLotteryId(), Long.valueOf(req.getIssueCode()), null,
						lockSharesList);
				if (lockIssue != null) {
					betTotal = lockIssue.getBetTotal();
				}
				if (gameDrawResult != null) {
					struc.setCurrentGameResult(gameDrawResult.getNumberRecord().substring(0, 3));
				}
				List<GameNumberShares> result = gameLockService.getGameNumberShares3(betTotal, lockSharesList);
				struc.setTheoryProfitValue(getTheoryProfitValue(result, struc.getCurrentGameResult()));
				struc.setGameSharesStruc(sortGameNumberSharesList(req.getSortType(), result));
				struc.setTotalSaleValue(Double.valueOf(betTotal)/ 10000);
				struc.setTotalSaleValue2(0d);
				response.setResult(struc);
			}
		} catch (Exception e) {
			log.error("getGameLockData error", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	 * @param lotteryId 彩種ID
	 * @param issueCode 當期獎期號碼
	 * @param key 組 redis key 用
	 * @param resultList
	 * @return
	 * @throws Exception
	 */
	private LockIssue getLockDate(Long lotteryId, Long issueCode, String key, List<GameNumberShares> resultList)
			throws Exception {
		LockIssue lockIssue = lockService.getLockIssueByLotteryIdAndIssueCode(lotteryId, issueCode, key);

		if (lockIssue != null) {
			Map<String, PontStatus> map = lockIssue.getPoints();
			Set<Entry<String, PontStatus>> entrySet = map.entrySet();

			for (Entry<String, PontStatus> entry : entrySet) {
				GameNumberShares gameNumberShares = new GameNumberShares();
				gameNumberShares.setNumber(entry.getKey());
				gameNumberShares.setProfitLoss(Double.valueOf(lockIssue.getBetTotal() - lockIssue.getRetPoint()
						+ entry.getValue().getTotal()));
				gameNumberShares.setSlipVal(Long.valueOf(entry.getValue().getBaishu()));
				resultList.add(gameNumberShares);
			}
			
			// 非排列3、六合彩時
			if(lotteryId != 99110 && lotteryId != 99701){
				GameLockDataQueryRequest lockdata = new GameLockDataQueryRequest();
				lockdata.setLotteryId(lotteryId);
				lockdata.setIssueCode(issueCode);
				Long Total = gameLockService.queryAlltotamount(lockdata);
				Long totalRetPoint = gameLockService.queryTotalRetPoint(lockdata);
				lockIssue.setBetTotal(Total-totalRetPoint);
			}
		}

		return lockIssue;
	}

	/**
	 * 若 sortType = 1 先將 sharesList 做 profitLoss 升冪排序，然後再將 sharesList 內每 10 個項目為一組拆出來(對應頁面每一列個數)。
	 * @param sortType 升幂排序依据方式；0:號碼、1:盈虧值、2:生肖
	 * @param sharesList
	 * @return
	 * @throws Exception
	 */
	private List<List<GameNumberShares>> sortGameNumberSharesList(Long sortType, List<GameNumberShares> sharesList)
			throws Exception {
		List<List<GameNumberShares>> result = new ArrayList<List<GameNumberShares>>();
		List<GameNumberShares> subList = null;
		if (sortType.intValue() == 1) {
			Collections.sort(sharesList, new Comparator<GameNumberShares>() {
				@Override
				public int compare(GameNumberShares o1, GameNumberShares o2) {
					if(o1.getProfitLoss() == o2.getProfitLoss()) {
						return 0;
					} else {
						return o1.getProfitLoss() > o2.getProfitLoss() ? 1 : -1;
					}
				}
			});
		} else if (sortType.intValue() == 2) {
			Collections.sort(sharesList, new Comparator<GameNumberShares>() {
				@Override
				public int compare(GameNumberShares o1, GameNumberShares o2) {
					if(o1.getZodiacIndex() == o2.getZodiacIndex()) {
						return 0;
					} else {
						return o1.getZodiacIndex() > o2.getZodiacIndex() ? 1 : -1;
					}
				}
			});
		}
		for (int i = 0; i < sharesList.size(); i++) {
			if (i % 10 == 0) {
				subList = new ArrayList<GameNumberShares>();
				result.add(subList);
			}
			subList.add(sharesList.get(i));
		}
		return result;
	}

	/**
	 * 修改配置
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateGameConfig")
	@ResponseBody
	public Response<Object> updateGameConfig(@RequestBody Request<GameLockRequest> request) throws Exception {
		log.info("updateGameConfig start");
		Response<Object> response = new Response<Object>(request);
		GameLockRequest gameLockRequest = request.getBody().getParam();
		try {
			if (gameLockRequest.getStatus() != 5l && gameLockRequest.getStatus() != 6l) {
				GameLock gameLock = new GameLock();
				gameLock.setId(gameLockRequest.getId());
				gameLock.setGameId(gameLockRequest.getGameId());
				gameLock.setStatus(gameLockRequest.getStatus());
				gameLock.setUpVal(gameLockRequest.getUpVal());
				gameLock.setUpValProcess(gameLockRequest.getUpValProcess());
				gameLock.setUpVal2(gameLockRequest.getUpVal2());
				gameLock.setUpValProcess2(gameLockRequest.getUpValProcess2());
				gameLock.setRedSlipVal(gameLockRequest.getRedSlipVal());
				gameLock.setRedSlipValProcess(gameLockRequest.getRedSlipValProcess());
				gameLock.setBlueSlipVal(gameLockRequest.getBlueSlipVal());
				gameLock.setBlueSlipValProcess(gameLockRequest.getBlueSlipValProcess());
				gameLock.setUpVal3(gameLockRequest.getUpVal3());
				gameLock.setUpValProcess3(gameLockRequest.getUpValProcess3());
				gameLockService.updateGameLock(gameLock);
			} else {
				cancelModify(gameLockRequest);
			}
		} catch (Exception e) {
			log.error("updateGameConfig error ", e);
			throw e;
		}
		return response;
	}

	private void cancelModify(GameLockRequest gameLockRequest) throws Exception {
		GameLock gameLock = gameLockService.queryGameLock(gameLockRequest.getGameId());
		gameLock.setUpValProcess(gameLock.getUpVal());
		gameLock.setUpValProcess2(gameLock.getUpVal2());
		gameLock.setUpValProcess3(gameLock.getUpVal3());
		gameLock.setBlueSlipValProcess(gameLock.getBlueSlipVal());
		gameLock.setRedSlipValProcess(gameLock.getRedSlipVal());
		gameLock.setStatus(4l);
		gameLockService.updateGameLock(gameLock);
	}
}
