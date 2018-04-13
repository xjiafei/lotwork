package com.winterframework.firefrog.active.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.winterframework.firefrog.active.web.dto.ActivityVipRequest;
import com.winterframework.firefrog.user.dao.vo.VipActivityVo;

public interface IActivityVipService {

	/** 
	* @Title: saveUserAppeal 
	* @Description: 保存帐号申诉信息 
	* @param appeal
	* @throws JsonProcessingException
	* @throws Exception
	*/
	public Integer saveApplicaiton(ActivityVipRequest appeal) throws Exception;

	/**
	 * 獲取註冊活動訊息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public VipActivityVo getActivityInfo(ActivityVipRequest request) throws Exception;
}
