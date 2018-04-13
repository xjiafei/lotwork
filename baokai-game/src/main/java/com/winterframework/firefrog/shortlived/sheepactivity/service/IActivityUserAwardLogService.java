package com.winterframework.firefrog.shortlived.sheepactivity.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.ActivityUserAwardLog;

/** 
* @ClassName IActivityUserAwardLogService 
* @Description 用户活动中奖日志 
* @author  hugh
* @date 2014年12月2日 下午3:34:02 
*  
*/
public interface IActivityUserAwardLogService {
	List<ActivityUserAwardLog> getToday() throws Exception;
	List<ActivityUserAwardLog> getUserAward(Long userId) throws Exception;
	void getDailyBetPrize(long userId, String betDate, Long money, Integer channel)throws Exception;
}
