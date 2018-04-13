/**   
* @Title: IFunChargeDao.java 
* @Package com.winterframework.firefrog.fund.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-1 上午9:54:17 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.dao;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.FundCharge;
import com.winterframework.firefrog.fund.entity.FundChargeOrder;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.firefrog.fund.web.dto.ChargeQueryRequest;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName: IFunChargeDao 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-1 上午9:54:17 
*  
*/
public interface IFundChargeDao {

	public long insert(FundChargeOrder fundChargeOrder) throws Exception;

	public long update(FundChargeOrder fundChargeOrder) throws Exception;

	public FundChargeOrder queryByOrderNum(String orderNum) throws Exception;

	public CountPage<FundChargeOrder> queryFundCharge(PageRequest<ChargeQueryRequest> pageReqeust) throws Exception;

	public FundChargeOrder queryByUserId(Long userId,Date days,Long depositeMode) throws Exception;

	public long updateStatus(Long chargeId, Long status);
	
	List<FundCharge> getMowFundChargeByTimeAndStatusOne(Date time) throws Exception;
	
	public Long queryDailyChargeSum(Long chargeMode,Long depositMode);
	
	public long updateFundCharge(FundCharge fundCharge) throws Exception;
	
	public Long queryPeriodChargeSum(Long userId, Date startTime,Date endTime);

	Long getDayCharge(Long bankId)throws Exception;
	/**
	* Title: queryTodayChargeAmt
	* Description:find user today chargeAmt total
	* @param userId
	* @param chargAmt
	* @return total 
	* @throws Exception 
	*/
	public long queryTodayChargeAmt (Long userId,Long chargeAmt) throws Exception;
	/**
	* Title: queryThirdChargeRecordsTemp
	* Description:query the user charge records before 2016-11-29 05:00:00
	* @param userId
	* @param firstLimitDay 
	* @return records 符合條件的筆數
	* @throws Exception 
	*/
	public long queryThirdChargeRecordsTemp(Long userId,Long firstLimitDay) throws Exception;
	/**
	* Title: queryThirdChargeRecords
	* Description:query the user charge records before 21 days
	* @param userId
	* @param secondLimitDay
	* @return records 符合條件的筆數
	* @throws Exception 
	*/
	public long queryThirdChargeRecords(Long userId,Long secondLimitDay) throws Exception;
	/**
	* Title: queryBankDayChargeSum
	* Description:查詢銀行單日充值總額
	* param depositMode 充值渠道
	* return long 單日充值總額
	* throws Exception 
	*/
	public long queryBankDayChargeSum(Long depositMode) throws Exception;
	/**
	* Title: queryDailyAppUnipayChargeSum
	* Description:查詢app銀聯每日充值金額
	* @param chargeMode
	* @param depositMode
	* @return long 單日充值總額
	* @throws Exception 
	*/
	public Long queryDailyAppUnipayChargeSum(Long chargeMode,Long depositMode);
	/**
	 * 获取未处理单据数量
	 * @return
	 */
	public Integer getCountUnhandle();
}
