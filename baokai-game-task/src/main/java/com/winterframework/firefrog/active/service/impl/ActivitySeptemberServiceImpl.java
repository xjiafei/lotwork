package com.winterframework.firefrog.active.service.impl;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.winterframework.firefrog.active.dao.IActivityConfigDao;
import com.winterframework.firefrog.active.dao.IActivityDao;
import com.winterframework.firefrog.active.dao.IActivityLogDao;
import com.winterframework.firefrog.active.dao.IActivityResultDao;
import com.winterframework.firefrog.active.dao.IActivitySignUpDao;
import com.winterframework.firefrog.active.dao.vo.Activity;
import com.winterframework.firefrog.active.dao.vo.ActivityConfig;
import com.winterframework.firefrog.active.dao.vo.ActivityLog;
import com.winterframework.firefrog.active.dao.vo.ActivityResult;
import com.winterframework.firefrog.active.service.IActivitySeptemberService;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.fund.exception.FundChangedException;
import com.winterframework.firefrog.fund.service.IFundAtomicOperationService;
import com.winterframework.firefrog.fund.web.controller.vo.FundChangeDetail;
import com.winterframework.firefrog.game.fund.ff.bean.FundGameVo;
import com.winterframework.firefrog.game.web.dto.NoticeSenderRequest;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.dao.IVipActivityDao;
import com.winterframework.firefrog.user.dao.vo.VipActivityVo;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 
 * @author Ami.Tsai
 *
 */
@Service(value="activitySeptemberServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class ActivitySeptemberServiceImpl implements IActivitySeptemberService {

	private static final Logger log = LoggerFactory.getLogger(ActivitySeptemberServiceImpl.class);
	//活動主檔
	@Resource(name = "activityDaoImpl")
	IActivityDao    activityDao;
	//活動LOG紀錄
	@Resource(name = "activityLogDaoImpl")
	private IActivityLogDao activityLogDaoImpl;
	//活動(中獎)結果
	@Resource(name = "activityResultDaoImpl")
	IActivityResultDao    activityResultDao;
	//活動報名表
	@Resource(name = "activitySignUpDaoImpl")
	IActivitySignUpDao    activitySignUpDao;
	//活動規則
	@Resource(name = "activityConfigDaoImpl")
	IActivityConfigDao    activityConfigDao;
	
	@Resource(name = "fundChangeServiceImpl")
	private IFundAtomicOperationService fundChangeService;
	
	@Resource(name="vipactivtyDao")
	private IVipActivityDao vipactivtyDao;

	@Resource(name="userCustomerDaoImpl")
	private IUserCustomerDao userCustomerDaoImpl;
	
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpJsonClientImpl;
	@PropertyConfig(value = "url.business.connect")
	private String serverPath;
	
	@PropertyConfig(value = "url.baseFundUrl")
	private String baseFundUrl;
	
	@PropertyConfig(value = "url.notice.activity.taskid")
	private String taskId;
	
	
	private final String step2Code ="160902";

	private static final String ACTIVITY_REASON_KEY = "PM-PGXX-3";

	private final Long month =9l; 
	
	private final String step3Code ="160903";
	
	/**喪失資格*/
	private final static String NO_QUALIFICATIONS = "0";
	/**擁有資格*/
	private final static String HAVE_QUALIFICATIONS = "1";
	/**尚未達標的LEVEL*/
	private final static String NO_COMPLIANCE_LEVEL = "0";
	
	/*
	private final static Map<Integer, Calendar> dateInteval_0903 = new HashMap<Integer, Calendar>();
	
	{
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void init() throws Exception{
		initDateInteval();
	}
	
	private Map<Integer, Calendar> initDateInteval() throws ParseException{
		if(dateInteval_0903.isEmpty()){
			Calendar start = Calendar.getInstance();
			if(isDebug){
				String debugTime = "20160831";
				Date date = sf.parse(debugTime);
				start.setTime(date);  // 2016. 8 . 19
			} else {
				start.set(2016, 8, 19);  // 2016. 9 . 19
			}
			Calendar end = Calendar.getInstance();
			end.setTime(start.getTime());
			end.add(Calendar.DAY_OF_MONTH, 7);
			
			dateInteval_0903.put(KEY_0903_START_DATE, start);
			dateInteval_0903.put(KEY_0903_END_DATE, end);
			Calendar draw = Calendar.getInstance();
			draw.setTime(start.getTime());
			draw.add(Calendar.DAY_OF_MONTH, 8);
			dateInteval_0903.put(KEY_0903_DRAW_DATE, draw);
		}
		return dateInteval_0903;
	}
	*/
	
	/**
	 * 第二波活動派獎
	 * @throws Exception 
	 */
	@Override
	public void drawStep2() throws Exception {

		Activity activity = new Activity();
		activity.setActivityCode(step2Code);
		activity = activityDao.getActivityByCode(activity);

		//活動時間過了不執行,最後一天為
		if(!DateUtils.between(activity.getStartTime(), DateUtils.addDays(activity.getEndTime(), 1))){
			return ;
		}
		
		VipActivityVo vo = new VipActivityVo();
		vo.setMonth(month);
		vo.setStartTime(activity.getStartTime());		
		vo.setEndTime(activity.getEndTime());			
		List<VipActivityVo> actives= vipactivtyDao.queryByActivityVo(vo);
		
		for(VipActivityVo activeUser:actives){
			
			User user= userCustomerDaoImpl.getUserByUserName(activeUser.getAccount());
			
			ActivityResult actRsVO = new ActivityResult();
			actRsVO.setActivityId(activity.getId());
			actRsVO.setUserId(user.getId());
			Long drawNumber= activityResultDao.queryActivityResultAwardToday(actRsVO);
			
			//今日領取過獎勵才派獎
			if(drawNumber!=null && drawNumber>0){
				Map<String, Object> amountMap = new HashMap<String, Object>();		
				Date start =  DateUtils.parse(DateUtils.format(DateUtils.addDays(new Date(), -1), "yyyy-MM-dd"), "yyyy-MM-dd");
				Date end =  DateUtils.parse(DateUtils.format(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd");				
				
				amountMap.put("userId", user.getId());
				amountMap.put("startTime", start.getTime());
				amountMap.put("endTime",   end.getTime());
				Long betAmount = httpJsonClientImpl.invokeHttpGet(serverPath+"/game/queryUserPeriodBets",
						amountMap, new TypeReference<Long>(){});
				Collection<ActivityConfig> activityConfigs = activityConfigDao.getActivityConfigByActivityIdAndBetAmount(activity.getId(), betAmount);
				Long totalAmount= 0l;
				for(ActivityConfig config:activityConfigs){
					
					if(config.getMaxPrize()==null || config.getMaxPrize()==0l){
						//琉璃燈跳過去
						continue;
					}
					
					try{
						Map<String,Object> ruleMap = DataConverterUtil.convertJson2Map(config.getRule());
						Long awardAmount = 0l;
						if(user.getVipLvl()>0){
							awardAmount = new Long(ruleMap.get("vip").toString());
						}else{
							awardAmount = new Long(ruleMap.get("normal").toString());
						}

						List<ActivityResult> checkResult = activityResultDao.getBeforeOneDayResultByActivityIdAndTypeAndUserIdAndActivityId(user.getId(),activity.getId(),config.getType());
						
						if(checkResult==null || checkResult.size()==0){
							ActivityResult result = new ActivityResult();
							result.setActivityId(activity.getId());
							result.setUserId(user.getId());
							result.setType(config.getType());
							result.setStatus(1L);
							result.setCreateUser(user.getId());
							result.setCreateTime(start);
							result.setModifyUser(user.getId());
							result.setModifyTime(start);
							result.setResult(" ");					
							activityResultDao.insert(result);
							saveActivityLog(activity, user, awardAmount, new Date(), result.getId());
							saveFundChaneLog(user.getId(),awardAmount,"九月活动第二波");							
							totalAmount+=awardAmount;
						}
					}catch(Exception e){
						log.error("activityDraw error!",e);
					}
				}
				
				//發站內信
				if(totalAmount>0){
					Map<String, String> paramMap = new HashMap<String, String>();
					Calendar cal = Calendar.getInstance();
					cal.setTime(DateUtils.addDays(new Date(), -1));
					Integer year = cal.get(Calendar.YEAR);
					Integer month = cal.get(Calendar.MONTH);
					Integer day = cal.get(Calendar.DAY_OF_MONTH);
					paramMap.put("year", year.toString());
					paramMap.put("month", new Integer(month+1).toString());
					paramMap.put("day", day.toString());					
					paramMap.put("activityName", "浓情中秋");					
					paramMap.put("awardAmount", new Long((totalAmount/10000)).toString());					
					//寄發站內信
					sendActivityNotice(taskId, user.getId(), paramMap);
				}
			}
		}
	}
	
	private void sendActivityNotice(String taskId,Long userId,Map<String, String> paramMap) throws Exception{
		NoticeSenderRequest request = new NoticeSenderRequest();
		request.setTaskId(Long.parseLong(taskId));
		request.setUserId(userId);
		request.setParamMap(paramMap);
		httpJsonClientImpl.invokeHttp(baseFundUrl + "/noticeAdmin/SendNotice", request, 31L, "admin", Object.class);
	}
	
	private void saveActivityLog(Activity activity, User user, Long awardAmount, Date now, Long resultId) {
		ActivityLog activityLog = new ActivityLog();
		activityLog.setActivityId(activity.getId());
		activityLog.setUserId(user.getId());
		activityLog.setPrize(awardAmount);
		activityLog.setCreateTime(now);
		activityLog.setAwardTime(now);
		activityLog.setStatus(1l);
		activityLog.setResultId(resultId);
		activityLogDaoImpl.insert(activityLog);
	}
	
	private void saveFundChaneLog(Long userId, Long amount,String memo) throws FundChangedException, Exception{
		FundGameVo vo = new FundGameVo();
		vo.setUserId(userId);
		vo.setAmount(amount);
		vo.setIsAclUser(0L);
		vo.setOperator(0L);
		vo.setReason(ACTIVITY_REASON_KEY);
		vo.setNote(memo);
		List<FundGameVo> vos = Lists.newArrayList();
		vos.add(vo);
		List<FundChangeDetail> maps = new ArrayList<FundChangeDetail>();
		fundChangeService.action(vos, maps);
	}


	@Override
	public void drawStep3() throws Exception {
		Activity activity = new Activity();
		activity.setActivityCode(step3Code);
		activity = activityDao.getActivityByCode(activity);

		
		VipActivityVo vo = new VipActivityVo();
		vo.setMonth(month);
		vo.setStartTime(activity.getStartTime());		
		vo.setEndTime(activity.getEndTime());			
		List<VipActivityVo> actives= vipactivtyDao.queryByActivityVo(vo);
		
		ActivityConfig activityConfig = new ActivityConfig();
		activityConfig.setActivityId(activity.getId());
		List<ActivityConfig> configs = activityConfigDao.getActCfgByCondition(activityConfig);
		
		final Map<String, ActivityConfig> configMap = new HashMap<String, ActivityConfig>();
		for(ActivityConfig config : configs){
			configMap.put(config.getType(), config);
		}
		
		for(VipActivityVo activeUser : actives){
			User user= userCustomerDaoImpl.getUserByUserName(activeUser.getAccount());
			//取得這星期每日的Result
			List<ActivityResult> results = 
					activityResultDao.queryAllResultByUserIdAndActivityIdOrderByResult(user.getId(), activity.getId());
			
			//判斷有沒有狀態0
			boolean isStatusZero = false;
			for(ActivityResult result : results){
				if(NO_QUALIFICATIONS.equals(result.getStatus())){
					isStatusZero = true;
					break;
				}
			}
			
			if(isStatusZero){
				log.debug("九月活動三, Userid : " + user.getId() + ", 沒有符合派獎資格");
				continue;
			} else {
				//取出最小的type
				String minType = "9";
				for(ActivityResult result : results){
					if(minType.compareTo(result.getType()) >= 0){
						minType = result.getType();
					}
				}
				
				//get jsonObject
				String rule = configMap.get(minType).getRule();
				Map<String, Object> ruleMap = DataConverterUtil.convertJson2Map(rule);
				
				Long awardAmount = 0l;
				if(user.getVipLvl()>0){
					awardAmount = new Long(ruleMap.get("vip").toString());
				}else{
					awardAmount = new Long(ruleMap.get("normal").toString());
				}
				Date now = new Date();
				saveActivityLog(activity, user, awardAmount, now, results.get(results.size()-1).getId());
				saveFundChaneLog(user.getId(),awardAmount,"九月活动第三波");
			}
		}
	}

	@Override
	public void intevalSumUp(Date calcDate, boolean isDaily) {
		
		try {
			Activity activity = new Activity();
			activity.setActivityCode(step3Code);
			activity = activityDao.getActivityByCode(activity);
			
			Date startDate = activity.getStartTime();
			Date endDate = activity.getEndTime();
			
			VipActivityVo vo = new VipActivityVo();
			vo.setMonth(9l);
			vo.setStartTime(startDate);		
			vo.setEndTime(endDate);			
			List<VipActivityVo> actives = vipactivtyDao.queryByActivityVo(vo);
			
			long activityId = activity.getId();
			List<ActivityConfig>  configs = queryActivityConfig(activityId);
			Collections.sort(configs, new Comparator<ActivityConfig>() {
				@Override
				public int compare(ActivityConfig o1, ActivityConfig o2) {
					return o1.getMinPrize().compareTo(o2.getMinPrize());
				}
			});
			
			//取得經過天數
			long theDay = 0;
			if(startDate.before(calcDate)){
				theDay = DateUtils.calcDateBetween(startDate, calcDate);
			}
			
			if(theDay <= 0 || theDay >= 7){
				//還沒超過開始時間 不用往下做
				return ; 
			}
			
			//已經超過開始時間
			for(VipActivityVo active : actives){
				
				User user= userCustomerDaoImpl.getUserByUserName(active.getAccount());
				
				//
				ActivityResult todayResult = activityResultDao.queryActivityResultByUserIdAndActivityIdAndResult(user.getId(), activityId, String.valueOf(theDay));
				
				String maxLevel = NO_COMPLIANCE_LEVEL;
				if(theDay > 1){
					//非第一天, 代表有之前的資料, 先判斷之前是否已經喪失資格, 若喪失資格 就不往下做
					long countZero = activityResultDao.queryStatusZeroByUserIdAndActivityId(user.getId(), activityId);
					if(countZero > 0){
						//之前已經喪失資格
						if(isDaily){
							activityResultDao.updateTypeAndStatusById(todayResult.getId(), NO_COMPLIANCE_LEVEL, NO_QUALIFICATIONS);
						} else {
							activityResultDao.updateTypeById(todayResult.getId(), NO_COMPLIANCE_LEVEL);
						}
						continue;
					} else {
						maxLevel = activityResultDao.queryMinTypeByUserIdAndActivityIdAndLessThanResult(user.getId(), activityId, String.valueOf(theDay));
					}
				} else {
					//第一天, donothing
				}
				
				Map<String, Object> amountMap = new HashMap<String, Object>();		
				Date start =  DateUtils.parse(DateUtils.format(calcDate, "yyyy-MM-dd"), "yyyy-MM-dd");
				Date end =  DateUtils.parse(DateUtils.format(DateUtils.addDays(calcDate, 1), "yyyy-MM-dd"), "yyyy-MM-dd");
				
				amountMap.put("userId", user.getId());	
				amountMap.put("startTime", start.getTime());
				amountMap.put("endTime",   end.getTime());
				Long betAmount = httpJsonClientImpl.invokeHttpGet(serverPath + "/game/queryUserPeriodBets", //"/game/queryUserPeriodBets",
						amountMap, new TypeReference<Long>(){});
//				Long betAmount = response.getBody().getResult();
				
				String type = NO_COMPLIANCE_LEVEL;
				for(ActivityConfig config : configs){
					if( config.getMinPrize() >= betAmount ){
						if(config.getMinPrize().compareTo(betAmount) == 0){
							type = config.getType();
						}
						continue;
					} else {
						// 金額比最小限制大
						if(config.getMaxPrize() == -1){
							//最後一筆設定 不限定上限
							type = config.getType();
							break;
						} else {
							//非最後一筆
							if(config.getMaxPrize() > betAmount){
								type = config.getType();
								break;
							} else {
								//非在此區間內
								continue;
							}
						}
					}
				}
				
				if(theDay > 1){
					if( maxLevel.compareTo(type) < 0 ) {
						// 如果之前的最大值比較小, 則以之前的為主
						type = maxLevel;
					}
				} else {
					//doNothing
				}
				
				
				if(isDaily){
					//計算昨天
					if(NO_COMPLIANCE_LEVEL.equals(type)){
						activityResultDao.updateTypeAndStatusById(todayResult.getId(), NO_COMPLIANCE_LEVEL, NO_QUALIFICATIONS);
					} else {
						activityResultDao.updateTypeAndStatusById(todayResult.getId(), type, HAVE_QUALIFICATIONS);
					}
				} else {
					//即時計算(當日)
					activityResultDao.updateTypeById(todayResult.getId(), type);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public List<ActivityResult> getListAwardRecord(Long userId, Long activityid){
		ActivityResult actRsVO = new ActivityResult();
		actRsVO.setUserId(userId);
		actRsVO.setActivityId(activityid);
		return activityResultDao.getListAwardRecord(actRsVO);
	}

	@Override
	public Long queryActivityId(String activityCode) throws Exception {
		Activity actVO = new Activity();
		Activity actResultVO = new Activity();
		actVO.setActivityCode(activityCode);
		log.debug("getAcvitityByCode : ActivityCode=" + actVO.getActivityCode());
		actResultVO = activityDao.getActivityByCode(actVO);
		return actResultVO.getId();
	}

	@Override
	public List<ActivityConfig> queryActivityConfig(Long  activityId) {
		ActivityConfig activityConfigVO = new ActivityConfig();
		activityConfigVO.setActivityId(activityId);
		List<ActivityConfig> activityConfig=activityConfigDao.getActCfgByCondition(activityConfigVO);
		return activityConfig;
	}

	@Override
	public void daySumUp(Calendar yesterday) throws Exception {
		yesterday.add(Calendar.DAY_OF_MONTH, -1);
		intevalSumUp(yesterday.getTime(), Boolean.TRUE);
	}
}
