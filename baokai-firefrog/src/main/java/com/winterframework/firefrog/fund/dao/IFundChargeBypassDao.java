package com.winterframework.firefrog.fund.dao;

import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.FundChargeBypassVO;
import com.winterframework.firefrog.fund.entity.FundChargeAppeal;
import com.winterframework.firefrog.fund.web.dto.FundChargeBypassRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;


public interface IFundChargeBypassDao {
	Page<FundChargeBypassVO> selectByPage(PageRequest<FundChargeBypassRequest> pageRequest) throws Exception;
	public void updateDeleteFlag(Long deleteId,String type);
	public void deleteById(Long id);
	public int saveWhiteList(FundChargeBypassVO vo) throws Exception;
	public FundChargeBypassVO searchByAccount(String account,Long chargeWaySet) throws Exception ;
	
}
