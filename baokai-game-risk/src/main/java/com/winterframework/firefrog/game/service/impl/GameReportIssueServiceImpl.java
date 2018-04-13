package com.winterframework.firefrog.game.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameIssueDao;
import com.winterframework.firefrog.game.dao.IGameOrderWinDao;
import com.winterframework.firefrog.game.dao.IGameReportIssueDao;
import com.winterframework.firefrog.game.dao.IGameTransactionFundDao;
import com.winterframework.firefrog.game.dao.vo.GameReportIssue;
import com.winterframework.firefrog.game.dao.vo.GameReportCountVo;
import com.winterframework.firefrog.game.exception.GameReportException;
import com.winterframework.firefrog.game.service.IGameReportIssueService;
import com.winterframework.firefrog.game.web.dto.GameRiskReportRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

@Service("gameReportIssueServiceImpl")
@Transactional
public class GameReportIssueServiceImpl implements IGameReportIssueService {

	private static final Logger log = LoggerFactory.getLogger(GameReportIssueServiceImpl.class);
	
	@Resource(name = "gameTransactionFundDaoImpl")
	private IGameTransactionFundDao transactionDao;

	@Resource(name = "gameOrderWinDaoImpl")
	private IGameOrderWinDao gameOrderWinDaoImpl;

	@Resource(name = "gameIssueDaoImpl")
	protected IGameIssueDao gameIssueDaoImpl;

	@Resource(name = "gameReportIssueDaoImpl")
	private IGameReportIssueDao gameReportIssueDaoImpl;

	/** 
	* @Title: createGameReport 
	* @Description: 创建营收报表
	* @throws GameReportException
	*/
	@Override
	public void createGameReport(Long lotteryId, Long issueCode) throws GameReportException {

		List<GameReportCountVo> fundCount = transactionDao.getFundCountGroup(lotteryId, issueCode);
		Map<String, Long> fundMap = new HashMap<String, Long>();
		for (GameReportCountVo gameReportCountVo : fundCount) {
			fundMap.put(gameReportCountVo.getFund(), gameReportCountVo.getAmoutSum());
		}
		try {
			GameReportIssue entity = new GameReportIssue();
			entity.setLotteryId(lotteryId);
			entity.setIssueCode(issueCode);
			entity.setWebIssueCode(gameIssueDaoImpl.getGameIssueByIssueCode(issueCode).getWebIssueCode());
			entity.setReportDate(new Date());
			entity.setUpdateTime(new Date());
			entity.setTotalSales(getMapNum("GM-DVCB-2", fundMap) + getMapNum("GM-DVNP-1", fundMap) - getMapNum("GM-RVCP-1", fundMap));
			entity.setTotalCancelOrder(getMapNum("GM-RVCP-1", fundMap) + getMapNum("GM-CCBX-1", fundMap));
			entity.setTotalCancel(getMapNum("GM-CFXX-1", fundMap));
			entity.setTotalActualAward(getMapNum("GM-PDXX-1", fundMap) + getMapNum("GM-CPDX-1", fundMap));
			entity.setTotalWins(pasreLongNum(gameOrderWinDaoImpl.getWinCounts(lotteryId, issueCode)));
			entity.setTotalPoints(getMapNum("GM-RTRX-1", fundMap) + getMapNum("GM-RTRX-2", fundMap) - getMapNum("GM-CRRX-1", fundMap)
					+ getMapNum("GM-CRRX-2", fundMap));
			entity.setTotalProfit(entity.getTotalSales() + entity.getTotalCancel() - entity.getTotalActualAward()
					- entity.getTotalPoints());
			saveOrUpdate(entity);
		} catch (Exception e) {
			log.error("创建营收报表异常", e);
			throw new GameReportException("创建营收报表异常");
		}
	}

	public void saveOrUpdate(GameReportIssue entity) {
		GameReportIssue report = gameReportIssueDaoImpl
				.getGameReportIssue(entity.getLotteryId(), entity.getIssueCode());
		if (report == null) {
			gameReportIssueDaoImpl.insert(entity);
		} else {
			entity.setId(report.getId());
			gameReportIssueDaoImpl.update(entity);
		}
	}

	private Long getMapNum(String type, Map<String, Long> fundMap){
		Long value = fundMap.get(type);
		return pasreLongNum(value);
	}
	
	private Long pasreLongNum(Long num){	
		return num == null ? 0L : num;
	}
	
	/** 
	* @Title: queryRiskGameIssues 
	* @Description: 查询报表list 
	* @param pr
	* @return
	*/
	public Page<GameReportIssue> queryGameReportIssues(PageRequest<GameRiskReportRequest> pr) {
		return gameReportIssueDaoImpl.queryGameReportIssues(pr);
	}

}
