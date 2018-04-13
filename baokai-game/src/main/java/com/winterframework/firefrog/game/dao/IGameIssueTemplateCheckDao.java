package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameIssueTemplateCheck;
import com.winterframework.firefrog.game.entity.GameIssueTemplateEntity;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IGameIssueTemplateCheckDao extends BaseDao<GameIssueTemplateCheck> {
    /** 
    * @Title: getGameIssueTemplateByRuleId 
    * @Description: 根据规则id获取规则模版
    * @param ruleId
    * @return
    */
    public List<GameIssueTemplateEntity> getGameIssueTemplateCheckByRuleId(Long ruleId);
    
    public void insertTemplate(GameIssueTemplateCheck gameIssueTemplateCheck);

	/** 
	* @Title: deleteCheckRecordByRuleId 
	* @Description: 审核后删除规则记录对应的贩毒案记录表 
	* @param ruleId
	*/
	public void deleteCheckRecordByRuleId(Long ruleId);
	
	public void updateCommonRuleTemplateCheck(Long lotteryId, Integer scheduleStopTime) throws Exception;
}
