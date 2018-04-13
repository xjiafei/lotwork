package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.phone.web.begin.entity.QuestionData;
import com.winterframework.firefrog.phone.web.begin.vo.BeginNewCharge;
import com.winterframework.firefrog.phone.web.begin.vo.BeginNewDaybet;
import com.winterframework.firefrog.phone.web.begin.vo.BeginNewDayques;
import com.winterframework.firefrog.phone.web.begin.vo.BeginNewMission;
import com.winterframework.firefrog.phone.web.begin.vo.BeginNewTolbet;

/**
 * 
 * @author Ami.Tsai
 *
 */
public class BeginMissionResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7844482928462028738L;

	private String isNewMission;//是否有新手權限
	
	private Long bindCardStatus;//是否綁卡
	
	private String isFirstChagre;//是否首充
	
	private String isFirstWithdraw;//是否首提
	
	private String dayAnsFinish;//答題是否完畢
	
	private Long missionDay;//新手任務剩餘天數
	
	private Long bindCardDay;//綁卡剩餘天數

	private BeginNewMission newMission;//新手任務設定
	
	private List<BeginNewDaybet> dayBests;//每日投注設定
	
	private List<BeginNewTolbet> tolBets;//累積投注設定
	
	private Long tolBetDay;//累積投注天數
	
	private List<Long> betDayList;//累積投注日曆資料
	
	private List<QuestionData> questionData;//題目設定
	
	private List<BeginNewDayques> dayques;//連續答題設定
	
	private Long ansMoney;//每日答題金額
	
	private Long answersDays;//連續答題天數
	
	private Map<String,Integer> lotteryMap;//砸蛋項目
	
	private Long lotteryAwardAmt;//開獎金額
	
	private List<BeginNewCharge> charges;
	
	private String errorAwardFlag;

	public Long getMissionDay() {
		return missionDay;
	}

	public void setMissionDay(Long missionDay) {
		this.missionDay = missionDay;
	}

	public Long getBindCardDay() {
		return bindCardDay;
	}

	public void setBindCardDay(Long bindCardDay) {
		this.bindCardDay = bindCardDay;
	}

	public BeginNewMission getNewMission() {
		return newMission;
	}

	public void setNewMission(BeginNewMission newMission) {
		this.newMission = newMission;
	}

	public List<BeginNewDaybet> getDayBests() {
		return dayBests;
	}

	public void setDayBests(List<BeginNewDaybet> dayBests) {
		this.dayBests = dayBests;
	}

	public List<BeginNewTolbet> getTolBets() {
		return tolBets;
	}

	public void setTolBets(List<BeginNewTolbet> tolBets) {
		this.tolBets = tolBets;
	}

	public Long getTolBetDay() {
		return tolBetDay;
	}

	public void setTolBetDay(Long tolBetDay) {
		this.tolBetDay = tolBetDay;
	}

	public List<Long> getBetDayList() {
		return betDayList;
	}

	public void setBetDayList(List<Long> betDayList) {
		this.betDayList = betDayList;
	}

	public List<QuestionData> getQuestionData() {
		return questionData;
	}

	public void setQuestionData(List<QuestionData> questionData) {
		this.questionData = questionData;
	}

	public List<BeginNewDayques> getDayques() {
		return dayques;
	}

	public void setDayques(List<BeginNewDayques> dayques) {
		this.dayques = dayques;
	}

	public Long getAnsMoney() {
		return ansMoney;
	}

	public void setAnsMoney(Long ansMoney) {
		this.ansMoney = ansMoney;
	}

	public Long getAnswersDays() {
		return answersDays;
	}

	public void setAnswersDays(Long answersDays) {
		this.answersDays = answersDays;
	}

	public Map<String, Integer> getLotteryMap() {
		return lotteryMap;
	}

	public void setLotteryMap(Map<String, Integer> lotteryMap) {
		this.lotteryMap = lotteryMap;
	}

	public Long getLotteryAwardAmt() {
		return lotteryAwardAmt;
	}

	public void setLotteryAwardAmt(Long lotteryAwardAmt) {
		this.lotteryAwardAmt = lotteryAwardAmt;
	}

	public List<BeginNewCharge> getCharges() {
		return charges;
	}

	public void setCharges(List<BeginNewCharge> charges) {
		this.charges = charges;
	}

	public String getIsNewMission() {
		return isNewMission;
	}

	public void setIsNewMission(String isNewMission) {
		this.isNewMission = isNewMission;
	}

	public Long getBindCardStatus() {
		return bindCardStatus;
	}

	public void setBindCardStatus(Long bindCardStatus) {
		this.bindCardStatus = bindCardStatus;
	}

	public String getIsFirstChagre() {
		return isFirstChagre;
	}

	public void setIsFirstChagre(String isFirstChagre) {
		this.isFirstChagre = isFirstChagre;
	}

	public String getIsFirstWithdraw() {
		return isFirstWithdraw;
	}

	public void setIsFirstWithdraw(String isFirstWithdraw) {
		this.isFirstWithdraw = isFirstWithdraw;
	}

	public String getDayAnsFinish() {
		return dayAnsFinish;
	}

	public void setDayAnsFinish(String dayAnsFinish) {
		this.dayAnsFinish = dayAnsFinish;
	}

	public String getErrorAwardFlag() {
		return errorAwardFlag;
	}

	public void setErrorAwardFlag(String errorAwardFlag) {
		this.errorAwardFlag = errorAwardFlag;
	}
	
}
