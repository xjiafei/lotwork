package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.entity.GamePlanOperationsEntity;
import com.winterframework.firefrog.game.web.dto.GamePlanOperationsQueryDTO;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/** 
* @ClassName: IGamePlanOperationsDao 
* @Description: 追号记录运营后台Dao
* @author Alan
* @date 2013-10-12 上午10:13:26 
*  
*/
public interface IGamePlanOperationsDao extends BaseDao<GamePlan> {
	
	/**
	 * 
	* @Title: getGamePlanByPage 
	* @Description: 根据请求参数查询追号记录分页列表
	* @param pageRequest
	* @return Page<GamePlanOperationsEntity>
	 */
	public Page<GamePlanOperationsEntity> getGamePlanByPage(PageRequest<GamePlanOperationsQueryDTO> pageRequest);
	
	/**
	 * 
	* @Title: getGamePlanList 
	* @Description: 追号记录数据列表 
	* @param queryDTO
	* @return List<GamePlanOperationsEntity>
	 */
	public List<GamePlanOperationsEntity> getGamePlanList(GamePlanOperationsQueryDTO queryDTO);
	
	/**
	 * 
	* @Title: queryGamePlanOperationsDetail 
	* @Description: 根据planid查询追号记录详情
	* @param planid
	* @return GamePlanOperationsEntity
	 */
	public GamePlanOperationsEntity queryGamePlanOperationsDetail(Long planid); 
	
	
}
