package com.winterframework.firefrog.game.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.game.entity.GameIssueRuleEntity;

public interface IGameIssueRuleDao {
	
	
	//约定：某一天的奖期规则只能存在一条进行中有效的特列规则，只能存在一条普通规则
	
	
	/** 
	* @Title: queryGameIssueRuleByRuleTypeAndDay 
	* @Description: 获取某个彩种在某天的普通规则
	* @param lotteryId
	* @param beginOfDate 某天起始之间00:00:00
	* @param endOfDate 某天结束时间23:59:59
	* @param day 周一到周日对应的值
	* @return
	*/
	public GameIssueRuleEntity getCommenGameIssueRuleByRuleTypeAndDay(Long lotteryId,Date beginOfDate,Date endOfDate,Integer day);
	
	
	/** 
	* @Title: getSpecialOrStopGameIssueRuleByRuleTypeAndDay 
	* @Description: 获取某个彩种在某天的特殊规则包括休市
	* @param lotteryId
	* @param beginOfDate 某天起始之间00:00:00
	* @param endOfDate 某天结束时间23:59:59
	* @param day 周一到周日对应的值
	* @return
	*/
	@Deprecated
	public GameIssueRuleEntity getSpecialOrStopGameIssueRuleByRuleTypeAndDay(Long lotteryId,Date beginOfDate,Date endOfDate,Integer day);


	
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

	/** 
	* @Title: queryGameIssueRuleByLotteryIdAndRuleId 
	* @Description: 根据彩种id和规则id查询奖期规则
	* @param lotteryId
	* @param ruleId
	* @return
	*/
	public List<GameIssueRuleEntity> queryGameIssueRuleByLotteryIdAndRuleId(long lotteryId, Long ruleId);


	/** 
	* @Title: cancelGameIssueRule 
	* @Description: 取消捡起规则 
	* @param lotteryId
	* @param ruleId
	*/
	public void cancelGameIssueRule(Long lotteryId, Long ruleId);


	/** 
	* @Title: deleteGameIssueRule 
	* @Description: 删除奖期规则
	* @param lotteryId
	* @param ruleId
	*/
	public void deleteGameIssueRule(Long lotteryId, Long ruleId);


	/** 
	* @Title: saveGameIssueRule 
	* @Description: 审核奖期规则后将规则保存到正式表 
	* @param gire
	*/
	public void saveGameIssueRule(GameIssueRuleEntity gire);


	/** 
	* @Title: getGameIssueRuleById 
	* @Description: 根据id获取规则记录 
	* @param ruleid
	* @return
	*/
	public GameIssueRuleEntity getGameIssueRuleById(Long ruleid);


	/** 
	* @Title: updateGameIssueRule 
	* @Description: 审核修改记录覆盖gameIssue 
	* @param gameIssueRuleCheck
	*/
	public void updateGameIssueRule(GameIssueRuleEntity gameIssueRuleCheck);

	/** 
	* @Title: getCommenGameIssueRuleByLottery 
	* @Description: 根据彩种查询常规奖期
	*/
	public GameIssueRuleEntity getCommenGameIssueRuleByLottery(Long lotteryId,
			Integer day,Date fromDate);
	
	/**
	 * 撈取某個取間是否有特殊獎期規則
	 * @param lotteryId
	 * @param beginOfDate
	 * @param endOfDate
	 * @param day
	 * @return
	 */
	public List<GameIssueRuleEntity> getSpecialGameIssueRuleByLotteryAndDate(Long lotteryId, Date beginOfDate,
			Date endOfDate);
	
	
	public List<GameIssueRuleEntity> queryGameIssueRuleByMap(Map<String, Object> map);
	
	
}
