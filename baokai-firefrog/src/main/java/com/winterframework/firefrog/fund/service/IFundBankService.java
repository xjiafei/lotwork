/**   
* @Title: IFundBankService.java 
* @Package com.winterframework.firefrog.fund.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-1 上午10:55:47 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service;

import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.FundBank;
import com.winterframework.firefrog.fund.dao.vo.FundBankWithSub;

/** 
* @ClassName: IFundBankService 
* @Description: 银行管理 Service
* @author Alan 
* @date 2013-7-1 上午10:55:47 
*  
*/
public interface IFundBankService {

	/**
	 * 
	* @Title: queryAllBank 
	* @Description: 查询所有银行记录
	* @return
	* @throws Exception
	 */
	public List<FundBank> queryAllBank() throws Exception;

	/**
	 * 
	* @Title: bankParamsSet 
	* @Description: 银行参数设置 
	* @param bank
	* @throws Exception
	 */
	public void updateBank(FundBank bank) throws Exception;
	
	public void saveBank(FundBank bank) throws Exception;
	
	public Boolean checkChargeOpen(Long bankId,Long userId,Long mode) throws Exception;
	
}
