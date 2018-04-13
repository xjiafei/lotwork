/**   
* @Title: IFundExceptionService.java 
* @Package com.winterframework.firefrog.fund.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-16 下午4:52:07 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service;

import com.winterframework.firefrog.fund.entity.FundChargeException;
import com.winterframework.firefrog.fund.service.impl.mow.MowQuerywithDrawResponse;
import com.winterframework.firefrog.fund.web.controller.charge.Status;
import com.winterframework.firefrog.fund.web.dto.ExceptionQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName: IFundExceptionService 
* @Description: 资金异常处理业务类
* @author Tod
* @date 2013-7-16 下午4:52:07 
*  
*/
public interface IFundExceptionService {

	/**
	 * 
	* @Title: queryException 
	* @Description: 查询异常信息
	* @param pageRequest
	* @return
	* @throws Exception
	 */
	public Page<FundChargeException> queryException(PageRequest<ExceptionQueryRequest> pageRequest) throws Exception;

	/**
	 * 
	* @Title: exceptionConfiscate 
	* @Description: 异常处理之没收
	* @param chargeException
	* @throws Exception
	 */
	public void exceptionConfiscate(FundChargeException chargeException) throws Exception;

	/**
	 * 
	* @Title: exceptionAddCoin 
	* @Description: 异常处理之加游戏币
	* @param chargeException
	 * @param checkStatuses
	* @throws Exception
	 */
	public void exceptionAddCoin(FundChargeException chargeException, Status... checkStatuses) throws Exception;
	
	//public void exceptionAddCoinAudit(Long exceptionId, Long status) throws Exception;

	/**
	 * 
	* @Title: exceptionRefund 
	* @Description: 一审获取订单
	* @param chargeException
	* @throws Exception
	 */
	public void yuchuli(Long id,String app,Long currStatus) throws Exception  ;
	public void zero(Long id) throws Exception  ;
	/**
	 * 一审结束订单
	 * @param id
	 * @param app
	 * @throws Exception
	 */
	public void yuchuliEnd(Long id,Long currStatus)throws Exception ;
	public void exceptionRefund(FundChargeException chargeException) throws Exception;
	
	//public void exceptionRefundAudit(Long exceptionId, Long status) throws Exception;

	/**
	 * 
	* @Title: exceptionRemark 
	* @Description: 修改异常备注信息
	* @param chargeException
	* @throws Exception
	 */
	public void exceptionRemark(Long exceptionId, String memo) throws Exception;
	
	public void exceptionAuditRemark(Long exceptionId, String memo) throws Exception;

	/**
	 * 
	* @Title: callbackExceptionRefund 
	* @Description: Mow二次回调告知退款结果
	* @param chargeException
	* @throws Exception
	 */
	public void callbackExceptionRefund(FundChargeException chargeException) throws Exception;

	/**
	 * @Title: queryMowWithdrawOrderStatus
	 * @Description:以Mow,平台订单序号查询退款订单状态
	 * @param mcSn
	 * @param sn
	 * @return MowQuerywithDrawResponse
	 * @throws Exception
	 */
	public MowQuerywithDrawResponse queryMowWithdrawOrderStatus(String mcSn, String sn)
			throws Exception;

	/**
	 * updateWithdrawalResult
	 * @Description:通知平台api更新訂單狀態
	 * @param mowOrder
	 * @throws Exception
	 */
	public void updateWithdrawalResult(Long exceptionId,MowQuerywithDrawResponse mowOrder)
			throws Exception;
}
