package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GamePlan;

/**
 * 
* @ClassName: ICommonGamePlanService 
* @Description: 公共追号接口， 
* @author Richard
* @date 2014-5-7 下午4:54:49 
*
 */
public interface ICommonGamePlanService { 
	public int save(GameContext ctx,GamePlan plan) throws Exception;
	/**
	 * 追号暂停
	 * @param planId
	 * @throws Exception
	 */
	public int pause(GameContext ctx,Long planId) throws Exception;
	/**
	 * 追号继续
	 * @param planId
	 * @throws Exception
	 */
	public int continues(GameContext ctx,Long planId) throws Exception;
	
	/**
	 * 追号结束(结点行为）
	 * @param planId
	 * @throws Exception
	 */
	public int finish(GameContext ctx,Long planId) throws Exception;
	/**
	 * 追号停止
	 * @param planId
	 * @throws Exception
	 */
	public int stop(GameContext ctx,Long planId) throws Exception;
	
	public GamePlan getById(Long planId) throws Exception;
	/**
	 * 
	* @Title: continueGamePlan 
	* @Description: 继续追号
	* @param planId
	* @throws Exception
	 */
	public void continueGamePlan(Long planId) throws Exception;
	
	/**
	 * 
	* @Title: pauseGamePlan 
	* @Description: 暂停追号
	* @param planId
	* @throws Exception
	 */
	public void pauseGamePlan(Long planId) throws Exception;
	
	/** 
	* @return 
	 * @throws Exception 
	 * @Title: checkHasNextPlanOrder 
	* @Description: 校验追号订单是否存在下一期追号订单
	*/
	public boolean checkHasNextPlanOrder(Long gameOrderId) throws Exception;

	/** 
	* @Title: checkIsExceuteGamePlan 
	* @Description: 校验是否应该执行追号计划
	* @param gameOrderId
	* @return
	* @throws Exception
	*/
	public boolean checkIsExceuteGamePlan(Long gameOrderId) throws Exception;
	
	/**
	 * 
	* @Title: updateGamePlanRevocation 
	* @Description: 更新撤销追号信息。
	* @param planId
	* @param issueCode
	* @param winAmount 要扣减的中奖金额
	* @throws Exception
	 */
	public void updateGamePlanRevocation(Long planId,Long issueCode,boolean isAskGamePlan,String user) throws Exception;
	
	public GamePlan getGamePlanById(Long planId) throws Exception;
	
	public void updateGamePlan(GamePlan plan) throws Exception;
	
	public GamePlan getGamePlanByOrderId(Long orderId) throws Exception;
	GamePlan getByPackageId(Long packageId) throws Exception;
	/**
	 * 新增补充订单开奖调度事件
	 * @param lotteryId
	 * @param issueCode
	 * @throws Exception
	 */
	public void addMakeupOrderDrawEvent(Long lotteryId, Long issueCode) throws Exception;
	public void addMakeupOrderDrawEvent(Long lotteryId, List<Long> issueCodeList) throws Exception;
	
	public void reservationCalled() throws Exception ;
}
