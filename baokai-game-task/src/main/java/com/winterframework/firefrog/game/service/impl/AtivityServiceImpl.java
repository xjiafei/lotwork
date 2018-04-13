package com.winterframework.firefrog.game.service.impl;

import com.winterframework.firefrog.game.dao.IActivitySheepDetailDao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.IActivityAwardConfigDao;
import com.winterframework.firefrog.game.dao.IActivitySheepHongBaoDao;
import com.winterframework.firefrog.game.dao.IActivitySheepOperateLogDao;
import com.winterframework.firefrog.game.dao.vo.ActivityAwardConfig;
import com.winterframework.firefrog.game.service.IActivityService;
import com.winterframework.firefrog.schedule.gameOrder.ActivitySheepHongBaoTask;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepDetail;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepHongBao;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepOperateLog;

@Transactional(rollbackFor = Exception.class)
@Service("ativityServiceImpl")
public class AtivityServiceImpl implements IActivityService{
	private Logger log = LoggerFactory.getLogger(AtivityServiceImpl.class);
	@Resource(name = "activitySheepHongBaoDaoImpl")
	private IActivitySheepHongBaoDao activitySheepHongBaoDaoImpl; 
	
	@Resource(name = "activitySheepDetailDaoImpl")
	private IActivitySheepDetailDao activitySheepDetailDaoImpl; 
	
	@Resource(name = "activitySheepOperateLogDaoImpl")
	private IActivitySheepOperateLogDao activitySheepOperateLogDaoImpl; 
	
	@Resource(name = "activityAwardConfigDaoImpl")
	private IActivityAwardConfigDao activityAwardConfigDaoImpl;
	
	
	@Override
	public void excute() {
		ActivitySheepHongBao bao = new ActivitySheepHongBao();
		bao.setStatus(5l);
		List<ActivitySheepHongBao> list = activitySheepHongBaoDaoImpl.getAllByEntity(bao);
		try {
			if(list != null){
				for (ActivitySheepHongBao hong : list) {
					Long bet = activitySheepHongBaoDaoImpl.getUserLastDateBet(hong.getUserId(), hong.getSignTime());
					hong.setAllAward( hong.getAllAward().longValue()+bet);
					if( (hong.getTargetAward()!=null?hong.getTargetAward():0L) <= hong.getAllAward()){
						hong.setStatus(6L);
						hong.setReachTime(new Date());
						
					}
					activitySheepHongBaoDaoImpl.update(hong);
					ActivitySheepDetail detail = new ActivitySheepDetail();
					detail.setActivityId(3L);
					detail.setActivityTime(DateUtils.addDays(new Date(), -1));//所有时间减一天 表明是昨天的投注数据
					detail.setActivityType(3L);//充值添加
					detail.setAward(0L);//管理员添加
					detail.setChannel(4L);
					detail.setUseNum(0L);
					detail.setResult("");//管理员添加
					detail.setRecharge(bet);
					detail.setStatus(1L);//充值状态直接是发布
					detail.setUserName(hong.getUserName());
					detail.setUserId(hong.getUserId());
					detail.setGetNum(0L);
					detail.setActivityConfigId(hong.getId());
					activitySheepDetailDaoImpl.insert(detail);
					
					
				}
			}
		} catch (Exception e) {
			log.error("error occurs when achieve the target",e);
			e.printStackTrace();
			System.out.println(e.getMessage()+"----------------excute error");
		}
		
	}

	
	@Override
	public void excuteConfig() {
		ActivitySheepOperateLog operLog = new ActivitySheepOperateLog();
		operLog.setOperateType(5L);
		operLog.setOperateContent("已开启");
		Map<Long ,Long> map = new HashMap<Long, Long>();
		try {
			List<ActivitySheepOperateLog> list = activitySheepOperateLogDaoImpl.getAllByEntity(operLog);
			if(list != null){
				for (ActivitySheepOperateLog hong : list) {
					if(hong.getGmtCreated() .after( new Date()) ){
						Long num = map.get(hong.getActivityConfigId());
						map.put(hong.getActivityConfigId(), num + hong.getNum());	
						operLog.setOperateContent("已执行");
						activitySheepOperateLogDaoImpl.update(hong);
					}
					
				}
				
				for (Long activityConfigId : map.keySet()) {
					
					ActivityAwardConfig config  = activityAwardConfigDaoImpl.getById(activityConfigId);
					config.setAllNumber(config.getAllNumber() + map.get(activityConfigId));
					config.setLastNumber(config.getLastNumber() + map.get(activityConfigId));
				}
			
			}
		} catch (Exception e) {
			log.error("",e);
			e.printStackTrace();
			System.out.println(e.getMessage()+"----------------excuteConfig error");
		}
		try {
		ActivitySheepHongBao hongBao = new ActivitySheepHongBao();
		hongBao.setDeadTime(new Date());		
		List<ActivitySheepHongBao> list = activitySheepHongBaoDaoImpl.getAllByEntity(hongBao);
		
		for (ActivitySheepHongBao activitySheepHongBao : list) {
			activitySheepHongBao.setStatus(10L);
			activitySheepHongBaoDaoImpl.update(activitySheepHongBao);
			
			ActivitySheepHongBao hongBao1 = new ActivitySheepHongBao();
			Long index = activitySheepHongBao.getIndexHb();
			if(index<3){
				hongBao1.setUserId(activitySheepHongBao.getUserId());	
				hongBao1.setIndexHb(index+1);
				ActivitySheepHongBao hongBao2  = activitySheepHongBaoDaoImpl.getAllByEntity(hongBao1).get(0);
				hongBao2.setStatus(2L);
				activitySheepHongBaoDaoImpl.update(hongBao2);
			}
		
			
		}
		} catch (Exception e) {
			log.error("",e);
			e.printStackTrace();
			System.out.println(e.getMessage()+"----------------excute  dead error");
		}
		
	}
}
