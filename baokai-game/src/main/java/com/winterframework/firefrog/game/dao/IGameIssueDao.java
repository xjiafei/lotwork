package com.winterframework.firefrog.game.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.game.dao.vo.GameExceptionAuditOrder;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.web.dto.GameExceptionAuditRequestDTO;
import com.winterframework.firefrog.game.web.dto.GameIssueListQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueManualGenerateRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueManualGenerateResponse;
import com.winterframework.firefrog.game.web.dto.HistoryLotteryAwardRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IGameIssueDao extends BaseDao<GameIssue> {

	GameIssue queryCurrentGameIssue(Long lotteryId) throws Exception;

	/**
	 * 
	* @Title: getGameIssueByIssueCode 
	* @Description: 根据奖期获取奖期信息
	* @param issueCode
	* @return
	* @throws Exception
	 */
	public GameIssue getGameIssueByIssueCode(Long issueCode) throws Exception;

	/** 
	* @Title: getMinIssueNotReciveResultCode 
	* @Description: 获取没有获取到开奖号码最小的奖期
	* @param lotteryId
	* @return
	* @throws Exception
	*/
	GameIssue getMinIssueNotReciveResultCode(Long lotteryId,Long userId) throws Exception;
	GameIssue getMaxIssueNotReciveResultCode(Long lotteryId, Long userId) throws Exception;

	/**
	 * 
	* @Title: getMaxGameIssuesByLotteryId 
	* @Description: 根据奖期获取奖期信息
	* @param issueCode
	* @return
	* @throws Exception
	 */
	public GameIssue getMaxGameIssuesByLotteryId(Long lotteryId) throws Exception;

	/** 
	 * 假如issuecode为null，则根据当前时间获取当前奖期，假如当前时间段没没有任何奖期则获取下一条即将开奖奖期
	 * @param lotteryid 彩种id
	 * @param issueCode 奖期
	 * @return
	 * @throws Exception
	 */
	public GameIssueEntity queryGameIssue(Long lotteryid, Long issueCode) throws Exception;

	/** 
	* @Title: queryGameIssue 
	* @Description:查询奖期数据 
	* @param lotteryid 彩种id
	* @param webIssueCode web显示期号
	* @return
	* @throws Exception
	*/
	public GameIssueEntity queryGameIssue(Long lotteryid, String webIssueCode) throws Exception;

	/** 
	* @Title: queryGameIssues 
	* @Description: 查询奖期list 
	* @param pr
	* @return
	*/
	public Page<GameIssueEntity> queryGameIssues(PageRequest<GameIssueListQueryRequest> pr,Date takeOffTime);

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
	* @param lotteryId
	* @param maxCountIssue
	* @return
	*/
	public List<GameIssueEntity> queryTraceGameIssues(Long lotteryId, Integer maxCountIssue);

	//	/** 
	//	* @Title: generateGameIssue 
	//	* @Description: 生成该彩种的未来奖期 
	//	* @param lotteryid
	//	*/
	//	public void generateGameIssue(Long lotteryid);

	/**
	 * 
	* @Title: getGameIssueByLotteryIdAndStatus 
	* @Description: 获取未结束的奖期信息
	* @param lotteryId
	* @return
	* @throws Exception
	 */
	public List<GameIssueEntity> getGameIssueByLotteryIdAndStatus(Long lotteryId) throws Exception;

	/** 
	* @Title: getNextGameIssueByIssueAndLotteryId 
	* @Description: 获取下一奖期
	* @param @param lotteryId
	* @param @param issueCode
	* @param @return    设定文件 
	* @return GameIssue    返回类型 
	* @throws 
	*/
	public GameIssue getNextGameIssueByIssueAndLotteryId(Long lotteryId, Long issueCode);

	/** 
	* @Title: getEarlierSuspendedGameIssue 
	* @Description: 获取期号更小的暂停奖期 
	*/
	public List<GameIssue> getEarlierSuspendedGameIssue(Long lotteryid, Long issueCode);

	/** 
	* @Title: queryGameWarnIssueAuditList 
	* @Description: 审核奖期列表全部奖期查询
	* @param pageRequest
	* @return
	*/
	public Page<GameExceptionAuditOrder> queryGameWarnIssueAuditList(
			PageRequest<GameExceptionAuditRequestDTO> pageRequest);

	/** 
	* @Title: queryGameWarnIssueToAuditList 
	* @Description: 审核奖期列表待处理奖期查询 
	* @param pageRequest
	* @return
	*/
	public Page<GameExceptionAuditOrder> queryGameWarnIssueToAuditList(
			PageRequest<GameExceptionAuditRequestDTO> pageRequest);

	/** 
	* @Title: queryGameWarnIssueAuditedList 
	* @Description: 审核奖期列表已完成奖期查询  
	* @param pageRequest
	* @return
	*/
	public Page<GameExceptionAuditOrder> queryGameWarnIssueAuditedList(
			PageRequest<GameExceptionAuditRequestDTO> pageRequest);
	
	/**
	 * 查詢彩種ID目前獎期之前(不含當期)的獎期資料，並依據獎期做降冪排序。
	 * @param lotteryid 彩種ID
	 * @param issueCode 當前獎期
	 * @param count 回傳筆數
	 * @return
	 */
	public List<GameIssueEntity> queryPreGameIssue(Long lotteryid, Long issueCode, Long count);

	/**
	 * 查詢彩種ID目前獎期之後(不含當期)的獎期資料，並依據獎期做升冪排序。
	 * @param lotteryid 彩種ID
	 * @param issueCode 當前獎期
	 * @param count 回傳筆數
	 * @return
	 */
	public List<GameIssueEntity> queryNextGameIssue(Long lotteryid, Long issueCode, Long count);

	public GameIssue getGameIssueByIssueCodeAndLottery(Long lotteryId, Long issueCode);

	List<GameIssueEntity> getGameIssues(Long lotteryId, String issueStartTime, String issueEndTime);

	/**
	 * 取得狀態>2(結束銷售)且獎期過程狀態不為2(待開獎)且有開獎號碼的最大獎期號碼(issueCode)資料。
	 * @param lotteryId 彩種ID
	 * @return
	 */
	public GameIssueEntity getLastDrawGameIssue(Long lotteryId);

	public List<GameIssueEntity> queryGameIssues(GameIssueListQueryRequest gameIssueListQueryRequest,Date takeOffTime);

	public GameIssueManualGenerateResponse manualGenerateIssues(GameIssueManualGenerateRequest res,Date takeOffTime) throws Exception;

	void updateTry(Long count, Long id);

	public GameIssueManualGenerateResponse manualDeleteIssues(int type, String start, String end,Long lotteryId) throws Exception;
	
	public void updateGameIssueScheduleStopTime(Long lotteryId,Integer scheduleStopTime) throws Exception;
	
	public String getStartWebIssueCode(Long lotteryId, Long startTime) throws Exception;

	List<GameIssueEntity> getBackGameIssuesByLotteryId(Long lotteryId);
	
	public List<GameIssue> getGameIssueNumberRecord(Long lotteryId) throws Exception;
	
	public boolean getGameIssueIsOpenAward(Long lotteryId) throws Exception;
	
	public String getGameIssueLastWebIssueCode(Long lotteryId,Date date) throws Exception;
	
	public GameIssue getGameIssueLastByDate(Long lotteryId,Date date) throws Exception;
	
	public GameIssue queryGameIssue(Map<String, Object> map) throws Exception;
	
	public GameIssue queryNextGameIssue(Map<String, Object> map)throws Exception;
	
	public GameIssue queryPreviousGameIssue(Map<String, Object> map)throws Exception;
	
	public void updateIssuseCodeAfterExtendGameIssue(Map<String, Object> map)throws Exception;
	
	public GameIssue getGameIssueByLotteryIssue(Long lotteryId, Long issueCode) throws Exception;
	
	public void updateGameIssue(GameIssue gameIssue) throws Exception;
	
	/**
	 * 依據彩種代碼取回指定筆數的歷史開獎資料。
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<GameIssue> getHistoryLotteryAward(HistoryLotteryAwardRequest request) throws Exception;
}
