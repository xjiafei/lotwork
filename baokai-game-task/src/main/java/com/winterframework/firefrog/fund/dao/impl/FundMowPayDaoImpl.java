package com.winterframework.firefrog.fund.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.fund.dao.IFundMowPayDao;
import com.winterframework.firefrog.fund.dao.vo.FundMowPay;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("fundMowPayDaoImpl")
public class FundMowPayDaoImpl extends BaseIbatis3Dao<FundMowPay> implements IFundMowPayDao{

	@Override
	public List<FundMowPay> queryThPayOrder(Long status) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("status", status);
		return sqlSessionTemplate.selectList("queryThPayOrder",param);
	}

	@Override
	public int updateFundMowPay(FundMowPay fundMowPay) {
		return update(fundMowPay);
	}

}
