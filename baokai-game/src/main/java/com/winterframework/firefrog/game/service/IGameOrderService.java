package com.winterframework.firefrog.game.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.entity.GameBetDetailTotAmountEntity;
import com.winterframework.firefrog.game.entity.GameOrder;
import com.winterframework.firefrog.game.entity.GameOrderOperationsEntity;
import com.winterframework.firefrog.game.entity.GamePackage.GamePackageType;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.entity.GameSlipOperationsEntity;
import com.winterframework.firefrog.game.web.dto.GameOrderOperationsQueryDTO;
import com.winterframework.firefrog.game.web.dto.GameOrderQueryDTO;
import com.winterframework.firefrog.game.web.dto.TaskOpenAwardResponse;
import com.winterframework.firefrog.game.web.dto.UserBetInfoSumStruc;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: IGameOrderService 
* @Description: 游戏订单Service 
* @author Denny 
* @date 2013-7-22 下午2:35:35 
*  
*/
public interface IGameOrderService {
	/** 
	* @Title: saveGameOrder 
	* @Description: 保存订单数据
	* @param gameOrder
	* @return
	* @throws Exception
	*/
	public Long saveGameOrder(GameOrder gameOrder) throws Exception;

	/**
	* @throws Exception 
	 * @Title: queryOrders 
	* @Description: 订单列表查询
	*/
	public Page<GameOrder> queryOrders(PageRequest<GameOrderQueryDTO> pr) throws Exception;

	/**
	* @param userId 
	 * @throws Exception 
	 * @Title: queryOrder
	* @Description: 单个订单查询
	*/
	public GameOrder queryOrder(long orderId, Long userId) throws Exception;

	/**
	* @Title: querySlipsByOrderId
	* @Description: 根据订单ID查询注单列表
	*/
	public List<GameSlip> querySlipsByOrderId(long orderId);
	
	public List<GameSlip> querySlipsByMap(Map<String ,Object> map);
	
	/**
	* @Title: querySlipsByOrderId
	* @Description: 根据订单ID查询注单列表
	*/
	public List<GameSlip> querySlips(long lotteryId,long userId,long issueCode,int status);

	/** 
	* @Title: queryOrdersByPlanId 
	* @Description: 根据追号ID查询 
	* @param @param planId
	* @param @return    设定文件 
	* @return List<OrdersStruc>    返回类型 
	* @throws 
	*/
	public List<GameOrder> queryOrdersByPlanId(long planId);

	/** 
	* @Title: cancelOrder 
	* @Description:   撤销订单
	* @param orderId
	* @param cancelTime
	 * @param userId 
	 * @throws Exception 
	*/
	public void cancelOrder(Long orderId, Long cancelTime, Long userId, boolean isFrontUser) throws Exception;
	/** 
	* @Title: cancelOrder 
	* @Description:   撤销订单
	* @param orderId
	* @param cancelTime
	 * @param userId 
	 * @throws Exception 
	*/
	public void cancelOrderMMC(Long orderId, Long cancelTime, Long userId, boolean isFrontUser) throws Exception;

	/**
	 * 
	* @Title: queryOrderOperations 
	* @Description: 订单记录运营分页列表
	* @param pr
	* @return
	* @throws Exception
	 */
	public Page<GameOrderOperationsEntity> queryOrderOperations(PageRequest<GameOrderOperationsQueryDTO> pr)
			throws Exception;

	/**
	 * 
	* @Title: queryOrderOperationsList 
	* @Description: 订单记录数据列表
	* @param queryDTO
	* @return
	* @throws Exception
	 */
	public List<GameOrderOperationsEntity> queryOrderOperationsList(GameOrderOperationsQueryDTO queryDTO)
			throws Exception;

	/**
	 * 
	* @Title: queryOrderOperationsDetail 
	* @Description: 根据订单ID查询订单记录详细 
	* @param orderid
	* @return
	* @throws Exception
	 */
	public GameOrderOperationsEntity queryOrderOperationsDetail(Long orderid) throws Exception;

	/**
	 * 
	* @Title: querySlipOperationsList 
	* @Description: 查询注单运营数据列表
	* @param orderid
	* @return
	* @throws Exception
	 */
	public List<GameSlipOperationsEntity> querySlipOperationsListByOrderID(Long orderid) throws Exception;

	/**
	 * 
	* @Title: queryOrderOperationsListByPlanID 
	* @Description: 查询订单运营数据列表 
	* @param planid
	* @return
	* @throws Exception
	 */
	public List<GameOrderOperationsEntity> queryOrderOperationsListByPlanID(Long planid) throws Exception;

	/**
	 * 
	* @Title: saveGameOrder 
	* @Description: 创建订单信息。
	* @param gameOrder
	* @param isGamePackage
	* @return
	* @throws Exception
	 */
	public Long saveGameOrder(GameOrder gameOrder, boolean isGamePackage, GamePackageType packageType, Long planId)
			throws Exception;

	/** 
	* @Title: getCancelOrderCharge 
	* @Description: 后台获取撤单金额  和getWebCancelOrderCharge的区别在于 后台处理时 金额已经乘以过10000
	* @param lotteryId
	* @param amount
	 * @return Double
	 * @throws Exception 
	*/
	public Double getCancelOrderCharge(Long lotteryId, Double amount) throws Exception;

	/** 
	* @Title: getWebCancelOrderCharge 
	* @Description: 投注页面撤单金额获取 
	* @param lotteryId
	* @param totalBetAmount
	* @return
	* @throws Exception
	*/
	Double getWebCancelOrderCharge(Long lotteryId, Double totalBetAmount) throws Exception;

	/** 
	* @Title: getUserBal 
	* @Description: 获取用户余额信息 
	* @param userId
	* @return
	* @throws Exception
	*/
	Long getUserBal(Long userId) throws Exception;
	
	/** 
	* @Title: getPlanIdByOrder 
	* @Description: 根据orderId获取planId
	* @param orderId order id
	* @return
	*/
	public Long getPlanIdByOrder(Long orderId) throws Exception;
	
	public com.winterframework.firefrog.game.dao.vo.GameOrder getGameOrderByOrderCode(String orderCode) throws Exception;

//	public Response<TaskOpenAwardResponse> mmcOpenAward(Long orderid) throws Exception;
	
	public boolean mmcOpenAward(Long orderid) throws Exception;

	public Integer getCurrentIssueMutiple(String betDetail, String betTypeCode, long userId, long lotteryId, Long issueCode);

	public Integer getCurrentIssueMutipleFile(String betDetail, String betTypeCode, long userId, long lotteryId, Long issueCode);
	
	public UserBetInfoSumStruc queryUserBetInfoByDate(Long userId,Date startDate) throws Exception;
	void setAwardGroup(GameOrder gameOrder) throws Exception;
	
	/**
	 * 新手任務查詢,查詢新手帳號投注每天總額
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<String> queryBeginMissionOrder(Map<String,Object> params) throws Exception;
	
	/**
	 * 雙倍寶箱活動,查詢帳號投注每天總額(超級2000(70%))
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Long queryUserDailyBets(Long userId) throws Exception;
	
	public void updateGameOrderMMC(com.winterframework.firefrog.game.dao.vo.GameOrder order) throws Exception;
	
	public int addOrderLog(GameContext ctx, com.winterframework.firefrog.game.dao.vo.GameOrder order, String operation,
			String remark) throws Exception ;
	public List<com.winterframework.firefrog.game.dao.vo.GameOrder> getWinningList(String orderIds,Long userId,Long lotteryId,Long issueCode) throws Exception;
	
	/**
	 * 获取订单（根据追号明细ID--理想情况不存在多条(xml中先加上撤销过滤)) 
	 * @param planDetailId
	 * @return
	 */
	public com.winterframework.firefrog.game.dao.vo.GameOrder getOrderByPlanDetailId(Long planDetailId);
	
	/**
	 * 刪除秒秒彩被撤銷的訂單
	 * @param orderId
	 * @param cancelTime
	 * @param userId
	 * @param isFrontUser
	 * @throws Exception
	 */
	public void deleteCancelOrderMMC(Long orderId, Long cancelTime, Long userId, boolean isFrontUser) throws Exception; 
	
	
	public List<GameOrder> queryPlayerBet(long lotteryId, Long userId, Long issueCode) throws Exception;
	
	
	/**
	 * 查詢帳號投注區間總額(超級2000(80%))
	 * @param userId,startTime,endTime
	 * @return
	 * @throws Exception
	 */
	public Long queryUserDailyPeriodBets(Long userId,Date startTime, Date endTime);

	List<GameSlip> querySlipsByCondition(long lotteryId, long userId, long issueCode, String betTypeCode, String betdetail, int status);
	
	/**
	 * 獎其查詢所有gameSlip
	 * @param issueCode
	 * @return List<GameSlip>
	*/
	public List<GameSlip> querySlipsByIssueCode( long issueCode) throws Exception;
	
	/**
	 * orderIdList查詢所有gameSlip
	 * @param List<Long> orderId
	 * @return List<GameSlip>
	*/
	public List<GameSlip> querySlipsByOrderIdList( List<Long> orderId) throws Exception;
	
	/**
	 * issudeCode查詢玩法金額分布
	 * @param long issueCode
	 * @return List<GameBetDetailTotAmountEntity>
	*/
	public List<GameBetDetailTotAmountEntity> queryBetDetaiByIssudeCode( long issueCode ,long lottertId) throws Exception;
	/**
	 * 单用户单期总中奖金额
	 * @param lotteryId
	 * @param issueCode
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	Double getWinAmt(Long lotteryId,Long issueCode,Long userId) throws Exception;
}
