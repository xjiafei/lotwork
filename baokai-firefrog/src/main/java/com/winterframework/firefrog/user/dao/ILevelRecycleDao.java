package com.winterframework.firefrog.user.dao;

import com.winterframework.firefrog.user.dao.vo.LevelRecycle;
import com.winterframework.firefrog.user.entity.LevelRecycleDTO;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
 * 类功能说明:一代回收Dao层
 */
public interface ILevelRecycleDao {
	
	/**
	 * 
	 * 方法描述：一代回收纪录
	 * @param freeze
	 * @throws Exception
	 */
	public Page<LevelRecycle> queryLevelRecycleHistory(PageRequest<LevelRecycleDTO> pageReqeust) throws Exception;
	
	/**
	 * 
	 * 方法描述：一代回收申请
	 * @param freeze
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
