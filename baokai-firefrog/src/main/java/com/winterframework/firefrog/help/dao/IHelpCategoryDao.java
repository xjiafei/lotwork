package com.winterframework.firefrog.help.dao;

import java.util.List;

import com.winterframework.firefrog.help.entity.HelpCategory;

public interface IHelpCategoryDao {

	/**
	 * 
	* @Title: queryHelpCategorys 
	* @Description:查询类目
	* @return List
	* @throws Exception
	 */
	public List<HelpCategory> queryHelpCategorys(HelpCategory helpCategory) throws Exception;

	/**
	 * 
	* @Title: createHelpCategory 
	* @Description:创建类目
	* @return int
	* @throws Exception
	 */
	public HelpCategory createHelpCategory(HelpCategory helpCategory) throws Exception;

	/**
	 * 
	* @Title: updateNo 
	* @Description:修改序号
	* @return int
	* @throws Exception
	 */
	public int update(HelpCategory helpCategory) throws Exception;

	/**
	 * 
	* @Title: deleteHelpCategory 
	* @Description:删除类目
	* @return int
	* @throws Exception
	 */
	public int deleteHelpCategory(Long id) throws Exception;

	/**
	 * 
	* @Title: getNo 
	* @Description:查询最大序号
	* @return Long
	* @throws Exception
	 */
	public Long getNo(HelpCategory helpCategory) throws Exception;
}
