package com.winterframework.firefrog.fund.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.fund.dao.IFundWithdrawLogDao;
import com.winterframework.firefrog.fund.dao.vo.FundWithdrawLog;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("fundWithdrawLogDaoImpl")
public class FundWithdrawLogDaoImpl extends BaseIbatis3Dao<FundWithdrawLog> implements
		IFundWithdrawLogDao {

	@Override
	public List<FundWithdrawLog> getLogs(String withdrawSn) {
		return this.sqlSessionTemplate.selectList("getLogsBySn", withdrawSn);
	}

}
