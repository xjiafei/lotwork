package com.winterframework.firefrog.game.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameWarnOrderLogDao;
import com.winterframework.firefrog.game.dao.vo.GameWarnOrderLog;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 
* @ClassName: GameWarnOrderLogDaoImpl 
* @Description: GameWarnOrder Log Impl
* @author Richard
* @date 2014-3-3 下午1:52:14 
*
 */
@Repository("gameWarnOrderLogDaoImpl")
public class GameWarnOrderLogDaoImpl extends BaseIbatis3Dao<GameWarnOrderLog> implements IGameWarnOrderLogDao {


}
