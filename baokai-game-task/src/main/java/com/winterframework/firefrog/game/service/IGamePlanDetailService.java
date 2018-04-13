package com.winterframework.firefrog.game.service;

import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;


/**
 *IGamePlanDetailService
 * @ClassName
 * @Description 追号明细接口
 * @author ibm
 * 2014年9月7日
 */
public interface IGamePlanDetailService { 
	/**
	 * 基础服务方法--更新
	 * @param detail
	 * @return
	 * @throws Exception
	 */
	public int save(GameContext ctx,GamePlanDetail detail) throws Exception;
	/**
	 * 追号明细执行
	 * @param ctx
	 * @param planDetailId
	 * @return 
	 * @throws Exception
	 */
	public int execute(GameContext ctx,Long planDetailId) throws Exception;
	public int execute(GameContext ctx,GamePlanDetail detail) throws Exception;
	/**
	 * 追号明细暂停
	 * @param planId
	 * @throws Exception
	 */
	public int pause(GameContext ctx,Long planDetailId) throws Exception;
	public int pause(GameContext ctx,GamePlanDetail detail) throws Exception;
	/**
	 * 追号明细继续
	 * @param planId
	 * @throws Exception
	 */
	public int continues(GameContext ctx,Long planDetailId) throws Exception;
	public int continues(GameContext ctx,GamePlanDetail detail) throws Exception;
	/**
	 * 追号明细撤销
	 * @param planId
	 * @throws Exception
	 */
	public int cancel(GameContext ctx,Long planDetailId) throws Exception;
	public int cancel(GameContext ctx,GamePlanDetail detail) throws Exception;
	
	/**
	 * 获取后续的追号（不含本期）
	 * @param lotteryId
	 * @param issueCode
	 * @param planId
	 * @return
	 * @throws Exception
	 */
	List<GamePlanDetail> getGamePlanDetailFollowedByPlanAndIssue(Long planId,Long issueCode) throws Exception;
	
	void reset(GameContext ctx,GamePlanDetail planDetail) throws Exception;
	
	//临时服务
	public Map<String, Long> getSummary(Long planId) throws Exception;
}
