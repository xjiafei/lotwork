package com.winterframework.firefrog.active.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.winterframework.firefrog.active.dao.IActivityLogDao;
import com.winterframework.firefrog.active.dao.IGameDiamondBettypeDao;
import com.winterframework.firefrog.active.dao.vo.ActivityLog;
import com.winterframework.firefrog.active.dao.vo.GameDiamondBettype;
import com.winterframework.firefrog.active.service.IOctActivityService;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.fund.service.IFundAtomicOperationService;
import com.winterframework.firefrog.fund.web.controller.vo.FundChangeDetail;
import com.winterframework.firefrog.fund.web.controller.vo.FundGameVo;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("octActivityServiceImpl")
public class OctActivityServiceImpl implements IOctActivityService{
	
	private static final Logger log = LoggerFactory.getLogger(OctActivityServiceImpl.class);	
	//計算投注金額
	@PropertyConfig("game_get_queryUserPeriodBets")
	private String queryUserPeriodBets;
	
	@Resource(name = "HttpJsonClientImpl")
	protected IHttpJsonClient httpJsonClient;
	
	@Resource(name = "activityLogDaoImpl")
	private IActivityLogDao activityLogDaoImpl;
	
	@Resource(name = "gameDiamondBettypeDaoImpl")
	private IGameDiamondBettypeDao gameDiamondBettypeDaoImpl;
	
	//資金系統
	@Resource(name = "fundChangeServiceImpl")
	private IFundAtomicOperationService fundChangeService;
	
	private static final String ACTIVITY_REASON_KEY = "PM-PGXX-3";
	public static final Long OCT_ACTIVITY_CONFID_ID = 9l;
	public static final List<String> scaleMap = new ArrayList<String>();
	public static final List<String> scaleVipMap = new ArrayList<String>();
	public static final List<Double> prizeMap = new ArrayList<Double>();
	public static final List<Double> prizeVipMap = new ArrayList<Double>();
    static {
    	scaleMap.add("0.1");
    	scaleMap.add("0.4");
    	scaleMap.add("0.6");
    	scaleMap.add("0.8");
    	
    	scaleVipMap.add("0.2");
    	scaleVipMap.add("0.5");
    	scaleVipMap.add("0.8");
    	scaleVipMap.add("1");
    	
    	prizeMap.add(0.08);
    	prizeMap.add(0.15);
    	prizeMap.add(0.25);
    	prizeMap.add(0.35);
    	prizeMap.add(0.50);
    	
    	prizeVipMap.add(0.10);
    	prizeVipMap.add(0.18);
    	prizeVipMap.add(0.30);
    	prizeVipMap.add(0.40);
    	prizeVipMap.add(0.60);
    }
    
    
	@Override
	public Long queryUserPeriodBets(Long userId) {
		
		Map<String, Object> amountMap = new HashMap<String, Object>();		
		amountMap.put("userId", userId);	
		amountMap.put("startTime", DateUtils.getStartTimeOfCurrentDate().getTime());
		amountMap.put("endTime", DateUtils.getEndTimeOfCurrentDate().getTime());
		return (Long) httpJsonClient.invokeHttp(queryUserPeriodBets,amountMap, new TypeReference<Long>(){});
	}


	@Override
	public ActivityLog isGetPrize(Long userId) {
		return activityLogDaoImpl.isSignUp(userId,OCT_ACTIVITY_CONFID_ID,DateUtils.getStartTimeOfCurrentDate(),DateUtils.getEndTimeOfCurrentDate());
	}

	@Override
	public String getScale(Long amount,Boolean isVip) {
		
		String scale = null;
		
		if(isVip){
			scale =  scaleVipMap.get(getScaleLv(amount));
		}else{
			scale =  scaleMap.get(getScaleLv(amount));
		}
		return scale;
	}
	
	@Override
	public Long getLeftAmount(Long amount) {
		Long legtAmount = 0l ;
		switch (getScaleLv(amount)) {
		case 0:
			legtAmount += 500000000l-amount;
			break;
		case 1:
			legtAmount += 1000000000l-amount;
			break;
		case 2:
			legtAmount += 2000000000l-amount;
			break;
		case 3:
			break;
		}
		return legtAmount;
	}
	
	@Override
	public String getMultiple(Long amount) {
		
		Long lv = 10l;
		switch (getScaleLv(amount)) {
		case 1:
			lv = 20l;
			break;
		case 2:
			lv = 30l;
			break;
		case 3:
			lv = 40l;
			break;
		}
		
		
		List<GameDiamondBettype> multipleGroup = gameDiamondBettypeDaoImpl.getDiamondBettypeByGroupCode(lv);
		List<Long> multipleList = new ArrayList<Long>();
		for(GameDiamondBettype bet : multipleGroup){
			for(int i = 0 ; i < bet.getProbability() ; i++){
				multipleList.add(bet.getMultiple());
			}
		}
		
		Double multiple = Double.valueOf(multipleList.get((int)Math.ceil((Math.random()*multipleList.size())-1)));
		return String.valueOf(multiple/10);
	}
	

	@Override
	public ActivityLog saveActivityLog(Long userId,Long activityId, String param,Date beginTime,Date endTime) {
		ActivityLog isPrize =  activityLogDaoImpl.isSignUp(userId,activityId,beginTime,endTime);
		
		if(isPrize == null){
			ActivityLog activityLog = new ActivityLog();
			activityLog.setUserId(userId);
			activityLog.setActivityId(activityId);
			activityLog.setCreateTime(DateUtils.currentDate());
			activityLog.setAwardTime(DateUtils.currentDate());
			activityLog.setStatus(1l);
			activityLog.setMemo(param);
			activityLogDaoImpl.saveActivityLog(activityLog);
		}
		return	isPrize;
	}
	
	@Override
	public void saveFundChaneLog(Long userId, Long amount){
		FundGameVo vo = new FundGameVo();
		vo.setUserId(userId);
		vo.setAmount(amount);
		vo.setIsAclUser(0L);
		vo.setOperator(0L);
		vo.setReason(ACTIVITY_REASON_KEY);
		vo.setNote("10月活動国庆一起打鬼子");
		List<FundGameVo> vos = Lists.newArrayList();
		vos.add(vo);
		List<FundChangeDetail> maps = new ArrayList<FundChangeDetail>();
		fundChangeService.action(vos, maps);
	}
	

	@Override
	public ActivityLog isSignUp(Long userId, Long activityId,Date beginTime, Date endTime) {
		return activityLogDaoImpl.isSignUp(userId,activityId,beginTime,endTime);
	}
	
	private int getScaleLv(Long amount){
		
		//amount >= 20000000l
		int lv = 0;
		
		if(amount >= 500000000l){
			lv = 1;
		}
		if(amount >= 1000000000l){
			lv = 2;
		}
		if(amount >= 2000000000l){
			lv = 3;
		}
		return lv;
	}


	@Override
	public List<String> checkMultiple() {
		return activityLogDaoImpl.checkMultiple();
	}


	@Override
	public String getPrize(Long charge, Long amount, Boolean isVip) {
		DecimalFormat df = new DecimalFormat("0.00");
		String prize = null;
		Double multiple = 0.0;
		int lv = getPrizeLv(charge,amount);
		
		//流水打不夠
		if(lv == 9){
			prize = "0.00";
		}else{
			if(isVip){
				multiple = prizeVipMap.get(lv);
			}else{
				multiple = prizeMap.get(lv);
			}
			prize = df.format(BigDecimal.valueOf(charge).multiply(BigDecimal.valueOf(multiple)).divide(BigDecimal.valueOf(10000l))).toString();
		}
		
		return prize;
	}

	private int getPrizeLv(Long charge, Long amount){
		
		int lv = 9;
		Long prize =0l;
		//流水 = 銷量 / 充值金額
		prize = amount / charge;
		
		if(prize >= 6 ){
			lv = 0;
		}
		if(prize >= 10 ){
			lv = 1;
		}
		if(prize >= 15 ){
			lv = 2;
		}
		if(prize >= 20 ){
			lv = 3;
		}
		if(prize >= 30 ){
			lv = 4;
		}
		return lv;
	}
	
}
