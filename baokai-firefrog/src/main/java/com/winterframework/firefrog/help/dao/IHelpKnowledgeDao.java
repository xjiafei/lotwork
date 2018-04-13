package com.winterframework.firefrog.help.dao;

import java.util.List;

import com.winterframework.firefrog.help.entity.HelpKnowledge;

/**
 * 
* @ClassName: IHelpKnowledgeDao 
* @Description: 彩种知识目录
* @author Floy
* @date 2013-9-18 
*
 */
public interface IHelpKnowledgeDao {

	/**
	 * 
	* @Title: add 
	* @Description:添加彩种知识目录
	* @return HelpKnowledge
	* @throws Exception
	 */
	public HelpKnowledge add(HelpKnowledge helpKnowledge) throws Exception;

	/**
	 * 
	* @Title: updateNo 
	* @Description:修改序号
	* @return int
	* @throws Exception
	 */
	public int update(HelpKnowledge helpKnowledge) throws Exception;

	/**
	 * 
	* @Title: delete 
	* @Description:删除彩种知识
	* @return int
	* @throws Exception
	 */
	public int delete(Long id) throws Exception;

	/**
	 * 
	* @Title: queryAll 
	* @Description:查询彩种知识
	* @return List
	* @throws Exception
	 */
	public List<HelpKnowledge> queryAll() throws Exception;

	/**
	 * 
	* @Title: getById 
	* @Description:查询彩种知识目录
	* @return HelpKnowledge
	* @throws Exception
	 */
	public HelpKnowledge getHelpKnowledgeById(Long id) throws Exception;

	/**
	 * 
	* @Title: getNo 
	* @Description:获取最大的序号
	* @return Long
	* @throws Exception
	 */
	public Long getNo() throws Exception;

}
