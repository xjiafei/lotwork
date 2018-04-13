package com.winterframework.firefrog.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.user.dao.IImGroupDao;
import com.winterframework.firefrog.user.entity.ImGroup;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("imGroupDaoImpl")
public class ImGroupDaoImpl extends BaseIbatis3Dao<ImGroup> implements IImGroupDao {

	@Override
	public List<ImGroup> queryImGroups(ImGroup request) {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("queryImGroups"),request);
	}

	@Override
	public ImGroup queryImGroup(ImGroup request) {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("queryImGroup"),request);
	}

	@Override
	public Long insertImGroup(ImGroup request) {
		return (long) this.sqlSessionTemplate.insert(this.getQueryPath("insertImGroup"),request);
	}

}

