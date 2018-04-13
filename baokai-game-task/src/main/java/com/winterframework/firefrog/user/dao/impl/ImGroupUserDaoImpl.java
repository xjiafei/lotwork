package com.winterframework.firefrog.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.user.dao.IImGroupUserDao;
import com.winterframework.firefrog.user.entity.ImGroupUser;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("imGroupUserDaoImpl")
public class ImGroupUserDaoImpl extends BaseIbatis3Dao<ImGroupUser> implements IImGroupUserDao {

	@Override
	public List<ImGroupUser> queryImGroupUsers(ImGroupUser request) {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("queryImGroupUsers"),request);
	}

	@Override
	public Long queryImGroupUserCount(ImGroupUser request) {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("queryImGroupUserCount"),request);
	}

	@Override
	public ImGroupUser queryImGroupUser(ImGroupUser request) {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("queryImGroupUser"),request);
	}

	@Override
	public Long insertImGroupUser(ImGroupUser request) {
		return (long) this.sqlSessionTemplate.insert(this.getQueryPath("insertImGroupUser"),request);
	}

	@Override
	public Long updateImGroupUser(ImGroupUser request) {
		return (long) this.sqlSessionTemplate.update(this.getQueryPath("updateImGroupUser"),request);
	}

	@Override
	public Long addImGroupUserWithoutOwner(ImGroupUser request){
		return (long) this.sqlSessionTemplate.update(this.getQueryPath("addImGroupUserWithoutOwner"),request);
	}

	@Override
	public Long updateImGroupUserByUserId(ImGroupUser request) {
		return (long) this.sqlSessionTemplate.update(this.getQueryPath("updateImGroupUserByUserId"),request);
	}

}

