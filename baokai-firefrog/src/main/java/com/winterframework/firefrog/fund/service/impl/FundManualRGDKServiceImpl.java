package com.winterframework.firefrog.fund.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.fund.dao.impl.FundManualDepositDaoImpl;
import com.winterframework.firefrog.fund.dao.vo.FundMowPay;
import com.winterframework.firefrog.fund.entity.FundManualOrder;
import com.winterframework.firefrog.fund.entity.FundOrder;
import com.winterframework.firefrog.fund.entity.MowCallbackInfo;
import com.winterframework.firefrog.fund.service.impl.mow.MowWithDraw;
import com.winterframework.firefrog.fund.service.impl.mow.MownecumWithdrawResponseData;
import com.winterframework.firefrog.fund.util.NumUtil;
import com.winterframework.firefrog.user.service.IUserProfileService;
import com.winterframework.modules.security.MD5Encrypt;

@Service("FundManualRGDKService")
public class FundManualRGDKServiceImpl extends FundMowBaseService {
	@Autowired
	protected FundManualDepositDaoImpl fundManualDepositDao;

	@Resource(name = "userProfileServiceImpl")
	protected IUserProfileService userProfile;	
	
	@Override
	public MownecumWithdrawResponseData apply(FundOrder fundOrder) throws Exception {
		FundManualOrder order = (FundManualOrder) fundOrder;	

		FundMowPay mowPay = new FundMowPay();
		mowPay.setFfBankId(order.getUserBank().getId());
		mowPay.setFfCompanyId(companyId);
		mowPay.setFfAmount(order.getDepositAmt().longValue());
		mowPay.setFfCardNum(order.getUserBank().getBankCardNo());
		mowPay.setFfCardName(order.getUserBank().getAccountHolder());
		mowPay.setFfIssueBankName(order.getUserBank().getSubBranch());
		mowPay.setApplyTime(new Date());
		mowPay.setExSn(order.getSn());
		mowPay.setExId(order.getId());
		fundMowPayDao.saveMowPay(mowPay);
		
		MowWithDraw map = new MowWithDraw();
		map.setAmount(NumUtil.toMow(order.getDepositAmt().longValue()));
		map.setBank_id(order.getUserBank().getBank().getId());
		map.setCard_num(order.getUserBank().getBankCardNo());
		map.setCard_name(order.getUserBank().getAccountHolder());
		map.setCompany_order_num(order.getSn());
		map.setCompany_user(MD5Encrypt.encrypt(""+order.getRcvAccount()));
		map.setIssue_bank_address(order.getUserBank().getAccountHolder());
		map.setMemo("");

		MownecumWithdrawResponseData resp = mcwClient.invokeHttp(mow_url+mcUrl, map,
				new MownecumWithdrawResponseData().getClass());
		//生成订单
		mowPay = new FundMowPay();
		mowPay.setMowTransactionCharge((resp.getTransaction_charge()));
		mowPay.setResponseTime(new Date());
		mowPay.setExSn(order.getSn());
		fundMowPayDao.updateMowPay(mowPay);
		return resp;

	}

	@Override
	protected void callbackFaild(MowCallbackInfo info) throws Exception {
		FundManualOrder deposit = fundManualDepositDao.queryManualDepositBySn(info.getCompanyOrderNum());
		deposit.setStatus(FundManualOrder.Status.PAYFAILED.getStatis());
		deposit.setMcNoticeTime(new Date());
		deposit.setMcStatus((long) info.getStatus().ordinal());
		fundManualDepositDao.updateManualDeposit(deposit);
	}

	@Override
	protected void callbackSuccessful(MowCallbackInfo info) throws Exception {
		FundManualOrder deposit = fundManualDepositDao.queryManualDepositBySn(info.getCompanyOrderNum());
		deposit.setStatus(FundManualOrder.Status.PAYSUCCESS.getStatis());
		deposit.setMcNoticeTime(new Date());
		deposit.setMcAmount(info.getAmount());
		deposit.setMcStatus((long) info.getStatus().ordinal());
		fundManualDepositDao.updateManualDeposit(deposit);
	}

	@Override
	protected void callbackIncomplete(MowCallbackInfo info) throws Exception {
		FundManualOrder deposit = fundManualDepositDao.queryManualDepositBySn(info.getCompanyOrderNum());
		deposit.setStatus(FundManualOrder.Status.PARTPAY.getStatis());
		deposit.setMcAmount(info.getAmount());
		deposit.setMcNoticeTime(new Date());
		deposit.setMcStatus((long) info.getStatus().ordinal());
		fundManualDepositDao.updateManualDeposit(deposit);


	}
}
