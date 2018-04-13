package com.winterframework.firefrog.fund.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PageBeanUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.fund.dao.IFundWithdrawDao;
import com.winterframework.firefrog.fund.dao.vo.FundWithdraw;
import com.winterframework.firefrog.fund.dao.vo.VOConverter;
import com.winterframework.firefrog.fund.entity.FundWithdrawOrder;
import com.winterframework.firefrog.fund.entity.FundWithdrawOrder.WithdrawStauts;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.firefrog.fund.web.controller.vo.SumCount;
import com.winterframework.firefrog.fund.web.dto.QueryFundWithdrawOrderRequest;
import com.winterframework.firefrog.subsys.service.impl.SubSysServiceImpl;
import com.winterframework.firefrog.subsys.web.dto.SubSysActivityWithdraw;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("fundWithdrawDaoImpl")
public class FundWithdrawDaoImpl extends BaseIbatis3Dao<FundWithdraw> implements IFundWithdrawDao {
	
	private static Logger log = LoggerFactory.getLogger(FundWithdrawDaoImpl.class);
    
	@Override  
	public CountPage<FundWithdrawOrder> queryFundWithdraw(PageRequest<QueryFundWithdrawOrderRequest> pageRequest)
			throws Exception {

		Map<String, Object> map = new PageBeanUtilsBean().describe(pageRequest.getSearchDo());
		SumCount totalCount = (SumCount) sqlSessionTemplate.selectOne("getCountByFundWithdrawList", map);
		log.info("Search data counts :"+totalCount.getCount());
		if (totalCount.getCount() == null || totalCount.getCount() == 0L) {
			return new CountPage<FundWithdrawOrder>(0);
		}

		CountPage<FundWithdrawOrder> page = new CountPage<FundWithdrawOrder>(pageRequest.getPageNumber(),
				pageRequest.getPageSize(), totalCount.getCount().intValue());
		page.setSum(totalCount.getSum());
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		filters.putAll(map);
		
		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<FundWithdraw> list = sqlSessionTemplate.selectList("queryFundWithdrawList", filters, rowBounds);

		List<FundWithdrawOrder> orderList = new ArrayList<FundWithdrawOrder>();

		for (FundWithdraw fundWithdraw : list) {

			try {

				FundWithdrawOrder Order = VOConverter.fundWithdrawTOFundWithdrawOrder(fundWithdraw);
				orderList.add(Order);
			} catch (Exception e) {

				log.error("查询提现申请列表，映射类转换 实体类出错；" + e.getMessage(), e);
				throw e;
			}
		}
		page.setResult(orderList);

		return page;

	}
	
	public CountPage<FundWithdrawOrder> queryFundWithdrawDetail(PageRequest<QueryFundWithdrawOrderRequest> pageRequest)
			throws Exception {

		Map<String, Object> map = new PageBeanUtilsBean().describe(pageRequest.getSearchDo());
		SumCount totalCount = (SumCount) sqlSessionTemplate.selectOne("getCountByRefundAndGetStatusList", map);
		log.info("Search data counts :"+totalCount.getCount());
		if (totalCount.getCount() == null || totalCount.getCount() == 0L) {
			return new CountPage<FundWithdrawOrder>(0);
		}

		CountPage<FundWithdrawOrder> page = new CountPage<FundWithdrawOrder>(pageRequest.getPageNumber(),
				pageRequest.getPageSize(), totalCount.getCount().intValue());
		page.setSum(totalCount.getSum());
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		filters.putAll(map);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<FundWithdraw> list = sqlSessionTemplate.selectList("queryReFundAndStatusList", filters, rowBounds);

		List<FundWithdrawOrder> orderList = new ArrayList<FundWithdrawOrder>();

		for (FundWithdraw fundWithdraw : list) {

			try {

				FundWithdrawOrder Order = VOConverter.fundWithdrawTOFundWithdrawOrder(fundWithdraw);
				orderList.add(Order);
			} catch (Exception e) {

				log.error("查询提现申请列表，映射类转换 实体类出错；" + e.getMessage(), e);
				throw e;
			}
		}
		page.setResult(orderList);

		return page;

	}
	
	public CountPage<FundWithdrawOrder> queryReFundWithdraw(PageRequest<QueryFundWithdrawOrderRequest> pageRequest)
			throws Exception {

		Map<String, Object> map = new PageBeanUtilsBean().describe(pageRequest.getSearchDo());
		SumCount totalCount = (SumCount) sqlSessionTemplate.selectOne("getCountByRefundAndGetStatusList", map);
		log.info("Search data counts :"+totalCount.getCount());
		if (totalCount.getCount() == null || totalCount.getCount() == 0L) {
			return new CountPage<FundWithdrawOrder>(0);
		}

		CountPage<FundWithdrawOrder> page = new CountPage<FundWithdrawOrder>(pageRequest.getPageNumber(),
				pageRequest.getPageSize(), totalCount.getCount().intValue());
		page.setSum(totalCount.getSum());
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		filters.putAll(map);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<FundWithdraw> list = sqlSessionTemplate.selectList("queryReFundAndStatusList", filters, rowBounds);

		List<FundWithdrawOrder> orderList = new ArrayList<FundWithdrawOrder>();

		for (FundWithdraw fundWithdraw : list) {

			try {

				FundWithdrawOrder Order = VOConverter.fundWithdrawTOFundWithdrawOrder(fundWithdraw);
				orderList.add(Order);
			} catch (Exception e) {

				log.error("查询提现申请列表，映射类转换 实体类出错；" + e.getMessage(), e);
				throw e;
			}
		}
		page.setResult(orderList);

		return page;

	}
	
	public CountPage<FundWithdrawOrder> queryDetailFundWithdraw(PageRequest<QueryFundWithdrawOrderRequest> pageRequest)
			throws Exception {

		Map<String, Object> map = new PageBeanUtilsBean().describe(pageRequest.getSearchDo());
		SumCount totalCount = (SumCount) sqlSessionTemplate.selectOne("getCountByRefundAndGetStatusList", map);
		log.info("Search data counts :"+totalCount.getCount());
		if (totalCount.getCount() == null || totalCount.getCount() == 0L) {
			return new CountPage<FundWithdrawOrder>(0);
		}

		CountPage<FundWithdrawOrder> page = new CountPage<FundWithdrawOrder>(pageRequest.getPageNumber(),
				pageRequest.getPageSize(), totalCount.getCount().intValue());
		page.setSum(totalCount.getSum());
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		filters.putAll(map);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<FundWithdraw> list = sqlSessionTemplate.selectList("queryReFundAndStatusList", filters, rowBounds);

		List<FundWithdrawOrder> orderList = new ArrayList<FundWithdrawOrder>();

		for (FundWithdraw fundWithdraw : list) {

			try {

				FundWithdrawOrder Order = VOConverter.fundWithdrawTOFundWithdrawOrder(fundWithdraw);
				orderList.add(Order);
			} catch (Exception e) {

				log.error("查询提现申请列表，映射类转换 实体类出错；" + e.getMessage(), e);
				throw e;
			}
		}
		page.setResult(orderList);

		return page;

	}	
	

	@Override
	public Long saveFundWithdraw(FundWithdrawOrder fundWithdraw, String sn) throws Exception {

		FundWithdraw _fundWithdraw = new FundWithdraw();
		_fundWithdraw.setUserId(fundWithdraw.getApplyUser().getId());
		_fundWithdraw.setApplyTime(fundWithdraw.getApplyTime());
		_fundWithdraw.setWithdrawAmt(fundWithdraw.getWithdrawAmt().longValue());
		_fundWithdraw.setApplyAccount(fundWithdraw.getApplyUser().getAccount());
		_fundWithdraw.setStatus(0l);
		_fundWithdraw.setSn(sn);
		_fundWithdraw.setIpAddr(fundWithdraw.getApplyIpAddr());
		
		_fundWithdraw.setUserBankStruc(fundWithdraw.getCardStr());
		_fundWithdraw.setRiskType(Long.valueOf(fundWithdraw.getRiskType().getIndex()));
		_fundWithdraw.setRootSn(fundWithdraw.getRootSn());
		_fundWithdraw.setIsSeperate(fundWithdraw.getIsSeperate());
		insert(_fundWithdraw);
		return _fundWithdraw.getId();
	}

	@Override
	public int updateFundWithdrawAudingInfo(Long id, String account, Long status, String attach, String memo)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("id", id);
		map.put("apprAccount", account);
		map.put("apprTime", new Date());
		map.put("status", status);
		map.put("memo", memo);
		map.put("attach", attach);

		if (status != null && status == 2) {
			//如果是通过的话，那么更新通知monecum时间
			map.put("NOTICE_MOW_TIME", new Date());
		}
		return this.sqlSessionTemplate.update("updateFundWithdrawAudit", map);
	}

	@Override
	public int updateFundWithdrawAudingInfo2(Long id, String account, Long status, String memo) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("id", id);
		map.put("appr2Acct", account);
		map.put("appr2Time", new Date());
		map.put("memo", memo);
		map.put("status", status);

		if (status != null && status == 2) {
			//如果是通过的话，那么更新通知monecum时间
			map.put("NOTICE_MOW_TIME", new Date());
		}
		return this.sqlSessionTemplate.update("updateFundWithdrawAudit", map);
	}
	
	//退款
	public int refundgo(Long id, String account, Long status, String memo,String attach) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("id", id);
		map.put("cancelAcct", account);
		map.put("cancelTime", new Date());
		map.put("memo", memo);
		map.put("status", status);
		map.put("attach", attach);
		
		return this.sqlSessionTemplate.update("updateReFund", map);
	}
		

	@Override
	public FundWithdraw queryFundWithdrawByMowNum(String sn, String mowNum) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sn", sn);

		return this.sqlSessionTemplate.selectOne("queryFundWithdrawByMowNum", map);
	}

	@Override
	public int updateFundWithdrawMow(Long id, Long status, Long amount, String detail,Date operatingTime) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		map.put("REAL_WITHDRAL_AMT", amount);
		map.put("MC_NOTICE_TIME", new Date());
		map.put("operatingTime", operatingTime);
		if (StringUtils.isNotBlank(detail)) {
			map.put("mcMemo", detail);
		}

		return this.sqlSessionTemplate.update("updateFundWithdrawCall", map);
	}

	/**
	* Title: updateRemark
	* Description:
	* @param id
	* @param remark
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.fund.dao.IFundWithdrawDao#updateRemark(java.lang.Long, java.lang.String) 
	*/
	@Override
	public int updateRemark(Long id, String remark) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("memo", remark);
		return this.sqlSessionTemplate.update("updateRemark", map);
	}

	@Override
	public FundWithdrawOrder queryById(Long id) throws Exception {
		FundWithdraw vo = this.getById(id);
		if (vo == null)
			return null;
		FundWithdrawOrder order = VOConverter.fundWithdrawTOFundWithdrawOrder(vo);
		return order;
	}
	
	public List<FundWithdrawOrder> queryByRootSn(String rootSn){
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("rootSn", rootSn);
		List<FundWithdraw> list = sqlSessionTemplate.selectList("queryByRootSn", filters);
		List<FundWithdrawOrder> orderList = new ArrayList<FundWithdrawOrder>();

		for (FundWithdraw fundWithdraw : list) {
			FundWithdrawOrder Order = VOConverter.fundWithdrawTOFundWithdrawOrder(fundWithdraw);
			orderList.add(Order);
		}
		return orderList;
	}

	@Override
	public void yuchuli(Long id, String yuchuliId, Long currStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("currApprer", yuchuliId);
		map.put("currDate", new Date());
		map.put("currStatus", currStatus);
		sqlSessionTemplate.update(this.getQueryPath("yuchuli"), map);
	}

	@Override
	public void yuchuliEnd(Long id, Long currStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("currStatus", currStatus);
		sqlSessionTemplate.update(this.getQueryPath("yuchuliEnd"), map);
	}

	@Override
	public int updateTargetStatusToLock(Long id, WithdrawStauts targetStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", WithdrawStauts.LOCKING.getValue());
		map.put("targetStatus", targetStatus.getValue());
		return sqlSessionTemplate.update(this.getQueryPath("updateTargetStatusToLock"), map);
	}
	
	public void yuchuliEndRefund(Long id, Long currStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("currStatus", currStatus);
		sqlSessionTemplate.update(this.getQueryPath("yuchuliEndRefund"), map);
	}
	
	@Override
	public void updateWithdrawMode(Long withdrawMode, String sn) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("withdrawMode", withdrawMode);
		map.put("sn", sn);
		sqlSessionTemplate.update("updateWithdrawMode",map);		
	}
	
	public void updateNowStatus(Long id,WithdrawStauts status){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status.getValue());
		sqlSessionTemplate.update("updateNowStatus",map);		
	}
	
	@Override
	public List<FundWithdraw> queryFundWithdrawList(Long userId, String sortColumns) {
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("userId", userId);
		filters.put("sortColumns", sortColumns);
		return sqlSessionTemplate.selectList("queryFundWithdrawList", filters);
	}
	
	@Override
	public List<SubSysActivityWithdraw> queryWithdrawFHXList(Long userId){
		List<SubSysActivityWithdraw> datas = null;
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("userId", userId);
		datas = sqlSessionTemplate.selectList("queryWithdrawFHXList", filters);
		
		if(datas.isEmpty() || datas == null){
			datas = new ArrayList<SubSysActivityWithdraw>();
		}
		return datas;
		
	}
	@Override
	public Integer getCountUnhandle() {
		return sqlSessionTemplate.selectOne(this.getQueryPath("getCountUnhandle"));
	}
}
