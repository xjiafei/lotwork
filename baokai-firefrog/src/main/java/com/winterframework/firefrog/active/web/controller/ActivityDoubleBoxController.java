package com.winterframework.firefrog.active.web.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Maps;
import com.winterframework.firefrog.active.dao.IActivityConfigDao;
import com.winterframework.firefrog.active.dao.IActivityDoubleboxLogDao;
import com.winterframework.firefrog.active.dao.vo.ActivityConfig;
import com.winterframework.firefrog.active.dao.vo.ActivityDoubleboxLog;
import com.winterframework.firefrog.active.entity.ActivityRatioEntity;
import com.winterframework.firefrog.active.web.dto.ActivityDoubleboxResponse;
import com.winterframework.firefrog.active.web.dto.ActivityRequest;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.RandomVal;
import com.winterframework.firefrog.fund.service.IFundAtomicOperationService;
import com.winterframework.firefrog.fund.web.controller.vo.FundChangeDetail;
import com.winterframework.firefrog.fund.web.controller.vo.FundGameVo;
import com.winterframework.firefrog.game.service.IGameBettypeStatusService;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.service.IUserProfileService;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller
@RequestMapping(value = "/activity/doublebox")
public class ActivityDoubleBoxController {
	
	private static final Logger log = LoggerFactory.getLogger(ActivityDoubleBoxController.class);	
	
	
	@Resource(name = "HttpJsonClientImpl")
	protected IHttpJsonClient httpJsonClient;
	
	@Resource(name = "RedisClient")
	private RedisClient redisSerive;
	
	@PropertyConfig("game_get_queryUserDailyBetsForAct")
	private String queryUserDailyBetsForAct;
	
	@PropertyConfig("doublebox.bet.ratio.probability")
	private String douboxBetRatioSetting;
	
	@Resource(name = "gameBettypeStatusServiceImpl")
	private IGameBettypeStatusService gameBettypeStatusService;
	
	@Resource(name ="userProfileServiceImpl")
	private IUserProfileService userProfileService;
	
	@Resource(name = "fundChangeServiceImpl")
	private IFundAtomicOperationService fundChangeService;

	@Resource(name = "activityDoubleboxLogDaoImpl")
	private IActivityDoubleboxLogDao activityDoubleboxLogDao;
	
	@Resource(name = "activityConfigDaoImpl")
	private IActivityConfigDao activityConfigDaoImpl;
	
	//雙倍寶箱活動ID
	private static final Long DOUBLE_BOX_ACTIVITY_ID = 6L;
	
	private static final Long WIN_LIST_COUNT = 20L;
	
	private static final Long RAND_MAX = 100000L;
	
	private static final Long RAND_MIN = 0L;
	
	private static final Long HIGHEST_MULTIPLE = 10L;
	
	private static final Long PERCENT =100L;
	
	private static final String ACTIVITY_REASON_KEY = "PM-PGXX-3";
	
	private static final String ACT_NOTE= "双倍宝箱活动礼金";
	
	private static final String DOUBLEBOX_REDISKEY = "DOUBLEBOX_REDIS_KEY";
	
	private static final Map<String, String>  FAKE_WIN_LIST = new TreeMap<String,String> ();
    static {
    	FAKE_WIN_LIST.put("2016不一样","19482");
    	FAKE_WIN_LIST.put("caochang86","4597");
    	FAKE_WIN_LIST.put("zqd83490184","97439");
    	FAKE_WIN_LIST.put("菲菲1987","598");
    	FAKE_WIN_LIST.put("富贵老吴","36761");
    	FAKE_WIN_LIST.put("Jiushiyaofacai","169237");
    	FAKE_WIN_LIST.put("我爱zhu77","9184");
    	FAKE_WIN_LIST.put("qq58764870","3058");
    	FAKE_WIN_LIST.put("meishibiebb","6968");
    	FAKE_WIN_LIST.put("Lilei19831104","10256");
    	FAKE_WIN_LIST.put("川q5572","791");
    	FAKE_WIN_LIST.put("Xinyuncaipiao","509");
    	FAKE_WIN_LIST.put("今年必须发","1910");
    	FAKE_WIN_LIST.put("糊涂神仙88","3725");
    	FAKE_WIN_LIST.put("zhenxiaoyu89","23677");
    	FAKE_WIN_LIST.put("叫我大爷","84706");
    	FAKE_WIN_LIST.put("zjk88794","22884");
    	FAKE_WIN_LIST.put("Sky6688","13159");
    }
    
    @RequestMapping(value = "initData")
	public @ResponseBody
	Response<ActivityDoubleboxResponse> initData(@RequestBody Request<ActivityRequest> request) throws Exception {
		Response<ActivityDoubleboxResponse> response = new Response<ActivityDoubleboxResponse>(request);
		ActivityDoubleboxResponse result = new ActivityDoubleboxResponse();
		List<Map<String,String>> winList = null;
		try{
			Long userId = request.getBody().getParam().getUserId();
			log.debug("initData userId : " + userId);
			User user = userProfileService.queryUserById(userId);
			ActivityConfig doubleboxCfg = activityConfigDaoImpl.getActCfgById(DOUBLE_BOX_ACTIVITY_ID);
			Calendar cal = Calendar.getInstance();
			Date now = cal.getTime();
			long nowMillis = cal.getTimeInMillis();
			log.debug("now : " + now);
			cal.set(Calendar.HOUR_OF_DAY, 24);  
	        cal.set(Calendar.SECOND, 0);  
	        cal.set(Calendar.MINUTE, 0);  
	        cal.set(Calendar.MILLISECOND, 0); 
	        long endTime = cal.getTimeInMillis() - nowMillis;
	        result.setEndTime(endTime);
			//給當天結束領獎時間
			if(null == user){
				throw new Exception();
			}
			
			//檢查是否在活動期間
			if(now.before(doubleboxCfg.getBeginTime()) || now.after(doubleboxCfg.getEndTime())){
				log.warn("initData error:no user or is not between activity time~~");
				throw new Exception();
			}
			
			//相關檢查(當天是否領過，一天只能一次)
			List<ActivityDoubleboxLog> actLogs = activityDoubleboxLogDao.queryTodayLogByUserId(userId, DOUBLE_BOX_ACTIVITY_ID);
			//查詢最近20筆中獎紀錄
			winList = getWinList(DOUBLE_BOX_ACTIVITY_ID,WIN_LIST_COUNT,doubleboxCfg.getBeginTime());
			log.debug("winList size : " + winList.size());
			//如果當天有紀錄 則回失敗(已領獎)
			if(!actLogs.isEmpty() || actLogs.size() > 0){
				log.debug("已領獎~~~!!");
				result.setIsSuccess(2L);
				result.setBetAmount(actLogs.get(0).getSales());
				result.setHisBonus(actLogs.get(0).getMultiplePrize());
				result.setData(winList);
				response.getBody().setResult(result);
				return response;
			} 
			
			Map<String, Object> map = Maps.newHashMap();
			map.put("userId", userId);
			//抓用戶當天到目前為止的總投注量
			Long betAmount = (Long) httpJsonClient.invokeHttp(queryUserDailyBetsForAct,
					map, new TypeReference<Long>(){});
			log.debug("betsTotal : " + betAmount);
			Float ratio = computeRatio(betAmount,user.getVipLvl());
			if(ratio.floatValue() <= 0f){
				log.debug("betAmount.intValue() <= 0");
				result.setIsSuccess(0L);
				result.setData(winList);
				result.setBetAmount(betAmount);
				result.setBetRatio(Float.toString(ratio) + "%");
				response.getBody().setResult(result);
				return response;
			}
			
			log.debug("ratio : " + ratio);
			result.setBetAmount(betAmount);
			result.setBetRatio(Float.toString(ratio) + "%");
			log.debug("result : " + result.getBetRatio());
			result.setIsSuccess(1L);
			result.setData(winList);
			response.getBody().setResult(result);
		}catch(Exception e){
			log.error("doublebox activity initData error~~!");
			result.setIsSuccess(-1L);
			result.setData(winList);
			response.getBody().setResult(result);
		}
		
		return response;
	}
	
	@RequestMapping(value = "award")
	public @ResponseBody
	Response<ActivityDoubleboxResponse> award(@RequestBody Request<ActivityRequest> request) throws Exception {
		Response<ActivityDoubleboxResponse> response = new Response<ActivityDoubleboxResponse>(request);
		ActivityDoubleboxResponse result = new ActivityDoubleboxResponse();
		try{
			//檢查
			Long userId = request.getBody().getParam().getUserId();
			User user = userProfileService.queryUserById(userId);
			log.debug(" userId : " + user.getId());
			if(null == user){
				throw new Exception("no user error!");
			}
			List<ActivityDoubleboxLog> actLogs = activityDoubleboxLogDao.queryTodayLogByUserId(userId, DOUBLE_BOX_ACTIVITY_ID);
			//如果當天有紀錄 則回失敗(已領獎)
			if(!actLogs.isEmpty() || actLogs.size() > 0){
				throw new Exception("It has award!");
			} 
			ActivityConfig doubleboxCfg = activityConfigDaoImpl.getActCfgById(DOUBLE_BOX_ACTIVITY_ID);
			Calendar cal = Calendar.getInstance();
			Date now = cal.getTime();
			//檢查是否在活動期間
			if(now.before(doubleboxCfg.getBeginTime()) || now.after(doubleboxCfg.getEndTime())){
				throw new Exception("not During the activity!");
			}
			//抓用戶當天到目前為止的總投注量
			Map<String, Object> map = Maps.newHashMap();
			map.put("userId", userId);
			Long betAmount = (Long) httpJsonClient.invokeHttp(queryUserDailyBetsForAct,
					map, new TypeReference<Long>(){});
			log.debug("betAmount : " + betAmount);
			
			//取倍數與獎金
			ActivityRatioEntity entity = computeAwardMultiple(betAmount,user.getVipLvl());
			if(entity.getRatio().floatValue() <= 0 || entity.getMultiple().floatValue() <= 0){
				throw new Exception("ratio or multiple wrong!");
			}
			log.debug("final ratio : " + entity.getRatio());
			log.debug("final multiple : " + entity.getMultiple());
			//計算總獎金
			Long bonus = new BigDecimal(betAmount.toString())
					.multiply(new BigDecimal(entity.getRatio().toString())
					.divide(new BigDecimal(PERCENT.toString()))
					.multiply(new BigDecimal(entity.getMultiple().toString()))).setScale(2, BigDecimal.ROUND_HALF_UP).longValue();
			log.debug("final mubonusltiple : " + bonus.toString());
			//寫入一筆log
			addActivityLog(user, entity, bonus, betAmount);
			//寫fund_change_log//寫入fund_change_log 需要參數:
			Long addBonus = new BigDecimal(bonus.toString()).longValue();
			addFundChangeLog(userId,addBonus);
			if(entity.getMultiple().intValue() == 10){
				String rediskey = DOUBLEBOX_REDISKEY + new SimpleDateFormat("yyyyMMdd").format(now);
				redisSerive.set(rediskey, user.getId().toString());
				log.debug("set redis value : " + redisSerive.get(rediskey));
			}
			result.setIsSuccess(1L);
			result.setBonus(bonus.toString());
			result.setMultiple(entity.getMultiple().toString());
			result.setBetAmount(betAmount);
			response.getBody().setResult(result);
		}catch(Exception e){
			log.error("doublebox activity award error : " + e.getMessage() );
			e.printStackTrace();
			result.setIsSuccess(0L);
			response.getBody().setResult(result);
		}
		return response;
	}
	
	private void addActivityLog(User user,ActivityRatioEntity entity, Long bonus,Long betAmount){
		ActivityDoubleboxLog record = new ActivityDoubleboxLog();
		record.setActivityId(DOUBLE_BOX_ACTIVITY_ID);
		record.setCreateTime(new Date());
		record.setMultiple(entity.getMultiple());
		record.setMultiplePrize(bonus);
		record.setUserAccount(user.getAccount());
		record.setUserId(user.getId());
		record.setSales(betAmount);
		activityDoubleboxLogDao.saveActivityDoubleboxLog(record);
	}
	
	private void addFundChangeLog(Long userId,Long prize){
		FundGameVo vo = new FundGameVo();
		vo.setUserId(userId);
		vo.setAmount(prize);
		vo.setIsAclUser(0L);
		vo.setOperator(0L);
		vo.setReason(ACTIVITY_REASON_KEY);
		vo.setNote(ACT_NOTE);
		List<FundGameVo> vos = new ArrayList<FundGameVo>();
		vos.add(vo);
		List<FundChangeDetail> maps = new ArrayList<FundChangeDetail>();
		fundChangeService.action(vos,maps);
	}
	
	private List<Map<String,String>> getWinList(Long activityId,Long rownum,Date beginTime){
		List<Map<String,String>> winList = new ArrayList<Map<String,String>>();
		List<ActivityDoubleboxLog> windebugs = activityDoubleboxLogDao.getWinList(activityId, rownum);
		for(ActivityDoubleboxLog log:windebugs){
			Map<String,String> win = new HashMap<String,String>();
			win.put("name", log.getUserAccount());
			win.put("win", log.getMultiplePrize().toString());
			winList.add(win);
		}
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		log.debug("now : " + now);
		cal.setTime(now);
		Long c1 = cal.getTimeInMillis();
		log.debug("c1 : " + c1);
		cal.setTime(beginTime);
		Long c2 = cal.getTimeInMillis();
		log.debug("c2 : " + c2);
		long difference=c1-c2;
		log.debug("difference : " + difference);
		long hour=difference/(3600*1000);
		log.debug("hour : " + hour);
		long index= hour/8;
		log.debug("index : " + index);
		int i=0;
		Entry<String, String> fake = null; 
		for(Entry<String, String> entry:FAKE_WIN_LIST.entrySet()){
			if(index == i){
				Map<String,String> fakeData= new HashMap<String,String>();
				fakeData.put("name",entry.getKey());
				log.debug("name : " + entry.getKey());
				fakeData.put("win",entry.getValue());
				log.debug("win : " + entry.getValue());
				winList.add(fakeData);
				break;
			}
			i++;
		}
		
		Collections.shuffle(winList);
		return winList;
	}
	
	//取得兑换比例
	private float computeRatio(Long betsAmount,Integer vipLvl){
		float ratio = 0f;
		String[] betConfs = douboxBetRatioSetting.split("&");
			
		for(String betconf:betConfs){
			String[] betconfdebugs = betconf.split(";");
			if(betsAmount >= Long.parseLong(betconfdebugs[0].toString())){
				ratio = Float.parseFloat(betconfdebugs[1].toString());
				if(vipLvl.intValue() > 0){//vip
					ratio = Float.parseFloat(betconfdebugs[2].toString());
				}
				break;
			}
		}
		return ratio;
	}
	
	private ActivityRatioEntity computeAwardMultiple(Long betsAmount,Integer vipLvl){
		ActivityRatioEntity entity = new ActivityRatioEntity();
		Float ratio = 0f;
		String[] betConfs = douboxBetRatioSetting.split("&");
				
		Map<Long,Float> probTable = new TreeMap<Long,Float>();
		for(String betconf:betConfs){
			String[] betconfdebugs = betconf.split(";");
			if(betsAmount >= Long.parseLong(betconfdebugs[0].toString())){
				ratio = Float.parseFloat(betconfdebugs[1].toString());
				if(vipLvl.intValue() > 0){//vip
					ratio = Float.parseFloat(betconfdebugs[2].toString());
				}
				String[] probabilitys = betconfdebugs[3].toString().split(",");
				for(String prob:probabilitys){
					String[] percents = prob.split(":");
					probTable.put(Long.parseLong(percents[0]),Float.parseFloat(percents[1]));
				}
				break;
			}
		}
		Float multiple = 0f;
		long rand = RandomVal.randlong(RAND_MAX, RAND_MIN);
		for(Entry<Long,Float> entry:probTable.entrySet()){
			if(rand <= entry.getKey().longValue()){
				//給倍數值
				multiple = entry.getValue();
				if(multiple.floatValue() == HIGHEST_MULTIPLE.floatValue()){
					String rediskey = DOUBLEBOX_REDISKEY + new SimpleDateFormat("yyyyMMdd").format(new Date());
					if(redisSerive.get(rediskey) != null){
						multiple = 2.0f;
					}
				}
				break;
			}
		}
		entity.setRatio(ratio);
		entity.setMultiple(multiple);
		return entity;
	}
	
	

}
