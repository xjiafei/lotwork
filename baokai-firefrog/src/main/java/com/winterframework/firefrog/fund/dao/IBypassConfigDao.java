package com.winterframework.firefrog.fund.dao;

import java.util.List;

import com.google.common.base.Optional;
import com.winterframework.firefrog.fund.dao.vo.BypassConfigVO;
import com.winterframework.firefrog.fund.dao.vo.FundChargeBypassVO;
import com.winterframework.firefrog.fund.entity.FundChargeAppeal;
import com.winterframework.firefrog.fund.web.dto.FundChargeBypassRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;


public interface IBypassConfigDao {
	public Optional<List<BypassConfigVO>> findByCondition(BypassConfigVO entity);
	public Integer udpateBypass(BypassConfigVO vo);
}
