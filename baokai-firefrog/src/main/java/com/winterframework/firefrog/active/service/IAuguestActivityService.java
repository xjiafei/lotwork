package com.winterframework.firefrog.active.service;

import java.util.Date;

import com.winterframework.firefrog.active.entity.ActivityAuguest;

/**
 * 2016 八月活動
 * @author David.Wu
 *
 */
public interface IAuguestActivityService {
	
	/**
	 * 取得活動資料
	 * @param activityEntity
	 * @return
	 */
	public ActivityAuguest getAcvitityByCode(ActivityAuguest activityEntity);
	
	/**
	 * 取得活動設定檔
	 * @param activityEntity 傳入 活動ID 與 設定檔規則代碼
	 * @return
	 */
	public ActivityAuguest getActivityConfig(ActivityAuguest activityEntity);
	
	/**
	 * 取得使用者今日投注金額
	 * @param activityEntity 傳入用戶ID
	 * @return
	 */
	public Long getAgentAmount(ActivityAuguest activityEntity);
	
	/**
	 * 計算目前餘額(仍需投注)
	 * 需有中獎金額才能計算
	 * 時間範圍:今日
	 * 用戶已給獎
	 * @param activityEntity userId 用戶ID ,activityId 活動ID, prize 用戶今日中獎金額
	 * @return
	 */
	public ActivityAuguest cuntShortAmount(ActivityAuguest activityEntity);
	
	
	/**
	 * 取得用戶今日中獎金額
	 * 取得用戶是否已中獎
	 * @param activityEntity userId 用戶ID ,activityId 活動ID
	 * @return
	 */
	public ActivityAuguest getAgentPrize(ActivityAuguest activityEntity);
	
	/**
	 * 給獎(含大獎)
	 * 確認用戶參加資格
	 * 確認紅包數量
	 * 確認用戶是否已給獎
	 * 計算目前餘額(仍需投注)
	 * 時間範圍:今日
	 * @param activityEntity
	 * @return
	 */
	public ActivityAuguest setPrize(ActivityAuguest activityEntity);
	
	/**
	 * 初始化資料
	 * 確認是否為活動期間
	 * 確認遊戲是否開始或結束 20:00~22:00
	 * 確認用戶參加資格
	 * 確認用戶是否已中獎
	 * 確認紅包數量
	 * 若用戶已中獎，計算仍需投注金額
	 * 
	 * @return
	 */
	public ActivityAuguest setDefaultParam(ActivityAuguest activityEntity);
	
	/**
	 * 計算今日中大獎數量
	 * @return
	 */
	public ActivityAuguest cuntAwards(ActivityAuguest activityEntity);
	
	/**
	 * 計算是否為活動期間
	 * @param activityEntity
	 * @return
	 */
	public boolean checkIsActPeriod(ActivityAuguest activityEntity);
	
	/**
	 * 確認參加資格
	 * 1. 活動白名單內用戶
	 * 2. 當日投注金額  >= 100 元 
	 * @param activityEntity
	 * @return
	 */
	public ActivityAuguest checkQualification(ActivityAuguest activityEntity);
	
	/**
	 * 計算紅包剩餘數量
	 * @param activityEntity
	 * @return
	 */
	public ActivityAuguest cuntQualification(ActivityAuguest activityEntity);
	
	/**
	 * LOG 紀錄
	 * @param activityEntity
	 */
	public void activityLog(ActivityAuguest activityEntity);
	
	/**
	 * 取得時間
	 * @param hourOfday
	 * @param second
	 * @param minute
	 * @param millisec
	 * @return
	 */
	public Date getWantedTime(int day, int hourOfday, int second, int minute,
			int millisec);
	
	public void saveAwards(ActivityAuguest activityEntity);
	
	/**
	 * 取得使用者帳號
	 * @param token
	 * @return
	 */
	public String getUserNameByToken(String token);

}
