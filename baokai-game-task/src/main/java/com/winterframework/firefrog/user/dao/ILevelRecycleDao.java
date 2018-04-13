package com.winterframework.firefrog.user.dao;

import java.util.List;

import com.winterframework.firefrog.user.entity.LevelRecycle;
import com.winterframework.firefrog.user.entity.LevelRecycleDTO;

/** 
 * 类功能说明:一代回收Dao层
 */
public interface ILevelRecycleDao {
	
	/**
	 * 
	 * 方法描述：一代回收纪录
	 * @param levelRecycleDTO
	 * @throws Exception
	 */
	public List<LevelRecycle> queryLevelRecycleHistory(LevelRecycleDTO levelRecycleDTO) throws Exception;
	
	/**
	 * 
	 * 方法描述：一代回收申请
	 * @param levelRecycleDTO
	 * @throws Exception
	 */
	public void applyLevelRecycle(LevelRecycleDTO levelRecycleDTO) throws Exception;
	
	/**
	 * 
	 * 方法描述：更新recycle状态
	 * @throws Exception
	 */
	public void updateRecycleStatus (LevelRecycleDTO levelRecycleDTO) throws Exception;
		
}
