package com.winterframework.firefrog.game.service;

import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;
 
/**
 *IGamePlanDetailService
 * @ClassName 追号明细接口
 * @Description
 * @author ibm
 * 2015年3月8日
 */
public interface IGamePlanDetailService { 
	/**
	 * 基础服务方法--更新
	 * @param detail
	 * @return
	 * @throws Exception
	 */
	public int save(GameContext ctx,GamePlanDetail detail) throws Exception; 
	
	//临时服务
	public Map<String, Long> getSummary(Long planId) throws Exception;
	List<GamePlanDetail> getGamePlanDetailsByPlanId(Long planId) throws Exception;
}
