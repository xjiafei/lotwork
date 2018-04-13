/**   
* @Title: FundExceptionServiceImpl.java 
* @Package com.winterframework.firefrog.fund.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-16 下午5:29:37 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.config.service.IConfigService;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.AccountTool;
import com.winterframework.firefrog.fund.dao.IFundChargeExceptionDao;
import com.winterframework.firefrog.fund.dao.IFundMowPayDao;
import com.winterframework.firefrog.fund.dao.vo.FundMowPay;
import com.winterframework.firefrog.fund.entity.FundChargeException;
import com.winterframework.firefrog.fund.entity.FundOrder;
import com.winterframework.firefrog.fund.enums.FundModel;
import com.winterframework.firefrog.fund.exception.FundChangedException;
import com.winterframework.firefrog.fund.service.IFundAtomicOperationService;
import com.winterframework.firefrog.fund.service.IFundExceptionService;
import com.winterframework.firefrog.fund.service.IFundWithdrawService;
import com.winterframework.firefrog.fund.service.IPaymentCallbackDispatcher;
import com.winterframework.firefrog.fund.service.impl.change.AbstractFundService;
import com.winterframework.firefrog.fund.service.impl.mow.MowQuerywithDraw;
import com.winterframework.firefrog.fund.service.impl.mow.MowQuerywithDrawResponse;
import com.winterframework.firefrog.fund.service.impl.mow.MowQuerywithDrawResponse.MOW_ORDER_STATUS;
import com.winterframework.firefrog.fund.service.impl.mow.MowWithDraw;
import com.winterframework.firefrog.fund.service.impl.mow.MownecumWithdrawResponseData;
import com.winterframework.firefrog.fund.service.impl.mow.WithdrawConfirmRequest;
import com.winterframework.firefrog.fund.util.ISNGenerator;
import com.winterframework.firefrog.fund.util.NumUtil;
import com.winterframework.firefrog.fund.web.controller.charge.Status;
import com.winterframework.firefrog.fund.web.controller.vo.FundGameVo;
import com.winterframework.firefrog.fund.web.dto.ExceptionQueryRequest;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.security.MD5Encrypt;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName: FundExceptionServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-16 下午5:29:37 
*  
*/
@Service("fundExceptionServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class FundExceptionServiceImpl extends AbstractFundService implements IFundExceptionService {

	@Resource(name = "fundChargeExceptionDao")
	private IFundChargeExceptionDao fundChargeExceptionDao;


	@Resource(name = "fundWithdrawService")
	private IFundWithdrawService fundWithdrawService;
	@Resource(name = "fundChangeServiceImpl")
	protected IFundAtomicOperationService fundChangeService;

	@PropertyConfig(value = "withdraw_url")
	private String mcUrl;

	@PropertyConfig(value = "company_id")
	private String companyId;
	
	@PropertyConfig(value = "querywithdral")
	private String querywithdral;

	@Resource(name = "configServiceImpl")
	private IConfigService configService;

	@Resource(name = "SNUtil")
	private ISNGenerator snUtil;

	@Resource(name = "HttpJsonClientImpl")
	private IHttpJsonClient mcwClient;
	
	@Resource(name = "paymentCallbackDispatcher")
	private IPaymentCallbackDispatcher paymentCallbackDispatcher;

	@Resource(name = "fundMowPayDao")
	private IFundMowPayDao fundMowPayDao;
	private static final Logger logger=LoggerFactory.getLogger(FundExceptionServiceImpl.class);

	/**
	* Title: queryException
	* Description:
	* @param chargeException
	* @return 
	 * @throws Exception 
	* @see com.winterframework.firefrog.fund.service.IFundExceptionService#queryException(com.winterframework.firefrog.fund.entity.FundChargeException) 
	*/
	@Override
	public Page<FundChargeException> queryException(PageRequest<ExceptionQueryRequest> pageRequest) throws Exception {

		//ExceptionQueryRequest exceptionRequest = pageRequest.getSearchDo();

		//setUseCurrApprer(exceptionRequest);
		return fundChargeExceptionDao.query(pageRequest);
	}
	public void zero(Long id) throws Exception {
		int updateCount = fundChargeExceptionDao.zero(id,Status.addCoinrefundCheck.getStatis(),Status.confiscaterefundCheck.getStatis(),Status.refundCheck.getStatis());
		if(updateCount==0){
			throw new Exception("update zero status error.");
		}
	}
	/**
	 * 
	* @Title: exceptionRefund 
	* @Description: 一审获取订单
	* @param chargeException
	* @throws Exception
	 */
	public void yuchuli(Long id,String app,Long currStatus) throws Exception {
		fundChargeExceptionDao.yuchuli(id,app,currStatus);
	}
	/**
	 * 一审结束订单
	 * @param id
	 * @param app
	 * @throws Exception
	 */
	public void yuchuliEnd(Long id,Long currStatus)throws Exception {
		fundChargeExceptionDao.yuchuliEnd(id,currStatus);
	}

	/**
	* Title: exceptionConfiscate
	* Description:
	* @param chargeException 
	 * @throws Exception 
	* @see com.winterframework.firefrog.fund.service.IFundExceptionService#exceptionConfiscate(com.winterframework.firefrog.fund.entity.FundChargeException) 
	*/
	@Override
	public void exceptionConfiscate(FundChargeException chargeException) throws Exception {
		chargeException.setStatus(Status.confiscate.getStatis());
		checkStatusAndUpdateChargeException(chargeException,Status.confiscaterefundCheck);
	}

	/**
	* Title: exceptionCoin
	* Description:
	* @param chargeException 
	* @see com.winterframework.firefrog.fund.service.IFundExceptionService#exceptionAddCoin(com.winterframework.firefrog.fund.entity.FundChargeException, Status...) 
	*/
	@Override
	public void exceptionAddCoin(FundChargeException chargeException, Status... checkStatuses) throws Exception {
		//自動匹配時，原本異常充值資料還不會有user id 資料，所以從自動匹配傳入，如果原本有user id 就用，沒有就用傳入的
		long userId = chargeException.getUserId()==null?0:chargeException.getUserId();
		Date applyTime = chargeException.getApplyTime();
		String applyAccount = chargeException.getApplyAccount();
		String apprMemo = chargeException.getApprMemo();
		chargeException=fundChargeExceptionDao.queryById(chargeException.getId());
		//自動匹配的話自行填入資訊
		if(chargeException.getUserId() ==null){chargeException.setUserId(userId);}
		if(StringUtils.isBlank(chargeException.getApplyAccount())){chargeException.setApplyAccount(applyAccount);}
		if(chargeException.getApplyTime() ==null){chargeException.setApplyTime(applyTime);}
		if(chargeException.getApprMemo() ==null){chargeException.setApprMemo(apprMemo);}
		
		chargeException.setStatus(Status.addCoin.getStatis());
		checkStatusAndUpdateChargeException(chargeException,checkStatuses);
		FundOrder fundOrder = new FundOrder(FundModel.FD.ABDX.ITEMS.DDCOINED);
		fundOrder.setSn("" + chargeException.getSn());
		fundOrder.setId(chargeException.getId());
		User user = new User();
		user.setId(chargeException.getUserId()==null?userId:chargeException.getUserId());
		fundOrder.setApplyUser(user);
		fundOrder.setApplyTime(new Date());

		updateFund(fundOrder);
	}

	/**
	* Title: exceptionRefund
	* Description:
	* @param chargeException 
	* @see com.winterframework.firefrog.fund.service.IFundExceptionService#exceptionRefund(com.winterframework.firefrog.fund.entity.FundChargeException) 
	*/
	@Override
	public void exceptionRefund(FundChargeException chargeException) throws Exception {
	
		if (chargeException.getStatus().equals(Status.refundCheck.getStatis()) 
				||chargeException.getStatus().equals(Status.confiscaterefundCheck.getStatis())
				||chargeException.getStatus().equals(Status.addCoinrefundCheck.getStatis())) {
			//如果是提交复审
			chargeException.setCurrApprer(null);
			chargeException.setApplyTime(new Date());
			chargeException.setApplyAccount(AccountTool.getRealAccount(chargeException.getApplyAccount()));
			checkStatusAndUpdateChargeException(chargeException,Status.untreated);
		} else {
			FundChargeException dd = fundChargeExceptionDao.queryById(chargeException.getId());
			//如果是审核通过
			chargeException.setStatus(Status.refunding.getStatis());
			if (dd.getStatus().equals(Status.refundCheck.getStatis())) {
				//如果是check以后的,那么是修改复核人账号
				chargeException.setApprAccount(AccountTool.getRealAccount(chargeException.getApplyAccount()));
				chargeException.setApprTime(new Date());
			}
			checkStatusAndUpdateChargeException(chargeException,Status.refundCheck);
			exceptionRefundAudit(chargeException.getId(), Status.refunding.getStatis());
		}
	}

	/**
	* Title: exceptionRemark
	* Description:
	* @param chargeException
	* @throws Exception 
	* @see com.winterframework.firefrog.fund.service.IFundExceptionService#exceptionRemark(com.winterframework.firefrog.fund.entity.FundChargeException) 
	*/
	@Override
	public void exceptionRemark(Long exceptionId, String memo) throws Exception {
		FundChargeException chargeException = new FundChargeException();
		chargeException.setId(exceptionId);
		chargeException.setApplyMemo(memo);
		fundChargeExceptionDao.update(chargeException);
	}

	private MowWithDraw createParam(FundChargeException chargeException) {
				
		MowWithDraw map = new MowWithDraw();
		map.setAmount(NumUtil.toMow(chargeException.getRealChargeAmt()));
		map.setBank_id(chargeException.getBankId());
		map.setCard_num(chargeException.getCardNumber());
		map.setCard_name(chargeException.getCardAcct());
		map.setCompany_order_num( chargeException.getSn());
		map.setCompany_user(MD5Encrypt.encrypt("-1"));
		map.setIssue_bank_address(chargeException.getBankName());
		map.setMemo("");// TODO

    	return map;
	}

	/**
	* Title: auditExceptionRefundResult
	* Description:
	* @param chargeException
	* @throws Exception 
	* @see com.winterframework.firefrog.fund.service.IFundExceptionService#callbackExceptionRefund(com.winterframework.firefrog.fund.entity.FundChargeException) 
	*/
	@Override
	public void callbackExceptionRefund(FundChargeException chargeException) throws Exception {
		fundChargeExceptionDao.update(chargeException);
	}


	public void exceptionRefundAudit(Long exceptionId, Long status) throws Exception {
		FundChargeException chargeException = new FundChargeException();
		chargeException.setId(exceptionId);
		chargeException.setStatus(status);
		fundChargeExceptionDao.update(chargeException);
		FundChargeException fundChargeException = fundChargeExceptionDao.queryById(exceptionId);

		FundMowPay mowPay = new FundMowPay();
		mowPay.setFfBankId(fundChargeException.getBankId());
		mowPay.setFfCompanyId(companyId);
		mowPay.setFfAmount(fundChargeException.getRealChargeAmt());
		mowPay.setFfCardNum(fundChargeException.getCardNumber());
		mowPay.setFfCardName(fundChargeException.getCardAcct());
		mowPay.setFfIssueBankName(fundChargeException.getBankName());
		mowPay.setFfIssueBankAddress(fundChargeException.getBankAddr());
		mowPay.setApplyTime(new Date());
		mowPay.setExSn(fundChargeException.getSn());
		mowPay.setExId(fundChargeException.getId());
		fundMowPayDao.saveMowPay(mowPay);
		

		MownecumWithdrawResponseData resp = (MownecumWithdrawResponseData) mcwClient.invokeHttp(mowUrl+mcUrl,
				createParam(fundChargeException), MownecumWithdrawResponseData.class);
		Long transCharge = resp.getTransaction_charge()==null?0L:resp.getTransaction_charge();

		mowPay = new FundMowPay();
		mowPay.setResponseTime(new Date());
		mowPay.setMowTransactionCharge(transCharge);
		mowPay.setExSn(fundChargeException.getSn());
		fundMowPayDao.updateMowPay(mowPay);

		fundChargeException.setMcFee(fundChargeException.getMcFee() == null ? 0 : fundChargeException.getMcFee()
				+ transCharge);
		fundChargeExceptionDao.update(fundChargeException);

	}

	@Override
	public void exceptionAuditRemark(Long exceptionId, String memo) throws Exception {
		FundChargeException chargeException = new FundChargeException();
		chargeException.setId(exceptionId);
		chargeException.setApprMemo(memo);
		fundChargeExceptionDao.update(chargeException);
	}


	
	public void updateFund(FundOrder fundOrder) throws FundChangedException {
		try {
			FundChargeException fc =fundChargeExceptionDao.queryById(fundOrder.getId());
			fundChangeService.action(
					new FundGameVo(FundModel.FD.ABDX.ITEMS.DDCOINED,fundOrder.getApplyUser().getId(),fc.getRealChargeAmt(),fundOrder.getSn(),true)
					,new FundGameVo(FundModel.PM.RBRC.ITEMS.RBRC,fundOrder.getApplyUser().getId(),getRealAmtWithFee(fc.getBankId(),fc.getRealChargeAmt(),fc.getMcBankFee()),null,true)
					);
			//添加余额					
		} catch (Exception e) {
			logger.error("ChargeExceptionServiceImpl.updateFund.error", e);
		}
	}
	
	@Override
	public MowQuerywithDrawResponse queryMowWithdrawOrderStatus(String mcSn,String sn) throws Exception{
		//api
		MowQuerywithDraw request = new MowQuerywithDraw();
		request.setCompany_id(Long.parseLong(companyId));
		request.setMownecum_order_num(mcSn);
		request.setCompany_order_num(sn);
		logger.info("Mownecum_order_num:  " + mcSn);
		logger.info("Company_order_num:  " + sn);
		MowQuerywithDrawResponse resp = mcwClient.invokeHttp(mowUrl + querywithdral, request,MowQuerywithDrawResponse.class);
		return resp;
	}
	
	@Override
	public void updateWithdrawalResult(Long exceptionId,MowQuerywithDrawResponse mowOrder) throws Exception{
		MOW_ORDER_STATUS status = MOW_ORDER_STATUS.mapping(mowOrder.getStatus()
				.intValue());
		logger.info("updateWithdrawalResult status:"+status.name());
		switch (status) {
			case DOING:
			case UNDO:
				logger.info("no need to do");
				break;
			case SUCCESS:
			case FAIL:
			case PART_SUCCESS:
				doUpdateWithDrawResult(mowOrder);
				break;
			case INVALID:
			case NET_ERROR:
				feedbackExceptionOrderStatus(exceptionId,mowOrder);
				break;
			default:
				break;
		}
	}
	
	private void doUpdateWithDrawResult(MowQuerywithDrawResponse mowOrder) throws Exception{
		logger.info("doUpdateWithDrawResult start");
		WithdrawConfirmRequest confirmRequest = new WithdrawConfirmRequest();
		confirmRequest.setAmount(new BigDecimal(mowOrder.getAmount()));
		confirmRequest
				.setCompany_order_num(mowOrder.getCompany_order_num());
		confirmRequest.setDetail(mowOrder.getDetail());
		confirmRequest.setError_msg(mowOrder.getError_msg());
		confirmRequest.setExact_transaction_charge(new BigDecimal(mowOrder
				.getExact_transaction_charge()));
		confirmRequest.setKey(mowOrder.getKey());
		confirmRequest.setMownecum_order_num(mowOrder
				.getMownecum_order_num());
		confirmRequest.setStatus(mowOrder.getStatus());
		String str = snUtil.parseBusinessSnDesc(confirmRequest
				.getCompany_order_num());
		paymentCallbackDispatcher.doDispatch(str, confirmRequest);
	}
	
	private void feedbackExceptionOrderStatus(Long exceptionId,MowQuerywithDrawResponse mowOrder) throws Exception{
		logger.info("feedbackExceptionOrderStatus start");
		FundChargeException chargeException = new FundChargeException();
		chargeException.setId(exceptionId);
		chargeException.setStatus(Status.untreated.getStatis());
		fundChargeExceptionDao.update(chargeException);
	}
	
	private void checkStatusAndUpdateChargeException(FundChargeException chargeException,Status... checkStatuses) throws Exception{
		if(checkStatuses!=null&&checkStatuses.length>0){
			Long[] statuses = new Long[checkStatuses.length];
			for(int i =0;i<checkStatuses.length;i++){
				Status status = checkStatuses[i];
				statuses[i] = status.getStatis();
			}
			chargeException.setCheckStatuses(statuses);
		}
		int updateCount = fundChargeExceptionDao.update(chargeException);
		if(updateCount==0){
			throw new Exception("update fundChargeException status error.");
		}
	}
}
