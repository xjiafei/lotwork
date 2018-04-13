package com.winterframework.firefrog.shortlived.sheepactivity.dao;

import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepWheelSurf;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/** 
* @ClassName IActivitySheepWheelSurfDao 
* @Description 羊年活动转运盘 
* @author  hugh
* @date 2015年1月14日 下午3:29:21 
*  
*/
public interface IActivitySheepWheelSurfDao extends BaseDao<ActivitySheepWheelSurf>{
	Long getUncheckNum();

	ActivitySheepWheelSurf getUserRotary(Long userId);

	void addUserRotaryLastNum(Long userId, Long times, Long amount);
	void reduceLastNum(ActivitySheepWheelSurf userId) throws Exception;
}
