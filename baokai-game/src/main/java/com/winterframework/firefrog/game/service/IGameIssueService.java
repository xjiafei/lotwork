package com.winterframework.firefrog.game.service;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssueLog;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameIssueRuleEntity;
import com.winterframework.firefrog.game.web.dto.GameIssueListQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueManualGenerateRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueManualGenerateResponse;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryResponse;
import com.winterframework.firefrog.game.web.dto.HistoryLotteryAwardRequest;
import com.winterframework.firefrog.game.web.dto.HistoryLotteryAwardResponse;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

public interface IGameIssueService {

	public boolean isCurrentIssue(Long issueCode) throws Exception;

	/**
	 * 取得某彩種當前獎期及前後各10筆的獎期資料。
	 * @param lotteryid 彩種ID
	 * @return 第一筆為當期獎期，2~11為前10筆獎期，12~21為後10筆獎期。
	 * @throws Exception
	 */
	public List<GameIssueEntity> getGameIssueForLockData(Long lotteryid) throws Exception;

	/**
	 * 假如issuecode为null，则根据当前时间获取当前奖期，假如当前时间段没没有任何奖期则获取下一条即将开奖奖期
	 * @param lotteryid 彩種ID
	 * @param issueCode 獎期
	 * @return
	 * @throws Exception
	 */
	public GameIssueEntity queryGameIssue(Long lotteryid, Long issueCode) throws Exception;
	
	GameIssueEntity getGameIssue(Long lotteryid, String webIssueCode) throws Exception;
	
	/** 
	* @Title: queryGameIssues 
	* @Description: 查询奖期列表
	* @param pr
	* @return
	*/
	public Page<GameIssueEntity> queryGameIssues(PageRequest<GameIssueListQueryRequest> pr,Date takeOffTime);

	/** 
	* @Title: queryGameIssueRules 
	* @Description: 查询奖期规则
	* @param lotteryId 彩种id 
	* @param ruleId 规则id
	* @return
	*/
	public List<GameIssueRuleEntity> queryGameIssueRules(long lotteryId, Long ruleId);

	/**
	 * 
	* @Title: queryGameIssueRuleById 
	* @Description: 根据条件查询gameIssueRule
	* @param lotteryId 彩种id
	* @param ruleId 规则id
	* @param status 状态
	* @return
	 */
	public GameIssueRuleEntity queryGameIssueRuleById(Long lotteryId, Long ruleId, Integer status);

	/** 
	* @Title: getGameIssuesByLotteryId 
	* @Description: 根据彩种id获取奖期列表 
	* @param lotteryId
	* @return
	*/
	public List<GameIssueEntity> getGameIssuesByLotteryId(Long lotteryId);

	/** 
	* @Title: queryTraceGameIssues 
	* @Description: 获取追号奖期列表 
	* @param lotteryId 彩种id
	* @param maxCountIssue 最大追号期数
	* @return
	*/
	public List<GameIssueEntity> queryTraceGameIssues(Long lotteryId, Integer maxCountIssue);

	/**
	 * 
	* @Title: getGameIssueByLotteryIdAndStatus 
	* @Description:获取未结束的奖期信息
	* @param lotteryId 彩种id
	* @return
	* @throws Exception
	 */
	public List<GameIssueEntity> getGameIssueByLotteryIdAndStatus(Long lotteryId) throws Exception;

	/**
	 * 
	* @Title: updateGameIssue 
	* @Description: 更新奖期
	* @param issueEntity
	* @throws Exception
	 */
	public void updateGameIssue(GameIssueEntity issueEntity) throws Exception;

	/**
	 * 
	* @Title: pauseIssue 
	* @Description: 暂停奖期
	* @param lotteryid
	* @param issueCode
	 * @param warnIssueLog 
	* @throws Exception
	 */
	public void pauseIssue(Long lotteryid, Long issueCode, GameWarnIssueLog warnIssueLog) throws Exception;

	/**
	 * 
	* @Title: continueIssue 
	* @Description: 继续奖期
	* @param lotteryid
	* @param issueCode
	 * @param warnIssueLog 
	* @throws Exception
	 */
	public void continueIssue(Long lotteryid, Long issueCode, GameWarnIssueLog warnIssueLog) throws Exception;

	/**
	 * 
	* @Title: updataGameIssuePauseStatus 
	* @Description: 更新奖期的暂停状态 
	* @param gameIssueEntity
	* @param i 暂停状态值 
	* @return     
	* @throws
	 */
	public void updataGameIssuePauseStatus(GameIssueEntity gameIssueEntity, int i);

	/**
	 * 
	* @Title: isEarlierSuspendedGameIssueExist 
	* @Description: 判断是否存在奖期号更小的暂停奖期
	* @param lotteryId
	* @param issueCode
	* @return boolean
	* @throws
	 */
	public boolean isEarlierSuspendedGameIssueExist(Long lotteryid, Long issueCode);

	/**
	 * 
	* @Title: queryNextGameIssue 
	* @Description: 获取下一奖期信息。
	* @param lotteryId
	* @param issueCode
	* @return
	* @throws Exception
	 */
	public GameIssueEntity queryNextGameIssue(Long lotteryId, Long issueCode) throws Exception;

	/**
	 * 
	* @Title: getMinEarlierSuspendedGameIssue 
	* @Description: 获取存在奖期号更小的暂停奖期
	* @param lotteryid
	* @param issueCode
	* @return
	 */
	public GameIssue getEarlierSuspendedGameIssue(Long lotteryid, Long issueCode);

	/**
	 * @Title: getGameIssue
	 * @Description: 获取奖期
	 * @param lotteryid 彩种
	 * @param issueCode 奖期号
	 * @return
	 */
	public GameIssueEntity getGameIssue(Long lotteryid, Long issueCode) throws Exception;

	/** 
	 * 获取彩种最新一期开奖奖期数据
	 * @param lotteryId
	 * @return
	 */
	public GameIssueEntity getLastDrawGameIssue(Long lotteryId);

	public List<GameIssueEntity> queryGameIssues(GameIssueListQueryRequest request,Date takeOffTime);

	public GameIssueManualGenerateResponse manualGenerateIssues(GameIssueManualGenerateRequest res,Date takeOffTime) throws Exception;

	public GameIssue getMaxGameIssuesByLotteryId(Long lotteryId) throws Exception;

	public GameIssueManualGenerateResponse manualDeleteIssues(int type, String start, String end, Long lotteryId)
			throws Exception;

	public void updateCommonRuleScheduleStopTime(Long lotteryId, Integer scheduleStopTime) throws Exception;

	public String getStartWebIssueCode(Long lotteryId, Long startTime) throws Exception;

	List<GameIssueEntity> getBackGameIssuesByLotteryId(Long lotteryId);
	
	public List<GameIssue> getGameIssueNumberRecord(Long lotteryId) throws Exception;
	
	public GameIssueQueryResponse updateOpenAwardTime(GameIssueQueryRequest request) throws Exception;
	
	public GameIssueQueryResponse getNextOpenAwardTime(GameIssueQueryRequest request)throws Exception;
	
	public GameIssueQueryResponse doExtendOpenAwardTime(GameIssueQueryRequest request)throws Exception;

	/**
	 * 依據彩種代碼取回指定筆數的歷史開獎資料。
	 * @param hlaReq
	 * @return
	 * @throws Exception
	 */
	public HistoryLotteryAwardResponse getHistoryLotteryAward(HistoryLotteryAwardRequest hlaReq) throws Exception;
}
