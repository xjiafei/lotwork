package com.winterframework.firefrog.help.service;

import java.util.List;

import com.winterframework.firefrog.help.entity.HelpCategory;

public interface IHelpCategoryService {
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
}
