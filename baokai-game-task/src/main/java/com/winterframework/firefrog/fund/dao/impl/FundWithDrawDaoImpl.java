package com.winterframework.firefrog.fund.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.fund.dao.IFundWithDrawDao;
import com.winterframework.firefrog.fund.dao.vo.FundMowPay;
import com.winterframework.firefrog.fund.dao.vo.FundWithdraw;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("fundWithDrawDaoImpl")
public class FundWithDrawDaoImpl extends BaseIbatis3Dao<FundMowPay> implements IFundWithDrawDao{

	@Override
	public FundWithdraw getBySn(String sn) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("sn", sn);
		return sqlSessionTemplate.selectOne("getBySn",param);
	}

}
