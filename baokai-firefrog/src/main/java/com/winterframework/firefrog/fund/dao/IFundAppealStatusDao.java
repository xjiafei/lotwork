package com.winterframework.firefrog.fund.dao;

import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.FundAppealStatusVO;
import com.winterframework.firefrog.fund.dao.vo.FundChargeAppealVO;
import com.winterframework.firefrog.fund.entity.FundAppealStatus;

public interface IFundAppealStatusDao {

	public Long queryFundAppealCount(FundAppealStatus request)
			throws Exception;

	public List<FundAppealStatusVO> queryFundAppealList(FundAppealStatus request)
			throws Exception;
	
	public FundChargeAppealVO queryFundAppeal(String appealSn) throws Exception;
}
