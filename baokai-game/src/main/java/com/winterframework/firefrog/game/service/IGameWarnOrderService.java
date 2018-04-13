package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameWarnOrder;
import com.winterframework.firefrog.game.dao.vo.RiskOrders;
import com.winterframework.firefrog.game.entity.GameSpiteOperationsEntity;
import com.winterframework.firefrog.game.web.dto.GameSpiteOrderQueryRequestDTO;
import com.winterframework.firefrog.game.web.dto.GameWarnOrderQueryDTO;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName: IGameWarnOrderService 
* @Description: 风险记录查询Service
* @author Alan
* @date 2013-10-15 下午3:11:14 
*  
*/
public interface IGameWarnOrderService {
	
	/**
	 * 
	* @Title: queryGameWarnOrders 
	* @Description: 风险记录分页列表 
	* @param pr
	* @return Page<RiskOrders>
	* @throws Exception
	 */
	public Page<RiskOrders> queryGameWarnOrders(PageRequest<GameWarnOrderQueryDTO> pr) throws Exception;
	
	/**
	 * 
	* @Title: queryGameSpiteOrders 
	* @Description: 恶意记录分页列表 
	* @param pr
	* @return Page<GameSpiteOperationsEntity>
	* @throws Exception
	 */
	public Page<GameSpiteOperationsEntity> queryGameSpiteOrders(PageRequest<GameSpiteOrderQueryRequestDTO> pr) throws Exception;

	/**
	 * 
	* @Title: getUndealGameWarnOrderByPlanId 
	* @Description: 根据追号id获取风险订单表中待处理的订单
	* @param planId
	* @return List<GameWarnOrder>
	* @throws Exception
	 */
	public List<GameWarnOrder> getUndealGameWarnOrderByPlanId(Long planId)
			throws Exception;

}
