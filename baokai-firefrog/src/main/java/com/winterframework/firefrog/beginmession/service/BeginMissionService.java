package com.winterframework.firefrog.beginmession.service;

import com.winterframework.firefrog.beginmession.entity.BeginMissionData;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.BindCardStatus;


public interface BeginMissionService {
	
	/**
	 * 註冊帳號時,將新手任務後台版本綁定,以及寫入新手任務主流成
	 * @param user
	 */
	public void bindMissionVer(Long userId);
	
	
	/**
	 * 進入新手任務頁面
	 * @param userId
	 * @return
	 */
	public BeginMissionData checkNewMission(Long userId);
	
	
	/**
	 * 儲存活動獎勵
	 * @param userId
	 * @param status
	 */
	public void bindCardAward(Long userId);
	
	/**
	 * 首充獎勵
	 * @param userId
	 * @param amount
	 */
	public void firstChargeAward(Long userId , Long amount);
	
	/**
	 * 首提獎勵
	 * @param userId
	 * @param amount
	 */
	public void firstWthdrawAward(Long userId , Long amount);

	/**
	 * 日常任務畫面
	 * @param userId
	 * @return
	 */
	public BeginMissionData getDailyMissionData(Long userId) throws Exception;
	
	/**
	 * 進入每日答題提取資料
	 * @param userId
	 * @return
	 */
	public BeginMissionData getDayQuestion(Long userId);
	
	/**
	 * 每日答題獎勵提交
	 * @param userId
	 */
	public BeginMissionData dailyAnswerAward(Long userId);
	
	/**
	 * 進入砸蛋獎勵
	 * @param userId
	 */
	public BeginMissionData gotoEggLottery(Long userId);

	/**
	 * 砸蛋開獎
	 * @param userId
	 * @param lotteryType
	 * @return
	 */
	public BeginMissionData eggLotteryAward(Long userId, Long lotteryType);
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public BeginMissionData getUserBeginNewCharge(Long userId);
	
	/**
	 * 检查新手任务绑卡异动时间，若不符则取消任务
	 * @param userId
	 */
	public void checkBindCardMissionAndCancel(Long userId);
	
	public void actionBindCheck(Long userId,String account,BindCardStatus status) throws Exception;
	
	public void bankCardLock(Long userId);
}
