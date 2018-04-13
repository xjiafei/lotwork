package com.winterframework.firefrog.game.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameSeriesConfigDao;
import com.winterframework.firefrog.game.dao.IGameSeriesDao;
import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.game.dao.vo.GameSeriesConfig;
import com.winterframework.firefrog.game.dao.vo.VOConverter4Task;
import com.winterframework.firefrog.game.entity.GameSeriesConfigEntity;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 
* @ClassName: GameSeriesConfigDaoImpl 
* @Description: 彩票运营参数DAO
* @author Richard & Alan
* @date 2013-9-17 下午5:47:27 
*
 */
@Repository("gameSeriesConfigDaoImpl")
public class GameSeriesConfigDaoImpl extends BaseIbatis3Dao<GameSeriesConfig> implements IGameSeriesConfigDao {

	@Resource(name = "gameSeriesDaoImpl")
	private IGameSeriesDao gameSeriesDao;

	@Override
	public GameSeriesConfigEntity getGameSeriesConfigByLotteryId(Long lotteryId) throws Exception {
		GameSeriesConfig vo = this.sqlSessionTemplate.selectOne("getGameSeriesConfigByLotteryId", lotteryId);
		if (null == vo) {
			return null;
		}
		GameSeriesConfigEntity entity = VOConverter4Task.gameSeriesConfig2GameSeriesConfigEntity(vo);
		return entity;
	}

	@Override
	public List<GameSeries> getAllGameSeries( Integer status) throws Exception {
		return gameSeriesDao.getAllValidLottery(status);
	}

}
