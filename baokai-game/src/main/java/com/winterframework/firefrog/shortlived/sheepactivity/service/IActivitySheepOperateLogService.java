package com.winterframework.firefrog.shortlived.sheepactivity.service;

import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepOperateLog;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepConfigOperateRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName IActivitySheepOperateLogService 
* @Description 羊年活动猜大小
* @author  hugh
* @date 2015年1月14日 下午4:00:34 
*  
*/
public interface IActivitySheepOperateLogService {
	Page<ActivitySheepOperateLog> queryPage(PageRequest<ActivitySheepOperateLog> pr) ;
	void update(ActivitySheepOperateLog activity) throws Exception;
	void updateEntityByType(ActivitySheepOperateLog activity) throws Exception;
	void insert(ActivitySheepOperateLog activity) throws Exception;
	void updateActivitySheepConfigOperate(ActivitySheepConfigOperateRequest request) throws Exception;
}
