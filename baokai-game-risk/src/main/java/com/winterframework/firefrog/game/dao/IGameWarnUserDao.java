package com.winterframework.firefrog.game.dao;

import com.winterframework.firefrog.game.dao.vo.GameWarnUser;
import com.winterframework.firefrog.game.web.dto.GameRiskWarnUserListQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/**
 * 
* @ClassName: IGameWarnUserDao 
* @Description: GameWarnUser Dao
* @author hugh
* @date 2014-4-10 下午2:49:30 
*
 */
public interface IGameWarnUserDao extends BaseDao<GameWarnUser> {

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
	* @Title: queryGameRiskWarnUser 
	* @Description: 获取风险用户 
	* @param pr  彩种 奖期  状态
	* @return
	*/
	Page<GameWarnUser> queryGameRiskWarnUser(PageRequest<GameRiskWarnUserListQueryRequest> pr);
}
