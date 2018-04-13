package com.winterframework.firefrog.fund.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.fund.dao.IFundChargeAppealDao;
import com.winterframework.firefrog.fund.dao.vo.FundChargeAppealVO;
import com.winterframework.firefrog.fund.entity.FundChargeAppeal;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("fundChargeAppealDaoImpl")
public class FundChargeAppealDaoImpl extends BaseIbatis3Dao<FundChargeAppealVO>
		implements IFundChargeAppealDao {
	@Override
	public Long insertAppeal(FundChargeAppeal request) throws Exception {
		Long count = sqlSessionTemplate.selectOne("insertAppeal", request);
		return count;
	}

	@Override
	public Long updateAppeal(FundChargeAppeal request) throws Exception {
		Long count = sqlSessionTemplate.selectOne("updateAppeal", request);
		return count;
	}

	@Override
	public Long queryAppealCount(FundChargeAppeal request) throws Exception {
		Long count = sqlSessionTemplate.selectOne("queryAppealCount", request);
		return count;
	}

	@Override
	public List<FundChargeAppealVO> queryAppealList(FundChargeAppeal request)
			throws Exception {
		List<FundChargeAppealVO> result = sqlSessionTemplate.selectList(
				"queryAppealList", request);
		return result;
	}

}
