package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.game.entity.GameIssueRuleEntity;

public interface IGameIssueRuleService {

	/** 
	* @Title: addOrUpdateSpecialGameIssueRule 
	* @Description: 新增或修改特例奖期规则，注意新增和修改时要考虑的问题 
	* @param gameIssueRuleEntity
	 * @throws Exception 
	*/
	public void addOrUpdateCommenOrSpecialGameIssueRule(GameIssueRuleEntity gameIssueRuleEntity, Integer oprationType) throws Exception;

	/** 
	* @Title: addOrUpdateStopGameIssueRule 
	* @Description:  新增或修改修市奖期规则，注意新增和修改时要考虑的问题 
	* @param gameIssueRuleEntity
	* @param operationType
	*/
	public void addOrUpdateStopGameIssueRule(GameIssueRuleEntity gameIssueRuleEntity, Integer operationType)
			throws Exception;

	/** 
	* @Title: cancelGameIssueRule 
	* @Description: 取消奖期规则 
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
	* @Title: auditGameIssueRule 
	* @Description: 审核奖期规则，注意当是修改的审核是覆盖正式表中的内容，审核完成后删除check表中的数据
	* @param lotteryId
	* @param ruleId
	* @param checkType
	* @param checkResult
	*/
	public void auditGameIssueRule(Long lotteryId, Long ruleId, Integer checkType, Integer checkResult)
			throws Exception;

}
