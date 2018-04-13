package com.winterframework.firefrog.fund.service;

import java.util.List;

import com.winterframework.firefrog.fund.entity.FundWithdrawOrder;
import com.winterframework.firefrog.fund.exception.FundChangedException;
import com.winterframework.firefrog.fund.service.impl.mow.MowQuerywithDrawResponse;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.firefrog.fund.web.dto.QueryFundWithdrawOrderRequest;
import com.winterframework.firefrog.fund.web.dto.WithdrawApplyRequest;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.modules.page.PageRequest;

/**
 * 
* @ClassName: IWithdrawService 
* @Description: 提现申请服务类 
* @author Richard
* @date 2013-6-28 下午5:54:08 
*
 */
public interface IFundWithdrawService {

	/**
	 * 
	* @Title: apply 
	* @Description: 提现申请服务
	 */
	public Long apply(FundWithdrawOrder withdraw,Long isVip,boolean isManual,boolean isSeperate) throws Exception;

	/**
	 * 
	* @Title: audit 
	* @Description: 提现复审
	 */
	public boolean audit2(Long id, String approver, Long status,String memo) throws Exception;
	
	/**
	 * 
	* @Title: audit 
	* @Description: 處理中退款
	 */
	public boolean refund(Long id, String approver, Long status,String memo,String attach) throws Exception;
	
	/**
	 * 
	* @Title: audit2
	* @Description: 提现审核
	 */
	public boolean audit(Long id, String approver, Long status,String attach,String memo) throws Exception;
	public void callMowService(Long id) throws Exception;

	/**
	 * 
	* @Title: remark 
	* @Description: 添加/修改备注
	* @param id
	* @param memo
	 */
	public void remark(Long id, String memo) throws Exception;

	/**
	 * 
	* @Title: queryFundWithdrawList 
	* @Description: 获取提现申请记录信息 
	* @return
	 */
	public CountPage<FundWithdrawOrder> queryFundWithdrawList(PageRequest<QueryFundWithdrawOrderRequest> pageRequest)
			throws Exception;
	
	/**
	 * 
	* @Title: queryFundWithdrawDetail 
	* @Description: 获取提现申请记录信息 
	* @return
	 */
	public CountPage<FundWithdrawOrder> queryFundWithdrawDetail(PageRequest<QueryFundWithdrawOrderRequest> pageRequest)
			throws Exception;
	
	/**
	 * 
	* @Title: queryFundWithdrawList 
	* @Description: 获取提现處理中退款
	* @return
	 */
	public CountPage<FundWithdrawOrder> queryReFundWithdrawList(PageRequest<QueryFundWithdrawOrderRequest> pageRequest)
			throws Exception;
	
	/**
	 * 
	* @Title: queryFundWithdrawList 
	* @Description: 提現明細表的查詢詳情
	* @return
	 */
	public CountPage<FundWithdrawOrder> queryDetailWithdrawList(PageRequest<QueryFundWithdrawOrderRequest> pageRequest)
			throws Exception;
	

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
	 * @Title: queryThWithdrawOrderStatus
	 * @Description:Thpay ,平台订单序号查询订单状态
	 * @param mcSn
	 * @param sn
	 * @return MowQuerywithDrawResponse
	 * @throws Exception
	 */
	public int queryThWithdrawOrderStatus(String sn) throws Exception;
	/**
	 * @Title: querySpWithdrawOrderStatus
	 * @Description:sppay ,平台订单序号查询订单状态
	 * @param mcSn
	 * @param sn
	 * @return MowQuerywithDrawResponse
	 * @throws Exception
	 */
	public int queryWithdrawOrderStatus(Integer withdrawMode,String sn) throws Exception;

	/**
	 * updateWithdrawalResult
	 * @Description:通知平台api更新訂單狀態
	 * @param mowOrder
	 * @throws Exception
	 */
	public void updateWithdrawalResult(MowQuerywithDrawResponse mowOrder)
			throws Exception;
	/**
	 * 
	* @Title: queryFundWithdrawById
	* @Description: 获取提现申请记录信息 
	* @return
	 * @throws Exception 
	 */
	public FundWithdrawOrder queryFundWithdrawById(Long id) throws Exception;
	/**
	 * 
 	 * @Description：提现成功一次，在配置中中同步增加提现次数
	 * @return
	 */
	public void addChargeCount(Long userId) throws Exception;
	/**
	 * 
 	 * @Description：提现成功一次，在配置中中减少提现次数
	 * @return
	 */
	public void reduceChargeCount(Long userId) throws Exception;
	/**
	 * 
	* @Title: getAvailWithdrawTime 
	* @Description: 获取用户可提现次数
	* @param key
	* @return
	* @throws Exception
	 */
	public Long getAvailWithdrawTime(Long userId) throws Exception;
	public void yuchuli(Long id,String yuchuliId,Long currStatus) throws Exception;
	public void yuchuliEnd(Long id,Long currStatus) throws Exception;
	public void yuchuliEndRefund(Long id,Long currStatus) throws Exception;
	public void callChange(FundWithdrawOrder fundOrder,String note) throws FundChangedException;
	
	
	// send msg 移出 出來 不同transaction  怕影響原 update fund
	public void sendMsgaudit2 (Long id ) throws Exception;
	public void sendMsgauditRefund (Long id ) throws Exception;
	public void sendMsgaudit (Long id ) throws Exception ;
	public List<FundWithdrawOrder>  seperateDrawApply(User user,WithdrawApplyRequest applyRequest) throws Exception;
	public void callMowWithDraw(FundWithdrawOrder order,boolean isTransfer) throws Exception;
	public List<FundWithdrawOrder> queryByRootSn(String rootSn);
	
	/**
	 * 
	* @Title: refundRestProcess
	* @Description: 退款後，寫入log，改變fundMowPay狀態
	* @param FundWithdrawOrder
	* @return
	* @throws Exception
	 */
	public void refundRestProcess(FundWithdrawOrder order) throws Exception;
	/**
	 * 查询未处理提现单据
	 * @return
	 */
	public Integer queryUnhandleWithdraw();
}

