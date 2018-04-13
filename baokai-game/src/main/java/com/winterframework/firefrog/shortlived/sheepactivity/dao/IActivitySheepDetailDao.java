package com.winterframework.firefrog.shortlived.sheepactivity.dao;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepDetail;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepDetailCount;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepHongBaoCount;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepHongBaoDetail;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/** 
* @ClassName IActivitySheepDetailDao 
* @Description 羊年活动详情 
* @author  hugh
* @date 2015年1月14日 下午3:28:07 
*  
*/
public interface IActivitySheepDetailDao extends BaseDao<ActivitySheepDetail>{
	List<ActivitySheepDetail> getNotPublishByUserId(Long userId,Long activityId);
	Long getUncheckNum(Long activityId);
	List<ActivitySheepDetail> getUserDiceDetailList(Long userId);
	List<ActivitySheepDetail> getAllUserRotaryDetailList();
	List<ActivitySheepDetail> getUserRotaryDetailList(Long userId);
	List<ActivitySheepDetailCount> getCounts(Long activityId, Date beginDate,Date endDate,boolean isGroupByChannel ,boolean isGroupByLevel);
	
	ActivitySheepHongBaoDetail getHongBaoCounts(Long hongBaoId, Date beginDate,Date endDate);
}
