package com.winterframework.firefrog.game.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameIssueDao;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.service.IGameRevocationPlanService;
import com.winterframework.firefrog.game.service.revocation.IGameRevocationIssueService;

/** 
* @ClassName: GameRevocationPlanServiceImpl 
* @Description: 撤销后续追号（起始期号-结束期号）
* @author Alan
* @date 2013-11-19 下午3:58:06 
*  
*/
@Service("gameRevocationPlanServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameRevocationPlanServiceImpl implements IGameRevocationPlanService {

	private static final Logger log = LoggerFactory.getLogger(GameRevocationPlanServiceImpl.class);

	@Resource(name = "gameIssueDaoImpl")
	private IGameIssueDao gameIssueDao;
	
	@Resource(name = "gameRevocationIssueSimplePlanServiceImpl")
	private IGameRevocationIssueService gameRevocationIssueSimplePlanServiceImpl;

	/**
	* Title: revocationFollowPlan
	* Description: 撤销后续追号
	* @param lotteryid
	* @param issueCode
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameRevocationPlanService#revocationFollowPlan(java.lang.Long, java.lang.Long) 
	*/
	@Override
	public void revocationFollowPlan(GameControlEvent event) throws Exception {
		
		Long startIssueCode = event.getStartIssueCode();
		Long endIssueCode = event.getEndIssueCode();
		
		Long lotteryId = event.getLotteryid();
		
		log.info("撤销后续追号信息，lotteryId="+ lotteryId+ ",startIssueCode="+ startIssueCode + ", endIssue="+ endIssueCode);
		
		List<GameIssueEntity> gameIssueList = gameIssueDao.queryGameIssuesByIssueCodes(lotteryId, startIssueCode, endIssueCode);
		
		for(GameIssueEntity entity : gameIssueList){
			gameRevocationIssueSimplePlanServiceImpl.doRevocation(lotteryId, entity.getIssueCode());
		}
		
	}
}
