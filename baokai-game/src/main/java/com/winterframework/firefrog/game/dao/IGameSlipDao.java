package com.winterframework.firefrog.game.dao;

import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.entity.GamePackage;
import com.winterframework.firefrog.game.entity.GameSlipOperationsEntity;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/** 
* @ClassName: IGameSlipDao 
* @Description: 游戏投注订单注单DAO接口 
* @author Denny 
* @date 2013-7-22 下午2:25:49 
*  
*/
public interface IGameSlipDao extends BaseDao<GameSlip> {

	public void saveSlip(com.winterframework.firefrog.game.entity.GameSlip gameOrderDetail, Long orderId);

	public void saveSlipList(List<com.winterframework.firefrog.game.entity.GameSlip> details, Long orderId)
			throws Exception;

	public void saveSlipList(List<com.winterframework.firefrog.game.entity.GameSlip> details,GamePackage gamePackage, Long orderId,
			Long planDetailId) throws Exception;

	/**
	 * 
	* @Title: getSlipsByOrderId
	* @Description: 根据订单ID查询注单列表
	 */
	public List<com.winterframework.firefrog.game.entity.GameSlip> getSlipsByOrderId(long orderId);
	
	public List<com.winterframework.firefrog.game.entity.GameSlip> getSlipsByMap(Map<String ,Object> map);

	/** 
	* @Title: updateGameOrderDetailByOrderId 
	* @Description: 取消订单时更新订单详情的状态
	* @param orderId
	* @param issueCode
	 * @param cancelTime 
	* @return
	* @throws Exception
	*/
	public int updateGameOrderDetailByOrderId(Long orderId, Long issueCode, Long cancelTime) throws Exception;

	/**
	 * 
	* @Title: querySlipOperationsListByOrderID 
	* @Description: 查询注单信息列表 
	* @param orderid
	* @return
	* @throws Exception
	 */
	public List<GameSlipOperationsEntity> querySlipOperationsListByOrderID(Long orderid) throws Exception;

	/**
	 * 
	* @Title: querySlipOperationsListByPlanID 
	* @Description: 查询注单信息列表 
	* @param planid
	* @return
	* @throws Exception
	 */
	public List<GameSlipOperationsEntity> querySlipOperationsListByPlanID(Long planid) throws Exception;

	/**
	 * 
	* @Title: querySlipByLotteryIdAndIssueCode 
	* @Description: 估计LotteryId和奖期获取注单信息 
	* @param lotteryId
	* @param issueCode
	* @return
	* @throws Exception
	 */
	public List<GameSlip> querySlipByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) throws Exception;

	/**
	 * 
	* @Title: querySlipByLotteryIdAndIssueCode 
	* @Description: 估计LotteryId和奖期获取注单信息 
	* @param lotteryId
	* @param issueCode
	* @return
	* @throws Exception
	 */
	public Long queryAllSlipSale(Long lotteryId, Long issueCode, int moneyModel) throws Exception;

	/** 
	* @Title: querySlipsByPlanId 
	* @Description: 根据追号ID查询注单信息 
	* @param planId
	* @return List<GameSlip>    返回类型 
	* @throws 
	*/
	public List<GameSlip> querySlipsByPlanId(long planId);

	/**
	 * 
	* @Title: delSlipByOrderId 
	* @Description: 根据订单Id及彩种信息，删除注单信息。
	* @param id
	* @param lotteryId
	 */
	public void delSlipByOrderId(Long id, Long lotteryId);

	/**
	 * 
	* @Title: updateGameOrderDetailCancalByOrderId 
	* @Description: 撤销GamePlanDetail
	* @param id
	* @param issueCode
	 */
	public void updateGameOrderDetailCancalByOrderId(Long id, Long issueCode);

	public List<com.winterframework.firefrog.game.entity.GameSlip> getCurrentIssueUserBetRecord(String betTypeCode,
			long userId, long lotteryId, Long issueCode);
	
	public List<GameSlip> querySlipByOrder(Long orderId) throws Exception ;
	
	public int changeStatus(Long orderId, Integer fromStatus, Integer toStatus);
	
	public void updateSlipByOrderID(Long orderID, Integer status) throws Exception ;

	public List<com.winterframework.firefrog.game.entity.GameSlip> querySlips(long lotteryId, long userId, long issueCode, int status);
	
	public List<com.winterframework.firefrog.game.entity.GameSlip> querySlipsByCondition(long lotteryId, long userId, long issueCode, String betTypeCode, String betdetail, int status);
	
	/** 
	* @Title: querySlipsByIssueCode
	* @Description: 獎其查詢所有gameSlip 
	* @param issueCode
	* @return List<GameSlip>    
	* @throws 
	*/
	public List<com.winterframework.firefrog.game.entity.GameSlip> querySlipsByIssueCode(long issueCode) throws Exception;
	

	/** 
	* @Title: querySlipsByListIssueCode
	* @Description: 當天獎其查詢所有gameSlip 
	* @param List<Long> orderId
	* @return List<GameSlip>    
	* @throws 
	*/
	public List<com.winterframework.firefrog.game.entity.GameSlip> querySlipsByOrderIdList(List<Long> orderId) throws Exception;
	
	/** 
	* @Title: queryBetDetaiByIssudeCode
	* @Description: 獎其查詢玩法金額分布
	* @param issueCode
	* @return List<GameSlip>    
	* @throws 
	*/
	public List<com.winterframework.firefrog.game.entity.GameBetDetailTotAmountEntity> queryBetDetaiByIssudeCode(long issueCode ,long lotteryId) throws Exception;
}
