package com.winterframework.firefrog.game.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IActivityAwardConfigDao;
import com.winterframework.firefrog.game.dao.vo.ActivityAwardConfig;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("activityAwardConfigDaoImpl")
public class ActivityAwardConfigDaoImpl  extends BaseIbatis3Dao<ActivityAwardConfig> implements IActivityAwardConfigDao{

}
