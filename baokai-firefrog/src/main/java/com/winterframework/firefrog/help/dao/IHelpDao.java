package com.winterframework.firefrog.help.dao;

import com.winterframework.firefrog.help.entity.Help;
import com.winterframework.firefrog.help.web.dto.HelpQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

public interface IHelpDao {

	public Long saveHelp(Help help) throws Exception;

	public void updateHelp(Help help) throws Exception;

	public void deleteHelp(Long id) throws Exception;

	public Help selectById(Long id) throws Exception;

	public Page<Help> selectHelp(PageRequest<HelpQueryRequest> pageRequest) throws Exception;

	public void updateFeedBackCount(Help help) throws Exception ;

	/** 
	 * 修改帮助的浏览量
	*/
	public void updateBrowsenum(Help help) throws Exception;

}
