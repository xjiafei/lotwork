package com.winterframework.firefrog.fund.dao;

import com.winterframework.firefrog.fund.dao.vo.FundMowPay;

public interface IFundMowPayDao {
	
	public void saveMowPay(FundMowPay mowPay) throws Exception;
	
	public void updateMowPay(FundMowPay mowPay) throws Exception;

}