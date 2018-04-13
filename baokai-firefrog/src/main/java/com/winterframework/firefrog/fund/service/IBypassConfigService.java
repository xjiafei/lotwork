package com.winterframework.firefrog.fund.service;

import java.util.List;

import com.google.common.base.Optional;
import com.winterframework.firefrog.fund.dao.vo.BypassConfigVO;
import com.winterframework.firefrog.fund.dao.vo.FundChargeBypassVO;
import com.winterframework.firefrog.fund.web.dto.FundChargeBypassRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;


public interface IBypassConfigService {
	//分流判斷 0:DP 1:通匯
	public int checkWithdrawBypass(Long amount,String account,Long type,String chargeWay) throws Exception;
	
	public int checkChargeBypass(Long amount,Long userId,Long type,Long chargeWay,Long platform) throws Exception;
	
	public Optional<List<BypassConfigVO>> findByCondition(BypassConfigVO entity)throws Exception;

	public void updateBypass(List<BypassConfigVO> configs) throws Exception;
	
	public long getDailySumByType(Long type,Long agency,Long chargeWaySet) throws Exception;
}
