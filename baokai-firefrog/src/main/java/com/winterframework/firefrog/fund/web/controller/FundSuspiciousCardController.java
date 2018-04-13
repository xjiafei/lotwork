/**   
* @Title: FundSuspiciousCardController.java 
* @Package com.winterframework.firefrog.fund.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-25 上午10:25:34 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.annotation.ValidRequestHeader;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.common.util.UserTools;
import com.winterframework.firefrog.fund.entity.FundSuspiciousCard;
import com.winterframework.firefrog.fund.service.IFundSuspiciousCardService;
import com.winterframework.firefrog.fund.web.controller.vo.SuspiciousSearch;
import com.winterframework.firefrog.fund.web.dto.DTOConverter;
import com.winterframework.firefrog.fund.web.dto.FundSuspiciousCardRequest;
import com.winterframework.firefrog.fund.web.dto.FundSuspiciousCardResponse;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.service.IUserProfileService;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/** 
* @ClassName: FundSuspiciousCardController 
* @Description: 可疑银行卡Controller 
* @author Alan
* @date 2013-7-25 上午10:25:34 
*  
*/
@Controller("fundSuspiciousCardController")
@RequestMapping("/fund")
public class FundSuspiciousCardController {

	private static final Logger log = LoggerFactory.getLogger(FundSuspiciousCardController.class);

	@Resource(name = "fundSuspiciousCardServiceImpl")
	private IFundSuspiciousCardService fundSuspiciousCardService;

	@Resource(name = "userProfileServiceImpl")
	private IUserProfileService userProfileService;

	/**
	 * 
	* @Title: querySuspiciousCard 
	* @Description: 查询可疑银行卡
	* @param request
	* @return
	* @throws Exception
	 */


	@RequestMapping(value = "/querySuspiciousCards")
	//待处理
	@ResponseBody
	public Object querySuspiciousCard(@RequestBody @ValidRequestHeader Request<SuspiciousSearch> request)
			throws Exception {
		log.info("查询可疑银行卡 start......");

		Response<List<FundSuspiciousCardResponse>> response = new Response<List<FundSuspiciousCardResponse>>(request);

		PageRequest<SuspiciousSearch> pageRequest = PageConverterUtils.getRestPageRequest(request.getBody().getPager()
				.getStartNo(), request.getBody().getPager().getEndNo());
		pageRequest.setSearchDo(request.getBody().getParam());

		Page<FundSuspiciousCard> list = fundSuspiciousCardService.queryFundSuspiciousCardByPage(pageRequest);

		if (null != list && list.getResult() != null && list.getResult().size() > 0) {

			List<FundSuspiciousCardResponse> responseList = new ArrayList<FundSuspiciousCardResponse>();

			for (int i = 0; i < list.getResult().size(); i++) {

				FundSuspiciousCard card = list.getResult().get(i);

				FundSuspiciousCardResponse cardResponse = DTOConverter
						.FundSuspiciousCard2FundSuspiciousCardResponse(card);

				responseList.add(cardResponse);

			}

			response.setResult(responseList);
			ResultPager pager = new ResultPager();
			pager.setEndNo(list.getThisPageLastElementNumber());
			pager.setStartNo(list.getThisPageFirstElementNumber());
			pager.setTotal(list.getTotalCount());

			response.setResultPage(pager);

		} else {

			response.setResult(new ArrayList<FundSuspiciousCardResponse>());

		}

		log.info("查询可疑银行卡 end......");

		return response;
	}
	/**
	 * 
	* @Title: querySuspiciousCard 
	* @Description: 查询可疑银行卡
	* @param request
	* @return
	* @throws Exception
	 */


	@RequestMapping(value = "/checkSuspiciousCard")
	//待处理
	@ResponseBody
	public Object checkOnly(@RequestBody @ValidRequestHeader Request<SuspiciousSearch> request)
			throws Exception {
		log.info("查询可疑银行卡 start......");

		Response<List<FundSuspiciousCardResponse>> response = new Response<List<FundSuspiciousCardResponse>>(request);

		PageRequest<SuspiciousSearch> pageRequest = PageConverterUtils.getRestPageRequest(request.getBody().getPager()
				.getStartNo(), request.getBody().getPager().getEndNo());
		pageRequest.setSearchDo(request.getBody().getParam());

		Page<FundSuspiciousCard> list = fundSuspiciousCardService.queryFundSuspiciousCardByPage(pageRequest);

		if (null != list && list.getResult() != null && list.getResult().size() > 0) {
			response.getHead().setStatus(2011);
		} else {		

		}

		return response;
	}

	/**
	 * 
	* @Title: addSuspiciousCards 
	* @Description: 添加可疑银行卡
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/addSuspiciousCards")
	@ResponseBody
	public Object addSuspiciousCards(@RequestBody @ValidRequestBody Request<FundSuspiciousCardRequest> request)
			throws Exception {
		log.info("添加可疑银行卡 start......");
		@SuppressWarnings("rawtypes")
		Response response = new Response(request);

		try {
			User user=UserTools.getUserFromHead(request);

			FundSuspiciousCardRequest cardRequest = request.getBody().getParam();

			FundSuspiciousCard card = DTOConverter.FundSuspiciousCardRequest2FundSuspiciousCard(cardRequest);
			card.setCreatorAccount(user.getAccount());
			card.setCreateTime(new Date());

			fundSuspiciousCardService.addFundSuspiciousCard(card);
		} catch (Exception e) {
			log.error("添加可疑银行卡 error......", e);
			throw e;
		}

		log.info("添加可疑银行卡 end......");
		return response;
	}

	/**
	 * 
	* @Title: deleteSuspiciousCards 
	* @Description: 删除可疑银行卡
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/deleteSuspiciousCards")
	@ResponseBody
	public Object deleteSuspiciousCards(@RequestBody @ValidRequestBody Request<FundSuspiciousCardRequest> request)
			throws Exception {
		log.info("删除可疑银行卡 start......");
		@SuppressWarnings("rawtypes")
		Response response = new Response(request);

		try {

			Long id = request.getBody().getParam().getId();

			fundSuspiciousCardService.deleteFundSuspiciousCard(id);
		} catch (Exception e) {
			log.error("删除可疑银行卡 error......", e);
			throw e;
		}

		log.info("删除可疑银行卡 end......");
		return response;
	}
}
