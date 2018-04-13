/**   
* @Title: IChargeService.java 
* @Package com.winterframework.firefrog.fund.service 
* @Description: 充值接口
* @author Tod   
* @date 2013-6-28 下午5:55:21 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service;

import java.util.Date;

import com.winterframework.firefrog.fund.dao.vo.FundCharge;
import com.winterframework.firefrog.fund.entity.FundChargeException;
import com.winterframework.firefrog.fund.entity.FundChargeMCOrder;
import com.winterframework.firefrog.fund.entity.FundChargeOrder;
import com.winterframework.firefrog.fund.entity.FundChargeOrder.Status;
import com.winterframework.firefrog.fund.entity.FundManualOrder;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.firefrog.fund.web.dto.ChargeApplyRequest;
import com.winterframework.firefrog.fund.web.dto.ChargeQueryRequest;
import com.winterframework.firefrog.user.entity.BaseUser;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName: IChargeService 
* @Description: 充值接口
* @author Tod
* @date 2013-6-28 下午5:55:21 
*  
*/
public interface IFundChargeService {

	/**
	 * @Title: apply
	 * @Description: 平台向MC(DP)提交充值申请
	 * @param order 平台订单
	 * @return order 平台订单
	 * @throws Exception
	 */
	public FundChargeOrder apply(FundChargeOrder order) throws Exception;
	/**
	 * @Title: thpayApply
	 * @Description: 平台向THPay(通匯)提交充值申请
	 * @param order 平台订单
	 * @return order 平台订单
	 * @throws Exception
	 */
	public FundChargeOrder thpayApply(FundChargeOrder order) throws Exception;
	/**
	 * @Title: ecpssApply
	 * @Description: 平台向ecpss(匯潮)提交充值申请
	 * @param order 平台订单
	 * @return order 平台订单
	 * @throws Exception
	 */
	public FundChargeOrder ecpssApply(FundChargeOrder order) throws Exception;
	/**
	 * @Title: hbpayApply
	 * @Description: 平台向HBPay(匯博)提交充值申请
	 * @param order 平台订单
	 * @return order 平台订单
	 * @throws Exception
	 */
	public FundChargeOrder hbpayApply(FundChargeOrder order) throws Exception;
	/**
	 * @Title: worthApply
	 * @Description: 平台向worth(華勢)提交充值申请
	 * @param order 平台订单
	 * @return order 平台订单
	 * @throws Exception
	 */
	public FundChargeOrder worthApply(FundChargeOrder order) throws Exception;
	public FundChargeOrder spApply(FundChargeOrder order) throws Exception;
	public FundChargeOrder ddbApply(FundChargeOrder order) throws Exception;
	public FundChargeOrder wftApply(FundChargeOrder order) throws Exception;
	public FundChargeOrder dinApply(FundChargeOrder order) throws Exception;
	public FundChargeOrder huayinApply(FundChargeOrder order) throws Exception;
	public FundChargeOrder yinbangApply(FundChargeOrder order) throws Exception;
	public FundChargeOrder jinyangApply(FundChargeOrder order) throws Exception;
	
	public FundChargeOrder queryById(String orderNum) throws Exception;

	public void cancelMowOrder(String orderNum,Status status) throws Exception;

	/**
	 * 
	* @Title: confirm 
	* @Description: MC向平台确认充值完成
	* @param mcOrder MC订单
	* @return 平台订单
	 */
	public FundChargeOrder confirm(FundChargeMCOrder mcOrder) throws Exception;

	/**
	 * 
	* @Title: query 
	* @Description: 查询充值记录
	* @return 充值记录列表
	 */
	public CountPage<FundChargeOrder> query(PageRequest<ChargeQueryRequest> pageReq) throws Exception;

	public void reportException(FundChargeException fundChargeException) throws Exception;

	public long updateOrder(Long chargeId,Status status) throws Exception;
	
	public void cancelOrder(Long chargeId,Status status) throws Exception;
	void cancelMowOrde() throws Exception;
	public FundChargeOrder haveChargeItem(Long userId,Long depositMode) throws Exception;

	public void changeOrderStatus(String orderNum, FundManualOrder.Status status) throws Exception;


	public void sendMsg (Long amount, BaseUser applyUser) throws Exception;
	
	public long updateFundCharge(FundCharge fundCharge) throws Exception;
	
	public Long queryPeriodChargeSum(Long userId,Date startTime,Date endTime) throws Exception;

    public Long checkChargeLimit(ChargeApplyRequest chargeApplyRequest)throws Exception;
    /**
	* Title: chargeThirdPartyLimit 
	* Description: 第三方支付充值限制檢查
	* param userId  用戶ID
	* param chartAmt 充值金額
	* return approvelResult 審核結果 true通過 false不通過
	* throws Exception
	 */	
    public boolean checkChargeThirdPartyLimit(Long userId,Long chargeAmt) throws Exception;
    /**
	 * 
	* Title: checkBankDayLimit 
	* Description:  銀行(支付寶)當日充值限制檢查
	* param  depositMode
	* return boolean 檢查結果 ture 通過 false不通過
	 */
    public boolean checkBankDayLimit(Long depositMode) throws Exception;
    
    /**
	 * 
	* Title: executeChargeDispatch 
	* Description:  執行充值發幣流程
	* param FundChargeMCOrder
	* param FundChargeOrder
	* param orderId

	 */
    public void executeChargeDispatch(FundChargeMCOrder mcOrder,FundChargeOrder order,String orderId) throws Exception;
    
    /**
	 * 查询未处理充值单据
	 * @return
	 */
	public Integer queryUnhandleCharge();
}
