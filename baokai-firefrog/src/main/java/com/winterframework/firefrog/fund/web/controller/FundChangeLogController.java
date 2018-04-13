package com.winterframework.firefrog.fund.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.fund.dao.vo.FundChangeLog;
import com.winterframework.firefrog.fund.service.IFundChangeLogService;
import com.winterframework.firefrog.fund.web.controller.charge.FundLogReq;
import com.winterframework.firefrog.user.service.ILevelRecycleService;
import com.winterframework.firefrog.user.web.dto.QueryLevelRecycleHistoryResponse;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/**
 * 
* @ClassName: WithdrawController 
* @Description: 提现申请控制类 
* @author Richard 
* @date 2013-6-28 下午6:00:45 
*
 */
@Controller
@RequestMapping("/fund/changeLog")
public class FundChangeLogController extends BaseUserController {

	@Autowired
	private IFundChangeLogService fundCharge;
	
	@Resource(name = "levelRecycleServiceImpl")
	private ILevelRecycleService levelRecycleService;

	/**
	 * 
	* @Title: queryFundWithdrawOrder
	* @Description: 查询提现申请订单 -後台
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryFundChangeLog")
	@ResponseBody
	public Response<List<FundChangeLog>> queryFundWithdrawOrder(@RequestBody Request<FundLogReq> request)
			throws Exception {
		Response<List<FundChangeLog>> response = new Response<List<FundChangeLog>>(request);
		try {

			if (null != request.getBody()) {
				return fundCharge.getChangeLog(request);
			}

		} catch (Exception e) {
			throw e;
		}
		return response;
	}
	
	/**
	 * 
	* @Title: queryFrontFundChangeLog
	* @Description: 查询提现申请订单 - 前台
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryFrontFundChangeLog")
	@ResponseBody
	public Response<List<FundChangeLog>> queryFrontFundChangeLog(@RequestBody Request<FundLogReq> request)
			throws Exception {
		Response<List<FundChangeLog>> response = new Response<List<FundChangeLog>>(request);
		try {
			if (null != request.getBody()) {
				FundLogReq req = request.getBody().getParam();
				if(req.getUserId()!=null){
					QueryLevelRecycleHistoryResponse recycleHist = levelRecycleService
							.queryRecycleLastHistory(req.getUserId());
					req.setRecycleDate(recycleHist.getActivityDate());
				}
				if(StringUtils.isNotBlank(req.getUserChain())){
					request.getBody().getParam().setFilterZero(true);
				}
				return fundCharge.getChangeLog(request);
			}

		} catch (Exception e) {
			throw e;
		}
		return response;
	}
}
