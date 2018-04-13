 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.winterframework.firefrog.game.dao.impl;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameLockAppraiseDao;
import com.winterframework.firefrog.game.dao.vo.GameLockAppraise;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;


/**
 * 
* @ClassName: gameLockAppraiseDaoImpl 
* @Description: 变价配置 DAO 实现类
* @author floy
* @date 2014-4-1 下午4:15:08 
*
 */
@Repository("gameLockAppraiseDaoImpl")
public class GameLockAppraiseDaoImpl extends BaseIbatis3Dao<GameLockAppraise> implements IGameLockAppraiseDao{

	
	/**
	* Title: queryAllGameLockAppraise
	* Description:
	* @param lotteryId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.dao.IGameLockAppraiseDao#queryAllGameLockAppraise(java.lang.Long) 
	*/
	@Override
	public List<GameLockAppraise> queryAllGameLockAppraise(Long lotteryId) throws Exception {
		return this.sqlSessionTemplate.selectList("getAllGameLockAppraise", lotteryId);
	}

	/**
	* Title: deleteGameLockAppraise
	* Description:
	* @param ids
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.dao.IGameLockAppraiseDao#deleteGameLockAppraise(java.util.List) 
	*/
	@Override
	public int deleteGameLockAppraise(List<Long> ids) throws Exception {
		return this.sqlSessionTemplate.delete("deleteAllGameLockAppraise", ids);
	}

	/**
	* Title: updateStatus
	* Description:
	* @param gameLockAppraise
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.dao.IGameLockAppraiseDao#updateStatus(com.winterframework.firefrog.game.dao.vo.GameLockAppraise) 
	*/
	@Override
	public int updateStatus(GameLockAppraise gameLockAppraise) throws Exception {
		return this.sqlSessionTemplate.update("com.winterframework.firefrog.game.dao.vo.GameLockAppraise.update", gameLockAppraise);
	}

	/**
	* Title: queryCurrUserGameLockAppraise
	* Description:
	* @param lotteryId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.dao.IGameLockAppraiseDao#queryCurrUserGameLockAppraise(java.lang.Long) 
	*/
	@Override
	public GameLockAppraise queryCurrUseGameLockAppraise(Long lotteryId) throws Exception {
		return this.sqlSessionTemplate.selectOne("getCurrUserGameLockAppraise", lotteryId);
	}

	/**
	* Title: updateCurUser
	* Description:
	* @param lotterId
	* @throws Exception 
	* @see com.winterframework.firefrog.game.dao.IGameLockAppraiseDao#updateCurUser(java.lang.Long) 
	*/
	@Override
	public void updateCurUser(Long id) throws Exception {
		this.sqlSessionTemplate.update("updateCurUser", id);
	}


}
