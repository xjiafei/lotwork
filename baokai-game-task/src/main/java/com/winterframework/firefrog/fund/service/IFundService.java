package com.winterframework.firefrog.fund.service;

import com.winterframework.firefrog.fund.entity.FundOrder;
import com.winterframework.firefrog.fund.entity.UserFund;
import com.winterframework.firefrog.fund.exception.FundChangedException;

public interface IFundService<T extends FundOrder> {
	/** 
	* @Title: getUserFund 
	* @Description: 获取用户资金信息
	* @param userId 用户id
	* @return
	*/
	public UserFund getUserFund(Long userId);
 
	public void updateFund(T fundOrder) throws FundChangedException;


}
