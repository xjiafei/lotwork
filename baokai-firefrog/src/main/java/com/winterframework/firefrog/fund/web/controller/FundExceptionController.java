/**   
* @Title: FundExceptionController.java 
* @Package com.winterframework.firefrog.fund.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-16 下午4:40:57 
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

import com.winterframework.firefrog.acl.entity.AclUser;
import com.winterframework.firefrog.common.annotation.ValidRequestHeader;
import com.winterframework.firefrog.common.convert.BeanConverter;
import com.winterframework.firefrog.common.util.AccountTool;
import com.winterframework.firefrog.common.util.UserTools;
import com.winterframework.firefrog.fund.entity.FundChargeException;
import com.winterframework.firefrog.fund.exception.FundChangedException;
import com.winterframework.firefrog.fund.service.IFundExceptionService;
import com.winterframework.firefrog.fund.service.impl.mow.MowQuerywithDrawResponse;
import com.winterframework.firefrog.fund.web.controller.charge.Status;
import com.winterframework.firefrog.fund.web.dto.ExceptionAuditRequest;
import com.winterframework.firefrog.fund.web.dto.ExceptionCoinRequest;
import com.winterframework.firefrog.fund.web.dto.ExceptionConfiscateRequest;
import com.winterframework.firefrog.fund.web.dto.ExceptionMowCheckRequest;
import com.winterframework.firefrog.fund.web.dto.ExceptionMowCheckResponse;
import com.winterframework.firefrog.fund.web.dto.ExceptionQueryRequest;
import com.winterframework.firefrog.fund.web.dto.ExceptionQueryResponse;
import com.winterframework.firefrog.fund.web.dto.ExceptionRefundRequest;
import com.winterframework.firefrog.fund.web.dto.ExceptionRemarkRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/** 
* @ClassName: FundExceptionController 
* @Description: 充值异常的处理接口类
* @author Todg
* @date 2013-7-16 下午4:40:57 
*  
*/
@Controller("fundExceptionController")
@RequestMapping(value = "/fund")
public class FundExceptionController {

	private static Logger logger = LoggerFactory.getLogger(FundChargeController.class);

	@Resource(name = "fundExceptionServiceImpl")
	private IFundExceptionService fundExceptionService;
	
	private final long SUCCESS = 1L;
	
	private final long FAIL = 0L;

	/**
	 * 
	* @Title: exceptionQuery 
	* @Description: 根据查询条件进行异常的查询
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/exceptionQuery")
	@ResponseBody
	public Response<List<ExceptionQueryResponse>> exceptionQuery(
			@RequestBody @ValidRequestHeader Request<ExceptionQueryRequest> request) throws Exception {
		Response<List<ExceptionQueryResponse>> response = new Response<List<ExceptionQueryResponse>>(request);
		ExceptionQueryRequest queryReq = request.getBody().getParam();
		AclUser user = UserTools.getBackUserFromHead(request);
			queryReq.setCurrApprer(null);
			queryException(request, response, queryReq, user);
		return response;
	}

	private void queryException(Request<ExceptionQueryRequest> request,
			Response<List<ExceptionQueryResponse>> response, ExceptionQueryRequest queryReq, AclUser user)
			throws Exception {
		//设置当前处理人

		try {
			PageRequest<ExceptionQueryRequest> pageReq = PageRequest.getRestPageRequest(request.getBody().getPager().getStartNo(), request.getBody().getPager().getEndNo());
			pageReq.setSearchDo(queryReq);
			pageReq.setSortColumns(" nvl(MC_NOTICE_TIME,SYSDATE-999900) desc");
			Page<FundChargeException> page = fundExceptionService.queryException(pageReq);

			List<FundChargeException> list = page.getResult();
			List<ExceptionQueryResponse> reslist = new ArrayList<ExceptionQueryResponse>();
			for (FundChargeException exception : list) {
				ExceptionQueryResponse res = new ExceptionQueryResponse();
				BeanConverter.convert(res, exception);
				reslist.add(res);

			}
			response.setResult(reslist);

			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageLastElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			response.setResultPage(pager);

		} catch (Exception e) {
			logger.error("exceptionQuery error.", e);
			throw e;
		}
	}

	/**
	 * 
	 * @param 预处理
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/yuchuli")
	@ResponseBody
	public Response<Object> yuchuli(@RequestBody @ValidRequestHeader Request<ExceptionRefundRequest> request)
			throws Exception {
		Response<Object> response = new Response<Object>(request);
		try {
			fundExceptionService.yuchuli(request.getBody().getParam().getExceptId(),
					AccountTool.getRealAccount(request.getHead().getUserAccount()), request.getBody().getParam()
							.getCurrStatus());
		} catch (Exception e) {
			logger.error("exceptionRefund error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * @param 预处理结束
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/yuchuliEnd")
	@ResponseBody
	public Response<Object> yuchuliEnd(@RequestBody @ValidRequestHeader Request<ExceptionRefundRequest> request)
			throws Exception {
		Response<Object> response = new Response<Object>(request);
		try {
			fundExceptionService.yuchuliEnd(request.getBody().getParam().getExceptId(), request.getBody().getParam()
					.getCurrStatus());
		} catch (Exception e) {
			logger.error("exceptionRefund error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: exceptionRefund 
	* @Description:  异常处理之退款
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/exceptionRefund")
	@ResponseBody
	public Response<Object> exceptionRefund(@RequestBody @ValidRequestHeader Request<ExceptionRefundRequest> request)
			throws Exception {
		Response<Object> response = new Response<Object>(request);
		ExceptionRefundRequest req = request.getBody().getParam();
		FundChargeException chargeException = new FundChargeException();
		BeanConverter.convert(chargeException, req);
		try {
			chargeException.setApplyTime(new Date());
			chargeException.setUserId(request.getBody().getParam().getUserId());
			chargeException.setAttachment(request.getBody().getParam().getAttachMent());
			chargeException.setApplyMemo(request.getBody().getParam().getApplyMemo());
			chargeException.setApplyAccount(AccountTool.getRealAccount(request.getHead().getUserAccount()));
			fundExceptionService.exceptionRefund(chargeException);
		} catch (Exception e) {
			logger.error("exceptionRefund error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: exceptionRefundAudit 
	* @Description:  异常处理之退款确认
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/exceptionRefundReject")
	@ResponseBody
	public Response<Object> exceptionRefundAudit(@RequestBody @ValidRequestHeader Request<ExceptionAuditRequest> request)
		throws Exception {
	Response<Object> response = new Response<Object>(request);
	ExceptionAuditRequest req = request.getBody().getParam();
	try {
		fundExceptionService.zero(req.getExceptionId());
	} catch (Exception e) {
		logger.error("exceptionRefundAudit error.", e);
		throw e;
	}
	return response;
	}

	/**
	 * 
	* @Title: exceptionAddGameCoin 
	* @Description: 异常处理之加游戏币
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/exceptionAddGameCoin")
	@ResponseBody
	public Response<Object> exceptionAddGameCoin(@RequestBody @ValidRequestHeader Request<ExceptionCoinRequest> request)
			throws Exception {
		Response<Object> response = new Response<Object>(request);
		ExceptionCoinRequest req = request.getBody().getParam();
		FundChargeException chargeException = new FundChargeException();
		BeanConverter.convert(chargeException, req);
		try {
			fundExceptionService.exceptionAddCoin(chargeException, Status.addCoinrefundCheck);
			
			//fundExceptionService.exceptionAddCoinAudit(req.getExceptionId(), req.getStatus());
		} catch (FundChangedException e) {
			response.getHead().setStatus(FundChangedException.CODE);
			logger.error("转账时保存资金，资金记录发生变动", e);
		} catch (Exception e) {
			logger.error("exceptionCoin error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: exceptionConfiscate 
	* @Description: 异常处理之没收
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/exceptionConfiscate")
	@ResponseBody
	public Response<Object> exceptionConfiscate(
			@RequestBody @ValidRequestHeader Request<ExceptionConfiscateRequest> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		ExceptionConfiscateRequest req = request.getBody().getParam();
		FundChargeException chargeException = new FundChargeException();
		BeanConverter.convert(chargeException, req);
		try {
			fundExceptionService.exceptionConfiscate(chargeException);
		} catch (Exception e) {
			logger.error("exceptionConfiscate error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: exceptionRemark 
	* @Description: 修改异常信息备注
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/exceptionRemark")
	@ResponseBody
	public Response<Object> exceptionRemark(@RequestBody @ValidRequestHeader Request<ExceptionRemarkRequest> request)
			throws Exception {
		Response<Object> response = new Response<Object>(request);
		ExceptionRemarkRequest req = request.getBody().getParam();
		try {
			fundExceptionService.exceptionRemark(req.getExceptId(), req.getMemo());
		} catch (Exception e) {
			logger.error("exceptionRemark error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: exceptionAuditRemark 
	* @Description: 修改审核备注
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/exceptionAuditRemark")
	@ResponseBody
	public Response<Object> exceptionAuditRemark(
			@RequestBody @ValidRequestHeader Request<ExceptionRemarkRequest> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		ExceptionRemarkRequest req = request.getBody().getParam();
		try {
			fundExceptionService.exceptionAuditRemark(req.getExceptId(), req.getMemo());
		} catch (Exception e) {
			logger.error("exceptionAuditRemark error.", e);
			throw e;
		}
		return response;
	}
	
	/**
	 * 
	* @Title: checkAndUpdateReFundOrderStatus
	* @Description: call Mow檢查提现申请订单目前狀態並更新
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/checkAndUpdateReFundStatus")
	@ResponseBody
	public Response<ExceptionMowCheckResponse> checkAndUpdateReFundStatus(
			@RequestBody Request<ExceptionMowCheckRequest> request) throws Exception {

		Response<ExceptionMowCheckResponse> response = new Response<ExceptionMowCheckResponse>(request);
		ExceptionMowCheckResponse result = new ExceptionMowCheckResponse();
		try {

			if (null != request.getBody()) {
				ExceptionMowCheckRequest param = request.getBody()
						.getParam();
				String mcSn = param.getMownecumOrderNum();
				String sn = param.getCompanyOrderNum();
				Long exceptionId = param.getExceptionId();
				MowQuerywithDrawResponse mowRes = fundExceptionService.queryMowWithdrawOrderStatus(mcSn, sn);
				if(mowRes!=null){
					fundExceptionService.updateWithdrawalResult(exceptionId,mowRes);
					result.setStatus(mowRes.getStatus());
					result.setErrMsg(mowRes.getError_msg());
					result.setCompanyOrderNum(sn);
					result.setMownecumOrderNum(mcSn);
					result.setExceptionId(exceptionId);
					response.setResult(result);
					response.getHead().setStatus(SUCCESS);
				}else{
					response.getHead().setStatus(FAIL);
				}
			}else{
				response.getHead().setStatus(FAIL);
			}
		} catch (Exception e) {
			logger.error("checkAndUpdateReFundStatus error:", e);
			response.getHead().setStatus(FAIL);
		}  
		return response;
	}
	
}
