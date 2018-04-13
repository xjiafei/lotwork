package com.winterframework.firefrog.game.service.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameSeriesDao;
import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.game.enums.YesNo;
import com.winterframework.firefrog.game.service.IGameSeriesService;

@Service("gameSeriesServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameSeriesServiceImpl implements IGameSeriesService {

	@Resource(name = "gameSeriesDaoImpl")
	private IGameSeriesDao gameSeriesDao;
	
	@Override
	public List<GameSeries> getAll() {
		return gameSeriesDao.getAll();
	}
	@Override
	public List<GameSeries> getAllForSale() {
		List<GameSeries> gameSeriesList=getAll();
		if(gameSeriesList!=null){	 
		    for(Iterator<GameSeries> iter=gameSeriesList.iterator();iter.hasNext();){
		    	GameSeries gameSeries=iter.next(); 
				if(gameSeries.getStatus().intValue()==YesNo.NO.getValue()){
					iter.remove();
				}
			}
		}
		return gameSeriesList;
	}
	@Override
	public List<GameSeries> getAllSeriesForSale() throws Exception {
		return gameSeriesDao.getGameSeries();
	}
	

}
