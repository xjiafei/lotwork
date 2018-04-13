package com.winterframework.firefrog.game.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameControlEventDao;
import com.winterframework.firefrog.game.dao.IGameIssueDao;
import com.winterframework.firefrog.game.dao.IGameIssueRuleCheckDao;
import com.winterframework.firefrog.game.dao.IGameIssueRuleDao;
import com.winterframework.firefrog.game.dao.IGameIssueTemplateCheckDao;
import com.winterframework.firefrog.game.dao.IGameIssueTemplateDao;
import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGameWarnIssueDao;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssue;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssueLog;
import com.winterframework.firefrog.game.dao.vo.VOConverter4Task;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameIssueRuleEntity;
import com.winterframework.firefrog.game.entity.GameIssueTemplateEntity;
import com.winterframework.firefrog.game.entity.GameSeriesConfigEntity;
import com.winterframework.firefrog.game.entity.VOConvert;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGamePlanService;
import com.winterframework.firefrog.game.service.IGameSeriesConfigService;
import com.winterframework.firefrog.game.service.IGameWarnIssueLogService;
import com.winterframework.firefrog.game.web.dto.GameIssueListQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueManualGenerateRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueManualGenerateResponse;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryResponse;
import com.winterframework.firefrog.game.web.dto.HistoryLotteryAwardRequest;
import com.winterframework.firefrog.game.web.dto.HistoryLotteryAwardResponse;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

@Service("gameIssueServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameIssueServiceImpl implements IGameIssueService {

	private Logger log = LoggerFactory.getLogger(GameIssueServiceImpl.class);

	@Resource(name = "gameIssueDaoImpl")
	private IGameIssueDao gameIssueDao;

	@Resource(name = "gameIssueRuleDaoImpl")
	private IGameIssueRuleDao gameIssueRuleDao;

	@Resource(name = "gameIssueRuleCheckDaoImpl")
	private IGameIssueRuleCheckDao gameIssueRuleCheckDao;

	@Resource(name = "gameIssueTemplateCheckDaoImpl")
	private IGameIssueTemplateCheckDao gameIssueTemplateCheckDao;

	@Resource(name = "gameIssueTemplateDaoImpl")
	private IGameIssueTemplateDao gameIssueTemplateDao;

	@Resource(name = "gameSeriesConfigServiceImpl")
	private IGameSeriesConfigService configService;

	@Resource(name = "gameControlEventDaoImpl")
	private IGameControlEventDao gameControlEventDao;

	@Resource(name = "gameWarnIssueDaoImpl")
	private IGameWarnIssueDao gameWarnIssueDao;

	@Resource(name = "gameWarnIssueLogServiceImpl")
	private IGameWarnIssueLogService gameWarnIssueLogServiceImpl;
	@Resource(name = "gamePlanServiceImpl")
	private IGamePlanService gamePlanServiceImpl;

	@Resource(name="gameOrderDaoImpl")
	private IGameOrderDao gameOrderDaoImpl;
	
	@Override
	public boolean isCurrentIssue(Long issueCode) throws Exception {

		GameIssue gameIssue = gameIssueDao.getGameIssueByIssueCode(issueCode);
		//销售中，开始销售
		if (1 == gameIssue.getStatus() && 1 == gameIssue.getPeriodStatus()) {
			return true;
		}
		return false;
	}

	@Override
	public GameIssueEntity queryGameIssue(Long lotteryid, Long issueCode) throws Exception {
		return gameIssueDao.queryGameIssue(lotteryid, issueCode);
	}

	@Override
	public Page<GameIssueEntity> queryGameIssues(PageRequest<GameIssueListQueryRequest> pr,Date takeOffTime) {
		Page<GameIssueEntity> page = gameIssueDao.queryGameIssues(pr,takeOffTime);
		return page;
	}
	
	@Override
	public List<GameIssueEntity> queryGameIssues(GameIssueListQueryRequest request,Date takeOffTime) {
		List<GameIssueEntity> list = gameIssueDao.queryGameIssues(request,takeOffTime);
		return list;
	}

	/**
	* Title: queryGameIssueRules
	* Description: 当有修改的规则存在时，显示check中的记录
	* @param lotteryId
	* @param ruleId
	* @return 
	* @see com.winterframework.firefrog.game.service.IGameIssueService#queryGameIssueRules(long, java.lang.Long) 
	*/
	@Override
	public List<GameIssueRuleEntity> queryGameIssueRules(long lotteryId, Long ruleId) {
		List<GameIssueRuleEntity> rules = gameIssueRuleCheckDao.queryGameIssueRuleCheckByLotteryIdAndRuleId(lotteryId,
				ruleId);
		for (GameIssueRuleEntity rule : rules) {
			List<GameIssueTemplateEntity> templates = new ArrayList<GameIssueTemplateEntity>();
			if (rule.getStatus() == 4) {//如果规则状态是待审核 //20131114 状态为4才是待审核
				templates = gameIssueTemplateCheckDao.getGameIssueTemplateCheckByRuleId(rule.getId());
			} else {
				templates = gameIssueTemplateDao.getGameIssueTemplateByRuleId(rule.getId());
			}
			rule.setGameIssueTemplateEntitys(templates);
		}
		return rules;
	}

	public GameIssueRuleEntity queryGameIssueRuleById(Long lotteryId, Long ruleId, Integer status) {
		/*1:进行中
		*2:已删除  3:已停止
		*4:待审核
		*/

		if (null != status && status < 3) { //正式表
			GameIssueRuleEntity rules = gameIssueRuleDao.getGameIssueRuleById(ruleId);
			List<GameIssueTemplateEntity> templates = new ArrayList<GameIssueTemplateEntity>();
			templates = gameIssueTemplateDao.getGameIssueTemplateByRuleId(rules.getId());
			rules.setGameIssueTemplateEntitys(templates);
			return rules;
		} else {

			GameIssueRuleEntity rules = gameIssueRuleCheckDao.getGameIssueRuleCheckByLotteryIdAndRuleId(lotteryId,
					ruleId);
			List<GameIssueTemplateEntity> templates = new ArrayList<GameIssueTemplateEntity>();
			templates = gameIssueTemplateCheckDao.getGameIssueTemplateCheckByRuleId(ruleId);
			rules.setGameIssueTemplateEntitys(templates);
			return rules;
		}

	}

	@Override
	public List<GameIssueEntity> getGameIssuesByLotteryId(Long lotteryId) {
		return gameIssueDao.getGameIssuesByLotteryId(lotteryId);
	}
	
	@Override
	public List<GameIssueEntity> getBackGameIssuesByLotteryId(Long lotteryId) {
		return gameIssueDao.getBackGameIssuesByLotteryId(lotteryId);
	}

	@Override
	public List<GameIssueEntity> queryTraceGameIssues(Long lotteryId, Integer maxCountIssue) {
		return gameIssueDao.queryTraceGameIssues(lotteryId, maxCountIssue);
	}

	@Override
	public List<GameIssueEntity> getGameIssueByLotteryIdAndStatus(Long lotteryId) throws Exception {
		return gameIssueDao.getGameIssueByLotteryIdAndStatus(lotteryId);
	}

	@Override
	public void updateGameIssue(GameIssueEntity issueEntity) throws Exception {
		gameIssueDao.update(VOConvert.gameIssueEntity2GameIssue(issueEntity));
	}

	@Override
	public void pauseIssue(Long lotteryid, Long issueCode, GameWarnIssueLog warnIssueLog) throws Exception {
		GameIssueEntity entity = gameIssueDao.queryGameIssue(lotteryid, issueCode);
		GameIssue gameIssue = VOConvert.gameIssueEntity2GameIssue(entity);
		//将奖期暂停
		gameIssue.setId(entity.getId());
		gameIssue.setPauseStatus(0);

		gameIssueDao.update(gameIssue);

		// 20140212 奖期暂停时将下一期的LAST_ISSUE_STOP字段更新
		GameIssue nextGameIssue = gameIssueDao.getNextGameIssueByIssueAndLotteryId(lotteryid, issueCode);

		if (nextGameIssue != null) {
			GameSeriesConfigEntity en = configService.queryGameSeriesConfigByLotteryId(lotteryid);
			nextGameIssue.setIssuewarnExceptionTime(en.getIssuewarnExceptionTime());
			nextGameIssue.setLastIssueStop(1);
			if (nextGameIssue.getStatus() == 0L) {
				nextGameIssue.setStatus(1L);
				nextGameIssue.setPeriodStatus(1L);
			}
			gameIssueDao.update(nextGameIssue);
		}

		//添加奖期警告表，由于不用添加主控任务，奖期异常任务直接标识为已处理
		GameWarnIssue warn = new GameWarnIssue();
		warn.setIssueCode(issueCode);
		warn.setLotteryid(lotteryid);
		warn.setWebIssueCode(entity.getWebIssueCode());
		warn.setIssueWarnId(warnIssueLog.getEvent());
		warn.setCreateTime(new Date());
		warn.setUpdateTime(warn.getCreateTime());
		warn.setReadFlag(0L);
		warn.setStatus(1L);
		warn.setStatusRout(warnIssueLog.getEvent());
		gameWarnIssueDao.saveOrUpdate(warn);
		gameWarnIssueLogServiceImpl.addWarnIssueLog(warnIssueLog);
	}

	@Override
	public void continueIssue(Long lotteryid, Long issueCode, GameWarnIssueLog warnIssueLog) throws Exception {
		GameIssueEntity entity = gameIssueDao.queryGameIssue(lotteryid, issueCode);
		updataGameIssuePauseStatus(entity, 1);

		// 20140216 add 更新追号的状态为“进行中”
		gamePlanServiceImpl.continueGamePlan(lotteryid, issueCode);
		// 20140218 奖期继续时将下一期的LAST_ISSUE_STOP字段更新
		GameIssue nextGameIssue = gameIssueDao.getNextGameIssueByIssueAndLotteryId(lotteryid, issueCode);
		nextGameIssue.setLastIssueStop(0);
		gameIssueDao.update(nextGameIssue);
		
		
		//添加奖期警告表
		GameWarnIssue warn = new GameWarnIssue();
		warn.setIssueCode(issueCode);
		warn.setLotteryid(lotteryid);
		warn.setWebIssueCode(gameIssueDao.getGameIssueByIssueCode(issueCode).getWebIssueCode());
		warn.setIssueWarnId(warnIssueLog.getEvent());
		warn.setCreateTime(new Date());
		warn.setUpdateTime(warn.getCreateTime());
		warn.setReadFlag(0L);
		warn.setStatus(2L);
		warn.setStatusRout(warnIssueLog.getEvent());
		gameWarnIssueDao.saveOrUpdate(warn);
		gameWarnIssueLogServiceImpl.addWarnIssueLog(warnIssueLog);

		//2、根据奖期状态添加相关待处理事件
		GameControlEvent event = new GameControlEvent();
		event.setLotteryid(lotteryid);
		event.setStartIssueCode(issueCode);
		event.setEndIssueCode(issueCode);
		event.setSaleStartTime(entity.getSaleStartTime());
		event.setSaleEndTime(entity.getSaleEndTime());
		event.setStatus(0L);
		event.setCreateTime(new Date());
		event.setWarnIssueId(warn.getId());
		event.setEnentType(9L);
		gameControlEventDao.addControlEvent(event);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updataGameIssuePauseStatus(GameIssueEntity gameIssueEntity, int i) {
		GameIssue issue = gameIssueDao.getById(gameIssueEntity.getId());
		issue.setPauseStatus(i);
		gameIssueDao.update(issue);
	}

	@Override
	public boolean isEarlierSuspendedGameIssueExist(Long lotteryid, Long issueCode) {
		List<GameIssue> earlierSuspendedGameIssueList = gameIssueDao.getEarlierSuspendedGameIssue(lotteryid, issueCode);
		if (earlierSuspendedGameIssueList != null && earlierSuspendedGameIssueList.size() > 0) {
			return true;
		}

		return false;
	}

	@Override
	public GameIssueEntity queryNextGameIssue(Long lotteryId, Long issueCode) throws Exception {

		GameIssue issue = gameIssueDao.getNextGameIssueByIssueAndLotteryId(lotteryId, issueCode);
		if (null != issue) {
			return VOConverter4Task.gameIssue2GameIssueEntity(issue);
		}
		return null;
	}

	@Override
	public GameIssue getEarlierSuspendedGameIssue(Long lotteryid, Long issueCode) {

		//		List<GameIssue> earlierSuspendedGameIssueList = gameIssueDao.getEarlierSuspendedGameIssue(lotteryid, issueCode);
		//		if (earlierSuspendedGameIssueList != null && earlierSuspendedGameIssueList.size() > 0) {
		//			return earlierSuspendedGameIssueList.get(0);
		//		}
		//		return null;
		return gameIssueDao.getGameIssueByIssueCodeAndLottery(lotteryid, issueCode);
	}

	@Override
	public GameIssueEntity getGameIssue(Long lotteryid, Long issueCode) throws Exception {
		return gameIssueDao.queryGameIssue(lotteryid, issueCode);
	}
	public GameIssueEntity getGameIssue(Long lotteryid, String webIssueCode) throws Exception {
		return gameIssueDao.queryGameIssue(lotteryid, webIssueCode);
	}
	@Override
	public List<GameIssueEntity> getGameIssueForLockData(Long lotteryid) throws Exception {
		Long issueCode = null;
		GameIssueEntity currentEntity = gameIssueDao.queryGameIssue(lotteryid, issueCode);
		List<GameIssueEntity> preList = gameIssueDao.queryPreGameIssue(lotteryid, currentEntity.getIssueCode(), 10l);

		List<GameIssueEntity> nextList = gameIssueDao.queryNextGameIssue(lotteryid, currentEntity.getIssueCode(), 10l);
		List<GameIssueEntity> resultList = new ArrayList<GameIssueEntity>();
		resultList.add(currentEntity);
		for (int i = preList.size() - 1; i >= 0; i--) {
			resultList.add(preList.get(i));
		}
		resultList.addAll(nextList);
		return resultList;
	}

	@Override
	public GameIssueEntity getLastDrawGameIssue(Long lotteryId) {
		return gameIssueDao.getLastDrawGameIssue(lotteryId);
	}

	/**
	* Title: manualGenerateIssues
	* Description:
	* @param res
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameIssueService#manualGenerateIssues(com.winterframework.firefrog.game.web.dto.GameIssueManualGenerateRequest) 
	*/
	@Override
	public GameIssueManualGenerateResponse manualGenerateIssues(GameIssueManualGenerateRequest res,Date takeOffTime) throws Exception {
		return gameIssueDao.manualGenerateIssues(res,takeOffTime);
	}

	@Override
	public GameIssue getMaxGameIssuesByLotteryId(Long lotteryId) throws Exception {
		return gameIssueDao.getMaxGameIssuesByLotteryId(lotteryId);
	}

	/**
	* Title: manualDeleteIssues
	* Description:
	* @param type
	* @param start
	* @param end
	* @param lotteryId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameIssueService#manualDeleteIssues(int, java.lang.String, java.lang.String, java.lang.Long) 
	*/
	@Override
	public GameIssueManualGenerateResponse manualDeleteIssues(int type, String start, String end, Long lotteryId)
			throws Exception {
		return gameIssueDao.manualDeleteIssues(type, start, end, lotteryId);
	}

	/**
	* Title: updateCommonRuleScheduleStopTime
	* Description:
	* @param lotteryId
	* @param scheduleStopTime
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameIssueService#updateCommonRuleScheduleStopTime(java.lang.Long, java.lang.Integer) 
	*/
	@Override
	public void updateCommonRuleScheduleStopTime(Long lotteryId, Integer scheduleStopTime) throws Exception {
		gameIssueTemplateDao.updateCommonRuleTemplate(lotteryId, scheduleStopTime);
		gameIssueTemplateCheckDao.updateCommonRuleTemplateCheck(lotteryId, scheduleStopTime);
		gameIssueDao.updateGameIssueScheduleStopTime(lotteryId, scheduleStopTime);
	}

	@Override
	public String getStartWebIssueCode(Long lotteryId, Long startTime) throws Exception {
		return gameIssueDao.getStartWebIssueCode(lotteryId, startTime);
	}
	
	@Override
	public List<GameIssue> getGameIssueNumberRecord(Long lotteryId) throws Exception {
		return gameIssueDao.getGameIssueNumberRecord(lotteryId);
	}
	
	@Override
	public GameIssueQueryResponse updateOpenAwardTime(GameIssueQueryRequest request) throws Exception {
		GameIssueQueryResponse resp = new GameIssueQueryResponse();
		try{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long lotteryId = request.getLotteryId();
			long issueCode = request.getIssueCode();
			Date openAwardTime = request.getOpenAwardTime(); //修改的開獎時間
			//Date openDrawTime = null; //這一期開獎時間
			Date nextOpenDrawTime =null;//下一期開獎時間
			Date extendDoubleOpenDrawTime = null; //可調整延長兩倍時間
			Date earlyDoubleOpenDrawTime = null; //可調整提早兩倍時間
			Date previousExtendDoubleOpenDrawTime =null;
			Date newSaleEndTime = null;
			long scheduleStopTime = 0;//開獎等待時間
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("lotteryid", lotteryId);
			map.put("ruleType", 1);
			
			//開獎等待時間
			List<GameIssueRuleEntity> rules = gameIssueRuleDao.queryGameIssueRuleByMap(map);
			for (GameIssueRuleEntity rule : rules) {
				List<GameIssueTemplateEntity> templates  = gameIssueTemplateDao.getGameIssueTemplateByRuleId(rule.getId());
				if(templates != null && templates.size()>0)
				scheduleStopTime = templates.get(0).getScheduleStopTime();
			
			}
			map.put("lotteryId",lotteryId);
			map.put("issueCode",issueCode);
			
			//原開獎時間
			GameIssue gameIssue = gameIssueDao.queryGameIssue(map);
			
			
			
			map.put("lotteryId", lotteryId);
			map.put("issueCode", issueCode);
			map.put("count", 1L);
			
			//下一筆開獎時間
			GameIssue nextGameIssue = gameIssueDao.queryNextGameIssue(map);
			nextOpenDrawTime = nextGameIssue.getOpenDrawTime();

			//上一筆開獎時間
			GameIssue previousGameIssue = gameIssueDao.queryPreviousGameIssue(map);
			//上期開獎時間往後兩倍開獎時間
			Calendar cl = Calendar.getInstance();
			cl.setTime(previousGameIssue.getOpenDrawTime());
			cl.add(Calendar.SECOND , Integer.valueOf(String.valueOf(scheduleStopTime*2)));
			previousExtendDoubleOpenDrawTime = cl.getTime();

			//下期開獎時間往前兩倍開獎時間
			cl.setTime(nextOpenDrawTime);
			cl.add(Calendar.SECOND , -Integer.valueOf(String.valueOf(scheduleStopTime*2)));
			extendDoubleOpenDrawTime = cl.getTime();
			
			//當前時間往後兩倍等待開獎時間
			cl.setTime(new Date());
			cl.add(Calendar.SECOND , Integer.valueOf(String.valueOf(scheduleStopTime*2)));
			earlyDoubleOpenDrawTime = cl.getTime();
			
			//判斷
			resp.setResponseStatus(2L);
			if(openAwardTime.compareTo(nextOpenDrawTime) >0){
				resp.setMessage("调整时间不允许超过下一期开奖时间");
			}else if(openAwardTime.compareTo(extendDoubleOpenDrawTime) > 0 
					|| openAwardTime.compareTo(earlyDoubleOpenDrawTime) < 0){
				
				resp.setMessage("开奖时间设定错误，重新调整。");
				//resp.setMessage("调整时间不允许超过"+extendDoubleOpenDrawTime);
			}/*else if(openAwardTime.compareTo(earlyDoubleOpenDrawTime)<0){
				resp.setMessage("调整时间不允许低于"+earlyDoubleOpenDrawTime);
			}*/
			else if(new Date().compareTo(gameIssue.getSaleEndTime()) > 0){
				resp.setMessage("奖期已经停止销售，不再允许调整开奖时间。");
			}else if(openAwardTime.compareTo(previousExtendDoubleOpenDrawTime) <= 0){
				resp.setMessage("调整时间不允许提前至上一期的开奖时间+2*开奖等待时间"/*+earlyDoubleOpenDrawTime*/);
			}else{
			
				//重新計算當期的銷售截止日
				cl.setTime(openAwardTime);
				cl.add(Calendar.SECOND , -Integer.valueOf(String.valueOf(scheduleStopTime)));
				newSaleEndTime = cl.getTime();
				
				gameIssue.setSaleEndTime(newSaleEndTime);
				gameIssue.setOpenDrawTime(openAwardTime);
				gameIssue.setUpdateTime(new Date());
				gameIssueDao.update(gameIssue);
				
				//變更銷售時間,同時更改目前訂單可撤銷時間-lhc
				gameOrderDaoImpl.updateEndCanCancelTime(lotteryId,issueCode,newSaleEndTime);
				
				//上一期銷售截止立刻改下一期開賣時間
				nextGameIssue.setSaleStartTime(newSaleEndTime);
				gameIssueDao.update(nextGameIssue);
				resp.setResponseStatus(1L);
				resp.setMessage("开奖时间调整成功");
				resp.setNewSaleEndTime(dateFormat.format(newSaleEndTime));
				resp.setNewNextSaleStartTime(dateFormat.format(newSaleEndTime));
			}
		}catch(Exception e){
			resp.setMessage("开奖时间调整失败");
			log.error(e.toString());
		}
		
		
		return  resp;
	}
	
	@Override
	public GameIssueQueryResponse getNextOpenAwardTime(GameIssueQueryRequest request)throws Exception  {
		GameIssueQueryResponse resp = new GameIssueQueryResponse();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			
			map.put("lotteryId", request.getLotteryId());
			map.put("issueCode", request.getIssueCode());
			
			//原開獎時間
			GameIssue gameIssue = gameIssueDao.queryGameIssue(map);
			resp.setCurrentOpenDrawTime(dateFormat.format(gameIssue.getOpenDrawTime()));
			resp.setWebIssueCode(gameIssue.getWebIssueCode());
			resp.setId(gameIssue.getId());
			
			map.put("count", 1L);
			
			//下一筆開獎時間
			GameIssue nextGameIssue = gameIssueDao.queryNextGameIssue(map);
			
			resp.setNextOpenDrawTime(dateFormat.format(nextGameIssue.getOpenDrawTime()));
			resp.setNextWebIssueCode(nextGameIssue.getWebIssueCode());
			resp.setNextId(nextGameIssue.getId());
			resp.setResponseStatus(1L);

		}catch(Exception e){
			resp.setMessage("取得下期开奖时间失败");
			log.error(e.toString());
		}
		return resp;
		
	}
	
	
	@Override
	public GameIssueQueryResponse doExtendOpenAwardTime(GameIssueQueryRequest request)throws Exception  {
		GameIssueQueryResponse resp = new GameIssueQueryResponse();
			Map<String, Object> map = new HashMap<String, Object>();
		try{
			

			//原開獎時間
			GameIssue gameIssue = gameIssueDao.getById(request.getId());
			//下一筆開獎時間
			GameIssue nextGameIssue = gameIssueDao.getById(request.getNextId());
			//更新當期資料
			gameIssue.setOpenDrawTime(nextGameIssue.getOpenDrawTime());
			gameIssue.setSaleEndTime(nextGameIssue.getSaleEndTime());
			gameIssue.setUpdateTime(new Date());
			gameIssueDao.update(gameIssue);
			
			//變更銷售時間,同時更改目前訂單可撤銷時間-lhc
			gameOrderDaoImpl.updateEndCanCancelTime(request.getLotteryId(),gameIssue.getIssueCode(),nextGameIssue.getSaleEndTime());
			
			map.put("lotteryId", nextGameIssue.getLotteryid());
			map.put("issueCode", nextGameIssue.getIssueCode());
			map.put("count", 1L);
			//下下一筆獎期
			GameIssue next2GameIssue = gameIssueDao.queryNextGameIssue(map);
			//更新下下筆的上期獎期期號
			next2GameIssue.setLastIssue(gameIssue.getIssueCode());
			next2GameIssue.setUpdateTime(new Date());
			gameIssueDao.update(next2GameIssue);
			
			//刪除下一筆
			gameIssueDao.delete(nextGameIssue.getId());
			map.put("id", gameIssue.getId());
			map.put("lotteryId", gameIssue.getLotteryid());
			gameIssueDao.updateIssuseCodeAfterExtendGameIssue(map);
		
			resp.setResponseStatus(1L);

		}catch(Exception e){
			resp.setMessage("展延开奖时间调整失败");
			log.error(e.toString());
		}
		return resp;
		
	}

	@Override
	public HistoryLotteryAwardResponse getHistoryLotteryAward(
			HistoryLotteryAwardRequest hlaReq) throws Exception {
		HistoryLotteryAwardResponse response = new HistoryLotteryAwardResponse();
		
		try {
			response.setGameIssues(gameIssueDao.getHistoryLotteryAward(hlaReq));
			response.setStatus(1L);
			response.setMessage("成功");
		} catch(Exception e) {
			log.error("getHistoryLotteryAward error.{}", e.getMessage(), e);
			response.setStatus(2L);
			response.setMessage("无法取得历史开奖资料");
		}
		
		return response;
	}
	
	
}


