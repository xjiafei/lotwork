package com.winterframework.firefrog.game.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameAwardGroupDao;
import com.winterframework.firefrog.game.dao.vo.GameAwardGroup;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameAwardGroupDaoImpl")
public class GameAwardGroupDaoImpl extends BaseIbatis3Dao<GameAwardGroup> implements IGameAwardGroupDao {
}
