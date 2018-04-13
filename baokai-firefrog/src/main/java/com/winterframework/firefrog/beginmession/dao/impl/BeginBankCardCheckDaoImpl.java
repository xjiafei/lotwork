package com.winterframework.firefrog.beginmession.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PageBeanUtilsBean;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.winterframework.firefrog.beginmession.dao.BeginBankCardCheckDao;
import com.winterframework.firefrog.beginmession.dao.vo.BeginBankCardCheck;
import com.winterframework.firefrog.beginmession.entity.BeginCheckData;
import com.winterframework.firefrog.beginmession.entity.BeginSearchCheckData;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository
public class BeginBankCardCheckDaoImpl extends BaseIbatis3Dao<BeginBankCardCheck> implements
		BeginBankCardCheckDao {
	
	@Override
	public CountPage<BeginSearchCheckData> queryBankCardChecksByCondition(
			PageRequest<BeginCheckData> pageRequest) throws Exception {
		Map<String, Object> map = new PageBeanUtilsBean().describe(pageRequest.getSearchDo());
		Long totalCount = sqlSessionTemplate.selectOne("queryBankCardCheckCount", map);
		if (totalCount == null || totalCount == 0L) {
			return new CountPage<BeginSearchCheckData>(0);
		}
		CountPage<BeginSearchCheckData> page = new CountPage<BeginSearchCheckData>(pageRequest.getPageNumber(),
				pageRequest.getPageSize(), totalCount.intValue());
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		filters.putAll(map);
		log.info("param : " + filters.toString());
		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<BeginSearchCheckData> list = new ArrayList<BeginSearchCheckData>();
		list = sqlSessionTemplate.selectList("queryBankCardChecksByCondition", filters, rowBounds);
		page.setResult(list);
		return page;
	}
	
	@Override
	public Integer updateCheckStatus(Long userId, String checkUser, Long checkStatus) {
		Map<String,Object> params =Maps.newHashMap();
		params.put("checkUser", checkUser);
		params.put("checkStatus", checkStatus);
		params.put("checkTime", new Date());
		params.put("userId",userId);
		return this.sqlSessionTemplate.update(this.getQueryPath("update"), params);
	}
	
	@Override
	public List<BeginSearchCheckData> queryCheckDataAllByCondition(BeginCheckData req) throws Exception{
		Map<String,Object> params =Maps.newHashMap();
		params.put("status", req.getStatus());
		params.put("timeStart", req.getTimeStart());
		params.put("timeEnd", req.getTimeEnd());
		return this.sqlSessionTemplate.selectList(this.getQueryPath("queryBankCardChecksByCondition"), params);
	}
	
	@Override
	public BeginBankCardCheck queryByUserId(Long userId) throws Exception{
		Map<String,Object> params =Maps.newHashMap();
		params.put("userId", userId);
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("findByCondition"), params);
	}
	
	@Override
	public Long queryBetsTotalByCconditions(Long userId,Date startTime, Date endTime){
		Map<String,Object> params =Maps.newHashMap();
		params.put("userId", userId);
		params.put("timeStart", startTime);
		params.put("timeEnd", endTime);
		return this.sqlSessionTemplate.selectOne("queryBetsTotalByCconditions", params);
	}
	

}
