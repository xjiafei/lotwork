package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.IGameControlEventDao;
import com.winterframework.firefrog.game.dao.IGameDrawResultDao;
import com.winterframework.firefrog.game.dao.IGameHelpDao;
import com.winterframework.firefrog.game.dao.IGameIssueDao;
import com.winterframework.firefrog.game.dao.IGameWarnIssueDao;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameHelp;
import com.winterframework.firefrog.game.dao.vo.GameWarnEvent;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssue;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssueLog;
import com.winterframework.firefrog.game.entity.GameHelpEntity;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameIssuePeriodStatus;
import com.winterframework.firefrog.game.entity.GameIssueStatus;
import com.winterframework.firefrog.game.entity.VOConvert;
import com.winterframework.firefrog.game.service.IGameDrawService;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGameWarnIssueLogService;

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

	private Logger log = LoggerFactory.getLogger(GameDrawServiceImpl.class);
	
	@Resource(name = "gameDrawResultDaoImpl")
	private IGameDrawResultDao gameDrawResultDao;

	@Resource(name = "gameHelpDaoImpl")
	private IGameHelpDao gameHelpDao;

	@Resource(name = "gameIssueDaoImpl")
	private IGameIssueDao gameIssueDao;

	@Resource(name = "gameControlEventDaoImpl")
	private IGameControlEventDao gameControlEventDao;

	@Resource(name = "gameWarnIssueDaoImpl")
	private IGameWarnIssueDao gameWarnIssueDao;

	@Resource(name = "gameWarnIssueLogServiceImpl")
	private IGameWarnIssueLogService gameWarnIssueLogServiceImpl;
	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueServiceImpl;

	@Override
	public GameDrawResult getDrawResuleByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) {
		return gameDrawResultDao.getDrawResuleByLotteryIdAndIssueCode(lotteryId, issueCode);
	}

	@Override
	public List<GameHelpEntity> queryGameHelpByLotteryId(Long lotteryId) throws Exception {

		List<GameHelp> list = gameHelpDao.getGameHelpByLotteryId(lotteryId);
		List<GameHelpEntity> entityList = new ArrayList<GameHelpEntity>();
		for (GameHelp help : list) {

			entityList.add(VOConvert.convertGameHelp2Entity(help));
		}
		return entityList;
	}

	@Override
	public List<GameDrawResult> getAllByLotteryIdAndCountIssue(Long lotteryId, Integer count) throws Exception {
		return gameDrawResultDao.getAllByLotteryIdAndCountIssue(lotteryId, count);
	}

	@Override
	public List<GameDrawResult> getDrawResultByDate(Long lotteryId, Date startDate, Date endDate) throws Exception {
		return gameDrawResultDao.getDrawResultByDate(lotteryId, startDate, endDate);
	}

	/**
	* @Title: addDrawResult
	* @Description:正常开奖
	* @param lotteryid
	* @param issueCode
	* @param numberRecord
	* @param warnIssueLog
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameDrawService#addDrawResult(java.lang.Long, java.lang.Long, java.lang.String, com.winterframework.firefrog.game.dao.vo.GameWarnIssueLog) 
	*/
	@Override
	public void addDrawResult(Long lotteryid, Long issueCode, String numberRecord, GameWarnIssueLog warnIssueLog,Date ecVerifiedTime, String memo)
			throws Exception {
		GameIssueEntity issue = gameIssueDao.queryGameIssue(lotteryid, issueCode);
		Date now = new Date();
		//1、添加奖期结果记录
		GameDrawResult drawResult = new GameDrawResult();
		drawResult.setLotteryid(lotteryid);
		drawResult.setIssueCode(issueCode);
		drawResult.setWebIssueCode(issue.getWebIssueCode());
		drawResult.setNumberRecord(numberRecord);
		drawResult.setCreateTime(now);
		drawResult.setOpenDrawTime(now);
		drawResult.setType(0L);
		drawResult.setMemo(memo);
		gameDrawResultDao.insert(drawResult);

		//2、添加"待计算奖金"事件
		GameControlEvent event = new GameControlEvent();
		event.setLotteryid(lotteryid);
		event.setStartIssueCode(issueCode);
		event.setEndIssueCode(issueCode);
		event.setSaleStartTime(issue.getSaleStartTime());
		event.setSaleEndTime(issue.getSaleEndTime());
		event.setStatus(0L);
		event.setCreateTime(now);
		event.setEnentType(2L);
		if (warnIssueLog != null) {
			event.setParams(warnIssueLog.getDisposeUser());
		}
		gameControlEventDao.addControlEvent(event);

		//更新奖期状态"开奖号码已确认"
		issue.setNumberRecord(numberRecord);
		issue.setNumberUpdateTime(DateUtils.currentDate());
		issue.setNumberUpdateCount((issue.getNumberUpdateCount()==null?0:issue.getNumberUpdateCount())+1);
		issue.setStatus(GameIssueStatus.ACK_DRAW_RESULT);
		issue.setPeriodStatus(GameIssuePeriodStatus.WAIT_DRAW_RESULT);
		issue.setRecivceDrawTime(now);
		//issue.setFactionDrawTime(now);
		issue.setEcVerifiedTime(ecVerifiedTime);
		issue.setUpdateTime(now);
		gameIssueServiceImpl.updateGameIssue(issue);
		
		if(lotteryid==99501L){
			try{
				Long otherLottreyId=99601L;
				GameIssueEntity gameIssue = gameIssueDao.queryGameIssue(otherLottreyId, issue.getWebIssueCode());
				if(gameIssue!=null){
					if(null!=warnIssueLog){
						warnIssueLog.setLotteryid(otherLottreyId);
						warnIssueLog.setIssueCode(gameIssue.getIssueCode());
					}
					addDrawResult(otherLottreyId, gameIssue.getIssueCode(), numberRecord, warnIssueLog, ecVerifiedTime, memo);
				}
			}catch(Exception e){
				log.error("exception occurs when query dice issue.",e);
			}
		}
	}

	/**
	* @Title: inputNumberDrawResult
	* @Description:手动输入开奖号码
	* @param lotteryid
	* @param issueCode
	* @param numberRecord
	* @param warnIssueLog
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameDrawService#inputNumberDrawResult(java.lang.Long, java.lang.Long, java.lang.String, com.winterframework.firefrog.game.dao.vo.GameWarnIssueLog) 
	*/
	@Override
	public void inputNumberDrawResult(Long lotteryid, Long issueCode, String numberRecord, GameWarnIssueLog warnIssueLog,Date ecVerifiedTime)
			throws Exception {
		GameIssueEntity issue = gameIssueDao.queryGameIssue(lotteryid, issueCode);

		//更新奖期状态"开奖号码已确认"
		issue.setNumberRecord(numberRecord);
		issue.setNumberUpdateTime(DateUtils.currentDate());
		issue.setNumberUpdateCount((issue.getNumberUpdateCount()==null?0:issue.getNumberUpdateCount())+1);
		issue.setStatus(GameIssueStatus.ACK_DRAW_RESULT);
		issue.setPeriodStatus(GameIssuePeriodStatus.WAIT_DRAW_RESULT);
		//issue.setFactionDrawTime(new Date());
		issue.setRecivceDrawTime(new Date());
		issue.setUpdateTime(new Date());
		gameIssueServiceImpl.updateGameIssue(issue);

		//1、添加奖期结果记录
		GameDrawResult gameDrawResult = getDrawResuleByLotteryIdAndIssueCode(lotteryid, issueCode);
		GameDrawResult drawResult = new GameDrawResult();
		drawResult.setLotteryid(lotteryid);
		drawResult.setIssueCode(issueCode);
		drawResult.setWebIssueCode(issue.getWebIssueCode());
		drawResult.setNumberRecord(numberRecord);
		drawResult.setCreateTime(new Date());
		drawResult.setOpenDrawTime(issue.getOpenDrawTime());
		drawResult.setType(1L);
		drawResult.setOpenDrawTime(new Date());
		if (gameDrawResult == null) {
			gameDrawResultDao.insert(drawResult);
		} else {
			drawResult.setId(gameDrawResult.getId());
			gameDrawResultDao.update(drawResult);
		}

		GameWarnIssue warn = new GameWarnIssue();
		if (warnIssueLog != null) {
			//添加奖期警告表			
			warn.setIssueCode(issueCode);
			warn.setLotteryid(lotteryid);
			warn.setWebIssueCode(gameIssueDao.getGameIssueByIssueCode(issueCode).getWebIssueCode());
			warn.setIssueWarnId(warnIssueLog.getEvent());
			warn.setCreateTime(new Date());
			warn.setUpdateTime(new Date());
			warn.setReadFlag(0L);
			warn.setStatus(1L);
			warn.setStatusRout(warnIssueLog.getEvent());
			gameWarnIssueDao.updateIfHave(warn);
			gameWarnIssueLogServiceImpl.addWarnIssueLog(warnIssueLog);
		}

		//12、添加"输入开奖号码"事件
		GameControlEvent event = new GameControlEvent();

		event.setLotteryid(lotteryid);
		event.setStartIssueCode(issueCode);
		event.setEndIssueCode(issueCode);
		event.setSaleStartTime(issue.getSaleStartTime());
		event.setSaleEndTime(issue.getSaleEndTime());
		event.setStatus(0L);
		event.setCreateTime(new Date());
		event.setEnentType(12L);
		if (warnIssueLog != null) {
			event.setParams(warnIssueLog.getDisposeUser());
		}

		if (warnIssueLog != null) {
			event.setWarnIssueId(warn.getId());
			if (warn.getId() == null) {
				event.setWarnIssueId(-1L * warnIssueLog.getId());
			} else {
				event.setWarnIssueId(warn.getId());
			}
		}
		gameControlEventDao.addControlEvent(event);
				
		if(lotteryid==99501L){
			Long otherLotteryId=99601L; 
			GameIssueEntity gameIssue = gameIssueDao.queryGameIssue(otherLotteryId, issue.getWebIssueCode());
			if(gameIssue!=null){
				if(null!=warnIssueLog){
					warnIssueLog.setLotteryid(otherLotteryId);
					warnIssueLog.setIssueCode(gameIssue.getIssueCode());
				}
				inputNumberDrawResult(otherLotteryId, gameIssue.getIssueCode(), numberRecord, warnIssueLog, ecVerifiedTime);
			}
		}

	}

	@Override
	public void modifyDrawResult(Long lotteryid, Long issueCode, String numberRecord, GameWarnIssueLog warnIssueLog,Date ecVerifiedTime)
			throws Exception {
		modifyDrawResult(lotteryid, issueCode, numberRecord, warnIssueLog, 1L, ecVerifiedTime);
	}

	@Override
	public void modifyDrawResult(Long lotteryid, Long issueCode, String numberRecord, GameWarnIssueLog warnIssueLog,
			Long type,Date ecVerifiedTime) throws Exception {
		GameIssueEntity issue = gameIssueDao.queryGameIssue(lotteryid, issueCode);
		issue.setNumberRecord(numberRecord);
		issue.setNumberUpdateTime(DateUtils.currentDate());
		issue.setNumberUpdateCount((issue.getNumberUpdateCount()==null?0:issue.getNumberUpdateCount())+1);
		issue.setRecivceDrawTime(new Date());
		issue.setUpdateTime(new Date());
		gameIssueServiceImpl.updateGameIssue(issue);
		//1、更新开奖号码
		GameDrawResult drawResult = gameDrawResultDao.getDrawResuleByLotteryIdAndIssueCode(lotteryid, issueCode);
		drawResult.setNumberRecord(numberRecord);
		drawResult.setUpdateTime(new Date());
		drawResult.setType(type);
		drawResult.setOpenDrawTime(issue.getOpenDrawTime());
		gameDrawResultDao.update(drawResult);

		//添加奖期警告表
		GameWarnIssue warn = new GameWarnIssue();
		warn.setIssueCode(issueCode);
		warn.setLotteryid(lotteryid);
		warn.setWebIssueCode(issue.getWebIssueCode());
		warn.setIssueWarnId(warnIssueLog.getEvent());
		warn.setCreateTime(new Date());
		warn.setUpdateTime(warn.getCreateTime());
		warn.setReadFlag(0L);
		warn.setStatus(2L);
		warn.setStatusRout(warnIssueLog.getEvent());
		gameWarnIssueDao.updateIfHave(warn);
		gameWarnIssueLogServiceImpl.addWarnIssueLog(warnIssueLog);

		//2、添加"重新开奖计奖"事件
		GameControlEvent event = new GameControlEvent();
		event.setLotteryid(lotteryid);
		event.setStartIssueCode(issueCode);
		event.setEndIssueCode(issueCode);
		event.setSaleStartTime(issue.getSaleStartTime());
		event.setSaleEndTime(issue.getSaleEndTime());
		event.setStatus(0L);
		event.setCreateTime(new Date());
		event.setEnentType(6L);
		if (warnIssueLog != null) {
			event.setParams(warnIssueLog.getDisposeUser());
		}
		if (warn.getId() == null) {
			event.setWarnIssueId(-1L * warnIssueLog.getId());
		} else {
			event.setWarnIssueId(warn.getId());
		}
		event.setMessage(warnIssueLog.getDisposeInfo());
		gameControlEventDao.addControlEvent(event);
		
		if(lotteryid==99501L){
			Long otherLotteryId=99601L; 
			GameIssueEntity gameIssue = gameIssueDao.queryGameIssue(otherLotteryId, issue.getWebIssueCode());
			if(gameIssue!=null){
				if(null!=warnIssueLog){
					warnIssueLog.setLotteryid(otherLotteryId);
					warnIssueLog.setIssueCode(gameIssue.getIssueCode());
				}
				modifyDrawResult(otherLotteryId, gameIssue.getIssueCode(), numberRecord, warnIssueLog, type, ecVerifiedTime);
			}
		}
	}

	@Override
	public void receivedAwardNumberBeforeSaleTime(Long lotteryid, Long issueCode, String numberRecord,
			GameWarnIssueLog warnIssueLog,Date ecVerifiedTime) throws Exception {
		Date now = new Date();
		GameIssueEntity issue = gameIssueDao.queryGameIssue(lotteryid, issueCode);
		
		//1、添加奖期结果记录
		GameDrawResult drawResultDB = gameDrawResultDao.getDrawResuleByLotteryIdAndIssueCode(lotteryid, issueCode);
		if(drawResultDB == null){
						
			//更新奖期状态"开奖号码已确认"	
			issue.setNumberRecord(numberRecord);
			issue.setNumberUpdateTime(DateUtils.currentDate());
			issue.setNumberUpdateCount((issue.getNumberUpdateCount()==null?0:issue.getNumberUpdateCount())+1);
			issue.setStatus(GameIssueStatus.ACK_DRAW_RESULT);
			issue.setPeriodStatus(GameIssuePeriodStatus.WAIT_DRAW_RESULT);
			//issue.setFactionDrawTime(now);
			issue.setRecivceDrawTime(now);
			issue.setEcVerifiedTime(ecVerifiedTime);
			issue.setUpdateTime(now);
			gameIssueServiceImpl.updateGameIssue(issue);
			
			GameDrawResult drawResult = new GameDrawResult();
			drawResult.setLotteryid(lotteryid);
			drawResult.setIssueCode(issueCode);
			drawResult.setWebIssueCode(issue.getWebIssueCode());
			drawResult.setNumberRecord(numberRecord);
			drawResult.setCreateTime(now);
			drawResult.setOpenDrawTime(now);
			drawResult.setType(0L);
			gameDrawResultDao.insert(drawResult);
		}else if(drawResultDB != null && drawResultDB.getType().longValue() != 0L){
			log.warn(issueCode+"已经手动输入开奖号码！！！");
		}else{			
			drawResultDB.setNumberRecord(numberRecord);
			drawResultDB.setUpdateTime(now);
			drawResultDB.setType(0L);
			drawResultDB.setOpenDrawTime(now);
			gameDrawResultDao.update(drawResultDB);
		}
		

		//暂停奖期
		warnIssueLog.setEvent(GameWarnEvent.SYSTEM_B_AWARD_1.getCode());
		warnIssueLog.setDisposeInfo(GameWarnEvent.SYSTEM_B_AWARD_1.getMessage());
		gameIssueServiceImpl.pauseIssue(lotteryid, issueCode, warnIssueLog);

		if(lotteryid==99501L){ //江苏骰宝借用江苏快三开奖结果
			Long otherLotteryId=99601L;
			GameIssueEntity gameIssue = gameIssueDao.queryGameIssue(otherLotteryId, issue.getWebIssueCode());
			if(gameIssue!=null){
				warnIssueLog.setLotteryid(otherLotteryId);
				warnIssueLog.setIssueCode(gameIssue.getIssueCode());
				receivedAwardNumberBeforeSaleTime(otherLotteryId, gameIssue.getIssueCode(), numberRecord, warnIssueLog, ecVerifiedTime);
			}
		}
	}

	@Override
	public void receivedAwardNumberBeforeSaleTimeNotCare(Long lotteryid, Long issueCode, String numberRecord,
			GameWarnIssueLog warnIssueLog,Date ecVerifiedTime) throws Exception {
		warnIssueLog.setEvent(GameWarnEvent.SYSTEM_B_AWARD_2.getCode());
		warnIssueLog.setDisposeInfo(GameWarnEvent.SYSTEM_B_AWARD_2.getMessage());
		gameWarnIssueLogServiceImpl.addWarnIssueLog(warnIssueLog);
		addDrawResult(lotteryid, issueCode, numberRecord, warnIssueLog,ecVerifiedTime, "");

	}

	@Override
	public void receivedSystemCancelAward(Long lotteryid, Long issueCode, String numberRecord,
			GameWarnIssueLog warnIssueLog) throws Exception {
		
		warnIssueLog.setEvent(GameWarnEvent.SYSTEM_C_AWARD.getCode());
		warnIssueLog.setDisposeInfo(GameWarnEvent.SYSTEM_C_AWARD.getMessage());
		gameWarnIssueLogServiceImpl.addWarnIssueLog(warnIssueLog);
		
		if(lotteryid==99501L){ //江苏骰宝借用江苏快三开奖结果
			Long otherlotteryId=99601L;
			String webIssueCode = getWebIssueCode(issueCode);
			GameIssueEntity gameIssue = gameIssueDao.queryGameIssue(otherlotteryId, webIssueCode);
			if(gameIssue!=null){
				warnIssueLog.setLotteryid(otherlotteryId);
				warnIssueLog.setIssueCode(gameIssue.getIssueCode());
				receivedSystemCancelAward(otherlotteryId, gameIssue.getIssueCode(), numberRecord, warnIssueLog);
			}
		}
		
//		GameIssueEntity issue = gameIssueDao.queryGameIssue(lotteryid, issueCode);
//		if(issue.getStatus().getValue() < GameIssueStatus.ACK_DRAW_RESULT.getValue() ){
//			
//			warnIssueLog.setEvent(GameWarnEvent.SYSTEM_C_AWARD.getCode());
//			warnIssueLog.setDisposeInfo(GameWarnEvent.SYSTEM_C_AWARD.getMessage());
//			gameWarnIssueLogServiceImpl.addWarnIssueLog(warnIssueLog);
//			
//			//暂停奖期
//			//gameIssueServiceImpl.pauseIssue(lotteryid, issueCode, warnIssueLog);
//		}else{
//			warnIssueLog.setEvent(GameWarnEvent.SYSTEM_C_AWARD.getCode());
//			warnIssueLog.setDisposeInfo(GameWarnEvent.SYSTEM_C_AWARD.getMessage());
//			gameWarnIssueLogServiceImpl.addWarnIssueLog(warnIssueLog);
//			
//			//添加撤销本期方案事件
//			GameControlEvent event = new GameControlEvent();
//			event.setLotteryid(lotteryid);
//			event.setStartIssueCode(issueCode);
//			event.setEndIssueCode(issueCode);
//			event.setSaleStartTime(issue.getSaleStartTime());
//			event.setSaleEndTime(issue.getSaleEndTime());
//			event.setStatus(0L);
//			event.setCreateTime(new Date());
//			event.setEnentType(4L);
//			event.setParams(warnIssueLog.getDisposeUser());	
//			event.setWarnIssueId(-1L * warnIssueLog.getId());			
//			event.setMessage(warnIssueLog.getDisposeInfo());
//			gameControlEventDao.addControlEvent(event);
//		}
		
	}

	private String getWebIssueCode(Long issueCode) {
		String strIssueCode=String.valueOf(issueCode);
		String webIssueCode=strIssueCode.substring(0,8)+"-"+strIssueCode.substring(strIssueCode.length()-3,strIssueCode.length());
		return webIssueCode;
	}
	
	@Override
	public void saveDrawECResultEvent(Long lotteryId,String gameWebIssueCode,String params) throws Exception{
		GameIssueEntity issue = gameIssueDao.queryGameIssue(lotteryId, gameWebIssueCode);
		GameControlEvent event = new GameControlEvent();
		event.setEnentType((long)GameControlEvent.EventType.EC_DRAW.getValue());
		event.setLotteryid(lotteryId);
		event.setStartIssueCode(issue.getIssueCode());
		event.setEndIssueCode(issue.getIssueCode());
		event.setSaleStartTime(issue.getSaleStartTime());
		event.setSaleEndTime(issue.getSaleEndTime());
		event.setStatus(-1L);
		event.setCreateTime(new Date());
		event.setParams(params);
		gameControlEventDao.addControlEvent(event);
		//如果是江蘇快三 則同步新增江蘇骰寶EC開獎任務
		boolean isJsk3 = 99501L==lotteryId;
		if(isJsk3){
			saveDrawECResultEvent(99601L, gameWebIssueCode, params);
		}
	}
	@Override
	public void saveDrawZKResultEvent(Long lotteryId,String gameWebIssueCode,String params) throws Exception{
		GameIssueEntity issue = gameIssueDao.queryGameIssue(lotteryId, gameWebIssueCode);
		GameControlEvent event = new GameControlEvent();
		event.setEnentType((long)GameControlEvent.EventType.EC_DRAW.getValue());
		event.setLotteryid(lotteryId);
		event.setStartIssueCode(issue.getIssueCode());
		event.setEndIssueCode(issue.getIssueCode());
		event.setSaleStartTime(issue.getSaleStartTime());
		event.setSaleEndTime(issue.getSaleEndTime());
		event.setStatus(-1L);
		event.setCreateTime(new Date());
		event.setParams(params);
		gameControlEventDao.addControlEvent(event);
	}
}
