package com.winterframework.firefrog.user.web.dto;

import com.winterframework.firefrog.user.entity.UserAgentCount;

public class UserAgentCountConvert {

	/** 
	 * 
	 * 将实体对象转换为前台使用的 结构体对象
	*/
	public static AgentRpt convertEntityToAgentRpt(UserAgentCount userAgentCount) {
		AgentRpt agentRpt = new AgentRpt();
		agentRpt.setBet(userAgentCount.getBet());
		agentRpt.setCharge(userAgentCount.getCharge());
		agentRpt.setDay(userAgentCount.getDay());
		agentRpt.setNewUser(userAgentCount.getNewUser());
		agentRpt.setReward(userAgentCount.getReward());
		agentRpt.setTime(userAgentCount.getTime());
		agentRpt.setUserId(userAgentCount.getUserId());
		agentRpt.setWithDraw(userAgentCount.getWithDraw());
		return agentRpt;
	}
}
