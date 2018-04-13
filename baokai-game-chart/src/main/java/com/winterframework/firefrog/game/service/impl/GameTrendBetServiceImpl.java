package com.winterframework.firefrog.game.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.IGameTrendBetDao;
import com.winterframework.firefrog.game.dao.vo.GameTrendBet;
import com.winterframework.firefrog.game.service.IGameTrendBetService;


 
/**
 * 走势图奖期服务实现类
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月28日
 */
@Service("gameTrendBetServiceImpl") 
@Transactional(rollbackFor = Exception.class)
public class GameTrendBetServiceImpl implements IGameTrendBetService { 
	@Resource(name="gameTrendBetDaoImpl")
	private IGameTrendBetDao gameTrendBetDao;  
	@Override
	public GameTrendBet getByLotteryIdAndBetTypeAndType(GameContext ctx,
			Long lotteryId, Integer groupCode, Integer setCode, Integer methodCode,Integer type)
			throws Exception {
		return this.gameTrendBetDao.getByLotteryIdAndBetTypeAndType(lotteryId,groupCode,setCode,methodCode,type);
	} 
}
