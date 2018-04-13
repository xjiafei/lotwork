package com.winterframework.firefrog.game.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IActivitySheepOperateLogDao;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepOperateLog;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName ActivitySheepOperateLogDaoImpl 
* @Description 羊年活动日志
* @author  hugh
* @date 2015年1月14日 下午3:30:56 
*  
*/
@Repository("activitySheepOperateLogDaoImpl")
public class ActivitySheepOperateLogDaoImpl  extends BaseIbatis3Dao<ActivitySheepOperateLog> implements IActivitySheepOperateLogDao{

}
