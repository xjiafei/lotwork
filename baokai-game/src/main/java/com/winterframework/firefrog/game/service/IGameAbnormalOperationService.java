package com.winterframework.firefrog.game.service;

/** 
* @ClassName: IGameDrawLrylService 
* @Description: 冷热遗漏查询service接口 
* @author Denny 
* @date 2013-12-20 上午10:35:02 
*  
*/
public interface IGameAbnormalOperationService {

	public void continueIssue(Long lotteryid, Long issueCode, String disposeMemo, String userAccount) throws Exception;
}
