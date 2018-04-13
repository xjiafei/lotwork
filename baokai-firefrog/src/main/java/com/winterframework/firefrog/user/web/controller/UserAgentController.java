package com.winterframework.firefrog.user.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.fund.dao.vo.FundChargeWithdrawVo;
import com.winterframework.firefrog.user.entity.UserAgentCount;
import com.winterframework.firefrog.user.service.IUserAgentCountService;
import com.winterframework.firefrog.user.web.dto.AgentRpt;
import com.winterframework.firefrog.user.web.dto.UserAgentCountConvert;
import com.winterframework.firefrog.user.web.dto.UserAgentCountStruc;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("userAgentController")
@RequestMapping(value = "/user/agent/")
public class UserAgentController {

	@Resource(name = "userAgentCountServiceImpl")
	private IUserAgentCountService userAgentCountServiceImpl;

	private static final Logger logger = LoggerFactory.getLogger(UserAgentController.class);

	/** 
	 * 查询代理信息
	*/
	@RequestMapping(value = "queryUserAgentCount")
	public @ResponseBody
	Response<List<AgentRpt>> queryUserAgentCount(@RequestBody Request<UserAgentCountStruc> request) throws Exception {
		Response<List<AgentRpt>> response = new Response<List<AgentRpt>>(request);
		UserAgentCountStruc struc = request.getBody().getParam();
		try {
			List<AgentRpt> agentRptList = new ArrayList<AgentRpt>();
			List<UserAgentCount> agentCountList = userAgentCountServiceImpl.queryUserAgentCount(struc);
			if (agentCountList != null) {
				for (UserAgentCount userAgentCount : agentCountList) {
					AgentRpt agentRpt = UserAgentCountConvert.convertEntityToAgentRpt(userAgentCount);
					agentRptList.add(agentRpt);
				}
			}
			response.getBody().setResult(agentRptList);
		} catch (Exception e) {
			logger.error("queryUserAgentCount error.", e);
			throw e;
		}
		return response;
	}
	@RequestMapping(value = "queryTopChargeWithdrawRpt")
	public @ResponseBody
	Response<List<FundChargeWithdrawVo>> queryTopChargeWithdrawRpt(@RequestBody Request<UserAgentCountStruc> request) throws Exception {
		Response<List<FundChargeWithdrawVo>> response = new Response<List<FundChargeWithdrawVo>>(request);
		UserAgentCountStruc struc = request.getBody().getParam();
		try {
			List<AgentRpt> agentRptList = new ArrayList<AgentRpt>();
			List<FundChargeWithdrawVo> agentCountList = userAgentCountServiceImpl.queryTopChargeWithdrawRpt(struc);
			
			response.getBody().setResult(agentCountList);
		} catch (Exception e) {
			logger.error("queryUserAgentCount error.", e);
			throw e;
		}
		return response;
	}
	/** 
	 * 查询代理信息
	*/
	@RequestMapping(value = "/queryUserCount")
	public @ResponseBody
	Response<Map> queryUserTeamAgent(@RequestBody Request<Long> request) throws Exception {
		Response<Map> response = new Response<Map>(request);
       Long userId=request.getBody().getParam();
		try {
			response.setResult(userAgentCountServiceImpl.queryTeamUser(userId));		
		} catch (Exception e) {
			logger.error("queryUserAgentCount error.", e);
			throw e;
		}
		return response;
	}
}
