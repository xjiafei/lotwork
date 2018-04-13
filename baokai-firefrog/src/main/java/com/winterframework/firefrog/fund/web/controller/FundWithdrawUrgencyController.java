package com.winterframework.firefrog.fund.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.fund.dao.vo.FundWithdrawUrgency;
import com.winterframework.firefrog.fund.service.IFundWithdrawUrgencyService;
import com.winterframework.firefrog.fund.web.dto.FundWithdrawUrgencyRequest;
import com.winterframework.firefrog.fund.web.dto.FundWithdrawUrgencyResponse;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("fundWithdrawUrgencyController")
@RequestMapping("/fund")
public class FundWithdrawUrgencyController {

	private static final Logger log = LoggerFactory.getLogger(FundWithdrawUrgencyController.class);


	@Resource(name="fundWithdrawUrgencyServiceImpl")
	private IFundWithdrawUrgencyService fundWithdrawUrgencyService;
	
	
	/**
	 * 新增一筆緊急發布
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addUrgency")
	@ResponseBody
	public Response<FundWithdrawUrgencyResponse> addUrgency(@RequestBody @ValidRequestBody Request<FundWithdrawUrgencyRequest> request)
			throws Exception {
		Response<FundWithdrawUrgencyResponse> response = new Response<FundWithdrawUrgencyResponse>(request);
		try {
			if (null != request.getBody()) {
				FundWithdrawUrgencyRequest urgencyReq = request.getBody().getParam();
				FundWithdrawUrgency urgency = urgencyReq.getUrgencys();
				urgency.setCreateTime(DateUtils.currentDate());
				urgency.setCreator(request.getHead().getUserId());
				urgency.setCreateUser(request.getHead().getUserAccount());
				
				fundWithdrawUrgencyService.addUrgency(urgency);
				
				FundWithdrawUrgencyResponse urgencyRep = new FundWithdrawUrgencyResponse();				
				response.setResult(urgencyRep);
			}
		} catch (Exception e) {
			log.error("Fund WithdrawUrgency addLog error:", e);
			throw e;
		}
		return response;
	}
	
	/**
	 * 更新一筆緊急發布
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateUrgency")
	@ResponseBody
	public Response<FundWithdrawUrgencyResponse> updateUrgency(@RequestBody @ValidRequestBody Request<FundWithdrawUrgencyRequest> request)
			throws Exception {
		Response<FundWithdrawUrgencyResponse> response = new Response<FundWithdrawUrgencyResponse>(request);
		try {
			if (null != request.getBody()) {
				FundWithdrawUrgencyRequest urgencyReq = request.getBody().getParam();
				FundWithdrawUrgency urgency = urgencyReq.getUrgencys();
				urgency.setUpdateUser(request.getHead().getUserAccount());

				
				fundWithdrawUrgencyService.updateUrgency(urgency);
				
				FundWithdrawUrgencyResponse urgencyRep = new FundWithdrawUrgencyResponse();				
				response.setResult(urgencyRep);
			}
		} catch (Exception e) {
			log.error("Fund WithdrawUrgency updateLog error:", e);
			throw e;
		}
		return response;
	}
	
	/**
	 * 根據提現單生成時間搜尋緊急發布
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchUrgencyByApplyDate")
	@ResponseBody
	public Response<FundWithdrawUrgencyResponse> searchUrgencyByApplyDate(@RequestBody @ValidRequestBody Request<FundWithdrawUrgencyRequest> request)
			throws Exception {

		Response<FundWithdrawUrgencyResponse> response = new Response<FundWithdrawUrgencyResponse>(request);
		try {
			if (null != request.getBody()) {
				
				FundWithdrawUrgencyRequest urgencyReq = request.getBody().getParam();
				
				Date applyTime= urgencyReq.getApplyTime();
				
				 List<FundWithdrawUrgency> urgencys= fundWithdrawUrgencyService.searchUrgencyAfterTime(applyTime);
				
				FundWithdrawUrgencyResponse urgencyRep = new FundWithdrawUrgencyResponse();
				
				urgencyRep.setUrgencys(urgencys);
				
				response.setResult(urgencyRep);
			}
		} catch (Exception e) {
			log.error("Fund WithdrawUrgency searchLogs error:", e);
			throw e;
		}
		return response;
	}
	
	
	/**
	 * 根據條件搜尋緊急發布
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchUrgency")
	@ResponseBody
	public Response<FundWithdrawUrgencyResponse> searchUrgency(@RequestBody Request<FundWithdrawUrgencyRequest> request)
			throws Exception {

		Response<FundWithdrawUrgencyResponse> response = new Response<FundWithdrawUrgencyResponse>(request);
		try {
			List<FundWithdrawUrgency> urgencys= fundWithdrawUrgencyService.getAll();
			final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			if(urgencys!=null){
				urgencys = Lists.transform(urgencys, new Function<FundWithdrawUrgency, FundWithdrawUrgency>() {
					@Override
					public FundWithdrawUrgency apply(FundWithdrawUrgency obj) {
						if(obj!=null && obj.getCreateTime()!=null){
							obj.setCreateTimeText(format.format(obj.getCreateTime()));
						}
						return obj;
					}
				});
			}
			
			FundWithdrawUrgencyResponse urgencyRep = new FundWithdrawUrgencyResponse();
			urgencyRep.setUrgencys(urgencys);
			response.setResult(urgencyRep);
		} catch (Exception e) {
			log.error("Fund WithdrawUrgency searchLogs error:", e);
			throw e;
		}
		return response;
	}
	
}
