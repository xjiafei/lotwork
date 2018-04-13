package com.winterframework.firefrog.shortlived.sheepactivity.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IActivityAwardConfigDao;
import com.winterframework.firefrog.game.dao.vo.ActivityAwardConfig;
import com.winterframework.firefrog.game.service.IGameFundRiskService;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.IActivitySheepOperateLogDao;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepOperateLog;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepConfigOperateRequest;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IActivitySheepOperateLogService;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;


/** 
* @ClassName ActivitySheepOperateLogServiceImpl 
* @Description 羊年活动红包
* @author  hugh
* @date 2015年1月12日 下午3:33:03 
*  
*/
@Service("activitySheepOperateLogServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class ActivitySheepOperateLogServiceImpl implements IActivitySheepOperateLogService{
	
	@Resource(name = "activityAwardConfigDaoImpl")
	private IActivityAwardConfigDao activityAwardConfigDaoImpl; 
	
	@Resource(name = "activitySheepOperateLogDaoImpl")
	private IActivitySheepOperateLogDao activitySheepOperateLogDaoImpl; 
	
	@Resource(name = "gameFundRiskServiceImpl")
	private IGameFundRiskService fundRiskService;
	
	public Page<ActivitySheepOperateLog> queryPage(
			PageRequest<ActivitySheepOperateLog> pr) {
		return activitySheepOperateLogDaoImpl.getAllByPage(pr);
	}
		
	public void update(ActivitySheepOperateLog activity) throws Exception{
		activitySheepOperateLogDaoImpl.update(activity);
	}
	
	public void updateEntityByType(ActivitySheepOperateLog activity) throws Exception{
			
		
		
	}
	
	public void insert(ActivitySheepOperateLog activity) throws Exception{
			
		activitySheepOperateLogDaoImpl.insert(activity);
		
	}
	
	public void updateActivitySheepConfigOperate(ActivitySheepConfigOperateRequest request) throws Exception{
			
		
		ActivityAwardConfig config = activityAwardConfigDaoImpl.getById(request.getActivityConfigId());
		
		config.setRechargeLimit(request.getIsOpen());
		activityAwardConfigDaoImpl.update(config);
		
		ActivitySheepOperateLog activity = new ActivitySheepOperateLog();
		activity.setActivityConfigId(request.getActivityConfigId());
		List<ActivitySheepOperateLog> logs = activitySheepOperateLogDaoImpl.getAllByEntity(activity);
		List<ActivitySheepOperateLog> updates =  request.getLogs();
		for (ActivitySheepOperateLog log1 : logs) {
			boolean have = false;
			for (ActivitySheepOperateLog log2 : updates) {				
				if(log2.getId()!=null && log1.getId() == log2.getId()){
					have= true;	
					log1.setGmtCreated(log2.getGmtCreated());
					if(log1.getGmtCreated().after(new Date())) {
						log1.setOperateContent("已开启");
					}
				}				
			}
			
			if( request.getIsOpen() == 1 && log1.getGmtCreated().after(new Date())){
				log1.setOperateContent("已开启");
			}else if( request.getIsOpen() == 0 ){
				log1.setOperateContent("已关闭");
			}
			
			if(!have){
				activitySheepOperateLogDaoImpl.delete(log1.getId());
			}else{
				activitySheepOperateLogDaoImpl.update(log1);
			}
		}
		
		for (ActivitySheepOperateLog log1 : updates) {
			boolean have = false;
			for (ActivitySheepOperateLog log2 : logs) {				
				if(log1.getId()!=null && log1.getId() == log2.getId()){
					have= true;					
				}				
			}
			
			if( request.getIsOpen() == 1 ){
				log1.setOperateContent("已开启");
			}else if( request.getIsOpen() == 0 ){
				log1.setOperateContent("已关闭");
			}
			
			if(!have){
				log1.setActivityId(5L);
				log1.setActivityConfigId(request.getActivityConfigId());
				activitySheepOperateLogDaoImpl.insert(log1);
			}
		}
	}
	
}
