package com.winterframework.firefrog.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.winterframework.firefrog.user.entity.Appeal;
import com.winterframework.firefrog.user.entity.UserAppealQueryDTO;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

public interface IAppealService {

	/** 
	* @Title: saveUserAppeal 
	* @Description: 保存帐号申诉信息 
	* @param appeal
	* @throws JsonProcessingException
	* @throws Exception
	*/
	public void saveUserAppeal(Appeal appeal) throws JsonProcessingException, Exception;

	/** 
	* @Title: searchUserAppeal 
	* @Description:  根据查询条件查询用户帐号申述列表 
	* @param pageReqeust
	* @return
	* @throws Exception
	*/
	public Page<Appeal> searchUserAppeal(PageRequest<UserAppealQueryDTO> pageReqeust) throws Exception;

	/** 
	* @Title: getUserAppealDetail 
	* @Description: 根据申述id查询申述详情  
	* @param userAppleId
	* @return
	* @throws Exception
	*/
	public Appeal getUserAppealDetail(int userAppleId) throws Exception;

	/** 
	* @Title: userAppealAudit 
	* @Description: 审核申诉信息 
	* @param appeal
	* @throws Exception
	*/
	public void userAppealAudit(Appeal appeal) throws Exception;
}
