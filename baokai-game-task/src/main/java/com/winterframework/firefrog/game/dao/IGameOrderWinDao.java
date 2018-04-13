package com.winterframework.firefrog.game.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.game.dao.vo.GameOrderWin;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/** 
* @ClassName: IGameOrderWinDao 
* @Description: 中奖订单Dao
* @author Alan
* @date 2013-11-19 下午1:52:22 
*  
*/
public interface IGameOrderWinDao extends BaseDao<GameOrderWin> {
	void deleteByLotteryIssue(Long lotteryid, Long issueCode);
	
	/**
	 * 
	* @Title: updateGameOrderWins 
	* @Description: 撤销中奖订单记录
	* @param map
	* @throws Exception
	 */
	public void updateGameOrderWins(Map<String, Object> map) throws Exception;

	/**
	 * 
	* @Title: getGameOrderWinsByIssueCode 
	* @Description: 根据奖期获取中奖订单记录
	 */
	public List<GameOrderWin> getGameOrderWinsByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) throws Exception;
	
	/**
	 * 
	* @Title: selectGameOrderWinByOrderId 
	* @Description: 根据订单ID查找对应的中奖订单信息 
	* @param orderId
	* @return
	 */
	public GameOrderWin selectGameOrderWinByOrderId(Long orderId);

	/**
	 * 
	* @Title: getGameOrderWinsByLotteryIdAndIssueCode 
	* @Description:获取中奖信息。
	* @param lotteryId
	* @param issueCode
	* @param date
	* @return
	 */
	public List<GameOrderWin> getGameOrderWinsByLotteryIdAndIssueCode(Long lotteryId, Long issueCode, Date date);

	/** 
	* @Title: updateStatus 
	* @Description: 批处理更新状态
	* @param orderIds
	* @param status
	* @return
	*/
	int updateStatus(List<Long> orderIds, Long status);
	
	List<GameOrderWin> selectUnNoticeSendedMondeyWinOrder() ;
}
