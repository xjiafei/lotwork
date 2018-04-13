package com.winterframework.firefrog.beginmession.dao;

import com.google.common.base.Optional;
import com.winterframework.firefrog.beginmession.dao.vo.BeginMissionVer;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface BeginMissionVerDao extends BaseDao<BeginMissionVer>{

	public Optional<BeginMissionVer> findOneByCondition(BeginMissionVer missionVer);
	
	public BeginMissionVer findByUserId(Long userId);
}
