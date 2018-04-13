package com.winterframework.firefrog.active.service;

import com.winterframework.firefrog.active.dao.vo.Activity;
import com.winterframework.firefrog.active.dao.vo.ActivityWinningLog;
import com.winterframework.firefrog.active.web.dto.ActivityAwardsApplyRequest;
import com.winterframework.firefrog.active.web.dto.ActivityAwardsRequest;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.modules.page.PageRequest;

import java.util.List;

/**
 * @author Marco.huang
 *
 */
public interface IActivityAwardsService {
	
	/** 
	* Title: queryActivityNameDate 
	* Description: 查詢活動資訊
	* return List<Activity>
	* throws Exception
	*/
	public List<Activity> queryActivityDetail() throws Exception;
	/** 
	* Title: queryWinningLogUntreat 
	* Description: 查詢未派獎的資訊
	* param  PageRequest<ActivityAwardsRequest> pageReq
	* return CountPage<ActivityWinningLog>
	* throws Exception
	*/
	public CountPage<ActivityWinningLog> queryWinningLogUntreat(PageRequest<ActivityAwardsRequest> pageReq) throws Exception;
	/** 
	* Title: queryWinningLogTreat 
	* Description: 查詢已派獎的資訊
	* param  PageRequest<ActivityAwardsRequest> pageReq
	* return CountPage<ActivityWinningLog>
	* throws Exception
	*/
	public CountPage<ActivityWinningLog> queryWinningLogTreat(PageRequest<ActivityAwardsRequest> pageReq) throws Exception;
	/** 
	* Title: awardsAppy 
	* Description: 派發獎金
	* param  List<ActivityAwardsApplyRequest> list
	* param  Approver 管理員帳號
	* return Long
	* throws Exception
	*/
	public Long awardsAppy(List<ActivityAwardsApplyRequest> list,String approver) throws Exception;
	/** 
	* Title: addAwardList
	* Description: 寫入需派獎名單
	* param  ActivityAwardsRequest request
	* param  Approver 管理員帳號
	* return String
	* throws Exception
	*/
	public String addAwardList(ActivityAwardsRequest request) throws Exception;

}
