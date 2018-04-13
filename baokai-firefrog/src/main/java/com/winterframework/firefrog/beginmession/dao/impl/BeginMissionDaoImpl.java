package com.winterframework.firefrog.beginmession.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PageBeanUtilsBean;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.winterframework.firefrog.beginmession.dao.BeginMissionDao;
import com.winterframework.firefrog.beginmession.dao.vo.BeginMission;
import com.winterframework.firefrog.beginmession.entity.CancelListData;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.Status;
import com.winterframework.firefrog.beginmession.utils.BeginMissionServiceHelper;
import com.winterframework.firefrog.beginmession.web.dto.BeginCancelListDto;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 
 * @author Ami.Tsai
 *
 */
@Repository
public class BeginMissionDaoImpl extends BaseIbatis3Dao<BeginMission> implements BeginMissionDao{

	@Override
	public BeginMission findByUserId(Long userId) {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("findByUserId"), userId);
	}

	@Override
	public Long findFirstWithdraw(Long userId, List<Long> status) {
		Map<String,Object> params =Maps.newHashMap();
		params.put("userId", userId);
		params.put("missionStatus", Status.VALID.getValue());	  	
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("findFirstWithdraw"), params);
	}

	@Override
	public Long findFirstcharge(Long userId, Long status) { 
		Map<String,Object> params =Maps.newHashMap();
		params.put("userId", userId);
		params.put("status", status);
		params.put("missionStatus", Status.VALID.getValue());
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("findFirstcharge"), params);
	}

	@Override
	public Integer updateMissionTime(Long userId , Date bindCardTime,Date misisonEndTime,Date bindCardLockTime) {
		Map<String,Object> params =Maps.newHashMap();
		params.put("userId", userId);
		params.put("bindCardDate", bindCardTime);
		params.put("misisonEndTime", misisonEndTime);
		params.put("bindCardLockTime", bindCardLockTime);		
		return this.sqlSessionTemplate.update(this.getQueryPath("updateMissionTime"), params);
	}

	@Override
	public Integer updateChargeAmt(Long userId, Long chargeAmt) {
		Map<String,Object> params =Maps.newHashMap();
		params.put("userId", userId);
		params.put("chargeAmt", chargeAmt);
		params.put("chargeTime", new Date());		
		return this.sqlSessionTemplate.update(this.getQueryPath("updateChargeAmt"), params);
	}

	@Override
	public Integer updateWithdrawAmt(Long userId, Long withdrawAmt) {
		Map<String,Object> params =Maps.newHashMap();
		params.put("userId", userId);
		params.put("withdrawAmt", withdrawAmt);
		params.put("withdrawTime", new Date());		
		return this.sqlSessionTemplate.update(this.getQueryPath("updateWithdrawAmt"), params);
	}

	@Override
	public void cancelMission(Long userId, String cancelReason,String cancelUser) {
		Map<String,Object> map = Maps.newHashMap();
		map.put("userId", userId);
		map.put("cancelReason", cancelReason);
		map.put("status", Status.CANCEL.getValue());		
		if(cancelUser==null){
			map.put("cancelUser", BeginMissionServiceHelper.AUTO_USER);					
		}else{
			map.put("cancelUser", cancelUser);					
		}

		map.put("cancelTime", new Date());				
		this.sqlSessionTemplate.update(this.getQueryPath("cancelMission"), map);
	}
	
	@Override
	public CountPage<CancelListData> searchCancelList(PageRequest<BeginCancelListDto> pageRequest) throws Exception{
		Map<String, Object> map = new PageBeanUtilsBean().describe(pageRequest.getSearchDo());
		Long totalCount = sqlSessionTemplate.selectOne("searchCancelListCount", map);
		if (totalCount == null || totalCount == 0L) {
			return new CountPage<CancelListData>(0);
		}
		CountPage<CancelListData> page = new CountPage<CancelListData>(pageRequest.getPageNumber(),
				pageRequest.getPageSize(), totalCount.intValue());
		
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		filters.putAll(map);
		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<CancelListData> list = sqlSessionTemplate.selectList(this.getQueryPath("searchCancelList"), map,rowBounds);
		log.info("list :"+list.size());
		page.setResult(list);
		return page;
	}
	@Override
	public List<CancelListData> searchCancelListAll(BeginCancelListDto req) throws Exception {
		Map<String,Object> params =Maps.newHashMap();
		params.put("statusList", req.getStatusList());
		params.put("timeStart", req.getTimeStart());
		params.put("timeEnd", req.getTimeEnd());
		return this.sqlSessionTemplate.selectList(this.getQueryPath("searchCancelList"), params);
	}

	@Override
	public void updateStatusValid(Long userId) {
		Map<String,Object> params =Maps.newHashMap();
		params.put("userId", userId);
		params.put("status", Status.VALID.getValue());
		params.put("validTime", new Date());
		this.sqlSessionTemplate.update(this.getQueryPath("updateStatusValid"), params);
		
	}

	@Override
	public void updateBindCardLockTime(Long userId) {
		this.sqlSessionTemplate.update(this.getQueryPath("updateBindCardLockTime"), userId);
	}

}
