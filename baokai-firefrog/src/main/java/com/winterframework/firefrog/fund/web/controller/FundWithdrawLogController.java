package com.winterframework.firefrog.fund.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.fund.dao.vo.FundWithdrawLog;
import com.winterframework.firefrog.fund.dao.vo.FundWithdrawUrgency;
import com.winterframework.firefrog.fund.service.IFundWithdrawLogService;
import com.winterframework.firefrog.fund.service.IFundWithdrawUrgencyService;
import com.winterframework.firefrog.fund.web.dto.FundWithdrawLogRequest;
import com.winterframework.firefrog.fund.web.dto.FundWithdrawLogResponse;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;


/**
 * 
 * @ClassName FundWithdrawLogController.java
 * @Description 
 * @author Ami.Tsai
 * @date 2015年12月23日
 *
 */
@Controller("fundWithdrawLogController")
@RequestMapping("/fund")
public class FundWithdrawLogController {

	private static final Logger log = LoggerFactory.getLogger(FundWithdrawLogController.class);

	@Resource(name="fundWithdrawLogServiceImpl")
	private IFundWithdrawLogService fundWithdrawLogService;

	@Resource(name="fundWithdrawUrgencyServiceImpl")
	private IFundWithdrawUrgencyService fundWithdrawUrgencyService;
	
//	/**
//	 * 新增一筆提現歷程
//	 * @param request
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/addLog")
//	@ResponseBody
//	public Response<FundWithdrawLogResponse> addLog(@RequestBody @ValidRequestBody Request<FundWithdrawLogRequest> request)
//			throws Exception {
//		Response<FundWithdrawLogResponse> response = new Response<FundWithdrawLogResponse>(request);
//		try {
//			if (null != request.getBody()) {
//				FundWithdrawLog drawLog = initFundWithdrawLog(request);
//				fundWithdrawLogService.addLog(drawLog);
//				FundWithdrawLogResponse logResponse = new FundWithdrawLogResponse();
//				logResponse.setIsSuccess(true);
//				response.setResult(logResponse);
//			}
//		} catch (Exception e) {
//			log.error("Fund WithdrawLog addLog error:", e);
//			throw e;
//		}
//		return response;
//	}
//	
//	/**
//	 * 新增一筆申請提現歷程
//	 * @param request
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/addDrawLogByStatus")
//	@ResponseBody
//	public Response<FundWithdrawLogResponse> addDrawLogByStatus(@RequestBody  Request<FundWithdrawLogRequest> request)
//			throws Exception {
//		Response<FundWithdrawLogResponse> response = new Response<FundWithdrawLogResponse>(request);
//		try {
//			if (null != request.getBody()) {
//				FundWithdrawLogResponse logResponse = new FundWithdrawLogResponse();
//				FundWithdrawLog drawLog = initFundWithdrawLog(request);
//				drawLog.setLogContent(FundLogEnum.STATUS.getTextByCode(request.getBody().getParam().getStatus()));
//				fundWithdrawLogService.addLog(drawLog);
//				logResponse.setIsSuccess(true);
//				response.setResult(logResponse);
//			}
//		} catch (Exception e) {
//			log.error("Fund WithdrawLog addLog error:", e);
//			throw e;
//		}
//		return response;
//	}
	
	
	/**
	 * 查詢提現歷程+緊急提示
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchLog")
	@ResponseBody
	public Response<FundWithdrawLogResponse> searchLogs(@RequestBody @ValidRequestBody Request<FundWithdrawLogRequest> request)
			throws Exception {

		Response<FundWithdrawLogResponse> response = new Response<FundWithdrawLogResponse>(request);
		try {
			if (null != request.getBody()) {
				FundWithdrawLogRequest logRequest = request.getBody().getParam();
				FundWithdrawLogResponse logResponse = new FundWithdrawLogResponse();
				SimpleDateFormat formatYMD = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date beforeTime = new Date();
				if(StringUtils.isNotBlank(logRequest.getWithdrawTimeStr5())){
					beforeTime = formatYMD.parse(logRequest.getWithdrawTimeStr5().replace("<br>"," "));
					
				}
				Map<String,Date> timeMap = new HashMap <String,Date> ();
				timeMap.put("afterTime", logRequest.getApplyTime());
				timeMap.put("beforeTime", beforeTime);
				List<FundWithdrawLog> fundWithdrawLogs = fundWithdrawLogService.searchLogs(logRequest.getWithdrawSn());
				List<FundWithdrawUrgency> fundWithdrawUrgencys = fundWithdrawUrgencyService.searchUrgencyBetweenTime(timeMap);

				List<String> logData= fundWithdrawLogService.mergeLogByTime(fundWithdrawLogs, fundWithdrawUrgencys);
				logResponse.setLogData(logData);
				response.setResult(logResponse);
			}
		} catch (Exception e) {
			log.error("Fund WithdrawLog searchLogs error:", e);
			throw e;
		}
		return response;
	}
	
}
