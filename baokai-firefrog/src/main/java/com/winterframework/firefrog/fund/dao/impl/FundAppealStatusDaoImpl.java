package com.winterframework.firefrog.fund.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.fund.dao.IFundAppealStatusDao;
import com.winterframework.firefrog.fund.dao.vo.FundAppealStatusVO;
import com.winterframework.firefrog.fund.dao.vo.FundChargeAppealVO;
import com.winterframework.firefrog.fund.entity.FundAppealStatus;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("fundAppealStatusDaoImpl")
public class FundAppealStatusDaoImpl extends BaseIbatis3Dao<FundAppealStatusVO>
		implements IFundAppealStatusDao {

	@Override
	public Long queryFundAppealCount(FundAppealStatus request) throws Exception {
		Long count = sqlSessionTemplate.selectOne("queryFundAppealCount",
				request);
		return count;
	}

	@Override
	public List<FundAppealStatusVO> queryFundAppealList(FundAppealStatus request)
			throws Exception {
		List<FundAppealStatusVO> result = sqlSessionTemplate.selectList(
				"queryFundAppealList", request);
		return result;
	}
	
	@Override
	public FundChargeAppealVO queryFundAppeal(String appealSn) throws Exception{
		FundChargeAppealVO result = sqlSessionTemplate.selectOne("queryFundAppeal",appealSn);
		return result;
	}

}
