package com.winterframework.firefrog.fund.web.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.fund.service.IFundAppealStatusService;
import com.winterframework.firefrog.fund.web.dto.FundAppealStatusRequest;
import com.winterframework.firefrog.fund.web.dto.FundAppealStatusResponse;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/**
 * @ClassName: FundAppealStatusController
 * 
 */
@Controller("fundAppealStatusController")
@RequestMapping(value = "/fund")
public class FundAppealStatusController {

	private static Logger logger = LoggerFactory
			.getLogger(FundBankController.class);

	@Resource(name = "fundAppealStatusServiceImpl")
	private IFundAppealStatusService fundAppealStatusService;

	/**
	 * 
	 * @Title: queryFundAppealStatus
	 * @Description: 查询申诉进度
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryFundAppealStatus")
	@ResponseBody
	public Response<List<FundAppealStatusResponse>> queryFrontFundChangeLog(
			@RequestBody Request<FundAppealStatusRequest> request)
			throws Exception {
		logger.info("/fundAppeal/queryFundAppealStatus");
		Response<List<FundAppealStatusResponse>> response = new Response<List<FundAppealStatusResponse>>(
				request);
		try {
			if (null != request.getBody()) {
				FundAppealStatusRequest param = request.getBody().getParam();
				param.setUserId(request.getHead().getUserId());
				Calendar cl = Calendar.getInstance();
				cl.setTime(new Date());
				cl.add(cl.DATE, -15); //查包含今天的前15日資料
				if(param.getStartDate().compareTo(cl.getTime())==-1){
					param.setStartDate(cl.getTime());
				}
					
				Pager pager = request.getBody().getPager();
				Long total = fundAppealStatusService
						.queryFundAppealCount(param);
				List<FundAppealStatusResponse> result = fundAppealStatusService
						.queryFundAppealList(param, pager);
				response.setResult(result);
				ResultPager resultPager = new ResultPager(pager.getStartNo(),
						pager.getEndNo(), total.intValue());
				response.setResultPage(resultPager);

			}

		} catch (Exception e) {
			throw e;
		}
		return response;
	}

}
