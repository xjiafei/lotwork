/**   
* @Title: FundChargeController.java 
* @Package com.winterframework.firefrog.fund.web.controller 
* @Description: 资金充值类
* @author Tod
* @date 2013-7-2 上午10:52:49 
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

import com.winterframework.firefrog.common.annotation.ValidRequestHeader;
import com.winterframework.firefrog.fund.dao.vo.FundChargeAppealVO;
import com.winterframework.firefrog.fund.entity.FundChargeAppeal;
import com.winterframework.firefrog.fund.service.IFundAppealStatusService;
import com.winterframework.firefrog.fund.service.IFundChargeAppealService;
import com.winterframework.firefrog.fund.web.dto.FundRechargeAppealRequest;
import com.winterframework.firefrog.fund.web.dto.FundRechargeAppealResponse;
import com.winterframework.firefrog.fund.web.dto.FundRechargeStrucResponse;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/** 
* @ClassName: FundChargeController 
* @Description: 资金充值类
* @author Tod
* @date 2013-7-2 上午10:52:49 
*  
*/
@Controller("fundRechargeApprealController")
@RequestMapping(value = "/fund")
public class FundRechargeApprealController {

	private static Logger log = LoggerFactory.getLogger(FundRechargeApprealController.class);

	@Resource(name = "fundAppealStatusServiceImpl")
	private IFundAppealStatusService fundAppealStatusService;
	
	@Resource(name = "fundChargeAppealServiceImpl")
	private IFundChargeAppealService fundChargeAppealService;
	/**
	 * 
	* @Title: getRechargeAppreals 
	* @Description: 充值申訴查詢
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/getRechargeAppreals")
	@ResponseBody
	public Response<List<FundRechargeAppealResponse>> getRechargeAppreals(
			@RequestBody @ValidRequestHeader Request<FundRechargeAppealRequest> request) throws Exception {
		Response<List<FundRechargeAppealResponse>> response = new Response<List<FundRechargeAppealResponse>>(request);
		
		try{
			if(null != request.getBody()){
				FundRechargeAppealRequest param = request.getBody().getParam();
				Pager pager = request.getBody().getPager();
				log.debug("-----------------------------------start no : " + pager.getStartNo());
				log.debug("end no : " + pager.getEndNo());
				List<FundRechargeAppealResponse> result = new ArrayList<FundRechargeAppealResponse>();
				
				List<FundChargeAppealVO> list = fundChargeAppealService.queryAppealRechargesByCondition(param, pager);				
				
				if(null != list && list.size() > 0){
					for(FundChargeAppealVO info:list){
						FundRechargeAppealResponse data = new FundRechargeAppealResponse();
						data.setAccount(info.getUserAccount());
						data.setAppealAmt(info.getChargeAmt());
						data.setAppealSn(info.getAppealSn());
						data.setAppealTime(info.getArgueTime());
						log.debug(" date : " + data.getAppealTime());
						data.setIsvip(info.getVipLvl());
						data.setVipLvl(info.getVipLvl());
						data.setReviewer(info.getAppealAcct());
						data.setMemo(info.getAppealMemo());
						data.setReviewEndTime(info.getReviewEndTime());
						data.setReviewStartTime(info.getReviewStartTime());
						data.setStatus(info.getAppealStatus().toString());
						data.setUserlvl(info.getUserLvl());
						data.setReviewMemo(info.getReviewMemo());
						data.setTenpayAccount(info.getTenpayAccount());
						data.setTenpayName(info.getTenpayName());
						result.add(data);
					}
				}			
				response.setResult(result);
				ResultPager resultPager = new ResultPager(pager.getStartNo(),
						pager.getEndNo(), result.size());
				response.setResultPage(resultPager);
				log.debug("-------------------------------------------------------------");
			}
		} catch (Exception e) {
			throw e;
		}	
		return response;
	}
	
	/**
	 * 
	* @Title: getRechargeAppreals 
	* @Description: 充值申訴單查詢
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/getRechargeAppealReview")
	@ResponseBody
	public Response<FundRechargeStrucResponse> getRechargeAppealReview(
			@RequestBody @ValidRequestHeader Request<FundRechargeAppealRequest> request) throws Exception {
		Response<FundRechargeStrucResponse> response = new Response<FundRechargeStrucResponse>(request);
		
		log.debug("---------------------------getRechargeAppealReview----------------------------------");
		try{
			if(null != request.getBody()){
				FundRechargeAppealRequest param = request.getBody().getParam();
				String appealSn = param.getAppealSn();
				log.debug("appealSn : " + appealSn);
				
				FundRechargeStrucResponse result = fundAppealStatusService.queryFundAppeal(appealSn);
				
				
				
				if(null != result){
					//update review_start_time
					FundChargeAppeal fca = new FundChargeAppeal();
					if(result.getFundChargeAppealVO().getReviewStartTime() == null){
						fca.setReviewStartTime(new Date());
						fca.setAppealSn(appealSn);
						fundChargeAppealService.updateAppeal(fca);
					}
					response.setResult(result);
				}				
				log.debug("-------------------------------------------------------------");
			}
		} catch (Exception e) {
			throw e;
		}	
		return response;
	}

}
