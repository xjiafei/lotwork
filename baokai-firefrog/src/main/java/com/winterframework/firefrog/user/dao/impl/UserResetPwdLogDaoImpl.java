package com.winterframework.firefrog.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.user.dao.IUserResetPwdLogDao;
import com.winterframework.firefrog.user.dao.vo.UserResetPwdLogRequestVo;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("userResetPwdLogDaoImpl")
public class UserResetPwdLogDaoImpl extends
		BaseIbatis3Dao<UserResetPwdLogRequestVo> implements IUserResetPwdLogDao {

	@Override
	public void insertUserResetPwdLog(UserResetPwdLogRequestVo vo) {
		this.sqlSessionTemplate.insert("insertUserResetPwdLog", vo);
	}
}
