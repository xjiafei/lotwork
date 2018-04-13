package com.winterframework.firefrog.shortlived.sheepactivity.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.IActivityUserAwardLogDao;
import com.winterframework.firefrog.game.dao.vo.ActivityUserAwardLog;
import com.winterframework.firefrog.game.dao.vo.DailyActivityVo;
import com.winterframework.firefrog.game.service.IGameFundRiskService;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.game.web.util.GameFundTypesUtils;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IActivityService;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IActivityUserAwardLogService;

/** 
* @ClassName ActivityUserAwardLogServiceImpl 
* @Description 用户活动中奖记录 
* @author  hugh
* @date 2014年12月2日 下午3:32:26 
*  
*/
@Service("activityUserAwardLogServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class ActivityUserAwardLogServiceImpl implements  IActivityUserAwardLogService{

	private Logger log = LoggerFactory.getLogger(ActivityUserAwardLogServiceImpl.class);
	@Resource(name = "activityUserAwardLogDaoImpl")
	private IActivityUserAwardLogDao activityUserAwardLogDaoImpl; 
	
	@Resource(name="activityServiceImpl")
	private IActivityService activityServiceImpl;
	
	@Resource(name = "gameFundRiskServiceImpl")
	private IGameFundRiskService fundRiskService;
	
	public List<ActivityUserAwardLog> getToday() throws Exception{		
		return activityUserAwardLogDaoImpl.getToday();
	}
	
	public List<ActivityUserAwardLog> getUserAward(Long userId) throws Exception{
		return activityUserAwardLogDaoImpl.getUserAwardLog(userId);
	}

	@Override
	public void getDailyBetPrize(long userId, String betDate, Long money,Integer channel)
			throws Exception {
		
		String startTime=DateUtils.format(DateUtils.getStartTimeOfDate(DateUtils.parse(betDate)),DateUtils.DATETIME_FORMAT_PATTERN);
		String endTime=DateUtils.format(DateUtils.getEndTimeOfDate(DateUtils.parse(betDate)),DateUtils.DATETIME_FORMAT_PATTERN);
		List<DailyActivityVo> vos=activityServiceImpl.getDailyActivityStrucs(startTime, endTime, userId);
		if(vos==null||vos.isEmpty()){
			log.error("当天无投注奖励领取：userid="+userId+",date="+betDate);
			throw new Exception();
		}
		DailyActivityVo vo=vos.get(0);
		if(vo.getRewardLogid()!=null){
			log.error("当天投注奖已领取：userid="+userId+",date="+betDate);
			throw new Exception();
		}else{
			ActivityUserAwardLog log=new ActivityUserAwardLog();
			log.setUserId(userId);
			log.setActivityId(2L);
			log.setAward(money);
			log.setGmtCreated(new Date());
			log.setDailyBetAwardTime(betDate);
			log.setRewardChannel(channel);
			activityUserAwardLogDaoImpl.insert(log);
		}
		
		List<TORiskDTO> toRiskDTOList = new ArrayList<TORiskDTO>();
		TORiskDTO activityDTO = new TORiskDTO();
		activityDTO.setAmount(money*10000+"");
		activityDTO.setType(GameFundTypesUtils.ACTIVITY_DETAIL_TYPE);
		activityDTO.setUserid(userId+"");
//		activityDTO.setIssueCode(gameOrderVo.getIssueCode());
//		activityDTO.setLotteryid(gameOrderVo.getLotteryid());
//		activityDTO.setOrderCodeList(gameOrderVo.getOrderCode());
		toRiskDTOList.add(activityDTO);
		fundRiskService.betAmountFreezer(toRiskDTOList);
		
		
	}
}
