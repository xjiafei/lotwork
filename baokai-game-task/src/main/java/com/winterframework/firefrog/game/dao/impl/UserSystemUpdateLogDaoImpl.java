package com.winterframework.firefrog.game.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IUserSystemUpdateLogDao;
import com.winterframework.firefrog.game.dao.vo.UserSystemUpdateLog;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("userSystemUpdateLogDaoImpl")
public class UserSystemUpdateLogDaoImpl extends BaseIbatis3Dao<UserSystemUpdateLog> implements IUserSystemUpdateLogDao{

}
