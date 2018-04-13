package com.winterframework.firefrog.shortlived.sheepactivity.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.shortlived.sheepactivity.dao.IActivitySheepDetailDao;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepDetail;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepDetailCount;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepHongBaoCount;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepHongBaoDetail;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName ActivitySheepDetailDaoImpl 
* @Description 羊年详情
* @author  hugh
* @date 2015年1月14日 下午3:30:56 
*  
*/
@Repository("activitySheepDetailDaoImpl")
public class ActivitySheepDetailDaoImpl  extends BaseIbatis3Dao<ActivitySheepDetail> implements IActivitySheepDetailDao{
	
	public List<ActivitySheepDetail> getNotPublishByUserId(Long userId,Long activityId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("activityId", activityId);
		return this.sqlSessionTemplate.selectList("getNotPublishByUserId", map);
	}
	
	public Long getUncheckNum(Long activityId){
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getUncheckNum"),activityId);
	}

	@Override
	public List<ActivitySheepDetail> getUserDiceDetailList(Long userId) {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getUserDiceDetailList"),userId);
	}

	@Override
	public List<ActivitySheepDetail> getAllUserRotaryDetailList() {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getAllUserRotaryDetailList"));
	}

	@Override
	public List<ActivitySheepDetail> getUserRotaryDetailList(Long userId) {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getUserRotaryDetailList"),userId);
	}
	
	public List<ActivitySheepDetailCount> getCounts(Long activityId, Date beginDate,Date endDate,boolean isGroupByChannel ,boolean isGroupByLevel){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("beginTime", beginDate);
		map.put("endTime", endDate);
		if(isGroupByChannel){
			map.put("isGroupByChannel", "isGroupByChannel");
		}else{
			map.put("isGroupByChannel", null);
		}
		
		if(isGroupByLevel){
			map.put("isGroupByLevel", "isGroupByLevel");
		}else{
			map.put("isGroupByLevel", null);
		}
		
		map.put("activityId", activityId);
		return this.sqlSessionTemplate.selectList(  this.getQueryPath("getSheepDetailCounts"), map);
	}
	
	public ActivitySheepHongBaoDetail getHongBaoCounts(Long hongBaoId, Date beginDate,Date endDate){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("beginTime", beginDate);
		map.put("endTime", endDate);
		map.put("hongBaoId", hongBaoId);		
		map.put("activityId", 3);
		return this.sqlSessionTemplate.selectOne(  this.getQueryPath("getSheepHongBaoDetailCounts"), map);
	}
}
