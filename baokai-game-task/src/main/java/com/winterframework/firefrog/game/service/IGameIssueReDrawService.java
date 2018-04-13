package com.winterframework.firefrog.game.service;

/** 
* @ClassName: IGameReDrawService 
* @Description: 重新开奖service 
* @author 你的名字 
* @date 2014-5-6 上午11:12:28 
*  
*/
public interface IGameIssueReDrawService {

	/** 
	* @Title: reDrawGameIssue 
	* @Description: 重新开奖 
	* @param lotteryId 彩种id
	* @param issueCode 奖期
	*/
	public void reDrawGameIssue(Long lotteryId, Long issueCode) throws Exception;
}
