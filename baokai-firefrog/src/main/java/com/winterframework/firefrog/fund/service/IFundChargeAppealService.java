package com.winterframework.firefrog.fund.service;

import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.FundChargeAppealVO;
import com.winterframework.firefrog.fund.entity.FundChargeAppeal;
import com.winterframework.firefrog.fund.web.dto.FundChargeAppealRequest;
import com.winterframework.firefrog.fund.web.dto.FundChargeAppealResult;
import com.winterframework.firefrog.fund.web.dto.FundRechargeAppealRequest;
import com.winterframework.modules.web.jsonresult.Pager;

public interface IFundChargeAppealService {

	public FundChargeAppealResult queryCanAppealRechargeList(Long userId,
			Pager pager) throws Exception;

	public void addRechargeAppeal(FundChargeAppealRequest param)
			throws Exception;
	
	public List<FundChargeAppealVO> queryAppealRechargesByCondition(
			FundRechargeAppealRequest request, Pager pager) throws Exception ;
	
	public Long updateAppeal(FundChargeAppeal request) throws Exception;
	
	public Long queryAppealCountsByStatus(int status) throws Exception;
}
