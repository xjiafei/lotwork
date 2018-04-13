package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameIssueTemplate;
import com.winterframework.firefrog.game.entity.GameIssueTemplateEntity;

public interface IGameIssueTemplateDao {
	/** 
	* @Title: getGameIssueTemplateByRuleId 
	* @Description: 根据规则id获取规则模版
	* @param ruleId
	* @return
	*/
	public List<GameIssueTemplateEntity> getGameIssueTemplateByRuleId(Long ruleId);

	/** 
	* @Title: insertTemplate 
	* @Description: 保存分段时间规则表 
	* @param gameIssueTemplate
	*/
	public void insertTemplate(GameIssueTemplate gameIssueTemplate);

	/** 
	* @Title: deleteGameIssueTemplateByRuleId 
	* @Description: 审核修改时保存正式记录需要先删除原有的分段时间记录 
	* @param ruleId
	*/
	public void deleteGameIssueTemplateByRuleId(Long ruleId);
}
