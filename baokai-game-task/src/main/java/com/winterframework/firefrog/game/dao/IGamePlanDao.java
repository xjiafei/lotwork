package com.winterframework.firefrog.game.dao;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/**
 * 
* @ClassName: IGamePlanDao 
* @Description: 追号计划DAO
* @author Richard
* @date 2013-11-18 下午8:14:56 
*
 */
public interface IGamePlanDao extends BaseDao<GamePlan> {

	/**
	 * 
	* @Title: getChannelIdByOrderId 
	* @Description: 通过订单id获取chnnelId
	* @param id
	* @return
	 */
	Long getChannelIdByOrderId(Long id);

	/**
	 * 
	* @Title: getPauseGamePlanList 
	* @Description: 获取彩种的暂停追号计划
	* @param lotteryId
	* @return
	 */
	List<GamePlan> getPauseGamePlanList(Long lotteryId);

	/**
	 * 
	* @Title: getPauseGamePlanList 
	* @Description: 根据彩期获取彩种的追号计划
	* @param lotteryId
	* @return
	 */
	List<GamePlan> getGamePlanListByIssue(Long lotteryId);

	/** 
	* @Title: getGamePlanByPackageId 
	* @Description: 根据方案id获取追号对象
	* @param parentid
	* @return
	*/
	GamePlan getGamePlanByPackageId(Long packageId);

	/** 
	* @Title: undoGamePlan 
	* @Description: 重做追号计划
	* @param lotteryId
	* @param issueCode
	* @param saleDate
	*/
	void redoGamePlan(Long lotteryId, Long issueCode, Date saleDate);

	/** 
	* @Title: getGamePlanByOrderId 
	* @Description: 根据OrderId获取追号对象
	* @param orderId
	* @return
	*/
	GamePlan getGamePlanByOrderId(Long orderId);
	GamePlan getByPackageId(Long packageId) throws Exception;
	/**
	 * 
	* @Title: getOrderIdbyPlanId 
	* @Description: 根据PlanId获取 OrderId
	* @param planId
	* @param issueCode
	* @return
	* @throws Exception
	 */
	Long getOrderIdbyPlanId(Long planId, Long issueCode) throws Exception;

	GamePlan getPlanById(Long planId);
	 
	/**
	 * 获取包含该彩种该奖期的所有追号计划
	 * @param lotteryId
	 * @param issueCode
	 * @return
	 * @throws Exception
	 */
	List<GamePlan> getGamePlanByLotteryIdAndIssueCode(Long lotteryId,Long issueCode) throws Exception;
	List<GamePlan> getByLotteryIdAndStartIssueCode(Long lotteryId,Long startIssueCode) throws Exception;
	
	/**
	 * 获取包含该彩种该奖期且未生成第一期訂單的所有追号计划
	 * #7598 Fh4.0 后台 - 投注内容空白 :  修正第一筆追號細項被撤銷後下一筆追號訂單無法自動生成的問題
	 * @param lotteryId
	 * @param issueCode
	 * @return
	 * @throws Exception
	 */
	List<GamePlan> getGamePlanByLotteryIdAndIssueCodeAndNoOrder(Long lotteryId,Long issueCode) throws Exception;
	boolean hasNoOrder(Long planId) throws Exception;
}
