package com.winterframework.firefrog.game.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.entity.GameOrderOperationsEntity;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/**
 * 
* @ClassName: IGameOrderDao 
* @Description: GAMEORDER DAO
* @author Richard
* @date 2013-11-18 下午3:38:59 
*
 */
public interface IGameOrderDao extends BaseDao<GameOrder> {

	/**
	 * 
	* @Title: queryOrderByLotteryIdAndIssueCode 
	* @Description: 获取订单信息
	* @param lotteryId
	* @param issueCode
	* @return
	* @throws Exception
	 */
	public List<GameOrder> queryOrderByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) throws Exception;

	/**
	 * 
	* @Title: queryOrderByParent 
	* @Description: 根据父ID和类型查找对应的Order列表
	* @param parentId
	* @param parentType
	* @return
	* @throws Exception
	 */
	public List<GameOrder> queryOrderByParent(Long parentId, Long parentType) throws Exception;

	/** 
	* @Title: getOrdersByIssueCode 
	* @Description: 根据奖期查询订单数据 
	* @param @param issueCode
	* @param @return    设定文件 
	* @return List<GameOrder>    返回类型 
	* @throws 
	*/
	public List<GameOrderOperationsEntity> getOrdersByIssueCode(Long issueCode);

	/**
	 * 
	* @Title: saveGameOrder 
	* @Description:生成订单信息
	* @param gameOrderVo
	* @return
	 */
	public Long saveGameOrder(GameOrder gameOrderVo);

	/**
	 * 
	* @Title: updateOrders 
	* @Description: 批量更新订单状态为撤销订单 
	* @param map
	* @throws Exception
	 */
	public void updateOrders(Map<String, Object> map) throws Exception;

	/**
	 * 
	* @Title: updateOrders2)
	* @Description: 批量更新订单状态为撤销订单 (起始-结束期号)
	* @param map
	* @throws Exception
	 */
	public void updateOrders2(Map<String, Object> map) throws Exception;

	public List<GameOrder> getGameOrderByPreGameIssueAndLotterId(Long issueCode, Long lotteryId) throws Exception;

	public GameOrder getGameOrderBySlipId(Long gameSlipId);

	/**
	 * 
	* @Title: getGameOrderListByLotteryAndIssueCode 
	* @Description: 取中奖或未中奖信息。
	* @param lotteryId
	* @param issueCode
	* @return
	* @throws Exception
	 */
	public List<GameOrder> getGameOrderListByLotteryAndIssueCode(Long lotteryId, Long issueCode) throws Exception;

	/** 
	* @Title: selectGameOrderByPlanIdAndIssueCode 
	* @Description: 根据追号ID和奖期号查找对应的GameOrder
	* @param planid
	* @param issueCode
	* @return GameOrder
	*/
	public GameOrder selectGameOrderByPlanIdAndIssueCode(Long planid, Long issueCode);

	/** 
	* @Title: selectGameOrderByPlanid 
	* @Description: 根据追号ID查找对应的GameOrder列表
	* @param planid
	* @return
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
	* @Title: selectFollowGameOrderByPlanid 
	* @Description: 根据planid查询orderid之后生成的订单列表 
	* @param orderMap
	* @return
	*/
	public List<GameOrder> selectFollowGameOrderByPlanid(Map<String, Object> orderMap);

	/** 
	* @Title: getGameOrderByLotteryIdAndIssueCodeStatus 
	* @Description: 根据彩种id，奖期，已经状态获取对应的列表
	* @param orderMap
	* @return
	*/
	public List<GameOrder> getGameOrderByLotteryIdAndIssueCodeStatus(Map<String, Object> orderMap);

	/** 
	* @Title: undoGameOrders 
	* @Description: 撤销订单
	* @param orderMap
	*/
	public void undoGameOrders(Map<String, Object> orderMap);

	/** 
	* @Title: getCalculateTimeByLotteryIdAndIssueCode 
	* @Description:根据彩种和奖期获取计奖时间
	* @param orderMap
	* @return
	*/
	public Date getCalculateTimeByLotteryIdAndIssueCode(Map<String, Object> orderMap);

	/** 
	* @Title: getGameOrderByLotteryIdAndIssueCodeAndSaleTime 
	* @Description: 根据彩种和奖期,计奖时间获取订单
	* @param orderMap
	* @return
	*/
	public List<GameOrder> getGameOrderByLotteryInfo(Map<String, Object> orderMap);

	/** 
	* @Title: getNextIssueGameOrder 
	* @Description: 获取
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
	* @Title: getBetAfterDrawTimeOrder 
	* @Description: 找出在开奖时间后投注的单
	* @param lotteryId
	* @param issueCode
	* @param drawTime
	* @return
	*/
	List<GameOrder> getBetAfterDrawTimeOrders(Long lotteryId, Long issueCode, Date drawTime);

	/**
	 * 
	* @Title: getOrderByCancalGamePlanDetail 
	* @Description: 根据撤销追号明细获取已产生追号订单
	* @param planId
	* @return
	 */
	public List<GameOrder> getOrderByCancalGamePlanDetail(Long planId, Long issueCode);

	/** 
	* @Title: queryOrderByLotteryIdAndIssueCodeForKaiJiang 
	* @Description: 开奖查询订单
	* @param lotteryId
	* @param issueCode
	* @return
	*/
	public List<GameOrder> queryOrderByLotteryIdAndIssueCodeForKaiJiang(Long lotteryId, Long issueCode);
	
	/**
	 * 获取订单
	 * @param lotteryId
	 * @param issueCode
	 * @return
	 */
	public List<GameOrder> getGameOrderByIssueAndLottery(Long lotteryId, Long issueCode);
	List<GameOrder> getFromPlanByIssueAndLottery(Long lotteryId, Long issueCode);
	
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
	List<GameOrder> getByPackageId(Long packageId) throws Exception;
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
	 * 暂停追号时更新后续订单：撤销
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

	public List<GameOrder> getOrderListByGameIssue(Long issueCode);
	/**
	 * 获取未调用资金订单总数目
	 * @param lotteryId
	 * @param issueCode
	 * @param fundStatus
	 * @return
	 * @throws Exception
	 */
	Integer getCountByLotteryIdAndIssueCodeAndFundStatus(Long lotteryId, Long issueCode,GameOrder.FundStatus fundStatus) throws Exception;
	/**
	 * 获取未调用资金订单列表
	 * @param lotteryId
	 * @param issueCode
	 * @param fundStatus
	 * @param batchSize
	 * @return
	 * @throws Exception
	 */
	List<GameOrder> getByLotteryIdAndIssueCodeAndFundStatusAndBatchSize(Long lotteryId, Long issueCode,GameOrder.FundStatus fundStatus,int batchSize) throws Exception;
	List<GameOrder> getByLotteryIdAndIssueCodeAndFundStatusAndIndexes(Long lotteryId, Long issueCode,GameOrder.FundStatus fundStatus,int beginIndex,int endIndex) throws Exception;
	/**
	 * 更新资金状态
	 * @param fundStatus
	 * @param orderIdList
	 * @return
	 * @throws Exception
	 */
	Integer updateFundStatus(GameOrder.FundStatus fundStatus,List<String> orderCodeList) throws Exception;
	
	public int updateMMC(GameOrder order);

	/**
	 * 根據UserId取出目前投注完成的總金額
	 * @param userId
	 * @param statuses
	 * @return
	 */
	public Long getSumAmtByUserIdAndStatus(Long userId,List<Integer> statuses,Date dateLimit,Date chargeTime);
	
	/**
	 * 計算今日投注金額byUser
	 * @param userId
	 * @param statuses
	 * @return
	 */
	public Long getSumAmtByUserThisDay(Long userId, List<Integer> statuses,Date validTime);
	
	public List<String> queryBeginMissionOrder(Map<String,Object> params);
	
	
	public GameOrder getOrderByPlanIdAndIssueCode(Long planId, Long nextIssueCode);
	
}
