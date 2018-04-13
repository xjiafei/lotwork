package com.winterframework.firefrog.fund.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.fund.dao.vo.FundWithdrawTips;
import com.winterframework.firefrog.fund.enums.FundLogEnum;
import com.winterframework.firefrog.fund.service.IFundWithdrawTipsService;
import com.winterframework.firefrog.fund.web.dto.FundWithdrawTipsRequest;
import com.winterframework.firefrog.fund.web.dto.FundWithdrawTipsResponse;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("fundWithdrawTipsController")
@RequestMapping("/fund")
public class FundWithdrawTipsController {

	private static final Logger log = LoggerFactory.getLogger(FundWithdrawTipsController.class);
	
	@Resource(name="fundWithdrawTipsServiceImpl")
	private IFundWithdrawTipsService fundWithdrawTipsServiceImpl;
	
	/**
	 * 新增提示配置
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addTips")
	@ResponseBody
	public Response<FundWithdrawTipsResponse> addTips(@RequestBody @ValidRequestBody Request<FundWithdrawTipsRequest> request)
			throws Exception {
		Response<FundWithdrawTipsResponse> response = new Response<FundWithdrawTipsResponse>(request);
		try {
			if (null != request.getBody()) {
				FundWithdrawTipsRequest tipsRequest = request.getBody().getParam();
				final Long userId = request.getHead().getUserId();
				final String userAccount = request.getHead().getUserAccount();
				List<FundWithdrawTips> list = (List<FundWithdrawTips>)tipsRequest.getTipsList();
				list =  Lists.transform(list, new Function<FundWithdrawTips , FundWithdrawTips>(){
					@Override
					public FundWithdrawTips apply(FundWithdrawTips tips) {
						tips.setCreator(userId);
						tips.setModifyTime(DateUtils.currentDate());
						tips.setModifyUser(userAccount);
						if(StringUtils.isEmpty(tips.getCreateUser())){
							tips.setCreateTime(DateUtils.currentDate());
							tips.setCreateUser(userAccount);							
						}
						return tips;
					}
				});
				fundWithdrawTipsServiceImpl.deleteTipsAndAddTipsList(list, tipsRequest.getTips());
				list = fundWithdrawTipsServiceImpl.searchTipsByCondition(tipsRequest.getTips());
				FundWithdrawTipsResponse tipsResponse = new FundWithdrawTipsResponse();
				tipsResponse.setTipsList(list);
				response.setResult(tipsResponse);
			}
		} catch (Exception e) {
			log.error("Fund WithdrawLog addLog error:", e);
			throw e;
		}
		return response;
	}
	
	/**
	 * search tips by conditions
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchTips")
	@ResponseBody
	public Response<FundWithdrawTipsResponse> searchTips(@RequestBody @ValidRequestBody Request<FundWithdrawTipsRequest> request)
			throws Exception {
		Response<FundWithdrawTipsResponse> response = new Response<FundWithdrawTipsResponse>(request);
		try {
			if (null != request.getBody()) {
				FundWithdrawTipsRequest tipsRequest = request.getBody().getParam();
				List<FundWithdrawTips> list = fundWithdrawTipsServiceImpl.searchTipsByCondition(tipsRequest.getTips());
				FundWithdrawTipsResponse tipsResponse = new FundWithdrawTipsResponse();
				tipsResponse.setTipsList(list);
				response.setResult(tipsResponse);
			}
		} catch (Exception e) {
			log.error("Fund WithdrawLog addLog error:", e);
			throw e;
		}
		return response;
	}
	
	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertTips")
	@ResponseBody
	public Response<Object> insertTips(@RequestBody @ValidRequestBody Request<FundWithdrawTips> request)
			throws Exception {
		log.debug("------------- insertTips ");
		Response<Object> response = new Response<Object>(request);
		try {
			if (null != request.getBody()) 
			{
				FundWithdrawTips tip = request.getBody().getParam();
				final Long userId = request.getHead().getUserId();
				final String userAccount = request.getHead().getUserAccount();
				tip.setCreator(userId);
				tip.setModifyTime(DateUtils.currentDate());
				tip.setModifyUser(userAccount);
				if(StringUtils.isEmpty(tip.getCreateUser())){
					tip.setCreateTime(DateUtils.currentDate());
					tip.setCreateUser(userAccount);							
				}
				
				fundWithdrawTipsServiceImpl.insertTip(tip);
				response.getHead().setStatus(1L);
			}
		} catch (Exception e) {
			log.error("Fund WithdrawLog addLog error:", e);
			throw e;
		}
		return response;
	}
	
	
	
	/**
	 * 審核提示配置資料撈取
	 * search tips by conditions
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchDrawTips")
	@ResponseBody
	public Response<FundWithdrawTipsResponse> searchDrawTips(@RequestBody @ValidRequestBody Request<FundWithdrawTipsRequest> request)
			throws Exception {
		Response<FundWithdrawTipsResponse> response = new Response<FundWithdrawTipsResponse>(request);
		try {
			if (null != request.getBody()) {
				FundWithdrawTipsRequest tipsRequest = request.getBody().getParam();
				FundWithdrawTips tips =tipsRequest.getTips();
				tips.setTipsGroupa(FundLogEnum.TipsGroupA.UNPASS.getCode());
				//取得審核配置未處理資料
				List<FundWithdrawTips> unChecklist = fundWithdrawTipsServiceImpl.searchTipsByCondition(tipsRequest.getTips());
				//取得審核配置未處理分類最大筆數
				Integer unCheckMaxGroupBSize = fundWithdrawTipsServiceImpl.getGroupBCount(tips);
				
				//待複審
				tips.setTipsGroupa(FundLogEnum.TipsGroupA.UNDER_CHEKC.getCode());
				List<FundWithdrawTips> reviewlist = fundWithdrawTipsServiceImpl.searchTipsByCondition(tipsRequest.getTips());
				Integer retriveMaxGroupBSize = fundWithdrawTipsServiceImpl.getGroupBCount(tips);
				FundWithdrawTipsResponse tipsResponse = new FundWithdrawTipsResponse();
				
				tipsResponse.setRevieMaxSize(retriveMaxGroupBSize);
				tipsResponse.setUncheckMaxSize(unCheckMaxGroupBSize);
				fundWithdrawTipsServiceImpl.processDrawTipsData(tipsResponse, unChecklist, reviewlist);
				response.setResult(tipsResponse);
			}
		} catch (Exception e) {
			log.error("Fund WithdrawLog addLog error:", e);
			throw e;
		}
		return response;
	}
	
	/**
	 * 申訴提示配置資料撈取
	 * search tips by conditions
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchAppealTips")
	@ResponseBody
	public Response<FundWithdrawTipsResponse> searchAppealTips(@RequestBody @ValidRequestBody Request<FundWithdrawTipsRequest> request)
			throws Exception {
		Response<FundWithdrawTipsResponse> response = new Response<FundWithdrawTipsResponse>(request);
		try {
			if (null != request.getBody()) {
				FundWithdrawTipsRequest tipsRequest = request.getBody().getParam();
				FundWithdrawTips tips =tipsRequest.getTips();
				//未通過
				tips.setTipsGroupb(FundLogEnum.TipsGroupB.UNPASS.getCode());
				List<FundWithdrawTips> unPassTips = fundWithdrawTipsServiceImpl.searchTipsByCondition(tipsRequest.getTips());
				//通過
				tips.setTipsGroupb(FundLogEnum.TipsGroupB.PASS.getCode());
				List<FundWithdrawTips> passTips = fundWithdrawTipsServiceImpl.searchTipsByCondition(tipsRequest.getTips());
				
				//待複審
				tips.setTipsGroupb(FundLogEnum.TipsGroupB.UNDER_CHEKC.getCode());
				List<FundWithdrawTips> reviewTips = fundWithdrawTipsServiceImpl.searchTipsByCondition(tipsRequest.getTips());
				
				//待定
				tips.setTipsGroupb(FundLogEnum.TipsGroupB.WAIT.getCode());
				List<FundWithdrawTips> waitTips = fundWithdrawTipsServiceImpl.searchTipsByCondition(tipsRequest.getTips());
				
				
				FundWithdrawTipsResponse tipsResponse = new FundWithdrawTipsResponse();
				tipsResponse.setAppealUnPassTips(unPassTips);
				tipsResponse.setAppealPassTips(passTips);
				tipsResponse.setAppealReviewTips(reviewTips);
				tipsResponse.setAppealWaitTips(waitTips);
				response.setResult(tipsResponse);
			}
		} catch (Exception e) {
			log.error("Fund WithdrawLog addLog error:", e);
			throw e;
		}
		return response;
	}
	
}
