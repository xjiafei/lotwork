package com.winterframework.firefrog.fund.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winterframework.firefrog.fund.dao.IFundWithDrawDao;
import com.winterframework.firefrog.fund.dao.vo.FundWithdraw;
import com.winterframework.firefrog.fund.service.IFundWithDrawService;

@Service("fundWithDrawServiceImpl")
public class FundWithDrawServiceImpl implements IFundWithDrawService{

	
	@Resource(name = "fundWithDrawDaoImpl")
	private IFundWithDrawDao fundWithDrawDao;
	
	@Override
	public FundWithdraw getBySn(String sn) {
		return fundWithDrawDao.getBySn(sn);
	}

}
