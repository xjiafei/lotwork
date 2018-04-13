package com.winterframework.firefrog.game.dao;

import com.winterframework.firefrog.schedule.dto.GameAwardUserGroupDTO;

/** 
 * 类功能说明:奖金组Dao层
 */
public interface IGameAwardUserGroupDao {
	
	/**
	 * 方法描述：更新奖金组至預設值，BetType=0
	 * @throws Exception
	 */
	public void updateGameAwardGruopBetType(GameAwardUserGroupDTO gameAwardUserGroupDTO) throws Exception;
	
}
