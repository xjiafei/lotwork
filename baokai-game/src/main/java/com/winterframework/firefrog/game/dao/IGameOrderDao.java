package com.winterframework.firefrog.game.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.entity.GameOrderOperationsEntity;
import com.winterframework.firefrog.game.entity.GameOrderUserBetInfoEntity;
import com.winterframework.firefrog.game.web.dto.GameLockDataQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderOperationsQueryDTO;
import com.winterframework.firefrog.game.web.dto.GameOrderQueryDTO;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/**
 * 游戏投注订单DAO接口 
 * @author Denny 
 * @date 2013-7-22 下午1:59:00 
 */
public interface IGameOrderDao extends BaseDao<GameOrder> {

	/**
	 * 查询订单列表
	 * @param pr
	 * @return
	 * @throws Exception
	 */
	public Page<com.winterframework.firefrog.game.entity.GameOrder> getOrders(PageRequest<GameOrderQueryDTO> pr)
			throws Exception;

	/**
	 * 保存订单数据
	 * @param gameOrder
	 * @return
	 */
	public Long saveGameOrder(GameOrder gameOrder);

	/**
	 * 按订单ID查询订单
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public com.winterframework.firefrog.game.entity.GameOrder getOrderById(long orderId) throws Exception;

	/**
	 * 根据追号计划ID来查询对于的订单列表 
	 * @param planId 追号计划ID  
	 * @return  
	 */
	public List<com.winterframework.firefrog.game.entity.GameOrder> getOrdersByPlanId(long planId);

	/**
	 * 撤销订单更新订单信息
	 * @param orderId
	 * @param issueCode
	 * @param cancelTime
	 */
	public void updateOrder(Long orderId, Long issueCode, Long cancelTime);

	/**
	 * 查询订单运营记录分页列表
	 * @param pr
	 * @return
	 * @throws Exception
	 */
	public Page<GameOrderOperationsEntity> getOrderOperations(PageRequest<GameOrderOperationsQueryDTO> pr)
			throws Exception;

	/**
	 * 查询订单运营列表
	 * @param queryDTO
	 * @return
	 * @throws Exception
	 */
	public List<GameOrderOperationsEntity> getOrderOperationsList(GameOrderOperationsQueryDTO queryDTO)
			throws Exception;

	/**
	 * 查询订单运营详细
	 * @param orderid
	 * @return
	 * @throws Exception
	 */
	public GameOrderOperationsEntity getOrderOperationsDetail(Long orderid) throws Exception;

	/**
	 * 查询订单运营数据列表
	 * @param planid
	 * @return
	 * @throws Exception
	 */
	public List<GameOrderOperationsEntity> queryOrderOperationsListByPlanID(Long planid) throws Exception;

	/**
	 * 获取奖期订单信息
	 * @param lotteryId
	 * @param issueCode
	 * @return
	 * @throws Exception
	 */
	public List<com.winterframework.firefrog.game.entity.GameOrder> queryOrderByLotteryIdAndIssueCode(Long lotteryId,
			Long issueCode) throws Exception;

	/**
	 * 根据订单id和用户id获取订单信息，防止后台用户直接通过订单id获取到其他用户的信息
	 * @param orderId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public com.winterframework.firefrog.game.entity.GameOrder getOrderByOrderIdAndUserId(long orderId, Long userId)
			throws Exception;

	/**
	 * 根据追号计划信息及奖期获取订单信息。 
	 * @param planId
	 * @param nextIssueCode
	 * @return
	 */
	public GameOrder getOrderByPlanIdAndIssueCode(Long planId, Long nextIssueCode);

	/**
	 * 根据orderCode获取gameOrderList
	 * @param orderCode
	 * @return
	 */
	public List<GameOrder> getGameOrderByOrderCode(String orderCode);

	/**
	 * 更新订单撤销状态。
	 * @param orderId
	 * @param issueCode
	 * @param cancelTime
	 * @param isFrontUser
	 * @return
	 */
	public int updateOrderCancel(Long orderId, Long issueCode, Long cancelTime, boolean isFrontUser);

	/**
	 * 根据orderId获取planId
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public Long getPlanIdByOrder(Long orderId) throws Exception;

	Long saveMMCGameOrder(GameOrder gameOrder);

	public Integer getCurrentIssueMutiple(String betDetail, String betTypeCode, long userId, long lotteryId,
			Long issueCode);
	
	public List<GameOrderUserBetInfoEntity> queryUserBetInfoByDate(Long userId, Date startDate)throws Exception;
	
	public List<String> queryBeginMissionOrder(Map<String,Object> params);
	
	public Long queryUserDailyBets(Long userId,List<String> betTypeCodes);
	
	public Long queryUserDailyPeriodBets(Long userId,Date beginTime,Date endTime, List<String> betTypeCodes);
	
	public void updateEndCanCancelTime(Long lotteryId, Long issueCode,Date newSaleEndTime);
	
	public GameOrder getWinningList(Long lotteryId,Long issueCode,Long userId);
	
	public List<com.winterframework.firefrog.game.entity.GameOrder> queryPlayerBet(long lotteryId, Long userId, Long issueCode) throws Exception;

	
	public int updateMMC(GameOrder order);
	
	/**
	 * 获取订单（根据追号明细ID--理想情况不存在多条(xml中先加上撤销过滤)) 
	 * @param planDetailId
	 * @return
	 */
	public GameOrder getOrderByPlanDetailId(Long planDetailId);
	
	/**
	 * 依據 lotteryid, issueCode 取得狀態為 1(等待開獎), 2(中獎), 3(未中獎) 的訂單總金額。
	 * @param lockdata
	 * @return
	 * @throws Exception
	 */
	public Long getTotamount(GameLockDataQueryRequest lockdata) throws Exception;
	/**
	 * 单用户单期总中奖金额
	 * @param lotteryId
	 * @param issueCode
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	Double getWinAmt(Long lotteryId,Long issueCode,Long userId);
}