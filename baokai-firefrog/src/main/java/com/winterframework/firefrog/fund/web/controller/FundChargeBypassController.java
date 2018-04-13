package com.winterframework.firefrog.fund.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.fund.dao.vo.FundChargeBypassVO;
import com.winterframework.firefrog.fund.service.IFundChargeBypassService;
import com.winterframework.firefrog.fund.web.dto.FundChargeBypassRequest;
import com.winterframework.firefrog.fund.web.dto.FundChargeBypassResponse;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.service.IUserProfileService;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/**
 * @ClassName: FundBypassController
 * 
 */
@Controller("fundChargeBypassController")
@RequestMapping(value = "/fund/bypass")
public class FundChargeBypassController {

	private static Logger log = LoggerFactory.getLogger(FundChargeBypassController.class);
	
	private static  Integer PAGE_SIZE = 10;
	
	@Resource(name = "fundChargeBypassServiceImpl")
	private IFundChargeBypassService fundChargeBypassServiceImpl;
	
	@Autowired
	private IUserProfileService userProfileService;
	
	/**
	 * 
	 * @Title: queryWhiteList
	 * @Description: 查询分流設置白名單
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryBypassWhiteList")
	@ResponseBody
	public Response<FundChargeBypassResponse> queryBypassWhiteList(
			@RequestBody Request<FundChargeBypassRequest> request) throws Exception {
		log.info("--------------FundChargeBypassController-----------------");
		Response<FundChargeBypassResponse> response = new Response<FundChargeBypassResponse>(request);
		try {
			if (null != request.getBody()) {
				FundChargeBypassRequest req = request.getBody().getParam();
				Pager pager = new Pager();
				if(req.getPageNo()==null){
					req.setPageNo(1l);
				}
				
				int startNo = (int) ((req.getPageNo()) * PAGE_SIZE + 1l);
				pager.setStartNo(startNo);
				pager.setEndNo(PAGE_SIZE + startNo - 1);
				PageRequest<FundChargeBypassRequest> pageRequest = PageConverterUtils.getRestPageRequest(startNo, PAGE_SIZE + startNo - 1);		
				pageRequest.setSearchDo(req);
				
				Page<FundChargeBypassVO> results = fundChargeBypassServiceImpl.getAllByPage(pageRequest);
				
				FundChargeBypassResponse res = new FundChargeBypassResponse();
				res.setBypassList(results.getResult());
				ResultPager resultPager = new ResultPager();
				resultPager.setStartNo(results.getThisPageFirstElementNumber());
				resultPager.setEndNo(results.getThisPageLastElementNumber());
				resultPager.setTotal(results.getTotalCount());
				response.setResultPage(resultPager);
				response.setResult(res);
			}
		} catch (Exception e) {
			log.error(" queryBypassWhiteList error:", e);
			throw e;
		}
		return response;
	}
	
	@RequestMapping(value="/deleteBypassWhiteListData")
	public void deleteBypassWhiteListData(@RequestBody @ValidRequestBody Request<FundChargeBypassRequest> request) throws Exception {
		if (null != request.getBody()) {
			fundChargeBypassServiceImpl.deleteById(request.getBody().getParam().getDeleteId());
		}
	}
	
	@RequestMapping(value="/isAccountExist")
	@ResponseBody
	public Response<FundChargeBypassResponse> isAccountExist(@RequestBody @ValidRequestBody Request<FundChargeBypassRequest> request) throws Exception {
		Response<FundChargeBypassResponse> response = new Response<FundChargeBypassResponse>(request);
		//User usr = userProfileService.queryUserByName(request.getBody().getParam().getAccount());
		String isExist = fundChargeBypassServiceImpl.isAccountExist(request.getBody().getParam().getAccount(),request.getBody().getParam().getChargeWaySet());
		FundChargeBypassResponse res = new FundChargeBypassResponse();
		res.setIsExist(isExist);
		response.setResult(res);
		return response;
	}

	@RequestMapping(value = "/saveWhiteList")
	@ResponseBody
	public void saveWhiteList(@RequestBody @ValidRequestBody Request<FundChargeBypassRequest> request)
			throws Exception {
		try {
			if (null != request.getBody()) {
				Long chargeChannel = request.getBody().getParam().getChargeChannel();
				Long chargeWaySet = request.getBody().getParam().getChargeWaySet();
				String userAccount = request.getBody().getParam().getAccount();
				String memo = request.getBody().getParam().getMemo();
				FundChargeBypassVO fundChargeBypassVO = new FundChargeBypassVO();
				fundChargeBypassVO.setChargeChannel(chargeChannel);
				fundChargeBypassVO.setCreateTime(DateUtils.currentDate());
				fundChargeBypassVO.setDeleteFlag("N");
				fundChargeBypassVO.setMemo(memo);
				fundChargeBypassVO.setUserAccount(userAccount);
				fundChargeBypassVO.setChargeWaySet(chargeWaySet);
				fundChargeBypassServiceImpl.saveWhiteList(fundChargeBypassVO);
				
			}
		} catch (Exception e) {
			log.error("saveQuestion error:", e);
			throw e;
		}
	}
}
