/**   
* @Title: IWithdrawAppealService.java 
* @Package com.winterframework.firefrog.notice.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-28 上午11:36:35 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service;

import java.util.List;

import com.winterframework.firefrog.fund.entity.WithdrawAppeal;
import com.winterframework.firefrog.fund.web.dto.WithdrawAppealStruc;
import com.winterframework.firefrog.fund.web.dto.WithdrawAuditRequest;
import com.winterframework.modules.web.jsonresult.Request;


/** 
* @ClassName: IWithdrawAppealService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-28 上午11:36:35 
*  
*/
public interface IWithdrawAppealService {
	
	public List<WithdrawAppeal> queryWithdrawAppeal(Request<WithdrawAppealStruc> request) throws Exception;
	
	public List<WithdrawAppeal> queryAppeal(Request<WithdrawAppealStruc> request) throws Exception;
	
	public List<WithdrawAppeal> updateAppealByWithdrawSn(Request<WithdrawAppealStruc> request) throws Exception;
	
	public List<WithdrawAppeal> updateAppealStatus(Request<WithdrawAppealStruc> request) throws Exception;
	
	public List<WithdrawAppeal> queryAppealbySn(Request<WithdrawAppealStruc> request) throws Exception;
	
	public List<WithdrawAppeal> createAppealSn(Request<WithdrawAuditRequest> request) throws Exception;
	
	public Long queryUncheckAppeal()throws Exception;
}
