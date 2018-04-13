package com.winterframework.firefrog.fund.dao;

import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.FundMowPay;

public interface IFundMowPayDao {

	public List<FundMowPay> queryThPayOrder(Long status);

	public int updateFundMowPay(FundMowPay fundMowPay);
}
