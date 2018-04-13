package com.winterframework.firefrog.user.dao;

import com.winterframework.firefrog.user.dao.vo.UserPtRegister;

public interface IUserPtRegisterDao {

	public void insertPtRegister(UserPtRegister vo);
	
	public UserPtRegister getPtRegister(UserPtRegister vo) ;
}
