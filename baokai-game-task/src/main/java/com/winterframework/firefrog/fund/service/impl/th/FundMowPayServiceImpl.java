package com.winterframework.firefrog.fund.service.impl.th;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winterframework.firefrog.fund.dao.IFundMowPayDao;
import com.winterframework.firefrog.fund.dao.vo.FundMowPay;
import com.winterframework.firefrog.fund.service.IFundMowPayService;

@Service("fundMowPayServiceImpl")
public class FundMowPayServiceImpl implements IFundMowPayService{

	
	@Resource(name = "fundMowPayDaoImpl")
	private IFundMowPayDao fundMowPayDao;
	
	@Override
	public List<FundMowPay> queryThPayOrder(Long status) {
		return fundMowPayDao.queryThPayOrder(status);
	}

	@Override
	public int updateFundMowPay(FundMowPay fundMowPay) {
		return fundMowPayDao.updateFundMowPay(fundMowPay);
	}

}
