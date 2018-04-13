package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.ActivityAwardConfig;
import com.winterframework.firefrog.game.dao.vo.ActivityUserAwardLog;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IActivityUserAwardLogDao  extends BaseDao<ActivityUserAwardLog>{
	List<ActivityUserAwardLog> getToday() throws Exception;

	List<ActivityUserAwardLog> getUserAwardLog(Long userId);
}
