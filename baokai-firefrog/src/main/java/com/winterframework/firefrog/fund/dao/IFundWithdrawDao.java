package com.winterframework.firefrog.fund.dao;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.FundWithdraw;
import com.winterframework.firefrog.fund.entity.FundWithdrawOrder;
import com.winterframework.firefrog.fund.entity.FundWithdrawOrder.WithdrawStauts;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.firefrog.fund.web.dto.QueryFundWithdrawOrderRequest;
import com.winterframework.firefrog.subsys.web.dto.SubSysActivityWithdraw;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/**
 * 
* @ClassName: IFundWithdrawDao 
* @Description: 提现申请数据操作 
* @author Richard
* @date 2013-7-1 下午3:08:12 
*
 */
public interface IFundWithdrawDao extends BaseDao<FundWithdraw> {

	/**
	 * 
	* @Title: queryFundWithdraw 
	* @Description:查询提现信息 
	* @param pageRequest
	* @return
	* @throws Exception
	 */
	public CountPage<FundWithdrawOrder> queryFundWithdraw(PageRequest<QueryFundWithdrawOrderRequest> pageRequest)
			throws Exception;
	
	/**
	 * 
	* @Title: queryFundWithdraw 
	* @Description:查询提现信息 
	* @param pageRequest
	* @return
	* @throws Exception
	 */
	public CountPage<FundWithdrawOrder> queryFundWithdrawDetail(PageRequest<QueryFundWithdrawOrderRequest> pageRequest)
			throws Exception;
	
	
	/**
	 * 
	* @Title: queryReFundWithdraw 
	* @Description:查询提现處理中退款 
	* @param pageRequest
	* @return
	* @throws Exception
	 */
	public CountPage<FundWithdrawOrder> queryReFundWithdraw(PageRequest<QueryFundWithdrawOrderRequest> pageRequest)
			throws Exception;
	
	/**
	 * 
	* @Title: queryFundWithdraw 
	* @Description:查询提现信息 
	* @param pageRequest
	* @return
	* @throws Exception
	 */
	public CountPage<FundWithdrawOrder> queryDetailFundWithdraw(PageRequest<QueryFundWithdrawOrderRequest> pageRequest)
			throws Exception;
						
	/**
	 * 
	* @Title: saveFundWithdraw 
	* @Description: 保存提现申请信息
	* @param fundWithdraw
	* @param fundFreezeLogI
	* @throws Exception
	 */
	public Long saveFundWithdraw(FundWithdrawOrder fundWithdraw, String sn) throws Exception;

	/**
	 * 
	* @Title: updateFundWithdraw 
	* @Description: 提现审核信息
	* @param id
	* @param approver
	* @param status
	* @param memo
	* @return
	* @throws Exception
	 */
	public int updateFundWithdrawAudingInfo(Long id, String approver, Long status, String memo, String attach)
			throws Exception;

	/**
	 * 
	* @Title: updateFundWithdraw 
	* @Description: 复审核信息
	* @param id
	* @param approver
	* @param status
	* @param memo
	* @return
	* @throws Exception
	 */
	public int updateFundWithdrawAudingInfo2(Long id, String approver, Long status, String memo) throws Exception;
	
	/**
	 * 
	* @Title: updateFundWithdraw 
	* @Description: 退款信息
	* @param id
	* @param approver
	* @param status
	* @param memo
	* @return
	* @throws Exception
	 */
	public int refundgo(Long id, String approver, Long status, String memo,String attach) throws Exception;

	/**
	 * 
	* @Title: updateMowFundWithdraw 
	* @Description: 提现申请发送mow更新返回信息 
	* @param id
	* @param mcMemo
	* @param detail
	* @return
	* @throws Exception
	 */
	public int updateFundWithdrawMow(Long id, Long status, Long amount, String detail,Date operatingTime) throws Exception;

	/**
	 * 
	* @Title: queryFundWithdrawByMowNum 
	* @Description: 根据平台订单号和MowNum获取提现信息
	* @param sn
	* @param mowNum
	* @return
	* @throws Exception
	 */
	public FundWithdraw queryFundWithdrawByMowNum(String sn, String mowNum) throws Exception;

	public int updateRemark(Long id, String remark) throws Exception;

	public FundWithdrawOrder queryById(Long id) throws Exception;

	public void yuchuli(Long id, String yuchuliId, Long currStatus);

	public void yuchuliEnd(Long id, Long currStatus);
	
	/**
	 * 更新目標status為lock狀態
	 * @param id
	 * @param targetStatus
	 * @return
	 */
	public int updateTargetStatusToLock(Long id, WithdrawStauts targetStatus);
	public void yuchuliEndRefund(Long id, Long currStatus);
	public void updateWithdrawMode(Long withdrawMode , String sn);
	public List<FundWithdraw> queryFundWithdrawList(Long userId, String sortColumns);
	public void updateNowStatus(Long id,WithdrawStauts status);
	public List<FundWithdrawOrder> queryByRootSn(String rootSn);
	//X平台活動用
	public List<SubSysActivityWithdraw> queryWithdrawFHXList(Long userId);
	/**
	 * 获取未处理单据数量
	 * @return
	 */
	public Integer getCountUnhandle();
	
}
