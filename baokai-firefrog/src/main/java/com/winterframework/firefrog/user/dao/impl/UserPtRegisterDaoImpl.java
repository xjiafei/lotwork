package com.winterframework.firefrog.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.user.dao.IUserPtRegisterDao;
import com.winterframework.firefrog.user.dao.vo.UserPtRegister;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("userPtRegisterDaoImpl")
public class UserPtRegisterDaoImpl extends BaseIbatis3Dao<UserPtRegister>
		implements IUserPtRegisterDao {

	@Override
	public void insertPtRegister(UserPtRegister vo) {
		sqlSessionTemplate.insert("insertToPt", vo);
	}

	@Override
	public UserPtRegister getPtRegister(UserPtRegister vo) {
		return sqlSessionTemplate.selectOne("getUserPtRegister", vo);
	}

}
