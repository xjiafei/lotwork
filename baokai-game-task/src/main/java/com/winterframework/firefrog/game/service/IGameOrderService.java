package com.winterframework.firefrog.game.service;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GamePackageItem;
import com.winterframework.firefrog.game.entity.GamePackage.GamePackageType;

/**
 * 
* @ClassName: IGameOrderService 
* @Description: 游戏订单信息
* @author Richard
* @date 2013-11-18 下午3:25:26 
*
 */
public interface IGameOrderService {

	////////////////////////行为----begin//////////////////////////
	public int save(GameContext ctx,GameOrder order) throws Exception;
	
	/**
	 * 恢复订单
	 * @param ctx
	 * @param gameOrder
	 * @throws Exception
	 */
	public int reset(GameContext ctx,GameOrder order) throws Exception;
	public int reset_tmp(GameContext ctx,GameOrder order) throws Exception;
	 
	/**
	 * 撤销订单
	 * @param ctx
	 * @param orderId
	 * @return 实际撤销数目
	 * @throws Exception
	 */
	public int cancel(GameContext ctx,Long orderId) throws Exception;
	public int cancel(GameContext ctx,GameOrder order) throws Exception; 
	/**
	 * 异常
	 * @param ctx
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public int excep(GameContext ctx,GameOrder order) throws Exception;
	/**
	 * 开奖
	 * @param ctx
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public int draw(GameContext ctx,GameOrder order) throws Exception;  
	////////////////////////行为----end//////////////////////////
	////////////////////////业务服务----begin//////////////////////////
	int addOrderLog(GameContext ctx,GameOrder order,String operation,String remark) throws Exception;  
	List<GameOrder> getByLotteryIdAndIssueCode(GameContext ctx,Long lotteryId,Long issueCode) throws Exception;
	List<GameOrder> getPlanByLotteryIdAndIssueCode(GameContext ctx,Long lotteryId,Long issueCode) throws Exception;
	GameOrder getOnePlanByLotteryIdAndIssueCode(GameContext ctx,Long lotteryId,Long issueCode) throws Exception;
	////////////////////////业务服务----end//////////////////////////
	/**
	 * 
	* @Title: getGameOrderByLotteryIdAndIssueCode 
	* @Description: 获取订单信息
	* @param lotteryId
	* @param issueCode
	* @return
	* @throws Exception
	 */
	public List<GameOrder> getGameOrderByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) throws Exception;

	/**
	* 
	* @Title: updateOrders 
	* @Description: 修改该奖期所有订单 （等待开奖、撤销）
	* @param lotteryid
	* @param issueCode
	* @param status
	* @throws Exception
	*/
	public void updateOrders(Long lotteryid, Long issueCode, Integer status) throws Exception;

	/**
	 * 
	* @Title: revocationWinOrders 
	* @Description: 撤销该奖期所有中奖订单
	* @param lotteryid
	* @param issueCode
	* @throws Exception
	 */
	public void revocationWinOrders(Long lotteryid, Long issueCode) throws Exception;

	/**
	 * 
	* @Title: updateGameOrder 
	* @Description: 更新订单表信息
	* @param order
	* @throws Exception
	 */
	public void updateGameOrder(GameOrder order) throws Exception;

	/**
	 * 
	* @Title: updateGameOrder 
	* @Description: 更新订单表信息
	* @param order
	* @throws Exception
	 */
	public void updateGameOrderMMC(GameOrder order) throws Exception;
	
	/**
	 * 
	* @Title: getGameOrderById 
	* @Description: 获取游戏订单信息。
	* @param orderId
	* @return
	* @throws Exception
	 */
	public GameOrder getGameOrderById(Long orderId) throws Exception;

	/**
	 * 
	* @Title: getGameOrderByPreGameIssueAndLotteryId 
	* @Description: 获取订单信息，状态不为存在异常，数据归档
	* @param issueCode
	* @param lotteryId
	* @return
	 */
	public List<com.winterframework.firefrog.game.dao.vo.GameOrder> getGameOrderByPreGameIssueAndLotteryId(
			Long issueCode, Long lotteryId) throws Exception;

	/**
	 * 
	* @Title: getGameOrderListByLotteryAndIssueCode 
	* @Description: 根据LotteryId和issueCode 获取中奖或未中奖订单信息。
	* @param lotteryId
	* @param issueCode
	* @return
	* @throws Exception
	 */
	public List<GameOrder> getGameOrderListByLotteryAndIssueCode(Long lotteryId, Long issueCode) throws Exception;

	/**
	* 
	* @Title: updateOrders 
	* @Description: 修改起始期号-结束期号所有订单 （撤销）
	* @param lotteryid
	* @param startIssueCode
	* @param endIssueCode
	* @param status
	* @throws Exception
	*/
	public void updateOrders(Long lotteryid, Long startIssueCode, Long endIssueCode, Integer status) throws Exception;

	/** 
	* @Title: selectGameOrderByPlanIdAndIssueCode 
	* @Description: 根据计划ID和奖期号查询对应的GameOrder
	* @param planid
	* @param issueCode
	* @return GameOrder
	*/
	public GameOrder selectGameOrderByPlanIdAndIssueCode(Long planid, Long issueCode);

	/**
	 * 
	* @Title: selectGameOrderByPlanid 
	* @Description: 根据计划ID查询对应的GameOrder列表
	* @param planid
	* @return List<GameOrder>
	 */
	public List<GameOrder> selectGameOrderByPlanid(Long planid);

	/** 
	* @Title: selectOrderWinByOrderCode 
	* @Description: 根据订单编号查找中奖金额
	* @param orderCode
	* @return
	*/
	public Long selectOrderWinByOrderCode(String orderCode);

	/** 
	* @Title: selectFollowGameOrdersByPlanid 
	* @Description: 根据planid查询orderid之后生成的订单列表
	* @param planid
	* @param orderid
	* @return List<GameOrder>
	*/
	public List<GameOrder> selectFollowGameOrdersByPlanid(Long planid, Long orderid);

	/** 
	* @Title: getGameOrderByLotteryIdAndIssueCodeStatus 
	* @Description: 根据彩种id，奖期，已经状态获取对应的列表
	* @param lotteryId 彩种id
	* @param issueCode 奖期
	* @param status 状态集合
	* @return
	*/
	public List<GameOrder> getGameOrderByLotteryIdAndIssueCodeStatus(Long lotteryId, Long issueCode,
			List<Integer> status);

	/** 
	* @Title: undoGameOrders 
	* @Description: 撤销订单
	* @param lotteryId
	* @param issueCode
	* @param saleDate
	 * @param orderStatus 
	*/
	public void undoRedoGameOrders(Long lotteryId, Long issueCode, Date saleDate, int orderStatus);

	/** 
	* @Title: getCalculateTimeByLotteryIdAndIssueCode 
	* @Description: 根据彩种和奖期获取计奖时间
	* @param lotteryId
	* @param issueCode
	* @return
	*/
	public Date getCalculateTimeByLotteryIdAndIssueCode(Long lotteryId, Long issueCode);

	/** 
	* @Title: getGameOrderByLotteryIdAndIssueCodeAndSaleTime 
	* @Description: 根据开奖时间，彩种以及奖期获取订单
	* @param lotteryId
	* @param issueCode
	* @param saleTime
	* @param aimStatus 查询指定状态列表的订单
	* @return
	*/
	public List<GameOrder> getGameOrderByLotteryInfo(Long lotteryId, Long issueCode, List<Integer> aimStatus,
			Date saleTime);

	public Long saveGameOrder(com.winterframework.firefrog.game.entity.GameOrder order, boolean b,
			GamePackageType plan, Long id,List<GamePackageItem> itemList,boolean isNeedFreeze) throws Exception;
	public Long saveGameOrder(GameContext ctx,com.winterframework.firefrog.game.entity.GameOrder order, boolean b,
			GamePackageType plan, Long id,List<GamePackageItem> itemList,boolean isNeedFreeze) throws Exception;

	/** 
	* @Title: getNextIssueGameOrder 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param orderId
	* @return
	*/
	public List<GameOrder> getNextIssueGameOrder(Long orderId);

	/** 
	* @Title: getGamePlanOrderListByLotteryAndIssueCode 
	* @Description: 获取彩种奖期下面是追号订单的订单列表
	* @param lotteryId
	* @param issueCode
	* @return
	*/
	public List<GameOrder> getGamePlanOrderListByLotteryAndIssueCode(Long lotteryId, Long issueCode); 
	/**
	 * 获取订单
	 * @param lotteryId
	 * @param issueCode
	 * @return
	 */
	public List<GameOrder> getGameOrderByIssueAndLottery(Long lotteryId, Long issueCode);
	List<GameOrder> getFromPlanByIssueAndLottery(Long lotteryId, Long issueCode);
 
	/** 
	* @Title: noticeWinOrder 
	* @Description: 中奖订单通知
	*/
	void noticeWinOrder(); 
	
	/**
	 * 获取后续订单（含本期）
	 * @param planId
	 * @param issueCode
	 * @return
	 */
	public List<GameOrder> getOrderFollowedByPlanIdAndIssueCode(Long planId, Long issueCode); 
	
	/**
	 * 获取普通方案生成的订单
	 * @param lotteryId
	 * @param issueCode
	 * @return
	 */
	public List<GameOrder> getNotPlanByLotteryAndIssue(Long lotteryId, Long issueCode);
	public List<GameOrder> getByPackageId(Long packageId) throws Exception;
	
	/**
	 * 获取订单（根据追号明细ID--理想情况不存在多条(xml中先加上撤销过滤)) 
	 * @param planDetailId
	 * @return
	 */
	public GameOrder getOrderByPlanDetailId(Long planDetailId);
	
	/**
	 * 获取订单（根据彩种和投注时间）
	 * @param lotteryId
	 * @param startTim
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public List<GameOrder> getGameOrderByLotteryIdAndTime(Long lotteryId,Date startTime,Date endTime) throws Exception;
	
	
	/**
	 * 撤销订单（非追号生成）
	 * @param lotteryId
	 * @param issueCode
	 * @throws Exception
	 */
	void cancelNotPlan(Long lotteryId,Long issueCode) throws Exception;
	
	/**
	 * 获取等待开奖的订单（根据PlanId）
	 * @param planId
	 * @throws Exception
	 */
	public List<GameOrder> getGameOrderWaiting(Long planId) throws Exception;
	
	/**
	 * 秒秒彩根据彩种用户ID和时间查询
	 * @param LotteryId
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	List<GameOrder> getByLotteryUserIdTime(Long lotteryId,Long userId,Date startTime,Date endTime) throws Exception;

	/**
	 * @param issueCode  获取当期的订单数据
	 * @return
	 */
	public List<GameOrder> getOrderListByGameIssue(Long issueCode);
	/**
	 * 未调用资金订单总数目
	 * @param ctx
	 * @param lotteryId
	 * @param issueCode
	 * @return
	 * @throws Exception
	 */
	int getUnfundedCountByLotteryIdAndIssueCode(GameContext ctx,Long lotteryId,Long issueCode) throws Exception;
	/**
	 * 未调用资金订单列表
	 * @param ctx
	 * @param lotteryId
	 * @param issueCode
	 * @return
	 * @throws Exception
	 */
	List<GameOrder> getUnfundedByLotteryIdAndIssueCodeAndBatchSize(GameContext ctx,Long lotteryId,Long issueCode,int batchSize) throws Exception;
	List<GameOrder> getByLotteryIdAndIssueCodeAndFundStatusAndIndexes(GameContext ctx,Long lotteryId,Long issueCode,int beginIndex,int endIndex) throws Exception;
	
	/**
	 * 更新资金状态
	 * @param ctx
	 * @param fundStatus
	 * @param orderIdList
	 * @return
	 * @throws Exception
	 */ 
	int updateFundStatus(GameContext ctx,GameOrder.FundStatus fundStatus,List<String> orderCodeList) throws Exception;
}
