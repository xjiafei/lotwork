package com.winterframework.firefrog.game.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameOrderLogDao;
import com.winterframework.firefrog.game.dao.vo.GameOrderLog;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;


/**
 * 订单操作DAO实现类
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月10日
 */
@Repository("gameOrderLogDaoImpl")
public class GameOrderLogDaoImpl extends BaseIbatis3Dao<GameOrderLog> implements IGameOrderLogDao {
}
