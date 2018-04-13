package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.user.dao.vo.LevelRecycle;
import com.winterframework.firefrog.user.entity.LevelRecycleDTO;

/** 
 * 类功能说明:一代回收Dao层
 */
public interface IGameLevelRecycleDao {
	
	/**
	 * 
	 * 方法描述：一代回收纪录
	 * @param freeze
	 * @throws Exception
	 */
	public List<LevelRecycle> queryLevelRecycleHistory(LevelRecycleDTO levelRecycleDTO) throws Exception;

}
