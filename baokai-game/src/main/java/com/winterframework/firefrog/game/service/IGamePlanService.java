package com.winterframework.firefrog.game.service;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssueLog;
import com.winterframework.firefrog.game.entity.GameOrder;
import com.winterframework.firefrog.game.entity.GamePlan;
import com.winterframework.firefrog.game.entity.GamePlanDetail;
import com.winterframework.firefrog.game.web.dto.GamePlanBetOriginDataStruc;
import com.winterframework.firefrog.game.web.dto.GamePlanQueryDTO;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/**
 * 
* @ClassName: IGamePlanService 
* @Description: 追号服务接口
* @author Richard
* @date 2013-7-22 上午9:42:31 
*
 */
public interface IGamePlanService {

	/**
	 * 
	* @param list 
	 * @Title: gamePlan 
	* @Description:追号服务接口
	* @param gamePlan
	* @param gamePlanBetOriginDataStruc  追号接触数据结构，即每期要追的投注基本数据
	 * @param currentIssueCode 
	* @return
	* @throws Exception
	 */
	public Long doBusiness(GameOrder gameOrder, List<GamePlanBetOriginDataStruc> gamePlanBetOriginDataStruc, Long currentIssueCode) throws Exception;

	/**
	* @Title: queryPlans 
	* @Description: 追号列表查询
	*/
	public Page<GamePlan> queryPlans(PageRequest<GamePlanQueryDTO> pr) throws Exception;

	/**
	* @Title: queryPlanDetail 
	* @Description: 追号详情查询
	*/
	public GamePlan queryPlanDetail() throws Exception;

	/**
	 * 
	* @Title: stopGamePlan 
	* @Description: 停止追号 
	* @param planId
	* @param cancelTime
	 * @param userId 
	 * @param userType （1为用户终止、2为系统终止）
	* @throws Exception
	 */
	public void stopGamePlan(Long planId, Long cancelTime, Long userId, Integer userType) throws Exception;

	/**
	 * 
	* @Title: reservationCalled 
	* @Description: 预约撤销 
	* @param planId
	* @param issueCode
	* @param userType （1为用户终止、2为系统终止）
	* @throws Exception
	 */
	public void reservationCalled(Long planId, Long issueCode, Long userId, Integer userType) throws Exception;
	
	/**
	 * 
	* @Title: reservationCalledChangeStatus 
	* @Description: 预约撤销 
	* @param planId
	* @param issueCode
	* @param userType （1为用户终止、2为系统终止）
	* @throws Exception
	 */
	public void reservationCalledChangeStatus(Long planId, String[] issueCode, Long userId, Integer userType) throws Exception;

	/**
	 * @param userId  
	* @Title: queryPlanById 
	* @Description: 根据追号计划ID查询该计划
	* @param @param planId
	* @param @return    设定文件 
	* @return PlansStruc    返回类型 
	* @throws 
	*/
	public GamePlan queryPlanById(Long planId, Long userId);

	/** 
	* @Title: queryPlanDetailsByPlanId 
	* @Description: 根据追号计划ID查询该计划详情列表
	* @param @param planId
	* @param @return    设定文件 
	* @return List<PlansFuturesStruc>    返回类型 
	* @throws 
	*/
	public List<GamePlanDetail> queryPlanDetailsByPlanId(Long planId);
	

	/** 
	* @Title: queryPackageIdByPlanId 
	* @Description: 根据追号计划Id获取gamePackeageId
	*/
	public Long queryPackageIdByPlanId(Long planId);
	
	/**
	 * 
	* @param warnIssueLog 
	 * @Title: cancelFollowGamePlan 
	* @Description: 撤销后续追号(异常操作项) 
	 */
	public void cancelFollowGamePlan(Long lotteryid, String startIssueCode, String endIssueCode, GameWarnIssueLog warnIssueLog) throws Exception;
	
	/**
	 * 
	* @Title: generateGamePlanByIssueCode 
	* @Description: 追号计划（由任务项目进行调用）
	* @param issueCode
	* @throws Exception
	 */
	public void generateGamePlanByIssueCode(Long issueCode,Long lotteryId) throws Exception;

	/** 
	* @Title: querySlipsByPlanId 
	* @Description: 根据追号ID查询注单信息
	* @param planId
	* @return List<GameSlip>    返回类型 
	* @throws 
	*/
	public List<GameSlip> querySlipsByPlanId(long planId) throws Exception;

	/**
	 * 
	* @Title: continueGamePlan 
	* @Description: 继续追号(由继续派奖操作触发)
	* @param lotteryid
	* @param issueCode
	* @throws Exception
	 */
	public void continueGamePlan(Long lotteryid, Long issueCode);
	
	/**
	 * 
	* @Title: undo 
	* @Description: 回滚追号信息 
	* @param lotteryId
	* @param issueCode
	* @throws Exception
	 */
	public void redo(Long lotteryId, Long issueCode) throws Exception;
	
	/**
	 * 
	* @Title: cancel 
	* @Description: 撤销追号信息根据奖期
	* @param lotteryId
	* @param issueCode
	* @throws Exception
	 */
	public void undo(Long lotteryId, Long issueCode) throws Exception;
	
	/**
	 * 
	* @Title: cancel 
	* @Description: 撤销追号信息根据奖期，时间
	* @param lotteryId
	* @param issueCode
	* @param date
	* @throws Exception
	 */
	public void undo(Long lotteryId, Long issueCode, Date date) throws Exception;
	
	public com.winterframework.firefrog.game.dao.vo.GamePlan queryPlanByCode(String planCode) throws Exception;

	/**
	 * 获取追号最晚可执行终止的时间
	* @Title: getLastPlanCanStopTime 
	* @Description: 撤销追号信息根据奖期，时间
	* @param planId
	* @throws Exception
	 */
	public Date getLastPlanCanStopTime(Long planId) throws Exception;
	
	/** 
	* @Title: getUnDoPlansCountByUserId 
	* @Description: 根据userId获取未开始与进行中追号数
	*/
	public Long getUndoPlansCountByUserId(Long userId) throws Exception;
}
