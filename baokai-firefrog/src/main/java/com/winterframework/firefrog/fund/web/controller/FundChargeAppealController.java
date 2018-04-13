package com.winterframework.firefrog.fund.web.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.fund.dao.IFundChargeDao;
import com.winterframework.firefrog.fund.dao.vo.FundCharge;
import com.winterframework.firefrog.fund.entity.FundChargeAppeal;
import com.winterframework.firefrog.fund.entity.FundChargeException;
import com.winterframework.firefrog.fund.entity.FundChargeOrder;
import com.winterframework.firefrog.fund.entity.FundChargeOrder.Status;
import com.winterframework.firefrog.fund.service.IFundChargeAppealService;
import com.winterframework.firefrog.fund.service.IFundChargeService;
import com.winterframework.firefrog.fund.service.IFundExceptionService;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.firefrog.fund.web.dto.ChargeQueryRequest;
import com.winterframework.firefrog.fund.web.dto.ExceptionQueryRequest;
import com.winterframework.firefrog.fund.web.dto.FundChargeAppealRequest;
import com.winterframework.firefrog.fund.web.dto.FundChargeAppealResponse;
import com.winterframework.firefrog.fund.web.dto.FundChargeAppealResult;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/**
 * @ClassName: FundChargeAppealController
 * 
 */
@Controller("fundChargeAppealController")
@RequestMapping(value = "/fund")
public class FundChargeAppealController {

	private static Logger logger = LoggerFactory
			.getLogger(FundBankController.class);

	@Resource(name = "fundChargeAppealServiceImpl")
	private IFundChargeAppealService fundChargeAppealService;
	
	@Resource(name = "fundExceptionServiceImpl")
	private IFundExceptionService fundExceptionService;
	
	@Resource(name = "fundChargeServiceImpl")
	private IFundChargeService fundChargeService;
	
	@Resource(name = "fundChargeDaoImpl")
	private IFundChargeDao fundChargeDao;
	
	private final Long SUCCESS = 0L;
	
	private final Long FAIL = 1L;

	/**
	 * 
	 * @Title: queryRechargeList
	 * @Description: 查询可申诉充值单
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/appeal/queryRechargeList")
	@ResponseBody
	public Response<List<FundChargeAppealResponse>> queryRechargeList(
			@RequestBody Request<FundChargeAppealRequest> request)
			throws Exception {
		logger.info("/fundAppeal/queryRechargeList");
		Response<List<FundChargeAppealResponse>> response = new Response<List<FundChargeAppealResponse>>(
				request);
		try {
			if (null != request.getBody()) {
				Long userId = request.getHead().getUserId();
				Pager pager = request.getBody().getPager();
				FundChargeAppealResult result = fundChargeAppealService
						.queryCanAppealRechargeList(userId, pager);
				response.setResult(result.getResponses());
				ResultPager resultPager = new ResultPager(pager.getStartNo(),
						pager.getEndNo(), result.getTotalCount().intValue());
				response.setResultPage(resultPager);
				response.getHead().setStatus(SUCCESS);
			}

		} catch (Exception e) {
			logger.error("queryRechargeList", e);
			response.getHead().setStatus(FAIL);
		}
		return response;
	}

	/**
	 * 
	 * @Title: appealRecharge
	 * @Description: 充值申诉
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/appeal/appealRecharge")
	@ResponseBody
	public Response<List<FundChargeAppealResponse>> appealRecharge(
			@RequestBody Request<FundChargeAppealRequest> request)
			throws Exception {
		logger.info("/fundAppeal/appealRecharge");
		Response<List<FundChargeAppealResponse>> response = new Response<List<FundChargeAppealResponse>>(
				request);
		try {
			if (null != request.getBody()) {
				FundChargeAppealRequest param = request.getBody().getParam();
				
				CountPage<FundChargeOrder> cp = null;
				if(StringUtils.isNotBlank(param.getChargeSn())){
					ChargeQueryRequest charge = new ChargeQueryRequest();
					charge.setSn(param.getChargeSn());
					charge.setStatus(new Long[]{2L});
					PageRequest<ChargeQueryRequest> pr = new PageRequest<ChargeQueryRequest>();
					pr.setSearchDo(charge);
					//先查看是否已經支付成功
					cp = fundChargeDao.queryFundCharge(pr);
					
				}

				if(cp!=null && cp.getResult() != null && cp.getResult().size()>0){
					response.getHead().setStatus(FAIL);
				}else{
					//有交易流水號就自動匹配
					boolean check = false;
					if(StringUtils.isNotBlank(param.getTransactionNum())
							&& StringUtils.isNotBlank(param.getChargeSn())){
						check = this.checkFundChargeException(param);
					}
					
					
					if(!check){
						fundChargeAppealService.addRechargeAppeal(param);
						response.getHead().setStatus(SUCCESS);
					}else{
						response.getHead().setStatus(FAIL);
					}
					
					
				}

			}
			
		} catch (Exception e) {
			logger.error("appealRecharge", e);
			response.getHead().setStatus(FAIL);
		}
		return response;
	}
	
	/**
	 * 
	 * @Title: appealReview
	 * @Description: 充值申诉审核(update infos by appealSn)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/appeal/review")
	@ResponseBody
	public Response<Object> appealReview(
			@RequestBody Request<FundChargeAppeal> request)
			throws Exception {
		Response<Object> response = new Response<Object>(
				request);
		try {
			if (null != request.getBody()) {
				final String userAct = request.getHead().getUserAccount();
				FundChargeAppeal param = request.getBody().getParam();
				Date date = new Date();
				param.setAppealAcct(userAct);
				param.setReviewEndTime(date);
				fundChargeAppealService.updateAppeal(param);
			}
			response.getHead().setStatus(SUCCESS);
		} catch (Exception e) {
			logger.error("appealRecharge", e);
			response.getHead().setStatus(FAIL);
		}
		return response;
	}
	
	/**
	 * 
	 * @Title: appealReview
	 * @Description: 充值申诉审核(update infos by appealSn)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/appeal/queryAppealCountsByStatus")
	@ResponseBody
	public Response<Object> queryAppealCountsByStatus(
			@RequestBody Request<Object> request)
			throws Exception {
		Response<Object> response = new Response<Object>(
				request);
		try {
			Long count = 0L;
			if (null != request.getBody()) {
				
				Integer status = Integer.parseInt(request.getBody().getParam().toString());
				logger.debug("status : " + status);
				count = fundChargeAppealService.queryAppealCountsByStatus(status);
			}
			response.getBody().setResult(count);
		} catch (Exception e) {
			logger.error("appealRecharge", e);
			response.getBody().setResult(0);
		}
		return response;
	}
	
	private boolean checkFundChargeException(FundChargeAppealRequest param)throws Exception {
		
		try {

				PageRequest<ExceptionQueryRequest> pr= new PageRequest<ExceptionQueryRequest>();
				ExceptionQueryRequest er = new ExceptionQueryRequest();
				er.setBaseInfo(param.getTransactionNum());
				er.setRcvBank(param.getBankId()==null?"":param.getBankId().toString());
				er.setStatus(new Long[] {0L});
				pr.setSearchDo(er);
				
				Page<FundChargeException> page = fundExceptionService.queryException(pr);
				
				if(page.getResult()!=null && page.getResult().size()>0){
					FundChargeException fx = page.getResult().get(0);
					
					
					FundChargeOrder order = fundChargeService.queryById(param.getChargeSn());
					//客户提交指令号，与平台异常订单指令号一致
					//客户银行到账时间必须晚于该笔充值发起时间
					if(fx.getMcExactTime().compareTo(order.getApplyTime())>-1){
						logger.info("自動匹配"+" userid:"+param.getUserId()
								+" ChargeSn:"+param.getChargeSn()
								+" exMcSn:"+fx.getMcSn()+" exSn:"+fx.getSn());
						
						fx.setUserId(param.getUserId());
						fx.setApplyTime(new Date());
						fx.setApplyAccount("自动匹配");
						fx.setApprMemo(param.getUserAccount());//自動匹配用戶名顯示在備註
						//加幣
						fundExceptionService.exceptionAddCoin(fx,com.winterframework.firefrog.fund.web.controller.charge.Status.untreated);
						//更新異常充值狀態
						//fundChargeService.updateOrder(order.getId(), Status.SUCCESS);
						FundCharge fundcharge = new FundCharge();
						fundcharge.setSn(param.getChargeSn());
						fundcharge.setStatus(Status.SUCCESS.getValue());
						fundcharge.setChargeTime(new Date());//交易時間
						fundcharge.setRealChargeAmt(fx.getRealChargeAmt());//金額
						fundChargeService.updateFundCharge(fundcharge);
						
						return true;
					}
					
					
				}

				return false;
				
				
			
		} catch (Exception e) {
			logger.error("checkFundChargeException", e);
			return false;
			
		}
		
	}

	
	
}
