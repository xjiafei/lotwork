package com.winterframework.firefrog.game.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameWarnUserDao;
import com.winterframework.firefrog.game.dao.vo.GameWarnUser;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 
* @ClassName: GameWarnUserDaoImpl 
* @Description: GameWarnUser Dao　Impl 
* @author Richard
* @date 2014-2-12 下午2:51:30 
*
 */
@Repository("gameWarnUserDaoImpl")
public class GameWarnUserDaoImpl extends BaseIbatis3Dao<GameWarnUser> implements IGameWarnUserDao {

	/**
	* @Title: deleteByLotteryIssue
	* @Description:删除一期数据
	* @param lotteryid
	* @param issueCode 
	*/
	@Override
	public void deleteByLotteryIssue(Long lotteryid, Long issueCode) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("lotteryid", lotteryid);
		map.put("issueCode", issueCode);
		this.sqlSessionTemplate.delete(getQueryPath("deleteByLotteryIssue"), map);
	}

	@Override
	public GameWarnUser queryWarnUserByUserIdAndLotteryIdIssueCode(Long userid, Long lotteryid, Long issueCode) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userid);
		map.put("lotteryid", lotteryid);
		map.put("issueCode", issueCode);

		return this.sqlSessionTemplate.selectOne("queryWarnUserByUserIdAndLotteryIdIssueCode", map);
	}
	
	@Override
	public List<GameWarnUser> getLastWarnUserBefore(Long userId, Long lotteryId, Long issueCode) { 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);

		return this.sqlSessionTemplate.selectList("getLastWarnUserBefore", map);
	}
	
	@Override
	public List<GameWarnUser> getLastWarnUserAfter(Long userId, Long lotteryId, Long issueCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("lotteryId", lotteryId); 
		map.put("issueCode", issueCode);

		return this.sqlSessionTemplate.selectList("getLastWarnUserAfter", map);
	} 
}
