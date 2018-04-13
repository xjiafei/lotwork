package com.winterframework.firefrog.shortlived.sheepactivity.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.shortlived.sheepactivity.dao.IActivitySheepWheelSurfDao;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepWheelSurf;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName ActivitySheepWheelSurfDaoImpl 
* @Description 羊年活动转盘 
* @author  hugh
* @date 2015年1月14日 下午3:30:56 
*  
*/
@Repository("activitySheepWheelSurfDaoImpl")
public class ActivitySheepWheelSurfDaoImpl  extends BaseIbatis3Dao<ActivitySheepWheelSurf> implements IActivitySheepWheelSurfDao{
	public Long getUncheckNum(){
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getUncheckNum"));
	}

	@Override
	public ActivitySheepWheelSurf getUserRotary(Long userId) {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getUserRotary"),userId);
	}

	@Override
	public void reduceLastNum(ActivitySheepWheelSurf userId) throws Exception {
		if( this.sqlSessionTemplate.update(this.getQueryPath("reduceTime"),userId)<1){
			throw new Exception("作弊");
		}
		
	}

	@Override
	public void addUserRotaryLastNum(Long userId, Long times,Long amount) {
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("times", times);
		map.put("amount", amount);
		this.sqlSessionTemplate.update(this.getQueryPath("addUserRotaryLastNum"),map);
		
	}
}
