package com.winterframework.firefrog.user.dao;

import java.util.List;

import com.winterframework.firefrog.user.entity.GameAwardUserGroupDTO;
import com.winterframework.firefrog.user.entity.GameAwardUserGroupVo;

/** 
 * 类功能说明:奖金组Dao层
 */
public interface IGameAwardUserGroupDao {
	
	/**
	 * 方法描述：更新奖金组至預設值，BetType=0
	 * @throws Exception
	 */
	public void updateGameAwardGruopBetType(GameAwardUserGroupDTO gameAwardUserGroupDTO) throws Exception;
	
	public List<GameAwardUserGroupVo> queryGameAwardGruopByUserId(GameAwardUserGroupDTO gameAwardUserGroupDTO) throws Exception;
	
}
