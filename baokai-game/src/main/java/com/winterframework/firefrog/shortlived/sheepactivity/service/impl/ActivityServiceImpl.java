package com.winterframework.firefrog.shortlived.sheepactivity.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.IActivityAwardConfigDao;
import com.winterframework.firefrog.game.dao.IActivityDao;
import com.winterframework.firefrog.game.dao.IActivityUserAwardLogDao;
import com.winterframework.firefrog.game.dao.vo.ActivityAwardConfig;
import com.winterframework.firefrog.game.dao.vo.ActivityUserAwardLog;
import com.winterframework.firefrog.game.dao.vo.DailyActivityVo;
import com.winterframework.firefrog.game.service.IGameFundRiskService;
import com.winterframework.firefrog.game.web.dto.GetLuckyRequest;
import com.winterframework.firefrog.game.web.dto.LotteryRecordStruc;
import com.winterframework.firefrog.game.web.dto.LotteryResultStruc;
import com.winterframework.firefrog.game.web.dto.QueryLotteryRecordRequest;
import com.winterframework.firefrog.game.web.dto.QueryLotteryResultRequest;
import com.winterframework.firefrog.game.web.dto.QueryRedEnvelopeRequest;
import com.winterframework.firefrog.game.web.dto.RedEnvelopeStruc;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IActivityService;
import com.winterframework.firefrog.shortlived.sheepactivity.service.util.Award;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName ActivityServiceImpl 
* @Description 活动 
* @author  hugh
* @date 2014年12月2日 下午3:32:55 
*  
*/
@Service("activityServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class ActivityServiceImpl implements IActivityService{
	
	@Resource(name = "activityDaoImpl")
	private IActivityDao activityDaoImpl; 
	
	@Resource(name = "activityAwardConfigDaoImpl")
	private IActivityAwardConfigDao activityAwardConfigImpl; 
	
	@Resource(name = "activityUserAwardLogDaoImpl")
	private IActivityUserAwardLogDao activityUserAwardLogImpl; 
	
	@Resource(name = "gameFundRiskServiceImpl")
	private IGameFundRiskService fundRiskService;
	/** 
	* @Title: getAward 
	* @Description: 用户"投注抽大奖"开奖
	* @param userId
	* @param activityId
	* @return
	 * @throws Exception 
	*/
	
	@Override
	public Award getBetAward(GetLuckyRequest param) throws Exception{
		return getAward(param.getUserId(),1L,param.getChannel());
	}
	
	/** 
	* @Title: getAward 
	* @Description: 用户某活动开奖
	* @param userId
	* @param activityId
	* @return
	 * @throws Exception 
	*/
	public Award getAward(Long userId,Long activityId,Integer channel) throws Exception{
		ActivityAwardConfig activity = new ActivityAwardConfig();
		activity.setActivityId(activityId);
		List<ActivityAwardConfig> configs = activityAwardConfigImpl.getAllByEntity(activity);
		
		//开奖机
		Award award = getAward(configs);		
		if(award == null){
			return null;
		}
		
		for (ActivityAwardConfig config : configs) {
			if(config.getId().longValue() == award.getId()){
				config.setWinNumber(config.getWinNumber()==null?0:config.getWinNumber() + 1 );
				config.setLastNumber(config.getLastNumber()==null?0:config.getLastNumber() - 1 );
			}
			activityAwardConfigImpl.update(config);
		}
		
		//存储开奖结果
		ActivityUserAwardLog activityUserAwardLog = new ActivityUserAwardLog();
		activityUserAwardLog.setActivityAwardConfigId(award.getId());
		activityUserAwardLog.setActivityId(activityId);
		activityUserAwardLog.setUserId(userId);
		activityUserAwardLog.setAward(award.getAward());
		activityUserAwardLog.setGmtCreated(new Date());
		activityUserAwardLog.setRewardChannel(channel);
		activityUserAwardLogImpl.insert(activityUserAwardLog);
		
		award.setDate(activityUserAwardLog.getGmtCreated());
		
//		List<TORiskDTO> toRiskDTOList = new ArrayList<TORiskDTO>();
//		TORiskDTO activityDTO = new TORiskDTO();
//		activityDTO.setAmount(award.getAward()+"");
//		activityDTO.setType(GameFundTypesUtils.GAME_RET_FREEZER_DETEAIL_TYPE);
//		activityDTO.setUserid(userId+"");
		//activityDTO.setIssueCode(gameOrderVo.getIssueCode());
		//activityDTO.setLotteryid(gameOrderVo.getLotteryid());
		//activityDTO.setOrderCodeList(gameOrderVo.getOrderCode());
//		toRiskDTOList.add(activityDTO);
//		fundRiskService.betAmountFreezer(toRiskDTOList);
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
	
	public static void main(String[] args) {
		int a0 = 0;
		int a1 = 0;
		int a2 = 0;
		int a3 = 0;
		int a4 = 0;
		int a5 = 0;

		for (int i = 0; i < 10000; i++) {
			int num = new Random().nextInt(5);
			if(num == 0){
				a0++;
			}else if(num == 1){
				a1++;
			}else if(num == 2){
				a2++;
			}else if(num == 3){
				a3++;
			}else if(num == 4){
				a4++;
			}else if(num == 5){
				a5++;
			}	
		}
		
		System.out.println("a0=" + a0);
		System.out.println("a1=" + a1);
		System.out.println("a2=" + a2);
		System.out.println("a3=" + a3);
		System.out.println("a4=" + a4);
		System.out.println("a5=" + a5);
	}

	@Override
	public List<DailyActivityVo> getDailyActivityStrucs(String startTime,
			String endTime, Long userId) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("userId", userId);
		return activityDaoImpl.getDailyActivityStrucs(map);
	}

	@Override
	public Long getUserLuckyTime(Long userId, Date activityStartTime,Date activityEndTime) {
		//首先统计出活动期间有多次天的有效投注天数，然后在看抽奖记录表中抽了多少次，相减得到剩余可投注次数
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("startTime", DateUtils.format(DateUtils.getStartTimeOfDate(activityStartTime)));
		map.put("endTime", DateUtils.format(DateUtils.getEndTimeOfDate(activityEndTime)));
		map.put("userId", userId);
		Long betDays= (Long) activityDaoImpl.getBetDays(map);
		Long rewardNumber= (Long) activityDaoImpl.getRewardNumber(map);
		//TODO 发布的时候改成7天
		return betDays/2-rewardNumber.longValue();
	}

	@Override
	public Page<RedEnvelopeStruc> queryRedEnvelope(
			PageRequest<QueryRedEnvelopeRequest> pr) {
		return activityDaoImpl.queryRedEnvelop(pr);
	}

	@Override
	public Page<LotteryRecordStruc> queryLotteryRecord(
			PageRequest<QueryLotteryRecordRequest> pr) {
		return activityDaoImpl.queryLotteryRecord(pr);
	}

	@Override
	public Page<LotteryResultStruc> queryLotteryResult(
			PageRequest<QueryLotteryResultRequest> pr) {
		return activityDaoImpl.queryLotteryResult(pr);
	}

}
