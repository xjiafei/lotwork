/**   
* @Title: IWithdrawRiskIdentifierService.java 
* @Package com.winterframework.firefrog.fund.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-12-12 下午1:37:35 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service;

import com.winterframework.firefrog.fund.entity.FundWithdrawOrder;
import com.winterframework.firefrog.fund.entity.FundWithdrawOrder.RiskType;

/** 
* @ClassName: IWithdrawRiskIdentifierService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-12-12 下午1:37:35 
*  
*/
public interface IWithdrawRiskIdentifierService {
	public RiskType queryRiskType(FundWithdrawOrder withdraw) throws Exception;
}
