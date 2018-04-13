package com.winterframework.firefrog.active.dao.impl;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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




import com.winterframework.firefrog.active.dao.IActivityDao;
import com.winterframework.firefrog.active.dao.vo.Activity;
import com.winterframework.firefrog.active.dao.vo.ActivityWinningLog;
import com.winterframework.firefrog.active.entity.ActivityAuguest;
import com.winterframework.firefrog.active.web.dto.ActivityAwardsRequest;
import com.winterframework.firefrog.fund.dao.vo.FundCharge;
import com.winterframework.firefrog.fund.dao.vo.VOConverter;
import com.winterframework.firefrog.fund.entity.FundChargeOrder;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.firefrog.fund.web.controller.vo.SumCount;
import com.winterframework.firefrog.fund.web.dto.ChargeQueryRequest;
import com.winterframework.firefrog.user.dao.vo.LevelRecycle;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("activityDaoImpl")
public class ActivityDaoImpl extends BaseIbatis3Dao<Activity> implements IActivityDao{

	private Logger log = LoggerFactory.getLogger(ActivityDaoImpl.class);
	
	@Override
	public Activity getActivityByCode(Activity actVO) {
		log.debug("getActivityByCode: ActivityCode=" + actVO.getActivityCode());
		return this.sqlSessionTemplate.selectOne("queryActivityByCode", actVO);		
	}

	@Override
	public Long getUserAmountPerDay(Map<String, Long> amountMap) {
		log.debug("getUserAmountPerDay: userId=" + amountMap.get("userId"));
		return this.sqlSessionTemplate.selectOne("queryUserAmountPerDay", amountMap);		
	}

	@Override
	public List<Activity> getActivityDetail(String type) {

		List<Activity> result=this.sqlSessionTemplate.selectList("queryActivityByTypeStatus", type);
		return result;
	}

	@Override
	public CountPage<ActivityWinningLog> getAwardsUntreadList(PageRequest<ActivityAwardsRequest> pageReqeust) {
		
		ActivityAwardsRequest search = pageReqeust.getSearchDo();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = new PageBeanUtilsBean().describe(search);
			map.putAll(pageReqeust.getFilters());
			map.put("activity_id", pageReqeust.getSearchDo().getActId());
			map.put("querydate", pageReqeust.getSearchDo().getActDate().replace("-", ""));
		}catch(Exception e){}
		
		
		Long totalCount = sqlSessionTemplate.selectOne("getWinningCountByPage", map);
		if (totalCount == 0) {
			return new CountPage<ActivityWinningLog>(0);
		}
		CountPage<ActivityWinningLog> page = new CountPage<ActivityWinningLog>(pageReqeust.getPageNumber(),
				pageReqeust.getPageSize(), (int) (long)totalCount);
//		page.setSum(totalCount.getSum());
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageReqeust.getSortColumns());
		filters.put("activity_id", pageReqeust.getSearchDo().getActId());
		filters.put("gmt_created", pageReqeust.getSearchDo().getActDate());
		filters.putAll(map);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<ActivityWinningLog> list = sqlSessionTemplate.selectList("getWinningByPage", filters, rowBounds);


		page.setResult(list);
		return page;
	}

	@Override
	public CountPage<ActivityWinningLog> getAwardsTreadList(PageRequest<ActivityAwardsRequest> pageReqeust) {
		ActivityAwardsRequest search = pageReqeust.getSearchDo();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = new PageBeanUtilsBean().describe(search);
			map.putAll(pageReqeust.getFilters());
			map.put("activity_id", pageReqeust.getSearchDo().getActId());
			map.put("querydate", pageReqeust.getSearchDo().getActDate().replace("-", ""));
		}catch(Exception e){}
		
		
		Long totalCount = sqlSessionTemplate.selectOne("getWinningCompleteCountByPage", map);
		if (totalCount == 0) {
			return new CountPage<ActivityWinningLog>(0);
		}
		CountPage<ActivityWinningLog> page = new CountPage<ActivityWinningLog>(pageReqeust.getPageNumber(),
				pageReqeust.getPageSize(), (int) (long)totalCount);
//		page.setSum(totalCount.getSum());
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageReqeust.getSortColumns());
		filters.put("activity_id", pageReqeust.getSearchDo().getActId());
		filters.put("gmt_created", pageReqeust.getSearchDo().getActDate());
		filters.putAll(map);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<ActivityWinningLog> list = sqlSessionTemplate.selectList("getWinningCompleteByPage", filters, rowBounds);


		page.setResult(list);
		return page;
	}
}
