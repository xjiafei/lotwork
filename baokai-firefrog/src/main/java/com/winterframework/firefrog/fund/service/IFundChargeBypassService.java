package com.winterframework.firefrog.fund.service;

import com.winterframework.firefrog.fund.dao.vo.FundChargeBypassVO;
import com.winterframework.firefrog.fund.web.dto.FundChargeBypassRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;


public interface IFundChargeBypassService {
	Page<FundChargeBypassVO> getAllByPage(PageRequest<FundChargeBypassRequest> pageRequest) throws Exception;
	public void deleteById(Long deleteId);
	public void saveWhiteList(FundChargeBypassVO vo) throws Exception;
	public String isAccountExist(String account,Long chargeWaySet) throws Exception;
}
