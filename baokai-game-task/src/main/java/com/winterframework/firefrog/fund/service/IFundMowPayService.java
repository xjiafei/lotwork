package com.winterframework.firefrog.fund.service;

import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.FundMowPay;

public interface IFundMowPayService {

	public List<FundMowPay> queryThPayOrder(Long status);
	public int updateFundMowPay(FundMowPay fundMowPay);
}
