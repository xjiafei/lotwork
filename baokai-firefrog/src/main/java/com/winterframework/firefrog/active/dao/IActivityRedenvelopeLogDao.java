package com.winterframework.firefrog.active.dao;

import java.util.List;

import com.winterframework.firefrog.active.dao.vo.ActivityDoubleboxLog;
import com.winterframework.firefrog.active.dao.vo.ActivityRedenvelopeLog;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IActivityRedenvelopeLogDao  extends BaseDao<ActivityRedenvelopeLog>{
	void saveActivityDoubleboxLog(ActivityRedenvelopeLog log);
	public Long queryTodayCountgByVipLvl(Integer vipLvl, Long activityId);
	public Long queryTodayLottoCount(Integer vipLvl, Long activityId,Long Lotto_prize);
	public ActivityRedenvelopeLog queryTodayRedEnvelopeByUserId(Long activityId, Long userId);
		
}
