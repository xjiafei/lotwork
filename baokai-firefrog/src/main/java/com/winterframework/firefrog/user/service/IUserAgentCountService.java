package com.winterframework.firefrog.user.service;

import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.fund.dao.vo.FundChargeWithdrawVo;
import com.winterframework.firefrog.user.entity.UserAgentCount;
import com.winterframework.firefrog.user.web.dto.UserAgentCountStruc;

public interface IUserAgentCountService {
	/** 
	 * 根据查询条件获取对应的列表
	*/
	List<UserAgentCount> queryUserAgentCount(UserAgentCountStruc userAgentCount) throws Exception;
	List<FundChargeWithdrawVo> queryTopChargeWithdrawRpt(UserAgentCountStruc userAgentCount) throws Exception;

	Map queryTeamUser(Long userId) throws Exception;
	/** 
	* @Title: userAgentCountOneHourBefore 
	* @Description: 统计前一小时代理用户情况
	* @throws Exception
	*/
	void userAgentCountOneHourBefore() throws Exception;
}