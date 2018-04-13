package com.winterframework.firefrog.game.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.game.web.dto.LotteryRecordStruc;
import com.winterframework.firefrog.game.web.dto.LotteryResultStruc;
import com.winterframework.firefrog.game.web.dto.QueryLotteryRecordRequest;
import com.winterframework.firefrog.game.web.dto.QueryLotteryRecordResponse;
import com.winterframework.firefrog.game.web.dto.QueryLotteryResultRequest;
import com.winterframework.firefrog.game.web.dto.QueryLotteryResultResponse;
import com.winterframework.firefrog.game.web.dto.QueryRedEnvelopeRequest;
import com.winterframework.firefrog.game.web.dto.QueryRedEnvelopeResponse;
import com.winterframework.firefrog.game.web.dto.RedEnvelopeStruc;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IActivityAwardConfigService;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IActivityService;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IUserSystemUpdateLogService;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/**
 * 红包活动抽奖后台管理控制器
 * @author david
 *
 */
@Controller("activityAdminController")
@RequestMapping("/gameoa")
public class ActivityAdminController {

	private Logger log = LoggerFactory.getLogger(ActivityAdminController.class);

	@Resource(name = "activityAwardConfigServiceImpl")
	private IActivityAwardConfigService activityAwardConfigServiceImpl;

	@Resource(name = "activityServiceImpl")
	private IActivityService activityServiceImpl;

	@Resource(name = "userSystemUpdateLogServiceImpl")
	private IUserSystemUpdateLogService userSystemUpdateLogServiceImpl;

	/**
	 * 
	* @Title: queryRedEnvelope 
	* @Description: 红包记录查询
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryRedEnvelope")
	@ResponseBody
	public Response<QueryRedEnvelopeResponse> queryRedEnvelope(
			@RequestBody @ValidRequestBody Request<QueryRedEnvelopeRequest> request) throws Exception {
		log.info("queryRedEnvelope  start...");

		Response<QueryRedEnvelopeResponse> response = new Response<QueryRedEnvelopeResponse>();

		int sNo = request.getBody().getPager().getStartNo();
		int eNo = request.getBody().getPager().getEndNo();

		PageRequest<QueryRedEnvelopeRequest> pr = PageConverterUtils.getRestPageRequest(sNo, eNo);
		pr.setSearchDo(request.getBody().getParam());

		Page<RedEnvelopeStruc> page = null;
		QueryRedEnvelopeResponse result = new QueryRedEnvelopeResponse();
		try {
			page = activityServiceImpl.queryRedEnvelope(pr);
			if(page.getOtherCount() !=null){
				result.setRedEnvelopeAmount((Long)page.getOtherCount().get("rewardcount"));
				result.setTotalBetAmount((Long)page.getOtherCount().get("betCount"));
				
			}else{
				result.setRedEnvelopeAmount(0L);
				result.setTotalBetAmount(0L);
			}
			if(page != null){
				result.setRedEnvelopeStruc(page.getResult());
			}
		
			response.setResult(result);
			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageFirstElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			response.setResultPage(pager);

		} catch (Exception e) {
			log.error("queryRedEnvelope error", e);
			throw e;
		}

		log.info("queryRedEnvelope end...");
		return response;
	}
	
	/**
	 * 
	* @Title: queryLotteryRecord 
	* @Description: 抽奖记录查询
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryLotteryRecord")
	@ResponseBody
	public Response<QueryLotteryRecordResponse> queryLotteryRecord(
			@RequestBody @ValidRequestBody Request<QueryLotteryRecordRequest> request) throws Exception {
		log.info("queryRedEnvelope  start...");

		Response<QueryLotteryRecordResponse> response = new Response<QueryLotteryRecordResponse>();

		int sNo = request.getBody().getPager().getStartNo();
		int eNo = request.getBody().getPager().getEndNo();

		PageRequest<QueryLotteryRecordRequest> pr = PageConverterUtils.getRestPageRequest(sNo, eNo);
		pr.setSearchDo(request.getBody().getParam());

		Page<LotteryRecordStruc> page = null;
		QueryLotteryRecordResponse result = new QueryLotteryRecordResponse();
		try {
			page = activityServiceImpl.queryLotteryRecord(pr);
			result.setList(page.getResult());
			response.setResult(result);
			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageFirstElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			response.setResultPage(pager);

		} catch (Exception e) {
			log.error("queryRedEnvelope error", e);
			throw e;
		}

		log.info("queryRedEnvelope end...");
		return response;
	}
	
	/**
	 * 
	* @Title: queryLotteryRecord 
	* @Description: 抽奖记录查询
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryLotteryResult")
	@ResponseBody
	public Response<QueryLotteryResultResponse> queryLotteryResult(
			@RequestBody @ValidRequestBody Request<QueryLotteryResultRequest> request) throws Exception {
		log.info("queryRedEnvelope  start...");

		Response<QueryLotteryResultResponse> response = new Response<QueryLotteryResultResponse>();

		int sNo = request.getBody().getPager().getStartNo();
		int eNo = request.getBody().getPager().getEndNo();

		PageRequest<QueryLotteryResultRequest> pr = PageConverterUtils.getRestPageRequest(sNo, eNo);
		pr.setSearchDo(request.getBody().getParam());

		Page<LotteryResultStruc> page = null;
		QueryLotteryResultResponse result = new QueryLotteryResultResponse();
		try {
			page = activityServiceImpl.queryLotteryResult(pr);
			result.setList(page.getResult());
			response.setResult(result);
			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageFirstElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			response.setResultPage(pager);

		} catch (Exception e) {
			log.error("queryRedEnvelope error", e);
			throw e;
		}

		log.info("queryRedEnvelope end...");
		return response;
	}

}
