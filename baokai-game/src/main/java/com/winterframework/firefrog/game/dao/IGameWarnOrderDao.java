package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameWarnOrder;
import com.winterframework.firefrog.game.dao.vo.RiskOrderDetail;
import com.winterframework.firefrog.game.dao.vo.RiskOrders;
import com.winterframework.firefrog.game.dao.vo.SpiteOrders;
import com.winterframework.firefrog.game.entity.GameSpiteOperationsEntity;
import com.winterframework.firefrog.game.entity.GameWarnOrderEntity;
import com.winterframework.firefrog.game.web.dto.GameSpiteOrderQueryRequestDTO;
import com.winterframework.firefrog.game.web.dto.GameWarnDetailQueryDto;
import com.winterframework.firefrog.game.web.dto.GameWarnOrderQueryDTO;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/**
 * 
* @ClassName: IGameWarnOrderDao 
* @Description:订单警告表 
* @author Richard
* @date 2013-10-12 下午3:00:07 
*
 */
public interface IGameWarnOrderDao extends BaseDao<GameWarnOrder> {
	
	/**
	 * 
	* @Title: queryGameWarnOrderByLotteryIdAndIssueCode 
	* @Description: 获取订单警告信息 
	* @param lotteryId
	* @param issueCode
	* @return
	* @throws Exception
	 */
	public Page<RiskOrders> queryGameWarnOrderByLotteryIdAndIssueCode(PageRequest<GameWarnDetailQueryDto> pageRequest) throws Exception;
	
	/**
	 * 
	* @Title: querySpiteOrderByLotteryIdAndIssueCode 
	* @Description: 查询恶意方案
	* @param lotteryId
	* @param issueCode
	* @return
	 */
	public List<SpiteOrders> querySpiteOrderByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) throws Exception;
	
	/**
	 * 
	* @Title: queryGameWarnOrderDetail 
	* @Description: 订单警告表
	* @param lotteryId
	* @param issueCode
	* @return
	* @throws Exception
	 */
	public List<GameWarnOrderEntity> queryGameWarnOrderDetail(Long lotteryId, Long issueCode) throws Exception;

	/**
	 * 
	* @Title: queryGameWarnOrderByPage 
	* @Description: 查询风险记录分页列表
	* @param pr
	* @return
	* @throws Exception
	 */
	public Page<RiskOrders> queryGameWarnOrderByPage(PageRequest<GameWarnOrderQueryDTO> pr) throws Exception;
	
	/**
	 * 
	* @Title: queryGameSpiteOrders 
	* @Description: 查询恶意记录分页列表 
	* @param pr
	* @return
	* @throws Exception
	 */
	public Page<GameSpiteOperationsEntity> queryGameSpiteOrders(PageRequest<GameSpiteOrderQueryRequestDTO> pr) throws Exception;
	
	/**
	 * 
	* @Title: queryGameWarnOrderByLotteryIdIssueCodeUserId 
	* @Description:用户订单详情信息
	* @param lotteryId
	* @param issueCode
	* @param userid
	* @return
	* @throws Exception
	 */
	public List<RiskOrderDetail> queryGameWarnOrderByLotteryIdIssueCodeUserId(Long lotteryId, Long issueCode, Long userid) throws Exception;
	
	/**
	 * 
	* @Title: updateGameWarnOrderByLotteryIdAndIssueCode 
	* @Description: 根据状态更新订单审核信息
	* @param lotteryId
	* @param issueCode
	* @param status
	* @throws Exception
	 */
	public void updateGameWarnOrderByLotteryIdAndIssueCode(Long lotteryId, Long issueCode, Integer status) throws Exception;
	
	/**
	 * 
	* @Title: updateGameWarnOrderByOrderCode 
	* @Description: 更新订单审核信息 
	* @param orderCode
	* @param status
	* @throws Exception
	 */
	public void updateGameWarnOrderByOrderCode(String orderCode, Integer status) throws Exception;

	/**
	 * 
	* @Title: getGameWarnOrderByOrderCode 
	* @Description: 异常订单信息。
	* @param orderCode
	* @return
	* @throws Exception
	 */
	public GameWarnOrder getGameWarnOrderByOrderCode(String orderCode) throws Exception;

	/**根据追号id获取风险订单表中待处理的订单
	 * @param planId
	 * @return
	 * @throws Exception
	 */
	public List<GameWarnOrder> getUndealGameWarnOrderByPlanId(Long planId)
			throws Exception;
}
