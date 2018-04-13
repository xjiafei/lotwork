package com.winterframework.firefrog.game.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameSeriesDao;
import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.game.service.IGameLotteryService;

/** 
* @ClassName: GameLotteryServiceImpl 
* @Description: 彩种service实现类
* @author Denny 
* @date 2013-9-30 下午1:27:03 
*  
*/
@Service("gameLotteryServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameLotteryServiceImpl implements IGameLotteryService {

	@Resource(name = "gameSeriesDaoImpl")
	private IGameSeriesDao gameSeriesDaoImpl;
	
	@Override
	public List<GameSeries> queryLotteryList(Long lotteryid, Integer status) {
		return gameSeriesDaoImpl.getLotterySeriesByLotteryidAndStatus(lotteryid, status);
	}

}
