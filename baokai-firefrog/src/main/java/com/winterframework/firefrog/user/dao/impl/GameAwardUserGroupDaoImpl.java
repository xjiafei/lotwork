package com.winterframework.firefrog.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.user.dao.IGameAwardUserGroupDao;
import com.winterframework.firefrog.user.dao.vo.GameAwardUserGroup;
import com.winterframework.firefrog.user.entity.GameAwardUserGroupDTO;
import com.winterframework.firefrog.user.entity.GameAwardUserGroupVo;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameAwardUserGroupDaoImpl")
public class GameAwardUserGroupDaoImpl extends BaseIbatis3Dao<GameAwardUserGroup> implements IGameAwardUserGroupDao{

	@Override
	public void updateGameAwardGruopBetType(
			GameAwardUserGroupDTO gameAwardUserGroupDTO) throws Exception {
		sqlSessionTemplate.update("updateGameAwardGruopBetType", gameAwardUserGroupDTO);		
	}
	
	@Override
	public List<GameAwardUserGroupVo> queryGameAwardGruopByUserId(GameAwardUserGroupDTO gameAwardUserGroupDTO) throws Exception{
		return sqlSessionTemplate.selectList("getAwardByUserId",gameAwardUserGroupDTO);
	}

}
