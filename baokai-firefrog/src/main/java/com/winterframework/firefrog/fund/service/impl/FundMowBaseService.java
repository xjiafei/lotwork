package com.winterframework.firefrog.fund.service.impl;

import javax.annotation.Resource;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.fund.dao.IFundMowPayDao;
import com.winterframework.firefrog.fund.dao.vo.FundMowPay;
import com.winterframework.firefrog.fund.entity.MowCallbackInfo;
import com.winterframework.firefrog.fund.service.IFundMowService;
import com.winterframework.firefrog.fund.service.impl.mow.MowBigCallback;
import com.winterframework.modules.spring.exetend.PropertyConfig;

public abstract class FundMowBaseService implements IFundMowService {

	@Resource(name = "HttpJsonClientImpl")
	protected IHttpJsonClient mcwClient;

	@PropertyConfig(value = "company_id")
	protected String companyId;
	@PropertyConfig(value = "config")
	protected String config;

	@PropertyConfig(value = "withdraw_url")
	protected String mcUrl;
	@PropertyConfig(value = "big_url")
	protected String bigUrl;
	@PropertyConfig(value = "mownum_url")
	protected String mow_url;
	@Resource(name = "fundMowPayDao")
	protected IFundMowPayDao fundMowPayDao;
	public void callBack(MowBigCallback bigBack) throws Exception{}
	@Override
	public void callback(MowCallbackInfo info) throws Exception {
		callbackUpdateFundMowPay(info);
		switch (info.getStatus()) {
		case failed:{
			info.setAmount(0L);
			callbackFaild(info);
			break;
		}
		case sucessful:
			callbackSuccessful(info);
			break;
		case incomplete:
			callbackIncomplete(info);
			break;
		default:
			break;
		}
	}

	private void callbackUpdateFundMowPay(MowCallbackInfo info) throws Exception {
		FundMowPay mowPay = new FundMowPay();
		mowPay.setMowAmount(info.getAmount());
		mowPay.setMowDetail(info.getDetail());
		mowPay.setMownecumOrderNum(info.getMowOrderNum());
		mowPay.setMowStatus((long) info.getStatus().ordinal());
		mowPay.setExSn(info.getCompanyOrderNum());
		mowPay.setNoticeTime(info.getNoticeTime());
		mowPay.setResponseTime(info.getResponseTime());
		mowPay.setMowExactTransactionCharge(info.getMowTransactionCharge());
		fundMowPayDao.updateMowPay(mowPay);
	}

	abstract protected void callbackFaild(MowCallbackInfo info) throws Exception;

	abstract protected void callbackSuccessful(MowCallbackInfo info) throws Exception;

	abstract protected void callbackIncomplete(MowCallbackInfo info) throws Exception;
}
