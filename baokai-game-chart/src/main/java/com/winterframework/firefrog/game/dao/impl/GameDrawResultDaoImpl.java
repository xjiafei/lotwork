package com.winterframework.firefrog.game.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameDrawResultDao;
import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.LotteryIdAndIssueCode;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: GameDrawResultDaoImpl 
* @Description: 游戏历史中奖号码相关统计DAO 
* @author Denny 
* @date 2013-7-22 下午3:50:25 
*  
*/
@Repository("gameDrawResultDaoImpl")
public class GameDrawResultDaoImpl extends BaseIbatis3Dao<GameDrawResult> implements IGameDrawResultDao {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public List<GameDrawResult> getAllByLotteryIdAndCountIssue(Long lotteryid, Integer countIssue) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != lotteryid) {
			map.put("lotteryid", lotteryid);
		}
		if (null != countIssue) {
			map.put("countIssue", countIssue);
		}

		List<GameDrawResult> gdrs = sqlSessionTemplate.selectList("getAllByLotteryIdAndCountIssue", map);
		return gdrs;
	}

	@Override
	public List<GameDrawResult> getDrawResultByDate(Long lotteryId, Date startDate, Date endDate) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryid", lotteryId);
		map.put("startDate", dateFormat.format(startDate));
		map.put("endDate", dateFormat.format(endDate));

		return sqlSessionTemplate.selectList("getDrawResultByDate", map);
	}
	
	@Override
	public List<LotteryIdAndIssueCode> getAllLotteryIdAndIssueCode() throws Exception {
		List<GameDrawResult> gameDrawResultList = this.getAll();
		List<LotteryIdAndIssueCode> list = new ArrayList<LotteryIdAndIssueCode>();
		for (GameDrawResult gdr : gameDrawResultList) {
			LotteryIdAndIssueCode item = new LotteryIdAndIssueCode();
			item.setIssueCode(gdr.getIssueCode());
			item.setLotteryid(gdr.getLotteryid());
			list.add(item);
		}
		return list;
	}

	/**
	* Title: getNextDrawResuleByLotteryIdAndIssueCode
	* Description:
	* @param lotteryIdAndIssueCode
	* @return 
	* @see com.winterframework.firefrog.game.dao.IGameDrawResultDao#getNextDrawResuleByLotteryIdAndIssueCode(com.winterframework.firefrog.game.dao.vo.LotteryIdAndIssueCode) 
	*/
	@Override
	public GameDrawResult getNextDrawResuleByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryid", lotteryId);
		map.put("issueCode", issueCode);
		return this.sqlSessionTemplate.selectOne("getNextDrawResuleByLotteryIdAndIssueCode", map);
	}
	
	@Override
	public List<GameDrawResult> getAllByLotteryId(long lotteryid) throws Exception {
		List<GameDrawResult> gdrs = sqlSessionTemplate.selectList(
				"getAllByLotteryId", lotteryid);
		return gdrs;
	}
	
	
	
	
}
