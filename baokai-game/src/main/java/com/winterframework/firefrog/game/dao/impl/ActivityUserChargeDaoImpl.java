package com.winterframework.firefrog.game.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IActivityUserChargeDao;
import com.winterframework.firefrog.game.dao.vo.ActivityUserCharge;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("activityUserChargeDaoImpl")
public class ActivityUserChargeDaoImpl  extends BaseIbatis3Dao<ActivityUserCharge> implements IActivityUserChargeDao{

}
