package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.dao.vo.GameSlipAssist;

/**
 * 注单辅助服务接口
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月12日
 */
public interface IGameSlipAssistService { 
	////////////////////////行为----begin////////////////////////// 
	public List<GameSlipAssist> getBySlipId(GameContext ctx,Long slipId) throws Exception;    
	public int win(GameContext ctx,GameSlip slip,GameSlipAssist slipAssist) throws Exception;  
	public int unwin(GameContext ctx,GameSlip slip,GameSlipAssist slipAssist) throws Exception;  
	////////////////////////行为----end//////////////////////////  	
}
