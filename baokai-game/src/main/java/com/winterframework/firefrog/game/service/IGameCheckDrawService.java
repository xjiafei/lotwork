package com.winterframework.firefrog.game.service;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.service.bean.BigAwardCacheBean;

/**
 * 
* @ClassName: IGameCheckDrawService 
* @Description: 订单中奖情况判断 Service
* @author Richard
* @date 2014-2-12 下午9:03:02 
*
 */
public interface IGameCheckDrawService {
	
	
	/** 
	* @Title: doCheckIsDraw 
	* @Description: 验证并更新订单中奖
	* @param result
	* @param order
	* @param record
	* @param calculateDate
	* @param bigAward
	* @return ProcessResult
	* @throws Exception
	*/
	void doCheckIsDrawMMC(GameContext ctx, String record, GameOrder order, BigAwardCacheBean bigAward,Long diamondLv) throws Exception;
	
	/**
	 * 预计奖
	 * @param numberRecord 开奖号码
	 * @param orderId  订单ID
	 * @return  中奖金额  0:未中奖
	 * @throws Exception
	 */
	Long preCalculateWin(String numberRecord,Long orderId) throws Exception;
	
	
	/** 
	* @Title: updateIssueOpenAwardFinshed 
	* @Description: 更新奖期开奖结束，变更奖期开奖号码 开奖次数
	* @param gameIssue
	* @param record
	* @throws Exception
	*/
	void updateIssueOpenAwardFinshed(GameIssue gameIssue , String record) throws Exception;
}
