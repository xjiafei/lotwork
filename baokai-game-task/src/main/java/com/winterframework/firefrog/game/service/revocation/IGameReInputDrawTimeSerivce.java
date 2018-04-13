package com.winterframework.firefrog.game.service.revocation;

import java.util.Date;

/** 
* @ClassName IGameReInputDrawTimeSerivce 
* @Description 重新输入开奖时间 
* @author  hugh
* @date 2014年5月6日 上午11:25:10 
*  
*/
public interface IGameReInputDrawTimeSerivce {
	
	/** 
	* @Title: reInputDrawTime 
	* @Description: 重新输入开奖时间 
	* @param lotteryId
	* @param issueCode
	* @param time
	*/
	void reInputDrawTime(Long lotteryId, Long issueCode, Date time) throws Exception;

}
