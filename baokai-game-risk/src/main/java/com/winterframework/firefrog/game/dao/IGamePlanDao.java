package com.winterframework.firefrog.game.dao;

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
	* @Title: getGamePlanByOrderId 
	* @Description: 根据OrderId获取追号对象
	* @param orderId
	* @return
	*/
	GamePlan getGamePlanByOrderId(Long orderId);

}
