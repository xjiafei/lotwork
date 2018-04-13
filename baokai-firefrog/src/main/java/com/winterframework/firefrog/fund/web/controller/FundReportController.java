package com.winterframework.firefrog.fund.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.fund.dao.impl.FundReportDepositDao;
import com.winterframework.firefrog.fund.dao.impl.FundReportWithdrawDao;
import com.winterframework.firefrog.fund.service.IFundTransferService;
import com.winterframework.firefrog.fund.web.controller.vo.DatePeriod;
import com.winterframework.firefrog.fund.web.controller.vo.WithCharge;
import com.winterframework.firefrog.fund.web.dto.FundReportDeposit;
import com.winterframework.firefrog.fund.web.dto.FundReportDepositRes;
import com.winterframework.firefrog.fund.web.dto.FundTransferDetailResponse;
import com.winterframework.firefrog.fund.web.dto.FundTransferRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

@Controller("fundReportController")
@RequestMapping(value = "/fund")
public class FundReportController {
	@Autowired
	private FundReportDepositDao fundReportDepositDao;
	@Autowired
	private FundReportWithdrawDao fundReportWithdrawDao;
	@Resource(name = "fundTransferServiceImpl")
	private IFundTransferService fundTransferService;
	
	
	@RequestMapping(value = "/report/deposit")
	@ResponseBody
	public Response<List<FundReportDepositRes>> fundAdjustApply(@RequestBody Request<FundReportDeposit> request)
			throws Exception {
		Response<List<FundReportDepositRes>> response = new Response<List<FundReportDepositRes>>(request);
		try {
			Page<FundReportDepositRes> page = fundReportWithdrawDao.getSumByPeriod(request.getBody());
			response.setResult(page.getResult());
			ResultPager page2 = new ResultPager();
			page2.setStartNo(page.getThisPageFirstElementNumber());
			page2.setEndNo(page.getThisPageLastElementNumber());
			page2.setTotal(page.getTotalCount());

			response.setResult(page.getResult());
			response.setResultPage(page2);
			return response;
		} catch (Exception e) {
			throw e;
		}
	}

	@RequestMapping(value = "/report/withdrawAndCharge")
	@ResponseBody
	public Response<List<WithCharge>> withdrawCharge(@RequestBody Request<DatePeriod> request) throws Exception {
		Response<List<WithCharge>> response = new Response<List<WithCharge>>(request);
		try {
			Page<WithCharge> page = fundReportDepositDao.getSumByPeriod(request.getBody());
			response.setResult(page.getResult());
			ResultPager page2 = new ResultPager();
			page2.setStartNo(page.getThisPageFirstElementNumber());
			page2.setEndNo(page.getThisPageLastElementNumber());
			page2.setTotal(page.getTotalCount());

			response.setResultPage(page2);
		} catch (Exception e) {
			throw e;
		}

		return response;
	}

	/** 
	* @Title: userFundTransfer 
	* @Description: 用户转账明细
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/report/userFundTransfer")
	@ResponseBody
	public Response<FundTransferDetailResponse> userFundTransfer(@RequestBody Request<FundTransferRequest> request) throws Exception {
		Response<FundTransferDetailResponse> response = new Response<FundTransferDetailResponse>(request);		
		response.setPageResult(fundTransferService.userFundTransfer(request));		
		return response;
	}
	
}
