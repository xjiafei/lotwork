package com.winterframework.firefrog.game.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameIssueTemplateDao;
import com.winterframework.firefrog.game.dao.vo.GameIssueTemplate;
import com.winterframework.firefrog.game.dao.vo.VOConverter4Task;
import com.winterframework.firefrog.game.entity.GameIssueTemplateEntity;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameIssueTemplateDaoImpl")
public class GameIssueTemplateDaoImpl extends BaseIbatis3Dao<GameIssueTemplate> implements IGameIssueTemplateDao {

	@Override
	public List<GameIssueTemplateEntity> getGameIssueTemplateByRuleId(Long ruleId) {
		List<GameIssueTemplate> gameIssueTemplates = this.sqlSessionTemplate.selectList("getGameIssueTemplateByRuleId",
				ruleId);
		List<GameIssueTemplateEntity> gameIssueTemplateEntitys = new ArrayList<GameIssueTemplateEntity>();
		if (!gameIssueTemplates.isEmpty() && gameIssueTemplates.size() != 0) {
			for (GameIssueTemplate gameIssueTemplate : gameIssueTemplates) {
				GameIssueTemplateEntity gite = VOConverter4Task.gameIssueTemplate2GameIssueTemplateEntity(gameIssueTemplate);
				gameIssueTemplateEntitys.add(gite);
			}
		}
		return gameIssueTemplateEntitys;
	}

	@Override
	public void insertTemplate(GameIssueTemplate gameIssueTemplate) {
		this.insert(gameIssueTemplate);
	}

	@Override
	public void deleteGameIssueTemplateByRuleId(Long ruleId) {
		this.sqlSessionTemplate.delete("deleteGameIssueTemplateByRuleId", ruleId);
	}

}
