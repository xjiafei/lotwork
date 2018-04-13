package com.winterframework.firefrog.beginmession.service;

import java.util.List;

import com.winterframework.firefrog.beginmession.dao.vo.BeginAwardLottery;
import com.winterframework.firefrog.beginmession.dao.vo.BeginLotterySet;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewCharge;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewDayques;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewMission;

public interface BeginMissionAwardService {
	
	
	/**
	 * 提現獎勵
	 * @param userId
	 * @param newMission
	 * @param amount
	 */
	public void firstWithdrawAward(Long userId,BeginNewMission newMission,Long amount);
	
	/**
	 * 充值獎勵
	 * @param missionChagres
	 * @param userId
	 * @param amount
	 */
	public void firstChargeAward(List<BeginNewCharge> missionChagres,Long userId , Long amount);
	
	/**
	 * 每日答題獎勵
	 * @param userId
	 * @param amount
	 */
	public Long dailyAnswerAward(Long userId , Long amount , BeginNewMission mission);
	
	/**
	 * 連續答題派獎
	 * @param userId
	 * @param amount
	 * @param dayQues
	 */
	public void continueDailyAnsAward(Long userId , Long amount , BeginNewDayques dayQues);

	/**
	 * 計算砸蛋所得金額
	 * @param userId
	 * @param awardAmt
	 * @param beginAwardLottery
	 * @param setting 
	 * @return
	 */
	public Long eggLotteryAward(Long userId, Long awardAmt,
			BeginAwardLottery beginAwardLottery, BeginLotterySet setting);
	
	public void bindCardAward(Long userId,BeginNewMission mission);
}
