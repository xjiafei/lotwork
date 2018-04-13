package com.winterframework.firefrog.game.dao;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.LotteryIdAndIssueCode;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/**
 * 游戏历史中奖号码相关统计DAO接口
 * @author Denny 
 * @date 2013-7-22 下午3:26:13 
 */
public interface IGameDrawResultDao extends BaseDao<GameDrawResult> {

	/**
	 * 根据彩种历史中奖号码 
	 * @param lotteryId
	 * @return
	 * @throws Exception
	 */
	public List<GameDrawResult> getAllByLotteryId(long lotteryId) throws Exception;

	/**
	 * 根据彩种和期数查询历史中奖号码
	 * @param lotteryId
	 * @param countIssue
	 * @return
	 */
	public List<GameDrawResult> getAllByLotteryIdAndCountIssue(Long lotteryId, Integer countIssue);

	/**
	 * 根据彩种和期号查询当期中奖号码
	 * @param lotteryId
	 * @param issueCode
	 * @return
	 */
	public String getNumberRecordByLotteryIdAndIssueCode(Long lotteryId, Long issueCode);
	
	/**
	 * 根据彩种和期号查询当期中奖记录，若无期号则取最大的奖期号码那笔资料。
	 * @param lotteryId 彩種ID
	 * @param issueCode 當期獎期編碼
	 * @return
	 */
	public GameDrawResult getDrawResuleByLotteryIdAndIssueCode(Long lotteryId, Long issueCode);
	
	/**
	 * 获取开奖信息
	 * @param lotteryId
	 * @param countIssue
	 * @return
	 * @throws Exception
	 */
	public List<GameDrawResult> getGameDrawResultByLotteryId(Long lotteryId, Integer countIssue) throws Exception;
	
	/**
	 * 根据LotteryId获取当前已开奖号码
	 * @param lotteryId
	 * @return
	 * @throws Exception
	 */
	public Long currentIssueCode(Long lotteryId) throws Exception;

	public List<GameDrawResult> getDrawResultByDate(Long lotteryId, Date startDate, Date endDate) throws Exception;
	
	public List<LotteryIdAndIssueCode> getAllLotteryIdAndIssueCode() throws Exception; 
	
	/**
	 * 获取开奖信息
	 * @param lotteryId
	 * @param issueCode
	 * @return
	 * @throws Exception
	 */
	public String getnumberRecordByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) throws Exception;
}
