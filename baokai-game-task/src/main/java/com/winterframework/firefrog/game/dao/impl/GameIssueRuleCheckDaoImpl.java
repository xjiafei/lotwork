package com.winterframework.firefrog.game.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.IGameIssueRuleCheckDao;
import com.winterframework.firefrog.game.dao.vo.GameIssueRuleCheck;
import com.winterframework.firefrog.game.dao.vo.VOConverter4Task;
import com.winterframework.firefrog.game.entity.GameIssueRuleEntity;
import com.winterframework.firefrog.game.entity.GameIssueRuleEntity.RuleType;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameIssueRuleCheckDaoImpl")
public class GameIssueRuleCheckDaoImpl extends BaseIbatis3Dao<GameIssueRuleCheck> implements IGameIssueRuleCheckDao {

	@Override
	public GameIssueRuleEntity getCommenGameIssueRuleByRuleTypeAndDayWithUnAuditRule(Long lotteryId, Date tempFromDate,
			Date tempToDate, int dayValue) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("fromDate", tempFromDate);
		map.put("toDate", tempToDate);
		map.put("ruleType", RuleType.COMMEN.getValue());
		map.put("day", dayValue + "");
		Date date = DateUtils.getStartTimeOfDate(DateUtils.addDays(new Date(),
				(int) GameIssueRuleEntity.AUDIT_LIMIT_DAY));
		map.put("auditLimitDate", date);
		GameIssueRuleCheck girs = this.sqlSessionTemplate.selectOne(
				"getCommenGameIssueRuleByRuleTypeAndDayWithUnAuditRule", map);
		GameIssueRuleEntity gire = VOConverter4Task.gameIssueRuleCheck2GameIssueRuleEntity(girs);
		return gire;
	}

	@Override
	public GameIssueRuleEntity getSpecialOrStopGameIssueRuleByRuleTypeAndDayWithUnAuditRule(Long lotteryId,
			Date tempFromDate, Date tempToDate, int dayValue) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("fromDate", tempFromDate);
		map.put("toDate", tempToDate);
		map.put("day", dayValue + "");
		Date date = DateUtils.getStartTimeOfDate(DateUtils.addDays(new Date(),
				(int) GameIssueRuleEntity.AUDIT_LIMIT_DAY));
		map.put("auditLimitDate", date);
		GameIssueRuleCheck girs = this.sqlSessionTemplate.selectOne(
				"getSpecialOrStopGameIssueRuleByRuleTypeAndDayWithUnAuditRule", map);
		GameIssueRuleEntity gire = new GameIssueRuleEntity();
		if (girs != null) {
			gire = VOConverter4Task.gameIssueRuleCheck2GameIssueRuleEntity(girs);
		}
		return gire;
	}

}
