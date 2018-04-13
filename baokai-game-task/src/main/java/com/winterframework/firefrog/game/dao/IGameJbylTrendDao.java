package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.dao.vo.LotteryIdAndIssueCode;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/**
 * 
* @ClassName: IGameTrendJbylDao 
* @Description: 号码走势分析表DAO
* @author Richard & Denny
* @date 2013-12-28 下午4:14:32 
*
 */
public interface IGameJbylTrendDao extends BaseDao<GameTrendJbyl> {

	/**
	 * 
	* @Title: getLastLotteryIdAndIssueCode 
	* @Description: 获取所有彩种的最后统计奖期
	* @return List<GameTrendJbyl>    返回类型 
	* @throws
	 */
	public List<LotteryIdAndIssueCode> getLastLotteryIdAndIssueCode() throws Exception;
	
	/**
	 * 
	* @Title: getLastGameTrendChart 
	* @Description: 查询彩种某一奖期的数据
	* @param LotteryIdAndIssueCode
	* @return List<GameTrendJbyl>    返回类型 
	* @throws
	 */
	public List<GameTrendJbyl> getGameTrendChart(LotteryIdAndIssueCode lotteryIdAndIssueCode) throws Exception;
	
	/** 
	* @Title: deleteFollowGameTrendChart 
	* @Description: 删除某彩种，某期后续的走势图
	* @param lotteryId
	* @param issueCode
	*/
	int deleteFollowGameTrendChart(Long lotteryId , Long issueCode,Long userId);
	
	/** 
	* @Title: deleteFollowGameTrendChart 
	* @Description: 删除某彩种，某期后续的走势图
	* @param lotteryId
	* @param issueCode
	*/
	int getCheckChartMMC(Long lotteryId , Long issueCode);
	
	/** 
	* @Title: getPreGameTrendChart 
	* @Description: 获取前一期走势信息
	* @param lotteryId
	* @param issueCode
	*/
	GameTrendJbyl getPreGameTrendChart(Long lotteryId , Long issueCode,Long userId);
	
	List<GameTrendJbyl> getLatestByLotteryIdAndBetTypeAndType(Long lotteryId,Integer groupCode,Integer setCode,Integer methodCode,Integer trendType,Integer topNum) throws Exception;
	GameTrendJbyl getLatestSpecificByLotteryIdAndBetTypeAndTypeAndNum(Long lotteryId,Integer groupCode,Integer setCode,Integer methodCode,Integer trendType,Integer num) throws Exception;
	
}
