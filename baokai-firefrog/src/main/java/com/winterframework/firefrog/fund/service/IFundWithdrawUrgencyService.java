package com.winterframework.firefrog.fund.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.fund.dao.vo.FundWithdrawUrgency;

public interface IFundWithdrawUrgencyService {

	public void addUrgency(FundWithdrawUrgency urgency);
	
	public void updateUrgency(FundWithdrawUrgency urgency);
	
	/**
	 * 搜尋傳入日期之後緊急發布
	 * @param date
	 * @return
	 */
	public List<FundWithdrawUrgency> searchUrgencyAfterTime(Date date);

	public List<FundWithdrawUrgency> getAll();
	
	public List<FundWithdrawUrgency> searchUrgencyBetweenTime(Map<String,Date> map);
}
