
package com.winterframework.firefrog.game.service.impl;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.common.util.ProcessResult;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.service.ICommonGamePlanService;
import com.winterframework.firefrog.game.service.IGamePlanOnlyService;
import com.winterframework.firefrog.game.service.IGamePlanService;
import com.winterframework.firefrog.game.service.utils.ParamsParserUtil;

 
@Service("gamePlanOnlyServiceImpl") 
@Transactional(rollbackFor = Exception.class)
public class GamePlanOnlyServiceImpl implements IGamePlanOnlyService {
	private static final Logger log = LoggerFactory.getLogger(GamePlanOnlyServiceImpl.class);
	
	@Resource(name = "generateNotWinGamePlanServiceImpl")
	private IGamePlanService generateNotWinGamePlanService;
	@Resource(name = "gamePlanService")
	protected ICommonGamePlanService gamePlanService;
	
	@Override
	public void doBusiness(GameControlEvent event) throws Exception {
		/** 
		 * 1.导出当期注单信息
		 * 2.导出活动
		 */
		// 解析event.params 得到需要的业务信息 
		Map map = ParamsParserUtil.parse(event.getParams());
		if (map == null || map.size() == 0) {
			throw new Exception("调度事件的参数不正确");
		}		
		Long lotteryId = map.get("lotteryId") == null ? null : Long.valueOf(((String) map.get("lotteryId")));
		Long issueCode = map.get("issueCode") == null ? null : Long.valueOf(((String) map.get("issueCode")));
		Long planId = map.get("planId") == null ? null : Long.valueOf(((String) map.get("planId"))); 
		if(lotteryId==null || issueCode==null || planId==null){
			throw new Exception("调度事件的参数不正确");
		} 
		GameContext ctx=new GameContext(); 
		ProcessResult result=new ProcessResult();
		generateNotWinGamePlanService.generateGamePlan(ctx, result, lotteryId, issueCode, planId);
		//生成调度任务(可能存在多个奖期）
		List<Long> issueCodeList = (List<Long>) result.getFromRetParaMap(String.valueOf(lotteryId));
		if (issueCodeList != null) {
			for (Long ret_issueCode : issueCodeList) {
				//这里不作奖期是否开奖判断，因为存在并发，添加调度任务，由调度控制并发
				gamePlanService.addMakeupOrderDrawEvent(lotteryId, ret_issueCode);
			}
		}
	}  
////////////////////////业务服务----end////////////////////////// 
}
