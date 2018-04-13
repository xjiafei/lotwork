package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.dao.vo.GameSlipDetail;

/**
 * 注单明细服务接口
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月12日
 */
public interface IGameSlipDetailService { 
	////////////////////////行为----begin////////////////////////// 
	public List<GameSlipDetail> getByParentId(GameContext ctx,Long parentId) throws Exception;    
	public int save(GameContext ctx,GameSlip slip,GameSlipDetail slipDetail) throws Exception;  
	////////////////////////行为----end//////////////////////////  	
}
