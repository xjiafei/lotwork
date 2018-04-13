package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameBettypeStatus;
import com.winterframework.firefrog.game.entity.SellingStatus;

/** 
* @ClassName: IBetMethodSellingStatusService 
* @Description: 投注方式销售状态Service接口
* @author Denny 
* @date 2013-8-29 上午12:50:53 
*  
*/
public interface IBetMethodSellingStatusService {

	public List<SellingStatus> queryBetMethodSellingStatus(long lotteryid) throws Exception;
	
	public List<GameBettypeStatus> queryValidBetMethods(long lotteryid) throws Exception;
	
	public void modifyBetMethodSellingStatus(List<SellingStatus> sellingStatusList, Long lotteryid) throws Exception;
	
	public void checkBetMethodSellingStatus(Long lotteryid, Long auditType);
	
	public void publishBetMethodSellingStatus(Long lotteryid, Long publishType);
}
