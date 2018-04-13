package com.winterframework.firefrog.user.dao;

import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.fund.dao.vo.FundChargeWithdrawVo;
import com.winterframework.firefrog.user.dao.vo.UserAgentCountVo;
import com.winterframework.firefrog.user.entity.UserAgentCount;
import com.winterframework.firefrog.user.web.dto.UserAgentCountStruc;
import com.winterframework.orm.dal.ibatis3.BaseDao;


public interface IUserAgentCountDao extends BaseDao<UserAgentCountVo>{

	/** 
	 * 根据查询条件获取对应的agentCount对象
	*/
	List<UserAgentCount> queryUserAgentCount(UserAgentCountStruc userAgentCount);
	List<FundChargeWithdrawVo> queryTopChargeWithdrawRpt(UserAgentCountStruc userAgentCount);
	Map queryUserCount(Long userId);
}
