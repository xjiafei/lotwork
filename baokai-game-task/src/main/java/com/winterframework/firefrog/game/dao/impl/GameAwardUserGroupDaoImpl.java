package com.winterframework.firefrog.game.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameAwardUserGroupDao;
import com.winterframework.firefrog.game.vo.GameAwardUserGroup;
import com.winterframework.firefrog.schedule.dto.GameAwardUserGroupDTO;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameAwardUserGroupDaoImpl")
public class GameAwardUserGroupDaoImpl extends BaseIbatis3Dao<GameAwardUserGroup> implements IGameAwardUserGroupDao{

	@Override
	public void updateGameAwardGruopBetType(
			GameAwardUserGroupDTO gameAwardUserGroupDTO) throws Exception {
		sqlSessionTemplate.update("updateGameAwardGruopBetType", gameAwardUserGroupDTO);		
	}

}
