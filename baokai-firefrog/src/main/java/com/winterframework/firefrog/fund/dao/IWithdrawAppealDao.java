/**   
* @Title: INoticeTaskDao.java 
* @Package com.winterframework.firefrog.notice.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-28 上午9:41:56 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.dao;

import java.util.List;

import com.winterframework.firefrog.fund.entity.FundWithdrawOrder;
import com.winterframework.firefrog.fund.entity.WithdrawAppeal;
import com.winterframework.firefrog.fund.util.ISNGenerator;
import com.winterframework.firefrog.fund.web.dto.WithdrawAppealStruc;
import com.winterframework.firefrog.fund.web.dto.WithdrawAuditRequest;
import com.winterframework.modules.web.jsonresult.Request;

/** 
* @ClassName: INoticeTaskDao 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-28 上午9:41:56 
*  
*/
public interface IWithdrawAppealDao {	

	public List<WithdrawAppeal> queryWithdrawAppeal(Request<WithdrawAppealStruc> request) throws Exception;
	
	public List<WithdrawAppeal> queryAppeal(Request<WithdrawAppealStruc> request) throws Exception;
	
	public List<WithdrawAppeal> updateAppealByWithdrawSn(Request<WithdrawAppealStruc> request) throws Exception;
	
	public List<WithdrawAppeal> updateAppealStatus(Request<WithdrawAppealStruc> request) throws Exception;
	
	public List<WithdrawAppeal> queryAppealbySn(Request<WithdrawAppealStruc> request) throws Exception;
	
	public List<WithdrawAppeal> createAppealSn(FundWithdrawOrder entity,Request<WithdrawAuditRequest> request,ISNGenerator snUtil) throws Exception;

	public Long queryUncheckAppealCount();
}
