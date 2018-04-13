package com.winterframework.firefrog.active.dao;

import java.util.Map;

import com.winterframework.firefrog.active.dao.vo.Activity;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IActivityDao extends BaseDao<Activity>{
	
	/**
	 * 取得活動主表資料 by 活動代碼
	 * @param code 活動代碼
	 * @return Activity
	 */
	public Activity getActivityByCode(Activity actVO);
	
	/**
	 * 計算用戶今日投注額(已開獎訂單)
	 * 骰寶 單雙不列入計算
	 * 超級2000系列僅計算 70%
	 * PT遊戲不列入計算
	 * @param actEntity
	 * @return
	 */
	public Long getUserAmountPerDay(Map<String, Long> amountMap);
	
}
