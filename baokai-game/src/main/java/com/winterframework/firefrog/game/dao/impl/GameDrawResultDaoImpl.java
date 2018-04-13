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
 * 游戏历史中奖号码相关统计DAO 
 * @author Denny 
 * @date 2013-7-22 下午3:50:25 
 */
@Repository("gameDrawResultDaoImpl")
public class GameDrawResultDaoImpl extends BaseIbatis3Dao<GameDrawResult> implements IGameDrawResultDao {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public List<GameDrawResult> getAllByLotteryId(long lotteryid) throws Exception {
		List<GameDrawResult> gdrs = sqlSessionTemplate.selectList(
				"com.winterframework.firefrog.game.dao.vo.GameDrawResult.getAllByLotteryId", lotteryid);
		return gdrs;
	}

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

	public List<GameDrawResult> getGameDrawResultByLotteryId(Long lotteryId, Integer countIssue) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("lotteryid", lotteryId);
		map.put("countIssue", countIssue);

		List<GameDrawResult> drawResults = sqlSessionTemplate.selectList("getGameDrawResultByLotteryId", map);

		return drawResults;

	}

	@Override
	public List<GameDrawResult> getAll() {
		return super.getAll();
	}

	@Override
	public List<GameDrawResult> getAllByEntity(GameDrawResult entity) {
		return super.getAllByEntity(entity);
	}

	@Override
	public String getNumberRecordByLotteryIdAndIssueCode(Long lotteryid, Long issueCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != lotteryid) {
			map.put("lotteryid", lotteryid);
		}
		if (null != issueCode) {
			map.put("issueCode", issueCode);
		}
		String numberRecord = sqlSessionTemplate.selectOne("getnumberRecordByLotteryIdAndIssueCode", map);
		return numberRecord;
	}

	@Override
	public GameDrawResult getDrawResuleByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) {
		GameDrawResult gameDrawResult = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryid", lotteryId);
		if (null != issueCode) {
			map.put("issueCode", issueCode);
			gameDrawResult = sqlSessionTemplate.selectOne("getDrawResuleByLotteryIdAndIssueCode", map);
		} else {
			gameDrawResult = sqlSessionTemplate.selectOne("getDrawResuleByLotteryId", lotteryId);
		}
		return gameDrawResult;
	}

	@Override
	public Long currentIssueCode(Long lotteryId) throws Exception {
		Long value = sqlSessionTemplate.selectOne("currentIssueCode", lotteryId);

		if (null != value) {
			return value;
		}
		return 0L;
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
	
	@Override
	public String getnumberRecordByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryid", lotteryId);
		map.put("issueCode", issueCode);
		return this.sqlSessionTemplate.selectOne("getnumberRecordByLotteryIdAndIssueCode", map);
	}

}
