package com.winterframework.firefrog.game.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameTrendJbylDao;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.dao.vo.LotteryIdAndIssueCode;
import com.winterframework.firefrog.game.entity.TrendType;
import com.winterframework.modules.web.util.RequestContext;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 
* @ClassName: GameTrendJbylDaoImpl 
* @Description: 号码走势分析表 DAO 实现类
* @author Denny
* @date 2014-4-1 下午4:15:08 
*
 */
@Repository("gameTrendJbylDaoImpl")
public class GameTrendJbylDaoImpl extends BaseIbatis3Dao<GameTrendJbyl> implements IGameTrendJbylDao {
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public List<GameTrendJbyl> getTrendJbyl(Long lotteryId, Integer gameGroupCode, Integer gameSetCode, Integer betMethodCode,
			Integer trendType, int num) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (lotteryId != null) {
			map.put("lotteryId", lotteryId);
		}
		if (gameGroupCode != null) {
			map.put("gameGroupCode", gameGroupCode);
		}
		if (gameSetCode != null) {
			map.put("gameSetCode", gameSetCode);
		}
		if (trendType != null) {
			// 和值尾数类型的取和值的值来处理
			if (trendType == TrendType.SUMVALMANTISSA.getValue()) {
				trendType = TrendType.SUMVAL.getValue();
			}
			map.put("trendType", trendType);
		}
        Long userId = RequestContext.getCurrUser().getId();
        if ((lotteryId.longValue() == 99112l||lotteryId.longValue() == 99306l) && userId != null && userId > 0) {
            map.put("userId", userId);
        }
		map.put("num", num);

		return this.sqlSessionTemplate.selectList("getTrendJbyl", map);
	}

	@Override
	public List<GameTrendJbyl> getTrendByBetMethod(Long lotteryId, Integer gameGroupCode, Integer gameSetCode,
			Integer betMethodCode, int num) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (lotteryId != null) {
			map.put("lotteryId", lotteryId);
		}
		if (gameGroupCode != null) {
			map.put("gameGroupCode", gameGroupCode);
		}
		if (gameSetCode != null) {
			map.put("gameSetCode", gameSetCode);
		}
		if (betMethodCode != null) {
			map.put("betMethodCode", betMethodCode);
		}
		map.put("num", num);
        Long userId = RequestContext.getCurrUser().getId();
        if ((lotteryId.longValue() == 99112l||lotteryId.longValue() == 99306l) && userId != null && userId > 0) {
            map.put("userId", userId);
        }
		return this.sqlSessionTemplate.selectList("getTrendJbyl", map);
	}
	
	@Override
	@Deprecated
	public List<GameTrendJbyl> getGameTrendChart(LotteryIdAndIssueCode lotteryIdAndIssueCode) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryIdAndIssueCode.getLotteryid());
		map.put("issueCode", lotteryIdAndIssueCode.getIssueCode());
		return this.sqlSessionTemplate.selectList("getGameTrendChart", map);
	}

	/**
	* Title: getAllLotteryIdAndIssueCode
	* Description:
	* @return
	* @throws Exception 
	*/
	@Override
	@Deprecated
	public List<LotteryIdAndIssueCode> getLastLotteryIdAndIssueCode() throws Exception {
		List<GameTrendJbyl> gameJbylTrends = this.sqlSessionTemplate.selectList("getLastLotteryIdAndIssueCode");
		List<LotteryIdAndIssueCode> result = new ArrayList<LotteryIdAndIssueCode>();
		if (!gameJbylTrends.isEmpty()) {
			for (GameTrendJbyl gameJbylTrend : gameJbylTrends) {
				LotteryIdAndIssueCode code = new LotteryIdAndIssueCode();
				code.setIssueCode(gameJbylTrend.getIssueCode());
				code.setLotteryid(gameJbylTrend.getLotteryid());
				result.add(code);
			}
		}
		return result;
	}

	@Override
	public GameTrendJbyl getLastWeiShuTrend(Long lotteryId, Integer gameGroupCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (lotteryId != null) {
			map.put("lotteryId", lotteryId);
		}
		if (gameGroupCode != null) {
			map.put("gameGroupCode", gameGroupCode);
		}

		return this.sqlSessionTemplate.selectOne("getLastWeiShuTrend", map);
	}

	@Override
	public List<GameTrendJbyl> getTrendJbylTimePeriod(Long lotteryId, Integer gameGroupCode, Integer gameSetCode,
			Integer betMethodCode, Integer trendType, Date startDate, Date endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (lotteryId != null) {
			map.put("lotteryId", lotteryId);
		}
		if (gameGroupCode != null) {
			map.put("gameGroupCode", gameGroupCode);
		}
		if (gameSetCode != null) {
			map.put("gameSetCode", gameSetCode);
		}
		if (trendType != null) {
			// 和值尾数类型的取和值的值来处理
			if (trendType == TrendType.SUMVALMANTISSA.getValue()) {
				trendType = TrendType.SUMVAL.getValue();
			}
			map.put("trendType", trendType);
		}
		
		map.put("startDate", startDate);
		map.put("endDate", endDate);
        Long userId = RequestContext.getCurrUser().getId();
        if ((lotteryId.longValue() == 99112l||lotteryId.longValue() == 99306l) && userId != null && userId > 0) {
            map.put("userId", userId);
        }
		return this.sqlSessionTemplate.selectList("getTrendJbylTimePeriod", map);
	}

	@Override
	public List<GameTrendJbyl> getTrendResultByLotteryIdAndCountIssue(Long lotteryId, Integer count) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != lotteryId) {
			map.put("lotteryid", lotteryId);
		}
		if (null != count) {
			map.put("countIssue", count);
		}
		return sqlSessionTemplate.selectList("getTrendResultByLotteryIdAndCountIssue", map);
	}

	@Override
	public List<GameTrendJbyl> getTrendResultByDate(Long lotteryId, Date startDate, Date endDate) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryid", lotteryId);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return sqlSessionTemplate.selectList("getTrendResultByDate", map);
	}

	@Override
	public List<GameTrendJbyl> getWapChart(Long lotteryId) {
		return sqlSessionTemplate.selectList("getWapChart", lotteryId);
	}
	
	@Override
	public List<Map<String, Object>> getDrawResultsByUserIdAndLotteryId(Long userid, Long lotteryId, Integer num, Date startDate, Date endDate){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("userid", userid);
		Integer[] numbersArray = new Integer[]{30, 50, 100};
		if(Arrays.asList(numbersArray).contains(num)){
			//判斷是否是30 or 50筆的條件
			map.put("num", (num + 1));
		}
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return sqlSessionTemplate.selectList("getDrawResultsByUserIdAndLotteryId", map);
	}
	
	public String checkQueryTypeByLotteryid(){
		String lotteryids = sqlSessionTemplate.selectOne("checkQueryTypeByLotteryid");
		return lotteryids;
	}
}
