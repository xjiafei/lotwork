package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.common.util.ProcessResult;
import com.winterframework.firefrog.game.dao.vo.GamePlan;



/**
 * 追号计划服务接口
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月3日
 */
public interface IGamePlanNewService {
	public GamePlan getById(Long planId) throws Exception;
}
