package com.winterframework.firefrog.shortlived.sheepactivity.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.service.IGameFundRiskService;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.game.web.util.GameFundTypesUtils;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.IActivitySheepHongBaoDao;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.IActivitySheepOperateLogDao;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepHongBao;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepOperateLog;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepDetailCount;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepHongBaoCount;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserApplyHongBaoRequest;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IActivitySheepHongBaoService;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.util.JsonMapper;


/** 
* @ClassName ActivitySheepHongBaoServiceImpl 
* @Description 羊年活动红包
* @author  hugh
* @date 2015年1月12日 下午3:33:03 
*  
*/
@Service("activitySheepHongBaoServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class ActivitySheepHongBaoServiceImpl implements IActivitySheepHongBaoService{
	
	@Resource(name = "activitySheepOperateLogDaoImpl")
	private IActivitySheepOperateLogDao activitySheepOperateLogDaoImpl; 
	
	@Resource(name = "activitySheepHongBaoDaoImpl")
	private IActivitySheepHongBaoDao activitySheepHongBaoDaoImpl; 
	
	@Resource(name = "gameFundRiskServiceImpl")
	private IGameFundRiskService fundRiskService;
	
	@Resource(name = "RedisClient")
	private RedisClient redisClient;
	private static JsonMapper jmapper = JsonMapper.nonEmptyMapper();
	public Page<ActivitySheepHongBao> queryPage(
			PageRequest<ActivitySheepHongBao> pr) {
		return activitySheepHongBaoDaoImpl.getAllByPage(pr);
	}
		
	public void update(ActivitySheepHongBao activity) throws Exception{
		activitySheepHongBaoDaoImpl.update(activity);
	}
	
	public void updateEntityByType(ActivitySheepHongBao activity) throws Exception{
			
		if ( activity.getUpdateType() == 1){
			
			//更新------------------------------------
			ActivitySheepHongBao entity = activitySheepHongBaoDaoImpl.getById(activity.getId());
			entity.setUpdateAward(activity.getUpdateAward());
			entity.setUpdateName(activity.getUpdateName());
			entity.setUpdateReason(activity.getUpdateReason());
			entity.setUpdateStatus(1L);
			activitySheepHongBaoDaoImpl.update(entity);
			
			ActivitySheepOperateLog log = new ActivitySheepOperateLog();
			log.setGmtCreated(new Date());
			log.setActivityId(3L);
			log.setActivityName("羊年抽红包");
			log.setNum(activity.getUpdateAward());
			if(activity.getUpdateAward() ==null){
				activity.setUpdateAward(0L);
			}
			if(activity.getUpdateAward() > 0){
				log.setOperateContent("添加"+activity.getUpdateAward()/10000.00+"元");
			}else{
				log.setOperateContent("减少"+(0-activity.getUpdateAward())/10000.00+"元");
			}
			log.setUserName(entity.getUserName());
			log.setOperateName(activity.getUpdateName());
			log.setOperateType(1L);
			activitySheepOperateLogDaoImpl.insert(log );
		}else if (activity.getUpdateType() == 2){
			
			//发布------------------------------------
			ActivitySheepHongBao entity = activitySheepHongBaoDaoImpl.getById(activity.getId());
					
			
			entity.setUpdateReason("");	
			entity.setAllAward(entity.getAllAward() + entity.getUpdateAward());
			if(entity.getStatus() == 5L && entity.getTargetAward() <= entity.getAllAward()){
				entity.setStatus(6L);
				entity.setReachTime(new Date());
			}
			
		
			
			ActivitySheepOperateLog log = new ActivitySheepOperateLog();
			log.setGmtCreated(new Date());
			log.setActivityId(3L);
			log.setActivityName("羊年抽红包");
			log.setNum(activity.getUpdateAward());
		
			if(entity.getUpdateAward() > 0){
				log.setOperateContent("添加"+entity.getUpdateAward()/10000.00+"元");
			}else{
				log.setOperateContent("减少"+(0-entity.getUpdateAward())/10000.00+"元");
			}
			
			entity.setUpdateAward(0L);
			entity.setUpdateStatus(0L);	
			log.setUserName(entity.getUserName());
			
			log.setOperateName(activity.getUpdateName());
			entity.setUpdateName("");
			log.setOperateType(2L);
			activitySheepOperateLogDaoImpl.insert(log );
			activitySheepHongBaoDaoImpl.update(entity);
		}else if (activity.getUpdateType() == 5){
			
			//发布拒绝------------------------------------
			ActivitySheepHongBao entity = activitySheepHongBaoDaoImpl.getById(activity.getId());
				
			
			entity.setUpdateReason("");	
			//entity.setAllAward(entity.getAllAward() + entity.getUpdateAward());
			
			activitySheepHongBaoDaoImpl.update(entity);
			
			ActivitySheepOperateLog log = new ActivitySheepOperateLog();
			log.setGmtCreated(new Date());
			log.setActivityId(3L);
			log.setActivityName("羊年抽红包");
			log.setNum(activity.getUpdateAward());
			if(entity.getUpdateStatus() > 0){
				log.setOperateContent("添加"+entity.getUpdateAward()/10000.00+"元");
			}else{
				log.setOperateContent("减少"+(1-entity.getUpdateAward()/10000.00)+"元");
			}
			entity.setUpdateAward(0L);
			entity.setUpdateStatus(0L);		
			log.setUserName(entity.getUserName());
			
			log.setOperateName(activity.getUpdateName());
			entity.setUpdateName("");
			log.setOperateType(3L);
			activitySheepOperateLogDaoImpl.insert(log );
		}else if (activity.getUpdateType() == 3){
			
			//审核通过------------------------------------
			Long[] ids = activity.getIds();
			List<TORiskDTO> toRiskDTOList = new ArrayList<TORiskDTO>();
			for (Long id : ids) {
				ActivitySheepHongBao entity = activitySheepHongBaoDaoImpl.getById(id);
				if(entity.getStatus()!=7){
					continue;
				}
				
				entity.setVerifyName(activity.getVerifyName());
				entity.setVerifyTime(new Date());
				entity.setVerifyStatus(2l);
				entity.setStatus(8L);				
				
				TORiskDTO activityDTO = new TORiskDTO();
				activityDTO.setAmount(entity.getAward()+"");
				activityDTO.setType(GameFundTypesUtils.ACTIVITY_DETAIL_TYPE);
				activityDTO.setUserid(entity.getUserId()+"");
				toRiskDTOList.add(activityDTO);
				activitySheepHongBaoDaoImpl.update(entity);
			}
			fundRiskService.activityFund(toRiskDTOList);
		}else if (activity.getUpdateType() == 4){
			
			//审核不通过------------------------------------
			Long[] ids = activity.getIds();
			for (Long id : ids) {
				ActivitySheepHongBao entity = activitySheepHongBaoDaoImpl.getById(id);
				if(entity.getStatus()!=7){
					continue;
				}
				entity.setVerifyName(activity.getVerifyName());
				entity.setVerifyTime(new Date());
				entity.setVerifyReason(activity.getVerifyReason());
				entity.setVerifyStatus(3l);
				entity.setStatus(9L);
				activitySheepHongBaoDaoImpl.update(entity);
			}
		}
		
	}
	
	public Long getUncheckNum(){
		return activitySheepHongBaoDaoImpl.getUncheckNum();
	}
	@Override
	public List<ActivitySheepHongBao> getUserHongBaoList(Long userId) {
		return activitySheepHongBaoDaoImpl.getUserHongBaoList(userId);
	}

	@Override
	public void initUserHongbao(List<ActivitySheepHongBao> list) {
		activitySheepHongBaoDaoImpl.insert(list);
		
	}

	@Override
	public void applyUserHongBao(ActivitySheepUserApplyHongBaoRequest param) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("applyDate", param.getApplyDate());
		map.put("award", param.getAward());
		map.put("deadDate", param.getDeadDate());
		map.put("indexHb", param.getIndex());
		map.put("userId", param.getUserId());
		map.put("targetAward",param.getTargetAmount());
		activitySheepHongBaoDaoImpl.applyUserHongbao(map);
		
	}

	@Override
	public Long getUserValideBetAmount(Date applyDate, Long userId) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("applyDate",applyDate);
		map.put("userId", userId);
		return activitySheepHongBaoDaoImpl.getUserValideBetAmount(map);
	}

	@Override
	public void abortUserHongBao(Long userId, Long index) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("indexHb",index);
		map.put("userId", userId);
		activitySheepHongBaoDaoImpl.abortUserHongBao(map);
		if(index!=3){//打开下一个红包
		map.put("index",index+1);
		activitySheepHongBaoDaoImpl.openUserNextHongBao(map);
		}
		
	}

	@Override
	public void drawUserHongBao(Long userId, Long index) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("index",index);
		map.put("userId", userId);
		activitySheepHongBaoDaoImpl.drawUserHongBao(map);
		if(index!=3){//打开下一个红包
			map.put("index",index+1);
			activitySheepHongBaoDaoImpl.openUserNextHongBao(map);
		}
	}
	
	@Override
	public ActivitySheepHongBao getUserHongBaoInfo(Long userId, Long index) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("index",index);
		map.put("userId", userId);
		return activitySheepHongBaoDaoImpl.getUserHongBaoInfo(map);
	}

	public List<ActivitySheepHongBaoCount> getCounts(String date) throws Exception{
		
		List<ActivitySheepHongBaoCount> list = new ArrayList<ActivitySheepHongBaoCount>();
				
		if(date != null){
			return getCounts(date,false);
		}
				
		Date begin =  DateUtils.parse("2015-01-31");
		Date end =  DateUtils.parse("2015-03-25");
		Date now = DateUtils.getStartTimeOfCurrentDate();
		Date notChangeNow = DateUtils.getStartTimeOfCurrentDate();
		if(now.after(end)){
			now = end;
		}
		while(now.after(begin)){
			String dateOne = DateUtils.format(now);
			String key = "game.sheep.hongbao.count" + dateOne;
			ActivitySheepHongBaoCount count = null;
			
			try {
				redisClient.del(key);
			} catch (Exception e) {
				// TODO: handle exception
			}
//			if(redisClient.exists(key)){
//				String value=  redisClient.get(key);
//				count = jmapper.fromJson(value, ActivitySheepHongBaoCount.class);
//			}else{
				List<ActivitySheepHongBaoCount> counts = getCounts(dateOne,false);
				if(counts == null){
					count= new ActivitySheepHongBaoCount();
				}else{
					count = counts.get(0);
				}
				if(count==null)count= new ActivitySheepHongBaoCount();
//				if(now.before( DateUtils.addDays(notChangeNow, -1))){					
//					String value = jmapper.toJson(count);
//					redisClient.set(key, value );
//				}				
//			}	
			count.setCountDate(now);
			list.add(count);			
			now = DateUtils.addDays(now, -1);			
		}
		return list;
	}
	
	public List<ActivitySheepHongBaoCount> getCounts(String dateOne ,boolean isGroupByChannel){
		
		Date beginDateOne = DateUtils.parse(dateOne  + " 00:00:00",DateUtils.DATETIME_FORMAT_PATTERN);
		Date endDateOne = DateUtils.parse(dateOne + " 23:59:59",DateUtils.DATETIME_FORMAT_PATTERN);
		return  activitySheepHongBaoDaoImpl.getCounts(beginDateOne,endDateOne,isGroupByChannel);		
	}

	@Override
	public ActivitySheepHongBao getUserValidHongBaoInfo(Long userId) {
		return activitySheepHongBaoDaoImpl.getUserValidHongBaoInfo(userId);
	}
}
