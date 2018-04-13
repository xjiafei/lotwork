package com.winterframework.firefrog.game.dao;

import com.winterframework.firefrog.game.dao.vo.GameUserAward;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IGameUserAwardDao extends BaseDao<GameUserAward> {

	/**
	 * 
	* @Title: getUserAward 
	* @Description: 获取用户实际奖金。 
	* @param lotteryid
	* @param gameGroupCode
	* @param gameSetCode
	* @param betMethodCode
	* @param userid
	* @return
	 */
	Long getUserAward(Long lotteryid, Integer gameGroupCode, Integer gameSetCode, Long betMethodCode, Long userid);

}
