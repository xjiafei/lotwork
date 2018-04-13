package com.winterframework.firefrog.help.service;

import java.util.List;

import com.winterframework.firefrog.help.entity.Help;
import com.winterframework.firefrog.help.entity.HelpFeedback;
import com.winterframework.firefrog.help.web.dto.HelpQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/**
 * 
* @ClassName: IHelpService 
* @Description: 帮助业务接口
* @author 你的名字 
* @date 2013-9-24 下午1:40:41 
*
 */
public interface IHelpService {

	/**
	 * 
	* @Title: createHelp 
	* @Description: 创建帮助
	* @param help
	* @throws Exception
	 */
	public void createHelp(Help help) throws Exception;

	/**
	 * 
	* @Title: modifyHelp 
	* @Description: 修改帮助
	* @param help
	* @throws Exception
	 */
	public void modifyHelp(Help help) throws Exception;

	/**
	 * 
	* @Title: deleteHelp 
	* @Description: 删除帮助
	* @param id
	* @throws Exception
	 */
	public void deleteHelp(Long id) throws Exception;

	/**
	 * 
	* @Title: queryHelpDetail 
	* @Description: 查询帮助详情
	* @param id
	* @return
	* @throws Exception
	 */
	public Help queryHelpDetail(Long id) throws Exception;

	/**
	 * 
	* @Title: queryHelp 
	* @Description: 根据条件查询帮助
	* @param pageRequest
	* @return
	* @throws Exception
	 */
	public Page<Help> queryHelp(PageRequest<HelpQueryRequest> pageRequest) throws Exception;

	/**
	 * 
	* @Title: createFeedback 
	* @Description: 创建帮助反馈
	* @param feedback
	* @throws Exception
	 */
	public void createFeedback(HelpFeedback feedback) throws Exception;
	
	/**
	 * 
	* @Title: queryUnsolvedFeedback 
	* @Description: 查询未解决的帮助
	* @param List
	* @throws Exception
	 */
	public List<HelpFeedback> queryUnsolvedFeedback(Long helpId) throws Exception;

	/** 
	 * 修改帮助的浏览总数
	 * @throws Exception 
	*/
	public void updateBrowsenum(Help help) throws Exception;
}
