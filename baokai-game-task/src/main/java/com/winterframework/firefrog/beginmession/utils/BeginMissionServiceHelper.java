package com.winterframework.firefrog.beginmession.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.winterframework.firefrog.beginmession.dao.vo.BeginAward;
import com.winterframework.firefrog.beginmession.dao.vo.BeginAwardLog;
import com.winterframework.firefrog.beginmession.dao.vo.BeginAwardLottery;
import com.winterframework.firefrog.beginmession.dao.vo.BeginBankCardCheck;
import com.winterframework.firefrog.beginmession.dao.vo.BeginMission;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.AwardStatus;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.BindCardStatus;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.LotteryType;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.MissionType;
import com.winterframework.firefrog.common.util.DateUtils;

public class BeginMissionServiceHelper {
	
	public static final String AUTO_USER = "系统自动";
	
	public static final Long calcuteUnit = 10000l;
	
	public static final Long DEFAULT_MISSION_DAY = 21l;
	
	public static final Long DEFAULT_BINDCARD_DAY = 7l;
	
	public static final int QUES_SIZE=3;
	
	/**
	 * 判斷活動剩餘天數
	 * @return
	 */
	public static Long calMissionDay(BeginMission mission){
		if(mission.getBindCardTime()==null){
			return DEFAULT_MISSION_DAY;
		}else{
			return DEFAULT_MISSION_DAY-DateUtils.calcDateBetween(mission.getBindCardTime(), DateUtils.currentDate());			
		}

	}
	
	/**
	 * 判斷綁卡剩餘天數
	 * @return
	 */
	public static Long calBindCardDay(BeginMission mission){
		return DEFAULT_BINDCARD_DAY-DateUtils.calcDateBetween(mission.getMissionStartTime(), DateUtils.currentDate());	
	}
	
	/**
	 * 產生預設的派獎資料
	 * @param userId
	 * @param mission
	 * @return
	 */
	public static BeginAward createBeginAward(Long userId , MissionType mission){
		BeginAward award = new BeginAward();
		award.setUserId(userId);
		award.setAwardType1(mission.getValue());
		award.setCreateTime(DateUtils.currentDate());
		award.setStatus(AwardStatus.UnAward.getValue());
		return award;
	}
	
	public static BeginAwardLog createBeginAwardLog(BeginAward award , Long awardAmount){
		BeginAwardLog awardLog = new BeginAwardLog();
		awardLog.setUserId(award.getUserId());
		awardLog.setAwardId(award.getId());
		awardLog.setPrize(awardAmount);
		awardLog.setAwardTime(DateUtils.currentDate());
		return awardLog; 
	}
	

	/**
	 * 根據傳入的值區間,計算該區間的數值
	 * @param high
	 * @param low
	 * @return
	 */
	public static long createRandomValue(Long low,Long high) {
		if(high==null || low==null){
			return 0l;
		}
		return (long)(Math.random()*(high-low+1)+low);
	}
	
	/**
	 * 產生預設的砸蛋派獎資料
	 * @param userId
	 * @param mission
	 * @return
	 */
	public static BeginAwardLottery createBeginAwardLottery(Long userId , Long missionId , LotteryType type ,MissionType mission){
		BeginAwardLottery award = new BeginAwardLottery();
		award.setUserId(userId);
		award.setMissionId(missionId);
		award.setLotteryType1(mission.getValue());
		award.setLotteryType2(type.getType());
		award.setStatus(AwardStatus.UnAward.getValue());
		award.setCreateTime(DateUtils.currentDate());
		return award;
	}
	
	public static BeginBankCardCheck createBeginBankCardCheck(Long userId , BindCardStatus status){
		BeginBankCardCheck bindCardCheck = new BeginBankCardCheck();
		bindCardCheck.setUserId(userId);
		bindCardCheck.setCreateTime(new Date());
		bindCardCheck.setCheckTime(new Date());
		bindCardCheck.setCheckStatus(status.getValue());
		bindCardCheck.setCheckUser(BeginMissionServiceHelper.AUTO_USER);
		return bindCardCheck;
	}
	
	/**
	 * 產生預設的派獎資料
	 * @param userId
	 * @param mission
	 * @return
	 */
	public static BeginAward createQueryBeginAward(Long userId , MissionType mission,Long missionId){
		BeginAward award = new BeginAward();
		award.setUserId(userId);
		award.setAwardType1(mission.getValue());
		award.setMissionId(missionId);
		return award;
	}
	
	public static Date addDaysToBeforeDawn(Date date,Long day){
		Date addDate= DateUtils.addDays(date, day.intValue());
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		try {
			addDate = format.parse(format.format(addDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return addDate;
	}
	
}
