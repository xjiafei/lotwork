package com.winterframework.firefrog.active.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.active.dao.IActivityResultDao;
import com.winterframework.firefrog.active.dao.vo.ActivityResult;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;


@Repository("activityResultDaoImpl")
public class ActivityResultDaoImpl extends BaseIbatis3Dao<ActivityResult> implements IActivityResultDao{

	private Logger log = LoggerFactory.getLogger(ActivityResultDaoImpl.class);
	
	@Override
	public ActivityResult getUserPrize(ActivityResult actRsVO) {
		log.debug("getUserPrize: userId=" + actRsVO.getUserId() + " ActivityId=" + actRsVO.getActivityId());
		return this.sqlSessionTemplate.selectOne("queryActivityResultToday", actRsVO);
	}

	@Override
	public Long queryActivityResultAwardToday(ActivityResult actRsVO) {
		log.debug("getAwardRecord: type=" + actRsVO.getType());
		return this.sqlSessionTemplate.selectOne("queryActivityResultAwardToday", actRsVO);
	}

	@Override
	public ActivityResult queryActivityResultByUserIdAndType(Long userId,
			String type) {
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("type", type);
		return this.sqlSessionTemplate.selectOne("queryActivityResultByUserIdAndType", type);
	}
	
	@Override
	public List<ActivityResult> getListAwardRecord(ActivityResult actRsVO) {
		
		return this.sqlSessionTemplate.selectList("queryActivityResultListAwardToday", actRsVO);
	}

	@Override
	public ActivityResult queryActivityResultByUserIdAndActivityIdAndResult(Long userId, Long activityId,
			String day) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("activityId", activityId);
		params.put("result", day);
		return this.sqlSessionTemplate.selectOne("queryActivityResultByUserIdAndActivityIdAndResult", params);
	}
	
	public Integer updateTypeById(Long id, String type){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		params.put("type", type);
		return this.sqlSessionTemplate.update("updateTypeById", params);
	}
	
	public Integer updateTypeAndStatusById(Long id, String type, String status){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		params.put("type", type);
		params.put("status", status);
		return this.sqlSessionTemplate.update("updateTypeAndStatusById", params);
	}

	@Override
	public String queryMinTypeByUserIdAndActivityIdAndLessThanResult(Long userId, Long activityId, String day) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("activityId", activityId);
		params.put("result", day);
		return this.sqlSessionTemplate.selectOne("queryMinTypeByUserIdAndActivityIdAndLessThanResult", params);
	}

	@Override
	public Long queryStatusZeroByUserIdAndActivityId(Long userId, Long activityId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("activityId", activityId);
		return this.sqlSessionTemplate.selectOne("queryStatusZeroByUserIdAndActivityId", params);
	}
	
	@Override
	public List<ActivityResult> queryAllResultByUserIdAndActivityIdOrderByResult(Long userId, Long activityId){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("activityId", activityId);
		return this.sqlSessionTemplate.selectList("queryAllResultByUserIdAndActivityIdOrderByResult", params);
	}

	@Override
	public List<ActivityResult> getBeforeOneDayResultByActivityIdAndTypeAndUserIdAndActivityId(
			Long userId, Long activityId, String type) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("activityId", activityId);
		params.put("type", type);		
		return this.sqlSessionTemplate.selectList("getBeforeOneDayResultByActivityIdAndTypeAndUserIdAndActivityId", params);
	}

}
