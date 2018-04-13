package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;


/**
 *IGamePlanDetailService
 * @ClassName
 * @Description 返点服务接口
 * @author ibm
 * 2014年9月7日
 */
public interface IGameRetPointService { 
	void reset(GameContext ctx,GamePlanDetail planDetail) throws Exception;
}
