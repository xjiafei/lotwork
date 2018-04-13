package com.winterframework.firefrog.game.service;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssueLog;

/**
 * 
* @ClassName: IGameDrawService 
* @Description: 具体开奖接口类
* @author Richard
* @date 2013-11-18 上午10:12:08 
*
 */
public interface IGameDrawService {

	/**
	 * 
	* @Title: gameDrawService 
	* @Description: 奖期开奖计奖派奖流程
	* @param lotteryId
	* @param issueCode
	* @throws Exception
	 */
	public void doBusiness(Long lotteryId, Long issueCode) throws Exception; 
	/**
	 * @param lotteryId
	 * @param issueCode
	 * @param isRedraw	是否重开(重开只做重开的事，不兼职做补开的 事，所以此次事件不用考虑撤销和等待开奖的订单（即只考虑中奖和未中奖及异常的订单）
	 * @throws Exception
	 */
	//void doBusiness(Long lotteryId, Long issueCode,boolean isRedraw) throws Exception;
	void doBusiness(GameContext ctx,Long lotteryId, Long issueCode) throws Exception;

	/** 
	* @Title: doBusiness 
	* @Description: 针对一个订单的开奖流程
	* @param lotteryId
	* @param issueCode
	* @param orderId
	* @throws Exception
	*/
	public void doBusiness(Long lotteryId, Long issueCode, Long orderId) throws Exception;
	
	/**
	 * 开奖流程
	 * @param event
	 * @throws Exception
	 */
	public void doBusiness(GameControlEvent event) throws Exception;
	void doFundRequestBatch(GameContext ctx,Long lotteryId, Long issueCode, int beginIndex,int endIndex) throws Exception;
	/**
	 * 处理一批订单资金请求
	 * @param lotteryId
	 * @param issueCode
	 * @param batchSize	一批的订单数
	 * @throws Exception
	 */
	void doFundRequestSubBatch(GameContext ctx,List<GameOrder> orderList) throws Exception;
	//void doFundRequestSubBatch(GameContext ctx,List<Long> orderIdList,List<TORiskDTO> riskDtoList) throws Exception;
	
	
}
