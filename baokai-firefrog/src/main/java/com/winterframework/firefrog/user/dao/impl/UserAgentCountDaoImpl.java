package com.winterframework.firefrog.user.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.fund.dao.vo.FundChargeWithdrawVo;
import com.winterframework.firefrog.user.dao.IUserAgentCountDao;
import com.winterframework.firefrog.user.dao.vo.UserAgentCountVo;
import com.winterframework.firefrog.user.dao.vo.VOConverter;
import com.winterframework.firefrog.user.entity.UserAgentCount;
import com.winterframework.firefrog.user.web.dto.UserAgentCountStruc;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("userAgentCountDaoImpl")
public class UserAgentCountDaoImpl extends BaseIbatis3Dao<UserAgentCountVo> implements IUserAgentCountDao {
	@Override
	public List<UserAgentCount> queryUserAgentCount(UserAgentCountStruc userAgentCount) {
		if(userAgentCount.getEndTime()==null){
			userAgentCount.setEndTime(new Date());
		}
		if(userAgentCount.getStartTime()==null){
			userAgentCount.setStartTime(new Date());
		}
		List<UserAgentCountVo> agentCountList = this.sqlSessionTemplate.selectList(
				"com.winterframework.firefrog.user.dao.vo.UserAgentCountVo.queryUserAgentCount", userAgentCount);
		//		List<UserAgentCountVo> agentCountList = this.getAll();
		List<UserAgentCount> userAgentList = null;
		if (CollectionUtils.isNotEmpty(agentCountList)) {
			userAgentList = new ArrayList<UserAgentCount>();
			UserAgentCount agentCount = null;
			for (UserAgentCountVo userAgentCountVo : agentCountList) {
				agentCount = VOConverter.agentVoToAgent(userAgentCountVo);
				userAgentList.add(agentCount);
			}
		}
		return userAgentList;
	}

	public List<FundChargeWithdrawVo> queryTopChargeWithdrawRpt(UserAgentCountStruc userAgentCount) {
		return this.sqlSessionTemplate.selectList(
				"com.winterframework.firefrog.user.dao.vo.UserAgentCountVo.queryTopChargeWithdraw", userAgentCount);

	}

	@Override
	public Map queryUserCount(Long userId) {
		return this.sqlSessionTemplate.selectOne(
				"com.winterframework.firefrog.user.dao.vo.UserAgentCountVo.queryUserAgentCount3", userId);

	}

}
