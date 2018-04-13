package com.winterframework.firefrog.active.dao;

import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.active.dao.vo.Activity;
import com.winterframework.firefrog.active.dao.vo.ActivityWinningLog;
import com.winterframework.firefrog.active.web.dto.ActivityAwardsRequest;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.modules.page.PageRequest;
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
	
	/**
	 * 查詢需要人工派獎的活動資訊
	 * activityType=9999, status=1
	 * param String type
	 * return List<Activity>
	 */
	public List<Activity> getActivityDetail(String type);
	
	/**
	 * 查詢未派獎的得獎名單
	 * param  PageRequest<ActivityAwardsRequest> page
	 * return CountPage<ActivityWinningLog>
	 */
	public CountPage<ActivityWinningLog> getAwardsUntreadList(PageRequest<ActivityAwardsRequest> page);
	
	/**
	 * 查詢已派獎的得獎名單
	 * param  PageRequest<ActivityAwardsRequest> page
	 * return CountPage<ActivityWinningLog>
	 */
	public CountPage<ActivityWinningLog> getAwardsTreadList(PageRequest<ActivityAwardsRequest> page);
	
	
	
}
