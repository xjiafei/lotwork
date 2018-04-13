package com.winterframework.firefrog.game.dao;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.game.entity.GameIssueRuleEntity;

public interface IGameIssueRuleCheckDao {

	/** 
	* @Title: queryGameIssueRuleCheckByLotteryIdAndRuleId 
	* @Description: 根据彩种id和奖期id获取审核规则列表 包括已审核规则表中的规则
	* @param lotteryId
	* @param ruleId
	* @return
	*/
	public List<GameIssueRuleEntity> queryGameIssueRuleCheckByLotteryIdAndRuleId(long lotteryId, Long ruleId);

	/** 
	* @Title: queryGameIssueRuleCheckByLotteryIdAndRuleId 
	* @Description: 根据彩种id和时间段获取特殊审核规则 包括已审核规则表中的规则
	* @param lotteryId
	* @param ruleId
	* @return
	*/
	public List<GameIssueRuleEntity> querySpecialGameIssueRuleCheckByLotteryIdAndPeriodTime(Long lotteryId,
			Date fromDate, Date toDate, Long ruleId);

	/** 
	* @Title: saveSpecialGameIssueRule 
	* @Description: 保存常规或特例奖期规则 
	* @param gameIssueRuleEntity
	*/
	public Long saveCommonOrSpecialGameIssueRule(GameIssueRuleEntity gameIssueRuleEntity);

	/**
	* @Title: updateGameIssueRuleCheck 
	* @Description: 保存奖期规则 
	* @param gameIssueRuleEntity
	 */
	public void updateGameIssueRuleCheck(GameIssueRuleEntity gameIssueRuleEntity);

	/** 
	* @Title: updateSpecialGameIssuseRule 
	* @Description: 修改常规或特例奖期规则
	* @param gameIssueRuleEntity
	*/
	public void updateCommonOrSpecialGameIssuseRule(GameIssueRuleEntity gameIssueRuleEntity);

	/** 
	* @Title: saveStopGameIssueRule 
	* @Description: 新增休市奖期规则
	* @param gameIssueRuleEntity
	*/
	public void saveStopGameIssueRule(GameIssueRuleEntity gameIssueRuleEntity);

	/** 
	* @Title: updateStopGameIssuseRule 
	* @Description:修改休市奖期规则
	* @param gameIssueRuleEntity
	*/
	public void updateStopGameIssuseRule(GameIssueRuleEntity gameIssueRuleEntity);

	/** 
	* @Title: getGameIssueRuleCheckByLotteryIdAndRuleId 
	* @Description: 根据彩种id和规则id获取规则记录 
	* @param lotteryId
	* @param ruleId
	* @return
	*/
	public GameIssueRuleEntity getGameIssueRuleCheckByLotteryIdAndRuleId(Long lotteryId, Long ruleId);

	/** 
	* @Title: deleteCheckRecordByRuleId 
	* @Description:审核后删除check规则记录 
	* @param ruleId
	*/
	public void deleteCheckRecordByRuleId(Long ruleId);

	/** 
	* @Title: getCommenGameIssueRuleByRuleTypeAndDayWithUnAuditRule 
	* @Description: 获取包含未审核的普通规则，由于系统中只能有一条普通规则，假如含有未审核的普通规则，则判断该规则是否还具有审核时效，即生效时间要在后天24：00:00  即大后天00:00:00 
	* @param lotteryId 彩种id
	* @param beginOfDate 某天起始时间
	* @param endOfDate 某天结束时间
	* @param dayValue 星期几对应的值
	* @return
	*/
	public GameIssueRuleEntity getCommenGameIssueRuleByRuleTypeAndDayWithUnAuditRule(Long lotteryId, Date beginOfDate,
			Date endOfDate, int dayValue);

	/** 
	* @Title: getSpecialOrStopGameIssueRuleByRuleTypeAndDayWithUnAuditRule 
	* @Description: 获取包含未审核的特殊规则，都只需要获取具有审核时效性的待审核规则  即生效时间要在后天24：00:00  即大后天00:00:00 
	* @param lotteryId 彩种id
	* @param beginOfDate 某天起始时间
	* @param endOfDate 某天结束时间
	* @param dayValue 星期几对应的值
	* @return
	*/
	@Deprecated
	public GameIssueRuleEntity getSpecialOrStopGameIssueRuleByRuleTypeAndDayWithUnAuditRule(Long lotteryId,
			Date beginOfDate, Date endOfDate, int dayValue);

	/** 
	* @Title: getSpecialOrStopGameIssueRuleByRuleTypeAndDayWithUnAuditRule 
	* @Description: 获取包含未审核的特殊规则，都只需要获取具有审核时效性的待审核规则  即生效时间要在后天24：00:00  即大后天00:00:00 
	* @param lotteryId 彩种id
	* @param beginOfDate 某天起始时间
	* @param endOfDate 某天结束时间
	* @param dayValue 星期几对应的值
	* @return
	*/
	public GameIssueRuleEntity getSpecialGameIssueRuleByRuleTypeAndDayWithUnAuditRule(Long lotteryId, Date beginOfDate,
			Date endOfDate, int dayValue);

	/** 
	* @Title: getSpecialOrStopGameIssueRuleByRuleTypeAndDayWithUnAuditRule 
	* @Description: 获取包含未审核的休市规则，都只需要获取具有审核时效性的待审核规则  即生效时间要在后天24：00:00  即大后天00:00:00 
	* @param lotteryId 彩种id
	* @param beginOfDate 某天起始时间
	* @param endOfDate 某天结束时间
	* @param dayValue 星期几对应的值
	* @return
	*/
	public List<GameIssueRuleEntity> getStopGameIssueRuleByRuleTypeAndDayWithUnAuditRule(Long lotteryId,
			Date beginOfDate, Date endOfDate, int dayValue);

	/** 
	* @Title: getCommenGameIssuesByMainRuleId 
	* @Description: 根据主表id查询check表中 
	* @param mainRuleid
	* @return
	*/
	public List<GameIssueRuleEntity> getCommenGameIssuesByMainRuleId(Long mainRuleid);

	public GameIssueRuleEntity getGameIssueRuleCheckById(Long id);

	List<GameIssueRuleEntity> querySpecialGameIssueRuleCheckByLotteryAndTime(Long lotteryId, Date fromDate,
			Date toDate, Long ruleId);

	/** 
	* @Title: getCommenGameIssueRuleByLottery 
	* @Description: 根据彩种查询常规奖期
	*/
	public GameIssueRuleEntity getCommenGameIssueRuleByLottery(Long lotteryId, Integer day);

	/** 
	* @Title: getSpecialOrStopGameIssueRuleByRuleTypeAndDay 
	* @Description: 获取某个彩种在某天的特殊规则
	* @param lotteryId
	* @param beginOfDate 某天起始之间00:00:00
	* @param endOfDate 某天结束时间23:59:59
	* @param day 周一到周日对应的值
	* @return
	*/
	public GameIssueRuleEntity getSpecialGameIssueRuleByRuleTypeAndDay(Long lotteryId, Date beginOfDate,
			Date endOfDate, Integer day);

	/** 
	* @Title: getSpecialOrStopGameIssueRuleByRuleTypeAndDay 
	* @Description: 获取某个彩种在某天的休市规则 休市规则可能一天存在多条
	* @param lotteryId
	* @param beginOfDate 某天起始之间00:00:00
	* @param endOfDate 某天结束时间23:59:59
	* @param day 周一到周日对应的值
	* @return
	*/
	public List<GameIssueRuleEntity> getStopGameIssueRuleByRuleTypeAndDay(Long lotteryId, Date beginOfDate,
			Date endOfDate, Integer day);
}
