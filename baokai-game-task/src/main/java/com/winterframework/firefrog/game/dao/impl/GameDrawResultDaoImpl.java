package com.winterframework.firefrog.game.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameDrawResultDao;
import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.LotteryIdAndIssueCode;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 
* @ClassName: GameIssueResultDaoImpl 
* @Description: 开奖信息DAO
* @author Richard
* @date 2013-11-18 下午2:41:38 
*
 */
@Repository("gameDrawResultDaoImpl")
public class GameDrawResultDaoImpl extends BaseIbatis3Dao<GameDrawResult> implements IGameDrawResultDao {

	@Override
	public String getnumberRecordByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryid", lotteryId);
		map.put("issueCode", issueCode);
		return this.sqlSessionTemplate.selectOne("getnumberRecordByLotteryIdAndIssueCode", map);
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
	/** 
	* @Title: getFollowDrawResuleByLotteryIdAndIssueCode 
	* @Description: 获取该奖期 以及后续99期开奖结果
	* @param lotteryId
	* @param issueCode
	* @return
	*/	
	public List<GameDrawResult> getFollowDrawResuleByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryid", lotteryId);
		map.put("issueCode", issueCode);
		return this.sqlSessionTemplate.selectList("getFollowDrawResuleByLotteryIdAndIssueCode", map);
	} 
	
}
