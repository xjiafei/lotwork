package com.winterframework.firefrog.game.dao;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.web.dto.GamePlanQueryDTO;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/** 
* @ClassName: IGamePlanDao 
* @Description: 游戏追号计划DAO接口
* @author Denny 
* @date 2013-7-22 下午2:38:53 
*  
*/
public interface IGamePlanDao extends BaseDao<GamePlan> {

	/**
	* @Title: getPlans 
	* @Description: 查询追号列表
	 */
	public Page<com.winterframework.firefrog.game.entity.GamePlan> getPlans(PageRequest<GamePlanQueryDTO> pr);

	/**
	 * 
	* @Title: saveGamePlan 
	* @Description: 保存追号计划信息
	* @param gamePlan
	* @return
	* @throws Exception
	 */
	public GamePlan saveGamePlan(com.winterframework.firefrog.game.entity.GamePlan gamePlan) throws Exception;

	/**
	 * 
	* @Title: stopGamePlan 
	* @Description: 停止追号计划 
	* @param planId
	* @param cancelTime
	* @param userType
	* @return
	* @throws Exception
	 */
	public Integer stopGamePlan(Long planId, Integer userType, Date cancelTime) throws Exception;

	/** 
	* @Title: getPlanById 
	* @Description: 根据追号计划ID查询计划 
	* @param @param planId
	* @param @return    设定文件 
	* @return com.winterframework.firefrog.game.entity.GamePlan    返回类型 
	* @throws 
	*/
	public com.winterframework.firefrog.game.entity.GamePlan getPlanById(Long planId);

	/** 
	* @Title: PackageIdByPlanId 
	* @Description: 根据计划id获取方案id 
	*/
	public Long getPackageIdByPlanId(Long planId);

	/**
	 * 
	* @Title: updateGamePlanSoldAmount 
	* @Description: 更新追号计划销售金额
	* @param soldAmount
	* @param id
	 */
	public void updateGamePlanSoldAmount(Long soldAmount, Long id);

	/** 
	* @Title: getPlanByUserIdAndPlanId 
	* @Description: 根据账户id和userid获取planId 
	* @param planId
	* @param userId
	* @return
	*/
	public com.winterframework.firefrog.game.entity.GamePlan getPlanByUserIdAndPlanId(Long planId, Long userId);

	/**
	 * 
	* @Title: getPlanByLotteryIdAndIssueCode 
	* @Description: 
	* @param lotteryId
	* @param issueCode
	* @param nextIssueCode
	* @return
	 */
	public List<com.winterframework.firefrog.game.dao.vo.GamePlan> getPlanByLotteryIdAndIssueCode(Long lotteryId,
			Long issueCode,Long nextIssueCode);

	/**
	 * 
	* @Title: queryPlanByLotteryIdAndIssue 
	* @Description: 
	* @param lotteryId
	* @param issueCode
	* @param date
	* @return
	 */
	public List<com.winterframework.firefrog.game.dao.vo.GamePlan> queryPlanByLotteryIdAndIssue(Long lotteryId,
			Long issueCode, Date date);
	/** 
	* @Title: getGamePlanByOrderId 
	* @Description: 根据OrderId获取追号对象
	* @param orderId
	* @return
	*/
	GamePlan getGamePlanByOrderId(Long orderId);
	/** 
	* @Title: getGamePlanByPlanCode 
	* @Description: 根据追号单号获取追号信息
	* @param planCode
	* @return
	*/
	public List<GamePlan> getGamePlanByPlanCode(String planCode);

	/** 
	* @Title: getPlanVoById 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param planId
	* @return
	*/
	public com.winterframework.firefrog.game.dao.vo.GamePlan getPlanVoById(Long planId);
	
	/** 
	* @Title: getUnDoPlansCountByUserId 
	* @Description: 根据userId获取未开始与进行中追号
	*/
	public Long getUndoPlansCountByUserId(Long userId);
}
