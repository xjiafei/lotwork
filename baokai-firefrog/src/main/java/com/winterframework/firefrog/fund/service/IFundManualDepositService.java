/**   
* @Title: IFundManualDepositService.java 
* @Package com.winterframework.firefrog.fund.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-29 下午4:12:28 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service;

import com.winterframework.firefrog.fund.entity.FundManualOrder;
import com.winterframework.firefrog.fund.web.dto.DepositAuditRequest;
import com.winterframework.firefrog.fund.web.dto.DepositQueryRequest;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName: IFundManualDepositService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Tod
* @date 2013-7-29 下午4:12:28 
*  
*/
public interface IFundManualDepositService {

	public Page<FundManualOrder> query(PageRequest<DepositQueryRequest> pageReq) throws Exception;

	public void remark(Long id, String remark) throws Exception;
	public void apply(FundManualOrder deposit) throws Exception;
	//public FundManualDepositOrder preCreate(FundManualDepositOrder deposit) throws Exception;
	public void audit(User user, DepositAuditRequest request) throws Exception;
	//public FundManualDepositOrder preAudit(User user, DepositAuditRequest request) throws Exception;
}
