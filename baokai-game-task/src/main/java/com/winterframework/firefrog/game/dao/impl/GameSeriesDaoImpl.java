package com.winterframework.firefrog.game.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameSeriesDao;
import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameSeriesDaoImpl")
public class GameSeriesDaoImpl extends BaseIbatis3Dao<GameSeries> implements IGameSeriesDao {

	@Override
	public GameSeries getByLotteyId(Long lotteryid) {
		return this.sqlSessionTemplate.selectOne("com.winterframework.firefrog.game.dao.vo.GameSeries.getByLotteryId",
				lotteryid);
	}

	@Override
	public List<GameSeries> getAllValidLottery(Integer statusL) {
		Long status = Long.valueOf(statusL);//修改参数类型与配置文件不统一
		return this.sqlSessionTemplate.selectList("getAllValidLottery", status);
	}

}
