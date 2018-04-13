package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameWarnUser;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/**
 * 
* @ClassName: IGameWarnUserDao 
* @Description: GameWarnUser Dao
* @author Richard
* @date 2014-2-12 下午2:49:30 
*
 */
public interface IGameWarnUserDao extends BaseDao<GameWarnUser> {
	void deleteByLotteryIssue(Long lotteryid, Long issueCode);
	/**
	 * 
	* @Title: queryWarnUserByUserIdAndLotteryIdIssueCode 
	* @Description: 根据userId、lotteryId、issueCode 获取GameWarnUser信息 
	* @param userid
	* @param lotteryid
	* @param issueCode
	* @return
	 */
	GameWarnUser queryWarnUserByUserIdAndLotteryIdIssueCode(Long userid, Long lotteryid, Long issueCode);

	/**
	 * 获取警告用户前置最近一期数据
	 * @param userid
	 * @param lotteryid
	 * @param issueCode
	 * @return
	 */
	List<GameWarnUser> getLastWarnUserBefore(Long userid, Long lotteryid, Long issueCode);
	/**
	 * 获取警告用户后置最后一期连续中奖数据
	 * @param userId
	 * @param lotteryId
	 * @return
	 */
	List<GameWarnUser> getLastWarnUserAfter(Long userId, Long lotteryId, Long issueCode);
	
}
