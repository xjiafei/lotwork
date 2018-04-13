package com.winterframework.firefrog.shortlived.sheepactivity.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IActivityAwardConfigDao;
import com.winterframework.firefrog.game.dao.vo.ActivityAwardConfig;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IActivityAwardConfigService;


/** 
* @ClassName ActivityAwardConfigServiceImpl 
* @Description 活动配置 
* @author  hugh
* @date 2014年12月2日 下午3:33:03 
*  
*/
@Service("activityAwardConfigServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class ActivityAwardConfigServiceImpl implements IActivityAwardConfigService{
	
	@Resource(name = "activityAwardConfigDaoImpl")
	private IActivityAwardConfigDao activityAwardConfigDaoImpl; 
	
	public List<ActivityAwardConfig> getActivityAwardConfigByActivityId(Long id) throws Exception{
		ActivityAwardConfig activity = new ActivityAwardConfig();
		activity.setActivityId(id);
		return activityAwardConfigDaoImpl.getAllByEntity(activity);
	}
	
	public ActivityAwardConfig getById(Long id) throws Exception{

		return activityAwardConfigDaoImpl.getById(id);
	}
	
	public void update(ActivityAwardConfig config) throws Exception{
		activityAwardConfigDaoImpl.update(config);
	}
}
