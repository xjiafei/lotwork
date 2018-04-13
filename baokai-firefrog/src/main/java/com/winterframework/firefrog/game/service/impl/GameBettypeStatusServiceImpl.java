package com.winterframework.firefrog.game.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.active.dao.vo.ActivityConfig;
import com.winterframework.firefrog.game.dao.IGameBettypeStatusDao;
import com.winterframework.firefrog.game.service.IGameBettypeStatusService;


@Repository("gameBettypeStatusServiceImpl")
public class GameBettypeStatusServiceImpl  implements IGameBettypeStatusService {
	private static final Logger log = LoggerFactory.getLogger(GameBettypeStatusServiceImpl.class);
	
	@Resource(name = "gameBettypeStatusDaoImpl")
	private IGameBettypeStatusDao gameBettypeStatusDao;
	
	public List<String> getSuper2000BettypecodeByLotId(){
		log.info("-----------------gameBettypeStatusServiceImpl---------------");
		List<String> bettypecodes = null; 
		List<String> fourLvlBtcs = null;
		bettypecodes = gameBettypeStatusDao.getSuper2000BetTypeCode();
		fourLvlBtcs = gameBettypeStatusDao.getSuper2000FOURLvlBtc();
		bettypecodes.addAll(fourLvlBtcs);
		return bettypecodes;
	}
	
	
	public long getSuper2000TotalBets(Long userId, List<String> betTypeCodes,ActivityConfig actCfg,List<Integer> lotsList){
		return gameBettypeStatusDao.getSuper2000TotalBets(userId,betTypeCodes,actCfg,lotsList);
	}
	
	public long getTotalBets(Long userId,ActivityConfig actCfg,List<Integer> lotsList){
		return gameBettypeStatusDao.getTotalBets(userId,actCfg,lotsList);
	}
	
	public Long getTotalBetsByInterval(Long userId, Date startTime, Date endTime, List<Integer> lotsList){
		return gameBettypeStatusDao.getTotalBetsByInterval(userId, startTime, endTime, lotsList);
	}
	
}
