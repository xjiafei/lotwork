package com.winterframework.firefrog.game.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IActivitySheepDetailDao;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepDetail;
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
	
}
