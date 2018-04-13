package com.winterframework.firefrog.game.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.entity.GameIssueEntity;

/** 
* @ClassName: IGameIssueFacadeService 
* @Description: 奖期Facade Service 
* @author Denny 
* @date 2013-11-21 上午10:15:55 
*  
*/
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public interface IGameIssueFacadeService {

	/**
	 * 监控理论开奖时间截止流程 
	 * @param lotteryId 控制彩種；null 時不執行吉利分分彩(99111)、非 null 時只執行吉利分分彩。
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<GameIssueEntity>> monitoringOpenDrawTimeEnd(Long lotteryId) throws Exception;

	/**
	 * 
	* @Title: toBeFinished 
	* @Description:  待结束奖期流程 
	* @throws Exception    
	 */
	public void toBeFinished() throws Exception;
	
	public void sendSsqSaleEndEmail() throws Exception;
}
