package com.winterframework.firefrog.beginmession.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.google.common.base.Optional;
import com.winterframework.firefrog.beginmession.dao.BeginMissionVerDao;
import com.winterframework.firefrog.beginmession.dao.vo.BeginMissionVer;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 
 * @author Ami.Tsai
 *
 */
@Repository
public class BeginMissionVerDaoImpl extends BaseIbatis3Dao<BeginMissionVer> implements BeginMissionVerDao{

	@Override
	public Optional<BeginMissionVer> findOneByCondition(BeginMissionVer missionVer) {
		
		List<BeginMissionVer> lists = this.sqlSessionTemplate.selectList(this.getQueryPath("findByCondition"),missionVer);
		BeginMissionVer ver = null;
		if(lists!=null && lists.size()>0){
			ver = lists.get(0);
		}
		
		return Optional.fromNullable(ver);
	}

	@Override
	public BeginMissionVer findByUserId(Long userId) {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("findByUserId"), userId);
	}

}
