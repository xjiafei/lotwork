package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.entity.GameOrderOperationsEntity;
import com.winterframework.firefrog.game.entity.GamePlanOperationsEntity;
import com.winterframework.firefrog.game.entity.GameSlipOperationsEntity;
import com.winterframework.firefrog.game.web.dto.GamePlanOperationsQueryDTO;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName: IGamePlanOperationsService 
* @Description: 追号记录后台运营Service
* @author Alan
* @date 2013-10-14 下午3:33:35 
*  
*/
public interface IGamePlanOperationsService {
	
	/**
	 * 
	* @Title: queryGamePlanOperations 
	* @Description: 追号记录后台运营分页列表
	* @param queryDTO
	* @return Page<GamePlanOperationsEntity>
	* @throws Exception
	 */
	public Page<GamePlanOperationsEntity> queryGamePlanOperations(PageRequest<GamePlanOperationsQueryDTO> queryDTO) throws Exception;
	
	/**
	 * 
	* @Title: queryGamePlanOperationsList 
	* @Description: 追号记录运营数据列表
	* @param queryDTO
	* @return List<GamePlanOperationsEntity>
	* @throws Exception
	 */
	public List<GamePlanOperationsEntity> queryGamePlanOperationsList(GamePlanOperationsQueryDTO queryDTO) throws Exception;
	
	/**
	 * 
	* @Title: queryGamePlanOperationsDetail 
	* @Description: 查询追号记录详情 
	* @param planid
	* @return GamePlanOperationsEntity
	* @throws Exception
	 */
	public GamePlanOperationsEntity queryGamePlanOperationsDetail(Long planid) throws Exception;
	
	/**
	 * 
	* @Title: querySlipOperationsListByPlanID 
	* @Description: 查询注单列表
	* @param planid
	* @return List<GameSlipOperationsEntity>
	* @throws Exception
	 */
	public List<GameSlipOperationsEntity> querySlipOperationsListByPlanID(Long planid) throws Exception;
	
	/**
	 * 
	* @Title: queryOrderOperationsListByPlanID 
	* @Description: 查询订单列表
	* @param planid
	* @return List<GameOrderOperationsEntity>
	* @throws Exception
	 */
	public List<GameOrderOperationsEntity> queryOrderOperationsListByPlanID(Long planid) throws Exception;
	
	
}
