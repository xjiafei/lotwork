/**   
* @Title: IFundManualDepositAuditService.java 
* @Package com.winterframework.firefrog.fund.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-12-18 下午6:02:09 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service;

import com.winterframework.firefrog.fund.entity.FundManualOrder;

/** 
* @ClassName: IFundManualDepositAuditService 
* @Description: audit service 
* @author Page
* @date 2013-12-18 下午6:02:09 
*  
*/
public interface IFundManualDepositAuditService {
	public void audit(FundManualOrder deposit) throws Exception;
}
