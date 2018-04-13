/**   
* @Title: PaymentCallbackDispatch.java 
* @Package com.winterframework.firefrog.fund.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-22 下午2:01:33 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.fund.dao.IFundMowPayDao;
import com.winterframework.firefrog.fund.dao.vo.FundMowPay;
import com.winterframework.firefrog.fund.entity.MowCallbackInfo;
import com.winterframework.firefrog.fund.service.IFundMowService;
import com.winterframework.firefrog.fund.service.IPaymentCallbackDispatcher;
import com.winterframework.firefrog.fund.service.impl.mow.WithdrawConfirmRequest;
import com.winterframework.firefrog.fund.util.MowNumTool;

/** 
* @ClassName: PaymentCallbackDispatch 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Tod
* @date 2013-7-22 下午2:01:33 
*  
*/
public class PaymentCallbackDispatcher implements IPaymentCallbackDispatcher {

	private Map<String, IFundMowService> serviceMap = new HashMap<String, IFundMowService>();

	private static final Logger logger = LoggerFactory.getLogger(PaymentCallbackDispatcher.class);
	@Resource(name = "fundMowPayDao")
	private IFundMowPayDao fundMowPayDao;

	/**
	* Title: doDispatch
	* Description:
	* @param str
	* @param confirmRequest
	* @throws Exception 
	* @see com.winterframework.firefrog.fund.service.IPaymentCallbackDispatcher#doDispatch(java.lang.String, com.winterframework.firefrog.fund.web.dto.WithdrawConfirmRequest) 
	*/
	@Override
	public void doDispatch(String str, WithdrawConfirmRequest confirmRequest) throws Exception {

		MowCallbackInfo info = new MowCallbackInfo();
		info.setAmount(MowNumTool.fromMow(confirmRequest.getAmount()));
		info.setCompanyOrderNum(confirmRequest.getCompany_order_num());
		info.setDetail(confirmRequest.getDetail());
		info.setErrorMsg(confirmRequest.getError_msg());
		info.setKey(confirmRequest.getKey());
		info.setMowOrderNum(confirmRequest.getMownecum_order_num());
		info.setNoticeTime(new Date());
		info.setResponseTime(new Date());
		info.setMowTransactionCharge(MowNumTool.fromMow(confirmRequest.getExact_transaction_charge()));
		info.setStatus(confirmRequest.getStatus()==1?MowCallbackInfo.Status.sucessful:(confirmRequest.getStatus()==2?MowCallbackInfo.Status.incomplete:MowCallbackInfo.Status.failed));
		info.setOperatingTime(DataConverterUtil.convertLong3Date(confirmRequest.getOperating_time()));
    	
		IFundMowService callbackservice = serviceMap.get(str);
		callbackservice.callback(info);

	}

	public void setServiceMap(Map<String, IFundMowService> serviceMap) {
		this.serviceMap = serviceMap;
	}
}
