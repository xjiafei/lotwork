package com.winterframework.firefrog.shortlived.sheepactivity.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IActivityAwardConfigDao;
import com.winterframework.firefrog.game.dao.vo.ActivityAwardConfig;
import com.winterframework.firefrog.game.service.IGameFundRiskService;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.IActivitySheepDetailDao;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.IActivitySheepOperateLogDao;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.IActivitySheepWheelSurfDao;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepDetail;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepOperateLog;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepWheelSurf;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IActivitySheepWheelSurfService;
import com.winterframework.firefrog.shortlived.sheepactivity.service.util.Award;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;


/** 
* @ClassName ActivitySheepWheelSurfServiceImpl 
* @Description 羊年活动转盘
* @author  hugh
* @date 2015年1月12日 下午3:33:03 
*  
*/
@Service("activitySheepWheelSurfServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class ActivitySheepWheelSurfServiceImpl implements IActivitySheepWheelSurfService{
	
	@Resource(name = "activitySheepWheelSurfDaoImpl")
	private IActivitySheepWheelSurfDao activitySheepWheelSurfDaoImpl; 
	
	@Resource(name = "activitySheepDetailDaoImpl")
	private IActivitySheepDetailDao activitySheepDetailDaoImpl; 
	
	@Resource(name = "activityAwardConfigDaoImpl")
	private IActivityAwardConfigDao activityAwardConfigImpl; 
	
	@Resource(name = "activitySheepOperateLogDaoImpl")
	private IActivitySheepOperateLogDao activitySheepOperateLogDaoImpl; 
	
	@Resource(name = "gameFundRiskServiceImpl")
	private IGameFundRiskService fundRiskService;
	
	@Resource(name="userCustomerDaoImpl")
	private IUserCustomerDao userCustomerDao;
	
	public Page<ActivitySheepWheelSurf> queryPage(
			PageRequest<ActivitySheepWheelSurf> pr) {
		return activitySheepWheelSurfDaoImpl.getAllByPage(pr);
	}
		
	public void update(ActivitySheepWheelSurf activity) throws Exception{
		activitySheepWheelSurfDaoImpl.update(activity);
	}
	
	public void updateEntityByType(ActivitySheepWheelSurf activity) throws Exception{
		ActivitySheepWheelSurf big = activitySheepWheelSurfDaoImpl.getById(activity.getId());
		if ( activity.getUpdateType() == 1){
			//编辑
			big.setUpdateStatus(1L);
			big.setUpdateName(activity.getUpdateName());
			big.setUpdateReason(activity.getUpdateReason());
			big.setUpdateLastNum(activity.getUpdateLastNum());
			
			ActivitySheepDetail detail = new ActivitySheepDetail();
			detail.setActivityId(5L);
			detail.setActivityTime(new Date());
			detail.setActivityType(1L);//管理员添加
			detail.setAward(0L);//管理员添加
			detail.setChannel(4L);
			if(activity.getUpdateLastNum() > 0){
				detail.setGetNum(activity.getUpdateLastNum());
			}else{
				detail.setUseNum(0-activity.getUpdateLastNum());
			}
			detail.setUserId(big.getUserId());
			detail.setResult("");//管理员添加
			detail.setRecharge(0L);
			detail.setStatus(0L);
			detail.setUserName(big.getUserName());
			activitySheepDetailDaoImpl.insert(detail);
			
			ActivitySheepOperateLog log = new ActivitySheepOperateLog();
			log.setGmtCreated(new Date());
			log.setActivityId(5L);
			log.setActivityName("羊年转运盘");
			log.setNum(activity.getUpdateLastNum());
			if(activity.getUpdateLastNum() > 0){
				log.setOperateContent("添加"+activity.getUpdateLastNum()+"次");
			}else{
				log.setOperateContent("减少"+(0-activity.getUpdateLastNum())+"次");
			}
			log.setUserName(big.getUserName());
			log.setOperateName(big.getUpdateName());
			log.setOperateType(1L);
			activitySheepOperateLogDaoImpl.insert(log );
			
		}else if( activity.getUpdateType() == 2){
			//发布
			big.setUpdateStatus(0L);
			
			big.setUpdateReason("");
			big.setLastNum(big.getLastNum() + big.getUpdateLastNum());
			
			
			List<ActivitySheepDetail> detail = activitySheepDetailDaoImpl.getNotPublishByUserId(big.getUserId(),5L);
			for (ActivitySheepDetail activitySheepDetail : detail) {
				activitySheepDetail.setStatus(1L);
				activitySheepDetailDaoImpl.update(activitySheepDetail);
			}
			
			ActivitySheepOperateLog log = new ActivitySheepOperateLog();
			log.setGmtCreated(new Date());
			log.setActivityId(5L);
			log.setActivityName("羊年转运盘");
			log.setNum(activity.getUpdateLastNum());
			if(big.getUpdateLastNum() > 0){
				log.setOperateContent("添加"+big.getUpdateLastNum()+"次");
			}else{
				log.setOperateContent("减少"+(0-big.getUpdateLastNum())+"次");
			}
			big.setUpdateLastNum(0L);
			log.setUserName(big.getUserName());
			
			log.setOperateName(big.getUpdateName());
			big.setUpdateName("");
			log.setOperateType(2L);
			activitySheepOperateLogDaoImpl.insert(log );
		}else if( activity.getUpdateType() == 3){
			//拒绝发布
			big.setUpdateStatus(0L);
			
			big.setUpdateReason("");
			//big.setLastNum(big.getLastNum() + big.getUpdateLastNum());

			
			List<ActivitySheepDetail> detail = activitySheepDetailDaoImpl.getNotPublishByUserId(big.getUserId(),5L);			
			for (ActivitySheepDetail activitySheepDetail : detail) {
//				activitySheepDetail.setStatus(2L);
//				activitySheepDetailDaoImpl.update(activitySheepDetail);
				activitySheepDetailDaoImpl.delete(activitySheepDetail.getId());
			}
			
			ActivitySheepOperateLog log = new ActivitySheepOperateLog();
			log.setGmtCreated(new Date());
			log.setActivityId(5L);
			log.setActivityName("羊年转运盘");
			log.setNum(activity.getUpdateLastNum());
			if(big.getUpdateLastNum() > 0){
				log.setOperateContent("添加"+big.getUpdateLastNum()+"次");
			}else{
				log.setOperateContent("减少"+(1-big.getUpdateLastNum())+"次");
			}
			big.setUpdateLastNum(0L);
			log.setUserName(big.getUserName());
			
			log.setOperateName(big.getUpdateName());
			big.setUpdateName("");
			log.setOperateType(3L);
			activitySheepOperateLogDaoImpl.insert(log );
		}
		activitySheepWheelSurfDaoImpl.update(big);	
		
		
	}
	
	@Override
	@Transactional( rollbackFor = Exception.class,isolation=Isolation.SERIALIZABLE)
	public Award getAward(Long userId,Long activityId,Long channel) throws Exception{
		ActivityAwardConfig activity = new ActivityAwardConfig();
		activity.setActivityId(activityId);
		List<ActivityAwardConfig> configs = activityAwardConfigImpl.getAllByEntity(activity);
		
		ActivitySheepWheelSurf big = new ActivitySheepWheelSurf();
		big.setUserId(userId);
		List<ActivitySheepWheelSurf> bigs = activitySheepWheelSurfDaoImpl.getAllByEntity(big);
		ActivitySheepWheelSurf user = bigs.get(0);
		
		if(user.getLastNum()<=0){
			throw new Exception("user.getLastNum()<=0");
		}
		
		//开奖机
		Award award = getAward(configs);		
		if(award == null){
			return new Award(false);
		}
		
		for (ActivityAwardConfig config : configs) {
			if(config.getId().longValue() == award.getId()){
				config.setWinNumber(config.getWinNumber()==null?0:config.getWinNumber() + 1 );
				config.setLastNumber(config.getLastNumber()==null?0:config.getLastNumber() - 1 );
			}
			activityAwardConfigImpl.update(config);
		}
		
	
		ActivitySheepDetail detail = new ActivitySheepDetail();
		detail.setActivityId(5L);
		detail.setActivityTime(new Date());
		detail.setActivityType(0L);
		detail.setChannel(channel);
		detail.setUseNum(1L);
		detail.setStatus(3L);
		detail.setResult(award.getDesc());
		detail.setAward(award.getAward());
		detail.setUserName(user.getUserName());
		detail.setActivityConfigId(award.getId());
		detail.setUserId(userId);
		activitySheepDetailDaoImpl.insert(detail);
		
		user.setLastNum(user.getLastNum() - 1);
		user.setAllAward(user.getAllAward() + award.getAward());
		activitySheepWheelSurfDaoImpl.reduceLastNum(user);
		award.setLastGuessNum(user.getLastNum());
		award.setDate(new Date());
		return award;
	}
	
	
	/** 
	* @Title: getAward 
	* @Description: 开奖机
	* @param configs
	* @return
	*/
	public Award getAward(List<ActivityAwardConfig> configs){

		if(configs == null){
			return null;
		}
		
		List<Award> awards = new ArrayList<Award>();
		//按比例加入奖品
		for (ActivityAwardConfig activityAwardConfig : configs) {	
			//系统都存的  *10000  ，比例9.55% 存  95500
			long num = (long) ((activityAwardConfig.getLastNumber() * activityAwardConfig.getRatio())*0.01);//剩余数 * 比例
			for (int i = 0; i < num; i++) {
				awards.add(new Award(activityAwardConfig.getId(),activityAwardConfig.getAward(),activityAwardConfig.getAwardName()));
			}
		}
		
		//混乱  再  随机取
		Collections.shuffle(awards);
		if(awards.size()!=0){
			return awards.get(new Random().nextInt(awards.size()));
		}else{
			return null;
		}
		
	}

	@Override
	public ActivitySheepWheelSurf getUserRotary(Long userId) {
		return activitySheepWheelSurfDaoImpl.getUserRotary(userId);
	}

	@Override
	public void initUserRotary(Long userId, String userName,Long channel) {
		ActivitySheepWheelSurf asbl=new ActivitySheepWheelSurf();
		asbl.setUserName(userName);
		asbl.setUserId(userId);
		asbl.setLastNum(0L);
		asbl.setAllAward(0l);
		asbl.setStatus(0l);
		asbl.setChannel(channel);
		activitySheepWheelSurfDaoImpl.insert(asbl);
		
	}

	@Override
	public void initUserRotary(Long userId, Long times, Long amount,Long channel) {
		ActivitySheepWheelSurf asbl=new ActivitySheepWheelSurf();
		asbl.setUserName(userCustomerDao.getUserNameById(userId));
		asbl.setUserId(userId);
		asbl.setLastNum(times);
		asbl.setAllRecharge(amount);
		asbl.setStatus(0l);
		asbl.setChannel(channel);
		activitySheepWheelSurfDaoImpl.insert(asbl);
		
	}

	@Override
	public void addUserRotaryLastNum(Long userId, Long times,Long amount) {
		activitySheepWheelSurfDaoImpl.addUserRotaryLastNum(userId,times,amount);
		
	}
}
