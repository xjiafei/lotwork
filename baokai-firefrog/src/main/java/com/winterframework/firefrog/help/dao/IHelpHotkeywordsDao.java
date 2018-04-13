package com.winterframework.firefrog.help.dao;

import java.util.List;

import com.winterframework.firefrog.help.entity.HelpHotkeywords;

/**
 * 
* @ClassName: IHelpHotkeywordsDao 
* @Description: 热门关键词操作
* @author Floy
* @date 2013-9-18 
*
 */
public interface IHelpHotkeywordsDao {

	/**
	 * 
	* @Title: getAllHelpHotkeywords 
	* @Description:查询所有热门关键字
	* @return List
	* @throws Exception
	 */
	public List<HelpHotkeywords> getAllHelpHotkeywords() throws Exception;

	/**
	 * 
	* @Title: updateHotkeyword 
	* @Description:修改关键词名称
	* @return int
	* @throws Exception
	 */
	public int updateHotkeyword(HelpHotkeywords helpHotkeyword) throws Exception;
}
