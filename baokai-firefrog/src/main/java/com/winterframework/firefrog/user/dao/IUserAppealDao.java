package com.winterframework.firefrog.user.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.winterframework.firefrog.user.entity.Appeal;
import com.winterframework.firefrog.user.entity.UserAppealQueryDTO;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

public interface IUserAppealDao {

	/** 
	* @Title: saveUserAppeal 
	* @Description:  保存账户申诉信息 
	* @param appeal
	* @throws JsonProcessingException
	*/
	public void saveUserAppeal(Appeal appeal) throws JsonProcessingException;

	/** 
	* @Title: searchUserAppeal 
	* @Description: 查询用户申诉信息 
	* @param pageReqeust
	* @return
	* @throws Exception
	*/
	public Page<Appeal> searchUserAppeal(PageRequest<UserAppealQueryDTO> pageReqeust) throws Exception;

	/** 
	* @Title: getUserAppealDetail 
	* @Description: 根据申述id获取申述详情 
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
