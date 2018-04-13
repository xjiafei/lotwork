/**   
* @Title: FundChargeDaoImpl.java 
* @Package com.winterframework.firefrog.fund.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-1 上午10:39:51 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PageBeanUtilsBean;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


import com.winterframework.firefrog.fund.dao.IFundChargeDao;
import com.winterframework.firefrog.fund.dao.vo.FundCharge;
import com.winterframework.firefrog.fund.dao.vo.VOConverter;
import com.winterframework.firefrog.fund.entity.FundChargeOrder;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.firefrog.fund.web.controller.vo.SumCount;
import com.winterframework.firefrog.fund.web.dto.ChargeQueryRequest;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: FundChargeDaoImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-1 上午10:39:51 
*  
*/
@Repository("fundChargeDaoImpl")
public class FundChargeDaoImpl extends BaseIbatis3Dao<FundCharge> implements IFundChargeDao {
	private static final Logger logger = LoggerFactory.getLogger(FundChargeDaoImpl.class);
	
	@Override
	public FundChargeOrder queryByUserId(Long userId, Date days, Long depositeMode) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("currentC", days);
		List<FundCharge> fundCharges = this.sqlSessionTemplate.selectList("getByItemUserId", map);
		if (!fundCharges.isEmpty()) {
			if (depositeMode != null)
				for (FundCharge c : fundCharges) {
					if (depositeMode.equals(c.getDepositMode())) {
						return VOConverter.transFundCharge2FundChargeOrder(c);
					}

				}

		}
		return null;

	}

	@Override
	public List<FundCharge> getMowFundChargeByTimeAndStatusOne(Date time) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("time", time);
		return this.sqlSessionTemplate.selectList("getMowFundChargeByTimeAndStatusOne", map);
	}

	@Override
	public long updateStatus(Long chargeId, Long status) {
		FundCharge fundCharge = new FundCharge();
		fundCharge.setId(chargeId);
		fundCharge.setStatus(status);
		return this.sqlSessionTemplate.update("updateStatus", fundCharge);
	}

	/**
	* Title: insert
	* Description:
	* @param fundCharge 
	* @see com.winterframework.firefrog.fund.dao.IFundChargeDao#insert(com.winterframework.firefrog.fund.entity.FundChargeOrder) 
	*/
	@Override
	public long insert(FundChargeOrder fundChargeOrder) throws Exception {
		FundCharge charge = VOConverter.transFundChargeOrder2FundCharge(fundChargeOrder);
		long tt = insert(charge);
		fundChargeOrder.setId(charge.getId());
		return tt;
	}

	/**
	* Title: update
	* Description:
	* @param fundCharge 
	* @see com.winterframework.firefrog.fund.dao.IFundChargeDao#update(com.winterframework.firefrog.fund.entity.FundChargeOrder) 
	*/
	@Override
	public long update(FundChargeOrder fundChargeOrder) throws Exception {
		return update(VOConverter.transFundChargeOrder2FundChargeUpdate(fundChargeOrder));
	}

	/**
	* Title: queryByOrderNum
	* Description:
	* @param orderNum
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.fund.dao.IFundChargeDao#queryByOrderNum(java.lang.String) 
	*/
	@Override
	public FundChargeOrder queryByOrderNum(String orderNum) throws Exception {

		FundCharge fundCharge = new FundCharge();
		fundCharge.setSn(orderNum);
		List<FundCharge> list = this.getAllByEntity(fundCharge);
		if (list != null && !list.isEmpty()) {
			return VOConverter.transFundCharge2FundChargeOrder(list.get(0));
		}
		return null;
	}

	/**
	* Title: queryFundCharge
	* Description:
	* @param pageReqeust
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.fund.dao.IFundChargeDao#queryFundCharge(com.winterframework.modules.page.PageRequest) 
	*/
	@Override
	public CountPage<FundChargeOrder> queryFundCharge(PageRequest<ChargeQueryRequest> pageReqeust) throws Exception {
		ChargeQueryRequest search = pageReqeust.getSearchDo();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = new PageBeanUtilsBean().describe(search);
			map.putAll(pageReqeust.getFilters());
		}catch(Exception e){}
		

		SumCount totalCount = ((SumCount) sqlSessionTemplate.selectOne("getChargeCountByPage", map));
		if (totalCount.getCount() == null) {
			return new CountPage<FundChargeOrder>(0);
		}
		CountPage<FundChargeOrder> page = new CountPage<FundChargeOrder>(pageReqeust.getPageNumber(),
				pageReqeust.getPageSize(), totalCount.getCount().intValue());
		page.setSum(totalCount.getSum());
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageReqeust.getSortColumns());
		filters.put("fromDate", pageReqeust.getSearchDo().getFromDate());
		filters.put("notAppealStatus", pageReqeust.getSearchDo().getNotAppealStatus());
		filters.putAll(map);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<FundCharge> list = sqlSessionTemplate.selectList("getChargeByPage", filters, rowBounds);

		List<FundChargeOrder> orderList = new ArrayList<FundChargeOrder>();
		for (FundCharge fundCharge : list) {
			orderList.add(VOConverter.transFundCharge2FundChargeOrder(fundCharge));
		}
		page.setResult(orderList);
		return page;
	}
	
	public Long queryDailyChargeSum(Long chargeMode,Long depositMode){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("chargeMode", chargeMode);
		map.put("depositMode", depositMode);
		return sqlSessionTemplate.selectOne(this.getQueryPath("queryDailyChargeSum"),map);
	}
	
	public Long queryPeriodChargeSum(Long userId, Date startTime,Date endTime){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return sqlSessionTemplate.selectOne(this.getQueryPath("queryPeriodChargeSum"),map);
	}
	
	@Override
	public long updateFundCharge(FundCharge fundCharge) throws Exception {
		return update(fundCharge);
	}
	
	@Override
	public Long getDayCharge(Long bankId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bankId", bankId);
		return sqlSessionTemplate.selectOne("getDayCharge", map);
	}

	@Override
	public long queryTodayChargeAmt(Long userId,Long chargeAmt) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		long total =0L;
		if(sqlSessionTemplate.selectOne("getDayChargeThirdParty", map)!=null){
			total=sqlSessionTemplate.selectOne("getDayChargeThirdParty", map);
		}
		long totalAmt = total+chargeAmt; //本次充值金額加上今日充值金額
		logger.debug("totalAmtInDB="+total);
		logger.debug("totalAmt="+totalAmt);
		return total;
	}

	@Override
	public long queryThirdChargeRecordsTemp(Long userId,Long firstLimitDay) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("firstLimitDay", firstLimitDay);
		
		long records = sqlSessionTemplate.selectOne("queryThirdChargeRecordsTemp", map);
		
		logger.debug("firstLimitDay="+firstLimitDay);
		logger.debug("firtsLimitDayResult="+records);
		return records;
	}

	@Override
	public long queryThirdChargeRecords(Long userId,Long secondLimitDay) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("secondLimitDay", secondLimitDay);
		long records = sqlSessionTemplate.selectOne("queryThirdChargeRecords", map);
		
		logger.debug("secondLimitDay="+secondLimitDay);
		logger.debug("secondLimitDayResult="+records);
		return records;
	}

	@Override
	public long queryBankDayChargeSum(Long depositMode) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deposit_mode", depositMode);
		Long sum = sqlSessionTemplate.selectOne("queryBankDayChargeSum",map);
		if(sum == null){
			sum = 0L;			
		}
		return sum;
	}

	@Override
	public Long queryDailyAppUnipayChargeSum(Long chargeMode, Long depositMode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("chargeMode", chargeMode);
		map.put("depositMode", depositMode);
		return sqlSessionTemplate.selectOne(this.getQueryPath("queryDailyAppUnipayChargeSum"),map);
	}
	
	@Override
	public Integer getCountUnhandle() {
		return sqlSessionTemplate.selectOne(this.getQueryPath("getCountUnhandle"));
	}
}
