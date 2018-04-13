package com.winterframework.firefrog.game.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameIssueTemplateCheckDao;
import com.winterframework.firefrog.game.dao.vo.GameIssueTemplateCheck;
import com.winterframework.firefrog.game.dao.vo.VOConverter;
import com.winterframework.firefrog.game.entity.GameIssueTemplateEntity;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameIssueTemplateCheckDaoImpl")
public class GameIssueTemplateCheckDaoImpl extends BaseIbatis3Dao<GameIssueTemplateCheck> implements
		IGameIssueTemplateCheckDao {

	@Override
	public List<GameIssueTemplateEntity> getGameIssueTemplateCheckByRuleId(Long ruleId) {
		List<GameIssueTemplateCheck> gameIssueTemplateChecks = this.sqlSessionTemplate.selectList(
				"getGameIssueTemplateCheckByRuleId", ruleId);
		List<GameIssueTemplateEntity> gameIssueTemplateEntitys = new ArrayList<GameIssueTemplateEntity>();
		if (!gameIssueTemplateChecks.isEmpty() && gameIssueTemplateChecks.size() != 0) {
			for (GameIssueTemplateCheck gameIssueTemplateCheck : gameIssueTemplateChecks) {
				GameIssueTemplateEntity gite = VOConverter
						.gameIssueTemplateCheck2GameIssueTemplateEntity(gameIssueTemplateCheck);
				gameIssueTemplateEntitys.add(gite);
			}
		}
		return gameIssueTemplateEntitys;
	}

	@Override
	public void insertTemplate(GameIssueTemplateCheck gameIssueTemplateCheck) {
		this.insert(gameIssueTemplateCheck);
	}

	@Override
	public void deleteCheckRecordByRuleId(Long ruleCheckId) {
		this.sqlSessionTemplate.delete("deleteTemplateRecordByRuleId", ruleCheckId);

	}

	@Override
	public void updateCommonRuleTemplateCheck(Long lotteryId, Integer scheduleStopTime) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("scheduleStopTime", Long.valueOf(scheduleStopTime));
		this.sqlSessionTemplate.update("updateCommonRuleTemplateCheck", map);
	}

}
