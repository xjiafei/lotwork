package com.winterframework.firefrog.game.service;

import java.util.Date;

/** 
* @ClassName: IGameIssueSaleService 
* @Description: 投注奖期撤销接口类
* @author 你的名字 
* @date 2014-4-22 上午11:41:19 
*  
*/
public interface IGameIssueSaleService {

	/** 
	* @Title: undo 
	* @Description: 撤销奖期下面的订单
	* @param lotteryId
	* @param issueCode
	* @throws Exception
	*/
	void undo(Long lotteryId, Long issueCode) throws Exception;

	/** 
	* @Title: undo 
	* @Description:撤销指定日期之后的订单
	* @param lotteryId
	* @param issueCode
	* @param saleTime
	* @throws Exception
	*/
	void undo(Long lotteryId, Long issueCode, Date saleTime) throws Exception;
}
