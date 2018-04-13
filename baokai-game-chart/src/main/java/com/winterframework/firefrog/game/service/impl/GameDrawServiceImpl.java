package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameDrawResultDao;
import com.winterframework.firefrog.game.dao.IGameTrendJbylDao;
import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.service.IGameDrawService;

/** 
* @ClassName: GameDrawServiceImpl 
* @Description: 历史开奖号码相关统计Service 
* @author Denny 
* @date 2013-7-22 下午5:22:48 
*  
*/
@Service("gameDrawServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameDrawServiceImpl implements IGameDrawService {

	@Resource(name = "gameDrawResultDaoImpl")
	private IGameDrawResultDao gameDrawResultDao;

	@Resource(name = "gameTrendJbylDaoImpl")
	private IGameTrendJbylDao gameTrendJbylDaoImpl;

	@Override
	public List<GameDrawResult> getAllByLotteryIdAndCountIssue(Long lotteryId, Integer count) throws Exception {
		return gameDrawResultDao.getAllByLotteryIdAndCountIssue(lotteryId, count);
	}

	@Override
	public List<GameDrawResult> getDrawResultByDate(Long lotteryId, Date startDate, Date endDate) throws Exception {
		return gameDrawResultDao.getDrawResultByDate(lotteryId, startDate, endDate);
	}

	@Override
	@Deprecated
	public List<GameDrawResult> getTrendResultByLotteryIdAndCountIssue(Long lotteryId, Integer count) throws Exception {
		List<GameTrendJbyl> list = gameTrendJbylDaoImpl.getTrendResultByLotteryIdAndCountIssue(lotteryId, count);
		List<GameDrawResult> result = new ArrayList<GameDrawResult>();
		for (GameTrendJbyl gameTrendJbyl : list) {
			GameDrawResult gameDrawResult = new GameDrawResult();
			gameDrawResult.setLotteryid(gameTrendJbyl.getLotteryid());
			gameDrawResult.setIssueCode(gameTrendJbyl.getIssueCode());
			gameDrawResult.setWebIssueCode(gameTrendJbyl.getWebIssueCode());
			gameDrawResult.setNumberRecord(gameTrendJbyl.getValue());
			result.add(gameDrawResult);
		}
		return result;
	}

	@Override
	@Deprecated
	public List<GameDrawResult> getTrendResultByDate(Long lotteryId, Date startDate, Date endDate) throws Exception {
		List<GameTrendJbyl> list = gameTrendJbylDaoImpl.getTrendResultByDate(lotteryId, startDate, endDate);
		List<GameDrawResult> result = new ArrayList<GameDrawResult>();
		for (GameTrendJbyl gameTrendJbyl : list) {
			GameDrawResult gameDrawResult = new GameDrawResult();
			gameDrawResult.setLotteryid(gameTrendJbyl.getLotteryid());
			gameDrawResult.setIssueCode(gameTrendJbyl.getIssueCode());
			gameDrawResult.setWebIssueCode(gameTrendJbyl.getWebIssueCode());
			gameDrawResult.setNumberRecord(gameTrendJbyl.getValue());
			result.add(gameDrawResult);
		}
		return result;
	}
}
