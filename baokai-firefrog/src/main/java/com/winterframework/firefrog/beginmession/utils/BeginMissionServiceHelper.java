package com.winterframework.firefrog.beginmession.utils;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.winterframework.firefrog.beginmession.dao.vo.BeginAward;
import com.winterframework.firefrog.beginmession.dao.vo.BeginAwardLog;
import com.winterframework.firefrog.beginmession.dao.vo.BeginAwardLottery;
import com.winterframework.firefrog.beginmession.dao.vo.BeginBankCardCheck;
import com.winterframework.firefrog.beginmession.dao.vo.BeginMission;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewQuestion;
import com.winterframework.firefrog.beginmession.entity.QuestionData;
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
	 * 判斷是否具有新手資格
	 * @return
	 */
	public static Boolean checkIsBindCard(BeginMission mission){
		if(mission.getBindCardTime()!=null){
			Long time = DateUtils.getTImeDiff(mission.getBindCardTime(), DateUtils.currentDate())/60;
			if(time>=60){
				return true;
			}
		}{
			return false;
		}
	}
	
	/**
	 * 判斷活動剩餘天數
	 * @return
	 */
	public static Long calMissionDay(BeginMission mission){
		if(mission.getBindCardTime()==null){
			return DEFAULT_MISSION_DAY;
		}else{
			Long missionDay = 0l;
			missionDay = DEFAULT_MISSION_DAY-DateUtils.calcDateBetween(mission.getBindCardTime(), DateUtils.currentDate());
			return missionDay<=0?0:missionDay;			
		}
	}
	
	/**
	 * 判斷綁卡剩餘天數
	 * @return
	 */
	public static Long calBindCardDay(BeginMission mission){
		Long bindDay = 0l;
		bindDay = DEFAULT_BINDCARD_DAY-DateUtils.calcDateBetween(mission.getMissionStartTime(), DateUtils.currentDate());			
		return bindDay<=0?0:bindDay;	
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
	
	public static BeginAwardLog createBeginAwardLog(BeginAwardLottery award , Long awardAmount){
		BeginAwardLog awardLog = new BeginAwardLog();
		awardLog.setUserId(award.getUserId());
		awardLog.setAwardLotteryId(award.getId());
		awardLog.setPrize(awardAmount);
		awardLog.setAwardTime(DateUtils.currentDate());
		return awardLog; 
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
	
	public static BeginAwardLottery createQueryBeginAwardLottery(Long userId , Long missionId ,MissionType mission){
		BeginAwardLottery award = new BeginAwardLottery();
		award.setUserId(userId);
		award.setMissionId(missionId);
		award.setLotteryType1(mission.getValue());
		return award;
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
	
	
	/**
	 * 取得隨機抽獎題目
	 * @param questions
	 * @return
	 */
	public static List<QuestionData> getQuestion(List<BeginNewQuestion> questions){
		List<QuestionData> quesData = Lists.newArrayList();
		Collections.shuffle(questions);
		
		int size =0;
		if(questions.size()<QUES_SIZE){
			size=questions.size();
		}else{
			size=QUES_SIZE;
		}
		
		for(int i = 0 ; i<size;i++){
			QuestionData data = new QuestionData();
			BeginNewQuestion question = questions.get(i);
			List<String> answers = Lists.newArrayList();
			answers.add(question.getCrtAnswer());
			answers.add(question.getErrAnswer1());
			answers.add(question.getErrAnswer2());			
			Collections.shuffle(answers);
			int index = 0;
			for(String answer :answers){
				if(answer.equals(question.getCrtAnswer())){
					data.setCorrect(index);
				}
				index++;
			}
			data.setTitle(question.getQuestion());
			data.setAnswer(answers);
			quesData.add(data);
		}
		
		return quesData;
		
	}

	/**
	 * 傳入每日答題獎勵,計算目前連續答題天數
	 * @param awards
	 * @return
	 */
	public static Long calcuteAnsDays(List<BeginAward> awards) {
		
		final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		List<String> dateStrs = Lists.transform(awards, new Function<BeginAward, String>() {
			@Override
			public String apply(BeginAward input) {
				if(input.getCreateTime()!=null){
					return format.format(input.getCreateTime());
				}
				return "";
			}
		});
		Long days = 0l;
		for(int i =0;i<=dateStrs.size();i++){
			Date date = DateUtils.minusMinutes(new Date(),i*60*24);
			String  dateStr= format.format(date);
			if(dateStrs.contains(dateStr)){
				days++;
			}else{
				if(i!=0){
					break;
				}

			}
		}
		return days;
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
		low = low/BeginMissionUtils.calcuteUnit;
		high = high/BeginMissionUtils.calcuteUnit;
		
		return ((long)(Math.random()*(high-low+1)+low))*BeginMissionUtils.calcuteUnit;
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
	
}
