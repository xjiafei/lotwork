package com.winterframework.firefrog.fund.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.fund.dao.IFundWithdrawUrgencyDao;
import com.winterframework.firefrog.fund.dao.vo.FundWithdrawUrgency;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 
 * @ClassName FundWithdrawUrgencyImpl.java
 * @Description 
 * @author Ami.Tsai
 * @date 2015年12月22日
 *
 */
@Repository("fundWithdrawUrgencyDaoImpl")
public class FundWithdrawUrgencyDaoImpl extends BaseIbatis3Dao<FundWithdrawUrgency> implements IFundWithdrawUrgencyDao{

	@Override
	public List<FundWithdrawUrgency> getUrgenctAfterTime(Date date) {
		return this.sqlSessionTemplate.selectList("getUrgenctAfterTime", date);
	}
	
	@Override
	public List<FundWithdrawUrgency> getUrgenctBetweenTime(Map<String,Date> map) {
		return this.sqlSessionTemplate.selectList("getUrgenctBetweenTime", map);
	}
	
}
