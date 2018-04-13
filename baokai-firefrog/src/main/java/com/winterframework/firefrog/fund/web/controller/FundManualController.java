package com.winterframework.firefrog.fund.web.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.active.dao.IActivityLogDao;
import com.winterframework.firefrog.active.dao.vo.Activity;
import com.winterframework.firefrog.active.dao.vo.ActivityLog;
import com.winterframework.firefrog.active.dao.vo.ActivityWinningLog;
import com.winterframework.firefrog.active.service.IActivityAwardsService;
import com.winterframework.firefrog.active.web.dto.ActivityAwardsApplyRequest;
import com.winterframework.firefrog.active.web.dto.ActivityAwardsRequest;
import com.winterframework.firefrog.active.web.dto.ActivityRequest;

import com.winterframework.firefrog.common.annotation.ValidRequestHeader;
import com.winterframework.firefrog.common.convert.BeanConverter;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.common.util.UserTools;
import com.winterframework.firefrog.fund.dao.vo.FundBank;
import com.winterframework.firefrog.fund.dao.vo.VOConverter;
import com.winterframework.firefrog.fund.entity.BankCard;
import com.winterframework.firefrog.fund.entity.FundChargeOrder;
import com.winterframework.firefrog.fund.entity.FundManualOrder;
import com.winterframework.firefrog.fund.exception.FundBalanceShortageException;
import com.winterframework.firefrog.fund.exception.FundChangedException;
import com.winterframework.firefrog.fund.exception.FundManualDeposRepeat;
import com.winterframework.firefrog.fund.exception.FundPTNoUserException;
import com.winterframework.firefrog.fund.exception.FundWithdrawHighException;
import com.winterframework.firefrog.fund.exception.FundWithdrawLowException;
import com.winterframework.firefrog.fund.exception.FundWithdrawTooMuchException;
import com.winterframework.firefrog.fund.service.IBankCardService;
import com.winterframework.firefrog.fund.service.IFundAtomicOperationService;
import com.winterframework.firefrog.fund.service.IFundManualDepositService;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.firefrog.fund.web.controller.vo.CountResultPager;
import com.winterframework.firefrog.fund.web.controller.vo.FundChangeDetail;
import com.winterframework.firefrog.fund.web.controller.vo.FundGameVo;
import com.winterframework.firefrog.fund.web.dto.ChargeQueryRequest;
import com.winterframework.firefrog.fund.web.dto.ChargeStruc;
import com.winterframework.firefrog.fund.web.dto.DepositApplyRequest;
import com.winterframework.firefrog.fund.web.dto.DepositAuditRequest;
import com.winterframework.firefrog.fund.web.dto.DepositQueryRequest;
import com.winterframework.firefrog.fund.web.dto.DepositRemarkRequest;
import com.winterframework.firefrog.fund.web.dto.RemitStruc;
import com.winterframework.firefrog.fund.web.dto.UserBankStruc;
import com.winterframework.firefrog.user.entity.LevelRecycleDTO;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.service.IUserProfileService;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/**
 * 
* @ClassName: FundManualDepositController 
* @Description: 人工打款接口
* @author 你的名字 
* @date 2013-8-5 下午4:05:01 
*
 */
@Controller
@RequestMapping(value = "/fund")
public class FundManualController {

	private static Logger logger = LoggerFactory.getLogger(FundManualController.class);
	@Resource(name = "bankCardServiceImpl")
	private IBankCardService bankCardService;
	
	@Resource(name = "activityAwardsImpl")
	private IActivityAwardsService activityAwardsImpl;

	@Resource(name = "RedisClient")
	private RedisClient redisClient;

	public FundManualController() {
	}

	@Resource(name = "fundManualDepositService")
	private IFundManualDepositService fundManualDepositService;
	
	@Resource(name = "RedisClient")
	private RedisClient rc;
	
	

	/**
	 * 
	* @Title: depositQuery 
	* @Description: 人工单查询
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/depositQuery")
	@ResponseBody
	public Response<List<RemitStruc>> depositQuery(@RequestBody @ValidRequestHeader Request<DepositQueryRequest> request)
			throws Exception {
		Response<List<RemitStruc>> response = new Response<List<RemitStruc>>(request);

		PageRequest<DepositQueryRequest> pageReq = PageConverterUtils.getRestPageRequest(request.getBody().getPager()
				.getStartNo(), request.getBody().getPager().getEndNo());
		DepositQueryRequest req = request.getBody().getParam();

		pageReq.setSearchDo(req);
		pageReq.setSortColumns("APPLY_TIME DESC");
		List<RemitStruc> remitList = new ArrayList<RemitStruc>();
		try {
			Page<FundManualOrder> page = fundManualDepositService.query(pageReq);
			List<FundManualOrder> orderList = page.getResult();
			response.setResult(remitList);
			for (FundManualOrder order : orderList) {
				RemitStruc remit = new RemitStruc();
				BeanConverter.convert(remit, order);
				remit.setApplyAccount(order.getApplyUser().getAccount());
				remit.setApprAccount(order.getApprover());
				remit.setApprTime(order.getApproveTime());
				remit.setRealWithdrawAmt(order.getRealDepositAmt());
				remit.setVipLvl(order.getVipLvl());
				BankCard card = order.getUserBank();
				UserBankStruc struc = new UserBankStruc();
				if (card != null) {
					struc.setBankAccount(card.getAccountHolder());
					if (card.getBank() != null) {
						struc.setBankId(card.getBank().getId());
					}
					struc.setBankNumber(card.getBankCardNo());
				}
				remit.setUserBankStruc(struc);
				remit.setId(order.getId());
				remitList.add(remit);
				ResultPager rp = new ResultPager();
				rp.setStartNo(page.getThisPageFirstElementNumber());
				rp.setEndNo(page.getThisPageLastElementNumber());
				rp.setTotal(page.getTotalCount());
				response.setResultPage(rp);
			}
		} catch (Exception e) {
			logger.error("Deposit query error.", e);
			throw e;
		}

		return response;
	}

	/**
	 * 
	* @Title: depositRemark 
	* @Description: 审核备注
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/depositRemark")
	@ResponseBody
	public Response<Object> depositRemark(@RequestBody @ValidRequestHeader Request<DepositRemarkRequest> request)
			throws Exception {
		Response<Object> response = new Response<Object>(request);
		DepositRemarkRequest param = request.getBody().getParam();
		try {
			fundManualDepositService.remark(param.getTypeId(), param.getMemo());
		} catch (Exception e) {
			logger.error("Deposit remark error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: depositAudit 
	* @Description: 审核
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/depositAudit")
	@ResponseBody
	public Response<Object> depositAudit(@RequestBody @ValidRequestHeader Request<DepositAuditRequest> request)
			throws Exception {

		Response<Object> response = new Response<Object>(request);
		String key = "FUND_DEPOSIT_AUDITS_"+request.getHead().getUserId();
		if(!redisClient.exists(key)){
			try {
				fundManualDepositService.audit(UserTools.getUserFromHead(request), request.getBody().getParam());
			} catch (FundManualDeposRepeat e){ 
				logger.error(FundManualDeposRepeat.MSG, e);
				response.getHead().setStatus(e.getStatus());
			} catch (FundBalanceShortageException e) {
				logger.error(FundBalanceShortageException.MSG, e);
				response.getHead().setStatus(e.getStatus());
			} catch (FundChangedException e) {
				logger.error(FundChangedException.MSG, e);
				response.getHead().setStatus(e.getStatus());
			}  catch (FundPTNoUserException e) {
				logger.error(FundPTNoUserException.MSG+":"+e.getAccount(), e);
				response.getHead().setStatus(e.getStatus());
				response.setResult(e.getAccount());
			} catch (Exception e) {
				logger.error("Deposit audit error.", e);
				throw e;
			}finally{
				redisClient.del(key);
			}
		}else{
			Exception e = new Exception("短时间内重复审核");
			logger.error("Deposit audit error. re send in the short time.", e);
			throw e;
		}
		return response;
	}
	@RequestMapping(value = "/depositAudits")
	@ResponseBody
	public Response<Object> depositAudits(@RequestBody @ValidRequestHeader Request<List<DepositAuditRequest>> request)
			throws Exception {
		Response<Object> response = new Response<Object>(request);
		String key = "FUND_DEPOSIT_AUDITS_"+request.getHead().getUserId();
		if(!redisClient.exists(key)){
			redisClient.set(key,"true",5);
			try {
				for(DepositAuditRequest dr:request.getBody().getParam()){
					fundManualDepositService.audit(UserTools.getUserFromHead(request), dr);
				}
			} catch (FundManualDeposRepeat e){ 
				logger.error(FundManualDeposRepeat.MSG, e);
				response.getHead().setStatus(e.getStatus());
			} catch (FundBalanceShortageException e) {
				logger.error(FundBalanceShortageException.MSG, e);
				response.getHead().setStatus(e.getStatus());
			} catch (FundChangedException e) {
				logger.error(FundChangedException.MSG, e);
				response.getHead().setStatus(e.getStatus());
			} catch (FundPTNoUserException e) {
				logger.error(FundPTNoUserException.MSG+":"+e.getAccount(), e);
				response.getHead().setStatus(e.getStatus());
				response.setResult(e.getAccount());
			} catch (Exception e) {
				logger.error("Deposit audit error.", e);
				throw e;
			}finally{
				redisClient.del(key);
			}
		}else{
			Exception e = new Exception("短时间内重复审核");
			logger.error("Deposit audit error. re send in the short time.", e);
			throw e;
		}

		return response;
	}
	@Resource(name = "userProfileServiceImpl")
	protected IUserProfileService userProfile;

	/**
	 * 
	* @Title: depositApply 
	* @Description: 打款请求
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/depositApply")
	@ResponseBody
	public Response<Object> depositApply(@RequestBody @ValidRequestHeader Request<DepositApplyRequest> request)
			throws Exception {

		Response<Object> response = new Response<Object>(request);
		User us = UserTools.getUserFromHead(request);
		DepositApplyRequest param = request.getBody().getParam();

		FundManualOrder deposit = new FundManualOrder(param.getTypeId());
		BeanConverter.convert(deposit, param);
		deposit.setType(VOConverter.manualLong2Type(param.getTypeId()));
		deposit.setApplyUser(us);
		deposit.setApplyTime(new Date());
		User rcvUser = userProfile.queryUserByName(deposit.getRcvAccount());
		deposit.setRcvId(rcvUser.getId());
		if (param.getUserBankStruc() != null) {

			BankCard card = new BankCard();
			card.setAccountHolder(param.getUserBankStruc().getBankAccount());
			card.setBankCardNo(String.valueOf(param.getUserBankStruc().getBankNumber()));
			FundBank bank = new FundBank();
			bank.setId(param.getUserBankStruc().getBankId());
			card.setBank(bank);
			deposit.setUserBank(card);
		}
		if (param.getUserBankStruc() == null && param.getBankId() != null) {
			BankCard card = new BankCard();
			FundBank bank = new FundBank();
			bank.setId(param.getBankId());
			card.setBank(bank);
			deposit.setUserBank(card);
			/*List<BankCard> result = bankCardService.queryBoundBankCard(deposit.getRcvId(), null);
			if (result != null && result.size() > 0) {
				for(BankCard bc:result){
					if(bc.getMownecumId().equals(param.getBankId())){
						card.setAccountHolder(bc.getAccountHolder());
						card.setBankCardNo(bc.getBankCardNo());
					}					
				}
			} */
		}
		try {
			fundManualDepositService.apply(deposit);
		} catch (FundWithdrawLowException e) {
			logger.error(FundWithdrawLowException.MSG, e);
			response.getHead().setStatus(e.getStatus());
		} catch (FundWithdrawHighException e) {
			logger.error(FundWithdrawHighException.MSG, e);
			response.getHead().setStatus(e.getStatus());
		} catch (FundBalanceShortageException e) {
			logger.error(FundBalanceShortageException.MSG, e);
			response.getHead().setStatus(e.getStatus());
		} catch (FundWithdrawTooMuchException e) {
			logger.error(FundWithdrawTooMuchException.MSG, e);
			response.getHead().setStatus(e.getStatus());
		} catch (FundChangedException e) {
			logger.error(FundChangedException.MSG, e);
			response.getHead().setStatus(e.getStatus());
		} catch (Exception e) {
			logger.error("Deposit apply error.", e);
			throw e;
		}
		return response;
	}

	@RequestMapping(value = "/depositApplys")
	@ResponseBody
	public Response<Object> depositApplys(@RequestBody @ValidRequestHeader Request<List<DepositApplyRequest>> request)
			throws Exception {

		logger.info("=批量上傳:/fund/depositApplys Start"); 
		
		List<DepositApplyRequest> list = request.getBody().getParam();
		
		for(DepositApplyRequest model : list){
			
			if(model.getTypeId() == 14L){
				model.setNote("补派奖金"); 
			}
			
			logger.info("=model : Attach: "+model.getAttach()); 
			logger.info("=model : Memo: "+model.getMemo());
			logger.info("=model : Note: "+model.getNote());
			logger.info("=model : RcvAct: "+model.getRcvAct());
			logger.info("=model : BankId: "+model.getBankId());
			logger.info("=model : DepositAmt: "+model.getDepositAmt());
			logger.info("=model : RcvId: "+model.getRcvId());
			logger.info("=model : TypeId: "+model.getTypeId());
			logger.info("=model : UserBankStruc: "+model.getUserBankStruc());
		}
		
		Response<Object> response = new Response<Object>(request);
		User us = UserTools.getUserFromHead(request);
		List<DepositApplyRequest> params = request.getBody().getParam();
		for (DepositApplyRequest param : params) {
			FundManualOrder deposit = new FundManualOrder(param.getTypeId());
			deposit.setIsBatch(1L);
			BeanConverter.convert(deposit, param);
			deposit.setType(VOConverter.manualLong2Type(param.getTypeId()));
			deposit.setApplyUser(us);
			deposit.setApplyTime(new Date());
			if(param.getRcvId()==null){
				User rcvUser = userProfile.queryUserByName(deposit.getRcvAccount());
				if(rcvUser==null) continue;
				deposit.setRcvId(rcvUser.getId());
			}else{
				deposit.setRcvId(param.getRcvId());
			}
			if (param.getUserBankStruc() != null) {

				BankCard card = new BankCard();
				card.setAccountHolder(param.getUserBankStruc().getBankAccount());
				card.setBankCardNo(String.valueOf(param.getUserBankStruc().getBankNumber()));
				FundBank bank = new FundBank();
				bank.setId(param.getUserBankStruc().getBankId());
				card.setBank(bank);
				deposit.setUserBank(card);
			}
			if (param.getUserBankStruc() == null && param.getBankId() != null) {
				BankCard card = new BankCard();
				FundBank bank = new FundBank();
				bank.setId(param.getBankId());
				card.setBank(bank);
				deposit.setUserBank(card);
				/*List<BankCard> result = bankCardService.queryBoundBankCard(deposit.getRcvId(), null);
				if (result != null && result.size() > 0) {
					for(BankCard bc:result){
						if(bc.getMownecumId().equals(param.getBankId())){
							card.setAccountHolder(bc.getAccountHolder());
							card.setBankCardNo(bc.getBankCardNo());
						}					
					}
				} */
			}
			try {
				fundManualDepositService.apply(deposit);
			} catch (FundWithdrawLowException e) {
				logger.error(FundWithdrawLowException.MSG, e);
				response.getHead().setStatus(e.getStatus());
			} catch (FundWithdrawHighException e) {
				logger.error(FundWithdrawHighException.MSG, e);
				response.getHead().setStatus(e.getStatus());
			} catch (FundBalanceShortageException e) {
				logger.error(FundBalanceShortageException.MSG, e);
				response.getHead().setStatus(e.getStatus());
			} catch (FundWithdrawTooMuchException e) {
				logger.error(FundWithdrawTooMuchException.MSG, e);
				response.getHead().setStatus(e.getStatus());
			} catch (FundChangedException e) {
				logger.error(FundChangedException.MSG, e);
				response.getHead().setStatus(e.getStatus());
			} catch (Exception e) {
				logger.error("Deposit apply error.", e);
				throw e;
			}
		}
		
		logger.info("=批量上傳:/fund/depositApplys End");
		
		return response;
	}
	/**
	 * 
	* Title: queryActivityDetail 
	* Description: 查詢需要人工派獎的活動
	* param request
	* return
	* throws Exception
	 */
	@RequestMapping(value = "/queryActivityDetail")
	@ResponseBody
	public Response<List<Activity>> queryActivityDetail(@RequestBody @ValidRequestHeader Request<ActivityRequest> request)
			throws Exception {
		
		Response<List<Activity>> response = new Response<List<Activity>>(request);
		List<Activity> result = activityAwardsImpl.queryActivityDetail();
		response.setResult(result);

		return response;
	}
	/**
	 * 
	* Title: queryWinningLogUntreat 
	* Description: 查詢活動得獎名單(未派獎)
	* param request
	* return
	* throws Exception
	 */
	@RequestMapping(value = "/queryWinningLogUntreat")
	@ResponseBody
	public Response<List<ActivityWinningLog>> queryWinningLogUntreat(@RequestBody @ValidRequestHeader Request<ActivityAwardsRequest> request)
			throws Exception {
		
		Response<List<ActivityWinningLog>> response = new Response<List<ActivityWinningLog>>(request);
		ActivityAwardsRequest req = request.getBody().getParam();
		
     
        PageRequest<ActivityAwardsRequest> pageReq = getPageRequest(request);
        pageReq.setSearchDo(req);
        pageReq.setSortColumns("ID");
        try {
	        CountPage<ActivityWinningLog> page = activityAwardsImpl.queryWinningLogUntreat(pageReq);
	        response = fillResponse(response, page, request);
        } catch (Exception e) {
			logger.error("chargeQuery error.", e);
			throw e;
		}

		

		return response;
	}
	/**
	 * 
	* Title: queryWinningLogTreat 
	* Description: 查詢活動得獎名單(已派獎)
	* param request
	* return
	* throws Exception
	 */
	@RequestMapping(value = "/queryWinningLogTreat")
	@ResponseBody
	public Response<List<ActivityWinningLog>> queryWinningLogTreat(@RequestBody @ValidRequestHeader Request<ActivityAwardsRequest> request)
			throws Exception {
		
		Response<List<ActivityWinningLog>> response = new Response<List<ActivityWinningLog>>(request);
		ActivityAwardsRequest req = request.getBody().getParam();
		
     
        PageRequest<ActivityAwardsRequest> pageReq = getPageRequest(request);
        pageReq.setSearchDo(req);
        pageReq.setSortColumns("ID");
        try {
	        CountPage<ActivityWinningLog> page = activityAwardsImpl.queryWinningLogTreat(pageReq);
	        response = fillResponse(response, page, request);
        } catch (Exception e) {
			logger.error("chargeQuery error.", e);
			throw e;
		}

		

		return response;
	}
	
	/**
	 * 
	* Title: awardsApply 
	* Description: 提交派獎
	* param request
	* return
	* throws Exception
	 */
	@RequestMapping(value = "/awardsApply")
	@ResponseBody
	public Long awardsApply(@RequestBody @ValidRequestHeader Request<List<ActivityAwardsApplyRequest>> request)
			throws Exception {
		Long successNum = 0L;
		String key = "/awardsApply-activiyAwards";
		logger.info("--------------/awardsApply-activiyAwards");
		try {
			// 我们服务端要判断用户在100毫秒之内只能有一个订单这个判断不是通过数据库实现，是通过redis来实现
			Long thisTime = new Date().getTime();
			//鎖定只能一名使用者寫入派獎帳變資料
			if (!rc.acquireLock(key,30000)) {
				logger.info("employee submit in same time：userId=" + request.getHead().getUserId());
				throw new Exception();
			} else {

				logger.info("=submit awards:/fund/awardsApply Start"); 
				
				String approver = request.getHead().getUserAccount();
				List<ActivityAwardsApplyRequest> list = request.getBody().getParam();				

				successNum=activityAwardsImpl.awardsAppy(list, approver);

				
				logger.info("=end awards:/fund/awardsApply End，update data=:"+successNum);
			}
			
			
		} catch (Exception e) {
			logger.error("/fund/awardsApply has error：", e);
			throw e;
		}finally{
			//完成後解除鎖定
			rc.releaseLock(key);
		}

		return successNum;
	}	

	private PageRequest<ActivityAwardsRequest> getPageRequest(Request<?> request) {

		PageRequest<ActivityAwardsRequest> pageReqeust = PageConverterUtils.getRestPageRequest(request.getBody()
				.getPager().getStartNo(), request.getBody().getPager().getEndNo());

		return pageReqeust;
	}
	
	private Response<List<ActivityWinningLog>> fillResponse(Response<List<ActivityWinningLog>> response, Page<ActivityWinningLog> page,
			Request<?> request) throws Exception {

		if (null == response) {
			response = new Response<List<ActivityWinningLog>>(request);
		}

		response.setResult(page.getResult());

		CountResultPager pager = new CountResultPager();
		pager.setEndNo(page.getThisPageLastElementNumber());
		pager.setStartNo(page.getThisPageFirstElementNumber());
		pager.setTotal(page.getTotalCount());
		if (page instanceof CountPage) {
			pager.setSum(((CountPage) page).getSum());
		}

		response.setResultPage(pager);

		return response;
	}


}
