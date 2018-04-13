package com.winterframework.firefrog.shortlived.sheepactivity.service;

import java.util.List;

import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepDetail;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepDetailCount;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepHongBaoCount;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepHongBaoDetail;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName IActivitySheepDetailService 
* @Description 羊年活动详情
* @author  hugh
* @date 2015年1月14日 下午4:00:34 
*  
*/
public interface IActivitySheepDetailService {
	Page<ActivitySheepDetail> queryPage(PageRequest<ActivitySheepDetail> pr) ;
	void update(ActivitySheepDetail activity) throws Exception;
	void updateEntityByType(ActivitySheepDetail activity) throws Exception;
	Long getUncheckNum(Long activity);
	List<ActivitySheepDetail> getUserDiceDetailList(Long userId);
	List<ActivitySheepDetail> getAllUserRotaryDetailList();
	List<ActivitySheepDetail> getUserRotaryDetailList(Long userId);
	
	List<ActivitySheepDetailCount> getCounts(String date,Long activityId) throws Exception;
	List<ActivitySheepHongBaoDetail> getCount(Long hongBaoId) throws Exception;	
}
