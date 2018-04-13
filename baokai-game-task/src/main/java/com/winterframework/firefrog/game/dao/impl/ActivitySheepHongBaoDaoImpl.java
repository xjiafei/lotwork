package com.winterframework.firefrog.game.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.IActivitySheepHongBaoDao;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepHongBao;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName ActivitySheepHongBaoDaoImpl 
* @Description 羊年活动红包 
* @author  hugh
* @date 2015年1月14日 下午3:30:56 
*  
*/
@Repository("activitySheepHongBaoDaoImpl")
public class ActivitySheepHongBaoDaoImpl  extends BaseIbatis3Dao<ActivitySheepHongBao> implements IActivitySheepHongBaoDao{
	public Long getUncheckNum(){
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getUncheckNum"));
	}
	
	public Long getUserLastDateBet(Long userId,Date sighDate){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("sighDate", sighDate);
		String yesterday =DateUtils.format(DateUtils.getYesterday() ,DateUtils.DATE_FORMAT_PATTERN) ;
		map.put("beginDate", DateUtils.parse(yesterday+" 00:00:00", DateUtils.DATETIME_FORMAT_PATTERN));
		map.put("endDate", DateUtils.parse(yesterday+" 23:59:59", DateUtils.DATETIME_FORMAT_PATTERN));				
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getUserLastDateBet"),map);
	}
}
