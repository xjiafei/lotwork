package com.winterframework.firefrog.fund.service;

import com.winterframework.firefrog.fund.entity.FundOrder;
import com.winterframework.firefrog.fund.entity.MowCallbackInfo;
import com.winterframework.firefrog.fund.service.impl.mow.MowBigCallback;
import com.winterframework.firefrog.fund.service.impl.mow.MownecumWithdrawResponseData;

/**
 * 
* @ClassName: IFundPaymentService 
* @Description: 资金支付服务接口，用于提现，人工打款等业务。
* @author 你的名字 
* @date 2013-8-6 上午10:02:30 
*
 */
public interface IFundMowService {

	public MownecumWithdrawResponseData apply(FundOrder fundOrder) throws Exception;

	public void callback(MowCallbackInfo info) throws Exception;
	public void callBack(MowBigCallback bigBack) throws Exception;

}
