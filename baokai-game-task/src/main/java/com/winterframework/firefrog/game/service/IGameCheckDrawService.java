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
	* @Title: checkIsDraw 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param lotteryId
	* @param issueCode
	* @return
	* @throws Exception
	*/
	public int doBusiness(GameContext ctx, String record, GameOrder order, BigAwardCacheBean bigAward) throws Exception; 
	public List<GameOrder> doBusiness_bk(Long lotteryId, Long issueCode) throws Exception;
	/** 
	* @Title: doBusiness 
	* @Description:只开指定奖期的一个订单的操作
	* @param lotteryId
	* @param issueCode
	* @param orderId
	* @return
	*/
	public GameOrder doBusiness(Long lotteryId, Long issueCode, Long orderId) throws Exception;

	/** 
	* @Title: cancelGameOrder 
	* @Description: 撤销奖期下面的所有订单(只处理中奖订单，撤销的单据不能重新计奖)
	* @param lotteryId
	* @param issueCode
	* @throws Exception
	*/
	public void undo(Long lotteryId, Long issueCode) throws Exception;

	/** 
	* @Title: cancelGameOrder 
	* @Description: 撤销奖期下面的部分订单(只处理中奖订单，撤销的单据不能重新计奖)
	* @param lotteryId
	* @param issueCode
	* @param issueDate 撤销时间
	* @throws Exception
	*/
	public void undo(Long lotteryId, Long issueCode, Date issueDate) throws Exception;

	/** 
	* @Title: redo 
	* @Description: 重做奖期
	* @param lotteryId
	* @param issueCode
	* @throws Exception
	*/
	public void redo(Long lotteryId, Long issueCode) throws Exception;
	
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
	void doCheckIsDraw(GameContext ctx, String record, GameOrder order, BigAwardCacheBean bigAward) throws Exception; 
	void doCheckIsDrawMMC(GameContext ctx, String record, GameOrder order, BigAwardCacheBean bigAward,Long diamondLv) throws Exception; 
	
	/** 
	* @Title: updateIssueOpenAwardFinshed 
	* @Description: 更新奖期开奖结束，变更奖期开奖号码 开奖次数
	* @param gameIssue
	* @param record
	* @throws Exception
	*/
	void updateIssueOpenAwardFinshed(GameIssue gameIssue , String record) throws Exception;
	
	/** 
	* @Title: checkSBreawrd 
	* @Description: check 開獎結果 盈虧
	* @param recordNumber
	* @param loteryid
	* @param issuecode
	* @parm orderCount 注單數量門檻
	* @throws Exception
	*/
	Float checkSBreawrd (String recordNumber, Long loteryid , Long issuecode, Long orderCount)throws Exception;
}
