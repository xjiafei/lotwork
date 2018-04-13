package com.winterframework.firefrog.game.dao;

import java.util.Date;

import com.winterframework.firefrog.game.entity.GameIssueRuleEntity;

public interface IGameIssueRuleCheckDao {

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
	public GameIssueRuleEntity getSpecialOrStopGameIssueRuleByRuleTypeAndDayWithUnAuditRule(Long lotteryId,
			Date beginOfDate, Date endOfDate, int dayValue);

}
