package com.winterframework.firefrog.shortlived.sheepactivity.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.shortlived.sheepactivity.dao.IActivitySheepHongBaoDao;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepHongBao;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepHongBaoCount;
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
	@Override
	public List<ActivitySheepHongBao> getUserHongBaoList(Long userId) {
		return this.sqlSessionTemplate.selectList("getUserHongBaoList", userId);
	}

	@Override
	public void applyUserHongbao(Map<String, Object> map) {
		 this.sqlSessionTemplate.update("applyUserHongbao", map);
	}

	@Override
	public Long getUserValideBetAmount(Map<String, Object> map) {
		return this.sqlSessionTemplate.selectOne("getUserValideBetAmount", map);
	}

	@Override
	public void abortUserHongBao(Map<String, Object> map) {
		this.sqlSessionTemplate.update("abortUserHongBao", map);
		
	}

	@Override
	public void openUserNextHongBao(Map<String, Object> map) {
		this.sqlSessionTemplate.update("openUserHongBao", map);
		
	}

	@Override
	public void drawUserHongBao(Map<String, Object> map) {
		this.sqlSessionTemplate.update("drawUserHongBao", map);
		
	}
	
	@Override
	public ActivitySheepHongBao getUserHongBaoInfo(Map<String, Object> map) {
		return this.sqlSessionTemplate.selectOne("getUserHongBaoInfo", map);
		
	}

	public List<ActivitySheepHongBaoCount> getCounts(Date beginDate,Date endDate,boolean isGroupByChannel){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("beginTime", beginDate);
		map.put("endTime", endDate);
		if(isGroupByChannel){
			map.put("isGroupByChannel", "isGroupByChannel");
		}else{
			map.put("isGroupByChannel", null);
		}
		return this.sqlSessionTemplate.selectList(  this.getQueryPath("getSheepHongBaoCounts"), map);
	}
	@Override
	public ActivitySheepHongBao getUserValidHongBaoInfo(Long userId) {
		return this.sqlSessionTemplate.selectOne("getUserValidHongBaoInfo", userId);
	}
}
