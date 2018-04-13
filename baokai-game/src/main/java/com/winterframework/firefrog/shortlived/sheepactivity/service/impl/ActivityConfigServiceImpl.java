package com.winterframework.firefrog.shortlived.sheepactivity.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IActivityConfigDao;
import com.winterframework.firefrog.game.dao.vo.ActivityConfig;
import com.winterframework.firefrog.game.web.controller.ActivityController;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IActivityConfigService;


/** 
* @ClassName ActivityAwardConfigServiceImpl 
* @Description 活动配置 
* @author  hugh
* @date 2014年12月2日 下午3:33:03 
*  
*/
@Service("activityConfigServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class ActivityConfigServiceImpl implements IActivityConfigService{
	private Logger log = LoggerFactory.getLogger(ActivityConfigServiceImpl.class);
	@Resource(name = "activityConfigDaoImpl")
	private IActivityConfigDao activityConfigDaoImpl; 
	
	public ActivityConfig getById(Long id) throws Exception{
		log.info("--------------------service getById--------------------");
		log.debug("id = "+id);
		return activityConfigDaoImpl.getActCfgById(id);
	}
	
	
}
