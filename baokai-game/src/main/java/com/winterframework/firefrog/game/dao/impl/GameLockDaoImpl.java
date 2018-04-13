/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.game.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameLockDao;
import com.winterframework.firefrog.game.dao.vo.GameLock;
import com.winterframework.firefrog.game.web.dto.GameLockDataQueryRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 封锁参数 DAO 实现类
 * @author floy
 * @date 2014-4-1 下午4:15:08 
 */
@Repository("gameLockDaoImpl")
public class GameLockDaoImpl extends BaseIbatis3Dao<GameLock> implements IGameLockDao {

	@Override
	public GameLock queryGameLock(Long gameId) throws Exception {
		return this.sqlSessionTemplate.selectOne("com.winterframework.firefrog.game.dao.vo.GameLock.getByLotteryId", gameId);
	}
	
	@Override
	public List<String> queryGameLockTotalRetPoint(
			GameLockDataQueryRequest lockdata) throws Exception {
		return this.sqlSessionTemplate.selectList("com.winterframework.firefrog.game.dao.vo.GameLock.getTotalRetPoint", lockdata);
	}
}
