package com.winterframework.firefrog.fund.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.fund.dao.IFundChargeExceptionDao;
import com.winterframework.firefrog.fund.dao.IFundWithdrawDao;
import com.winterframework.firefrog.fund.entity.FundChargeException;
import com.winterframework.firefrog.fund.entity.FundOrder;
import com.winterframework.firefrog.fund.entity.FundWithdrawOrder;
import com.winterframework.firefrog.fund.entity.MowCallbackInfo;
import com.winterframework.firefrog.fund.service.IFundExceptionService;
import com.winterframework.firefrog.fund.service.impl.mow.MowWithDraw;
import com.winterframework.firefrog.fund.service.impl.mow.MownecumWithdrawResponseData;
import com.winterframework.firefrog.fund.util.NumUtil;
import com.winterframework.firefrog.fund.web.controller.charge.Status;
import com.winterframework.firefrog.fund.web.dto.UserBankStruc;
import com.winterframework.modules.security.MD5Encrypt;

/**
 * 
* @ClassName: FundMowExceptionWithdrawServiceImpl 
* @Description: 异常充值中的退款处理流程，向mow发起的提现操作
* @author Page
* @date 2013-12-20 下午1:37:51 
*
 */
@Service("FundMowExceptionWithdrawServiceImpl")
public class FundMowExceptionWithdrawServiceImpl extends FundMowBaseService {

	@Resource(name = "fundWithdrawDaoImpl")
	private IFundWithdrawDao fundWithdrawDao;

	public FundMowExceptionWithdrawServiceImpl(){}

	@Resource(name = "fundChargeExceptionDao")
	private IFundChargeExceptionDao fundChargeExceptionDao;

	@Resource(name = "fundExceptionServiceImpl")
	private IFundExceptionService fundExceptionService;
	private Logger log=LoggerFactory.getLogger(this.getClass());
	
	@Override
	public MownecumWithdrawResponseData apply(FundOrder fundOrder) throws Exception {
		FundWithdrawOrder order = (FundWithdrawOrder) fundOrder;
		UserBankStruc card = (UserBankStruc) DataConverterUtil.convertJson2Object(order.getCardStr(),
				new UserBankStruc().getClass());

		
		MowWithDraw map = new MowWithDraw();
		map.setAmount(NumUtil.toMow(order.getWithdrawAmt().longValue()));
		map.setBank_id(card.getBankId());
		map.setCard_num(card.getBankNumber());
		map.setCard_name(card.getBankNumber());
		map.setCompany_order_num(order.getSn());
		map.setCompany_user(MD5Encrypt.encrypt(""+order.getApplyUser().getId()));
		map.setIssue_bank_address(card.getBranchName());
		map.setMemo("");// TODO

		mcwClient.invokeHttp(mow_url+mcUrl, map, MownecumWithdrawResponseData.class);
		return null;

	}

	@Override
	protected void callbackFaild(MowCallbackInfo info) throws Exception {
		FundChargeException chargeException = fundChargeExceptionDao.queryBySn(info.getCompanyOrderNum());
		if (chargeException == null) {
			//脏数据
			return;
		}
		if(!Status.refunding.getStatis().equals(chargeException.getStatus())){
			log.info("mowcum 重复回调:"+info.getCompanyOrderNum());
			return;
		}
		chargeException.setStatus(Status.refundFailed.getStatis());
		fundChargeExceptionDao.update(chargeException);
	}

	@Override
	protected void callbackSuccessful(MowCallbackInfo info) throws Exception {
		FundChargeException chargeException = fundChargeExceptionDao.queryBySn(info.getCompanyOrderNum());;
		if (chargeException == null) {
			//脏数据
			return;
		}
		if(!Status.refunding.getStatis().equals(chargeException.getStatus())){
			log.info("mowcum 重复回调:"+info.getCompanyOrderNum());
			return;
		}
		chargeException.setStatus(Status.refundSucces.getStatis());
		chargeException.setRefundAmt( info.getAmount());
		fundChargeExceptionDao.update(chargeException);
	}

	@Override
	protected void callbackIncomplete(MowCallbackInfo info) throws Exception {
		FundChargeException chargeException = fundChargeExceptionDao.queryBySn(info.getCompanyOrderNum());
		if (chargeException == null) {
			//脏数据
			return;
		}
		if(!Status.refunding.getStatis().equals(chargeException.getStatus())){
			log.info("mowcum 重复回调:"+info.getCompanyOrderNum());
			return;
		}
		// 修改已退款金额（金额叠加）
		chargeException.setStatus(Status.partRefund.getStatis());
		chargeException.setRefundAmt( info.getAmount());
		fundChargeExceptionDao.update(chargeException);

		// 修改退款金额，重新发起退款（注意，不能入库）
		//chargeException.setRefundAmt(refusedAmount);
		//fundExceptionService.exceptionRefund(chargeException);
	}
}
