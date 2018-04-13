package com.winterframework.firefrog.game.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameSeriesConfigCheckDao;
import com.winterframework.firefrog.game.dao.IGameSeriesConfigDao;
import com.winterframework.firefrog.game.dao.IGameSeriesDao;
import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.game.entity.GameSeriesConfigEntity;
import com.winterframework.firefrog.game.service.IGameSeriesConfigService;
import com.winterframework.modules.page.PageRequest;

/**
 * 
* @ClassName: GameSeriesConfigServiceImpl 
* @Description: 彩种信息接口服务
* @author Richard
* @date 2013-11-27 下午3:12:56 
*
 */
@Service("gameSeriesConfigServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameSeriesConfigServiceImpl implements IGameSeriesConfigService {

	@Resource(name = "gameSeriesDaoImpl")
	private IGameSeriesDao gameSeriesDao;
	@Resource(name = "gameSeriesConfigCheckDaoImpl")
	private IGameSeriesConfigCheckDao gameSeriesConfigCheckDao;

	@Resource(name = "gameSeriesConfigDaoImpl")
	private IGameSeriesConfigDao gameSeriesConfigDao;

	/**
	 * 根据彩种Id和状态获取彩种信息。
	* Title: getAllGameSeries
	* Description:
	* @param lotteryId
	* @param status
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameSeriesConfigService#getAllGameSeries(java.lang.Long, java.lang.Integer)
	 */
	@Override
	public List<GameSeries> getAllGameSeries(Long lotteryId, Integer status) throws Exception {
		PageRequest<GameSeries> page = new PageRequest<GameSeries>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryid", lotteryId);
		map.put("status", status);
		page.setFilters(map);
		return gameSeriesDao.getAllByPage(page).getResult();
	}

	@Override
	public GameSeriesConfigEntity queryGameSeriesConfigByLotteryId(Long lotteryId) throws Exception {
		return getGameSeriesConfigByLotteryId(lotteryId);
	}

	private GameSeriesConfigEntity getGameSeriesConfigByLotteryId(Long lotteryId) throws Exception {
		GameSeriesConfigEntity entity = gameSeriesConfigDao.getGameSeriesConfigByLotteryId(lotteryId);
		/*GameSeriesConfigEntity check = gameSeriesConfigCheckDao.getGameSeriesConfigByLotteryId(lotteryId);
		if (check == null) {
			return entity;
		} else {
			if (!entity.getBackoutStartFee().equals(check.getBackoutStartFee())) {
				check.setBackoutStartFee_bak(entity.getBackoutStartFee());
			}
			if (!entity.getBackoutRatio().equals(check.getBackoutRatio())) {
				check.setBackoutRatio_bak(entity.getBackoutRatio());
			}
			if (!entity.getIssuewarnBackoutTime().equals(check.getIssuewarnBackoutTime())) {
				check.setIssuewarnBackoutTime_bak(entity.getIssuewarnBackoutTime());
			}
			if (!entity.getIssuewarnExceptionTime().equals(check.getIssuewarnExceptionTime())) {
				check.setIssuewarnExceptionTime_bak(entity.getIssuewarnExceptionTime());
			}
			return entity;
		}*/
		return entity;
	}
	
	@Override
	public void gameSeriesChangeStatus() throws Exception{
		PageRequest<GameSeries> page = new PageRequest<GameSeries>();
		Map<String, Object> map = new HashMap<String, Object>();
		Date now = new Date();
		map.put("takeOffTime", true);
		page.setFilters(map);
		List<GameSeries> gameSeries = gameSeriesDao.getAllByPage(page).getResult();
		System.out.println("gameSeriesChangeStatus 取得size="+gameSeries.size());
		for(GameSeries gs:gameSeries){
			System.out.println("gameSeriesChangeStatus gs.id="+gs.getLotteryid());
			if(gs.getTakeOffTime() != null){
				if(gs.getTakeOffTime().before(now)){
					gs.setStatus(gs.getStatus() == 0 ? 1 : 0);
					gs.setTakeOffTime(null);
					gameSeriesDao.update(gs);
				}else{
					continue;
				}
			}
		}
	}
	
	public GameSeriesConfigEntity getSeriesConfigByLotteryId(Long lotteryId) throws Exception {
		return gameSeriesConfigDao.getGameSeriesConfigByLotteryId(lotteryId);
	}
}
