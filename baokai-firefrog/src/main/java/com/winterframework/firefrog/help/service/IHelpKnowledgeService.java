package com.winterframework.firefrog.help.service;

import java.util.List;

import com.winterframework.firefrog.help.entity.HelpKnowledge;

public interface IHelpKnowledgeService {

	/**
	 * 
	* @Title: queryAllHelpKnowledge 
	* @Description:查询彩种知识
	* @return List
	* @throws Exception
	 */
	public List<HelpKnowledge> queryAllHelpKnowledge() throws Exception;

	/**
	 * 
	* @Title: updateHelpKnowledge 
	* @Description:修改彩种知识目录
	* @return int
	* @throws Exception
	 */
	public int updateHelpKnowledge(HelpKnowledge helpKnowledge) throws Exception;

	/**
	 * 
	* @Title: deleteHelpKnowledge 
	* @Description:删除彩种知识
	* @return int
	* @throws Exception
	 */
	public void deleteHelpKnowledge(Long id) throws Exception;

	/**
	 * 
	* @Title: addHelpKnowledge 
	* @Description:添加彩种知识目录
	* @return HelpKnowledge
	* @throws Exception
	 */
	public HelpKnowledge addHelpKnowledge(HelpKnowledge helpKnowledge) throws Exception;

}
