package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameLockAppraiseDao;
import com.winterframework.firefrog.game.dao.IGameLockDao;
import com.winterframework.firefrog.game.dao.IGameLockParamDao;
import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGameSeriesDao;
import com.winterframework.firefrog.game.dao.IGameSlipDao;
import com.winterframework.firefrog.game.dao.vo.GameLock;
import com.winterframework.firefrog.game.dao.vo.GameLockAppraise;
import com.winterframework.firefrog.game.dao.vo.GameLockParam;
import com.winterframework.firefrog.game.entity.GameLockEntity;
import com.winterframework.firefrog.game.service.IGameLockService;
import com.winterframework.firefrog.game.util.LHCUtil;
import com.winterframework.firefrog.game.web.dto.GameLockDataQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameNumberShares;

/**
 * 封锁参数设置
 * @author floy
 * @date 2014-5-5 下午5:34:32 
 */
@Service("gameLockServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameLockServiceImpl implements IGameLockService {

	@Resource(name = "gameLockDaoImpl")
	private IGameLockDao gameLockDaoImpl;

	@Resource(name = "gameLockParamDaoImpl")
	private IGameLockParamDao gameLockParamDaoImpl;

	@Resource(name = "gameLockAppraiseDaoImpl")
	private IGameLockAppraiseDao gameLockAppraiseDaoImpl;

	@Resource(name = "gameSlipDaoImpl")
	private IGameSlipDao gameSlipDao;

	@Resource(name = "gameSeriesDaoImpl")
	private IGameSeriesDao gameSeriesDao;
	
	@Resource(name = "gameOrderDaoImpl")
	private IGameOrderDao gameOrderDao;


	@Override
	public GameLockEntity queryCurrUseGameLockEntity(Long gameId) throws Exception {
		GameLock gameLock = gameLockDaoImpl.queryGameLock(gameId);
		GameLockParam gameLockParam = gameLockParamDaoImpl.queryGameLockParam(gameId);
		GameLockAppraise gameLockAppraise = gameLockAppraiseDaoImpl.queryCurrUseGameLockAppraise(gameId);
		GameLockEntity gameLockEntity = new GameLockEntity();
		gameLockEntity.setGameId(gameId);
		gameLockEntity.setUpVal(gameLock.getUpVal() == null ? null : gameLock.getUpVal() / 10000);
		gameLockEntity.setUpVal2(gameLock.getUpVal2() == null ? null : gameLock.getUpVal2() / 10000);
		gameLockEntity.setUpVal3(gameLock.getUpVal3() == null ? null : gameLock.getUpVal3() / 10000);
		gameLockEntity.setBlueSlipVal(gameLock.getBlueSlipVal() == null ? null : gameLock.getBlueSlipVal() / 10000);
		gameLockEntity.setRedSlipVal(gameLock.getRedSlipVal() == null ? null : gameLock.getRedSlipVal() / 10000);
		if (gameLockAppraise != null) {
			gameLockEntity
					.setMinVal(gameLockAppraise.getMinVal() == null ? null : gameLockAppraise.getMinVal() / 10000);
			gameLockEntity.setTemplate(gameLockAppraise.getTemplete());
			gameLockEntity.setChangeStruc(gameLockAppraise.getChangeStruc());
		}
		if (gameLockParam != null) {
			gameLockEntity.setStartTime(gameLockParam.getStartTime());
			gameLockEntity.setEndTime(gameLockParam.getEndTime());
		}
		if(gameId == 99109l){
			GameLockParam gameLockParam2 = gameLockParamDaoImpl.queryGameLockParam(99110l);
			GameLockAppraise gameLockAppraise2 = gameLockAppraiseDaoImpl.queryCurrUseGameLockAppraise(99110l);
			if (gameLockAppraise2 != null) {
				gameLockEntity
						.setMinVal2(gameLockAppraise2.getMinVal() == null ? null : gameLockAppraise2.getMinVal() / 10000);
				gameLockEntity.setTemplate2(gameLockAppraise2.getTemplete());
				gameLockEntity.setChangeStruc2(gameLockAppraise2.getChangeStruc());
			}
			if (gameLockParam2 != null) {
				gameLockEntity.setStartTime2(gameLockParam2.getStartTime());
				gameLockEntity.setEndTime2(gameLockParam2.getEndTime());
			}
		}
		return gameLockEntity;
	}

	@Override
	public GameLock queryGameLock(Long gameId) throws Exception {
		return gameLockDaoImpl.queryGameLock(gameId);
	}

	@Override
	public int updateGameLock(GameLock gameLock) throws Exception {

		Long status = gameLock.getStatus();
		gameSeriesDao.updateGameSeriesChangeStatus(gameLock.getGameId(), 2, status == 1 ? 3 : status == 2 ? 4
				: status == 3 ? 5 : status == 4 ? 1 : 6);

		return gameLockDaoImpl.update(gameLock);
	}

	@Override
	public List<GameNumberShares> getGameNumberShares3(Long betTal, List<GameNumberShares> lockList) throws Exception {
		List<GameNumberShares> list = new ArrayList<GameNumberShares>();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				for (int k = 0; k < 10; k++) {
					GameNumberShares shares = new GameNumberShares();
					shares.setNumber("" + i + j + k);
					shares.setProfitLoss(Double.valueOf(betTal));
					list.add(shares);
				}

			}
		}
		for (GameNumberShares shares : list) {
			for (GameNumberShares lockShares : lockList) {
				if (lockShares.getNumber().length() == 2
						&& shares.getNumber().equals(("0" + lockShares.getNumber().substring(0, 2)))) {
					shares.setProfitLoss(lockShares.getProfitLoss());
				} else if (lockShares.getNumber().length() == 3
						&& shares.getNumber().equals(lockShares.getNumber().substring(0, 3))) {
					shares.setProfitLoss(lockShares.getProfitLoss());
				}

			}
		}
		return list;
	}

	@Override
	public List<GameNumberShares> getGameNumberShares2(Long betTal, List<GameNumberShares> lockList) throws Exception {
		List<GameNumberShares> list = new ArrayList<GameNumberShares>();

		for (int j = 0; j < 10; j++) {
			for (int k = 0; k < 10; k++) {
				GameNumberShares shares = new GameNumberShares();
				shares.setNumber("" + j + k);
				shares.setProfitLoss(Double.valueOf(betTal));
				list.add(shares);
			}

		}
		for (GameNumberShares shares : list) {
			for (GameNumberShares lockShares : lockList) {
				if (shares.getNumber().equals(
						lockShares.getNumber().substring(lockShares.getNumber().length() - 2,
								lockShares.getNumber().length()))) {
					shares.setProfitLoss(lockShares.getProfitLoss());
				}
			}
		}
		return list;
	}

	@Override
	public Long queryAllSlipSale(Long lotteryId, Long issueCode) throws Exception {
		Long saleY = gameSlipDao.queryAllSlipSale(lotteryId, issueCode, 1);
		Long saleJ = gameSlipDao.queryAllSlipSale(lotteryId, issueCode, 2);
		return saleY + (saleJ / 10);
	}
	
	@Override
	public Long queryAlltotamount(GameLockDataQueryRequest lockdata) throws Exception {
		Long totamount = 0l;
		totamount = gameOrderDao.getTotamount(lockdata);
		if(totamount == null){
			totamount = 0l;
		}
		return totamount;
	}
	
	@Override
	public Long queryTotalRetPoint(GameLockDataQueryRequest lockdata)
			throws Exception {
		Long retPointAmount = 0L;
		List<String> retPointList = gameLockDaoImpl.queryGameLockTotalRetPoint(lockdata);
		if(null!=retPointList){
			for(String retPoint : retPointList){ 
				String[] rp = retPoint.split(",");
				for(int i=0; i<rp.length;i++){
					retPointAmount += Long.parseLong(rp[i]);
				}
			}
		}
		return retPointAmount;
	}

	@Override
	public List<GameNumberShares> getGameNumberShareslLHC(Long profitLoss,
			List<GameNumberShares> lockList, boolean isZodiac) throws Exception {
		List<GameNumberShares> list = new ArrayList<GameNumberShares>();
		
		if(isZodiac) {
			for(LHCUtil.Zodiac _zodiac : LHCUtil.Zodiac.values()) {
				GameNumberShares shares = new GameNumberShares();
				shares.setNumber(_zodiac.getCnName());
				shares.setProfitLoss(Double.valueOf(profitLoss));
				list.add(shares);
			}
		} else {
			// 做出一個 5 * 10 陣列，[0,0] 不使用(因為沒有 0 的號碼)
			for (int j = 0; j < 5; j++) {
				for (int k = 0; k < 10; k++) {
					if(j == 0 && k == 0){
						continue;
					}
					GameNumberShares shares = new GameNumberShares();
					shares.setNumber("" + j + k);
					shares.setProfitLoss(Double.valueOf(profitLoss));
					list.add(shares);
				}
			}
		}
		
		for (GameNumberShares shares : list) {
			for (GameNumberShares lockShares : lockList) {
				if (shares.getNumber().equals(lockShares.getNumber())) {
					shares.setProfitLoss(lockShares.getProfitLoss());
				}
			}
		}
		return list;
	}
}
