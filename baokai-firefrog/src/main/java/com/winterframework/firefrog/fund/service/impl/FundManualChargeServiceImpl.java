package com.winterframework.firefrog.fund.service.impl;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.fund.dao.IFundChargeDao;
import com.winterframework.firefrog.fund.dao.impl.FundManualDepositDaoImpl;
import com.winterframework.firefrog.fund.dao.vo.FundBank;
import com.winterframework.firefrog.fund.entity.BankCard;
import com.winterframework.firefrog.fund.entity.FundChargeMCOrder;
import com.winterframework.firefrog.fund.entity.FundChargeOrder;
import com.winterframework.firefrog.fund.entity.FundChargeOrder.Status;
import com.winterframework.firefrog.fund.entity.FundManualOrder;
import com.winterframework.firefrog.fund.entity.FundOrder;
import com.winterframework.firefrog.fund.entity.MowCallbackInfo;
import com.winterframework.firefrog.fund.enums.FundModel;
import com.winterframework.firefrog.fund.service.impl.mow.MowBigCallback;
import com.winterframework.firefrog.fund.service.impl.mow.MowBigDeposit;
import com.winterframework.firefrog.fund.service.impl.mow.MownecumWithdrawResponseData;
import com.winterframework.firefrog.fund.util.NumUtil;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.service.IUserProfileService;
import com.winterframework.modules.security.MD5Encrypt;

@Service("FundManualChargeService")
public class FundManualChargeServiceImpl extends FundMowBaseService {
	@Autowired
	protected FundManualDepositDaoImpl fundManualDepositDao;
	@Resource(name = "fundChargeDaoImpl")
	private IFundChargeDao fundChargeDao;
	@Resource(name = "userProfileServiceImpl")
	protected IUserProfileService userProfile;

	private MowBigDeposit createParams(FundManualOrder order) {
		MowBigDeposit params = new MowBigDeposit();
		params.setAmount(NumUtil.toMow(order.getDepositAmt()));
		params.setCompany_order_num(order.getSn());
		params.setEstimated_payment_bank("" + order.getUserBank().getBank().getId());
		/*if (order.getMemo() == null) {
			params.setMemo(UUID.randomUUID().toString().substring(32));
			params.setNote(UUID.randomUUID().toString().substring(32));
		} else {
			params.setMemo(order.getMemo());
			params.setNote(order.getMemo());
		}*/
		params.setMemo(UUID.randomUUID().toString().substring(32));
		params.setNote(UUID.randomUUID().toString().substring(32));
		params.setNote(order.getMemo());
		//params.setMemo(order.getMemo());
		params.setNote_model(1);
		Integer depositMode = 1;
		params.setDeposit_mode(depositMode);
		params.setCompany_user(MD5Encrypt.encrypt("" + order.getApplyUser().getId()));
		return params;
	}

	public void callBack(MowBigCallback bigBack) throws Exception {
		FundChargeOrder order = fundChargeDao.queryByOrderNum(bigBack.getCompany_order_num());
		BankCard rcvBank = new BankCard();
		rcvBank.setBankCardNo(bigBack.getBank_card_num());
		rcvBank.setSubBranch(bigBack.getIssuing_bank_address());
		FundBank fb = new FundBank();
		fb.setId(bigBack.getCollection_bank_id());
		rcvBank.setBank(fb);
		try {
			if (bigBack.getMode()!=null && 2 == bigBack.getMode().intValue()) {
				//如果是email充值
				order.setRevEmail(bigBack.getEmail());
			} else {
				rcvBank.setBankCardNo(bigBack.getBank_card_num());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rcvBank.setAccountHolder(bigBack.getBank_acc_name());
		order.setRevCard(rcvBank);

		order.setMode(bigBack.getMode().longValue());
		order.setMemo(bigBack.getNote());
		order.setIssuing_bank_address(bigBack.getIssuing_bank_address());
		fundChargeDao.update(order);

	};

	@Override
	public MownecumWithdrawResponseData apply(FundOrder fundOrder) throws Exception {
		FundManualOrder manual = (FundManualOrder) fundOrder;

		MowBigDeposit params = createParams(manual);
		MownecumWithdrawResponseData applayResponse = mcwClient.invokeHttp(mow_url + bigUrl, params,
				MownecumWithdrawResponseData.class);
		if (applayResponse.getStatus() != 1)
			throw applayResponse;
		applayResponse.setCompany_order_num(applayResponse.getCompany_order_num());
		BankCard revCard = new BankCard();

		FundChargeOrder resultOrder = new FundChargeOrder(FundModel.FD.AUTO.ITEMS.AUTO);
		resultOrder.setRevCard(revCard);

		FundChargeMCOrder fundOrder2 = new FundChargeMCOrder();
		fundOrder2.setSn(applayResponse.getMownecum_order_num());
		resultOrder.setMcOrder(fundOrder2);
		resultOrder.setStatus(Status.APPLY);
		resultOrder.setSn(applayResponse.getCompany_order_num());
		resultOrder.setMode(applayResponse.getStatus());

		FundChargeOrder fco = new FundChargeOrder(manual.getItem());
		fco.setApplyTime(new Date());
		User use = new User();
		use.setId(manual.getRcvId());
		use.setAccount(manual.getRcvAccount());
		fco.setApplyUser(use);
		fco.setSn(manual.getSn());
		fco.setMemo(params.getMemo());
		fco.setApplyTime(new Date());
		fco.setMcSn(applayResponse.getMownecum_order_num());
		fco.setDepositMode(1L);
		fco.setMcOrder(fundOrder2);
		fco.setPreChargeAmt(manual.getDepositAmt());
		fco.setPayCard(manual.getUserBank());
		fco.setStatus(Status.APPLY);
		fco.setDepositMode(4L);

		fundChargeDao.insert(fco);
		return applayResponse;

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
		deposit.setMcStatus((long) info.getStatus().ordinal());
		fundManualDepositDao.updateManualDeposit(deposit);
	}

	@Override
	protected void callbackIncomplete(MowCallbackInfo info) throws Exception {
		FundManualOrder deposit = fundManualDepositDao.queryManualDepositBySn(info.getCompanyOrderNum());
		deposit.setStatus(FundManualOrder.Status.PARTPAY.getStatis());
		deposit.setMcNoticeTime(new Date());
		deposit.setMcStatus((long) info.getStatus().ordinal());
		fundManualDepositDao.updateManualDeposit(deposit);

	}
}
