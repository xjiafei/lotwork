package com.winterframework.firefrog.game.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IActivityUserAwardLogDao;
import com.winterframework.firefrog.game.dao.vo.ActivityUserAwardLog;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("activityUserAwardLogDaoImpl")
public class ActivityUserAwardLogDaoImpl  extends BaseIbatis3Dao<ActivityUserAwardLog> implements IActivityUserAwardLogDao{

	@Override
	public List<ActivityUserAwardLog> getToday() throws Exception {

//		Map<String, Object> map = new HashMap<String, Object>();
//
//		map.put("lotteryId", lotteryId);
//		map.put("awardGroupId", groupId);
//		map.put("status", status);

		return sqlSessionTemplate.selectList("getToday");
	}

	@Override
	public List<ActivityUserAwardLog> getUserAwardLog(Long userId) {
		return sqlSessionTemplate.selectList("getAwardLogByUserId",userId);
	}
}
