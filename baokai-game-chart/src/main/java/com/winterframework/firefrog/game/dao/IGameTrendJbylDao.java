package com.winterframework.firefrog.game.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.dao.vo.LotteryIdAndIssueCode;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/**
 * 
* @ClassName: IGameTrendJbylDao 
* @Description: 号码走势分析表DAO
* @author Denny
* @date 2014-4-1 下午4:14:32 
*
 */
public interface IGameTrendJbylDao extends BaseDao<GameTrendJbyl> {
	
	/**
	 * 
	* @Title: getTrendJbyl 
	* @Description: 查询遗漏数据
	* @param   lotteryId
	* @param   gameGroupCode
	* @param   gameSetCode
	* @param   trendType
	* @param   num 
	* @return List<GameTrendJbyl>    返回类型 
	* @throws
	 */
	public List<GameTrendJbyl> getTrendJbyl(Long lotteryId, Integer gameGroupCode, Integer gameSetCode, Integer betMethodCode, Integer trendType, int num);

	/** 
	* @Title: getTrendByBetMethod 
	* @Description: (这里用一句话描述这个方法的作用) 
	* @param   设定文件 
	* @return List<GameTrendJbyl>    返回类型 
	* @throws 
	*/
	public List<GameTrendJbyl> getTrendByBetMethod(Long lotteryId, Integer gameGroupCode, Integer gameSetCode,
			Integer betMethodCode, int num);
	
	/**
	 * 
	* @Title: getLastLotteryIdAndIssueCode 
	* @Description: 获取所有彩种的最后统计奖期
	* @return List<GameJbylTrend>    返回类型 
	* @throws
	 */
	public List<LotteryIdAndIssueCode> getLastLotteryIdAndIssueCode() throws Exception;
	
	/**
	 * 
	* @Title: getLastGameTrendChart 
	* @Description: 查询彩种某一奖期的数据
	* @param LotteryIdAndIssueCode
	* @return List<GameJbylTrend>    返回类型 
	* @throws
	 */
	public List<GameTrendJbyl> getGameTrendChart(LotteryIdAndIssueCode lotteryIdAndIssueCode) throws Exception;
	
	public GameTrendJbyl getLastWeiShuTrend(Long lotteryId, Integer gameGroupCode);

	/** 
	* @Title: getTrendJbylTimePeriod 
	* @Description:  查询某一时间段内的
	* @param   设定文件 
	* @return List<GameTrendJbyl>    返回类型 
	* @throws 
	*/
	public List<GameTrendJbyl> getTrendJbylTimePeriod(Long lotteryId, Integer gameGroupCode, Integer gameSetCode, Integer betMethodCode, Integer trendType, Date startDate, Date endDate);
	
	public List<GameTrendJbyl> getTrendResultByLotteryIdAndCountIssue(Long lotteryId, Integer count) throws Exception;

	public List<GameTrendJbyl> getTrendResultByDate(Long lotteryId, Date startDate, Date endDate) throws Exception;

	public List<GameTrendJbyl> getWapChart(Long lotteryId);
	
	public List<Map<String, Object>> getDrawResultsByUserIdAndLotteryId(Long userid, Long lotteryId, Integer num, Date startDate, Date endDate);
	
	/**
	 * 判斷走勢圖查詢類型
	 * @param lotteryid
	 * @return
	 */
	public String checkQueryTypeByLotteryid();
}
