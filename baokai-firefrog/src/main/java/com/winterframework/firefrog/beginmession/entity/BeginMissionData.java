package com.winterframework.firefrog.beginmession.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.beginmession.dao.vo.BeginNewCharge;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewDaybet;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewDayques;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewMission;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewTolbet;

public class BeginMissionData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3942184850331540935L;

	
	private Boolean isNewMission;
	
	private Long bindCardStatus;
	
	private Boolean isFirstChagre;
	
	private Boolean isFirstWithdraw;
	
	private Long missionDay;
	
	private Long bindCardDay;

	private BeginNewMission newMission;
	
	private List<BeginNewDaybet> dayBests;//每日投注設定
	
	private List<BeginNewTolbet> tolBets;//累積投注設定
	
	private Long tolBetDay;//累積投注天數
	
	private List<Long> betDayList;//累積投注日曆資料
	
	private List<QuestionData> questionData;//題目設定
	
	private List<BeginNewDayques> dayques;//連續答題設定
	
	private Long ansMoney;//每日答題金額
	
	private Long answersDays;//連續答題天數
	
	private Boolean dayAnsFinish;//答題是否完畢
	
	private Map<String,Integer> lotteryMap;//砸蛋項目
	
	private Long lotteryAwardAmt;//開獎金額
	
	private List<BeginNewCharge> charges;
	
	private String errorAwardFlag;
	
	public Boolean getIsNewMission() {
		return isNewMission;
	}

	public void setIsNewMission(Boolean isNewMission) {
		this.isNewMission = isNewMission;
	}
	
	public Long getBindCardStatus() {
		return bindCardStatus;
	}

	public void setBindCardStatus(Long bindCardStatus) {
		this.bindCardStatus = bindCardStatus;
	}

	public Boolean getIsFirstChagre() {
		return isFirstChagre;
	}

	public void setIsFirstChagre(Boolean isFirstChagre) {
		this.isFirstChagre = isFirstChagre;
	}

	public Boolean getIsFirstWithdraw() {
		return isFirstWithdraw;
	}

	public void setIsFirstWithdraw(Boolean isFirstWithdraw) {
		this.isFirstWithdraw = isFirstWithdraw;
	}

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

	public Long getAnswersDays() {
		return answersDays;
	}

	public void setAnswersDays(Long answersDays) {
		this.answersDays = answersDays;
	}

	public Long getAnsMoney() {
		return ansMoney;
	}

	public void setAnsMoney(Long ansMoney) {
		this.ansMoney = ansMoney;
	}

	public Boolean getDayAnsFinish() {
		return dayAnsFinish;
	}

	public void setDayAnsFinish(Boolean dayAnsFinish) {
		this.dayAnsFinish = dayAnsFinish;
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

	public String getErrorAwardFlag() {
		return errorAwardFlag;
	}

	public void setErrorAwardFlag(String errorAwardFlag) {
		this.errorAwardFlag = errorAwardFlag;
	}

}
