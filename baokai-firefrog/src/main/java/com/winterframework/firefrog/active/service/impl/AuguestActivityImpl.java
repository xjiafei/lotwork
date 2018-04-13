package com.winterframework.firefrog.active.service.impl;


import java.security.Timestamp;
import java.util.Calendar;
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
import com.winterframework.firefrog.active.dao.IActivityConfigDao;
import com.winterframework.firefrog.active.dao.IActivityDao;
import com.winterframework.firefrog.active.dao.IActivityLogDao;
import com.winterframework.firefrog.active.dao.IActivityResultDao;
import com.winterframework.firefrog.active.dao.IActivitySignUpDao;
import com.winterframework.firefrog.active.dao.vo.Activity;
import com.winterframework.firefrog.active.dao.vo.ActivityConfig;
import com.winterframework.firefrog.active.dao.vo.ActivityLog;
import com.winterframework.firefrog.active.dao.vo.ActivityResult;
import com.winterframework.firefrog.active.dao.vo.ActivitySignUp;
import com.winterframework.firefrog.active.entity.ActivityAuguest;
import com.winterframework.firefrog.active.service.IAuguestActivityService;
import com.winterframework.firefrog.active.struc.activityResultJsonStruc;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.Encrypter;
import com.winterframework.firefrog.common.util.RandomVal;
import com.winterframework.modules.spring.exetend.PropertyConfig;


@Service("auguestActivityImpl")
@Transactional(rollbackFor = Exception.class)
public class AuguestActivityImpl implements IAuguestActivityService{
	
	//活動主檔
	@Resource(name = "activityDaoImpl")
	IActivityDao    activityDao;
	
	//活動LOG紀錄
	@Resource(name = "activityLogDaoImpl")
	IActivityLogDao    activityLogDao;
	//活動(中獎)結果
	@Resource(name = "activityResultDaoImpl")
	IActivityResultDao    activityResultDao;
	//活動報名表
	@Resource(name = "activitySignUpDaoImpl")
	IActivitySignUpDao    activitySignUpDao;
	//活動規則
	@Resource(name = "activityConfigDaoImpl")
	IActivityConfigDao    activityConfigDao;
	
	@PropertyConfig("game_get_queryUserPeriodBets")
	private String queryUserPeriodBets;
	
	@Resource(name = "HttpJsonClientImpl")
	protected IHttpJsonClient httpJsonClient;
	
	@PropertyConfig(value="encrypter.Key")
	protected String encrypterKey;
	@PropertyConfig(value="encrypter.value")
	protected String encrypterValue;
	
	
	private static final Logger log = LoggerFactory.getLogger(AuguestActivityImpl.class);	
	
	//大獎類別
	private static final String MAX_AWARDS = "34S";
	//資料庫金額放大倍數
	private static final Long MULTIPLE = 10000L;
	

	@Override
	public ActivityAuguest getAcvitityByCode(ActivityAuguest activityEntity) {
		Activity actResultVO = new Activity();
		Activity actVO = new Activity();
		actVO.setActivityCode(activityEntity.getActivityCode());
		log.debug("getAcvitityByCode : ActivityCode=" + actVO.getActivityCode());
		actResultVO = activityDao.getActivityByCode(actVO);
		activityEntity.setActivityId(actResultVO.getId());
		activityEntity.setActivityStartTime(actResultVO.getStartTime());
		activityEntity.setActivityEndTime(actResultVO.getEndTime());
		if(!actResultVO.getCommon().isEmpty()){
			activityResultJsonStruc tempActRsStruc = (activityResultJsonStruc)DataConverterUtil.convertJson2Object(actResultVO.getCommon() ,
					activityResultJsonStruc.class);
			activityEntity.setDayStart(tempActRsStruc.getDayStart());
			activityEntity.setDayEnd(tempActRsStruc.getDayEnd());
			activityEntity.setRateRandMax(tempActRsStruc.getRateRandMax());
			activityEntity.setRateRandMin(tempActRsStruc.getRateRandMin());
		}		
		log.debug("getAcvitityByCode : Common=" + actResultVO.getCommon());
		return activityEntity;
	}

	@Override
	public ActivityAuguest getActivityConfig(ActivityAuguest activityEntity) {
		
		Integer userLvl = activityEntity.getUserLv();
		ActivityConfig activityConfigVO = new ActivityConfig();
		activityConfigVO.setActivityId(activityEntity.getActivityId());
		activityConfigVO.setType(activityEntity.getConfigType());	
		
		log.info("getActivityConfig : ActivityId=" + activityEntity.getActivityId() + " ConfigType=" + activityEntity.getConfigType());
		ActivityConfig actConfig = activityConfigDao.getActCfg(activityConfigVO);
		if(actConfig !=null){			
			activityEntity.setRand_max(actConfig.getMaxPrize().longValue());
			activityEntity.setRand_min(actConfig.getMinPrize().longValue());
			//取得紅包數量設定
			activityResultJsonStruc actRsStruc = (activityResultJsonStruc)DataConverterUtil.convertJson2Object(actConfig.getRule() ,
					activityResultJsonStruc.class);
			Long quantity = actRsStruc.getQuantity();
			if(userLvl == 4){
				ActivityConfig tempConfigVO = new ActivityConfig();
				tempConfigVO.setActivityId(activityEntity.getActivityId());
				tempConfigVO.setType(MAX_AWARDS);	
				ActivityConfig tempConfig = activityConfigDao.getActCfg(activityConfigVO);
				activityResultJsonStruc tempActRsStruc = (activityResultJsonStruc)DataConverterUtil.convertJson2Object(tempConfig.getRule() ,
						activityResultJsonStruc.class);
				quantity = quantity + tempActRsStruc.getQuantity() ; //LV4用戶 含大獎所以須包含大獎設定
			}
			activityEntity.setQuantity(quantity);
		}else{
			activityEntity.setRand_max(0L);
			activityEntity.setRand_min(0L);
			activityEntity.setQuantity(0L);		
		}
		return activityEntity;
	}

	/**
	 * 今日投注額計算
	 * 1.未中獎 : 須計算整日投注額當作確認是否有翻牌資格的依據
	 * 2.已中獎 : 由中獎時間當作起始計算時間，計算是否有領獎資格
	 * @param activityEntity
	 * @return
	 */
	@Override
	public Long getAgentAmount(ActivityAuguest activityEntity) {
		//今日投注額計算若用戶未中獎須計算整日投注額當作確認是否有翻牌資格的依據 ，若用戶已中獎則須改為由中獎時間當作起始計算時間
		
		Map<String, Object> amountMap = new HashMap<String, Object>();		
		amountMap.put("userId", activityEntity.getUserId());	
		if(activityEntity.isFinished()){ //已中獎 : 由中獎時間當作起始計算時間，計算是否有領獎資格
			amountMap.put("startTime", activityEntity.getAwardTime().getTime());
		}else { //未中獎 : 須計算8/1 ~ 8/20 投注額當作確認是否有翻牌資格的依據
			amountMap.put("startTime", getWantedTime(0, 0, 0, 0, 0).getTime());
		}	
		amountMap.put("endTime", getWantedTime(0, 23, 59, 59, 0).getTime());
		log.debug("getAgentAmount : userId=" + activityEntity.getUserId() + "  startTime=" + amountMap.get("startTime") 
				+ " endTime" + amountMap.get("endTime") + " isFinished:" + (activityEntity.isFinished()? "true" : "false"));
		Long betAmount = (Long) httpJsonClient.invokeHttp(queryUserPeriodBets,
				amountMap, new TypeReference<Long>(){});
		log.debug("getAgentAmount : userId=" + activityEntity.getUserId() + "  startTime=" + amountMap.get("startTime") 
				+ " endTime" + amountMap.get("endTime") + " isFinished:" + (activityEntity.isFinished()? "true" : "false") + " betAmount =" + betAmount);
		return betAmount;
	}

	@Override
	public ActivityAuguest cuntShortAmount(ActivityAuguest activityEntity) {	
		//activityEntity = cuntPrizeAmount(activityEntity); //計算獎金對應 應投注金額
		Long prizeAmount = activityEntity.getPrizeAmount();//中獎後應投注金額
		Long totalAmount = getAgentAmount(activityEntity)/MULTIPLE; //用戶今日投注額
		activityEntity.setUserAmount(totalAmount);			
		activityEntity.setLeftAmount((totalAmount - prizeAmount) >=0 ? 0L : 0-(totalAmount - prizeAmount));
		return activityEntity;
	}


	@Override
	public ActivityAuguest getAgentPrize(ActivityAuguest activityEntity) {
		log.debug("red envelope getAgentPrize: 查詢用戶今日中獎資料");
		//查詢用戶今日中獎資料
		ActivityResult actRsVO = new ActivityResult();
		log.debug("getAgentPrize : userId=" + activityEntity.getUserId() + " ActivityId=" + activityEntity.getActivityId());
		actRsVO.setUserId(activityEntity.getUserId());
		actRsVO.setActivityId(activityEntity.getActivityId());
		List<ActivityResult> actRSList = activityResultDao.getUserPrize(actRsVO);
		log.debug("red envelope getAgentPrize: 查詢用戶("+ activityEntity.getUserId() +")今日中獎資料:" + actRSList.size());
		//ActivityResult actRS = activityResultDao.getUserPrize(actRsVO);
		if(actRSList.size() <= 0){//用戶今日未參加
			activityEntity.setFinished(false);
			activityEntity.setLeftAmount(0L);
			activityEntity.setPrizeAmount(0L);
			activityEntity.setPrize(0L);
			activityEntity.setAwardTime(null);
			//計算金額
		}else{
			activityEntity.setFinished(true);
			//activityEntity.setAwardTime(actRS.getCreateTime());
			activityEntity.setAwardTime(actRSList.get(0).getCreateTime());
			//取得中獎結果
			//activityResultJsonStruc actRsStruc = (activityResultJsonStruc)DataConverterUtil.convertJson2Object(actRS.getResult() ,
			//		activityResultJsonStruc.class);
			activityResultJsonStruc actRsStruc = (activityResultJsonStruc)DataConverterUtil.convertJson2Object(actRSList.get(0).getResult() ,
					activityResultJsonStruc.class);
			activityEntity.setPrize(actRsStruc.getPrize());
			
		}
		return activityEntity;
	}
	
	/**
	 * 計算中獎金額對應之應投注額
	 * @param prize 中獎金額
	 * @return
	 */
	private ActivityAuguest cuntPrizeAmount(ActivityAuguest activityEntity){
		Long prize = activityEntity.getPrize();
		activityEntity.setPrizeAmount((prize * 25));		
		return activityEntity; //計算獎金對應 應投注金額
	}
	/* 
	 * 給獎(含大獎)若不是 VIP4 用戶不再特別計算 大獎是否已抽出
	 * 確認用戶參加資格
	 * 確認紅包數量
	 * 確認用戶是否已給獎
	 * 計算目前餘額(仍需投注)
	 * 
	 */
	@Override
	public ActivityAuguest setPrize(ActivityAuguest activityEntity) {
		long redEnvelope = 0L;
		long rand_max = activityEntity.getRand_max();
		long rand_min = activityEntity.getRand_min()-1;
		
		//相關檢查(當天是否領過，一天只能一次) 確認用戶是否已中獎
		activityEntity = getAgentPrize(activityEntity);
		if(activityEntity.isFinished()){//用戶中獎
			activityEntity.setStatus(1L); //用戶已中獎 翻牌狀態設定為成功
			activityEntity = cuntPrizeAmount(activityEntity); //應投注金額
			activityEntity = cuntShortAmount(activityEntity); //已投注金額 + 仍需投注金額	
			log.debug("red envelope activity: Is Finished ~~");
			activityEntity.setMemo("用戶 " + activityEntity.getUserAccunt() + " 翻牌，中獎金額 :" + activityEntity.getPrize());
			activityLog(activityEntity);
			return activityEntity;
		}
		//檢查是否在活動期間
		boolean isPeriod = checkIsActPeriod(activityEntity);
		if(!isPeriod){
			activityEntity.setStatus(2L);
			log.debug("red envelope activity error: not in activity time~~");
			return activityEntity;
		}else{
			activityEntity.setStatus(1L);
		}
		
		//檢查是否為 當日活動時間
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		if(now.before(getWantedTime(0,activityEntity.getDayStart().intValue(),0,0,0))){
			log.warn("red envelope activity error: not in current day act time~~");
			activityEntity.setStatus(0L);
			activityEntity.setTodayFinished(false);
			return activityEntity;
		}
		if(now.after(getWantedTime(0,activityEntity.getDayEnd().intValue(),0,0,0))){//今日活動已結束等明天
			log.debug("red envelope activity error: day act finished~~");	
			activityEntity.setTodayFinished(true);
			activityEntity.setStatus(0L);	
			if(!activityEntity.isFinished()){//設定明日活動開始時間
				Date tomorrowStart = getWantedTime(1,activityEntity.getDayStart().intValue(),0,0,0);
				Date tomorrowEnd = getWantedTime(1,activityEntity.getDayEnd().intValue(),0,0,0);	
				activityEntity.setTodayStartTime(tomorrowStart);
				activityEntity.setTodayEndTime(tomorrowEnd);
			}
			return activityEntity;			
		}	
		//計算紅包資料
		//若lv4 紅包已抽完但是大獎還在
		activityEntity = cuntQualification(activityEntity);	
		if(activityEntity.getSurplusPrize() > 0){//還有紅包
			redEnvelope = RandomVal.randlong(rand_max, rand_min);
			//lv4 用戶 超級大獎機率
			if(activityEntity.getUserLv().intValue() ==4){
				//取得最大獎計算
				activityEntity = cuntAwards(activityEntity);
				if(activityEntity.getAwardsCunt() <= 0){
					long rate = RandomVal.randlong(activityEntity.getRateRandMax(),activityEntity.getRateRandMin());
					activityEntity.setMemo("用戶 " + activityEntity.getUserAccunt() + " 抽大獎數字 :" + rate);
					activityLog(activityEntity);
					if(50000 <= rate){ //沒有中大獎
						activityEntity.setConfigType(MAX_AWARDS);
						activityEntity = getActivityConfig(activityEntity);
						rand_max = activityEntity.getRand_max();
						rand_min = activityEntity.getRand_min();
						redEnvelope = RandomVal.randlong(rand_max, rand_min);
					}else{ //若 LV4 已沒有紅包可抽，且大獎又共辜
						log.debug(" LV4用戶 :" + activityEntity.getUserAccunt() + " 剩餘紅包數量 :" + activityEntity.getSurplusPrize() );	
						Long total = activityEntity.getSurplusPrize();
						total = total -1;
						if(total <1L){
							log.debug(" LV4用戶 :" + activityEntity.getUserAccunt() + " 剩餘紅包數量不含大獎 :" + total );	
							activityEntity.setStatus(0L);
							redEnvelope = 0L;
						}
					}
				}
			}
			//已投注金額 + 仍需投注金額	計算須由抽獎後開始
			if(redEnvelope >=1L && activityEntity.getStatus() >= 1L){
				activityEntity.setAwardTime(now);//中獎時間
				activityEntity.setPrize(redEnvelope);
				activityEntity = cuntPrizeAmount(activityEntity); //應投注金額
				activityEntity = cuntShortAmount(activityEntity); //已投注金額 + 仍需投注金額	
				//紀錄中獎資料
				saveAwards(activityEntity);
				activityEntity.setMemo("用戶 " + activityEntity.getUserAccunt() + " 抽獎，中獎金額 : " + redEnvelope);
				activityLog(activityEntity);
			} else {//LV4 用戶今日紅包已抽完  且未抽中大獎
				activityEntity.setMemo("LV4用戶 " + activityEntity.getUserAccunt() + " 今日紅包已用完且未抽中大獎回覆 : 今日紅包已用完");
				activityLog(activityEntity);
			}			
		}else{
			activityEntity.setStatus(0L); //紅包已抽完 staus設定為 false
			activityEntity.setMemo("用戶 " + activityEntity.getUserAccunt() + " 今日紅包已用完");
			activityLog(activityEntity);
		}
		return activityEntity;
	}

	/* 
	 * 初始化資料
	 * 確認是否為活動期間
	 * 確認遊戲是否開始或結束 20:00~22:00
	 * 確認用戶是否已中獎
	 * 確認紅包數量
	 * 若用戶已中獎，計算仍需投注金額
	 */
	@Override
	public ActivityAuguest setDefaultParam(ActivityAuguest activityEntity) {
			
		//檢查是否在活動期間
		boolean isPeriod = checkIsActPeriod(activityEntity);
		if(!isPeriod){
			activityEntity.setStatus(2L);
			activityEntity.setSurplusPrize(10L);
			log.warn("red envelope activity error: not in activity time~~");
			return activityEntity;
		}else{
			activityEntity.setStatus(1L);
		}
		//相關檢查(當天是否領過，一天只能一次) 確認用戶是否已中獎
		activityEntity = getAgentPrize(activityEntity);
		if(activityEntity.isFinished()){//用戶中獎 計算投注金額
			activityEntity = cuntPrizeAmount(activityEntity); //應投注金額
			activityEntity = cuntShortAmount(activityEntity); //已投注金額 + 仍需投注金額		
		}
		//檢查是否為 當日活動時間
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		//目前伺服器時間
		activityEntity.setNowTime(now);
		if(now.before(getWantedTime(0,activityEntity.getDayStart().intValue(),0,0,0))){
			log.warn("red envelope activity error: not in current day act time~~");
			activityEntity.setStatus(0L);
			activityEntity.setTodayFinished(false);
			activityEntity.setSurplusPrize(10L);
			return activityEntity;
		}
		if(now.after(getWantedTime(0,activityEntity.getDayEnd().intValue(),0,0,0))){//今日活動已結束等明天
			log.warn("red envelope activity error: day act finished~~");	
			activityEntity.setTodayFinished(true);
			activityEntity.setStatus(0L);	
			if(!activityEntity.isFinished()){//設定明日活動開始時間
				Date tomorrowStart = getWantedTime(1,activityEntity.getDayStart().intValue(),0,0,0);
				Date tomorrowEnd = getWantedTime(1,activityEntity.getDayEnd().intValue(),0,0,0);	
				activityEntity.setTodayStartTime(tomorrowStart);
				activityEntity.setTodayEndTime(tomorrowEnd);
			}
			return activityEntity;			
		}
		//計算紅包數量
		activityEntity = cuntQualification(activityEntity);	
			
		return activityEntity;
	}

	@Override
	public ActivityAuguest cuntAwards(ActivityAuguest activityEntity) {
		ActivityResult actRsVO = new ActivityResult();
		actRsVO.setType(MAX_AWARDS);
		Long result = activityResultDao.getAwardRecord(actRsVO);
		activityEntity.setAwardsCunt(result);
		return activityEntity;
	}

	@Override
	public boolean checkIsActPeriod(ActivityAuguest activityEntity) {
		boolean isPeriod = true;
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		log.info("begin time : " + activityEntity.getActivityStartTime());
		log.info("end time : " + activityEntity.getActivityEndTime());
		log.info("now.before(actCfg.getBeginTime() : " + activityEntity.getActivityStartTime());
		log.info("now.after(actCfg.getEndTime() : " + activityEntity.getActivityEndTime());
		
		if(now.before(activityEntity.getActivityStartTime() ) || now.after(activityEntity.getActivityEndTime()) ){
			isPeriod = false;
		}
		return isPeriod;
	}

	@Override
	public ActivityAuguest checkQualification(ActivityAuguest activityEntity) {
		ActivitySignUp actSignUpVO = new ActivitySignUp();
		actSignUpVO.setAccunt(activityEntity.getUserAccunt());
		actSignUpVO.setActivityId(activityEntity.getActivityId());
		Long cunt = activitySignUpDao.getUserQuanlification(actSignUpVO);
		if(cunt >0){
			activityEntity.setQualification(true);
			
			//用戶今日投注額
			Long totalAmount = getAgentAmount(activityEntity)/MULTIPLE; //用戶今日投注額
			activityEntity.setUserAmount(totalAmount);
			//當日投注金額小於100
			//if(totalAmount < 100){
			//	activityEntity.setQualification(false);
			//	activityEntity.setMemo("用戶 " + activityEntity.getUserAccunt() + " 投注金額不足無參加資格，今日有效投注金額 :" + totalAmount);
			//	activityLog(activityEntity);
			//}
		}else{
			activityEntity.setQualification(false);
			activityEntity.setMemo("用戶 " + activityEntity.getUserAccunt() + " 不再活動白名單內，無參加資格");
			activityLog(activityEntity);
		}
		return activityEntity;
	}
	
	

	@Override
	public ActivityAuguest cuntQualification(ActivityAuguest activityEntity) {
		ActivityResult actRSVO = new ActivityResult();
		Long rs = 0L;
		String configType = activityEntity.getConfigType();
		Integer userSource = (activityEntity.getUserSource() == null) ? 4:3;
		Integer userLvl = activityEntity.getUserLv();
		if(userLvl == 4){
			configType = "34S";
			actRSVO.setType(configType);
			rs = activityResultDao.getAwardRecord(actRSVO);
			configType = userSource+ "V" + userLvl;
		}
		actRSVO.setActivityId(activityEntity.getActivityId());
		actRSVO.setType(configType);
		rs += activityResultDao.getAwardRecord(actRSVO);
		Long quantity = activityEntity.getQuantity();
		log.debug("red envelope activity cuntQualification(紅包剩餘數量): " + (quantity-rs) + " quantity=" + quantity + " rs=" + rs);	
		activityEntity.setSurplusPrize(quantity-rs);
		return activityEntity;
	}

	@Override
	public void activityLog(ActivityAuguest activityEntity) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		ActivityLog actLogVO = new ActivityLog();
		actLogVO.setActivityId(activityEntity.getActivityId());
		actLogVO.setUserId(activityEntity.getUserId());
		actLogVO.setCreateTime(now);
		actLogVO.setAwardTime(now);
		actLogVO.setStatus(1L);
		actLogVO.setMemo(activityEntity.getMemo());
		activityLogDao.saveActivityLog(actLogVO);
	}

	@Override
	public Date getWantedTime(int day, int hourOfday, int second, int minute,
			int millisec) {
		Calendar cal = Calendar.getInstance();  
		cal.add(Calendar.DATE, day); 
	    cal.set(Calendar.HOUR_OF_DAY, hourOfday);  
	    cal.set(Calendar.SECOND, second);  
	    cal.set(Calendar.MINUTE, minute);  
	    cal.set(Calendar.MILLISECOND, millisec);
	    return cal.getTime();
	}

	@Override
	public void saveAwards(ActivityAuguest activityEntity) {
		ActivityResult actRsVO = new ActivityResult();
		actRsVO.setActivityId(activityEntity.getActivityId());
		actRsVO.setUserId(activityEntity.getUserId());
		actRsVO.setStatus(1L);
		actRsVO.setType(activityEntity.getConfigType());
		actRsVO.setCreateUser(activityEntity.getActivityId());
		actRsVO.setCreateTime(activityEntity.getAwardTime());
		actRsVO.setModifyUser(activityEntity.getActivityId());
		actRsVO.setModifyTime(activityEntity.getAwardTime());
		actRsVO.setResult("{" + '"' + "prize" + '"' + ":" + '"' + activityEntity.getPrize() + '"' +"}");
		activityResultDao.insert(actRsVO);
		log.debug("red envelope activity saveAwards: 紀錄中獎資訊");	
	}

	@Override
	public String getUserNameByToken(String token) {
		Encrypter en = Encrypter.getInstance(encrypterKey,encrypterValue);
		String tt = en.decrypt(token);
		String[] str = tt.split("\\|");
		return str[0];
	}

}
