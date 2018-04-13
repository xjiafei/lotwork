package com.winterframework.firefrog.fund.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.fund.dao.IFundMowPayDao;
import com.winterframework.firefrog.fund.dao.vo.FundMowPay;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("fundMowPayDao")
public class FundMowPayDaoImpl extends BaseIbatis3Dao<FundMowPay> implements IFundMowPayDao {

	@Override
	public void saveMowPay(FundMowPay mowPay) throws Exception {
		this.insert(mowPay);
	}

	@Override
	public void updateMowPay(FundMowPay mowPay) throws Exception {
		this.update(mowPay);
	}

}
