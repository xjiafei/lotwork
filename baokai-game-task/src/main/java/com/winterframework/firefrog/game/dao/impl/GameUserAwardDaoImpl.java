package com.winterframework.firefrog.game.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameUserAwardDao;
import com.winterframework.firefrog.game.dao.vo.GameUserAward;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 
* @ClassName: GameUserAwardDaoImpl 
* @Description: 用户奖金组Dao
* @author Richard
* @date 2013-11-20 下午1:43:03 
*
 */
@Repository("gameUserAwardDaoImpl")
public class GameUserAwardDaoImpl extends BaseIbatis3Dao<GameUserAward> implements IGameUserAwardDao {

	@Override
	public Long getUserAward(Long lotteryid, Integer gameGroupCode, Integer gameSetCode, Long betMethodCode, Long userid) {

		GameUserAward award = new GameUserAward();
//		award.setLotterySeriesCode(lotteryid.intValue());
//		award.setGameGroupCode(new Long(gameGroupCode));
//		award.setGameSetCode(new Long(gameSetCode));
//		award.setBetMethodCode(new Long(betMethodCode));
//		award.setUserid(userid);
//		award.setType(0); //投注奖金组

		List<GameUserAward> resultList = this.getAllByEntity(award);
		if (null == resultList || resultList.isEmpty()) {
			return 0L;
		}

     	return resultList.get(0).getRetValue();
	}

}
