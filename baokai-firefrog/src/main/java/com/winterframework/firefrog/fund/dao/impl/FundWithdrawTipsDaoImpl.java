package com.winterframework.firefrog.fund.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.fund.dao.IFundWithdrawTipsDao;
import com.winterframework.firefrog.fund.dao.vo.FundWithdrawTips;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("fundWithdrawTipsDaoImpl")
public class FundWithdrawTipsDaoImpl extends BaseIbatis3Dao<FundWithdrawTips> implements
		IFundWithdrawTipsDao {

	@Override
	public List<FundWithdrawTips> getTips(FundWithdrawTips tips) {
		return this.sqlSessionTemplate.selectList("getTips", tips);
	}

	@Override
	public void deleteByCondition(FundWithdrawTips tips) {
		this.sqlSessionTemplate.delete("deleteByCondition", tips);
	}

	@Override
	public Integer getGroupBCount(FundWithdrawTips tips) {
		return this.sqlSessionTemplate.selectOne("getGroupBCount", tips);
	}									

	
}
