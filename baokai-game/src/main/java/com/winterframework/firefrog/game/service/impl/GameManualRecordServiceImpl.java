package com.winterframework.firefrog.game.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameIssueDao;
import com.winterframework.firefrog.game.dao.IGameManualRecordDao;
import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GameManualRecord;
import com.winterframework.firefrog.game.dao.vo.GameWarnEvent;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssueLog;
import com.winterframework.firefrog.game.entity.GameIssueStatus;
import com.winterframework.firefrog.game.exception.GameManualRecordException;
import com.winterframework.firefrog.game.service.IGameDrawService;
import com.winterframework.firefrog.game.service.IGameManualRecordService;
import com.winterframework.firefrog.game.web.controller.utlis.GameAbnormalOperationUtils;
import com.winterframework.firefrog.game.web.dto.GameManualRecordEncodingRequest;
import com.winterframework.firefrog.game.web.dto.GameManualRecordPageResponse;
import com.winterframework.firefrog.game.web.dto.GameManualRecordRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName GameManualRecordServiceImpl 
* @Description 手工录号 
* @author  hugh
* @date 2014年9月16日 下午2:03:45 
*  
*/
@Service("gameManualRecordServiceImpl")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class GameManualRecordServiceImpl implements IGameManualRecordService{

	private Logger log = LoggerFactory.getLogger(GameManualRecordServiceImpl.class);
	
	@Resource(name = "gameIssueDaoImpl")
	private IGameIssueDao gameIssueDaoImpl;
	
	@Resource(name = "gameManualRecordDaoImpl")
	private IGameManualRecordDao gameManualRecordDaoImpl;
	
	@Resource(name = "gameDrawServiceImpl")
	private IGameDrawService gameDrawServiceImpl;
	
	/**
	* @Title: getGameManualRecordsByLottery
	* @Description:
	* @param pr
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameManualRecordService#getGameManualRecordsByLottery(com.winterframework.modules.page.PageRequest) 
	*/
	@Override
	public GameManualRecordPageResponse getGameManualRecordsByLottery(PageRequest<GameManualRecordRequest> pr) throws Exception {
		GameManualRecordPageResponse response = new GameManualRecordPageResponse();
		GameIssue issue = gameIssueDaoImpl.getMinIssueNotReciveResultCode(pr.getSearchDo().getLotteryId(),pr.getSearchDo().getUserId());
		GameIssue issue1 = gameIssueDaoImpl.getMaxIssueNotReciveResultCode(pr.getSearchDo().getLotteryId(),pr.getSearchDo().getUserId());
		Page<GameManualRecord> gameManualRecords = gameManualRecordDaoImpl.getGameManualRecordsByLottery(pr);
		response.setGameIssue(issue);
		response.setLastGameIssue(issue1);
		response.setGameManualRecords(gameManualRecords);
		return response;
	}

	/**
	* @Title: encodingGameManualRecordsByIssue
	* @Description:
	* @param request 
	 * @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameManualRecordService#encodingGameManualRecordsByIssue(com.winterframework.firefrog.game.web.dto.GameManualRecordEncodingRequest) 
	*/
	@Override
	public void encodingGameManualRecordsByIssue(GameManualRecordEncodingRequest request) throws Exception {
		GameManualRecord reord = gameManualRecordDaoImpl.getGameManualRecordByIssue(request.getIssueCode());		
		Date now = new Date();
		if(reord == null || reord.getId()==null){
			reord = new GameManualRecord();				
			reord.setIssueCode(request.getLotteryId());	
			reord.setCreator(request.getUserId());
			reord.setGmtCreated(now);
			reord.setGmtModified(now);
			reord.setFirstUserId(request.getUserId());
			reord.setFirstResuld(request.getResult());
			reord.setFirstEncodingTime(now);
			reord.setStatus(0);
			GameIssue issue = null;
			try {
				issue = gameIssueDaoImpl.getGameIssueByIssueCode(request.getIssueCode());
			} catch (Exception e) {
				log.error("查询奖期失败！", e);
			}	
				if (!GameAbnormalOperationUtils.isRightResultNumber(issue.getLotteryid(), request.getResult())) {
					throw new GameManualRecordException("2");
				}
				
				reord.setLotteryId(issue.getLotteryid());
				reord.setSaleEndTime(issue.getSaleEndTime());
				reord.setWebIssueCode(issue.getWebIssueCode());
				reord.setIssueCode(issue.getIssueCode());
						
			gameManualRecordDaoImpl.insert(reord);
		}else if(reord.getSencondUserId() == null){
			reord.setGmtModified(now);
			reord.setSencondUserId(request.getUserId());
			reord.setSencondResuld(request.getResult());
			reord.setSencondEncodingTime(now);
			
			if (!GameAbnormalOperationUtils.isRightResultNumber(reord.getLotteryId(), request.getResult())) {
				throw new GameManualRecordException("2");
			}
			if(StringUtils.equals(request.getResult(), reord.getFirstResuld())){
				reord.setConfirmResuld(request.getResult());
				try{
					inputResultCode(reord.getIssueCode(),reord.getLotteryId(), request.getResult());
					reord.setStatus(0);
				}catch(Exception ee){
					log.error("",ee);
					reord.setStatus(0);
					throw ee;
				}				
			}else{
				reord.setStatus(1);
			}
			gameManualRecordDaoImpl.update(reord);
		}else{
			if (!GameAbnormalOperationUtils.isRightResultNumber(reord.getLotteryId(), request.getResult())) {
				throw new GameManualRecordException("2");
			}
			reord.setGmtModified(now);
			//reord.setDealUserId(request.getUserId());			
			reord.setConfirmResuld(request.getResult());
			reord.setStatus(0);
			inputResultCode(reord.getIssueCode(),reord.getLotteryId(), request.getResult());
			gameManualRecordDaoImpl.update(reord);
		}
		
	}

	
	private void inputResultCode(Long issueCode,Long lotteryid,  String resultCode) throws Exception{
		GameDrawResult gameDrawResult = gameDrawServiceImpl.getDrawResuleByLotteryIdAndIssueCode(lotteryid,
				issueCode);

		GameIssue issue  = gameIssueDaoImpl.getGameIssueByIssueCode(issueCode);

		if (gameDrawResult != null) {
			log.info("已经存在开奖号码");
			throw new Exception("已经存在开奖号码");
		} else {
			if (issue.getStatus().intValue() != GameIssueStatus.SALE_END.getValue()) {
				log.info("奖期状态不是结束销售状态，不能输入开奖号码");
				throw new Exception("奖期状态不是结束销售状态，不能输入开奖号码");
			} else {				
				GameWarnIssueLog warnIssueLog = new GameWarnIssueLog();
				warnIssueLog.setDisposeInfo("手工录号输入开奖号码：" + resultCode);
				warnIssueLog.setIssueCode(issue.getIssueCode());
				warnIssueLog.setLotteryid(lotteryid);
				warnIssueLog.setWebIssueCode(issue.getWebIssueCode());
				warnIssueLog.setEvent(GameWarnEvent.MANUAL_I_AWARD.getCode());
				warnIssueLog.setCreateTime(new Date());
				warnIssueLog.setDisposeMemo("手工录号");
				warnIssueLog.setDisposeUser("手工录号");
				//1、添加开奖结果记录
				gameDrawServiceImpl.inputNumberDrawResult(lotteryid, issueCode, resultCode, warnIssueLog,null);
			}
		}
	}
}
