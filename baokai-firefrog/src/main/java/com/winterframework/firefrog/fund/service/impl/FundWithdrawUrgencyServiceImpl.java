package com.winterframework.firefrog.fund.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winterframework.firefrog.fund.dao.IFundWithdrawUrgencyDao;
import com.winterframework.firefrog.fund.dao.vo.FundWithdrawUrgency;
import com.winterframework.firefrog.fund.service.IFundWithdrawUrgencyService;

/**
 * 
 * @ClassName FundWithdrawUrgencyServiceImpl.java
 * @Description 
 * @author Ami.Tsai
 * @date 2015年12月24日
 *
 */
@Service("fundWithdrawUrgencyServiceImpl")
public class FundWithdrawUrgencyServiceImpl implements
		IFundWithdrawUrgencyService {

	@Resource(name="fundWithdrawUrgencyDaoImpl")
	private IFundWithdrawUrgencyDao fundWithdrawUrgencyDaoImpl;

	@Override
	public void addUrgency(FundWithdrawUrgency urgency) {
		fundWithdrawUrgencyDaoImpl.insert(urgency);
	}
	
	@Override
	public void updateUrgency(FundWithdrawUrgency urgency) {
		fundWithdrawUrgencyDaoImpl.update(urgency);
	}

	@Override
	public List<FundWithdrawUrgency> searchUrgencyAfterTime(Date date) {
		return fundWithdrawUrgencyDaoImpl.getUrgenctAfterTime(date);
	}

	@Override
	public List<FundWithdrawUrgency> getAll() {
		return fundWithdrawUrgencyDaoImpl.getAll();
	}
	
	@Override
	public List<FundWithdrawUrgency> searchUrgencyBetweenTime(Map<String,Date> map) {
		return fundWithdrawUrgencyDaoImpl.getUrgenctBetweenTime(map);
	}
	
	
}