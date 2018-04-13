package com.winterframework.firefrog.fund.dao;

import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.FundChargeAppealVO;
import com.winterframework.firefrog.fund.entity.FundChargeAppeal;

public interface IFundChargeAppealDao {

	public Long queryAppealCount(FundChargeAppeal vo) throws Exception;

	public List<FundChargeAppealVO> queryAppealList(FundChargeAppeal request)
			throws Exception;

	public Long insertAppeal(FundChargeAppeal request) throws Exception;

	public Long updateAppeal(FundChargeAppeal request) throws Exception;
}
