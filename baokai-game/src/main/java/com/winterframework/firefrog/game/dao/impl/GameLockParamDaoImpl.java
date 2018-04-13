 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.winterframework.firefrog.game.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameLockParamDao;
import com.winterframework.firefrog.game.dao.vo.GameLockParam;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 
* @ClassName: gameLockDaoImpl 
* @Description: 变价参数 DAO 实现类
* @author floy
* @date 2014-4-1 下午4:15:08 
*
 */
@Repository("gameLockParamDaoImpl")
public class GameLockParamDaoImpl extends BaseIbatis3Dao<GameLockParam> implements IGameLockParamDao{

	/**
	* Title: queryGameLockParam
	* Description:
	* @param gameId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.dao.IGameLockParamDao#queryGameLockParam(java.lang.Long) 
	*/
	@Override
	public GameLockParam queryGameLockParam(Long gameId) throws Exception {
		return this.sqlSessionTemplate.selectOne("queryGameLockParam", gameId);
	}
	
}
