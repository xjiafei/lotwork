package com.winterframework.firefrog.fund.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.fund.dao.IFundManualDepositDao;
import com.winterframework.firefrog.fund.entity.FundManualOrder;
import com.winterframework.firefrog.fund.entity.MowCallbackInfo;
import com.winterframework.firefrog.user.service.IUserProfileService;

@Service("FundManualWithdrawService")
public class FundManualWithdrawServiceImpl extends FundMowWithdrawServiceImpl {

	@Resource(name = "fundManualDepositDao")
	protected IFundManualDepositDao fundManualDepositDao;
	public FundManualWithdrawServiceImpl(){}

	@Resource(name = "userProfileServiceImpl")
	protected IUserProfileService userProfile;
	private final Logger log=LoggerFactory.getLogger(this.getClass());
	

	@Override
	protected void callbackFaild(MowCallbackInfo info) throws Exception {
		super.callbackFaild(info);
		FundManualOrder deposit = fundManualDepositDao.queryManualDepositBySn(info.getCompanyOrderNum());
			deposit.setStatus(FundManualOrder.Status.PAYFAILED.getStatis());
		deposit.setMcNoticeTime(new Date());
		deposit.setMcStatus((long) info.getStatus().ordinal());
		fundManualDepositDao.updateManualDeposit(deposit);
	}

	@Override
	protected void callbackSuccessful(MowCallbackInfo info) throws Exception {
		super.callbackSuccessful(info);
		FundManualOrder deposit = fundManualDepositDao.queryManualDepositBySn(info.getCompanyOrderNum());
		if (deposit == null) {
			//脏数据
			return;
		}
		deposit.setStatus(FundManualOrder.Status.PAYSUCCESS.getStatis());
		deposit.setMcNoticeTime(new Date());
		deposit.setMcStatus((long) info.getStatus().ordinal());
		fundManualDepositDao.updateManualDeposit(deposit);		

	}

	@Override
	protected void callbackIncomplete(MowCallbackInfo info) throws Exception {
		super.callbackIncomplete(info);
		FundManualOrder deposit = fundManualDepositDao.queryManualDepositBySn(info.getCompanyOrderNum());
		deposit.setStatus(FundManualOrder.Status.PARTPAY.getStatis());
		deposit.setMcNoticeTime(new Date());
		deposit.setMcStatus((long) info.getStatus().ordinal());
		fundManualDepositDao.updateManualDeposit(deposit);
		
	}
}
