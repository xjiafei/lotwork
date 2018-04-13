package com.winterframework.firefrog.beginmession.dao;

import java.util.List;

import com.winterframework.firefrog.beginmession.dao.vo.BeginAward;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface BeginAwardDao extends BaseDao<BeginAward>{

	public List<BeginAward> findByCondition(BeginAward award);
	
	
	public BeginAward findFirstByCondition(BeginAward award);
	
	public void updateAwardStatus(BeginAward award);
	
	public BeginAward getNowDailyAnsAward(Long userId);
	
	public Long getAwardNowByMissionId(BeginAward award);
	
}
