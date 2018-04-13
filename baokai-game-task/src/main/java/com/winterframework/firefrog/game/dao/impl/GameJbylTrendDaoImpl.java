package com.winterframework.firefrog.game.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameJbylTrendDao;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.dao.vo.LotteryIdAndIssueCode;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 
* @ClassName: GameTrendJbylDaoImpl 
* @Description: 号码走势分析表 DAO 实现类
* @author Richard & Denny
* @date 2013-12-28 下午4:15:08 
*
 */
@Repository("gameJbylTrendDaoImpl")
public class GameJbylTrendDaoImpl extends BaseIbatis3Dao<GameTrendJbyl> implements IGameJbylTrendDao {

	@Override
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
	public List<LotteryIdAndIssueCode> getLastLotteryIdAndIssueCode() throws Exception {
		List<GameTrendJbyl> GameTrendJbyls = this.sqlSessionTemplate.selectList("getLastLotteryIdAndIssueCode");
		List<LotteryIdAndIssueCode> result = new ArrayList<LotteryIdAndIssueCode>();
		if (!GameTrendJbyls.isEmpty()) {
			for (GameTrendJbyl GameTrendJbyl : GameTrendJbyls) {
				LotteryIdAndIssueCode code = new LotteryIdAndIssueCode();
				code.setIssueCode(GameTrendJbyl.getIssueCode());
				code.setLotteryid(GameTrendJbyl.getLotteryid());
				result.add(code);
			}
		}
		return result;
	}
	
	public GameTrendJbyl getPreGameTrendChart(Long lotteryId , Long issueCode,Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		map.put("userId", userId);
		return this.sqlSessionTemplate.selectOne(getQueryPath("getPreGameTrendChart"), map);
	}
	
	/** 
	* @Title: deleteFollowGameTrendChart 
	* @Description: 删除某彩种，某期后续的走势图
	* @param lotteryId
	* @param issueCode
	*/
	public int deleteFollowGameTrendChart(Long lotteryId , Long issueCode,Long userId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		map.put("userId", userId);
		return this.sqlSessionTemplate.delete("deleteFollowGameTrendChart", map);
	}
	@Override
	public List<GameTrendJbyl> getLatestByLotteryIdAndBetTypeAndType(
			Long lotteryId, Integer groupCode, Integer setCode,
			Integer methodCode, Integer trendType, Integer topNum)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("groupCode", groupCode);
		map.put("setCode", setCode);
		map.put("methodCode", methodCode);
		map.put("trendType", trendType);
		map.put("topNum", topNum);
		return this.sqlSessionTemplate.selectList("getLatestByLotteryIdAndBetTypeAndType", map);
	}
	@Deprecated
	@Override
	public GameTrendJbyl getLatestSpecificByLotteryIdAndBetTypeAndTypeAndNum(
			Long lotteryId, Integer groupCode, Integer setCode,
			Integer methodCode, Integer trendType, Integer num)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("groupCode", groupCode);
		map.put("setCode", setCode);
		map.put("methodCode", methodCode);
		map.put("trendType", trendType);
		map.put("num", num);
		return this.sqlSessionTemplate.selectOne("getLatestSpecificByLotteryIdAndBetTypeAndTypeAndNum", map);
	}

	@Override
	public int getCheckChartMMC(Long lotteryId, Long issueCode) {	
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		GameTrendJbyl gameTrendJbyl =  this.sqlSessionTemplate.selectOne("getCheckChartMMC", map);
		if(gameTrendJbyl != null){
			return 0;
		}else{
			return 1;
		}
	}
}
