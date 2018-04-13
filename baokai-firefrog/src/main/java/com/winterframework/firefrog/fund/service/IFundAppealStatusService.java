package com.winterframework.firefrog.fund.service;

import java.util.List;

import com.winterframework.firefrog.fund.web.dto.FundAppealStatusRequest;
import com.winterframework.firefrog.fund.web.dto.FundAppealStatusResponse;
import com.winterframework.firefrog.fund.web.dto.FundRechargeStrucResponse;
import com.winterframework.modules.web.jsonresult.Pager;

public interface IFundAppealStatusService {

	public Long queryFundAppealCount(FundAppealStatusRequest request)
			throws Exception;

	public List<FundAppealStatusResponse> queryFundAppealList(
			FundAppealStatusRequest request, Pager pager) throws Exception;
	
	public FundRechargeStrucResponse queryFundAppeal(String appealSn) throws Exception;
}
